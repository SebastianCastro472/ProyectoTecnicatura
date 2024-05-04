package com.capa1presentacion;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.Date;

import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.PrimeFaces;

import com.capa1Clases.Enfermedad;
import com.capa1Clases.Ternera;
import com.capa2LogicaNegocio.GestionEnfermedadService;
import com.capa2LogicaNegocio.GestionTerneraService;
import com.capa3Persistencia.dao.TerneraDAO;
import com.capa3Persistencia.exception.PersistenciaException;
import com.utils.ExceptionsTools;
import javax.enterprise.context.SessionScoped; //JEE8
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

//import javax.faces.bean.ManagedBean;
//import javax.faces.bean.SessionScoped;

//@ManagedBean(name="usuario")

@Named(value = "gestionEnfermedad") // JEE8
@SessionScoped // JEE8
public class GestionEnfermedad implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	GestionEnfermedadService persistenciaBean;
	
	@Inject
	TerneraDAO terneraDAO = new TerneraDAO();

	GestionEnfermedadService gestionEnfermedadService;
	private Long id;
	private String modalidad = "insert";
	private boolean modoEdicion = false;
	private boolean modoNickname = false;

	private Enfermedad enfermedadSeleccionado;
	
	private Date fechaRegistro;
	public void limpiarCampos() {
		PrimeFaces.current().ajax().update("idDatosEnfermedad");
	    
	}
	

	public GestionEnfermedad() {
		super();
	}

	@PostConstruct
	public void init() {
		enfermedadSeleccionado = new Enfermedad();
	}

	public void preRenderViewListener() {

		if (id != null) {
			enfermedadSeleccionado = persistenciaBean.buscarEnfermedad(id);
		} else {
			enfermedadSeleccionado = new Enfermedad();
		}
		if (modalidad.contentEquals("view")) {
			modoEdicion = true;
			modoNickname = true;
		} else if (modalidad.contentEquals("update")) {
			modoEdicion = true;
			modoNickname = true;
		} else if (modalidad.contentEquals("insert")) {
			modoEdicion = true;
			modoNickname = true;
		} else {
			modoEdicion = true;
			modalidad = "view";
			modoNickname = true;

		}
	}

	public String salvarCambios() {
		if (enfermedadSeleccionado.getId() == null) {
			Date fechaActual = new Date();
			if (terneraDAO.buscarTernera(enfermedadSeleccionado.getIdTernera().longValue()) == null || enfermedadSeleccionado.getIdTernera() == 0 ) {
	            FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Coloca un ID de ternera valido", "");
	            FacesContext.getCurrentInstance().addMessage(null, facesMsg);
	            return null; 
	        }
			
			if (enfermedadSeleccionado.getFechaRegistro() == null) {
		        // Manejar el caso cuando la fecha de inicio es posterior a la fecha actual
		        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Debes ingresar una fecha."));
		        return "";
		    }
			
			if (enfermedadSeleccionado.getFechaRegistro().after(fechaActual)) {
		        // Manejar el caso cuando la fecha de inicio es posterior a la fecha actual
		        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "La fecha no puede ser posterior a la fecha actual."));
		        return "";
		    }

			Enfermedad enfermedadNuevo;
			try {
				enfermedadNuevo = (Enfermedad) persistenciaBean.agregarEnfermedad(enfermedadSeleccionado);
				this.id = enfermedadNuevo.getId();

				enfermedadSeleccionado = new Enfermedad();
				// mensaje de actualizacion correcta
				FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO,
						"Se ha agregado una nueva enfermedad ", "");
				FacesContext.getCurrentInstance().addMessage(null, facesMsg);

				

			} catch (PersistenciaException e) {

				Throwable rootException = ExceptionsTools.getCause(e);
				String msg1 = e.getMessage();
				String msg2 = ExceptionsTools.formatedMsg(rootException);
				// mensaje de actualizacion correcta
				FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, msg1, msg2);
				FacesContext.getCurrentInstance().addMessage(null, facesMsg);

				

				e.printStackTrace();
			}
		} else {
			FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, "No se pudo agregar la enfermedad, verifica que la ternera exista",
					"");
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);
		}
		
		id = null;
		return "AltaEnfermedad";
	}

	public String reset() {
		enfermedadSeleccionado = new Enfermedad();
		this.modalidad = "insert";
		id = null;
		return "AltaEnfermedad";
		
	}

	public GestionEnfermedadService getPersistenciaBean() {
		return persistenciaBean;
	}

	public void setPersistenciaBean(GestionEnfermedadService persistenciaBean) {
		this.persistenciaBean = persistenciaBean;
	}

	public Enfermedad getEnfermedadSeleccionado() {
		return enfermedadSeleccionado;
	}

	public void setEnfermedadSeleccionado(Enfermedad EnfermedadSeleccionado) {
		this.enfermedadSeleccionado = EnfermedadSeleccionado;
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

	public GestionEnfermedadService getGestionEnfermedadService() {
		return gestionEnfermedadService;
	}

	public void setGestionEnfermedadService(GestionEnfermedadService gestionEnfermedadService) {
		this.gestionEnfermedadService = gestionEnfermedadService;
	}

	public boolean isModoNickname() {
		return modoNickname;
	}

	public void setModoNickname(boolean modoNickname) {
		this.modoNickname = modoNickname;
	}

}
