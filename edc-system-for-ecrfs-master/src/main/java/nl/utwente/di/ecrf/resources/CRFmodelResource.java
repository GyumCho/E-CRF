package nl.utwente.di.ecrf.resources;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;

import nl.utwente.di.ecrf.dao.CRFmodelDao;
import nl.utwente.di.ecrf.model.CRFmodel;

@Path("/models")
public class CRFmodelResource {

	@Context
	UriInfo uriInfo;
	@Context
	Request request;
	
	@GET
	@Produces({MediaType.APPLICATION_JSON })
	public List<CRFmodel> getModels() {
		// TODO: ERASE THIS LINE
		System.out.println("Here I am");
		List<CRFmodel> models = new ArrayList<CRFmodel>();
		models.addAll(CRFmodelDao.instance.getModels().values());
		
		return models;
	}
	
	@GET
	@Produces({MediaType.APPLICATION_JSON })
    @Path("/latest")
    public CRFmodel getLatestCreatedModel() {
		return CRFmodelDao.instance.getLatestCreatedModel();
	}
	
	@GET
	@Produces({MediaType.APPLICATION_JSON })
    @Path("{modelVersion}")
    public CRFmodel getModelById(@PathParam("modelVersion") String modelVersion) {
		return CRFmodelDao.instance.getModel(modelVersion);
	}
	
	@POST
	@Path("{modelVersion}")
	@Consumes({MediaType.APPLICATION_JSON })
	public void createModel(CRFmodel model) {
		CRFmodelDao.instance.addModel(model);
		
	}
	
}
