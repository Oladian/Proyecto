package com.iesvirgendelcarmen.proyecto.LeerCSV.modelo;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.time.LocalDateTime;

public class CrearLog {

	private static File file = new File("logs/logProyecto.txt");

	// Método que escribe los logs en el fichero proyecto.log
	public void crearLog(String log, String primaryKey){

		try(PrintWriter output = new PrintWriter(new BufferedOutputStream(new FileOutputStream(file,true)));) {
			output.println(LocalDateTime.now()+" | "+ primaryKey +" | "+log+"\n");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
