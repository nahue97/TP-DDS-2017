package batchProcesses;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.io.File;
import java.text.SimpleDateFormat;

import useCases.CuentasUseCases;

public class FileUpload extends TimerTask {

	final static String rutaCarga = "./WatchFolder Carga/";
	final static String rutaProcesado = "./WatchFolder Carga/Procesados/";

	public void run() {

		File folderCarga = new File(rutaCarga);
		File[] listOfFiles = folderCarga.listFiles();

		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {				
				CuentasUseCases.cargarArchivoDeCuentas(rutaCarga + listOfFiles[i].getName());
				listOfFiles[i].renameTo(
						new File(rutaProcesado + getCurrentDate("yyyyMMddHHmmsss") + listOfFiles[i].getName()));
				System.out.println("Archivo cargado");
			}
		}
	}

	public void init() {
		Timer timer = new Timer();

		timer.scheduleAtFixedRate(this, 0, 10 * 1000);

	}

	public static String getCurrentDate(String format) {
		String dtStr = "";
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Date dt1 = new Date();

		dtStr = sdf.format(dt1);

		return dtStr;
	}
}