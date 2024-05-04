package com.capa3Persistencia.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "Registro_Enfermedad")
public class EnfermedadEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQUENCE5")
	@SequenceGenerator(name = "SEQUENCE5", sequenceName = "SEQUENCE5", allocationSize = 1)
	private Long id;
	@Column(unique = true)
	private Integer idTernera;
	private String nombre;
	private String variante;
	private int severidad;
	private Date fechaRegistro;

	public EnfermedadEntity() {
		super();
	}

	public EnfermedadEntity(Integer idTernera, String nombre, String variante, int severidad, Date fechaRegistro) {
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
