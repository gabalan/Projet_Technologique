package fr.norips.busAPI;

import org.json.JSONObject;

/**
 * Message containing id, timestamp and payload (msg)
 * @author norips
 *
 */
public class Message {
	public JSONObject msg = null;
	public long timestamp = 0;
	public long mId;
	
	/**
	 * Return a JSONObject from a Message
	 * @param m
	 * @return
	 */
	public static JSONObject toJson(Message m) {
		JSONObject o = new JSONObject();
		o.put("msg_id", m.mId);
		o.put("date", m.timestamp);
		o.put("contents", m.msg);
		return o;
	}
}
