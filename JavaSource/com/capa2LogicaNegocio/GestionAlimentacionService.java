package com.capa2LogicaNegocio;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import com.capa1Clases.Alimentacion;
import com.capa1Clases.AlimentacionMovil;
import com.capa1Clases.Enfermedad;
import com.capa3Persistencia.dao.AlimentacionDAO;
import com.capa3Persistencia.dao.AlimentoDAO;
import com.capa3Persistencia.dao.AlimentacionDAO;
import com.capa3Persistencia.entities.AlimentacionEntity;
import com.capa3Persistencia.entities.EnfermedadEntity;
import com.capa3Persistencia.exception.PersistenciaException;



@Stateless
@LocalBean

public class GestionAlimentacionService implements Serializable{


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@EJB
	AlimentacionDAO AlimentacionesPersistenciaDAO;
	
	

	public Alimentacion fromAlimentacionEntity(AlimentacionEntity e) {
	    Alimentacion enf = new Alimentacion();
	    enf.setIdTernera(e.getTernera().getIdTernera().longValue());
	    enf.setNombre(e.getNombre());     
		enf.setFecha(e.getFecha());
	    enf.setCantidad(e.getCantidad());
	    enf.setMarca(e.getMarca());
	    return enf;
	}

	public AlimentacionEntity toAlimentacionEntity(Alimentacion e) {
	    AlimentacionEntity enf = new AlimentacionEntity();
	    enf.setTernera(AlimentacionesPersistenciaDAO.getTerneraById(Long.valueOf(e.getIdTernera())));
	    enf.setNombre(e.getNombre());     
	    enf.setFecha(e.getFecha()); 
	    enf.setCantidad(e.getCantidad());
	    enf.setMarca(e.getMarca());
	    return enf;
	}
	public AlimentacionMovil fromAlimentacionEntityMovil(AlimentacionEntity e) {
		AlimentacionMovil enf = new AlimentacionMovil();
	    enf.setIdTernera(e.getTernera().getIdTernera().longValue());
	    enf.setNombre(e.getNombre());
	    
	    SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yy"); // Formato deseado
	    String fechaFormateada = sdf.format(e.getFecha());
	    enf.setFecha(fechaFormateada);
	    
	    enf.setCantidad(e.getCantidad());
	    enf.setMarca(e.getMarca());
	    return enf;
	}
	
	public AlimentacionEntity toAlimentacionEntityMovil(AlimentacionMovil e) {
        AlimentacionEntity enf = new AlimentacionEntity();
        enf.setTernera(AlimentacionesPersistenciaDAO.getTerneraById(Long.valueOf(e.getIdTernera())));
        enf.setNombre(e.getNombre());        
        SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yy"); 
	    try {
	        Date fechaNacimiento = sdf.parse(e.getFecha());
	        enf.setFecha(fechaNacimiento);
	    } catch (ParseException ex) {
	       
	        ex.printStackTrace();
	        
	    }
        
        enf.setCantidad(e.getCantidad());
        enf.setMarca(e.getMarca());
        return enf;
    }

	public Alimentacion buscarAlimentacionEntity(Long id) {
		AlimentacionEntity e = AlimentacionesPersistenciaDAO.buscarAlimentacion(id);
		return fromAlimentacionEntity(e);
	}

	public Alimentacion buscarAlimentacion(Long id) {
		AlimentacionEntity e = AlimentacionesPersistenciaDAO.buscarAlimentacion(id);
		return fromAlimentacionEntity(e);
	}
	
	public Alimentacion agregarAlimentacion(Alimentacion AlimentacionSeleccionado) throws PersistenciaException   {
		AlimentacionEntity e = new AlimentacionEntity();
		e = toAlimentacionEntity(AlimentacionSeleccionado);
		e.setAlimento(AlimentacionesPersistenciaDAO.buscarAlimNombreMarca(e.getNombre(), e.getMarca()));
		AlimentacionesPersistenciaDAO.agregarAlimentacion(e);		
		return fromAlimentacionEntity(e);
	}
	public AlimentacionMovil agregarAlimentacionMovil(AlimentacionMovil AlimentacionSeleccionado) throws PersistenciaException   {
		AlimentacionEntity e = new AlimentacionEntity();
		e = toAlimentacionEntityMovil(AlimentacionSeleccionado);
		e.setAlimento(AlimentacionesPersistenciaDAO.buscarAlimNombreMarca(e.getNombre(), e.getMarca()));
		AlimentacionesPersistenciaDAO.agregarAlimentacion(e);		
		return fromAlimentacionEntityMovil(e);
	}
}

