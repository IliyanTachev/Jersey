package org.elsys.netprog.rest;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

public class Client {
	static int length;
	static String hash,server_hash;
	static Random random;
	static byte[] guess;
	
	public static String getMd5(byte[] input)
    { 
        try { 
  
            // Static getInstance method is called with hashing MD5 
            MessageDigest md = MessageDigest.getInstance("MD5"); 
  
            // digest() method is called to calculate message digest 
            //  of an input digest() return array of byte 
            byte[] messageDigest = md.digest(input); 
  
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
	
	static void postHTTP() throws ClientProtocolException, IOException, JSONException {
//		JSONBuilder json = new JSONBuilder();
//		json.setInput(guess.toString()); //Base64.getEncoder().encodeToString(guess)
//		json.setClient_hash(hash);
//		
//		String postUrl = "http://localhost:8080/jersey-rest-homework/rest/client";
//		HttpClient httpClient = HttpClientBuilder.create().build();
//		HttpPost post = new HttpPost(postUrl);
//		post.setHeader("Content-type", "application/json");
//		StringEntity postingString = new StringEntity(json.toString());
//		post.setEntity(postingString);
//		HttpResponse  response = httpClient.execute(post);
//		int status = response.getStatusLine().getStatusCode();
//		
//		return status;
		String url = "http://localhost:8080/jersey-rest-homework/game/startGame";
		URL object=new URL(url);

		HttpURLConnection con = (HttpURLConnection) object.openConnection();
		con.setDoOutput(true);
		con.setDoInput(true);
		con.setRequestProperty("Content-Type", "application/json");
		con.setRequestProperty("Accept", "application/json");
		con.setRequestMethod("POST");

		JSONBuilder json = new JSONBuilder();
		json.setLength(5);
		OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
		System.out.println(json.toString());
		wr.write(json.toString());
		wr.flush();

		//display what returns the POST request

		StringBuilder sb = new StringBuilder();  
		int HttpResult = con.getResponseCode(); 
		if (HttpResult == HttpURLConnection.HTTP_OK) {
		    BufferedReader br = new BufferedReader(
		            new InputStreamReader(con.getInputStream(), "utf-8"));
		    String line = null;  
		    while ((line = br.readLine()) != null) {  
		        sb.append(line + "\n");  
		    }
		    br.close();
		    System.out.println("" + sb.toString());  
		} else {
		    System.out.println(con.getResponseMessage());  
		}  
	}
	
	static void getHTTP() throws ClientProtocolException, IOException, ParseException {
//		String getUrl = "http://localhost:8080/jersey-rest-homework/game/games";
//		HttpClient httpClient = HttpClientBuilder.create().build();
//		HttpGet get = new HttpGet(getUrl);
//		get.setHeader("Content-type", "application/json");
//		HttpResponse  response = httpClient.execute(get);
//		
//		JSONBuilder json = (JSONBuilder) response.getEntity();
//		return json;
		String url = "http://localhost:8080/jersey-rest-homework/game/games";
		
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		// optional default is GET
		con.setRequestMethod("GET");

		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'GET' request to URL : " + url);
		System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		//print result
		System.out.println(response.toString());

	}
	
	static void connect() throws ClientProtocolException, IOException, JSONException {

//		JSONBuilder server_response;
//		length = server_response.getLength();
//		server_hash = (String) server_response.getServer_hash();
//		getHTTP();
		postHTTP();
		//int i=0;
		//do {
//			guess = new byte[length];
//			random = new Random();
//			random.nextBytes(guess);
//			hash = getMd5(guess);
//			System.out.println("Hash" + (i+1) + ": " + hash);
//			i++;
		//}while(i<3);
		
//		int status = postHTTP();
//		if(status == 200) {
//			System.out.println("OK");
//		}
//		else if(status == 406) {
//			System.out.println("ERROR");
//		}
		System.out.println("length" + length);
		System.out.println("server_hash" + server_hash);
		
	}
	
	public static void main(String[] args) throws ClientProtocolException, IOException, JSONException {
//		int i=0;
//		while(i<2) {
//			connect();
//			i++;
//		}
		connect();
	}
}
