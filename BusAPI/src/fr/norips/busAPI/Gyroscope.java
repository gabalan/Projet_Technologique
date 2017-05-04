package fr.norips.busAPI;

import org.json.JSONException;
import org.json.JSONObject;

public class Gyroscope extends Capteur {
	private double x;
	private double y;
	private double z;
	public Gyroscope(String json, Bus b) throws JSONException {
		super(json, b);
		x = 0;
		y = 0;
		z = 0;
	}
	
	public Gyroscope(String sender_class,String sender_name,Bus b) {
		super(sender_class,sender_name,b);
		x = 0;
		y = 0;
		z = 0;
	}
	
	public void setX(double _x){
		x = _x;
	}
	public void setY(double _y){
		y = _y;
	}
	public void setZ(double _z){
		z = _z;
	}
	public double getX() {
		return x;
	}
	public double getY() {
		return y;
	}
	public double getZ() {
		return z;
	}
	public void send() throws JSONException {
		JSONObject obj = new JSONObject();
	    obj.put("x", x);
	    obj.put("y", y);
	    obj.put("z", z);
		super.send(obj);
	}
}
