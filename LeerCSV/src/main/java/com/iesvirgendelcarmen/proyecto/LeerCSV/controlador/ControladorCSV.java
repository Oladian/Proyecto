package com.iesvirgendelcarmen.proyecto.LeerCSV.controlador;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;

import com.iesvirgendelcarmen.proyecto.LeerCSV.modelo.CochesDAOImp;
import com.iesvirgendelcarmen.proyecto.LeerCSV.modelo.CochesDTO;
import com.iesvirgendelcarmen.proyecto.LeerCSV.modelo.CrearLog;
import com.iesvirgendelcarmen.proyecto.LeerCSV.modelo.ExcepcionDTO;
import com.iesvirgendelcarmen.proyecto.LeerCSV.modelo.GenerarPDF;
import com.iesvirgendelcarmen.proyecto.LeerCSV.modelo.ReadCSV;
import com.iesvirgendelcarmen.proyecto.LeerCSV.vista.VistaCSV;

public class ControladorCSV implements ActionListener {
	int alto;
	Dimension dimension;
	JScrollBar barra;
	String path=".";
	CrearLog log = new CrearLog();
	
	List<CochesDTO> listaCochesActualizados = new ArrayList<>();
	static List<CochesDTO> listaCochesEstatica = new ArrayList<>();
	List<CochesDTO> listaFiltrado = new ArrayList<>();
	List<CochesDTO> listaReset = new ArrayList<>();
	ReadCSV reader = new ReadCSV();
	GenerarPDF generarPdf = new GenerarPDF();
	CochesDAOImp manipular = new CochesDAOImp();
	
	private List<CochesDTO> listaCoches;
	private VistaCSV vista;
	JScrollPane scrollPane; 
	private int posicion=0;
	private Set<String> colores = new HashSet<>();
	private Set<String> marcas = new HashSet<>();
	private Set<String> origenes = new HashSet<>();
	
	private int filas = 22;
	
	public ControladorCSV(VistaCSV vista) {
		this.vista = vista;
		registerComponent();
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().getClass()==JButton.class) {
			JButton jButton = (JButton) e.getSource();
			String textoBoton = jButton.getText();
			switch (textoBoton) {
			case ">":
				posicion++;
				break;
			case ">>":
				posicion+=10;
				break;
			case "<":
				posicion--;
				break;
			case "<<":
				posicion-=10;
				break;
			case "Anadir datos":
				try {
					lanzarInputRecogerDatos();
					vista.getMntmGuardar().setEnabled(true);
				} catch (ExcepcionDTO e1) {
					dialogo("Error añadiendo datos");
				}
				break;
			case "Actualizar datos":
				try {
					dialogo("Actualizados "+vista.getTable().getSelectedRowCount()+" elementos.");
					actualizarFilas();
					vista.getMntmGuardar().setEnabled(true);
				} catch (ExcepcionDTO e1) {
					dialogo("Error actualizando datos");
			}
				break;
			case "Borrar datos":
				int resultado = JOptionPane.showConfirmDialog(null, "¿Seguro que quiere borrar esta fila?", "Borrar datos", JOptionPane.OK_CANCEL_OPTION);
				if(resultado==JOptionPane.OK_OPTION) {
					try {
						dialogo("Borrados "+vista.getTable().getSelectedRowCount()+" elementos.");
						borrarFilas();
						vista.getMntmGuardar().setEnabled(true);
						vista.getMntmGenerarPdf().setEnabled(true);
					} catch (ExcepcionDTO e1) {
						dialogo("Error borrando datos");
					}
				}
				break;
			case "Buscar":
				String comand = jButton.getActionCommand();
				if(comand.equals("BuscarTabla")) {
					Object[] textFields = {
							"Matricula", vista.getTextAnadirMatricula() };
					int result = JOptionPane.showConfirmDialog(null, textFields, "Añadir datos", JOptionPane.OK_CANCEL_OPTION);
					String busqueda = vista.getTextAnadirMatricula().getText();
					if(result==JOptionPane.OK_OPTION) {
						CochesDTO cocheTabla = manipular.listaFromMatricula(busqueda);
					
						if(cocheTabla!=null) {
							JOptionPane.showMessageDialog(null, "Marca: "+cocheTabla.getMarca()+
								"\nColor: "+cocheTabla.getColor()+
								"\nModelo: "+cocheTabla.getModelo()+
								"\nOrigen: "+cocheTabla.getOrigen());
							actualizarDatosEnTabla();
						} else
							JOptionPane.showMessageDialog(null,"No se han encontrado datos");
					}
					break;
				}
				
				listaCoches = datosCSVFiltrados(listaCochesEstatica);
				posicion=0;
				vista.getBtnBuscar().setEnabled(false);
				vista.getBtnReset().setEnabled(true);
				break;
			case "Reset":
				listaCoches = listaCochesEstatica;
				posicion = 0;
				vista.getBtnBuscar().setEnabled(true);
				vista.getBtnReset().setEnabled(false);
				break;
			case "Siguiente":
				alto = vista.getTable().getRowHeight() * (filas - 1);
				barra = scrollPane.getVerticalScrollBar();
				barra.setValue( barra.getValue() + alto );
				actualizarDatosEnTabla();
				break;
			case "Anterior":
				alto = vista.getTable().getRowHeight() * (filas-1);
				barra = scrollPane.getVerticalScrollBar();
				barra.setValue( barra.getValue() - alto );
				break;
			default:
				break;
			}
			if (listaCoches.size()>=1) {
				posicion%=listaCoches.size();
				if(posicion<0)
					posicion+=listaCoches.size();
				colocarFormularioCoche(posicion);
			} else {
				dialogo("No existen coches con esos parámetros.");
				vista.getBtnBuscar().setEnabled(true);
				vista.getBtnReset().setEnabled(false);
			}
		}
		
