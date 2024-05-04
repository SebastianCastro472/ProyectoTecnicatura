package com.capa2LogicaNegocio;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import com.capa1Clases.HistoricoEnfermedad;
import com.capa1Clases.Ternera;
import com.capa3Persistencia.dao.HistoricoEnfermedadDAO;
import com.capa3Persistencia.dao.TerneraDAO;
import com.capa3Persistencia.entities.HistoricoEnfermedadEntity;
import com.capa3Persistencia.entities.TerneraEntity;
import com.capa3Persistencia.exception.PersistenciaException;


@Stateless
@LocalBean

public class GestionHistoricoEnfermedadService {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@EJB
	HistoricoEnfermedadDAO HistoricoEnfermedadPersistenciaDAO;
	
	

	public HistoricoEnfermedad fromHistoricoEnfermedadEntity(HistoricoEnfermedadEntity e) {
		HistoricoEnfermedad historicoEnfermedad=new HistoricoEnfermedad();
		historicoEnfermedad.setIdTernera(e.getIdTernera());
		historicoEnfermedad.setId(e.getId());
		historicoEnfermedad.setNombre(e.getNombre());
		historicoEnfermedad.setSeveridad(e.getSeveridad());
		historicoEnfermedad.setVariante(e.getVariante());
		historicoEnfermedad.setFechaRegistro(e.getFechaRegistro());
		return historicoEnfermedad;
	}
	public HistoricoEnfermedadEntity toHistoricoEnfermedadEntity(HistoricoEnfermedad e) {
		HistoricoEnfermedadEntity historicoEnfermedadEntity=new HistoricoEnfermedadEntity();
		historicoEnfermedadEntity.setIdTernera(e.getIdTernera());
		historicoEnfermedadEntity.setId(e.getId());
		historicoEnfermedadEntity.setNombre(e.getNombre());
		historicoEnfermedadEntity.setSeveridad(e.getSeveridad());
		historicoEnfermedadEntity.setVariante(e.getVariante());
		historicoEnfermedadEntity.setFechaRegistro(e.getFechaRegistro());
		return historicoEnfermedadEntity;
	}
	


	
	// servicios para capa de Presentacion


	public List<HistoricoEnfermedad> seleccionarHistoricoEnfermedad() throws PersistenciaException {
		//buscamos todos los  objetos UsuarioEntity
		List<HistoricoEnfermedadEntity> listaHistoricoEnfermedadEntity = HistoricoEnfermedadPersistenciaDAO.buscarHistoricos();
		
		List<HistoricoEnfermedad> listaHistoricoEnfermedad=new ArrayList<HistoricoEnfermedad>();
		//recorremos listaUsuarioEntity y vamos populando listaUsuario (haciendo la conversion requerída)
		for (HistoricoEnfermedadEntity HistoricoEnfermedadEntity_1 : listaHistoricoEnfermedadEntity) {
			listaHistoricoEnfermedad.add(fromHistoricoEnfermedadEntity(HistoricoEnfermedadEntity_1));
		}
		return listaHistoricoEnfermedad;
	}


	public List<HistoricoEnfermedad> seleccionarHistoricoEnfermedad(String criterioNombre,String criterioNickname,String criterioRol) throws PersistenciaException {
		//buscamos usuarios segun criterio indicado
		List<HistoricoEnfermedadEntity> listaHistoricoEnfermedadEntity = HistoricoEnfermedadPersistenciaDAO.seleccionarHistoricos(criterioNombre,criterioNickname,criterioRol);
		//lista para devolver la seleccion de usuarios
		List<HistoricoEnfermedad> listaHistoricoEnfermedad=new ArrayList<HistoricoEnfermedad>();
		//recorremos listaUsuarioEntity y vamos populando listaUsuarios (haciendo la conversion requerida)
		for (HistoricoEnfermedadEntity HistoricoEnfermedadEntity : listaHistoricoEnfermedadEntity) {
			listaHistoricoEnfermedad.add(fromHistoricoEnfermedadEntity(HistoricoEnfermedadEntity));
		}
		return listaHistoricoEnfermedad;
		
	}
	
	
	public HistoricoEnfermedad buscarHistoricoEnfermedadEntity(Long id) {
		HistoricoEnfermedadEntity e = HistoricoEnfermedadPersistenciaDAO.buscarHistorico(id);
		return fromHistoricoEnfermedadEntity(e);
	}

	public HistoricoEnfermedad buscarHistoricoEnfermedad(Long id2) {
		HistoricoEnfermedadEntity e = HistoricoEnfermedadPersistenciaDAO.buscarHistorico(id2);
		return fromHistoricoEnfermedadEntity(e);
	}
	
	public HistoricoEnfermedad agregarHistoricoEnfermedad(HistoricoEnfermedad HistoricoEnfermedadSeleccionado) throws PersistenciaException   {
		HistoricoEnfermedadEntity e = HistoricoEnfermedadPersistenciaDAO.agregarHistorico(toHistoricoEnfermedadEntity(HistoricoEnfermedadSeleccionado));
		return fromHistoricoEnfermedadEntity(e);
	}

	public void actualizarHistoricoEnfermedad(HistoricoEnfermedad HistoricoEnfermedadSeleccionado) throws PersistenciaException   {
		HistoricoEnfermedadEntity e = HistoricoEnfermedadPersistenciaDAO.modificarHistorico(toHistoricoEnfermedadEntity(HistoricoEnfermedadSeleccionado));
	}
	
	public void borrarHistoricoEnfermedad(HistoricoEnfermedad HistoricoEnfermedadSeleccionado) throws PersistenciaException   {
		HistoricoEnfermedadEntity e = HistoricoEnfermedadPersistenciaDAO.borrarHistorico(toHistoricoEnfermedadEntity(HistoricoEnfermedadSeleccionado));
	}
}