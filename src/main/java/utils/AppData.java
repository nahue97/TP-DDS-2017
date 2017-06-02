package utils;

import java.util.ArrayList;

import dtos.CargaDeCuentasDTO;
import dtos.DTO;
import dtos.IndicadoresDTO;
import model.repositories.RepositorioCarpeta;
import providers.FileProvider;
import providers.IProviderCuenta;
import providers.IProviderIndicador;

public class AppData {
	private RepositorioCarpeta repositorio = RepositorioCarpeta.getInstance();
	private ArrayList<IProviderCuenta> providersCuenta;
	private ArrayList<IProviderIndicador> providersIndicador;
	private static AppData instance;
	
	private AppData(){
		providersCuenta = new ArrayList<>();
		providersCuenta.add(new FileProvider());
		
		providersIndicador = new ArrayList<>();
		providersIndicador.add(new FileProvider());
	}
	
	public static synchronized AppData getInstance(){
		if (instance == null){
			instance = new AppData();
		}
		return instance;
	}
	
	public void cargarCuentas(CargaDeCuentasDTO datosDeCarga){
		providersCuenta.forEach(proveedor ->
			repositorio.agregarCuentas(proveedor.getInformationCuentas(datosDeCarga)));	
	}
	
	public void cargarIndicadores(IndicadoresDTO datosDeCarga){
		providersIndicador.forEach(proveedor ->
		repositorio.agregarIndicadores(proveedor.getInformationIndicador(datosDeCarga)));
	}
	
	public void guardar(Object objeto, DTO dto){
		Archivo.archivarObjeto(objeto, dto.getPathFile());
	}
	
	public void guardarIndicadores(String formula, String nombre) {
		//Generar el indicador
		//Añadirlo al repositorio
		//Añadirlo al archivo
		//this.guardar(objeto, dto);
	}
}
