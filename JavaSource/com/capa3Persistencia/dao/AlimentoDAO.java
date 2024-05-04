package com.capa3Persistencia.dao;

import java.sql.PreparedStatement;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import javax.resource.cci.ResultSet;

import com.capa1Clases.Alimento;
import com.capa3Persistencia.entities.AlimentoEntity;
//import com.capa3Persistencia.entities.Usuario;
import com.capa3Persistencia.exception.PersistenciaException;

/**
 * Session Bean implementation class UsuariosEJBBean
 */
@Stateless
@LocalBean

public class AlimentoDAO {

	@PersistenceContext
	private EntityManager em;

	/**
	 * Default constructor.
	 */
	public AlimentoDAO() {
		super();
	}

	public AlimentoEntity agregarAlimento(AlimentoEntity alimentoParaAgregar) throws PersistenciaException {

		try {
			AlimentoEntity alimento = em.merge(alimentoParaAgregar);
			em.flush();
			return alimento;
		} catch (PersistenceException e) {
			throw new PersistenciaException("No se pudo agregar al alimento." + e.getMessage(), e);
		} finally {

		}
	}

	public AlimentoEntity borrarAlimento(AlimentoEntity alimento) throws PersistenciaException {

		AlimentoEntity alimentoEntity = em.find(AlimentoEntity.class, alimento.getIdAlimento());
		if (alimentoEntity == null) {
			throw new PersistenciaException("No existe el empleado a borrar. Id=" + alimento.getIdAlimento());
		}
		try {
			AlimentoEntity Alimento = em.merge(alimento);
			em.flush();
			return alimentoEntity;
		} catch (PersistenceException e) {
			throw new PersistenciaException("No se pudo borrar el empleado. Id=" + alimento.getIdAlimento());
		}
	}

	public AlimentoEntity modificarAlimento(AlimentoEntity alimentoParaModificar) throws PersistenciaException {

		try {
			AlimentoEntity Alimento = em.merge(alimentoParaModificar);
			em.flush();
			return Alimento;
		} catch (PersistenceException e) {
			throw new PersistenciaException("No se pudo modificar el alimento." + e.getMessage(), e);
		}
	}

	public AlimentoEntity buscarAlimento(Long id) {
		AlimentoEntity alimento = em.find(AlimentoEntity.class, id);
		return alimento;
	}

	public List<AlimentoEntity> buscarAlimentos() throws PersistenciaException {
		try {

			String query = "Select e from AlimentoEntity e";
			List<AlimentoEntity> resultList = (List<AlimentoEntity>) em.createQuery(query, AlimentoEntity.class)
					.getResultList();
			return resultList;
		} catch (PersistenceException e) {
			throw new PersistenciaException("No se pudo hacer la consulta." + e.getMessage(), e);
		}

	}

	public List<AlimentoEntity> seleccionarAlimentos(String criterioNombre, String criterioMarca)
			throws PersistenciaException {
		try {

			String query = "Select e from AlimentoEntity e where e.borrado is null ";
			String queryCriterio = "";
			if (criterioNombre != null && !criterioNombre.contentEquals("")) {
				queryCriterio += (!queryCriterio.isEmpty() ? " and " : "") + " e.nombre like '%" + criterioNombre
						+ "%' ";
			}
			if (criterioMarca != null && !criterioMarca.equals("")) {
				queryCriterio += (!queryCriterio.isEmpty() ? " and " : "") + " e.marca='" + criterioMarca + "'  ";
			}
			if (!queryCriterio.contentEquals("")) {
				query += " where " + queryCriterio;
			}
			List<AlimentoEntity> resultList = (List<AlimentoEntity>) em.createQuery(query, AlimentoEntity.class)
					.getResultList();
			return resultList;
		} catch (PersistenceException e) {
			throw new PersistenciaException("No se pudo hacer la consulta." + e.getMessage(), e);
		}
	}
	public AlimentoEntity buscarAlimNombreMarca(String nombre, String marca) {
		try {
			TypedQuery<AlimentoEntity> query = em.createQuery(
				    "SELECT a FROM Registro_Alimento a WHERE a.nombre = :nombre AND a.marca = :marca",
				    AlimentoEntity.class);
				query.setParameter("nombre", nombre);
				query.setParameter("marca", marca);


			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

}
