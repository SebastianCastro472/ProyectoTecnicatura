package com.capa1Clases;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import javax.json.bind.annotation.JsonbDateFormat;

public class Ternera {
	
	
	private Long idTernera;
	private int idMadre;
	private int idPadre;
	private String raza;
	private String tipoDeParto;
	private String caravanaTambo;
	private String caravanaSnig;
	private int pesoAlNacer;
	private Date fechaNacimiento;
	private int id_guachera;
	private Boolean borrado;
	

	public Boolean getBorrado() {
		return borrado;
	}

	public void setBorrado(Boolean borrado) {
		this.borrado = borrado;
	}

	public Ternera() {
		super();
	}

	public Ternera(Long idTernera, int idMadre, int idPadre, String raza, String tipoDeParto,
			String caravanaTambo, String caravanaSnig, int pesoAlNacer, Date fechaNacimiento, int id_guachera) {
		super();
		this.idTernera = idTernera;
		this.idMadre = idMadre;
		this.idPadre = idPadre;
		this.raza = raza;
		this.tipoDeParto = tipoDeParto;
		this.caravanaTambo = caravanaTambo;
		this.caravanaSnig = caravanaSnig;
		this.pesoAlNacer = pesoAlNacer;
		this.fechaNacimiento = fechaNacimiento;
		this.id_guachera = id_guachera;

	}

	public int getId_guachera() {
		return id_guachera;
	}

	public void setId_guachera(int id_guachera) {
		this.id_guachera = id_guachera;
	}

	public Long getIdTernera() {
		return idTernera;
	}

	public void setIdTernera(Long idTernera) {
		this.idTernera = idTernera;
	}

	public int getIdMadre() {
		return idMadre;
	}

	public void setIdMadre(int idMadre) {
		this.idMadre = idMadre;
	}

	public int getIdPadre() {
		return idPadre;
	}

	public void setIdPadre(int idPadre) {
		this.idPadre = idPadre;
	}

	public String getRaza() {
		return raza;
	}

	public void setRaza(String raza) {
		this.raza = raza;
	}

	public String getTipoDeParto() {
		return tipoDeParto;
	}

	public void setTipoDeParto(String tipoDeParto) {
		this.tipoDeParto = tipoDeParto;
	}

	public String getCaravanaTambo() {
		return caravanaTambo;
	}

	public void setCaravanaTambo(String caravanaTambo) {
		this.caravanaTambo = caravanaTambo;
	}

	public String getCaravanaSnig() {
		return caravanaSnig;
	}

	public void setCaravanaSnig(String caravanaSnig) {
		this.caravanaSnig = caravanaSnig;
	}

	public int getPesoAlNacer() {
		return pesoAlNacer;
	}

	public void setPesoAlNacer(int pesoAlNacer) {
		this.pesoAlNacer = pesoAlNacer;
	}

	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}


    

}
