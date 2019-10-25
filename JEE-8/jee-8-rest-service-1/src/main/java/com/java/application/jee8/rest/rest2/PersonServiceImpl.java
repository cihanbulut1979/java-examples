package com.java.application.jee8.rest.rest2;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/person")
public class PersonServiceImpl {

	private static Map<Integer, PersonBean> persons = new HashMap<Integer, PersonBean>();

	@PUT
	@Path("/add")
	public Response addPerson(PersonBean p) {
		ResponseBean response = new ResponseBean();
		if (persons.get(p.getId()) != null) {
			response.setStatus(false);
			response.setMessage("Person Already Exists");
			return Response.status(200).entity(response).build();
		}
		persons.put(p.getId(), p);
		response.setStatus(true);
		response.setMessage("Person created successfully");
		return Response.status(200).entity(response).build();
	}
	
	@POST
	@Path("/update")
	public Response updatePerson(PersonBean p) {
		ResponseBean response = new ResponseBean();
		if (persons.get(p.getId()) == null) {
			response.setStatus(false);
			response.setMessage("Person Not Exists");
			return Response.status(200).entity(response).build();
		}
		persons.put(p.getId(), p);
		response.setStatus(true);
		response.setMessage("Person created successfully");
		return Response.status(200).entity(response).build();
	}

	@DELETE
	@Path("/delete/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deletePerson(@PathParam("id") int id) {
		ResponseBean response = new ResponseBean();
		if (persons.get(id) == null) {
			response.setStatus(false);
			response.setMessage("Person Doesn't Exists");
			return Response.status(200).entity(response).build();
		}
		persons.remove(id);
		response.setStatus(true);
		response.setMessage("Person deleted successfully");
		return Response.status(200).entity(response).build();
	}

	@GET
	@Path("/get/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getPerson(@PathParam("id") int id) {
		PersonBean person = persons.get(id);
		return Response.status(200).entity(person).build();
	}

	@GET
	@Path("/getDummy/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getDummyPerson(@PathParam("id") int id) {
		PersonBean person = new PersonBean();
		person.setAge(99);
		person.setName("Dummy");
		person.setId(id);

		return Response.status(200).entity(person).build();
	}

	@GET
	@Path("/getAll")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllPersons() {
		Set<Integer> ids = persons.keySet();
		PersonBean[] p = new PersonBean[ids.size()];
		int i = 0;
		for (Integer id : ids) {
			p[i] = persons.get(id);
			i++;
		}

		return Response.status(200).entity(p).build();
	}

}
