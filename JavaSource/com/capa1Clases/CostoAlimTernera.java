package com.capa1Clases;

import javax.persistence.Entity;
import javax.persistence.Table;

public class CostoAlimTernera {

	private int idTernera;
	private Float costo;
	private int cantidad;
	private int total;

	public int getIdTernera() {
		return idTernera;
	}

	public void setIdTernera(int idTernera) {
		this.idTernera = idTernera;
	}

	public Float getCosto() {
		return costo;
	}

	public void setCosto(Float costo) {
		this.costo = costo;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public CostoAlimTernera(int idTernera, Float costo, int cantidad, int total) {
		super();
		this.idTernera = idTernera;
		this.costo = costo;
		this.cantidad = cantidad;
		this.total = total;
	}

	public CostoAlimTernera(int idTernera, Float costo, int cantidad) {
		super();
		this.idTernera = idTernera;
		this.costo = costo;
		this.cantidad = cantidad;
		this.total = total;
	}

}
