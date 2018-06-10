package com.iesvirgendelcarmen.proyecto.LeerCSV.modelo;

import java.util.List;

public interface CochesDAO {
	List<CochesDTO> listarCoches();
	public void completarArrays(List<CochesDTO> lista);
	boolean borrarCoche(CochesDTO coche);
	boolean borrarListaCoches(List<CochesDTO> lista);
	boolean actualizarCoche(CochesDTO coche);
	boolean actualizarListaCoches(List<CochesDTO> lista);
	boolean insertarCoche(CochesDTO coche);
	boolean insertarListaCoches(List<CochesDTO> lista);
	boolean crearBaseDatos();
	CochesDTO listaFromMatricula(String matricula);
	void hacerCommit();
}
