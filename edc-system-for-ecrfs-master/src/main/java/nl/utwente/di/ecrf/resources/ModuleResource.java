package nl.utwente.di.ecrf.resources;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;

import nl.utwente.di.ecrf.dao.ModuleDao;
import nl.utwente.di.ecrf.model.Mod;

@Path("/modules")
public class ModuleResource {
	
	@Context
	UriInfo uriInfo;
	@Context
	Request request;
	
	@GET
	@Produces({MediaType.APPLICATION_JSON })
	public List<Mod> getModules() {
		System.out.println("Here I am");
		List<Mod> modules = new ArrayList<Mod>();
		modules.addAll(ModuleDao.instance.getModules().values());
		
		return modules;
	}
	
	@GET
	@Produces({MediaType.APPLICATION_JSON })
    @Path("{moduleId}")
    public Mod getById(@PathParam("moduleId") int id) {
		return ModuleDao.instance.getModule(id);
	}
	
	@Path("{moduleId}/sections")
	public SectionResource getSectionResource(@PathParam("moduleId") int moduleid) {
	    return new SectionResource(uriInfo, request, moduleid);
	}
	
}
