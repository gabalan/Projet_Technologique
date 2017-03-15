package fr.norips.busAPI;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Bus {
	Socket sockBus = null;
	PrintWriter w;
	BufferedReader i; 
	public Bus(String url, int port) throws UnknownHostException, IOException {
		sockBus = new Socket(url, port);
		w = new PrintWriter(sockBus.getOutputStream(),true);
		i = new BufferedReader(new InputStreamReader(sockBus.getInputStream())); 
	}
	
	public Bus(String url) throws UnknownHostException, IOException {
		this(url,7182);
	}
	
	public List<Capteur> list(String sender_class,String sender_name) {
		JSONObject request = new JSONObject();
		request.put("type", "list");
		if(sender_class == null && sender_name == null) {
			;
		} else if(sender_class != null && sender_name == null) {
			request.put("sender_class", sender_class);
		} else if(sender_class == null && sender_name != null) {
			request.put("sender_name", sender_name);
		} else {
			return null;
		}
		List<Capteur> capteurs = new ArrayList<Capteur>();
		JSONObject response = this.request(request);
		System.out.println(response);
		JSONArray arr = response.getJSONArray("results");
		for(int i = 0; i < arr.length();i++) {
			JSONObject cap = arr.getJSONObject(i);
			capteurs.add(new Capteur(cap.toString(),this));
		}
		return capteurs;
	}
	
	JSONObject request(JSONObject obj) {
		try {
			w.println(obj);
			return new JSONObject(i.readLine());
		} catch (JSONException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
}
