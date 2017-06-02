package utils;

import java.util.ArrayList;
import java.util.List;

import dtos.CargaDeCuentasDTO;
import dtos.DTO;
import dtos.IndicadoresDTO;
import model.Indicador;
import model.repositories.RepositorioCarpeta;
import providers.FileProvider;
import providers.IProviderCuenta;
import providers.IProviderIndicador;

public class AppData {
	private RepositorioCarpeta repositorio = RepositorioCarpeta.getInstance();
	private ArrayList<IProviderCuenta> providersCuenta = new ArrayList<>();
	private ArrayList<IProviderIndicador> providersIndicador = new ArrayList<>();
	private static AppData instance;
	private CargaDeCuentasDTO inicializacionDeCuentas = new CargaDeCuentasDTO();
	private IndicadoresDTO inicializacionDeIndicadores = new IndicadoresDTO();

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

	public void cargarCuentas(CargaDeCuentasDTO datosDeCarga) {
		providersCuenta.forEach(proveedor -> repositorio.agregarCuentas(proveedor.getInformationCuentas(datosDeCarga)));
	}

	public void cargarIndicadores(IndicadoresDTO datosDeCarga) {
		providersIndicador
				.forEach(proveedor -> repositorio.agregarIndicadores(proveedor.getInformationIndicador(datosDeCarga)));
	}

	public void inicializarRepositorios() {
		inicializarCuentas();
		inicializarIndicadores();
	}

	private void inicializarCuentas() {
		providersCuenta
				.forEach(proveedor -> repositorio.addCuentas(proveedor.getInformationCuentas(inicializacionDeCuentas)));
	}

	private void inicializarIndicadores() {
		providersIndicador.forEach(proveedor -> repositorio
				.addIndicadores(proveedor.getInformationIndicador(inicializacionDeIndicadores)));
	}

	public <T> void guardar(List<T> objetos, DTO dto) {
		Archivo.archivarObjetos(objetos, dto.getPathFile());
	}

	public void guardarIndicador(String formula, String nombre) {
		Indicador indicador = new Indicador(nombre, formula);
		repositorio.agregarIndicador(indicador);
	}

	public void setInicializacionDeCuentas(CargaDeCuentasDTO _inicializacionDeCuentas) {
		inicializacionDeCuentas = _inicializacionDeCuentas;
	}

	public void setInicializacionDeIndicadores(IndicadoresDTO _inicializacionDeIndicadores) {
		inicializacionDeIndicadores = _inicializacionDeIndicadores;
	}
}
