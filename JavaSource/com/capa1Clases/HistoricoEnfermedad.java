package com.capa1Clases;

public class HistoricoEnfermedad {
	public Long id;
	public Long idTernera;
	public String nombre;
	public String variante;
	public int Severidad;
	public String fechaRegistro;

	public HistoricoEnfermedad(Long idTernera, String nombre, String variante, int severidad, String fechaRegistro) {
		super();
		this.idTernera = idTernera;
		this.nombre = nombre;
		this.variante = variante;
		Severidad = severidad;
		this.fechaRegistro = fechaRegistro;
	}

	public HistoricoEnfermedad() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdTernera() {
		return idTernera;
	}

	public void setIdTernera(Long idTernera) {
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
		return Severidad;
	}

	public void setSeveridad(int severidad) {
		Severidad = severidad;
	}

	public String getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(String fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

}
