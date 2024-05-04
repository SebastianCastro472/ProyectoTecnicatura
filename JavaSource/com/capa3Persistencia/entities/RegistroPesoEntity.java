package com.capa3Persistencia.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

@Entity
@Table(name = "Registro_Peso")
public class RegistroPesoEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQUENCE8")
	@SequenceGenerator(name = "SEQUENCE8", sequenceName = "SEQUENCE8", allocationSize = 1)
	private Long Id;
	private Date fecha;
	private int peso;

	@ManyToOne
	@JoinColumn(name = "IdTernera", nullable = false)
	private TerneraEntity ternera;

	// getter and setter
	public TerneraEntity getTernera() {
		return ternera;
	}

	public void setTernera(TerneraEntity ternera) {
		this.ternera = ternera;
	}

	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public int getPeso() {
		return peso;
	}

	public void setPeso(int peso) {
		this.peso = peso;
	}

	public RegistroPesoEntity() {
		super();
	}

	public RegistroPesoEntity(TerneraEntity ternera, int peso, Date fecha) {
		super();
		this.ternera = ternera;
		this.peso = peso;
		this.fecha = fecha;
	}
}
