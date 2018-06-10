package com.iesvirgendelcarmen.proyecto.LeerCSV.modelo;

import java.util.List;

public class TestConexiones {
	public static void main(String[] args) throws ExcepcionDTO {
		ReadCSV csv = new ReadCSV();
		List<CochesDTO> listaCoches = csv.getCarListFromCSV("ficherosCSV/MOCK_DATA.csv");
		CochesDAOImp imp = new CochesDAOImp();
		System.out.println("Base de datos: " + imp.crearBaseDatos());
		System.out.println(listaCoches);
		System.out.println(imp.insertarListaCoches(listaCoches));
		System.out.println(imp.listarCoches());
	}
}
