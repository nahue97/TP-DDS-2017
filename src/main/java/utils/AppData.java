package utils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import dtos.PathFile;
import model.Criterio;
import model.Indicador;
import model.Metodologia;
import model.Regla;
import model.ReglaComparativa;
import model.ReglaTaxativa;
import model.repositories.RepositorioCuentas;
import model.repositories.RepositorioMetodologias;
import model.repositories.RepositorioIndicadores;
import providers.FileProvider;
import providers.IProviderCuenta;
import providers.IProviderIndicador;

public class AppData {

	private ArrayList<IProviderCuenta> providersCuenta = new ArrayList<>();
	private ArrayList<IProviderIndicador> providersIndicador = new ArrayList<>();
	private static AppData instance;
	private PathFile inicializacionDeCuentas;
	private PathFile inicializacionDeIndicadores;

	private AppData() {
		providersCuenta.add(new FileProvider());
		providersIndicador.add(new FileProvider());
	}

	public static synchronized AppData getInstance() {
		if (instance == null) {
			instance = new AppData();
		}
		return instance;
	}
	
	public static void limpiar() {
		instance = null;
	}

	public void cargarCuentas(PathFile datosDeCarga) {
		providersCuenta.forEach(proveedor -> RepositorioCuentas.getInstance()
				.agregarCuentas(proveedor.getInformationCuentas(datosDeCarga)));
	}

	public void cargarIndicadores(PathFile datosDeCarga) {
		providersIndicador.forEach(proveedor -> RepositorioIndicadores.getInstance()
				.agregarIndicadores(proveedor.getInformationIndicador(datosDeCarga)));
	}

	public void inicializarRepositorios() {
		inicializarCuentas();
		inicializarIndicadores();
		if (RepositorioIndicadores.getInstance().getIndicadores().size() > 7) {
			inicializarMetodologias();			
		}
	}

	private void inicializarCuentas() {
		providersCuenta.forEach(proveedor -> RepositorioCuentas.getInstance()
				.agregarCuentas(proveedor.getInformationCuentas(inicializacionDeCuentas)));
	}

	private void inicializarIndicadores() {
		providersIndicador.forEach(proveedor -> RepositorioIndicadores.getInstance()
				.agregarIndicadores(proveedor.getInformationIndicador(inicializacionDeIndicadores)));
	}
	
	private void inicializarMetodologias() {
		ReglaComparativa regla1 = new ReglaComparativa("Regla1", RepositorioIndicadores.getInstance().getIndicadores().get(0), Criterio.MAYOR);
		ReglaComparativa regla2 = new ReglaComparativa("Regla2", RepositorioIndicadores.getInstance().getIndicadores().get(1), Criterio.MAYOR);
		ReglaComparativa regla3 = new ReglaComparativa("Regla3", RepositorioIndicadores.getInstance().getIndicadores().get(2), Criterio.MAYOR);
		ReglaComparativa regla4 = new ReglaComparativa("Regla4", RepositorioIndicadores.getInstance().getIndicadores().get(3), Criterio.MAYOR);
		
		ReglaTaxativa regla5 = new ReglaTaxativa("Regla5", RepositorioIndicadores.getInstance().getIndicadores().get(4), '>', new BigDecimal(170));
		ReglaTaxativa regla6 = new ReglaTaxativa("Regla6", RepositorioIndicadores.getInstance().getIndicadores().get(5), '<', new BigDecimal(2900));
		ReglaTaxativa regla7 = new ReglaTaxativa("Regla7", RepositorioIndicadores.getInstance().getIndicadores().get(6), '>', new BigDecimal(260));
		ReglaTaxativa regla8 = new ReglaTaxativa("Regla8", RepositorioIndicadores.getInstance().getIndicadores().get(7), '>', new BigDecimal(10));
		
		List<Regla> reglas1 = new ArrayList<>();
		reglas1.add(regla1);
		reglas1.add(regla2);
		reglas1.add(regla3);
		reglas1.add(regla4);
		
		List<Regla> reglas2 = new ArrayList<>();
		reglas2.add(regla5);
		reglas2.add(regla6);
		reglas2.add(regla7);
		reglas2.add(regla8);
		
		List<Regla> reglas3 = new ArrayList<>();
		reglas3.add(regla1);
		reglas3.add(regla2);
		reglas3.add(regla3);
		reglas3.add(regla4);
		reglas3.add(regla5);
		reglas3.add(regla6);
		reglas3.add(regla7);
		reglas3.add(regla8);
		
		Metodologia metodologia1 = new Metodologia("Metodologia Berg", reglas1);
		Metodologia metodologia2 = new Metodologia("Metodologia Gonzalez Querzola", reglas2);
		Metodologia metodologia3 = new Metodologia("Metodologia Mazzeo", reglas3);
		
		RepositorioMetodologias.getInstance().agregarMetodologia(metodologia1);
		RepositorioMetodologias.getInstance().agregarMetodologia(metodologia2);
		RepositorioMetodologias.getInstance().agregarMetodologia(metodologia3);
	}

	public <T> void guardar(List<T> objetos, PathFile dto) {
		Archivo.archivarObjetos(objetos, dto.getPathFile());
	}

	public void guardarIndicador(String formula, String nombre) {
		Indicador indicador = new Indicador(nombre, formula);
		RepositorioIndicadores.getInstance().agregarIndicador(indicador);
	}

	public void setInicializacionDeCuentas(PathFile _inicializacionDeCuentas) {
		inicializacionDeCuentas = _inicializacionDeCuentas;
	}

	public void setInicializacionDeIndicadores(PathFile _inicializacionDeIndicadores) {
		inicializacionDeIndicadores = _inicializacionDeIndicadores;
	}
}
