package fr.norips.busAPI;

import org.json.JSONException;
import org.json.JSONObject;

public class GPS extends Capteur {

	private double latitude;
	private double longitude;
	public GPS(String json, Bus b) throws JSONException {
		super(json, b);
		latitude = 0;
		longitude = 0;
	}
	
	public GPS(String sender_class,String sender_name,Bus b) {
		super(sender_class,sender_name,b);
		latitude = 0;
		longitude = 0;
	}
	
	public void setLat(double lat){
		latitude = lat;
	}
	public void setLon(double lon){
		longitude = lon;
	}
	public double getLat() {
		return latitude;
	}
	public double getLon() {
		return longitude;
	}
	public void send() throws JSONException {
		JSONObject obj = new JSONObject();
	    obj.put("lat", latitude);
	    obj.put("lng", longitude);

		super.send(obj);
	}

}
