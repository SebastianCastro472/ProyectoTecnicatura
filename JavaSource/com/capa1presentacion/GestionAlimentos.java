package com.capa1presentacion;

import javax.ejb.EJB;
import java.io.Serializable;
import java.util.List;
import javax.inject.Named;
import com.capa1Clases.Alimento;
import com.capa2LogicaNegocio.GestionAlimentoService;
import com.capa3Persistencia.exception.PersistenciaException;

//import sun.misc.Perf.GetPerfAction;

import javax.enterprise.context.SessionScoped; //JEE8
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

//import javax.faces.bean.ManagedBean;
//import javax.faces.bean.SessionScoped;

//@ManagedBean(name="usuario")

@Named(value = "gestionAlimentos") // JEE8
@SessionScoped // JEE8
public class GestionAlimentos implements Serializable {

	@EJB
	GestionAlimentoService gestionAlimentoService;

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
	private String criterioMarca;
	private String filtro;
	private String valor;

	private List<Alimento> alimentosSeleccionados;
	private Alimento alimentoSeleccionado;

	public GestionAlimentos() {
		super();
	}

	// ********Acciones****************************
	public String seleccionarAlimentos() throws PersistenciaException {
		if (valor.isEmpty() || valor == "" || filtro.isEmpty() || filtro == "") {
			alimentosSeleccionados = gestionAlimentoService.seleccionarAlimentos(criterioNombre, criterioMarca);
			return "";
	    }else {
	    	alimentosSeleccionados = gestionAlimentoService.seleccionarAlimentos1(criterioNombre, criterioMarca, filtro, valor);
	    
	    return "";
	    }
	}
		
	public String verAltaAlimento() {
		// Navegamos a datos empleado
		return "AltaAlimento";
	}

// GETTERS AND SETTERS
	public GestionAlimentoService getGestionAlimentoService() {
		return gestionAlimentoService;
	}

	public void setGestionAlimentoService(GestionAlimentoService gestionAlimentoService) {
		this.gestionAlimentoService = gestionAlimentoService;
	}

	public String getCriterioNombre() {
		return criterioNombre;
	}

	public void setCriterioNombre(String criterioNombre) {
		this.criterioNombre = criterioNombre;
	}

	public String getCriterioMarca() {
		return criterioMarca;
	}

	public void setCriterioMarca(String criterioMarca) {
		this.criterioMarca = criterioMarca;
	}

	public List<Alimento> getAlimentosSeleccionados() {
		return alimentosSeleccionados;
	}

	public void setAlimentosSeleccionados(List<Alimento> alimentosSeleccionados) {
		this.alimentosSeleccionados = alimentosSeleccionados;
	}

	public Alimento getAlimentoSeleccionado() {
		return alimentoSeleccionado;
	}

	public void setAlimentoSeleccionado(Alimento alimentosSeleccionado) {
		this.alimentoSeleccionado = alimentoSeleccionado;
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
