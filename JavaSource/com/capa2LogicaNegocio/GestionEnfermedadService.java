package com.capa2LogicaNegocio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import com.capa1Clases.Enfermedad;
import com.capa3Persistencia.dao.EnfermedadDAO;
import com.capa3Persistencia.entities.EnfermedadEntity;
import com.capa3Persistencia.exception.PersistenciaException;

@Stateless
@LocalBean

public class GestionEnfermedadService implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EJB
	EnfermedadDAO EnfermedadsPersistenciaDAO;

	public Enfermedad fromEnfermedadEntity(EnfermedadEntity e) {
		Enfermedad enf = new Enfermedad();
		enf.setId(e.getId());
		enf.setIdTernera(e.getIdTernera());
		enf.setNombre(e.getNombre());
		enf.setSeveridad(e.getSeveridad());
		enf.setVariante(e.getVariante());
		enf.setFechaRegistro(e.getFechaRegistro());
		return enf;
	}

	public EnfermedadEntity toEnfermedadEntity(Enfermedad enf) {
		EnfermedadEntity enfe = new EnfermedadEntity();
		enfe.setId(enf.getId());
		enfe.setIdTernera(enf.getIdTernera());
		enfe.setNombre(enf.getNombre());
		enfe.setSeveridad(enf.getSeveridad());
		enfe.setVariante(enf.getVariante());
		enfe.setFechaRegistro(enf.getFechaRegistro());
		return enfe;
	}

	// servicios para capa de Presentacion

	public List<Enfermedad> seleccionarEnfermedads() throws PersistenciaException {
		// buscamos todos los objetos EnfermedadEntity
		List<EnfermedadEntity> listaEnfermedadEntity = EnfermedadsPersistenciaDAO.buscarEnfermedads();

		List<Enfermedad> listaEnfermedads = new ArrayList<Enfermedad>();
		// recorremos listaEnfermedadEntity y vamos populando listaEnfermedad (haciendo
		// la conversion requerída)
		for (EnfermedadEntity EnfermedadEntity_1 : listaEnfermedadEntity) {
			listaEnfermedads.add(fromEnfermedadEntity(EnfermedadEntity_1));
		}
		return listaEnfermedads;
	}

	public List<Enfermedad> seleccionarEnfermedads(String criterioNombre, String criterioNickname, String criterioRol)
			throws PersistenciaException {
		// buscamos Enfermedads segun criterio indicado
		List<EnfermedadEntity> listaEnfermedadEntity = EnfermedadsPersistenciaDAO.seleccionarEnfermedads(criterioNombre,
				criterioNickname, criterioRol);
		// lista para devolver la seleccion de Enfermedads
		List<Enfermedad> listaEnfermedads = new ArrayList<Enfermedad>();
		// recorremos listaEnfermedadEntity y vamos populando listaEnfermedads (haciendo
		// la conversion requerida)
		for (EnfermedadEntity EnfermedadEntity : listaEnfermedadEntity) {
			listaEnfermedads.add(fromEnfermedadEntity(EnfermedadEntity));
		}
		return listaEnfermedads;

	}

	public Enfermedad buscarEnfermedadEntity(Long id) {
		EnfermedadEntity e = EnfermedadsPersistenciaDAO.buscarEnfermedad(id);
		return fromEnfermedadEntity(e);
	}

//
	public Enfermedad buscarEnfermedad(Long id) {
		EnfermedadEntity e = EnfermedadsPersistenciaDAO.buscarEnfermedad(id);
		return fromEnfermedadEntity(e);
	}

	public Enfermedad agregarEnfermedad(Enfermedad enfermedadSeleccionado) throws PersistenciaException {
		EnfermedadEntity e = EnfermedadsPersistenciaDAO.agregarEnfermedad(toEnfermedadEntity(enfermedadSeleccionado));
		return fromEnfermedadEntity(e);
	}

	public void actualizarEnfermedad(Enfermedad EnfermedadSeleccionado) throws PersistenciaException {
		EnfermedadEntity e = EnfermedadsPersistenciaDAO.modificarEnfermedad(toEnfermedadEntity(EnfermedadSeleccionado));
	}

	public void borrarEnfermedad(Enfermedad EnfermedadSeleccionado) throws PersistenciaException {
		EnfermedadEntity e = EnfermedadsPersistenciaDAO.borrarEnfermedad(toEnfermedadEntity(EnfermedadSeleccionado));
	}
}
