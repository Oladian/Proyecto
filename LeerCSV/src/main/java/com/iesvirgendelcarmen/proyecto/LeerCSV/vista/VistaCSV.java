package com.iesvirgendelcarmen.proyecto.LeerCSV.vista;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JTabbedPane;
import javax.swing.JTable;

import java.awt.BorderLayout;
import javax.swing.JSplitPane;
import javax.swing.JPanel;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import java.awt.FlowLayout;
import javax.swing.JComboBox;
import javax.swing.border.LineBorder;
import java.awt.Color;

public class VistaCSV {

	private JFrame frame;
	private JButton buttonMenorMenor;
	private JButton buttonMenor;
	private JButton buttonMayor;
	private JButton buttonMayorMayor;
	private JTextField textFieldOrigen;
	private JTextField textFieldModelo;
	private JTextField textFieldColor;
	private JTextField textMarca;
	private JTextField textMatricula;
	private JMenuItem mntmCargarDatos;
	private JMenuItem mntmSalir;
	private JButton btnAnadirDatos;
	private JButton btnBorrarDatos;
	private JButton btnActualizarDatos;
	private JTable table;
	private JScrollPane scrollPaneTablas;

	private JTextField textAnadirMatricula = new JTextField();
	private JTextField textAnadirMarca = new JTextField();
	private JTextField textAnadirColor = new JTextField();
	private JTextField textAnadirModelo = new JTextField();
	private JTextField textAnadirOrigen = new JTextField();
	
	private JPanel panelComboBox1;
	private JPanel panelComboBox2;
	private JComboBox<String> comboBoxColor;
	private JComboBox<String> comboBoxMarca;
	private JPanel panelComboBox3;
	private JComboBox<String> comboBoxOrigen;
	private JPanel panelBotonesComboBox;
	private JButton btnReset;
	private JButton btnBuscar;
	private JPanel panelContenedorBotonesCB;
	private JPanel panelSiguienteAnterior;
	private JButton btnSiguiente;
	private JButton btnAnterior;
	private JPanel panelTablas;
	private JMenuItem mntmGuardar;
	private JButton btnBuscarEnTabla;
	private JMenuItem mntmGenerarPdf;
	
