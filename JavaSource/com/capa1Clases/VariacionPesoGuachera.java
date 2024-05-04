package com.capa1Clases;

import java.util.Date;

public class VariacionPesoGuachera {

	private Long idGuachera;
	private Long idTernera;
	private Float peso;
	private Date fecha_de_peso;

	public Long getIdGuachera() {
		return idGuachera;
	}

	public void setIdGuachera(Long idGuachera) {
		this.idGuachera = idGuachera;
	}

	public Float getPeso() {
		return peso;
	}

	public void setPeso(Float peso) {
		this.peso = peso;
	}

	public Date getFecha_de_peso() {
		return fecha_de_peso;
	}

	public void setFecha_de_peso(Date fecha_de_peso) {
		this.fecha_de_peso = fecha_de_peso;
	}

	public Long getIdTernera() {
		return idTernera;
	}

	public void setIdTernera(Long idTernera) {
		this.idTernera = idTernera;
	}

	public VariacionPesoGuachera(Long idGuachera, Long idTernera, Float peso, Date fecha_de_peso) {
		super();
		this.idGuachera = idGuachera;
		this.idTernera = idTernera;
		this.peso = peso;
		this.fecha_de_peso = fecha_de_peso;
	}

	public VariacionPesoGuachera(Long idTernera) {
		super();
		this.idTernera = idTernera;
	}

}
