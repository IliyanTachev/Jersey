package org.elsys.netprog.rest;

import java.io.PrintWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONException;
import org.json.JSONObject;


@Path("/game")
public class GameController {
	@POST
	@Path("/startGame")
	@Consumes(value={MediaType.APPLICATION_JSON})
	public Response startGame(JSONBuilder json) throws URISyntaxException, JSONException{
		int res = json.getLength();
		json.setLength(res + 7);
		JSONObject json1 = new JSONObject(json.toString());
		System.out.println(json1);
		return Response.status(200).entity(json1.toString()).build();
	}
	
	@GET
	@Path("/games")
	@Produces(value={MediaType.APPLICATION_JSON})
	public JSONBuilder getGames() {
		//TODO: Add your code here
		JSONBuilder json = new JSONBuilder();
		json.setServer_hash("gosho");
		json.setLength(3);
		return json;
	}
}
