package com.capa3Persistencia.entities;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
//usuario
@Table(name = "Registro_HistoricoEnfermedad")
public class HistoricoEnfermedadEntity {
	@Id

	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQUENCE6")
	@SequenceGenerator(name = "SEQUENCE6", sequenceName = "SEQUENCE6", allocationSize = 1)
	private Long id;
	private Long idTernera;
	private String nombre;
	private String variante;
	private int severidad;
	private String fechaRegistro;

	public HistoricoEnfermedadEntity(Long idTernera, String nombre, String variante, int severidad,
			String fechaRegistro) {
		super();
		this.idTernera = idTernera;
		this.nombre = nombre;
		this.variante = variante;
		this.severidad = severidad;
		this.fechaRegistro = fechaRegistro;
	}

	public HistoricoEnfermedadEntity() {
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
		return severidad;
	}

	public void setSeveridad(int severidad) {
		this.severidad = severidad;
	}

	public String getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(String fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

}
