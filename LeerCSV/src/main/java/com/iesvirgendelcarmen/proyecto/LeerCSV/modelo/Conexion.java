package com.iesvirgendelcarmen.proyecto.LeerCSV.modelo;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Properties;
import org.sqlite.SQLiteConfig;

public class Conexion {

	private static Connection conexion = null;
	private Conexion(){}
	
	public static Connection getConexion() {
		if (conexion == null) {
			try {
				Properties p = new Properties();
				p.load(new FileReader("PropiedadesSQLite/BD.properties"));

				final String DB_URL = p.getProperty("DB_URL");
				final String DRIVER = p.getProperty("DRIVER");
				final String BD = p.getProperty("BD");

				Class.forName(DRIVER);
				
				SQLiteConfig config = new SQLiteConfig();
				config.enforceForeignKeys(true);
				conexion = DriverManager.getConnection(DB_URL+BD,config.toProperties());
//				conexion.setAutoCommit(false);

			} catch ( ClassNotFoundException | SQLException e ) {
				e.printStackTrace ();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			Runtime.getRuntime().addShutdownHook(new MiShutdownHuk());
		}
		return conexion;
	}

	public static void desconectar () {
		if ( conexion != null )
			try {
				conexion.close();
			} catch ( SQLException e ) {
				e.printStackTrace();
			}
		
	}
	
	static class MiShutdownHuk extends Thread {
		@Override
		public void run(){
			Connection con = Conexion.getConexion();
			if (con!=null) {
				try {
					con.close();
					System.out.println("Desconectado "+LocalDateTime.now().getHour()+":"+
							LocalDateTime.now().getMinute()+":"+
							LocalDateTime.now().getSecond());
				} catch ( SQLException e ) {
					e.printStackTrace () ;
				}
			}
		}
	}
}