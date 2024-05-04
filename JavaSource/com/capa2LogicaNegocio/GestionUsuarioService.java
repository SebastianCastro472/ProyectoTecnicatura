package com.capa2LogicaNegocio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.capa1Clases.Usuario;
import com.capa3Persistencia.dao.UsuarioDAO;
import com.capa3Persistencia.entities.UsuarioEntity;
import com.capa3Persistencia.exception.PersistenciaException;

@Stateless
@LocalBean
public class GestionUsuarioService implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EJB
	UsuarioDAO usuariosPersistenciaDAO;
	
	@PersistenceContext
    private EntityManager entityManager;

	public Usuario fromUsuarioEntity(UsuarioEntity e) {
		Usuario usuario = new Usuario();
		usuario.setId(e.getId().longValue());
		usuario.setNickname(e.getNickname());
		usuario.setNombre(e.getNombre());
		usuario.setApellido(e.getApellido());
		usuario.setEmail(e.getEmail());
		usuario.setClave(e.getClave());
		usuario.setRol(e.getrol());
		usuario.setTitulo(e.getTitulo());
		usuario.setCedula(e.getCedula());
		usuario.setHoras(e.getHoras());
		usuario.setBorrado(e.getBorrado());
		return usuario;
	}

	public UsuarioEntity toUsuarioEntity(Usuario e) {
		UsuarioEntity usuarioEntity = new UsuarioEntity();
		usuarioEntity.setId(e.getId() != null ? e.getId().longValue() : null);
		usuarioEntity.setNickname(e.getNickname());
		usuarioEntity.setNombre(e.getNombre());
		usuarioEntity.setApellido(e.getApellido());
		usuarioEntity.setEmail(e.getEmail());
		usuarioEntity.setClave(e.getClave());
		usuarioEntity.setrol(e.getRol());
		usuarioEntity.setTitulo(e.getTitulo());
		usuarioEntity.setCedula(e.getCedula());
		usuarioEntity.setHoras(e.getHoras());
		usuarioEntity.setBorrado(e.getBorrado());
		return usuarioEntity;
	}

	// servicios para capa de Presentacion

	public List<Usuario> seleccionarUsuarios() throws PersistenciaException {
		// buscamos todos los objetos UsuarioEntity
		List<UsuarioEntity> listaUsuarioEntity = usuariosPersistenciaDAO.buscarUsuarios();

		List<Usuario> listaUsuarios = new ArrayList<Usuario>();
		// recorremos listaUsuarioEntity y vamos populando listaUsuario (haciendo la
		// conversion requerída)
		for (UsuarioEntity usuarioEntity_1 : listaUsuarioEntity) {
			listaUsuarios.add(fromUsuarioEntity(usuarioEntity_1));
		}
		return listaUsuarios;
	}

	public List<Usuario> seleccionarUsuarios(String criterioNombre, String criterioNickname, String criterioRol)
			throws PersistenciaException {
		// buscamos usuarios segun criterio indicado
		List<UsuarioEntity> listaUsuarioEntity = usuariosPersistenciaDAO.seleccionarUsuarios(criterioNombre,
				criterioNickname, criterioRol);
		// lista para devolver la seleccion de usuarios
		List<Usuario> listaUsuarios = new ArrayList<Usuario>();
		// recorremos listaUsuarioEntity y vamos populando listaUsuarios (haciendo la
		// conversion requerida)
		for (UsuarioEntity UsuarioEntity : listaUsuarioEntity) {
			listaUsuarios.add(fromUsuarioEntity(UsuarioEntity));
		}
		return listaUsuarios;

	}
	public List<Usuario> seleccionarUsuarios1(String criterioNombre, String criterioNickname, String criterioRol,String filtro,String valor)
			throws PersistenciaException {
		// buscamos usuarios segun criterio indicado
		List<UsuarioEntity> listaUsuarioEntity = usuariosPersistenciaDAO.seleccionarUsuarios(criterioNombre,
				criterioNickname, criterioRol);
		
		if (filtro != null && valor != null) {
			switch (filtro) {
                case "nickname":
                	listaUsuarioEntity.removeIf(usuario -> !valor.equals(String.valueOf(usuario.getNickname())));
                    break;
                case "nombre": 	         
                	listaUsuarioEntity.removeIf(usuario -> !valor.equals(String.valueOf(usuario.getNombre())));
                    break;
                case "apellido": 	         
                	listaUsuarioEntity.removeIf(usuario -> !valor.equals(String.valueOf(usuario.getApellido())));
                    break;
                case "email": 	         
                	listaUsuarioEntity.removeIf(usuario -> !valor.equals(String.valueOf(usuario.getEmail())));
                    break;
                case "rol": 	         
                	listaUsuarioEntity.removeIf(usuario -> !valor.equals(String.valueOf(usuario.getrol())));
                    break;
                case "titulo": 	         
                	listaUsuarioEntity.removeIf(usuario -> !valor.equals(String.valueOf(usuario.getTitulo())));
                    break;
                case "cedula": 	         
                	listaUsuarioEntity.removeIf(usuario -> !valor.equals(String.valueOf(usuario.getCedula())));
                    break;
                case "horas": 	         
                	listaUsuarioEntity.removeIf(usuario -> !valor.equals(String.valueOf(usuario.getHoras())));
                    break;
                
              
                // Agrega más casos según sea necesario para otros tipos de filtros
                default:
                    // Manejo de filtro no reconocido
                    break;
            	}  
			}
		
		// lista para devolver la seleccion de usuarios
		List<Usuario> listaUsuarios = new ArrayList<Usuario>();
		// recorremos listaUsuarioEntity y vamos populando listaUsuarios (haciendo la
		// conversion requerida)
		for (UsuarioEntity UsuarioEntity : listaUsuarioEntity) {
			listaUsuarios.add(fromUsuarioEntity(UsuarioEntity));
		}
		return listaUsuarios;

	}
	
	public Usuario obtenerUsuarioPorCredenciales(String nickname, String clave) {
	    try {
	        // Query para obtener un usuario por nombre de usuario y contraseña
	        String jpql = "SELECT u FROM UsuarioEntity u WHERE u.nickname = :nickname AND u.clave = :clave";
	        Query query = entityManager.createQuery(jpql, UsuarioEntity.class);
	        query.setParameter("nickname", nickname);
	        query.setParameter("clave", clave);

	        // Obtener el resultado de la consulta como UsuarioEntity
	        UsuarioEntity usuarioEntity = (UsuarioEntity) query.getSingleResult();

	        // Convertir UsuarioEntity a Usuario
	        Usuario usuario = fromUsuarioEntity(usuarioEntity);

	        return usuario;
	    } catch (Exception e) {
	        // Manejar la excepción, por ejemplo, loggearla
	        e.printStackTrace();
	        return null;
	    }
	}

	public Usuario buscarUsuarioEntity(Long id) {
		UsuarioEntity e = usuariosPersistenciaDAO.buscarUsuario(id);
		return fromUsuarioEntity(e);
	}

	public Usuario buscarUsuario(Long i) {
		UsuarioEntity e = usuariosPersistenciaDAO.buscarUsuario(i);
		return fromUsuarioEntity(e);
	}

	public Usuario agregarUsuario(Usuario usuarioSeleccionado) throws PersistenciaException {
		UsuarioEntity e = usuariosPersistenciaDAO.agregarUsuario(toUsuarioEntity(usuarioSeleccionado));
		return fromUsuarioEntity(e);
	}

	public void actualizarUsuario(Usuario usuarioSeleccionado) throws PersistenciaException {
		UsuarioEntity e = usuariosPersistenciaDAO.modificarUsuario(toUsuarioEntity(usuarioSeleccionado));
	}

	public void borrarUsuario(Usuario usuarioSeleccionado) throws PersistenciaException {
		UsuarioEntity e = usuariosPersistenciaDAO.borrarUsuario(toUsuarioEntity(usuarioSeleccionado));
	}
}
