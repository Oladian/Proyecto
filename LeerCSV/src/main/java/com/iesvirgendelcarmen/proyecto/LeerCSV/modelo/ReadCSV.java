package com.iesvirgendelcarmen.proyecto.LeerCSV.modelo;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;


public class ReadCSV {
	private static List<CochesDTO> carList = new ArrayList<>();
	
	public List<CochesDTO> getCarListFromCSV(String csvFilePath) {
		try(Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));) {
				// Para construir el csvReaderBuilder se necesitará el CsvParserBuilder, ya que este csv usa como delimitador el
				// caracter ; y no la coma (usada por defecto).
				
				CSVParser parser = new CSVParserBuilder().withSeparator(',').withIgnoreQuotations(true).build();
				
				// CsvReaderBuilder se encargará de la lectura del fichero, se obvian las dos primeras lineas y se parsea con
				// el delimitador , designado anteriormente
				
				CSVReader csvReader = new CSVReaderBuilder(reader).withSkipLines(1).withCSVParser(parser).build();
				String nextRecord[];
				while((nextRecord=csvReader.readNext())!=null) {
					carList.add(new CochesDTO(nextRecord[0], nextRecord[1], nextRecord[2], nextRecord[3], nextRecord[4]));
				}
			} catch (IOException e) {
				System.out.println("IO Exception");		
				} catch (ExcepcionDTO e) {
				e.printStackTrace();
			}
			return carList;
	}
}