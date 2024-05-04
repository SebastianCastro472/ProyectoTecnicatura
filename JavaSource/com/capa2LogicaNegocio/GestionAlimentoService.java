package com.capa2LogicaNegocio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import com.capa1Clases.Alimento;
import com.capa1Clases.Usuario;
import com.capa3Persistencia.dao.AlimentoDAO;
import com.capa3Persistencia.dao.UsuarioDAO;
import com.capa3Persistencia.entities.AlimentoEntity;
import com.capa3Persistencia.entities.UsuarioEntity;
import com.capa3Persistencia.exception.PersistenciaException;

@Stateless
@LocalBean

public class GestionAlimentoService implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EJB
	AlimentoDAO alimentosPersistenciaDAO;

	public Alimento fromAlimentoEntity(AlimentoEntity e) {
		Alimento ali = new Alimento();
		ali.setIdAlimento(e.getIdAlimento());
		ali.setNombre(e.getNombre());
		ali.setMarca(e.getMarca());
		ali.setCosto(e.getCosto());
		ali.setCantidad(e.getCantidad());
		ali.setBorrado(e.getBorrado());
		return ali;
	}

	public AlimentoEntity toAlimentoEntity(Alimento e) {
		AlimentoEntity aliEntity = new AlimentoEntity();
		aliEntity.setIdAlimento(e.getIdAlimento());
		aliEntity.setNombre(e.getNombre());
		aliEntity.setMarca(e.getMarca());
		aliEntity.setCosto(e.getCosto());
		aliEntity.setCantidad(e.getCantidad());
		aliEntity.setBorrado(e.getBorrado());
		return aliEntity;
	}

	// servicios para capa de Presentacion

	public List<Alimento> seleccionarAlimentos() throws PersistenciaException {
		// buscamos todos los objetos UsuarioEntity
		List<AlimentoEntity> listaAlimentoEntity = alimentosPersistenciaDAO.buscarAlimentos();

		List<Alimento> listaAlimentos = new ArrayList<Alimento>();
		// recorremos listaUsuarioEntity y vamos populando listaUsuario (haciendo la
		// conversion requerï¿½da)
		for (AlimentoEntity alimentoEntity_1 : listaAlimentoEntity) {
			listaAlimentos.add(fromAlimentoEntity(alimentoEntity_1));
		}
		return listaAlimentos;
	}

	public List<Alimento> seleccionarAlimentos(String criterioNombre, String criterioMarca)
			throws PersistenciaException {
		// buscamos usuarios segun criterio indicado
		List<AlimentoEntity> listaAlimentoEntity = alimentosPersistenciaDAO.seleccionarAlimentos(criterioNombre,
				criterioMarca);
		// lista para devolver la seleccion de usuarios
		List<Alimento> listaAlimentos = new ArrayList<Alimento>();
		// recorremos listaUsuarioEntity y vamos populando listaUsuarios (haciendo la
		// conversion requerida)
		for (AlimentoEntity AlimentoEntity : listaAlimentoEntity) {
			listaAlimentos.add(fromAlimentoEntity(AlimentoEntity));
		}
		return listaAlimentos;

	}
	
	public List<Alimento> seleccionarAlimentos1(String criterioNombre, String criterioMarca,String filtro,
			String valor)
			throws PersistenciaException {
		// buscamos usuarios segun criterio indicado
		List<AlimentoEntity> listaAlimentoEntity = alimentosPersistenciaDAO.seleccionarAlimentos(criterioNombre,
				criterioMarca);
		
		if (filtro != null && valor != null) {
			switch (filtro) {
                case "marca":
                	listaAlimentoEntity.removeIf(alimento -> !valor.equals(String.valueOf(alimento.getMarca())));
                    break;
                case "costo": 	         
                	listaAlimentoEntity.removeIf(alimento -> Integer.parseInt(valor) != alimento.getCosto());
                    break;
                case "nombre": 	         
                	listaAlimentoEntity.removeIf(alimento -> !valor.equals(String.valueOf(alimento.getNombre())));
                    break;
                case "cantidad": 	         
                	listaAlimentoEntity.removeIf(alimento -> Integer.parseInt(valor) != alimento.getCantidad());
                    break;
            
              
                // Agrega más casos según sea necesario para otros tipos de filtros
                default:
                    // Manejo de filtro no reconocido
                    break;
            	}  
			}
		
		
		
		// lista para devolver la seleccion de usuarios
		List<Alimento> listaAlimentos = new ArrayList<Alimento>();
		// recorremos listaUsuarioEntity y vamos populando listaUsuarios (haciendo la
		// conversion requerida)
		for (AlimentoEntity AlimentoEntity : listaAlimentoEntity) {
			listaAlimentos.add(fromAlimentoEntity(AlimentoEntity));
		}
		return listaAlimentos;

	}

	public Alimento buscarAlimentoEntity(long id) {
		AlimentoEntity e = alimentosPersistenciaDAO.buscarAlimento(id);
		return fromAlimentoEntity(e);
	}

	public Alimento buscarAlimento(Long i) {
		AlimentoEntity e = alimentosPersistenciaDAO.buscarAlimento(i);
		return fromAlimentoEntity(e);
	}

	public Alimento agregarAlimento(Alimento alimentoSeleccionado) throws PersistenciaException {
		AlimentoEntity e = alimentosPersistenciaDAO.agregarAlimento(toAlimentoEntity(alimentoSeleccionado));
		return fromAlimentoEntity(e);
	}

	public void actualizarAlimento(Alimento alimentoSeleccionado) throws PersistenciaException {
		AlimentoEntity e = alimentosPersistenciaDAO.modificarAlimento(toAlimentoEntity(alimentoSeleccionado));
	}

	public void borrarAlimento(Alimento alimentoSeleccionado) throws PersistenciaException {
		AlimentoEntity e = alimentosPersistenciaDAO.borrarAlimento(toAlimentoEntity(alimentoSeleccionado));
	}
}
