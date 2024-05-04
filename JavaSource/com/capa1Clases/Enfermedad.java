package com.capa1Clases;

import java.util.Date;

public class Enfermedad {
	public Long id;
	public Integer idTernera;
	public String nombre;
	public String variante;
	public int severidad;
	public Date fechaRegistro;

	public Enfermedad() {
		super();
	}

	public Enfermedad(Integer idTernera, String nombre, String variante, int severidad, Date fechaRegistro) {
		super();
		this.idTernera = idTernera;
		this.nombre = nombre;
		this.variante = variante;
		this.severidad = severidad;
		this.fechaRegistro = fechaRegistro;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getIdTernera() {
		return idTernera;
	}

	public void setIdTernera(Integer idTernera) {
		this.idTernera = idTernera;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getVariante() {
		return variante;
	}

	public void setVariante(String variante) {
		this.variante = variante;
	}

	public int getSeveridad() {
		return severidad;
	}

	public void setSeveridad(int severidad) {
		this.severidad = severidad;
	}

	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

}
