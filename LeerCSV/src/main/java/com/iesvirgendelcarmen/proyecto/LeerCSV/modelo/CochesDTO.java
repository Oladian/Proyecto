package com.iesvirgendelcarmen.proyecto.LeerCSV.modelo;

public class CochesDTO {
	private String matricula;
	private String marca;
	private String color;
	private String modelo;
	private String origen;
	
	public CochesDTO(String matricula, String marca, String color, String modelo, String origen) throws ExcepcionDTO {
		
		String regex = "[A-Z0-9]{17}";
		if(matricula.matches(regex)) {
			this.matricula = matricula;
			this.marca = marca;
			this.color = color;
			this.modelo = modelo;
			this.origen = origen;
		} else
			throw new ExcepcionDTO("Matrícula incorrecta.");
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public String getOrigen() {
		return origen;
	}

	public void setOrigen(String origen) {
		this.origen = origen;
	}

	@Override
	public String toString() {
		return "CochesDTO [matricula=" + matricula + ", marca=" + marca + ", color=" + color + ", modelo=" + modelo
				+ ", origen=" + origen + "]";
	}
}
