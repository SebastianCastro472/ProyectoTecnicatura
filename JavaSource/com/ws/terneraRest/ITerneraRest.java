package com.ws.terneraRest;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.capa1Clases.Alimentacion;
import com.capa1Clases.AlimentacionMovil;
import com.capa1Clases.HistoricoEnfermedad;
import com.capa1Clases.Ternera;
import com.capa1Clases.TerneraMovil;

@Path("")
public interface ITerneraRest {
	@GET
	@Path("todos")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getTernera();

	@GET
	@Path("todosHistorico")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getHistorico();
	
	@POST
	@Path("agregar")
	@Produces(MediaType.APPLICATION_JSON)
	public Response addTernera(TerneraMovil ternera);
	
	@POST
	@Path("agregarHistorico")
	@Produces(MediaType.APPLICATION_JSON)
	public Response addHistorico(HistoricoEnfermedad historico);
	
	@POST
	@Path("agregarAlimentacion")
	@Produces(MediaType.APPLICATION_JSON)
	public Response addAlimentacion(AlimentacionMovil alimentacion);

	

}