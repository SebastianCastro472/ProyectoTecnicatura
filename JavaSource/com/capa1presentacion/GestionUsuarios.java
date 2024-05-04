package com.capa1presentacion;

import javax.ejb.EJB;
import java.io.Serializable;
import java.util.List;
import javax.inject.Named;
import com.capa1Clases.Usuario;
import com.capa2LogicaNegocio.GestionUsuarioService;
import com.capa3Persistencia.exception.PersistenciaException;

//import sun.misc.Perf.GetPerfAction;

import javax.enterprise.context.SessionScoped; //JEE8

//import javax.faces.bean.ManagedBean;
//import javax.faces.bean.SessionScoped;

//@ManagedBean(name="usuario")

@Named(value = "gestionUsuarios") // JEE8
@SessionScoped // JEE8
public class GestionUsuarios implements Serializable {

	@EJB
	GestionUsuarioService gestionUsuarioService;

//	@Inject
//	PersistenciaBean persistenciaBean;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */

	private String criterioNombre;
	private String criterioNickname;
	private String criterioRol;
	private String filtro;
	private String valor;

	private List<Usuario> usuariosSeleccionados;
	private Usuario usuarioSeleccionado;

	public GestionUsuarios() {
		super();
	}

	
public String seleccionarUsuarios() throws PersistenciaException {
		
		if (valor.isEmpty() || valor == "" || filtro.isEmpty() || filtro == "") {
			usuariosSeleccionados = gestionUsuarioService.seleccionarUsuarios(criterioNombre, criterioNickname,
					criterioRol);
			return "";
	    }else {
	    	usuariosSeleccionados = gestionUsuarioService.seleccionarUsuarios1(criterioNombre, criterioNickname,
					criterioRol, filtro, valor);
	    
	    return "";
	    }
	}

	public String verAltaUsuario() {
		// Navegamos a datos empleado
		return "AltaUsuario";
	}

// GETTERS AND SETTERS
	public GestionUsuarioService getGestionUsuarioService() {
		return gestionUsuarioService;
	}

	public void setGestionUsuarioService(GestionUsuarioService gestionUsuarioService) {
		this.gestionUsuarioService = gestionUsuarioService;
	}

	public String getCriterioNombre() {
		return criterioNombre;
	}

	public void setCriterioNombre(String criterioNombre) {
		this.criterioNombre = criterioNombre;
	}

	public String getCriterioNickname() {
		return criterioNickname;
	}

	public void setCriterioNickname(String criterioNickname) {
		this.criterioNickname = criterioNickname;
	}

	public String getCriterioRol() {
		return criterioRol;
	}

	public void setCriterioRol(String criterioRol) {
		this.criterioRol = criterioRol;
	}

	public List<Usuario> getUsuariosSeleccionados() {
		return usuariosSeleccionados;
	}

	public void setUsuariosSeleccionados(List<Usuario> usuariosSeleccionados) {
		this.usuariosSeleccionados = usuariosSeleccionados;
	}

	public Usuario getUsuarioSeleccionado() {
		return usuarioSeleccionado;
	}

	public void setUsuarioSeleccionado(Usuario usuarioSeleccionado) {
		this.usuarioSeleccionado = usuarioSeleccionado;
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

}
