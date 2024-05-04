package com.capa1presentacion;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.Date;

import javax.inject.Inject;
import javax.inject.Named;
import com.capa1Clases.Ternera;
import com.capa2LogicaNegocio.GestionTerneraService;
import com.capa3Persistencia.exception.PersistenciaException;
import com.utils.ExceptionsTools;
import javax.enterprise.context.SessionScoped; //JEE8
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

@Named(value = "gestionTernera") // JEE8
@SessionScoped // JEE8
public class GestionTernera implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	GestionTerneraService persistenciaBean;

	GestionTerneraService gestionTerneraService;
	private Long id;
	private String modalidad = "insert";
	private boolean modoEdicion = false;
	private boolean modoNickname = false;

	private Ternera terneraSeleccionado;

	public GestionTernera() {
		super();
	}

	@PostConstruct
	public void init() {
		terneraSeleccionado = new Ternera();
	}

	public void preRenderViewListener() {

		if (id != null) {
			terneraSeleccionado = persistenciaBean.buscarTernera(id);
		} else {
			terneraSeleccionado = new Ternera();
		}
		if (modalidad.contentEquals("view")) {
			modoEdicion = false;
			modoNickname = false;
		} else if (modalidad.contentEquals("update")) {
			modoEdicion = true;
			modoNickname = false;
		} else if (modalidad.contentEquals("insert")) {
			modoEdicion = true;
			modoNickname = true;
		} else {
			modoEdicion = false;
			modalidad = "view";
			modoNickname = false;

		}
	}

	public String salvarCambios() {
		if (terneraSeleccionado.getIdTernera() == null) {
			Date fechaActual = new Date();
			
			if (terneraSeleccionado.getFechaNacimiento() == null) {
		   
		        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Debes ingresar una fecha."));
		        return "";
		    }
			if (terneraSeleccionado.getFechaNacimiento().after(fechaActual)) {
		     
		        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "La fecha no puede ser posterior a la fecha actual."));
		        return "";
		    }
			if ( terneraSeleccionado.getPesoAlNacer() <= 0) {
		        FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ingresa un peso válido", "");
		        FacesContext.getCurrentInstance().addMessage(null, facesMsg);
		        return null; 
		    }
			if ( terneraSeleccionado.getIdMadre() <= 0) {
		        FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ingresa un Id de madre válido", "");
		        FacesContext.getCurrentInstance().addMessage(null, facesMsg);
		        return null; 
		    }
			if ( terneraSeleccionado.getIdPadre() <= 0) {
		        FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ingresa un Id de padre válido", "");
		        FacesContext.getCurrentInstance().addMessage(null, facesMsg);
		        return null; 
		    }
			if ( terneraSeleccionado.getCaravanaTambo() == "0") {
		        FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ingresa una Canravana de tambo válida", "");
		        FacesContext.getCurrentInstance().addMessage(null, facesMsg);
		        return null; 
		    }
			if ( terneraSeleccionado.getCaravanaSnig() == "0") {
		        FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ingresa una caravana SNIG válida", "");
		        FacesContext.getCurrentInstance().addMessage(null, facesMsg);
		        return null; 
		    }
			

			Ternera terneraNuevo;
			try {
				terneraNuevo = (Ternera) persistenciaBean.agregarTernera(terneraSeleccionado);
				this.id = terneraNuevo.getIdTernera();

				terneraSeleccionado = new Ternera();
				// Mensaje de actualización correcta
				FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Se ha agregado una nueva Ternera",
						"");
				FacesContext.getCurrentInstance().addMessage(null, facesMsg);

				this.modalidad = "view";

			} catch (PersistenciaException e) {

				Throwable rootException = ExceptionsTools.getCause(e);
				String msg1 = e.getMessage();
				String msg2 = ExceptionsTools.formatedMsg(rootException);
				// Mensaje de actualización incorrecta
				FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "La caravana Tambo debe ser única",
						"");
				FacesContext.getCurrentInstance().addMessage(null, facesMsg);

				this.modalidad = "update";

				e.printStackTrace();
				return "";
			}

		} else if (modalidad.equals("update")) {

			try {
				persistenciaBean.actualizarTernera(terneraSeleccionado);

				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, "Se ha modificado la Ternera.", ""));

			} catch (PersistenciaException e) {

				Throwable rootException = ExceptionsTools.getCause(e);
				String msg1 = e.getMessage();
				String msg2 = ExceptionsTools.formatedMsg(e.getCause());
				// mensaje de actualizacion correcta
				FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, msg1, msg2);
				FacesContext.getCurrentInstance().addMessage(null, facesMsg);

				this.modalidad = "update";

				e.printStackTrace();
			}
		}
		
		return "";
	}

	public String eliminarTernera(Long id2) {
		Ternera terneraParaBorrar;
		try {
			terneraParaBorrar = (Ternera) persistenciaBean.buscarTernera(id2);
			terneraParaBorrar.setBorrado(true);
			persistenciaBean.borrarTernera(terneraParaBorrar);

			FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Se ha eliminado una ternera", "");
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);
			return "listaTerneras";

		} catch (PersistenciaException e) {

			Throwable rootException = ExceptionsTools.getCause(e);
			String msg1 = e.getMessage();
			String msg2 = ExceptionsTools.formatedMsg(rootException);
			// mensaje de actualizacion correcta
			FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, msg1, msg2);
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);

			this.modalidad = "update";

			e.printStackTrace();
		}

		return "listaTerneras";
	}

	public String reset() {
		this.modalidad = "insert";
		terneraSeleccionado = new Ternera();
		id = null;
		return "AltaTernera";
	}

	public GestionTerneraService getPersistenciaBean() {
		return persistenciaBean;
	}

	public void setPersistenciaBean(GestionTerneraService persistenciaBean) {
		this.persistenciaBean = persistenciaBean;
	}

	public Ternera getTerneraSeleccionado() {
		return terneraSeleccionado;
	}

	public void setTerneraSeleccionado(Ternera terneraSeleccionado) {
		this.terneraSeleccionado = terneraSeleccionado;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getModalidad() {
		return modalidad;
	}

	public void setModalidad(String modalidad) {
		this.modalidad = modalidad;
	}

	public boolean isModoEdicion() {
		return modoEdicion;
	}

	public void setModoEdicion(boolean modoEdicion) {
		this.modoEdicion = modoEdicion;
	}

	public GestionTerneraService getGestionTerneraService() {
		return gestionTerneraService;
	}

	public void setGestionTerneraService(GestionTerneraService gestionTerneraService) {
		this.gestionTerneraService = gestionTerneraService;
	}

	public boolean isModoNickname() {
		return modoNickname;
	}

	public void setModoNickname(boolean modoNickname) {
		this.modoNickname = modoNickname;
	}


}
