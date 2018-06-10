package com.iesvirgendelcarmen.proyecto.LeerCSV.modelo;

import java.awt.EventQueue;
//import java.util.List;

import com.iesvirgendelcarmen.proyecto.LeerCSV.controlador.ControladorCSV;
import com.iesvirgendelcarmen.proyecto.LeerCSV.vista.VistaCSV;

public class PrincipalCSV {
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new ControladorCSV(new VistaCSV());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
