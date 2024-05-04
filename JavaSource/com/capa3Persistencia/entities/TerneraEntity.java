package com.capa3Persistencia.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.json.bind.annotation.JsonbDateFormat;
import javax.persistence.*;

@Entity
@Table(name = "TerneraEntity")
public class TerneraEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQUENCE1")
	@SequenceGenerator(name = "SEQUENCE1", sequenceName = "SEQUENCE1", allocationSize = 1)
	private Long idTernera;
	
	private int idMadre;
	private int idPadre;
	
	private String raza;
	
	private String tipoDeParto;
	
	
	
	
	@Column(unique = true)
	private String caravanaTambo;
	@Column(unique = true)
	private String caravanaSnig;
	private int pesoAlNacer;
	
	@JsonbDateFormat("dd/MM/yy")
	private Date fechaNacimiento;

	@ManyToOne
	@JoinColumn(name = "id_guachera")
	private GuacheraEntity guachera;

	
	@OneToMany(mappedBy = "ternera")
	private List<AlimentacionEntity> alimentaciones;
	private Boolean borrado;
	
	
	

	public TerneraEntity(Long idTernera, int idMadre, int idPadre, String raza, String tipoDeParto,
			String caravanaTambo, String caravanaSnig, int pesoAlNacer, Date fechaNacimiento, GuacheraEntity guachera,
			List<AlimentacionEntity> alimentaciones, Boolean borrado) {
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
		this.guachera = guachera;
		this.alimentaciones = alimentaciones;
		this.borrado = borrado;
	}

	public TerneraEntity(int idMadre, int idPadre, String raza, String tipoDeParto, String caravanaTambo,
			String caravanaSnig, int pesoAlNacer, Date fechaNacimiento, GuacheraEntity guachera,
			List<AlimentacionEntity> alimentaciones, Boolean borrado) {
		super();
		this.idMadre = idMadre;
		this.idPadre = idPadre;
		this.raza = raza;
		this.tipoDeParto = tipoDeParto;
		this.caravanaTambo = caravanaTambo;
		this.caravanaSnig = caravanaSnig;
		this.pesoAlNacer = pesoAlNacer;
		this.fechaNacimiento = fechaNacimiento;
		this.guachera = guachera;
		this.alimentaciones = alimentaciones;
		this.borrado = borrado;
	}

	public TerneraEntity() {
		super();
	}

	public TerneraEntity(Long idTernera, int idMadre, int idPadre, String raza, String tipoDeParto,
			String caravanaTambo, String caravanaSnig, int pesoAlNacer, Date fechaNacimiento,
			GuacheraEntity guachera) {
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
		this.guachera = guachera;
	}

	
	public Boolean getBorrado() {
		return borrado;
	}

	public void setBorrado(Boolean borrado) {
		this.borrado = borrado;
	}

	// getters and setters
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

	public GuacheraEntity getGuachera() {
		return guachera;
	}

	public void setGuachera(GuacheraEntity guachera) {
		this.guachera = guachera;
	}

	public List<AlimentacionEntity> getAlimentaciones() {
		return alimentaciones;
	}

	public void setAlimentaciones(List<AlimentacionEntity> alimentaciones) {
		this.alimentaciones = alimentaciones;
	}
}