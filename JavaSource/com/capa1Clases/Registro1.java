package com.capa1Clases;

import java.sql.Date;

public class Registro1 {

	private int idCaravana;
	private Date fecha;
	private float peso;

	public int getIdCaravana() {
		return idCaravana;
	}

	public void setIdCaravana(int idCaravana) {
		this.idCaravana = idCaravana;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public float getPeso() {
		return peso;
	}

	public void setPeso(float peso) {
		this.peso = peso;
	}

	public Registro1(int idCaravana, Date fecha, float peso) {
		super();
		this.idCaravana = idCaravana;
		this.fecha = fecha;
		this.peso = peso;
	}

	@Override
	public String toString() {
		return "Registro1 [idCaravana=" + idCaravana + ", fecha=" + fecha + ", peso=" + peso + "]";
	}

}
