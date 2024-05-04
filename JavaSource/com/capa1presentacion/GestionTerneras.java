package com.capa1presentacion;

import javax.ejb.EJB;
import java.io.Serializable;
import java.util.List;
import javax.inject.Named;
import com.capa1Clases.Ternera;
import com.capa2LogicaNegocio.GestionTerneraService;
import com.capa3Persistencia.exception.PersistenciaException;

//import sun.misc.Perf.GetPerfAction;

import javax.enterprise.context.SessionScoped; //JEE8
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

//import javax.faces.bean.ManagedBean;
//import javax.faces.bean.SessionScoped;

//@ManagedBean(name="usuario")

@Named(value = "gestionTerneras") // JEE8
@SessionScoped // JEE8
public class GestionTerneras implements Serializable {

	@EJB
	GestionTerneraService gestionTerneraService;

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

	private List<Ternera> ternerasSeleccionados;
	private Ternera terneraSeleccionado;

	public GestionTerneras() {
		super();
	}

	// ********Acciones****************************
	public String seleccionarTerneras() throws PersistenciaException {
		
		if (valor.isEmpty() || valor == "" || filtro.isEmpty() || filtro == "") {
			ternerasSeleccionados = gestionTerneraService.seleccionarTerneras1(criterioNombre, criterioNickname,
					criterioRol);
			return "";
	    }else {
		ternerasSeleccionados = gestionTerneraService.seleccionarTerneras(criterioNombre, criterioNickname,
				criterioRol, filtro, valor);
	    
	    return "";
	    }
	}

	public String verAltaTernera() {
		// Navegamos a datos empleado
		return "AltaTernera";
	}

// GETTERS AND SETTERS
	public GestionTerneraService getGestionTerneraService() {
		return gestionTerneraService;
	}

	public void setGestionTerneraService(GestionTerneraService gestionTerneraService) {
		this.gestionTerneraService = gestionTerneraService;
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

	public List<Ternera> getTernerasSeleccionados() {
		return ternerasSeleccionados;
	}

	public void setTernerasSeleccionados(List<Ternera> ternerasSeleccionados) {
		this.ternerasSeleccionados = ternerasSeleccionados;
	}

	public Ternera getTerneraSeleccionado() {
		return terneraSeleccionado;
	}

	public void setTerneraSeleccionado(Ternera terneraSeleccionado) {
		this.terneraSeleccionado = terneraSeleccionado;
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
