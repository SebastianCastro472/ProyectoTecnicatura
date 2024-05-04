package com.capa2LogicaNegocio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import com.capa1Clases.Ternera;
import com.capa1Clases.TerneraMovil;
import com.capa1Clases.Usuario;
import com.capa3Persistencia.dao.TerneraDAO;
import com.capa3Persistencia.dao.UsuarioDAO;
import com.capa3Persistencia.entities.HistoricoEnfermedadEntity;
import com.capa3Persistencia.entities.TerneraEntity;
import com.capa3Persistencia.entities.UsuarioEntity;
import com.capa3Persistencia.exception.PersistenciaException;

@Stateless
@LocalBean

public class GestionTerneraService implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EJB
	TerneraDAO ternerasPersistenciaDAO;

	public Ternera fromTerneraEntity(TerneraEntity e) {
		Ternera ternera = new Ternera();
		ternera.setIdTernera(e.getIdTernera());
		ternera.setIdMadre(e.getIdMadre());
		ternera.setIdPadre(e.getIdPadre());
		ternera.setRaza(e.getRaza());
		ternera.setTipoDeParto(e.getTipoDeParto());
		ternera.setCaravanaTambo(e.getCaravanaTambo());
		ternera.setCaravanaSnig(e.getCaravanaSnig());
		ternera.setPesoAlNacer(e.getPesoAlNacer());
		ternera.setFechaNacimiento(e.getFechaNacimiento());
		ternera.setId_guachera(e.getGuachera().getIdGuachera().intValue());
		ternera.setBorrado(e.getBorrado());
		return ternera;
	}

	public TerneraEntity toTerneraEntity(Ternera e) {
		TerneraEntity terneraEntity = new TerneraEntity();
		terneraEntity.setIdTernera(e.getIdTernera());
		terneraEntity.setIdMadre(e.getIdMadre());
		terneraEntity.setIdPadre(e.getIdPadre());
		terneraEntity.setRaza(e.getRaza());
		terneraEntity.setTipoDeParto(e.getTipoDeParto());
		terneraEntity.setCaravanaTambo(e.getCaravanaTambo());
		terneraEntity.setCaravanaSnig(e.getCaravanaSnig());
		terneraEntity.setPesoAlNacer(e.getPesoAlNacer());
		terneraEntity.setFechaNacimiento(e.getFechaNacimiento());
		terneraEntity.setGuachera(ternerasPersistenciaDAO.getGuacheraById(Long.valueOf(e.getId_guachera())));
		terneraEntity.setBorrado(e.getBorrado());
		return terneraEntity;
	}
	
	public TerneraMovil fromTerneraEntityMovil(TerneraEntity e) {
		TerneraMovil ternera = new TerneraMovil();
		ternera.setIdTernera(e.getIdTernera());
		ternera.setIdMadre(e.getIdMadre());
		ternera.setIdPadre(e.getIdPadre());
		ternera.setRaza(e.getRaza());
		ternera.setTipoDeParto(e.getTipoDeParto());
		ternera.setCaravanaTambo(e.getCaravanaTambo());
		ternera.setCaravanaSnig(e.getCaravanaSnig());
		ternera.setPesoAlNacer(e.getPesoAlNacer());
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yy"); // Formato deseado
	    String fechaFormateada = sdf.format(e.getFechaNacimiento());
	    ternera.setFechaNacimiento(fechaFormateada);
	    
		ternera.setId_guachera(e.getGuachera().getIdGuachera().intValue());
		ternera.setBorrado(e.getBorrado());
		return ternera;
	}

	public TerneraEntity toTerneraEntityMovil(TerneraMovil e) {
		TerneraEntity terneraEntity = new TerneraEntity();
		terneraEntity.setIdTernera(e.getIdTernera());
		terneraEntity.setIdMadre(e.getIdMadre());
		terneraEntity.setIdPadre(e.getIdPadre());
		terneraEntity.setRaza(e.getRaza());
		terneraEntity.setTipoDeParto(e.getTipoDeParto());
		terneraEntity.setCaravanaTambo(e.getCaravanaTambo());
		terneraEntity.setCaravanaSnig(e.getCaravanaSnig());
		terneraEntity.setPesoAlNacer(e.getPesoAlNacer());
		
	    SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yy"); 
	    try {
	        Date fechaNacimiento = sdf.parse(e.getFechaNacimiento());
	        terneraEntity.setFechaNacimiento(fechaNacimiento);
	    } catch (ParseException ex) {
	       
	        ex.printStackTrace();
	        
	    }
		
		terneraEntity.setGuachera(ternerasPersistenciaDAO.getGuacheraById(Long.valueOf(e.getId_guachera())));
		terneraEntity.setBorrado(e.getBorrado());
		return terneraEntity;
	}
	
	
	
	public List<Ternera> seleccionarTernerasMovil() throws PersistenciaException {
		// buscamos usuarios segun criterio indicado
				List<TerneraEntity> listaTerneraEntity = ternerasPersistenciaDAO.buscarTerneras();
				
				
	
				List<Ternera> listaTerneras = new ArrayList<Ternera>();
				// recorremos listaUsuarioEntity y vamos populando listaUsuarios (haciendo la
				// conversion requerida)
				for (TerneraEntity TerneraEntity : listaTerneraEntity) {
					listaTerneras.add(fromTerneraEntity(TerneraEntity));
				}
				return listaTerneras;

			}
	// servicios para capa de Presentacion

	public List<Ternera> seleccionarTerneras1(String criterioNombre, String criterioNickname, String criterioRol) throws PersistenciaException {
		// buscamos usuarios segun criterio indicado
				List<TerneraEntity> listaTerneraEntity = ternerasPersistenciaDAO.seleccionarTerneras(criterioNombre,
						criterioNickname, criterioRol);
				
				
	
				List<Ternera> listaTerneras = new ArrayList<Ternera>();
				// recorremos listaUsuarioEntity y vamos populando listaUsuarios (haciendo la
				// conversion requerida)
				for (TerneraEntity TerneraEntity : listaTerneraEntity) {
					listaTerneras.add(fromTerneraEntity(TerneraEntity));
				}
				return listaTerneras;

			}

	public List<Ternera> seleccionarTerneras(String criterioNombre, String criterioNickname, String criterioRol, String filtro,
			String valor)
			throws PersistenciaException {
		// buscamos usuarios segun criterio indicado
		List<TerneraEntity> listaTerneraEntity = ternerasPersistenciaDAO.seleccionarTerneras(criterioNombre,
				criterioNickname, criterioRol);
		
		if (filtro != null && valor != null) {
			switch (filtro) {
				case "idTernera":
                	listaTerneraEntity.removeIf(ternera -> Integer.parseInt(valor) != ternera.getIdTernera());
                    break;
                case "idMadre":
                	listaTerneraEntity.removeIf(ternera -> Integer.parseInt(valor) != ternera.getIdMadre());
                    break;
                case "idPadre": 	         
                	listaTerneraEntity.removeIf(ternera -> Integer.parseInt(valor) != ternera.getIdPadre());
                    break;
                case "raza": 	         
                	listaTerneraEntity.removeIf(ternera -> !valor.equals(String.valueOf(ternera.getRaza())));
                    break;
                case "tipoDeParto": 	         
                	listaTerneraEntity.removeIf(ternera -> !valor.equals(String.valueOf(ternera.getTipoDeParto())));
                    break;
                case "caravanaTambo": 	         
                	listaTerneraEntity.removeIf(ternera -> !valor.equals(String.valueOf(ternera.getCaravanaTambo())));
                    break;
                case "caravanaSnig": 	         
                	listaTerneraEntity.removeIf(ternera -> !valor.equals(String.valueOf(ternera.getCaravanaSnig())));
                    break;
                case "pesoAlNacer": 	         
                	listaTerneraEntity.removeIf(ternera -> Integer.parseInt(valor) != ternera.getPesoAlNacer());
                    break;
                case "id_guachera": 	         
                	listaTerneraEntity.removeIf(ternera -> !valor.equals(String.valueOf(ternera.getGuachera().getIdGuachera().intValue())));
                    break;
              
                // Agrega más casos según sea necesario para otros tipos de filtros
                default:
                    // Manejo de filtro no reconocido
                    break;
            	}  
			}
		
		// lista para devolver la seleccion de usuarios
		List<Ternera> listaTerneras = new ArrayList<Ternera>();
		// recorremos listaUsuarioEntity y vamos populando listaUsuarios (haciendo la
		// conversion requerida)
		for (TerneraEntity TerneraEntity : listaTerneraEntity) {
			listaTerneras.add(fromTerneraEntity(TerneraEntity));
		}
		return listaTerneras;

	}

	public Ternera buscarTerneraEntity(Long id) {
		TerneraEntity e = ternerasPersistenciaDAO.buscarTernera(id);
		return fromTerneraEntity(e);
	}

	public Ternera buscarTernera(Long id2) {
		TerneraEntity e = ternerasPersistenciaDAO.buscarTernera(id2);
		return fromTerneraEntity(e);
	}

	public Ternera agregarTernera(Ternera terneraSeleccionado) throws PersistenciaException {
		TerneraEntity e = ternerasPersistenciaDAO.agregarTernera(toTerneraEntity(terneraSeleccionado));
		return fromTerneraEntity(e);
	}
	
	public TerneraMovil agregarTerneraMovil(TerneraMovil terneraSeleccionado) throws PersistenciaException {
		TerneraEntity e = ternerasPersistenciaDAO.agregarTernera(toTerneraEntityMovil(terneraSeleccionado));
		return fromTerneraEntityMovil(e);
	}

	public void actualizarTernera(Ternera terneraSeleccionado) throws PersistenciaException {
		TerneraEntity e = ternerasPersistenciaDAO.modificarTernera(toTerneraEntity(terneraSeleccionado));
	}

	public void borrarTernera(Ternera terneraSeleccionado) throws PersistenciaException {
		TerneraEntity e = ternerasPersistenciaDAO.borrarTernera(toTerneraEntity(terneraSeleccionado));
	}

	public void añadirRegistroEnfermedad(HistoricoEnfermedadEntity historicoEnfermedad) {
		try {
			ternerasPersistenciaDAO.agregarHistoricoEnfermedad(historicoEnfermedad);
		} catch (PersistenciaException e) {
			e.printStackTrace();
		}
	}

	public List<HistoricoEnfermedadEntity> traemeTodo() {
		try {
			return ternerasPersistenciaDAO.seleccionarHistoricoEnfermedades();
		} catch (PersistenciaException e) {
			e.printStackTrace();
			return null;
		}
	}
}
