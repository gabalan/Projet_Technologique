package fr.norips.busAPI;



import org.json.JSONException;
import org.json.JSONObject;

public class Capteur {
	private int sender_id;
	private String sender_class;
	private String sender_name;
	private int last_message_id;
	private Bus bus;
	
	/**
	 * Capteur constructor, using json to create a Capteur
	 * @param json JSON string representing capteur
	 * @param b Bus 
	 * @throws JSONException
	 */
	public Capteur(String json, Bus b) throws JSONException {
		JSONObject obj = new JSONObject(json);
		sender_id = obj.getInt("sender_id");
		sender_class = obj.getString("sender_class");
		sender_name = obj.getString("sender_name");
		last_message_id = obj.getInt("last_message_id");
		bus = b;
	}
	
	/**
	 * Capteur constructor. Create a Capteur using his class and name
	 * @param sender_class Name of the class (Gyroscope, GPS...)
	 * @param sender_name Name of the Capteur
	 * @param b Bus
	 */
	public Capteur(String sender_class,String sender_name,Bus b) {
		bus = b;
		this.sender_class = sender_class;
		this.sender_name = sender_name;
		last_message_id = 0;
		register();
	}
	
	/*
	 * Get first message
	 */
	public Message getFirst() {
		return getMessageId(last_message_id);
	}
	
	/*
	 * Register Capteur to the Bus
	 */
	private void register() {
		JSONObject request = new JSONObject();
		request.put("type", "register");
		request.put("sender_class", sender_class);
		request.put("sender_name", sender_name);
		JSONObject response = bus.request(request);
		sender_id = response.getInt("sender_id");
	}
	
	/**
	 * Retrieve Message at ID
	 * @param ID id of the message
	 * @return Message
	 */
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
	
	/**
	 * Get next message
	 * @return Message
	 */
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
	
	/**
	 * Get last message
	 * @return Message
	 */
	public Message getLast() {
		JSONObject request = new JSONObject();
		request.put("type", "get_last");
		request.put("sender_id",sender_id);
		JSONObject response = bus.request(request);
		
		JSONObject ack = response.getJSONObject("ack");
		String resp = ack.getString("resp");
		if(resp.equals("error")) {
			return null;
		}
		
		last_message_id = response.getInt("msg_id");
		Message m = new Message();
		m.mId = last_message_id;
		m.timestamp = response.getLong("date");
		m.msg = response.getJSONObject("contents");
		return m;
	}
	
	/**
	 * Send contents to the Bus
	 * @param contents JSON representing the contents
	 */
	public void send(JSONObject contents) {
		JSONObject request = new JSONObject();
		request.put("type", "send");
		request.put("sender_id", sender_id);
		request.put("contents",contents);
		bus.request(request);
	}
	
	
	
}
