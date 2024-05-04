package com.capa3Persistencia.dao;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import com.capa3Persistencia.entities.RegistroPesoEntity;
import com.capa3Persistencia.exception.PersistenciaException;

@Stateless
@LocalBean
public class PesoDAO {

	@PersistenceContext
	private EntityManager em;

	public PesoDAO() {
		super();
	}

	public RegistroPesoEntity agregarRegistroPeso(RegistroPesoEntity registroPesoParaAgregar)
			throws PersistenciaException {
		try {
			RegistroPesoEntity registroPeso = em.merge(registroPesoParaAgregar);
			em.flush();
			return registroPeso;
		} catch (PersistenceException e) {
			throw new PersistenciaException("No se pudo agregar el registro de peso." + e.getMessage(), e);
		}
	}

	/*
	 * private static final String FIND_NOMBRE =
	 * "SELECT * FROM ALIMENTO WHERE NOMBRE=? AND MARCA=?"; public
	 * AlimentacionEntity agregarAlimentacion(AlimentacionEntity
	 * AlimentacionParaAgregar) throws PersistenciaException { try {
	 * AlimentacionEntity Alimentacion = em.merge(AlimentacionParaAgregar);
	 * em.flush(); return Alimentacion; } catch (PersistenceException e) { throw new
	 * PersistenciaException("No se pudo agregar al Alimentacion." + e.getMessage(),
	 * e); } }
	 */

	public RegistroPesoEntity buscarRegistroPeso(Long id) {
		RegistroPesoEntity e = em.find(RegistroPesoEntity.class, id);
		return e;
	}

	/*
	 * private static Alimento getAlimentoFromResultSet(ResultSet resultado) throws
	 * SQLException {
	 * 
	 * 
	 * Long id = resultado.getLong("ID_ALIMENTO"); String nombre =
	 * resultado.getString("NOMBRE"); int costoUnitario =
	 * resultado.getInt("COSTO_UNITARIO"); int stock = resultado.getInt("STOCK");
	 * String marca = resultado.getString("MARCA");
	 * 
	 * 
	 * Alimento a = new Alimento(id, nombre, marca, stock, costoUnitario);
	 * 
	 * return a;
	 * 
	 * }
	 * 
	 * public AlimentoEntity buscarAlimNombreMarca(String nombre, String marca) {
	 * try { TypedQuery<AlimentoEntity> query = em.createQuery(
	 * "SELECT a FROM AlimentoEntity a WHERE a.nombre = :nombre AND a.marca = :marca"
	 * , AlimentoEntity.class); query.setParameter("nombre", nombre);
	 * query.setParameter("marca", marca);
	 * 
	 * return query.getSingleResult(); } catch (NoResultException e) { return null;
	 * } }
	 * 
	 * 
	 * 
	 * }
	 */

}
