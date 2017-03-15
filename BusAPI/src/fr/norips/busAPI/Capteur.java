package fr.norips.busAPI;



import org.json.JSONException;
import org.json.JSONObject;

public class Capteur {
	private int sender_id;
	private String sender_class;
	private String sender_name;
	private int last_message_id;
	private Bus bus;
	public Capteur(String json, Bus b) throws JSONException {
		JSONObject obj = new JSONObject(json);
		sender_id = obj.getInt("sender_id");
		sender_class = obj.getString("sender_class");
		sender_name = obj.getString("sender_name");
		last_message_id = obj.getInt("last_message_id");
		bus = b;
	}
	
	public Capteur(String sender_class,String sender_name,Bus b) {
		bus = b;
		this.sender_class = sender_class;
		this.sender_name = sender_name;
		last_message_id = 0;
		register();
	}
	
	public Message getFirst() {
		return getMessageId(last_message_id);
	}
	
	private void register() {
		JSONObject request = new JSONObject();
		request.put("type", "register");
		request.put("sender_class", sender_class);
		request.put("sender_name", sender_name);
		JSONObject response = bus.request(request);
		sender_id = response.getInt("sender_id");
	}
	
	private Message getMessageId(int ID) {
		JSONObject request = new JSONObject();
		request.put("type", "get");
		request.put("sender_id",sender_id);
		request.put("msg_id",ID);
		JSONObject response = bus.request(request);
		last_message_id = response.getInt("msg_id");
		Message m = new Message();
		m.mId = last_message_id;
		m.timestamp = response.getLong("date");
		m.msg = response.getJSONObject("contents");
		return m;
	}
	
	public Message getNext() {
		JSONObject request = new JSONObject();
		request.put("type", "get");
		request.put("sender_id",sender_id);
		request.put("msg_id",last_message_id);
		JSONObject response = bus.request(request);
		last_message_id = response.getInt("msg_id");
		Message m = new Message();
		m.mId = last_message_id;
		m.timestamp = response.getLong("date");
		m.msg = response.getJSONObject("contents");
		last_message_id++;
		return m;
	}
	
	public Message getLast() {
		JSONObject request = new JSONObject();
		request.put("type", "get_last");
		request.put("sender_id",sender_id);
		JSONObject response = bus.request(request);
		last_message_id = response.getInt("msg_id");
		Message m = new Message();
		m.mId = last_message_id;
		m.timestamp = response.getLong("date");
		m.msg = response.getJSONObject("contents");
		return m;
	}
	
	public void send(JSONObject contents) {
		JSONObject request = new JSONObject();
		request.put("type", "send");
		request.put("sender_id", sender_id);
		request.put("contents",contents);
		bus.request(request);
	}
	
	
	
}
