package com.capa1Clases;

import java.util.Date;

public class Alimentacion {
	private Long id;
	private Long idTernera;
	private String marca;	
	private String nombre;
	private int cantidad;
	private Long idALimento; 
	private Date fecha;
	
	
	
	
	public Alimentacion(Long idTernera, String marca, String nombre, int cantidad, Date fecha) {
		super();
		this.idTernera = idTernera;
		this.marca = marca;
		this.nombre = nombre;
		this.cantidad = cantidad;
		this.fecha = fecha;
	}
	public Alimentacion(Long idTernera, String marca, String nombre, int cantidad, Long idALimento, Date fecha) {
		super();
		this.idTernera = idTernera;
		this.marca = marca;
		this.nombre = nombre;
		this.cantidad = cantidad;
		this.idALimento = idALimento;
		this.fecha = fecha;
	}
	public Alimentacion(Long id, Long idTernera, String marca, String nombre, int cantidad, Long idALimento,
			Date fecha) {
		super();
		this.id = id;
		this.idTernera = idTernera;
		this.marca = marca;
		this.nombre = nombre;
		this.cantidad = cantidad;
		this.idALimento = idALimento;
		this.fecha = fecha;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
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
	
	public Alimentacion() {
		super();
	}
	
	
	
	
	
	

}
