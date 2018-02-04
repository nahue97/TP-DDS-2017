package model.repositories;

import java.math.BigDecimal;
import java.util.List;

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

public class RepositorioIndicadoresCalculados extends Repositorio<IndicadorCalculado> {

	// Singleton
	private static RepositorioIndicadoresCalculados instance;

	public RepositorioIndicadoresCalculados() {
		super();
	}

	public static synchronized RepositorioIndicadoresCalculados getInstance() {
		if (instance == null)
			instance = new RepositorioIndicadoresCalculados();
		return instance;
	}

	@Override
	protected Class<IndicadorCalculado> getEntityType() {
		return IndicadorCalculado.class;
	}

	@Override
	protected void addCriteriaToSearchByExample(Criteria criteria, IndicadorCalculado indicadorCalculado) {
		// Buscamos el/los indicador/es calculados que coincidan
		if (indicadorCalculado.getId() != null) {
			criteria.add(Restrictions.eq("id", indicadorCalculado.getId()));
		}
		if (indicadorCalculado.getNombre() != null) {
			criteria.add(Restrictions.eq("nombre", indicadorCalculado.getNombre()));
		}
		if (indicadorCalculado.getFormula() != null) {
			criteria.add(Restrictions.eq("formula", indicadorCalculado.getFormula()));
		}
		if (indicadorCalculado.getUsuario() != null) {
			criteria.add(Restrictions.eq("usuario", indicadorCalculado.getUsuario()));
		}
		if (indicadorCalculado.getPeriodo() != null) {
			criteria.add(Restrictions.eq("id", indicadorCalculado.getPeriodo()));
		}
		if (indicadorCalculado.getEmpresa() != null) {
			criteria.add(Restrictions.eq("empresa", indicadorCalculado.getEmpresa()));
		}
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

	@Override
	protected List<IndicadorCalculado> getAllFromUserId(Long id) {
		Session session = sessionFactory.openSession();
		try {
			IndicadorCalculado indicadorCalculado = new IndicadorCalculado();
			Usuario user = new Usuario(null, null);
			user.setId(id);
			indicadorCalculado.setUsuario(user);
			Criteria criteria = session.createCriteria(this.getEntityType());
			this.addCriteriaToSearchByExample(criteria, indicadorCalculado);
			return criteria.list();
		} catch (HibernateException e) {
			throw new RuntimeException(e);
		} finally {
			session.close();
		}
	}

	public BigDecimal obtenerValorDeIndicador(Indicador indicador, Empresa empresaParaCalcular, String periodo) {
		Session session = sessionFactory.openSession();
		try {
			Criteria criteria = session.createCriteria(this.getEntityType());
			criteria.add(Restrictions.eq("nombre", indicador.getNombre()));
			criteria.add(Restrictions.eq("formula", indicador.getFormula()));
			criteria.add(Restrictions.eq("empresa", empresaParaCalcular));
			criteria.add(Restrictions.eq("periodo", periodo));
			List<IndicadorCalculado> indicadoresCalculadosResultado = criteria.list();
			if (!indicadoresCalculadosResultado.isEmpty()){
				return indicadoresCalculadosResultado.get(0).getValor();
			} else {
				throw new IndicadorNotFoundException("Indicador calculado no encontrado: " + indicador.getNombre() + ", periodo: " + periodo);
			}
		} catch (HibernateException e) {
			throw new RuntimeException(e);
		} finally {
			session.close();
		}
	}
}
