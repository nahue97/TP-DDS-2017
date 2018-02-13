package model.repositories;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import ExceptionsPackage.IndicadorNotFoundException;
import model.Cuenta;
import model.Empresa;
import model.Indicador;
import model.IndicadorCalculado;
import model.Usuario;
import redis.clients.jedis.JedisPoolConfig;
import utils.CalculadorDeIndicadores;
import redis.clients.jedis.*;

public class RepositorioIndicadoresCalculados {

	// Singleton
	private static RepositorioIndicadoresCalculados instance;
	private static JedisPool pool;

	public RepositorioIndicadoresCalculados() {
		super();
	}

	public static synchronized RepositorioIndicadoresCalculados getInstance() {
		if (instance == null) {
			instance = new RepositorioIndicadoresCalculados();
			pool = new JedisPool(new JedisPoolConfig(), "localhost");
		}
		return instance;
	}

	public List<IndicadorCalculado> searchByExample(IndicadorCalculado indicadorCalculado) {
		// Buscamos el/los indicador/es calculados que coincidan
		List<IndicadorCalculado> indicadoresCalculados = new ArrayList<>();
		HashMap<String, IndicadorCalculado> clavesDeBusquedasAUtilizar = new HashMap<>();

		//
		// La clave es:
		// "usuarioId=usuarioId|nombre=nombre|cuentas=cuentas|periodo=periodo|empresaId=empresaId"
		//

		String claveDeBusquedaParcial = "usuarioId=" + indicadorCalculado.getUsuario().getId().toString();
		List<String> listaNombres = new ArrayList<>();
		List<String> listaPeriodos = new ArrayList<>();
		List<String> listaEmpresas = new ArrayList<>();

		if (indicadorCalculado.getNombre() != null) {
			listaNombres.add(indicadorCalculado.getNombre());
		} else {
			// Traigo todos los nombres posibles
			listaNombres.addAll(RepositorioIndicadores.getInstance().getNombresDeIndicadores());
		}
		if (indicadorCalculado.getPeriodo() != null) {
			listaPeriodos.add(indicadorCalculado.getPeriodo());
		} else {
			listaPeriodos.addAll(RepositorioCuentas.getInstance().getPeriodosDeCuenta());
		}
		if (indicadorCalculado.getEmpresa() != null) {
			listaEmpresas.add(indicadorCalculado.getEmpresa().getId().toString());
		} else {
			listaEmpresas.addAll(RepositorioEmpresas.getInstance().getAll().stream()
					.map(empresa -> empresa.getId().toString()).collect(Collectors.toList()));
		}

		// Ahora que tengo todas las listas, voy haciendo las combinaciones para
		// formar las claves
		for (String nombre : listaNombres) {
			Indicador indicadorParaBuscarCuentas = RepositorioIndicadores.getInstance().getIndicadorPorNombre(nombre);
			String cuentas = CalculadorDeIndicadores.getInstance()
					.obtenerCuentasSeparadasPorComa(indicadorParaBuscarCuentas);

			for (String periodo : listaPeriodos) {
				for (String empresaId : listaEmpresas) {
					IndicadorCalculado indicador = new IndicadorCalculado();
					indicador.setNombre(nombre);
					indicador.setUsuario(indicadorCalculado.getUsuario());
					indicador.setCuentas(cuentas);
					indicador.setPeriodo(periodo);
					Empresa empresa = new Empresa();
					empresa.setId(new Long(empresaId));
					indicador.setEmpresa(empresa);

					clavesDeBusquedasAUtilizar.put(claveDeBusquedaParcial + "|nombre=" + nombre + "|cuentas=" + cuentas
							+ "|periodo=" + periodo + "|empresaId=" + empresaId, indicador);
				}
			}
		}

		// Ahora tengo todas las combinaciones posibles, traigo los valores
		// asociados a las mismas.

		try (Jedis jedis = pool.getResource()) {
			Iterator it = clavesDeBusquedasAUtilizar.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry pair = (Map.Entry) it.next();
				String clave = pair.getKey().toString();
				IndicadorCalculado indicadorCalc = (IndicadorCalculado) pair.getValue();

				String valor = jedis.get(clave);
				if (valor != null && !valor.isEmpty()) {
					indicadorCalc.setValor(new BigDecimal(valor));
					indicadoresCalculados.add(indicadorCalc);
					// Lo agrego a la lista que voy a devolver solo si trajo un
					// valor de Redis.
					// Es decir, solo si es compatible con la info pasada.
				}

				it.remove(); // avoids a ConcurrentModificationException
			}
		}

		return indicadoresCalculados;
	}

	public List<IndicadorCalculado> getAllForCuenta(Cuenta cuenta) {
		// Obtengo todos los indicadores calculados que posean la cuenta, en ese
		// periodo y empresa
		Session session = sessionFactory.openSession();
		try {
			Criteria criteria = session.createCriteria(this.getEntityType());
			criteria.add(Restrictions.like("cuentas", cuenta.getTipo(), MatchMode.ANYWHERE));
			criteria.add(Restrictions.eq("empresa", cuenta.getEmpresa()));
			criteria.add(Restrictions.eq("periodo", cuenta.getPeriodo()));
			return criteria.list();
		} catch (HibernateException e) {
			throw new RuntimeException(e);
		} finally {
			session.close();
		}
	}

	public BigDecimal obtenerValorDeIndicador(Indicador indicador, Empresa empresaParaCalcular, String periodo) {
		String clave = "usuarioId=" + indicador.getUsuario().getId().toString() + "|nombre=" + indicador.getNombre()
				+ "|cuentas=" + CalculadorDeIndicadores.getInstance().obtenerCuentasSeparadasPorComa(indicador)
				+ "|periodo=" + periodo + "|empresaId=" + empresaParaCalcular.getId().toString();

		try (Jedis jedis = pool.getResource()) {
			String valor = jedis.get(clave);
			if (valor != null && !valor.isEmpty()) {
				return new BigDecimal(valor);
			} else {
				throw new IndicadorNotFoundException(
						"Indicador calculado no encontrado: " + indicador.getNombre() + ", periodo: " + periodo);
			}
		}

	}

	public void add(IndicadorCalculado indicadorCalculado) {
		try (Jedis jedis = pool.getResource()) {
			jedis.set("usuarioId=" + indicadorCalculado.getId().toString() + "|nombre=" + indicadorCalculado.getNombre()
					+ "|cuentas=" + indicadorCalculado.getCuentas() + "|periodo=" + indicadorCalculado.getPeriodo()
					+ "|empresaId=" + indicadorCalculado.getEmpresa().getId().toString(),
					indicadorCalculado.getValor().toString());
		}
	}
}
