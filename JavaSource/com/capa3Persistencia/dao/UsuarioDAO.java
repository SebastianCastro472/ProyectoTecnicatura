package com.capa3Persistencia.dao;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

//import com.capa3Persistencia.entities.Usuario;
import com.capa3Persistencia.entities.UsuarioEntity;
import com.capa3Persistencia.exception.PersistenciaException;

/**
 * Session Bean implementation class UsuariosEJBBean
 */
@Stateless
@LocalBean

public class UsuarioDAO {

	@PersistenceContext
	private EntityManager em;

	/**
	 * Default constructor.
	 */
	public UsuarioDAO() {
		super();
	}

	public UsuarioEntity agregarUsuario(UsuarioEntity usuarioParaAgregar) throws PersistenciaException {

		try {
			UsuarioEntity usuario = em.merge(usuarioParaAgregar);
			em.flush();
			return usuario;
		} catch (PersistenceException e) {
			throw new PersistenciaException("No se pudo agregar al usuario." + e.getMessage(), e);
		} finally {

		}
	}

	public UsuarioEntity borrarUsuario(UsuarioEntity usuario) throws PersistenciaException {

		UsuarioEntity usuarioEntity = em.find(UsuarioEntity.class, usuario.getId());
		if (usuarioEntity == null) {
			throw new PersistenciaException("No existe el empleado a borrar. Id=" + usuario.getId());
		}
		try {
			UsuarioEntity Usuario = em.merge(usuario);
			em.flush();
			return usuarioEntity;
		} catch (PersistenceException e) {
			throw new PersistenciaException("No se pudo borrar el empleado. Id=" + usuario.getId());
		}
	}

	public UsuarioEntity modificarUsuario(UsuarioEntity usuarioParaModificar) throws PersistenciaException {

		try {
			UsuarioEntity Usuario = em.merge(usuarioParaModificar);
			em.flush();
			return Usuario;
		} catch (PersistenceException e) {
			throw new PersistenciaException("No se pudo modificar el usuario." + e.getMessage(), e);
		}
	}

	public UsuarioEntity buscarUsuario(Long id) {
		UsuarioEntity Usuario = em.find(UsuarioEntity.class, id);
		return Usuario;
	}

	public List<UsuarioEntity> buscarUsuarios() throws PersistenciaException {
		try {

			String query = "Select e from UsuarioEntity e";
			List<UsuarioEntity> resultList = (List<UsuarioEntity>) em.createQuery(query, UsuarioEntity.class)
					.getResultList();
			return resultList;
		} catch (PersistenceException e) {
			throw new PersistenciaException("No se pudo hacer la consulta." + e.getMessage(), e);
		}

	}

	public List<UsuarioEntity> seleccionarUsuarios(String criterioNombre, String criterioNickname, String criterioRol)
			throws PersistenciaException {
		try {

			String query = "Select e from UsuarioEntity e where e.borrado is null  ";
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
			List<UsuarioEntity> resultList = (List<UsuarioEntity>) em.createQuery(query, UsuarioEntity.class)
					.getResultList();
			return resultList;
		} catch (PersistenceException e) {
			throw new PersistenciaException("No se pudo hacer la consulta." + e.getMessage(), e);
		}
	}

}
