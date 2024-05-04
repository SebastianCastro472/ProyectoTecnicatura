package com.capa1presentacion;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.Date;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.capa1Clases.Alimentacion;
import com.capa1Clases.Alimento;
import com.capa1Clases.Peso;
import com.capa2LogicaNegocio.GestionAlimentacionService;
import com.capa2LogicaNegocio.GestionPesoService;
import com.capa3Persistencia.dao.AlimentacionDAO;
import com.capa3Persistencia.dao.PesoDAO;
import com.capa3Persistencia.dao.TerneraDAO;
import com.capa3Persistencia.entities.AlimentacionEntity;
import com.capa3Persistencia.entities.AlimentoEntity;
import com.capa3Persistencia.entities.RegistroPesoEntity;
import com.capa3Persistencia.exception.PersistenciaException;
import com.utils.ExceptionsTools;
import javax.enterprise.context.SessionScoped; //JEE8
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.time.format.DateTimeFormatter;
import java.time.LocalDate;


@Named(value = "gestionPeso") // JEE8
@SessionScoped // JEE8
public class GestionPeso implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	GestionPesoService persistenciaBean;

	@Inject
	PesoDAO pesoDAO = new PesoDAO();

	@Inject
	TerneraDAO terneraDAO = new TerneraDAO();

	private Long id;
	private String modalidad = "insert";
	private boolean modoEdicion = true;

	private Peso pesoSeleccionado;

	public GestionPeso() {
		super();
	}

	@PostConstruct
	public void init() {
		pesoSeleccionado = new Peso();
	}
/*
	public String salvarCambios() throws PersistenciaException {
		try {
		Date fechaActual = new Date();
		if (terneraDAO.buscarTernera(pesoSeleccionado.getIdTernera()) != null) {
			
			if (pesoSeleccionado.getFecha() == null) {
		        // Manejar el caso cuando la fecha de inicio es posterior a la fecha actual
		        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Debes ingresar una fecha."));
		        return "";
		    }
			
			if (pesoSeleccionado.getFecha().after(fechaActual)) {
		        // Manejar el caso cuando la fecha de inicio es posterior a la fecha actual
		        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "La fecha no puede ser posterior a la fecha actual."));
		        return "";
		    }
			try {

			RegistroPesoEntity e = new RegistroPesoEntity();
			e.setTernera(terneraDAO.buscarTernera(pesoSeleccionado.getIdTernera()));
			e.setPeso(pesoSeleccionado.getPeso());
			e.setFecha(pesoSeleccionado.getFecha());

			pesoDAO.agregarRegistroPeso(e);
			FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Se ha agregado un nuevo registro de peso", "");
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);
			}catch (PersistenciaException e) {

				FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, "La ternera seleccionada no existe",
						"");
				FacesContext.getCurrentInstance().addMessage(null, facesMsg);

					this.modalidad = "insert";

					e.printStackTrace();
				}

		} else{
			FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, "La ternera seleccionada no existe",
					"");
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);
		}}catch (Exception e) {

			FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, "La ternera seleccionada no existe",
					"");
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);

				this.modalidad = "insert";

				e.printStackTrace();
			}
		return "";
	}
*/
	public String salvarCambios() throws PersistenciaException {
		Date fechaActual = new Date();
		if (pesoSeleccionado.getIdTernera() == null || pesoSeleccionado.getIdTernera().equals(0L)) {
	        FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Selecciona una ternera válida", "");
	        FacesContext.getCurrentInstance().addMessage(null, facesMsg);
	        return null; // Salir del método si no se selecciona una ternera válida
	    }

	   
	    if (terneraDAO.buscarTernera(pesoSeleccionado.getIdTernera()) == null) {
	        FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "La ternera no existe", "");
	        FacesContext.getCurrentInstance().addMessage(null, facesMsg);
	        return null; // Salir del método si la ternera no existe
	    }

	    // Validar si se ha ingresado un peso válido
	    if ( pesoSeleccionado.getPeso() <= 0) {
	        FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ingresa un peso válido", "");
	        FacesContext.getCurrentInstance().addMessage(null, facesMsg);
	        return null; // Salir del método si no se ingresa un peso válido
	    }

	    // Validar si se ha ingresado una fecha válida
	    if (pesoSeleccionado.getFecha() == null) {
	        FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Selecciona una fecha válida", "");
	        FacesContext.getCurrentInstance().addMessage(null, facesMsg);
	        return null; // Salir del método si no se selecciona una fecha válida
	    }
	    if (pesoSeleccionado.getFecha().after(fechaActual)) {
	        // Manejar el caso cuando la fecha de inicio es posterior a la fecha actual
	        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "La fecha no puede ser posterior a la fecha actual."));
	        return "";
	    }

			try {
			RegistroPesoEntity e = new RegistroPesoEntity();
			e.setTernera(terneraDAO.buscarTernera(pesoSeleccionado.getIdTernera()));
			e.setPeso(pesoSeleccionado.getPeso());
			e.setFecha(pesoSeleccionado.getFecha());

			pesoDAO.agregarRegistroPeso(e);

			FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Se ha agregado un nuevo registro de peso", "");
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);
			}catch (PersistenciaException e) {

				FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, "La ternera y/o el alimento/marca seleccionados no existen",
						"");
				FacesContext.getCurrentInstance().addMessage(null, facesMsg);

					this.modalidad = "insert";

					e.printStackTrace();
				}
			id = null;
			return "AltaPeso";
	}
	public String reset() {
		pesoSeleccionado = new Peso();
		return "";
	}

	public GestionPesoService getPersistenciaBean() {
		return persistenciaBean;
	}

	public void setPersistenciaBean(GestionPesoService persistenciaBean) {
		this.persistenciaBean = persistenciaBean;
	}

	public Peso getPesoSeleccionado() {
		return pesoSeleccionado;
	}

	public void setPesoSeleccionado(Peso pesoSeleccionado) {
		this.pesoSeleccionado = pesoSeleccionado;
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

}
