package com.capa1Clases;

import java.util.Date;

public class Peso {

	private Long idTernera;
	private Date fecha;
	private int peso;

	public Long getIdTernera() {
		return idTernera;
	}

	public void setIdTernera(Long idTernera) {
		this.idTernera = idTernera;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public int getPeso() {
		return peso;
	}

	public void setPeso(int peso) {
		this.peso = peso;
	}

	public Peso(Long idTernera, Date fecha, int peso) {
		super();
		this.idTernera = idTernera;
		this.fecha = fecha;
		this.peso = peso;
	}

	public Peso() {
		super();
	}
}
