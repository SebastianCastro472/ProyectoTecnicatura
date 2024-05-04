package com.capa3Persistencia.dao;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import com.capa3Persistencia.entities.GuacheraEntity;
import com.capa3Persistencia.entities.HistoricoEnfermedadEntity;
import com.capa3Persistencia.entities.PartoEntity;
import com.capa3Persistencia.entities.RazaEntity;
import com.capa3Persistencia.entities.TerneraEntity;
//import com.capa3Persistencia.entities.Usuario;

import com.capa3Persistencia.exception.PersistenciaException;

/**
 * Session Bean implementation class UsuariosEJBBean
 */
@Stateless
@LocalBean
@Named(value = "terneraDAO") // JEE8
public class TerneraDAO {

	@PersistenceContext
	private EntityManager em;
	
	String filtro;
	
	String valor;

	/**
	 * Default constructor.
	 */
	public TerneraDAO() {
		super();
	}
	
	

	public String getFiltro() {
		return filtro;
	}



	public void setFiltro(String filtro) {
		this.filtro = filtro;
	}



	public String getValor() {
		return valor;
	}



	public void setValor(String valor) {
		this.valor = valor;
	}



	public TerneraEntity agregarTernera(TerneraEntity terneraParaAgregar) throws PersistenciaException {

		try {
			TerneraEntity ternera = em.merge(terneraParaAgregar);
			em.flush();
			return ternera;
		} catch (PersistenceException e) {
			throw new PersistenciaException("No se pudo agregar al ternera." + e.getMessage(), e);
		} finally {

		}
	}

	public TerneraEntity borrarTernera(TerneraEntity terneraParaModificar) throws PersistenciaException {

		TerneraEntity terneraEntity = em.find(TerneraEntity.class, terneraParaModificar.getIdTernera());
		if (terneraEntity == null) {
			throw new PersistenciaException("No existe el empleado a borrar. Id=" + terneraParaModificar.getIdTernera());
		}
		try {
			TerneraEntity ternera = em.merge(terneraParaModificar);
			em.flush();
			return terneraEntity;
		} catch (PersistenceException e) {
			throw new PersistenciaException("No se pudo borrar el empleado. Id=" + terneraParaModificar.getIdTernera());
		}
	}

	public TerneraEntity modificarTernera(TerneraEntity terneraParaModificar) throws PersistenciaException {

		try {
			TerneraEntity ternera = em.merge(terneraParaModificar);
			em.flush();
			return ternera;
		} catch (PersistenceException e) {
			throw new PersistenciaException("No se pudo modificar el ternera." + e.getMessage(), e);
		}
	}

	public TerneraEntity buscarTernera(Long id2) {
		TerneraEntity ternera = em.find(TerneraEntity.class, id2);
		return ternera;
	}

	public List<TerneraEntity> buscarTerneras() throws PersistenciaException {
		try {

			String query = "Select e from TerneraEntity e";
			List<TerneraEntity> resultList = (List<TerneraEntity>) em.createQuery(query, TerneraEntity.class)
					.getResultList();
			
			return resultList;
		} catch (PersistenceException e) {
			throw new PersistenciaException("No se pudo hacer la consulta." + e.getMessage(), e);
		}

	}

	public List<TerneraEntity> seleccionarTerneras(String criterioNombre, String criterioNickname, String criterioRol)
			throws PersistenciaException {
		try {

			String query = "Select e from TerneraEntity e where e.borrado is null";
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
			
			List<TerneraEntity> resultList = (List<TerneraEntity>) em.createQuery(query, TerneraEntity.class)
					.getResultList();
			
			return resultList;
		} catch (PersistenceException e) {
			throw new PersistenciaException("No se pudo hacer la consulta." + e.getMessage(), e);
		}
	}

	public GuacheraEntity getGuacheraById(Long id) {
		return em.find(GuacheraEntity.class, id);
	}
	
	public RazaEntity getRazaById(Long id) {
		return em.find(RazaEntity.class, id);
	}
	
	public PartoEntity getPartoById(Long id) {
		return em.find(PartoEntity.class, id);
	}

	public HistoricoEnfermedadEntity agregarHistoricoEnfermedad(HistoricoEnfermedadEntity historicoEnfermedadEntity)
			throws PersistenciaException {

		try {
			HistoricoEnfermedadEntity entity = em.merge(historicoEnfermedadEntity);
			em.flush();
			return historicoEnfermedadEntity;
		} catch (PersistenceException e) {
			throw new PersistenciaException(
					"No se pudo agregar el registro histórico de enfermedad: " + e.getMessage(), e);
		} finally {

		}
	}

	public List<HistoricoEnfermedadEntity> seleccionarHistoricoEnfermedades() throws PersistenciaException {
		try {

			String query = "Select e from HistoricoEnfermedadEntity e ";

			List<HistoricoEnfermedadEntity> resultList = (List<HistoricoEnfermedadEntity>) em
					.createQuery(query, HistoricoEnfermedadEntity.class).getResultList();
			return resultList;
		} catch (PersistenceException e) {
			throw new PersistenciaException("No se pudo hacer la consulta." + e.getMessage(), e);
		}
	}

}
