package com.capa3Persistencia.dao;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import com.capa3Persistencia.entities.HistoricoEnfermedadEntity;
import com.capa3Persistencia.exception.PersistenciaException;

@Stateless
@LocalBean

public class HistoricoEnfermedadDAO {

	@PersistenceContext
	private EntityManager em;

	/**
	 * Default constructor.
	 */
	public HistoricoEnfermedadDAO() {
		super();
	}

	public HistoricoEnfermedadEntity agregarHistorico(HistoricoEnfermedadEntity enfParaAgregar)
			throws PersistenciaException {

		try {
			HistoricoEnfermedadEntity enf = em.merge(enfParaAgregar);
			em.flush();
			return enf;
		} catch (PersistenceException e) {
			throw new PersistenciaException("No se pudo agregar al enf." + e.getMessage(), e);
		} finally {

		}
	}

	public HistoricoEnfermedadEntity borrarHistorico(HistoricoEnfermedadEntity enf) throws PersistenciaException {

		HistoricoEnfermedadEntity HistoricoEnfermedadEntity = em.find(HistoricoEnfermedadEntity.class, enf.getId());
		if (HistoricoEnfermedadEntity == null) {
			throw new PersistenciaException("No existe el empleado a borrar. Id=" + enf.getId());
		}
		try {
			em.remove(HistoricoEnfermedadEntity);
			em.flush();
			return HistoricoEnfermedadEntity;
		} catch (PersistenceException e) {
			throw new PersistenciaException("No se pudo borrar el empleado. Id=" + enf.getId());
		}
	}

	public HistoricoEnfermedadEntity modificarHistorico(HistoricoEnfermedadEntity enfParaModificar)
			throws PersistenciaException {

		try {
			HistoricoEnfermedadEntity enf = em.merge(enfParaModificar);
			em.flush();
			return enf;
		} catch (PersistenceException e) {
			throw new PersistenciaException("No se pudo modificar el enf." + e.getMessage(), e);
		}
	}

	public HistoricoEnfermedadEntity buscarHistorico(Long id2) {
		HistoricoEnfermedadEntity enf = em.find(HistoricoEnfermedadEntity.class, id2);
		return enf;
	}

	public List<HistoricoEnfermedadEntity> buscarHistoricos() throws PersistenciaException {
		try {

			String query = "Select e from HistoricoEnfermedadEntity e";
			List<HistoricoEnfermedadEntity> resultList = (List<HistoricoEnfermedadEntity>) em
					.createQuery(query, HistoricoEnfermedadEntity.class).getResultList();
			return resultList;
		} catch (PersistenceException e) {
			throw new PersistenciaException("No se pudo hacer la consulta." + e.getMessage(), e);
		}

	}

	public List<HistoricoEnfermedadEntity> seleccionarHistoricos(String criterioNombre, String criterioNickname,
			String criterioRol) throws PersistenciaException {
		try {

			String query = "Select e from HistoricoEnfermedadEntity e  ";
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
			List<HistoricoEnfermedadEntity> resultList = (List<HistoricoEnfermedadEntity>) em
					.createQuery(query, HistoricoEnfermedadEntity.class).getResultList();
			return resultList;
		} catch (PersistenceException e) {
			throw new PersistenciaException("No se pudo hacer la consulta." + e.getMessage(), e);
		}
	}

}
