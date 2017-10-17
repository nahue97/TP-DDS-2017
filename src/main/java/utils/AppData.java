package utils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import dtos.PathFile;
import model.Criterio;
import model.Empresa;
import model.Indicador;
import model.Metodologia;
import model.Regla;
import model.ReglaComparativa;
import model.ReglaTaxativa;
import model.repositories.RepositorioCuentas;
import model.repositories.RepositorioEmpresas;
import model.repositories.RepositorioMetodologias;
import model.repositories.RepositorioIndicadores;
import providers.*;

public class AppData {

	private ArrayList<IProviderEmpresa> providersEmpresa = new ArrayList<>();
	private ArrayList<IProviderCuenta> providersCuenta = new ArrayList<>();
	private ArrayList<IProviderIndicador> providersIndicador = new ArrayList<>();
	private static AppData instance;
	private PathFile inicializacionDeEmpresas;
	private PathFile inicializacionDeCuentas;
	private PathFile inicializacionDeIndicadores;

	private AppData() {
		providersEmpresa.add(new FileProvider());
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
	
	public void cargarEmpresas(PathFile datosDeCarga) {
		providersEmpresa.forEach(proveedor -> RepositorioEmpresas.getInstance()
				.agregarEmpresas(proveedor.getInformationEmpresas(datosDeCarga)));
	}
	
	public void cargarEmpresasDeCuentas(PathFile datosDeCarga) {
		List<Empresa> empresas = new ArrayList<>();
		providersCuenta.forEach(proveedor -> proveedor.getInformationCuentas(datosDeCarga)
						.forEach(c -> empresas.add(c.getEmpresa())));
		RepositorioEmpresas.getInstance().agregarEmpresas(empresas);
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
		if (RepositorioEmpresas.getInstance().getAll().isEmpty())
			inicializarEmpresas();
		if (RepositorioCuentas.getInstance().getAll().isEmpty())
			inicializarCuentas();
		if (RepositorioIndicadores.getInstance().getAll().isEmpty())
			inicializarIndicadores();
		if (RepositorioIndicadores.getInstance().getAll().size() > 7)
			inicializarMetodologias();			
	}
	
	private void inicializarEmpresas() {
		providersEmpresa.forEach(proveedor -> RepositorioEmpresas.getInstance()
				.agregarEmpresas(proveedor.getInformationEmpresas(inicializacionDeEmpresas)));
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
		ReglaComparativa regla1 = new ReglaComparativa("Regla1", RepositorioIndicadores.getInstance().getAll().get(0), Criterio.MAYOR);
		ReglaComparativa regla2 = new ReglaComparativa("Regla2", RepositorioIndicadores.getInstance().getAll().get(1), Criterio.MAYOR);
		ReglaComparativa regla3 = new ReglaComparativa("Regla3", RepositorioIndicadores.getInstance().getAll().get(2), Criterio.MAYOR);
		ReglaComparativa regla4 = new ReglaComparativa("Regla4", RepositorioIndicadores.getInstance().getAll().get(3), Criterio.MAYOR);
		
		ReglaTaxativa regla5 = new ReglaTaxativa("Regla5", RepositorioIndicadores.getInstance().getAll().get(4), '>', new BigDecimal(170));
		ReglaTaxativa regla6 = new ReglaTaxativa("Regla6", RepositorioIndicadores.getInstance().getAll().get(5), '<', new BigDecimal(2900));
		ReglaTaxativa regla7 = new ReglaTaxativa("Regla7", RepositorioIndicadores.getInstance().getAll().get(6), '>', new BigDecimal(260));
		ReglaTaxativa regla8 = new ReglaTaxativa("Regla8", RepositorioIndicadores.getInstance().getAll().get(7), '>', new BigDecimal(10));
		
		ReglaComparativa regla9 = new ReglaComparativa("Regla9", RepositorioIndicadores.getInstance().getAll().get(2), Criterio.MAYOR);
		ReglaComparativa regla10 = new ReglaComparativa("Regla10", RepositorioIndicadores.getInstance().getAll().get(3), Criterio.MAYOR);
		ReglaTaxativa regla11 = new ReglaTaxativa("Regla11", RepositorioIndicadores.getInstance().getAll().get(4), '>', new BigDecimal(170));
		ReglaTaxativa regla12 = new ReglaTaxativa("Regla12", RepositorioIndicadores.getInstance().getAll().get(5), '<', new BigDecimal(2900));
		
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
		reglas3.add(regla9);
		reglas3.add(regla10);
		reglas3.add(regla11);
		reglas3.add(regla12);
		
		Metodologia metodologia1 = new Metodologia("Metodologia Berg", reglas1);
		
		Metodologia metodologia2 = new Metodologia("Metodologia Gonzalez Querzola", reglas2);
		
		Metodologia metodologia3 = new Metodologia("Metodologia Mazzeo", reglas3);
		
		RepositorioMetodologias.getInstance().add(metodologia1);
		RepositorioMetodologias.getInstance().add(metodologia2);
		RepositorioMetodologias.getInstance().add(metodologia3);
	}

	public <T> void guardar(List<T> objetos, PathFile dto) {
		Archivo.archivarObjetos(objetos, dto.getPathFile());
	}

	public void guardarIndicador(String formula, String nombre) {
		Indicador indicador = new Indicador(nombre, formula);
		RepositorioIndicadores.getInstance().add(indicador);
	}

	public void setInicializacionDeCuentas(PathFile _inicializacionDeCuentas) {
		inicializacionDeCuentas = _inicializacionDeCuentas;
	}
	
	public void setInicializacionDeEmpresas(PathFile _inicializacionDeEmpresas) {
		inicializacionDeEmpresas = _inicializacionDeEmpresas;
	}

	public void setInicializacionDeIndicadores(PathFile _inicializacionDeIndicadores) {
		inicializacionDeIndicadores = _inicializacionDeIndicadores;
	}

}