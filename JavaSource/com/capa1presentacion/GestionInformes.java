package com.capa1presentacion;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;

import java.io.BufferedReader;
import java.io.File;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletResponse;

import org.primefaces.model.file.UploadedFile;
import org.primefaces.model.file.UploadedFileWrapper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.primefaces.event.FileUploadEvent;

import com.capa1Clases.Ternera;
import com.capa2LogicaNegocio.GestionTerneraService;
import com.capa3Persistencia.exception.PersistenciaException;
import com.utils.ExceptionsTools;

import com.capa3Persistencia.dao.DAOInforme;
import com.capa3Persistencia.dao.HistoricoEnfermedadDAO;
import com.capa3Persistencia.entities.HistoricoEnfermedadEntity;
import com.capa1Clases.*;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.enterprise.context.Dependent;
import javax.enterprise.context.SessionScoped;

@SuppressWarnings("unused")
@MultipartConfig
@Named(value = "gestionInformes") // JEE8
@SessionScoped

public class GestionInformes implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	GestionTerneraService persistenciaBean;

	private Long id;
	private String modalidad = "insert";
	private boolean modoEdicion = false;
	private boolean modoNickname = false;
	private Ternera terneraSeleccionado;

	// Informe Costo Alim
	private Date fechaInicioInformeAlimTernera1;
    private Date fechaFinInformeAlimTernera1;
	private String caravanaInformeAlimTernera;
	private String fechaInicioInformeAlimTernera;
	private String fechaFinInformeAlimTernera;
	private String resultadoInformeAlimTernera;

	// Informe Registro Peso
	private Date fechaInicioInformePesoTernera1;
	private Date fechaFinInformePesoTernera1;
	private String caravanaInformePesoTernera;
	private String fechaInicioInformePesoTernera;
	private String fechaFinInformePesoTernera;
	private String resultadoInformePesoTernera;

	private boolean descargable = true;

	private Vector<CostoAlimTernera> listaCostoAlim;

	private Vector<VariacionPesoTernera> listaVariacionPeso;

	@EJB
	DAOInforme miInformeDAO = new DAOInforme();

	private HistoricoEnfermedadEntity historicoEnfermedadSeleccionado;

	private List<HistoricoEnfermedadEntity> historicosEnfermedadSeleccionados;

	private UploadedFile file;
	
	
	public void limpiarCamposAlim() {
        caravanaInformeAlimTernera = null;
        fechaInicioInformeAlimTernera1 = null;
        fechaFinInformeAlimTernera1 = null;
        resultadoInformeAlimTernera = null;
    }
	public void limpiarCamposPeso() {
		caravanaInformePesoTernera = null;
		fechaInicioInformePesoTernera1 = null;
		fechaFinInformePesoTernera1 = null;
		resultadoInformePesoTernera = null;
    }
	

	public GestionInformes() {
		super();
	}

	@PostConstruct
	public void init() {
		terneraSeleccionado = new Ternera();
	}

	public void seleccionarHistoricos() throws PersistenciaException {
		historicosEnfermedadSeleccionados = persistenciaBean.traemeTodo();
	}

	public void informeCostoAlimTernera() throws ParseException, PersistenciaException {
		Date fechaActual = new Date();
    	if (fechaInicioInformeAlimTernera1 == null && fechaFinInformeAlimTernera1 == null) {
    	    
	        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Debe seleccionar al menos una fecha de inicio."));
	        return;
	    }
    	
    	try {
            Long.parseLong(caravanaInformeAlimTernera);
        } catch (NumberFormatException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "El ID de ternera debe ser un número válido."));
            return;
        }

		if (fechaInicioInformeAlimTernera1 != null && fechaFinInformeAlimTernera1 == null) {
			
			if (fechaInicioInformeAlimTernera1.after(fechaActual)) {
		        // Manejar el caso cuando la fecha de inicio es posterior a la fecha actual
		        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "La fecha de inicio no puede ser posterior a la fecha actual."));
		        return;
		    }

			Long idTernera = Long.valueOf(caravanaInformeAlimTernera);
			
			 // Creamos un formato para la fecha
	        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");

	        // Convertimos la fecha a un String
	        String fechaInicioInformeAlimTernera = sdf.format(fechaInicioInformeAlimTernera1);


			listaCostoAlim = miInformeDAO.listaCostoAlimPorTernera1Fecha(idTernera, fechaInicioInformeAlimTernera);
			float contador = 0;
			for (int i = 0; i < listaCostoAlim.size(); i++) {
				float total = listaCostoAlim.get(i).getCantidad() * listaCostoAlim.get(i).getCosto();
				contador = total + contador;
			}

			resultadoInformeAlimTernera = String.valueOf(contador); // Mostrar resultado en el front

			descargable = false;

		} else {
			
			if (fechaInicioInformeAlimTernera1.after(fechaFinInformeAlimTernera1)) {
		        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "La fecha de fin no puede ser anterior a la fecha de inicio."));
		        return;
		    }
			
			if (fechaInicioInformeAlimTernera1.after(fechaActual)) {
		        // Manejar el caso cuando la fecha de inicio es posterior a la fecha actual
		        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "La fecha de inicio no puede ser posterior a la fecha actual."));
		        return;
		    }

			Long idTernera = Long.valueOf(caravanaInformeAlimTernera);
			
			 // Creamos un formato para la fecha
	        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
	        String fechaInicioInformeAlimTernera = sdf.format(fechaInicioInformeAlimTernera1);
	        String fechaFinInformeAlimTernera = sdf.format(fechaFinInformeAlimTernera1);
			

			listaCostoAlim = miInformeDAO.listaCostoAlimPorTernera2Fechas(idTernera, fechaInicioInformeAlimTernera,
					fechaFinInformeAlimTernera);
			float contador = 0;
			for (int i = 0; i < listaCostoAlim.size(); i++) {
				float total = listaCostoAlim.get(i).getCantidad() * listaCostoAlim.get(i).getCosto();
				contador = total + contador;
			}

			resultadoInformeAlimTernera = String.valueOf(contador);
			descargable = false;
		}
	}

	public void informeCostoAlimGuachera() throws ParseException {
		    try {
		    	Date fechaActual = new Date();
		    	if (fechaInicioInformeAlimTernera1 == null && fechaFinInformeAlimTernera1 == null) {
		    	    
			        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Debe seleccionar al menos una fecha de inicio."));
			        return;
			    }
		    	
		    	try {
		            Long.parseLong(caravanaInformeAlimTernera);
		        } catch (NumberFormatException e) {
		            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "El ID de ternera debe ser un número válido."));
		            return;
		        }
				    	
		        	if (fechaInicioInformeAlimTernera1 != null && fechaFinInformeAlimTernera1 != null) {
		        		if (fechaInicioInformeAlimTernera1.after(fechaFinInformeAlimTernera1)) {
					        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "La fecha de fin no puede ser anterior a la fecha de inicio."));
					        return;
					    }
						
						if (fechaInicioInformeAlimTernera1.after(fechaActual)) {
					        // Manejar el caso cuando la fecha de inicio es posterior a la fecha actual
					        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "La fecha de inicio no puede ser posterior a la fecha actual."));
					        return;
					    }

		            Long idGuachera = Long.valueOf(caravanaInformeAlimTernera);
		            
		            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
			        String fechaInicioInformeAlimTernera = sdf.format(fechaInicioInformeAlimTernera1);
			        String fechaFinInformeAlimTernera = sdf.format(fechaFinInformeAlimTernera1);

		            Vector<CostoAlimGuachera> listaGuachera = miInformeDAO.listaCostoAlimPorGuachera2Fechas(idGuachera,
		                    fechaInicioInformeAlimTernera, fechaFinInformeAlimTernera);
		            float contador = 0;
		            for (int i = 0; i < listaGuachera.size(); i++) {
		                float total = listaGuachera.get(i).getCantidad() * listaGuachera.get(i).getCosto();
		                contador = total + contador;
		            }

		            String contadorStr = String.valueOf(contador);
		            resultadoInformeAlimTernera = contadorStr;

		        } else {
		        	if (fechaInicioInformeAlimTernera1.after(fechaActual)) {
				        // Manejar el caso cuando la fecha de inicio es posterior a la fecha actual
				        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "La fecha de inicio no puede ser posterior a la fecha actual."));
				        return;
				    }
		            Long idGuachera = Long.valueOf(caravanaInformeAlimTernera);
		            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
			        String fechaInicioInformeAlimTernera = sdf.format(fechaInicioInformeAlimTernera1);

		            Vector<CostoAlimGuachera> listaGuachera = miInformeDAO.listaCostoAlimPorGuachera1Fecha(idGuachera,
		                    fechaInicioInformeAlimTernera);
		            float contador = 0;
		            for (int i = 0; i < listaGuachera.size(); i++) {
		                float total = listaGuachera.get(i).getCantidad() * listaGuachera.get(i).getCosto();
		                contador = total + contador;
		            }

		            String contadorStr = String.valueOf(contador);
		            resultadoInformeAlimTernera = contadorStr;
		        }
		    } catch (ParseException e) {
		    	FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, "No se encuentran registro disponibles", ""));
		    }
		}

	
	public void informeVariacionPesoTernera() throws ParseException {
		Date fechaActual = new Date();
    	if (fechaInicioInformePesoTernera1 == null && fechaFinInformePesoTernera1 == null) {
    	    
	        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Debe seleccionar al menos una fecha de inicio."));
	        return;
	    }
    	try {
            Long.parseLong(caravanaInformePesoTernera);
        } catch (NumberFormatException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "El ID de ternera debe ser un número válido."));
            return;
        }

		if (fechaInicioInformePesoTernera1 != null && fechaFinInformePesoTernera1 == null) {
			if (fechaInicioInformePesoTernera1.after(fechaActual)) {
		        // Manejar el caso cuando la fecha de inicio es posterior a la fecha actual
		        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "La fecha de inicio no puede ser posterior a la fecha actual."));
		        return;
		    }
			

			Long idTernera = Long.valueOf(caravanaInformePesoTernera);
			 SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
		     String fechaInicioInformePesoTernera = sdf.format(fechaInicioInformePesoTernera1);
		       

			listaVariacionPeso = miInformeDAO.listarVariacionDePeso1Fecha(idTernera, fechaInicioInformePesoTernera);

			if (!listaVariacionPeso.isEmpty()) {
			    Float variacion_ternera = listaVariacionPeso.lastElement().getPeso()
			                    - listaVariacionPeso.firstElement().getPeso();

			    resultadoInformePesoTernera = String.valueOf(variacion_ternera);
			    descargable = false;
			} else {
			    // Manejar el caso cuando listaVariacionPeso está vacía
			    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "La lista de variación de peso está vacía."));
			}

			descargable = false;

		} else {
			if (fechaInicioInformePesoTernera1.after(fechaFinInformePesoTernera1)) {
		        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "La fecha de fin no puede ser anterior a la fecha de inicio."));
		        return;
		    }
			
			if (fechaInicioInformePesoTernera1.after(fechaActual)) {
		        // Manejar el caso cuando la fecha de inicio es posterior a la fecha actual
		        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "La fecha de inicio no puede ser posterior a la fecha actual."));
		        return;
		    }

			Long idTernera = Long.valueOf(caravanaInformePesoTernera);
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
	        String fechaInicioInformePesoTernera = sdf.format(fechaInicioInformePesoTernera1);
	        String fechaFinInformePesoTernera = sdf.format(fechaFinInformePesoTernera1);

			listaVariacionPeso = miInformeDAO.listarVariacionDePeso2Fechas(idTernera, fechaInicioInformePesoTernera,
					fechaFinInformePesoTernera);

			if (!listaVariacionPeso.isEmpty()) {
			    Float variacion_ternera = listaVariacionPeso.lastElement().getPeso()
			                    - listaVariacionPeso.firstElement().getPeso();

			    resultadoInformePesoTernera = String.valueOf(variacion_ternera);
			    descargable = false;
			} else {
			    // Manejar el caso cuando listaVariacionPeso está vacía
			    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "La lista de variación de peso está vacía."));
			}
		}
	}

	public void informeVariacionPesoGuachera() throws ParseException {
		Date fechaActual = new Date();
    	if (fechaInicioInformePesoTernera1 == null && fechaFinInformePesoTernera1 == null) {
    	    
	        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Debe seleccionar al menos una fecha de inicio."));
	        return;
	    }
    	try {
            Long.parseLong(caravanaInformePesoTernera);
        } catch (NumberFormatException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "El ID de ternera debe ser un número válido."));
            return;
        }

		if (fechaInicioInformePesoTernera1 != null && fechaFinInformePesoTernera1 == null) {
			if (fechaInicioInformePesoTernera1.after(fechaActual)) {
		        // Manejar el caso cuando la fecha de inicio es posterior a la fecha actual
		        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "La fecha de inicio no puede ser posterior a la fecha actual."));
		        return;
		    }

			Long idGuachera = Long.valueOf(caravanaInformePesoTernera);
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
	        String fechaInicioInformePesoTernera = sdf.format(fechaInicioInformePesoTernera1);
	        

			
			Vector<VariacionPesoGuachera> listaVariacionPesoGuachera = miInformeDAO
					.listarVariacionDePeso1FechaGuachera(idGuachera, fechaInicioInformePesoTernera);
			float contador = 0;

			List<Long> sabelo = new ArrayList<>();
			if (listaVariacionPesoGuachera == null ) {
	    	    
		        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "La lista esta vacia."));
		        return;
		    }

			for (int i = 0; i < listaVariacionPesoGuachera.size(); i++) {
				if (i > 0) {
					if (!(listaVariacionPesoGuachera.get(i).getIdTernera()
							.equals(listaVariacionPesoGuachera.get(i - 1).getIdTernera()))) {
						sabelo.add(listaVariacionPesoGuachera.get(i).getIdTernera());
					}
				} else
					sabelo.add(listaVariacionPesoGuachera.get(i).getIdTernera());
			}

			for (int i = 0; i < sabelo.size(); i++) {
				Vector<VariacionPesoTernera> lista_aux = miInformeDAO.listarVariacionDePeso1Fecha(sabelo.get(i),
						fechaInicioInformePesoTernera);
				Float variacion_ternera = lista_aux.lastElement().getPeso() - lista_aux.firstElement().getPeso();

				contador = variacion_ternera + contador;
			}

			resultadoInformePesoTernera = String.valueOf(contador); // Mostrar resultado en el front

			descargable = false;

		} else {
			if (fechaInicioInformePesoTernera1.after(fechaFinInformePesoTernera1)) {
		        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "La fecha de fin no puede ser anterior a la fecha de inicio."));
		        return;
		    }
			
			if (fechaInicioInformePesoTernera1.after(fechaActual)) {
		        // Manejar el caso cuando la fecha de inicio es posterior a la fecha actual
		        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "La fecha de inicio no puede ser posterior a la fecha actual."));
		        return;
		    }

			Long idGuachera = Long.valueOf(caravanaInformePesoTernera);
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
	        String fechaInicioInformePesoTernera = sdf.format(fechaInicioInformePesoTernera1);
	        String fechaFinInformePesoTernera = sdf.format(fechaFinInformePesoTernera1);

			Vector<VariacionPesoGuachera> listaVariacionPesoGuachera = miInformeDAO
					.listarVariacionDePeso2FechasGuachera(idGuachera, fechaInicioInformePesoTernera,
							fechaFinInformePesoTernera);
			float contador = 0;

			List<Long> sabelo = new ArrayList<>();

			for (int i = 0; i < listaVariacionPesoGuachera.size(); i++) {
				if (i > 0) {
					if (!(listaVariacionPesoGuachera.get(i).getIdTernera()
							.equals(listaVariacionPesoGuachera.get(i - 1).getIdTernera()))) {
						sabelo.add(listaVariacionPesoGuachera.get(i).getIdTernera());
					}
				} else
					sabelo.add(listaVariacionPesoGuachera.get(i).getIdTernera());
			}

			for (int i = 0; i < sabelo.size(); i++) {
				Vector<VariacionPesoTernera> lista_aux = miInformeDAO.listarVariacionDePeso1Fecha(sabelo.get(i),
						fechaInicioInformePesoTernera);
				Float variacion_ternera = lista_aux.lastElement().getPeso() - lista_aux.firstElement().getPeso();

				contador = variacion_ternera + contador;
			}

			resultadoInformePesoTernera = String.valueOf(contador);
			descargable = false;
		}
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

			Ternera terneraNuevo;
			try {
				terneraNuevo = (Ternera) persistenciaBean.agregarTernera(terneraSeleccionado);
				this.id = terneraNuevo.getIdTernera();

				terneraSeleccionado = new Ternera();
				// mensaje de actualizacion correcta
				FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Se ha agregado una nueva Ternera",
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

	// Getters y setts informe alim ternera
	public String getCaravanaInformeAlimTernera() {
		return caravanaInformeAlimTernera;
	}

	public void setCaravanaInformeAlimTernera(String caravanaInformeAlimTernera) {
		this.caravanaInformeAlimTernera = caravanaInformeAlimTernera;
	}
	

	public Date getFechaInicioInformeAlimTernera1() {
		return fechaInicioInformeAlimTernera1;
	}

	public void setFechaInicioInformeAlimTernera1(Date fechaInicioInformeAlimTernera1) {
		this.fechaInicioInformeAlimTernera1 = fechaInicioInformeAlimTernera1;
	}

	public Date getFechaFinInformeAlimTernera1() {
		return fechaFinInformeAlimTernera1;
	}
	

	public Date getFechaInicioInformePesoTernera1() {
		return fechaInicioInformePesoTernera1;
	}

	public void setFechaInicioInformePesoTernera1(Date fechaInicioInformePesoTernera1) {
		this.fechaInicioInformePesoTernera1 = fechaInicioInformePesoTernera1;
	}

	public Date getFechaFinInformePesoTernera1() {
		return fechaFinInformePesoTernera1;
	}

	public void setFechaFinInformePesoTernera1(Date fechaFinInformePesoTernera1) {
		this.fechaFinInformePesoTernera1 = fechaFinInformePesoTernera1;
	}

	public void setFechaFinInformeAlimTernera1(Date fechaFinInformeAlimTernera1) {
		this.fechaFinInformeAlimTernera1 = fechaFinInformeAlimTernera1;
	}

	public String getFechaInicioInformeAlimTernera() {
		return fechaInicioInformeAlimTernera;
	}

	public void setFechaInicioInformeAlimTernera(String fechaInicioInformeAlimTernera) {
		this.fechaInicioInformeAlimTernera = fechaInicioInformeAlimTernera;
	}

	public String getFechaFinInformeAlimTernera() {
		return fechaFinInformeAlimTernera;
	}

	public void setFechaFinInformeAlimTernera(String fechaFinInformeAlimTernera) {
		this.fechaFinInformeAlimTernera = fechaFinInformeAlimTernera;
	}

	public String getResultadoInformeAlimTernera() {
		return resultadoInformeAlimTernera;
	}

	public void setResultadoInformeAlimTernera(String resultadoInformeAlimTernera) {
		this.resultadoInformeAlimTernera = resultadoInformeAlimTernera;
	}

	public String getCaravanaInformePesoTernera() {
		return caravanaInformePesoTernera;
	}

	public void setCaravanaInformePesoTernera(String caravanaInformePesoTernera) {
		this.caravanaInformePesoTernera = caravanaInformePesoTernera;
	}

	public String getFechaInicioInformePesoTernera() {
		return fechaInicioInformePesoTernera;
	}

	public void setFechaInicioInformePesoTernera(String fechaInicioInformePesoTernera) {
		this.fechaInicioInformePesoTernera = fechaInicioInformePesoTernera;
	}

	public String getFechaFinInformePesoTernera() {
		return fechaFinInformePesoTernera;
	}

	public void setFechaFinInformePesoTernera(String fechaFinInformePesoTernera) {
		this.fechaFinInformePesoTernera = fechaFinInformePesoTernera;
	}

	public String getResultadoInformePesoTernera() {
		return resultadoInformePesoTernera;
	}

	public void setResultadoInformePesoTernera(String resultadoInformePesoTernera) {
		this.resultadoInformePesoTernera = resultadoInformePesoTernera;
	}

	public HistoricoEnfermedadEntity getHistoricoEnfermedadSeleccionado() {
		return historicoEnfermedadSeleccionado;
	}

	public void setHistoricoEnfermedadSeleccionado(HistoricoEnfermedadEntity historicoEnfermedadSeleccionado) {
		this.historicoEnfermedadSeleccionado = historicoEnfermedadSeleccionado;
	}

	public UploadedFile getFile() {
		return file;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
	}

	public List<HistoricoEnfermedadEntity> getHistoricosEnfermedadSeleccionados() {
		return historicosEnfermedadSeleccionados;
	}

	public void setHistoricosEnfermedadSeleccionados(
			List<HistoricoEnfermedadEntity> historicosEnfermedadSeleccionados) {
		this.historicosEnfermedadSeleccionados = historicosEnfermedadSeleccionados;
	}

	public void upload() {
		try {
		if (file != null) {
			this.setFile(file);
			FacesMessage message = new FacesMessage("Succesful", file.getFileName() + " is uploaded.");
			FacesContext.getCurrentInstance().addMessage(null, message);
			procesarDatosDesdeCsv(file);
		}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	public void procesarDatosDesdeCsv(UploadedFile file) {
		if (file != null) {
			try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
				String line;
				while ((line = reader.readLine()) != null) {
					// AquÃ­ puedes procesar cada lÃ­nea del archivo CSV
					String[] data = line.split(",");

					historicoEnfermedadSeleccionado = new HistoricoEnfermedadEntity();
					historicoEnfermedadSeleccionado.setSeveridad(Integer.valueOf(data[0]));
					historicoEnfermedadSeleccionado.setFechaRegistro(data[1]);
					historicoEnfermedadSeleccionado.setIdTernera(Long.valueOf(data[2]));
					historicoEnfermedadSeleccionado.setNombre(data[3]);
					historicoEnfermedadSeleccionado.setVariante(data[4]);
					persistenciaBean.añadirRegistroEnfermedad(historicoEnfermedadSeleccionado);

				}
			}

			catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void descargar() {
		FacesContext fc = FacesContext.getCurrentInstance();
		ExternalContext ec = fc.getExternalContext();

		ec.responseReset();
		ec.setResponseContentType("text/csv");
		ec.setResponseHeader("Content-Disposition", "attachment; filename=\"historico_enfermedades.csv\"");

		try (OutputStream output = ec.getResponseOutputStream()) {
			for (HistoricoEnfermedadEntity historico : historicosEnfermedadSeleccionados) {
				String linea = historico.getId() + ", " + historico.getIdTernera() + ", " + historico.getNombre() + ", "
						+ historico.getVariante() + ", " + historico.getSeveridad() + ", "
						+ historico.getFechaRegistro() + "\n";
				output.write(linea.getBytes());
			}
		} catch (IOException ex) {
			// maneja el error aquÃ­
		}

		fc.responseComplete();
	}

	public void descargarListaComoCSV() {
		HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext()
				.getResponse();
		response.setContentType("text/csv");
		String reportName = "CostoAlimTernera.csv";
		response.setHeader("Content-disposition", "attachment;filename=" + reportName);

		try {
			// Creamos el contenido del archivo CSV
			StringBuilder sb = new StringBuilder();
			sb.append("idTernera");
			sb.append(',');
			sb.append("costo");
			sb.append(',');
			sb.append("cantidad");
			sb.append(',');
			sb.append("total");
			sb.append('\n');

			for (CostoAlimTernera cat : listaCostoAlim) {
				sb.append(cat.getIdTernera());
				sb.append(',');
				sb.append(cat.getCosto());
				sb.append(',');
				sb.append(cat.getCantidad());
				sb.append(',');
				sb.append(cat.getTotal());
				sb.append('\n');
			}

			response.getOutputStream().write(sb.toString().getBytes());
			response.getOutputStream().flush();
			response.getOutputStream().close();
			FacesContext.getCurrentInstance().responseComplete();

		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
}