	public VistaCSV() {
		initialize();
		frame.setVisible(true);
	}

	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 566);
		frame.setTitle("Proyecto de Programación de Izan Ortiz Serrano");
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JMenu mnArchivo = new JMenu("Archivo");
		menuBar.add(mnArchivo);
		
		mntmCargarDatos = new JMenuItem("Cargar datos");
		mnArchivo.add(mntmCargarDatos);
		
		mntmGenerarPdf = new JMenuItem("Generar PDF");
		mntmGenerarPdf.setEnabled(false);
		mnArchivo.add(mntmGenerarPdf);
		
		mntmGuardar = new JMenuItem("Guardar");
		mnArchivo.add(mntmGuardar);
		
		mntmSalir = new JMenuItem("Salir");
		mnArchivo.add(mntmSalir);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		frame.getContentPane().add(tabbedPane, BorderLayout.CENTER);
		
		JSplitPane splitPane = new JSplitPane();
		splitPane.setResizeWeight(0.25);
		tabbedPane.addTab("Filtros", null, splitPane, null);
		
		JPanel panelDerecho = new JPanel();
		panelDerecho.setBorder(new LineBorder(Color.GRAY, 3));
		splitPane.setRightComponent(panelDerecho);
		panelDerecho.setLayout(new GridLayout(0, 1, 0, 0));
		
		JLabel lblMatricula = new JLabel("Matricula");
		panelDerecho.add(lblMatricula);
		
		textMatricula = new JTextField();
		textMatricula.setEditable(false);
		panelDerecho.add(textMatricula);
		textMatricula.setColumns(10);
		
		JLabel lblMarca = new JLabel("Marca");
		panelDerecho.add(lblMarca);
		
		textMarca = new JTextField();
		textMarca.setEditable(false);
		panelDerecho.add(textMarca);
		textMarca.setColumns(10);
		
		JLabel lblColor = new JLabel("Color");
		panelDerecho.add(lblColor);
		
		textFieldColor = new JTextField();
		textFieldColor.setEditable(false);
		panelDerecho.add(textFieldColor);
		textFieldColor.setColumns(10);
		
		JLabel lblModelo = new JLabel("Modelo");
		panelDerecho.add(lblModelo);
		
		textFieldModelo = new JTextField();
		textFieldModelo.setEditable(false);
		panelDerecho.add(textFieldModelo);
		textFieldModelo.setColumns(10);
		
		JLabel lblOrigen = new JLabel("Origen");
		panelDerecho.add(lblOrigen);
		
		textFieldOrigen = new JTextField();
		textFieldOrigen.setEditable(false);
		panelDerecho.add(textFieldOrigen);
		textFieldOrigen.setColumns(10);
		
		JPanel panelBotones = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panelBotones.getLayout();
		flowLayout.setVgap(12);
		panelDerecho.add(panelBotones);
		
		buttonMenorMenor = new JButton("<<");
		panelBotones.add(buttonMenorMenor);
		
		buttonMenor = new JButton("<");
		panelBotones.add(buttonMenor);
		
		buttonMayor = new JButton(">");
		panelBotones.add(buttonMayor);
		
		buttonMayorMayor = new JButton(">>");
		panelBotones.add(buttonMayorMayor);
		
		JPanel panelIzquierdo = new JPanel();
		panelIzquierdo.setBorder(new LineBorder(Color.GRAY, 3));
		splitPane.setLeftComponent(panelIzquierdo);
		panelIzquierdo.setLayout(new GridLayout(0, 1, 0, 0));
		
		panelComboBox1 = new JPanel();
		panelIzquierdo.add(panelComboBox1);
		
		JLabel lblColor_1 = new JLabel("Color");
		panelComboBox1.add(lblColor_1);
		
		comboBoxColor = new JComboBox<String>();
		comboBoxColor.setEnabled(false);
		panelComboBox1.add(comboBoxColor);
		
		panelComboBox2 = new JPanel();
		panelIzquierdo.add(panelComboBox2);
		
		JLabel lblMarca_1 = new JLabel("Marca");
		panelComboBox2.add(lblMarca_1);
		
		comboBoxMarca = new JComboBox<String>();
		comboBoxMarca.setEnabled(false);
		panelComboBox2.add(comboBoxMarca);
		
		panelComboBox3 = new JPanel();
		panelIzquierdo.add(panelComboBox3);
		
		JLabel lblOrigen_1 = new JLabel("Origen");
		panelComboBox3.add(lblOrigen_1);
		
		comboBoxOrigen = new JComboBox<String>();
		comboBoxOrigen.setEnabled(false);
		panelComboBox3.add(comboBoxOrigen);
		
		panelContenedorBotonesCB = new JPanel();
		panelIzquierdo.add(panelContenedorBotonesCB);
		panelContenedorBotonesCB.setLayout(new BorderLayout(0, 0));
		
		panelBotonesComboBox = new JPanel();
		panelContenedorBotonesCB.add(panelBotonesComboBox);
		panelBotonesComboBox.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		btnBuscar = new JButton("Buscar");
		btnBuscar.setEnabled(false);
		panelBotonesComboBox.add(btnBuscar);
		
		btnReset = new JButton("Reset");
		btnReset.setEnabled(false);
		panelBotonesComboBox.add(btnReset);
		
		panelTablas = new JPanel();
		panelTablas.setBorder(new LineBorder(Color.GRAY, 3));
		tabbedPane.addTab("Tablas", null, panelTablas, null);
		panelTablas.setLayout(new BorderLayout(0, 0));
		
		scrollPaneTablas = new JScrollPane(table,JScrollPane.VERTICAL_SCROLLBAR_NEVER,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPaneTablas.setEnabled(false);
//		panelTablas.add(scrollPaneTablas,BorderLayout.CENTER);
		
		table = new JTable();
		
		panelSiguienteAnterior = new JPanel();
		panelTablas.add(panelSiguienteAnterior, BorderLayout.SOUTH);
		
		btnAnterior = new JButton("Anterior");
		btnAnterior.setEnabled(false);
		panelSiguienteAnterior.add(btnAnterior);
		
		btnSiguiente = new JButton("Siguiente");
		btnSiguiente.setEnabled(false);
		panelSiguienteAnterior.add(btnSiguiente);
		
		JPanel panelBotonesTablas = new JPanel();
		panelTablas.add(panelBotonesTablas, BorderLayout.NORTH);
		panelBotonesTablas.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		btnAnadirDatos = new JButton("Anadir datos");
		panelBotonesTablas.add(btnAnadirDatos);
		
		btnBorrarDatos = new JButton("Borrar datos");
		panelBotonesTablas.add(btnBorrarDatos);
		
		btnActualizarDatos = new JButton("Actualizar datos");
		panelBotonesTablas.add(btnActualizarDatos);
		
		
		buttonMayor.setEnabled(false);
		buttonMayorMayor.setEnabled(false);
		buttonMenor.setEnabled(false);
		buttonMenorMenor.setEnabled(false);
		btnAnadirDatos.setEnabled(false);
		btnBorrarDatos.setEnabled(false);
		btnActualizarDatos.setEnabled(false);
		
		btnBuscarEnTabla = new JButton("Buscar");
		btnBuscarEnTabla.setActionCommand("BuscarTabla");
		btnBuscarEnTabla.setEnabled(false);
		panelBotonesTablas.add(btnBuscarEnTabla);
		table.setEnabled(false);
		
		
	}

	public JMenuItem getMntmGuardar() {
		return mntmGuardar;
	}

	public void setMntmGuardar(JMenuItem mntmGuardar) {
		this.mntmGuardar = mntmGuardar;
	}

	public JButton getBtnAnterior() {
		return btnAnterior;
	}

	public void setBtnAnterior(JButton btnAnterior) {
		this.btnAnterior = btnAnterior;
	}

	public void setBtnBuscar(JButton btnBuscar) {
		this.btnBuscar = btnBuscar;
	}

	public JFrame getFrame() {
		return frame;
	}

	public void setFrame(JFrame frame) {
		this.frame = frame;
	}

	public JButton getButtonMenorMenor() {
		return buttonMenorMenor;
	}

	public void setButtonMenorMenor(JButton buttonMenorMenor) {
		this.buttonMenorMenor = buttonMenorMenor;
	}

	public JButton getButtonMenor() {
		return buttonMenor;
	}

	public void setButtonMenor(JButton buttonMenor) {
		this.buttonMenor = buttonMenor;
	}

	public JButton getButtonMayor() {
		return buttonMayor;
	}

	public void setButtonMayor(JButton buttonMayor) {
		this.buttonMayor = buttonMayor;
	}

	public JButton getButtonMayorMayor() {
		return buttonMayorMayor;
	}

	public void setButtonMayorMayor(JButton buttonMayorMayor) {
		this.buttonMayorMayor = buttonMayorMayor;
	}

	public JTextField getTextFieldOrigen() {
		return textFieldOrigen;
	}

	public void setTextFieldOrigen(JTextField textFieldOrigen) {
		this.textFieldOrigen = textFieldOrigen;
	}

	public JTextField getTextFieldModelo() {
		return textFieldModelo;
	}

	public void setTextFieldModelo(JTextField textFieldModelo) {
		this.textFieldModelo = textFieldModelo;
	}

	public JTextField getTextFieldColor() {
		return textFieldColor;
	}

	public void setTextFieldColor(JTextField textFieldColor) {
		this.textFieldColor = textFieldColor;
	}

	public JTextField getTextMarca() {
		return textMarca;
	}

	public void setTextMarca(JTextField textMarca) {
		this.textMarca = textMarca;
	}

	public JTextField getTextMatricula() {
		return textMatricula;
	}

	public void setTextMatricula(JTextField textMatricula) {
		this.textMatricula = textMatricula;
	}

	public JMenuItem getMntmCargarDatos() {
		return mntmCargarDatos;
	}

	public void setMntmCargarDatos(JMenuItem mntmCargarDatos) {
		this.mntmCargarDatos = mntmCargarDatos;
	}

	public JMenuItem getMntmSalir() {
		return mntmSalir;
	}

	public void setMntmSalir(JMenuItem mntmSalir) {
		this.mntmSalir = mntmSalir;
	}

	public JButton getBtnAnadirDatos() {
		return btnAnadirDatos;
	}

	public void setBtnAnadirDatos(JButton btnAnadirDatos) {
		this.btnAnadirDatos = btnAnadirDatos;
	}

	public JButton getBtnBorrarDatos() {
		return btnBorrarDatos;
	}

	public void setBtnBorrarDatos(JButton btnBorrarDatos) {
		this.btnBorrarDatos = btnBorrarDatos;
	}

	public JButton getBtnActualizarDatos() {
		return btnActualizarDatos;
	}

	public void setBtnActualizarDatos(JButton btnActualizarDatos) {
		this.btnActualizarDatos = btnActualizarDatos;
	}

	public JTable getTable() {
		return table;
	}

	public void setTable(JTable table) {
		this.table = table;
	}

	public JScrollPane getScrollPaneTablas() {
		return scrollPaneTablas;
	}

	public void setScrollPaneTablas(JScrollPane scrollPaneTablas) {
		this.scrollPaneTablas = scrollPaneTablas;
	}

	public JTextField getTextAnadirMatricula() {
		return textAnadirMatricula;
	}

	public void setTextAnadirMatricula(JTextField textAnadirMatricula) {
		this.textAnadirMatricula = textAnadirMatricula;
	}

	public JTextField getTextAnadirMarca() {
		return textAnadirMarca;
	}

	public void setTextAnadirMarca(JTextField textAnadirMarca) {
		this.textAnadirMarca = textAnadirMarca;
	}

	public JTextField getTextAnadirColor() {
		return textAnadirColor;
	}

	public void setTextAnadirColor(JTextField textAnadirColor) {
		this.textAnadirColor = textAnadirColor;
	}

	public JTextField getTextAnadirModelo() {
		return textAnadirModelo;
	}

	public void setTextAnadirModelo(JTextField textAnadirModelo) {
		this.textAnadirModelo = textAnadirModelo;
	}

	public JTextField getTextAnadirOrigen() {
		return textAnadirOrigen;
	}

	public void setTextAnadirOrigen(JTextField textAnadirOrigen) {
		this.textAnadirOrigen = textAnadirOrigen;
	}

	public JComboBox<String> getComboBoxColor() {
		return comboBoxColor;
	}

	public JComboBox<String> getComboBoxMarca() {
		return comboBoxMarca;
	}

	public JComboBox<String> getComboBoxOrigen() {
		return comboBoxOrigen;
	}

	public JButton getBtnReset() {
		return btnReset;
	}

	public JButton getBtnSiguiente() {
		return btnSiguiente;
	}

	public void setBtnSiguiente(JButton btnSiguiente) {
		this.btnSiguiente = btnSiguiente;
	}

	public JButton getBtnBuscar() {
		return btnBuscar;
	}

	public JPanel getPanelTablas() {
		return panelTablas;
	}

	public void setPanelTablas(JPanel panelTablas) {
		this.panelTablas = panelTablas;
	}

	public JButton getBtnBuscarEnTabla() {
		return btnBuscarEnTabla;
	}

	public void setBtnBuscarEnTabla(JButton btnBuscarEnTabla) {
		this.btnBuscarEnTabla = btnBuscarEnTabla;
	}

	public JMenuItem getMntmGenerarPdf() {
		return mntmGenerarPdf;
	}

	public void setMntmGenerarPdf(JMenuItem mntmGenerarPdf) {
		this.mntmGenerarPdf = mntmGenerarPdf;
	}
	
}
