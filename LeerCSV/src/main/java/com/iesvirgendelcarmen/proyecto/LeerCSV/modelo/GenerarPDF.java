package com.iesvirgendelcarmen.proyecto.LeerCSV.modelo;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.JTable;
import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class GenerarPDF{
	public void print(JTable tabla) {
		try {
			Document doc = new Document();
            PdfWriter.getInstance(doc, new FileOutputStream("tabla.pdf"));
            doc.open();
            PdfPTable pdfTabla = new PdfPTable(tabla.getColumnCount());

            // For que añade las cabeceras a la tabla del pdf
            for (int i = 0; i < tabla.getColumnCount(); i++) {
                pdfTabla.addCell(tabla.getColumnName(i));
            }
            
            // Extrae los datos desde la JTable y los inserta en la tabla del pdf
            for (int rows = 0; rows < tabla.getRowCount(); rows++) {
                for (int cols = 0; cols < tabla.getColumnCount(); cols++) {
                    pdfTabla.addCell(tabla.getModel().getValueAt(rows, cols).toString());
                }
            }
            doc.add(pdfTabla);
            doc.close();
            getPdf();
		} catch (Exception e) {
			new CrearLog().crearLog("Fallo al generar el PDF. Posible causa: Fichero en uso por el Sistema.",null);
		}
	}
	
	// Método que ejecutará el explorador por defecto para la lectura del pdf recién creado
	
	private void getPdf() {
		File pdf = new File("tabla.pdf");
		if(pdf.exists()) {
			ProcessBuilder processBuilderWindows = new ProcessBuilder("cmd.exe", "/C", "explorer tabla.pdf");
			ProcessBuilder processBuilderLinux = new ProcessBuilder("/bin/bash", "-c", "sensible-browser tabla.pdf");
			try {
				if(System.getProperty("os.name").contains("Windows"))
					processBuilderWindows.start();
				else 			
					processBuilderLinux.start();
			} catch (IOException e) {
				new CrearLog().crearLog("Error al ejecutar el comando de lectura del pdf", null);
				
			}
		}
	}
}
