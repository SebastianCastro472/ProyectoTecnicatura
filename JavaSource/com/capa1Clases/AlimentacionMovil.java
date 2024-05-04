package com.capa1Clases;

import java.util.Date;

public class AlimentacionMovil {
	private Long id;
	private Long idTernera;
	private String marca;	
	private String nombre;
	private int cantidad;
	private Long idALimento; 
	private String fecha;
	
	
	
	
	public AlimentacionMovil(Long idTernera, String marca, String nombre, int cantidad, String fecha) {
		super();
		this.idTernera = idTernera;
		this.marca = marca;
		this.nombre = nombre;
		this.cantidad = cantidad;
		this.fecha = fecha;
	}
	public AlimentacionMovil(Long idTernera, String marca, String nombre, int cantidad, Long idALimento, String fecha) {
		super();
		this.idTernera = idTernera;
		this.marca = marca;
		this.nombre = nombre;
		this.cantidad = cantidad;
		this.idALimento = idALimento;
		this.fecha = fecha;
	}
	public AlimentacionMovil(Long id, Long idTernera, String marca, String nombre, int cantidad, Long idALimento,
			String fecha) {
		super();
		this.id = id;
		this.idTernera = idTernera;
		this.marca = marca;
		this.nombre = nombre;
		this.cantidad = cantidad;
		this.idALimento = idALimento;
		this.fecha = fecha;
	}
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
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
	public Long getIdALimento() {
		return idALimento;
	}
	public void setIdALimento(Long idALimento) {
		this.idALimento = idALimento;
	}
	
	public AlimentacionMovil() {
		super();
	}
	
	
	
	
	
	

}
