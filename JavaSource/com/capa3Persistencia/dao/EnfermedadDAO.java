package com.capa3Persistencia.dao;

import java.io.Console;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import com.capa3Persistencia.entities.EnfermedadEntity;
import com.capa3Persistencia.entities.TerneraEntity;
//import com.capa3Persistencia.entities.Usuario;
import com.capa3Persistencia.exception.PersistenciaException;

/**
 * Session Bean implementation class UsuariosEJBBean
 */
@Stateless
@LocalBean

public class EnfermedadDAO {

	@PersistenceContext
	private EntityManager em;

	/**
	 * Default constructor.
	 */
	public EnfermedadDAO() {
		super();
	}

	public EnfermedadEntity agregarEnfermedad(EnfermedadEntity EnfermedadParaAgregar) throws PersistenciaException {

		try {
			System.out.print(EnfermedadParaAgregar);
			EnfermedadEntity enfermedad = em.merge(EnfermedadParaAgregar);
			em.flush();
			return enfermedad;
		} catch (PersistenceException e) {
			throw new PersistenciaException("No se pudo agregar al Enfermedad." + e.getMessage(), e);
		} finally {

		}
	}

	public EnfermedadEntity borrarEnfermedad(EnfermedadEntity Enfermedad) throws PersistenciaException {

		EnfermedadEntity EnfermedadEntity = em.find(EnfermedadEntity.class, Enfermedad.getId());
		if (EnfermedadEntity == null) {
			throw new PersistenciaException("No existe el empleado a borrar. Id=" + Enfermedad.getId());
		}
		try {
			em.remove(EnfermedadEntity);
			em.flush();
			return EnfermedadEntity;
		} catch (PersistenceException e) {
			throw new PersistenciaException("No se pudo borrar el empleado. Id=" + Enfermedad.getId());
		}
	}

	public EnfermedadEntity modificarEnfermedad(EnfermedadEntity EnfermedadParaModificar) throws PersistenciaException {

		try {
			EnfermedadEntity Enfermedad = em.merge(EnfermedadParaModificar);
			em.flush();
			return Enfermedad;
		} catch (PersistenceException e) {
			throw new PersistenciaException("No se pudo modificar el Enfermedad." + e.getMessage(), e);
		}
	}

	public EnfermedadEntity buscarEnfermedad(Long id2) {
		EnfermedadEntity Enfermedad = em.find(EnfermedadEntity.class, id2);
		return Enfermedad;
	}

	public List<EnfermedadEntity> buscarEnfermedads() throws PersistenciaException {
		try {

			String query = "Select e from EnfermedadEntity e";
			List<EnfermedadEntity> resultList = (List<EnfermedadEntity>) em.createQuery(query, EnfermedadEntity.class)
					.getResultList();
			return resultList;
		} catch (PersistenceException e) {
			throw new PersistenciaException("No se pudo hacer la consulta." + e.getMessage(), e);
		}

	}

	public List<EnfermedadEntity> seleccionarEnfermedads(String criterioNombre, String criterioNickname,
			String criterioRol) throws PersistenciaException {
		try {

			String query = "Select e from EnfermedadEntity e  ";
			String queryCriterio = "";
			if (criterioNombre != null && !criterioNombre.contentEquals("")) {
				queryCriterio += (!queryCriterio.isEmpty() ? " and " : "") + " e.nombre like '%" + criterioNombre
						+ "%' ";
			}
			if (criterioNickname != null && !criterioNickname.equals("")) {
				queryCriterio += (!queryCriterio.isEmpty() ? " and " : "") + " e.nickname='" + criterioNickname + "'  ";
			}
			if (criterioRol != null)

			{
				queryCriterio += (!queryCriterio.isEmpty() ? " and " : "") + " e.rol  ";
			}
			if (!queryCriterio.contentEquals("")) {
				query += " where " + queryCriterio;
			}
			List<EnfermedadEntity> resultList = (List<EnfermedadEntity>) em.createQuery(query, EnfermedadEntity.class)
					.getResultList();
			return resultList;
		} catch (PersistenceException e) {
			throw new PersistenciaException("No se pudo hacer la consulta." + e.getMessage(), e);
		}
	}

}