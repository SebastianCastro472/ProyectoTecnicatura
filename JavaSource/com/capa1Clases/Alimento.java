package com.capa1Clases;

public class Alimento {
	private Long idAlimento;
	private String marca;
	private String Nombre;
	private int Cantidad;
	private int Costo;
	private Boolean borrado;
	
	
	

	public Alimento(Long idAlimento, String marca, String nombre, int cantidad, int costo, Boolean borrado) {
		super();
		this.idAlimento = idAlimento;
		this.marca = marca;
		Nombre = nombre;
		Cantidad = cantidad;
		Costo = costo;
		this.borrado = borrado;
	}

	public Alimento(String marca, String nombre, int cantidad, int costo, Boolean borrado) {
		super();
		this.marca = marca;
		Nombre = nombre;
		Cantidad = cantidad;
		Costo = costo;
		this.borrado = borrado;
	}

	public Boolean getBorrado() {
		return borrado;
	}

	public void setBorrado(Boolean borrado) {
		this.borrado = borrado;
	}

	public Alimento() {
		super();
	}

	public Alimento(Long idAlimento, String nombre, String marca, int cantidad, int costo) {
		super();
		this.idAlimento = idAlimento;
		this.Nombre = nombre;
		this.marca = marca;
		this.Cantidad = cantidad;
		this.Costo = costo;
	}

	public Long getIdAlimento() {
		return idAlimento;
	}

	public void setIdAlimento(Long idAlimento) {
		this.idAlimento = idAlimento;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getNombre() {
		return Nombre;
	}

	public void setNombre(String nombre) {
		Nombre = nombre;
	}

	public int getCantidad() {
		return Cantidad;
	}

	public void setCantidad(int cantidad) {
		Cantidad = cantidad;
	}

	public int getCosto() {
		return Costo;
	}

	public void setCosto(int costo) {
		Costo = costo;
	}
}
