package com.java.application.jee8.rest.rest2.client;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.java.application.jee8.rest.rest2.PersonBean;

public class PersonServiceClient {
	public static void main(String[] args) {
		getUsers();
		createUser();
		getUser();
		updateUser();
		getUser();
		deleteUser();
		getUsers();
	}

	private static void getUsers() {
		Client client = ClientBuilder.newClient();

		String entity = client.target("http://localhost:8080/jee-8-rest-service-1/rest").path("person").path("getAll").request(MediaType.APPLICATION_JSON).header("some-header", "true").get(String.class);

		System.out.println(entity);
	}

	private static void getUser() {
		Client client = ClientBuilder.newClient();

		String entity = client.target("http://localhost:8080/jee-8-rest-service-1/rest").path("person").path("get/1").request(MediaType.APPLICATION_JSON).header("some-header", "true").get(String.class);

		System.out.println(entity);
	}

	private static void createUser() {
		Client client = ClientBuilder.newClient();
		WebTarget webTarget = client.target("http://localhost:8080/jee-8-rest-service-1/rest").path("person").path("add");

		PersonBean person = new PersonBean();

		person.setId(1);
		person.setName("Cihan");
		person.setAge(40);

		Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.put(Entity.entity(person, MediaType.APPLICATION_JSON));

		System.out.println(response.getStatus());
		System.out.println(response.readEntity(String.class));
	}

	private static void updateUser() {
		Client client = ClientBuilder.newClient();
		WebTarget webTarget = client.target("http://localhost:8080/jee-8-rest-service-1/rest").path("person").path("update");

		PersonBean person = new PersonBean();

		person.setId(1);
		person.setName("Cihan");
		person.setAge(40);

		Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.post(Entity.entity(person, MediaType.APPLICATION_JSON));

		String userJson = response.readEntity(String.class);

		System.out.println(response.getStatus());
		System.out.println(userJson);
	}

	private static void deleteUser() {

		Client client = ClientBuilder.newClient();
		WebTarget webTarget = client.target("http://localhost:8080/jee-8-rest-service-1/rest").path("person").path("delete/1");

		Invocation.Builder invocationBuilder = webTarget.request();
		Response response = invocationBuilder.delete();

		System.out.println(response.getStatus());
		System.out.println(response.readEntity(String.class));
	}
}
