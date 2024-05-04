package com.capa1presentacion;

import javax.annotation.PostConstruct;
import java.io.Serializable;

import javax.inject.Inject;
import javax.inject.Named;

import com.capa1Clases.Alimento;
import com.capa2LogicaNegocio.GestionAlimentoService;
import com.capa3Persistencia.exception.PersistenciaException;
import com.utils.ExceptionsTools;
import javax.enterprise.context.SessionScoped; //JEE8
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

//import javax.faces.bean.ManagedBean;
//import javax.faces.bean.SessionScoped;

//@ManagedBean(name="alimento")

@Named(value = "gestionAlimento") // JEE8
@SessionScoped // JEE8
public class GestionAlimento implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	GestionAlimentoService persistenciaBean;

	GestionAlimentoService gestionAlimentoService;
	private Long id;
	private String modalidad = "insert";
	private boolean modoEdicion = false;
	private boolean modoNickname = false;

	private Alimento alimentoSeleccionado;

	public GestionAlimento() {
		super();
	}

	@PostConstruct
	public void init() {
		id = null;
		alimentoSeleccionado = new Alimento();
	}

	public void preRenderViewListener() {

		if (id != null) {
			alimentoSeleccionado = persistenciaBean.buscarAlimento(id);
		} else {
			alimentoSeleccionado = new Alimento();
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
		if (alimentoSeleccionado.getIdAlimento() == null) {

			Alimento alimentoNuevo;

			try {
				alimentoNuevo = (Alimento) persistenciaBean.agregarAlimento(alimentoSeleccionado);
				this.id = alimentoNuevo.getIdAlimento();

				alimentoSeleccionado = new Alimento();
				// mensaje de actualizacion correcta
				FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Se ha agregado un nuevo Alimento",
						"");
				FacesContext.getCurrentInstance().addMessage(null, facesMsg);

				this.modalidad = "view";

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

		} else if (modalidad.equals("update")) {

			try {
				persistenciaBean.actualizarAlimento(alimentoSeleccionado);

				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, "Se ha modificado el Alimento.", ""));

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

	public String eliminarAlimento(Long id2) {
		Alimento alimentoParaBorrar;
		try {
			alimentoParaBorrar = (Alimento) persistenciaBean.buscarAlimento(id2);
			alimentoParaBorrar.setBorrado(true);
			persistenciaBean.borrarAlimento(alimentoParaBorrar);

			FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Se ha eliminado un Alimento", "");
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);
			return "listaAlimentos";

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

		return "listaAlimentos";
	}

	public String reset() {
		this.modalidad = "insert";
		alimentoSeleccionado = new Alimento();
		id = null;
		return "AltaAlimento";
	}

	public GestionAlimentoService getPersistenciaBean() {
		return persistenciaBean;
	}

	public void setPersistenciaBean(GestionAlimentoService persistenciaBean) {
		this.persistenciaBean = persistenciaBean;
	}

	public Alimento getAlimentoSeleccionado() {
		return alimentoSeleccionado;
	}

	public void setAlimentoSeleccionado(Alimento alimentoSeleccionado) {
		this.alimentoSeleccionado = alimentoSeleccionado;
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

	public GestionAlimentoService getGestionAlimentoService() {
		return gestionAlimentoService;
	}

	public void setGestionAlimentoService(GestionAlimentoService Alimento) {
		this.gestionAlimentoService = gestionAlimentoService;
	}

	public boolean isModoNickname() {
		return modoNickname;
	}

	public void setModoNickname(boolean modoNickname) {
		this.modoNickname = modoNickname;
	}

}
