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
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;

import nl.utwente.di.ecrf.dao.CRFDao;
import nl.utwente.di.ecrf.model.CRF;

@Path("/forms")
public class CRFResource {

	@Context
	UriInfo uriInfo;
	@Context
	Request request;
	
	public CRFResource() {
		
	}
	
	public CRFResource(UriInfo uriInfo, Request request) {
		this.uriInfo = uriInfo;
		this.request = request;
	}

	@GET
	@Produces({MediaType.APPLICATION_JSON })
	public List<CRF> getForms() {
		List<CRF> forms = new ArrayList<CRF>();
		MultivaluedMap<String, String> params =
                uriInfo.getQueryParameters();
		String modelVersion = params.getFirst("model_version");
		String doctorId = params.getFirst("doctor_id");
		//System.out.println(modelVersion + " " + doctorId);
		
		if(modelVersion != null) {
			forms.addAll(CRFDao.instance.getFormsByModelVersion(modelVersion));
			return forms;
		}
		else if(doctorId != null) {
			forms.addAll(CRFDao.instance.getFormsByDoctorId(Integer.parseInt(doctorId)));
			return forms;
		}
		
		forms.addAll(CRFDao.instance.getForms().values());
		return forms;
	}
	
	@GET
	@Produces({MediaType.APPLICATION_JSON })
    @Path("{formId}")
    public CRF getFormById(@PathParam("formId") String formId) {
		return CRFDao.instance.getForm(formId);
	}
	 
	@GET 
	@Produces({MediaType.APPLICATION_JSON })
	@Path("{formId}/modules") 
	public CRF getFormsModules(@PathParam("formId") String formId) { 
		return CRFDao.instance.getFormsModules(formId); 
	}
	
	@GET 
	@Produces({MediaType.APPLICATION_JSON })
	@Path("{formId}/modules/{moduleId}") 
	public CRF getModulesAnswers(@PathParam("formId") String formId, @PathParam("moduleId") int moduleId) {
		MultivaluedMap<String, String> params =
                uriInfo.getQueryParameters();
		String date_of_creation = params.getFirst("date");
		return CRFDao.instance.getModulesAnswers(formId, moduleId, date_of_creation); 
	}
	
	@POST
	@Path("{modelVersion}")
	@Consumes({MediaType.APPLICATION_JSON }) 
	public void createForm(CRF form) { 
		CRFDao.instance.addForm(form);
	}
	
}
