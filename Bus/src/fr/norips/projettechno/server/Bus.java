package fr.norips.projettechno.server;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import fr.norips.projettechno.server.sensors.*;

import org.json.*;
public class Bus {
	/** Private constructor, singleton */	
	private AtomicInteger counter = new AtomicInteger();
	private List<Sensor> sensors;
	private Bus()
	{
		sensors = new ArrayList<Sensor>();
	}
 
	/** Holder */
	private static class BusHolder
	{		
		/** Unique instance none Pre-initialized*/
		private final static Bus instance = new Bus();
	}
 
	/** Access point of the unique singleton*/
	public static Bus getInstance()
	{
		return BusHolder.instance;
	}
	
	/**
	 * Sends a connexion request to the bus.
	 * @param s Sensor.
	 * @return resp returns the new assigned ID.
	 */
	
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
	
	/**
	 * Lists all the sensors connected to the bus.
	 * @param clas name of the sensor's class.
	 * @param name name of the sensor.
	 * @return resp returns list af all the senors
	 */
	public JSONObject list(String clas, String name) {
		JSONArray results = new JSONArray();
		synchronized(sensors) {
			if(clas == null && name != null) {
				//Filter by name
				for (Sensor sensor : sensors) {
					if(sensor.getName().equals(clas))
						results.put(sensor.toJson());
						
				}
				
			} else if (clas != null && name == null) {
				//Filter by class
				for (Sensor sensor : sensors) {
					if(sensor.getClassName().equals(clas))
						results.put(sensor.toJsPermet de lister les capteurs présents sur le buson());
						
				}
				
			} else if (clas != null && name != null) {
				//Filter by class and name ????
				for (Sensor sensor : sensors) {
					if(sensor.getClassName().equals(clas) && sensor.getName().equals(clas))
						results.put(sensor.toJson());
						
				}	 
			} else if(clas == null && name == null) {
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
	
	/**
	 * Sends a message to the bus.
	 * @param senderId sensor's ID.
	 * @param m list of the sensor's attributes depending on its class.
	 * @return resp acknowledgement receipt "ok" in case of a success "error" otherwise.
	 */
	public JSONObject send(int senderId, Message m) {
		boolean err = true;
		synchronized(sensors) {
			Sensor s = sensors.get(senderId-1);
			if(s != null) {
				s.addMsg(m);
				err = false;
			}
		}
		JSONObject ack = new JSONObject();
		if(err) {
			ack.put("resp", "error");
		} else {
			ack.put("resp", "ok");
		}
		JSONObject resp = new JSONObject();
		resp.put("type", "send");
		resp.put("ack", ack);
		return resp;
	}
	
	/**
	 * Get last message sent on the bus.
	 * @param senderId sensor's ID.
	 * @return resp Last message + acknowledgement receipt "ok" in case of a success "error" otherwise.
	 */
	public JSONObject get_last(int senderId) {
		JSONObject resp = null;
		synchronized(sensors) {
			Sensor s = sensors.get(senderId-1);
			if(s != null) {
				if(s.getLastMsg() != null)
					resp = Message.toJson(s.getLastMsg());
			}
		}
		JSONObject ack = new JSONObject();
		//Sensor not found
		if(resp == null) {
			resp = new JSONObject();
			ack.put("resp", "error");
			resp.put("ack", ack);
			return resp;
		} else {
			ack.put("resp", "ok");
		}
		resp.put("type", "get_last");
		resp.put("ack",ack);
		return resp;
	}
}
