package com.capa3Persistencia.entities;

import java.io.Serializable;
import java.lang.Long;
import java.lang.String;
import java.util.List;

import javax.persistence.*;


@Entity
@Table(name = "Registro_Alimento")
public class AlimentoEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQUENCE1")
	@SequenceGenerator(name = "SEQUENCE1", sequenceName = "SEQUENCE1", allocationSize = 1)
	private Long IdAlimento;
	@Column(unique = true)
	private String marca;
	private String nombre;
	private int cantidad;
	private int costo;
	private Boolean borrado;
	
	@OneToMany(mappedBy = "alimento")
	private List<AlimentacionEntity> alimentaciones;
	
	
	

	public AlimentoEntity(String marca, String nombre, int cantidad, int costo, Boolean borrado,
			List<AlimentacionEntity> alimentaciones) {
		super();
		this.marca = marca;
		this.nombre = nombre;
		this.cantidad = cantidad;
		this.costo = costo;
		this.borrado = borrado;
		this.alimentaciones = alimentaciones;
	}

	public AlimentoEntity(Long idAlimento, String marca, String nombre, int cantidad, int costo, Boolean borrado,
			List<AlimentacionEntity> alimentaciones) {
		super();
		IdAlimento = idAlimento;
		this.marca = marca;
		this.nombre = nombre;
		this.cantidad = cantidad;
		this.costo = costo;
		this.borrado = borrado;
		this.alimentaciones = alimentaciones;
	}

	public Boolean getBorrado() {
		return borrado;
	}

	public void setBorrado(Boolean borrado) {
		this.borrado = borrado;
	}

	public AlimentoEntity() {
		super();
	}

	public List<AlimentacionEntity> getAlimentaciones() {
		return alimentaciones;
	}

	public void setAlimentaciones(List<AlimentacionEntity> alimentaciones) {
		this.alimentaciones = alimentaciones;
	}

	public AlimentoEntity(Long idAlimento, String marca, String nombre, int cantidad, int costo) {
		super();
		IdAlimento = idAlimento;
		this.marca = marca;
		this.nombre = nombre;
		this.cantidad = cantidad;
		this.costo = costo;
	}

	public Long getIdAlimento() {
		return IdAlimento;
	}

	public void setIdAlimento(Long idAlimento) {
		IdAlimento = idAlimento;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public int getCosto() {
		return costo;
	}

	public void setCosto(int costo) {
		this.costo = costo;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
