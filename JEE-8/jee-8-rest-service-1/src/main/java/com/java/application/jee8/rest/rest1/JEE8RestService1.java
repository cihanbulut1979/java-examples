package com.java.application.jee8.rest.rest1;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

@Path("/score")
public class JEE8RestService1 {
	@GET
	@Path("/")
	@Produces("application/json")
	public String getScore() {
		String pattern = "{ \"wins\":\"%s\", \"losses\":\"%s\", \"ties\": \"%s\"}";
		return String.format(pattern, Score.WINS, Score.LOSSES, Score.TIES);
	}
	
	@PUT
	@Path("/")
	@Produces("application/json")
	public String update(@QueryParam("wins") int wins, @QueryParam("losses") int losses, @QueryParam("ties") int ties) {
		Score.WINS = wins;
		Score.TIES = ties;
		Score.LOSSES = losses;
		String pattern = "{ \"wins\":\"%s\", \"losses\":\"%s\", \"ties\": \"%s\"}";
		return String.format(pattern, Score.WINS, Score.LOSSES, Score.TIES);

	}

	@POST
	@Path("/wins")
	@Produces("text/plain")
	public int increaseWins() {
		return Score.WINS++;
	}

	@POST
	@Path("/ties")
	@Produces("text/plain")
	public int increaseTies() {
		return Score.WINS++;
	}

	@POST
	@Path("/score/losses")
	@Produces("text/plain")
	public int increaseLosses() {
		return Score.LOSSES++;
	}

	@GET
	@Path("/wins")
	@Produces("text/plain")
	public int getWins() {
		return Score.WINS;
	}

	@GET
	@Path("/losses")
	@Produces("text/plain")
	public int getLosses() {
		return Score.LOSSES;
	}

	@GET
	@Path("/ties")
	@Produces("text/plain")
	public int getTies() {
		return Score.TIES;
	}
}