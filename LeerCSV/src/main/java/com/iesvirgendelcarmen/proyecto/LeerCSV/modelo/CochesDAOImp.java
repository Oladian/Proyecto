package com.iesvirgendelcarmen.proyecto.LeerCSV.modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CochesDAOImp implements CochesDAO {
	Connection conexion = Conexion.getConexion();
	private String[] cabeceras = {"Matricula", "Marca", "Color", "Modelo", "Origen"};
	private Object[][] datos;
	private CrearLog log = new CrearLog();

	@Override
	public boolean crearBaseDatos() {
		String sql = "DROP TABLE IF EXISTS coches;"
				+ "CREATE TABLE coches ("
				+ "matricula TEXT PRIMARY KEY,"
		        + "coche TEXT,"
		        + "color TEXT,"
		        + "modelo TEXT,"
		        + "origen TEXT "
		        + ");";
		
		try {
			Statement st = conexion.createStatement();
			st.executeUpdate(sql);
			return true;
		} catch (SQLException e1) {
			log.crearLog("Error creando base de datos. Base de datos ya existe.", null);
			return false;
		}
	}
	
	@Override
	public void completarArrays(List<CochesDTO> lista) {
		datos = new Object[lista.size()][5];
		int contador = 0;
		
		for (CochesDTO coche : listarCoches()) {
			datos[contador][0] = coche.getMatricula();
			datos[contador][1] = coche.getMarca();
			datos[contador][2] = coche.getColor();
			datos[contador][3] = coche.getModelo();
			datos[contador][4] = coche.getOrigen();
			contador++;
		}
	}
	
	// Devuelve la lista de coches según una matrícula
	
	@Override
	public CochesDTO listaFromMatricula(String matricula){
//		List<CochesDTO> lista = new ArrayList<>();
		String sql = "SELECT * FROM coches WHERE matricula = ?;";
		
		try(PreparedStatement statement = conexion.prepareStatement(sql);
				) {
			statement.setString(1,matricula);
			statement.executeQuery();
			ResultSet result = statement.executeQuery();
			CochesDTO coche = new CochesDTO(result.getString(1), result.getString(2), result.getString(3), result.getString(4), result.getString(5));
		
			return coche;
		} catch (SQLException e) {
			return null;
		} catch (ExcepcionDTO e) {
			return null;
		}
	}
	
	// Devuelve la lista de coches de la base de datos
	
	@Override
	public List<CochesDTO> listarCoches() {
		List<CochesDTO> listaDeCoches = new ArrayList<>();
		String sql = "SELECT * FROM coches;";
		CochesDTO coche;
		
		try(PreparedStatement statement = conexion.prepareStatement(sql);
				ResultSet result = statement.executeQuery();) {
			while(result.next()) {
				try {
					coche = new CochesDTO(result.getString(1), result.getString(2), result.getString(3), result.getString(4), result.getString(5));
					listaDeCoches.add(coche);
				} catch (ExcepcionDTO e) {
					log.crearLog("Error de lectura de la base de datos.",result.getString(1));
				}
			}
		} catch (SQLException e) {
			
		}
		return listaDeCoches;
	}

	// Borra un solo coche
	
	@Override
	public boolean borrarCoche(CochesDTO coche) {
		String sql = "DELETE FROM coches WHERE matricula=?;";
		try(PreparedStatement preparedStatement = conexion.prepareStatement(sql);) {
			preparedStatement.setString(1,coche.getMatricula());
			return !preparedStatement.execute();
		} catch (SQLException e) {
			log.crearLog("Error de borrado en la base de datos.",coche.getMatricula());
			return !false;
		} 
		
	}
	
	// Borra una lista de coches
	
	@Override
	public boolean borrarListaCoches(List<CochesDTO> lista) {
		try {
			conexion.setAutoCommit(true);
			for (CochesDTO cochesDTO : lista) {
				borrarCoche(cochesDTO);
			}
			return true;
		} catch (SQLException e1) {
			try {
				conexion.rollback();
				log.crearLog("Se ha ejecutado un rollback al no poder realizar el borrado de datos.",null);
				return false;
			} catch (SQLException e) {
				log.crearLog("Error al ejecutar rollback.",null);
				return false;
			}
		}
	}

	// Actualiza un solo coche
	
	@Override
	public boolean actualizarCoche(CochesDTO coche) {
		String sql="UPDATE coches SET coche=?, color=?, modelo=?, origen=? WHERE matricula=?;";
		try(PreparedStatement preparedStatement = conexion.prepareStatement(sql);) {
			preparedStatement.setString(1,coche.getMarca());
			preparedStatement.setString(2,coche.getColor());
			preparedStatement.setString(3, coche.getModelo());
			preparedStatement.setString(4, coche.getOrigen());
			preparedStatement.setString(5,coche.getMatricula());
			return preparedStatement.execute();
		} catch (SQLException e) {
			log.crearLog("Error de actualizado de la base de datos.",coche.getMatricula());
			return false;
		}
	}
	
	// Actualiza una lista de coches
	
	@Override
	public boolean actualizarListaCoches(List<CochesDTO> lista) {
		try {
			conexion.setAutoCommit(true);
			for (CochesDTO cochesDTO : lista) {
				actualizarCoche(cochesDTO);
			}
			return true;
		} catch (SQLException e1) {
			try {
				conexion.rollback();
				log.crearLog("Se ha ejecutado un rollback al no poder realizar la actualización de datos.",null);
				return false;
			} catch (SQLException e) {
				log.crearLog("Error al ejecutar rollback.",null);
				return false;
			}
		}
	}

	// Inserta un solo coche
	
	@Override
	public boolean insertarCoche(CochesDTO coche) {
		String sql = "INSERT INTO coches (matricula, coche, color, modelo, origen) VALUES (?,?,?,?,?);";
		try (PreparedStatement statement = conexion.prepareStatement(sql);){
			statement.setString(1, coche.getMatricula());
			statement.setString(2, coche.getMarca());
			statement.setString(3, coche.getColor());
			statement.setString(4, coche.getModelo());
			statement.setString(5, coche.getOrigen());
			return statement.execute();
		} catch (SQLException e) {
			log.crearLog("Error de inserción en la base de datos.",null);
		}
		return false;
	}
	
	// Inserta una lista de coches
	
	@Override
	public boolean insertarListaCoches(List<CochesDTO> lista) {
		try {
			conexion.setAutoCommit(true);
			for (CochesDTO cochesDTO : lista) {
				insertarCoche(cochesDTO);
				System.out.println(cochesDTO);
			}
			return true;
		} catch (SQLException e1) {
			try {
				conexion.rollback();
				log.crearLog("Se ha ejecutado un rollback al no poder realizar la inserción de datos.",null);
				return false;
			} catch (SQLException e) {
				log.crearLog("Error al ejecutar rollback.",null);
				return false;
			}
		} 
	}
	
	@Override
	public void hacerCommit() {
		try {
			conexion.setAutoCommit(false);
			conexion.commit();
		} catch (SQLException e) {
			log.crearLog("No se ha podido realizar el commit.",null);
		}
	}

	public String[] getCabeceras() {
		return cabeceras;
	}

	public void setCabeceras(String[] cabeceras) {
		this.cabeceras = cabeceras;
	}

	public Object[][] getDatos() {
		return datos;
	}

	public void setDatos(Object[][] datos) {
		this.datos = datos;
	}
}
