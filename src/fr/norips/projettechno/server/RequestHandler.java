package fr.norips.projettechno.server;

import java.util.Date;

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
			} else if(JSONrequest.getString("type").equals("send")) {
				System.out.println("Send");
				
				int sender_id; 
				if(JSONrequest.isNull("sender_id")) {
					sender_id = -1;
				} else {
					sender_id = JSONrequest.getInt("sender_id");
				}
				Message m = new Message();
				if(JSONrequest.isNull("contents")) {
					resp.put("type", "send");
					resp.put("ack",error(401));
				} else {
					m.msg = JSONrequest.getJSONObject("contents");
					Date d = new Date();
					m.timestamp = d.getTime();
					resp = Bus.getInstance().send(sender_id,m);
				}
			}
		}
		
		return resp;
	}
	
	private static JSONObject error(int errorId) {
		JSONObject err = new JSONObject();
		err.put("resp", "error");
		err.put("error_id", errorId);
		return err;
	}
}
