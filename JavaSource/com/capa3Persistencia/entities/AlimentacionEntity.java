package com.capa3Persistencia.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

@Entity
@Table(name = "Registro_Alimentacion")
public class AlimentacionEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQUENCE4")
	@SequenceGenerator(name = "SEQUENCE4", sequenceName = "SEQUENCE4", allocationSize = 1)
	private Long Id;

	@ManyToOne
	@JoinColumn(name = "IdAlimento", nullable = false)
	private AlimentoEntity alimento;

	private String marca;
	private String Nombre;
	private int cantidad;
	private Date fecha;

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
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public AlimentoEntity getAlimento() {
		return alimento;
	}

	public void setAlimento(AlimentoEntity alimento) {
		this.alimento = alimento;
	}

	public AlimentacionEntity() {
		super();
	}

	

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public AlimentacionEntity(Long id, AlimentoEntity alimento, String marca, String nombre, int cantidad, Date fecha,
			TerneraEntity ternera) {
		super();
		Id = id;
		this.alimento = alimento;
		this.marca = marca;
		Nombre = nombre;
		this.cantidad = cantidad;
		this.fecha = fecha;
		this.ternera = ternera;
	}

	public AlimentacionEntity(AlimentoEntity alimento, String marca, String nombre, int cantidad, Date fecha,
			TerneraEntity ternera) {
		super();
		this.alimento = alimento;
		this.marca = marca;
		Nombre = nombre;
		this.cantidad = cantidad;
		this.fecha = fecha;
		this.ternera = ternera;
	}

	
}
