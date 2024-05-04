package com.capa3Persistencia.entities;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.*;

@Entity
@Table(name = "Parto")
public class PartoEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private Long Id_parto;
	
	private String parto;
	
	
	public Long getId_parto() {
		return Id_parto;
	}
	public void setId_parto(Long id_parto) {
		Id_parto = id_parto;
	}
	public String getParto() {
		return parto;
	}
	public void setParto(String parto) {
		this.parto = parto;
	}
	
	




	

}
