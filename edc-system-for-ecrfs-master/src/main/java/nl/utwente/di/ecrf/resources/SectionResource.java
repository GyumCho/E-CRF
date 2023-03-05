package nl.utwente.di.ecrf.resources;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;

import nl.utwente.di.ecrf.dao.SectionDao;
import nl.utwente.di.ecrf.model.Section;

public class SectionResource {

	@Context
	UriInfo uriInfo;
	@Context
	Request request;
	int moduleid;
	
	public SectionResource(UriInfo uriInfo, Request request, int id) {
		this.uriInfo = uriInfo;
		this.request = request;
		this.moduleid = id;
	}
	
	@GET
	@Produces({MediaType.APPLICATION_JSON })
    public List<Section> getModuleSections() {
        return SectionDao.instance.getModuleSections(moduleid);
    }
	
	@GET
	@Produces({MediaType.APPLICATION_JSON })
    @Path("{sectionId}")
    public Section getSectionById(@PathParam("sectionId") int sectionId) { 
		// IMPORTANT: since sections are only retrieved in combination with a module
		// 			  this condition exists to ensure that any section that is not connected
		//			  to the module id in the path will not be returned.
		return ((SectionDao.instance.getSection(sectionId).getModuleid() == moduleid) ? 
				SectionDao.instance.getSection(sectionId) : null);
    }
	
	@Path("{sectionId}/fields")
	public FieldResource getFieldResource(@PathParam("sectionId") int sectionid) {
	    return new FieldResource(uriInfo, request, sectionid);
	}
	
	
}
