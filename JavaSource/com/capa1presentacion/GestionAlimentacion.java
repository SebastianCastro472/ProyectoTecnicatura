package com.capa1presentacion;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.capa1Clases.Alimentacion;
import com.capa1Clases.Alimento;
import com.capa1Clases.Ternera;
import com.capa2LogicaNegocio.GestionAlimentacionService;
import com.capa3Persistencia.dao.AlimentacionDAO;
import com.capa3Persistencia.dao.AlimentoDAO;
import com.capa3Persistencia.dao.TerneraDAO;
import com.capa3Persistencia.entities.AlimentacionEntity;
import com.capa3Persistencia.entities.AlimentoEntity;
import com.capa3Persistencia.exception.PersistenciaException;
import com.utils.ExceptionsTools;
import javax.enterprise.context.SessionScoped; //JEE8
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.time.format.DateTimeFormatter;
import java.time.LocalDate;

//import javax.faces.bean.ManagedBean;
//import javax.faces.bean.SessionScoped;

//@ManagedBean(name="usuario")

@Named(value = "gestionAlimentacion") // JEE8
@SessionScoped // JEE8
public class GestionAlimentacion implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	GestionAlimentacionService persistenciaBean;

	GestionAlimentacionService gestionAlimentacionService;
	
	@Inject
	AlimentacionDAO alimentacionDAO = new AlimentacionDAO();
	
	@Inject
	TerneraDAO terneraDAO = new TerneraDAO();
	
	@Inject
	AlimentoDAO alimentoDAO = new AlimentoDAO();
	
	private Long id;
	private String modalidad = "insert";
	private boolean modoEdicion = false;
	private boolean modoNickname = false;
	private Alimentacion AlimentacionSeleccionado;

	public GestionAlimentacion() {
		super();
	}

	@PostConstruct
	public void init() {
		AlimentacionSeleccionado = new Alimentacion();
	}

	public void preRenderViewListener() {

		if (id != null) {
		
			AlimentacionSeleccionado = persistenciaBean.buscarAlimentacion(id);
		} else {
			AlimentacionSeleccionado = new Alimentacion();
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

	public String salvarCambios() throws PersistenciaException {
		Date fechaActual = new Date();
		if ((terneraDAO.buscarTernera(AlimentacionSeleccionado.getIdTernera())!= null)
			&& alimentacionDAO.buscarAlimNombreMarca(AlimentacionSeleccionado.getNombre(),
					AlimentacionSeleccionado.getMarca()) != null) {
			
			if (terneraDAO.buscarTernera(AlimentacionSeleccionado.getIdTernera().longValue()) == null || AlimentacionSeleccionado.getIdTernera() == 0 ) {
	            FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Coloca un ID de ternera valido", "");
	            FacesContext.getCurrentInstance().addMessage(null, facesMsg);
	            return null; 
	        }
			
			if (AlimentacionSeleccionado.getFecha() == null) {
		        // Manejar el caso cuando la fecha de inicio es posterior a la fecha actual
		        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Debes ingresar una fecha."));
		        return "";
		    }
			
			if (AlimentacionSeleccionado.getFecha().after(fechaActual)) {
		        // Manejar el caso cuando la fecha de inicio es posterior a la fecha actual
		        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "La fecha no puede ser posterior a la fecha actual."));
		        return "";
		    }
			
			try {
	        AlimentacionEntity e = new AlimentacionEntity();
	        e.setNombre(AlimentacionSeleccionado.getNombre());
	        e.setMarca(AlimentacionSeleccionado.getMarca());
	        e.setTernera(terneraDAO.buscarTernera(AlimentacionSeleccionado.getIdTernera()));
	        e.setAlimento(alimentacionDAO.buscarAlimNombreMarca(e.getNombre(), e.getMarca()));
	        e.setFecha(AlimentacionSeleccionado.getFecha());
	        e.setCantidad(AlimentacionSeleccionado.getCantidad());
	        
	        alimentacionDAO.agregarAlimentacion(e);
	        
	        FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Se ha agregado un nuevo registro de alimentacion",
					"");
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);
			}catch (PersistenciaException e) {

				FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, "La ternera y/o el alimento/marca seleccionados no existen",
						"");
				FacesContext.getCurrentInstance().addMessage(null, facesMsg);

					this.modalidad = "insert";

					e.printStackTrace();
				}
			
	     
	} else {
		FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, "La ternera y/o el alimento/marca seleccionados no existen",
				"");
		FacesContext.getCurrentInstance().addMessage(null, facesMsg);
		
	}
		id = null;
		return "AltaAlimentacion";
	}

	public String reset() {
		AlimentacionSeleccionado = new Alimentacion();
		this.modalidad = "insert";
		id = null;
		return "AltaAlimentacion";
		
	}

	public GestionAlimentacionService getPersistenciaBean() {
		return persistenciaBean;
	}

	public void setPersistenciaBean(GestionAlimentacionService persistenciaBean) {
		this.persistenciaBean = persistenciaBean;
	}

	public Alimentacion getAlimentacionSeleccionado() {
		return AlimentacionSeleccionado;
	}

	public void setAlimentacionSeleccionado(Alimentacion AlimentacionSeleccionado) {
		this.AlimentacionSeleccionado = AlimentacionSeleccionado;
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

	public GestionAlimentacionService getGestionAlimentacionService() {
		return gestionAlimentacionService;
	}

	public void setGestionAlimentacionService(GestionAlimentacionService gestionAlimentacionService) {
		this.gestionAlimentacionService = gestionAlimentacionService;
	}

	public boolean isModoNickname() {
		return modoNickname;
	}

	public void setModoNickname(boolean modoNickname) {
		this.modoNickname = modoNickname;
	}

}
