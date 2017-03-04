package fr.norips.projettechno.server.sensors;

import org.json.JSONObject;

import fr.norips.projettechno.server.Sensor;

public class GPS implements Sensor{
	private int id;
	private String name;
	final static public String className = "GPS";
	
	public GPS(String name) {
		this.name = name;
	}
	
	@Override
	public void setId(int id) {
		this.id = id;
		
	}

	@Override
	public int getId() {
		return id;
	}

	@Override
	public String getClassName() {
		return className;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public JSONObject toJson() {
		JSONObject obj = new JSONObject();
		obj.put("sender_id", id);
		obj.put("sender_class", className);
		obj.put("sender_name", name);
		obj.put("last_message_id", id);
		return obj;
	}

}
