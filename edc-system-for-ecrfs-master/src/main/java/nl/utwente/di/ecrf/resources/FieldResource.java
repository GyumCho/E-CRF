package nl.utwente.di.ecrf.resources;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;

import nl.utwente.di.ecrf.dao.FieldDao;
import nl.utwente.di.ecrf.dao.SectionDao;
import nl.utwente.di.ecrf.model.Field;

public class FieldResource {

	@Context
	UriInfo uriInfo;
	@Context
	Request request;
	int sectionid;
	
	public FieldResource(UriInfo uriInfo, Request request, int sectionid) {
		this.uriInfo = uriInfo;
		this.request = request;
		this.sectionid = sectionid;
	}
	
	@GET
	@Produces({MediaType.APPLICATION_JSON })
    public List<Field> getSectionFields(@PathParam("sectionId") int sectionId, @PathParam("moduleId") int moduleId) {
		// IMPORTANT: The fields are only retrieved in combination with a real module-section
		// 			  connection, so this condition exists to ensure that no fields are returned if
		//			  this connection does not exist.
		if(SectionDao.instance.getSection(sectionid).getModuleid() == moduleId) { 
			return FieldDao.instance.getSectionFields(sectionId);
			// ALTERNATIVE: HERE I MAKE USE OF THE RESOURCE'S ATTRIBUTE
			// return FieldDao.instance.getSectionFields(sectionid);
		}
		return null;
		
		// Case when we don't care about the above mentioned
		// return FieldDao.instance.getSectionFields(sectionId);
		// ALTERNATIVE: HERE I MAKE USE OF THE RESOURCE'S ATTRIBUTE
		// return FieldDao.instance.getSectionFields(sectionid);
    }
	
	@GET
	@Produces({MediaType.APPLICATION_JSON })
    @Path("{fieldId}")
    public Field getFieldById(@PathParam("fieldId") int fieldId, @PathParam("moduleId") int moduleId) {
		
		// IMPORTANT: A field is only retrieved when there is a real module-section
		// 			  connection and in combination with a section, so this condition 
		//			  exists to ensure that if that connection does not exist and the
		//			  field is not connected to the section id in the path, 
		//			  nothing will be returned.
		if(SectionDao.instance.getSection(sectionid).getModuleid() == moduleId) { 
			if(FieldDao.instance.getField(fieldId).getSectionid() == sectionid) {
				return FieldDao.instance.getField(fieldId);
				// ALTERNATIVE: HERE I MAKE USE OF THE Path Parameter ATTRIBUTE
				// @PathParam("sectionId") int sectionId <- IN THE METHOD'S INPUTS
				// return FieldDao.instance.getSectionFields(sectionId);
			}
		}
		return null;
		
		// Case when we don't care about the above mentioned
		// return FieldDao.instance.getField(fieldId);
    }
	
}
