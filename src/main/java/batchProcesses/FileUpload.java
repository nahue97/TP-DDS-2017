package batchProcesses;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import useCases.CuentasUseCases;

public class FileUpload extends TimerTask {

	final static String rutaYNombre = "./WatchFolder Carga/ArchivoDeCarga.txt";
	
	public void run() {
		System.out.println("Generating report");
		CuentasUseCases.cargarArchivoDeCuentas(rutaYNombre);
	}

	public void init() {
		Timer timer = new Timer();
		Calendar date = Calendar.getInstance();
		date.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		date.set(Calendar.HOUR, 0);
		date.set(Calendar.MINUTE, 0);
		date.set(Calendar.SECOND, 0);
		date.set(Calendar.MILLISECOND, 0);
		timer.scheduleAtFixedRate(this, 0, 10*1000);
		// Schedule to run every Sunday in midnight
		//timer.schedule(this, date.getTime(), 1000 * 60 * 60 * 24 * 7);
	}
}