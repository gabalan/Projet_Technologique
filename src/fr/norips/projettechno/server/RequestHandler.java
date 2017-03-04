package fr.norips.projettechno.server;

import org.json.*;
import fr.norips.projettechno.server.sensors.*;
public class RequestHandler {
	private JSONObject JSONrequest;
	public RequestHandler(String json)  {
		JSONrequest = new JSONObject(json);
	}
	
	public JSONObject execute() {
		JSONObject resp = new JSONObject();
		if(! JSONrequest.isNull("type")) {
			if(JSONrequest.getString("type").equals("register")) {
				System.out.println("Register");
				
				String name = JSONrequest.getString("sender_name");
				String clas = JSONrequest.getString("sender_class");
				Sensor s = null;
				if(clas.equals(GPS.className)) {
					s = new GPS(name);
					resp = Bus.getInstance().register(s);
				}
			} else if(JSONrequest.getString("type").equals("list")) {
				System.out.println("List");
				
				String name; 
				if(JSONrequest.isNull("sender_name")) {
					name = null;
				} else {
					name = JSONrequest.getString("sender_name");
				}

				String clas;
				if(JSONrequest.isNull("sender_class")) {
					clas = null;
				} else {
					clas = JSONrequest.getString("sender_class");
				}
				
				resp = Bus.getInstance().list(clas,name);
			}
		}
		
		return resp;
	}
}
