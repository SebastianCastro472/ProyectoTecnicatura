package com.capa3Persistencia.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "Guachera")
public class GuacheraEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "guachera_sequence")
	@SequenceGenerator(name = "guachera_sequence", sequenceName = "guachera_sequence", allocationSize = 1)
	private Long idGuachera;

	@OneToMany(mappedBy = "guachera")
	private List<TerneraEntity> terneras;

	public Long getIdGuachera() {
		return idGuachera;
	}

	public void setIdGuachera(Long idGuachera) {
		this.idGuachera = idGuachera;
	}

	public List<TerneraEntity> getTerneras() {
		return terneras;
	}

	public void setTerneras(List<TerneraEntity> terneras) {
		this.terneras = terneras;
	}

	// Constructor, getters, setters, etc.
}