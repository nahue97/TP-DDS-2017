package utils;

import java.util.ArrayList;
import java.util.List;

import dtos.CargaDeArchivoTxtJsonDTO;
import dtos.DTO;
import model.Indicador;
import model.repositories.RepositorioCuentas;
import model.repositories.RepositorioIndicadores;
import providers.FileProvider;
import providers.IProviderCuenta;
import providers.IProviderIndicador;

public class AppData {

	private ArrayList<IProviderCuenta> providersCuenta = new ArrayList<>();
	private ArrayList<IProviderIndicador> providersIndicador = new ArrayList<>();
	private static AppData instance;
	private CargaDeArchivoTxtJsonDTO inicializacionDeCuentas = new CargaDeArchivoTxtJsonDTO();
	private CargaDeArchivoTxtJsonDTO inicializacionDeIndicadores = new CargaDeArchivoTxtJsonDTO();

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

	public void cargarCuentas(CargaDeArchivoTxtJsonDTO datosDeCarga) {
		providersCuenta.forEach(proveedor -> RepositorioCuentas.getInstance()
				.agregarCuentas(proveedor.getInformationCuentas(datosDeCarga)));
	}

	public void cargarIndicadores(CargaDeArchivoTxtJsonDTO datosDeCarga) {
		providersIndicador.forEach(proveedor -> RepositorioIndicadores.getInstance()
				.agregarIndicadores(proveedor.getInformationIndicador(datosDeCarga)));
	}

	public void inicializarRepositorios() {
		inicializarCuentas();
		inicializarIndicadores();
	}

	private void inicializarCuentas() {
		providersCuenta.forEach(proveedor -> RepositorioCuentas.getInstance()
				.agregarCuentas(proveedor.getInformationCuentas(inicializacionDeCuentas)));
	}

	private void inicializarIndicadores() {
		providersIndicador.forEach(proveedor -> RepositorioIndicadores.getInstance()
				.agregarIndicadores(proveedor.getInformationIndicador(inicializacionDeIndicadores)));
	}

	public <T> void guardar(List<T> objetos, DTO dto) {
		Archivo.archivarObjetos(objetos, dto.getPathFile());
	}

	public void guardarIndicador(String formula, String nombre) {
		Indicador indicador = new Indicador(nombre, formula);
		RepositorioIndicadores.getInstance().agregarIndicador(indicador);
	}

	public void setInicializacionDeCuentas(CargaDeArchivoTxtJsonDTO _inicializacionDeCuentas) {
		inicializacionDeCuentas = _inicializacionDeCuentas;
	}

	public void setInicializacionDeIndicadores(CargaDeArchivoTxtJsonDTO _inicializacionDeIndicadores) {
		inicializacionDeIndicadores = _inicializacionDeIndicadores;
	}
}
