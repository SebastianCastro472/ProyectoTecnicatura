package com.capa1Clases;

public class CostoAlimGuachera {

	private Long idGuachera;
	private Long idTernera;
	private Float costo;
	private int cantidad;
	private int total;

	public Long getIdGuachera() {
		return idGuachera;
	}

	public void setIdGuachera(Long idGuachera) {
		this.idGuachera = idGuachera;
	}

	public Long getIdTernera() {
		return idTernera;
	}

	public void setIdTernera(Long idTernera) {
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

	public CostoAlimGuachera(Long idGuachera, Long idTernera, Float costo, int cantidad) {
		super();
		this.idGuachera = idGuachera;
		this.idTernera = idTernera;
		this.costo = costo;
		this.cantidad = cantidad;
	}

}
