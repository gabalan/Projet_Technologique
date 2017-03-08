package fr.norips.busAPI;

import org.json.JSONObject;

public class Message {
	public JSONObject msg = null;
	public long timestamp = 0;
	public long mId;
	
	public static JSONObject toJson(Message m) {
		JSONObject o = new JSONObject();
		o.put("msg_id", m.mId);
		o.put("date", m.timestamp);
		o.put("contents", m.msg);
		return o;
	}
}
