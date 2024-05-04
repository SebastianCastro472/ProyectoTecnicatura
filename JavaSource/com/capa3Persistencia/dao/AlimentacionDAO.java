package com.capa3Persistencia.dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import com.capa1Clases.Alimento;
import com.capa3Persistencia.entities.AlimentacionEntity;
import com.capa3Persistencia.entities.AlimentoEntity;
import com.capa3Persistencia.entities.PartoEntity;
import com.capa3Persistencia.entities.TerneraEntity;
import com.capa3Persistencia.exception.PersistenciaException;

/**
 * Session Bean implementation class UsuariosEJBBean
 */
@Stateless
@LocalBean

public class AlimentacionDAO {

	@PersistenceContext
	private EntityManager em;

	/**
	 * Default constructor.
	 */
	public AlimentacionDAO() {
		super();
	}

	private static final String FIND_NOMBRE = "SELECT * FROM Registro_Alimento WHERE NOMBRE=? AND MARCA=?";

	public AlimentacionEntity agregarAlimentacion(AlimentacionEntity AlimentacionParaAgregar)
			throws PersistenciaException {
		try {
			AlimentacionEntity Alimentacion = em.merge(AlimentacionParaAgregar);
			em.flush();
			return Alimentacion;
		} catch (PersistenceException e) {
			throw new PersistenciaException("No se pudo agregar al Alimentacion." + e.getMessage(), e);
		}
	}

	public AlimentacionEntity buscarAlimentacion(Long id) {
		AlimentacionEntity enf = em.find(AlimentacionEntity.class, id);
		return enf;
	}

	private static Alimento getAlimentoFromResultSet(ResultSet resultado) throws SQLException {

		Long id = resultado.getLong("ID_ALIMENTO");
		String nombre = resultado.getString("NOMBRE");
		int costoUnitario = resultado.getInt("COSTO_UNITARIO");
		int stock = resultado.getInt("STOCK");
		String marca = resultado.getString("MARCA");

		Alimento a = new Alimento(id, nombre, marca, stock, costoUnitario);

		return a;

	}
	public TerneraEntity getTerneraById(Long id) {
		return em.find(TerneraEntity.class, id);
	}
	public AlimentoEntity getAlimentoById(Long id) {
		return em.find(AlimentoEntity.class, id);
	}

	public AlimentoEntity buscarAlimNombreMarca(String nombre, String marca) {
		try {
			TypedQuery<AlimentoEntity> query = em.createQuery(
				    "SELECT a FROM AlimentoEntity a WHERE a.nombre = :nombre AND a.marca = :marca",
				    AlimentoEntity.class);
				query.setParameter("nombre", nombre);
				query.setParameter("marca", marca);


			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

}
