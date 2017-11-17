package batchProcesses;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.io.File;
import java.text.SimpleDateFormat;

import useCases.CuentasUseCases;

public class FileUpload extends TimerTask {

	final static String rutaYNombre = "./WatchFolder Carga/ArchivoDeCarga.txt";
	final static String rutaProcesado = "./WatchFolder Carga/Procesados/";
	
	public void run() {
		
		File fileCarga = new File(rutaYNombre);
		if(fileCarga.exists()) {
			System.out.println("Archivo cargado");
			CuentasUseCases.cargarArchivoDeCuentas(rutaYNombre);		 
			fileCarga.renameTo(new File(rutaProcesado + getCurrentDate("yyyyMMddHHmmss") + "ArchivoCargado.txt"));		
		}	
	}

	public void init() {
		Timer timer = new Timer();

		timer.scheduleAtFixedRate(this, 0, 10*1000);

	}
	
	public static String getCurrentDate(String format) {
	    String dtStr = "";
	    SimpleDateFormat sdf = new SimpleDateFormat(format);
	    Date dt1 = new Date();

	    dtStr = sdf.format(dt1);

	    return dtStr;
	}
}