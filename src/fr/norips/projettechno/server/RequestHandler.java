package fr.norips.projettechno.server;

import org.json.*;
public class RequestHandler {
	private JSONObject JSONrequest;
	public RequestHandler(String json)  {
		JSONrequest = new JSONObject(json);
	}
	
	public JSONObject execute() {
		JSONObject resp = new JSONObject();
		if(! JSONrequest.isNull("type")) {
			if(JSONrequest.getString("type").equals("register")) {
				System.out.println("Register :");
				
				String name = JSONrequest.getString("sender_name");
				String clas = JSONrequest.getString("sender_class");
				System.out.println("Name : " + name + ", CLASS : " + clas);
				resp.put("type", "register");
				resp.put("sender_id",10);
				
			}
		}
		JSONObject ack = new JSONObject();
		ack.put("resp", "ok");
		resp.put("ack", ack);
		return resp;
	}
}
