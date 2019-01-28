package org.elsys.netprog.rest;

import java.io.PrintWriter;
import java.math.BigInteger;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServlet;
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

import com.sun.jersey.spi.resource.Singleton;


@Path("/game")
@Singleton
public class GameController {
	private static int length = 1;
	private byte[] input;
	private Random random;
	private String hash;
	
	public static String getMd5(String input)
    { 
        try { 
  
            // Static getInstance method is called with hashing MD5 
            MessageDigest md = MessageDigest.getInstance("MD5"); 
  
            // digest() method is called to calculate message digest 
            //  of an input digest() return array of byte 
            byte[] messageDigest = md.digest(input.getBytes()); 
  
            // Convert byte array into signum representation 
            BigInteger no = new BigInteger(1, messageDigest); 
  
            // Convert message digest into hex value 
            String hashtext = no.toString(16); 
            while (hashtext.length() < 32) { 
                hashtext = "0" + hashtext; 
            } 
            return hashtext; 
        }  
  
        // For specifying wrong message digest algorithms 
        catch (NoSuchAlgorithmException e) { 
            throw new RuntimeException(e); 
        } 
    } 
	
	public void randomize() {
		random = new Random();
    	input = new byte[length];
    	random.nextBytes(input);
    	hash = getMd5(new String(input));
	}
       
    /**
     * @see HttpServlet#HttpServlet()
     */
	
    public GameController() {
    	randomize();
    }
	
	@GET
	@Path("/games")
	@Produces(value={MediaType.APPLICATION_JSON})
	public JSONBuilder getGames() {
		randomize();
		//TODO: Add your code here
		JSONBuilder json = new JSONBuilder();
		json.setLength(length);
		json.setServer_hash(hash);
		System.out.println("Hash from the server: " + hash);
		return json;
	}
	
	@POST
	@Path("/startGame")
	@Consumes(value={MediaType.APPLICATION_JSON})
	public Response startGame(JSONBuilder json) throws URISyntaxException, JSONException{
		byte[] requested_input = Base64.getDecoder().decode(json.getClient_bytes());
		System.out.println("Server: " + new String(input));
		System.out.println("Server_hash: " + getMd5(new String(input)));
		System.out.println("Decoded: " + new String(requested_input));
    	String hashClient = json.getClient_hash();
			
		if(hash.equals(hashClient) && Arrays.equals(requested_input, input)){
			randomize();
			return Response.status(200).build();
		}
		else {
			return Response.status(406).build();
		}
		
//		int res = json.getLength();
//		json.setLength(res + 7);
//		JSONObject json1 = new JSONObject(json.toString());
//		System.out.println(json1);
//		return Response.status(200).entity(json1.toString()).build();
	}
}