		if (e.getSource().getClass() == JMenuItem.class) {
			
			if(e.getSource().getClass()==JMenuItem.class) {
				JMenuItem menuItem = (JMenuItem) e.getSource();
				String menuString = menuItem.getText();
				switch (menuString) {
				case "Salir":
					System.exit(0);
				case "About":
					//vista.messageAbout();
					break;
				case "Cargar datos":
					if (posicion>=0 && posicion<=1000 && lanzarEleccionFichero())
						colocarFormularioCoche(posicion);
					break;
				case "Guardar":
					manipular.hacerCommit();
					vista.getMntmGuardar().setEnabled(false);
					vista.getMntmGenerarPdf().setEnabled(true);
					break;
				case "Generar PDF":
					generarPdf.print(vista.getTable());
					vista.getMntmGenerarPdf().setEnabled(false);
					break;
				default:
					break;
				}
			}
		}
	}
	
	public void registerComponent() {
		
		// Botones
		vista.getButtonMayor().addActionListener(this);
		vista.getButtonMayorMayor().addActionListener(this);
		vista.getButtonMenor().addActionListener(this);
		vista.getButtonMenorMenor().addActionListener(this);
		vista.getBtnAnadirDatos().addActionListener(this);
		vista.getBtnActualizarDatos().addActionListener(this);
		vista.getBtnBorrarDatos().addActionListener(this);
		vista.getBtnBuscar().addActionListener(this);
		vista.getBtnReset().addActionListener(this);
		vista.getBtnAnterior().addActionListener(this);
		vista.getBtnSiguiente().addActionListener(this);
		vista.getBtnBuscarEnTabla().addActionListener(this);
		
		// Menús
		vista.getMntmCargarDatos().addActionListener(this);
		vista.getMntmSalir().addActionListener(this);
		vista.getMntmGuardar().addActionListener(this);
		vista.getMntmGenerarPdf().addActionListener(this);
	}
	
		// Formulario para CSV
	private void colocarFormularioCoche(int i) {
		if(listaCoches.size()>0) {
			vista.getTextMatricula().setText(listaCoches.get(i).getMatricula());
			vista.getTextMarca().setText(listaCoches.get(i).getMarca());
			vista.getTextFieldColor().setText(listaCoches.get(i).getColor());
			vista.getTextFieldModelo().setText(listaCoches.get(i).getModelo());
			vista.getTextFieldOrigen().setText(listaCoches.get(i).getOrigen());
		}
	}
	
		//Metodo que escoge el fichero
	private boolean lanzarEleccionFichero() {
		
		JFileChooser fileChooser = new JFileChooser("./ficherosCSV");
		int resultado = fileChooser.showOpenDialog(vista.getFrame());
		if(resultado==JFileChooser.APPROVE_OPTION) {
			path = fileChooser.getSelectedFile().getPath();
			if(listaCoches==null) {
				listaCochesEstatica = reader.getCarListFromCSV(path);
				listaCoches = listaCochesEstatica;
				
				if(manipular.listarCoches().size()<=0 ) {
					manipular.crearBaseDatos();
					manipular.insertarListaCoches(listaCochesEstatica);
					manipular.completarArrays(listaCochesEstatica);
				}
				
				for (CochesDTO coche : listaCoches) {
					colores.add(coche.getColor());
					marcas.add(coche.getMarca());
					origenes.add(coche.getOrigen());
					listaReset.add(coche);
				}
				
				vista.getComboBoxColor().addItem("--");
				for (String color : colores) {
					vista.getComboBoxColor().addItem(color);
				}
				
				vista.getComboBoxMarca().addItem("--");
				for (String marca : marcas) {
					vista.getComboBoxMarca().addItem(marca);
				}
				
				vista.getComboBoxOrigen().addItem("--");
				for (String origen : origenes) {
					vista.getComboBoxOrigen().addItem(origen);
				}
				
				vista.getComboBoxColor().setEnabled(true);
				vista.getComboBoxMarca().setEnabled(true);
				vista.getComboBoxOrigen().setEnabled(true);
				
				vista.getButtonMayor().setEnabled(true);
				vista.getButtonMayorMayor().setEnabled(true);
				vista.getButtonMenor().setEnabled(true);
				vista.getButtonMenorMenor().setEnabled(true);
				vista.getBtnAnadirDatos().setEnabled(true);
				vista.getBtnBorrarDatos().setEnabled(true);
				vista.getBtnActualizarDatos().setEnabled(true);
				vista.getTable().setEnabled(true);
				vista.getBtnBuscar().setEnabled(true);
				vista.getBtnSiguiente().setEnabled(true);
				vista.getBtnAnterior().setEnabled(true);
				vista.getBtnBuscarEnTabla().setEnabled(true);
				vista.getMntmCargarDatos().setEnabled(false);
				scrollPane = new JScrollPane(vista.getTable(),JScrollPane.VERTICAL_SCROLLBAR_NEVER,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
				actualizarDatosEnTabla();
			} 
			return true;
		} else {
			path = ".";
			return false;
		}
	}
	
	// Método que lanzará un input para almacenar los datos recogidos por el teclado
	private void lanzarInputRecogerDatos() throws ExcepcionDTO {
		Object[] textFields = {
				"Matricula", vista.getTextAnadirMatricula(),
				"Marca", vista.getTextAnadirMarca(),
				"Color", vista.getTextAnadirColor(),
				"Modelo", vista.getTextAnadirModelo(),
				"Origen", vista.getTextAnadirOrigen()
		};
		int resultado = JOptionPane.showConfirmDialog(null, textFields, "Añadir datos", JOptionPane.OK_CANCEL_OPTION);
		CochesDTO coche = new CochesDTO(vista.getTextAnadirMatricula().getText(), vista.getTextAnadirMarca().getText(), 
				vista.getTextAnadirColor().getText(), vista.getTextAnadirModelo().getText(),
				vista.getTextAnadirOrigen().getText());
		if(resultado==JOptionPane.OK_OPTION) {
			manipular.insertarCoche(coche);
			dialogo("COCHE INSERTADO CORRECTAMENTE"+"\nMarca: "+coche.getMarca()+
								"\nColor: "+coche.getColor()+
								"\nModelo: "+coche.getModelo()+
								"\nOrigen: "+coche.getOrigen());
			actualizarDatosEnTabla();
		}
	}
	
	// El siguiente método hace que la primera columna de la tabla no sea manipulable y repinta la tabla
	
	private void actualizarDatosEnTabla() {
		List<CochesDTO> lista = manipular.listarCoches();
		manipular.completarArrays(lista);
		DefaultTableModel model = new DefaultTableModel(manipular.getDatos(), manipular.getCabeceras()){
			private static final long serialVersionUID = 1L;
			@Override 
		    public boolean isCellEditable(int row, int column){
		    	return column!=0;
		    }
		};
		dimension = vista.getTable().getPreferredSize();
		vista.getTable().setModel(model);
		scrollPane.setPreferredSize(new Dimension(dimension.width, vista.getTable().getRowHeight()*filas));
		vista.getPanelTablas().add(scrollPane, BorderLayout.CENTER);
	}

	// Metodo para actualizar las filas y los datos de la base de datos
	
	private void actualizarFilas() throws ExcepcionDTO {
	    List<CochesDTO> listaCochesSeleccionados = new ArrayList<>();
	    String matricula;
	    String marca;
		String color;
		String modelo;
		String origen;
	    if (vista.getTable().getRowCount() > 0) {
	        if (vista.getTable().getSelectedRowCount() > 0) {
	            int selectedRow[] = vista.getTable().getSelectedRows();
	            for (int i : selectedRow) {
	            	matricula = vista.getTable().getValueAt(i, 0).toString();
	        		marca = vista.getTable().getValueAt(i, 1).toString();
	        		color = vista.getTable().getValueAt(i, 2).toString();
	        		modelo = vista.getTable().getValueAt(i, 3).toString();
	        		origen = vista.getTable().getValueAt(i, 4).toString();
	        		CochesDTO coche = new CochesDTO(matricula, marca, color, modelo, origen);
	        		listaCochesSeleccionados.add(coche);
	            }
	            manipular.actualizarListaCoches(listaCochesSeleccionados);
	            actualizarDatosEnTabla();
	        }
	    }
	}
	
	// Metodo para borrar las filas y los datos de la base de datos
	
	private void borrarFilas() throws ExcepcionDTO {
	    List<CochesDTO> listaCochesSeleccionados = new ArrayList<>();
	    String matricula;
	    String marca;
		String color;
		String modelo;
		String origen;
	    if (vista.getTable().getRowCount() > 0) {
	        if (vista.getTable().getSelectedRowCount() > 0) {
	            int selectedRow[] = vista.getTable().getSelectedRows();
	            for (int i : selectedRow) {
	            	matricula =vista.getTable().getValueAt(i, 0).toString();
	        		marca = vista.getTable().getValueAt(i, 1).toString();
	        		color = vista.getTable().getValueAt(i, 2).toString();
	        		modelo = vista.getTable().getValueAt(i, 3).toString();
	        		origen = vista.getTable().getValueAt(i, 4).toString();
	        		CochesDTO coche = new CochesDTO(matricula, marca, color, modelo, origen);
					listaCochesSeleccionados.add(coche);
	            }
	            manipular.borrarListaCoches(listaCochesSeleccionados);
	            actualizarDatosEnTabla();
	        }
	    }
	}
	
	// Método que permite realizar búsquedas en el fichero CSV
	
	private List<CochesDTO> datosCSVFiltrados(List<CochesDTO> lista) {
		listaFiltrado.clear();
		String color = (String) vista.getComboBoxColor().getSelectedItem();
		String marca = (String) vista.getComboBoxMarca().getSelectedItem();
		String origen = (String) vista.getComboBoxOrigen().getSelectedItem();
		for (CochesDTO coche : lista) {
			if(coche.getColor().equals(color) && coche.getMarca().equals(marca) && coche.getOrigen().equals(origen)) {
				listaFiltrado.add(coche);
			} else if(coche.getColor().equals(color) && coche.getMarca().equals(marca) && origen.equals("--")) {
				listaFiltrado.add(coche);
			} else if (coche.getMarca().equals(marca) && coche.getOrigen().equals(origen) && color.equals("--")) { 
				listaFiltrado.add(coche);
			} else if (coche.getColor().equals(color) && coche.getOrigen().equals(origen) && marca.equals("--")){
				listaFiltrado.add(coche);
			} else if (coche.getColor().equals(color) && marca.equals("--") && origen.equals("--")) {
				listaFiltrado.add(coche);
			} else if (coche.getMarca().equals(marca) && origen.matches("--") && color.equals("--")) {
				listaFiltrado.add(coche);
			} else if (coche.getOrigen().equals(origen) && marca.equals("--") && color.equals("--")) {
				listaFiltrado.add(coche);
			}
		}	
		return listaFiltrado;
	}

	private void dialogo(String string) {
		JOptionPane.showMessageDialog(null, string, "Aviso", 1);
	}
}
