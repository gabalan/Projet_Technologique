package fr.norips.projettechno.server;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.json.*;
public class Bus {
	/** Constructeur privé, singleton */	
	private AtomicInteger counter = new AtomicInteger();
	private List<Sensor> sensors;
	private Bus()
	{
		sensors = new ArrayList<Sensor>();
	}
 
	/** Holder */
	private static class BusHolder
	{		
		/** Instance unique non préinitialisée */
		private final static Bus instance = new Bus();
	}
 
	/** Point d'accès pour l'instance unique du singleton */
	public static Bus getInstance()
	{
		return BusHolder.instance;
	}
	
	
	
	public JSONObject register(Sensor s) {
		int newId = counter.incrementAndGet();
		s.setId(newId);
		synchronized(sensors) {
			sensors.add(s);
		}
		JSONObject resp = new JSONObject();
		resp.put("type", "register");
		resp.put("sender_id", newId);
		JSONObject ack = new JSONObject();
		ack.put("resp", "ok");
		resp.put("ack", ack);
		return resp;
	}
	
	public JSONObject list(String clas, String name) {
		JSONArray results = new JSONArray();
		synchronized(sensors) {
			if(clas == null && name != null) {
				//Filter by class
				for (Sensor sensor : sensors) {
					if(sensor.getClassName().equals(clas))
						results.put(sensor.toJson());
						
				}
				
			} else if (clas != null && name == null) {
				//Filter by name
				for (Sensor sensor : sensors) {
					if(sensor.getName().equals(clas))
						results.put(sensor.toJson());
						
				}
				
			} else if(clas == null && clas == null) {
				//No filter
				for (Sensor sensor : sensors) {
					results.put(sensor.toJson());
				}
				
			}
		}
		JSONObject resp = new JSONObject();
		resp.put("type", "list");
		resp.put("results", results);
		JSONObject ack = new JSONObject();
		ack.put("resp", "ok");
		resp.put("ack", ack);
		return resp;
		
	}
}
