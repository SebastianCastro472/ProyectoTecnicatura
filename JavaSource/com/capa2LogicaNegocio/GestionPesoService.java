package com.capa2LogicaNegocio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import com.capa1Clases.Alimentacion;
import com.capa1Clases.Enfermedad;
import com.capa1Clases.Peso;
import com.capa3Persistencia.dao.AlimentacionDAO;
import com.capa3Persistencia.dao.PesoDAO;
import com.capa3Persistencia.dao.AlimentacionDAO;
import com.capa3Persistencia.entities.AlimentacionEntity;
import com.capa3Persistencia.entities.EnfermedadEntity;
import com.capa3Persistencia.entities.RegistroPesoEntity;
import com.capa3Persistencia.exception.PersistenciaException;

@Stateless
@LocalBean

public class GestionPesoService implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	PesoDAO registroPesoPersistenciaDAO;

	/*
	 * public Peso fromRegistroPesoEntity(RegistroPesoEntity e) { Peso peso = new
	 * Peso(); enf.setIdTernera(enf.getIdTernera()); enf.setFecha(enf.getFecha());
	 * enf.setCantidad(enf.getCantidad()); return enf;
	 * 
	 * }
	 */
	public RegistroPesoEntity toRegistroPesoEntity(Peso e) {
		RegistroPesoEntity peso = new RegistroPesoEntity();
		peso.setId(e.getIdTernera());
		peso.setFecha(e.getFecha());
		peso.setPeso(e.getPeso());
		return peso;
	}

	/*
	 * public Peso buscarAlimentacionEntity(Long id) { RegistroPesoEntity e =
	 * AlimentacionesPersistenciaDAO.buscarAlimentacion(id); return
	 * fromRegistroPesoEntity(e); } /*
	 * 
	 * public Alimentacion buscarAlimentacion(Long id) { AlimentacionEntity e =
	 * AlimentacionesPersistenciaDAO.buscarAlimentacion(id); return
	 * fromAlimentacionEntity(e); }
	 */
	public void agregarRegistroPeso(Peso pesoSeleccionado) throws PersistenciaException {
		RegistroPesoEntity e = registroPesoPersistenciaDAO.agregarRegistroPeso(toRegistroPesoEntity(pesoSeleccionado));
	}
}
