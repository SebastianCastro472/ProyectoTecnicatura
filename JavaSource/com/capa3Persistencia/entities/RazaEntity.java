package com.capa3Persistencia.entities;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.*;

@Entity
@Table(name = "Raza")
public class RazaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private Long Id_raza;
	
	private String Raza;
	
	
	
	public Long getId_raza() {
		return Id_raza;
	}
	public void setId_raza(Long id_raza) {
		Id_raza = id_raza;
	}
	public String getRaza() {
		return Raza;
	}
	public void setRaza(String raza) {
		Raza = raza;
	}
	
	
	
	
	

	
}
