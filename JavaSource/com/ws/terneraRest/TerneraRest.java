package com.ws.terneraRest;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ws.rs.core.Response;

import com.capa1Clases.Alimentacion;
import com.capa1Clases.AlimentacionMovil;
import com.capa1Clases.HistoricoEnfermedad;
import com.capa1Clases.Ternera;
import com.capa1Clases.TerneraMovil;
import com.capa2LogicaNegocio.GestionAlimentacionService;
import com.capa2LogicaNegocio.GestionHistoricoEnfermedadService;
import com.capa2LogicaNegocio.GestionTerneraService;
import com.capa3Persistencia.exception.PersistenciaException;

@Stateless
@LocalBean
public class TerneraRest implements ITerneraRest {

	@EJB
	private GestionTerneraService persistenciaBean;
	
	@EJB
	private GestionAlimentacionService persistenciaBean3;
	
	@EJB
	private GestionHistoricoEnfermedadService persistenciaBean2;

	public TerneraRest() {
		// TODO Auto-generated constructor stub
	}

	

	@Override
	public Response getTernera() {
		try {
			List<Ternera> ret = persistenciaBean.seleccionarTernerasMovil();
			return Response.ok().entity(ret).build();
		} catch (PersistenciaException e) {
			e.printStackTrace();
			return Response.serverError().build();
		}

	}

	@Override
	public Response addTernera(TerneraMovil ternera) {
		try {
			persistenciaBean.agregarTerneraMovil(ternera);
			return Response.ok().build();
		} catch (PersistenciaException e) {
			e.printStackTrace();
			return Response.serverError().build();
		}

	}
	@Override
	public Response addHistorico(HistoricoEnfermedad historico) {
		try {
			persistenciaBean2.agregarHistoricoEnfermedad(historico);
			return Response.ok().build();
		} catch (PersistenciaException e) {
			e.printStackTrace();
			return Response.serverError().build();
		}

	}


	@Override
	public Response getHistorico() {
		try {
			List<HistoricoEnfermedad> ret = persistenciaBean2.seleccionarHistoricoEnfermedad();
			return Response.ok().entity(ret).build();
		} catch (PersistenciaException e) {
			e.printStackTrace();
			return Response.serverError().build();
		}

	}
	@Override
	public Response addAlimentacion(AlimentacionMovil alimentacion) {
		try {
			persistenciaBean3.agregarAlimentacionMovil(alimentacion);
			return Response.ok().build();
		} catch (PersistenciaException e) {
			e.printStackTrace();
			return Response.serverError().build();
		}

	}

}
