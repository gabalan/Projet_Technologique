package fr.norips.projettechno.server.sensors;

import org.json.JSONObject;

import fr.norips.projettechno.server.Sensor;
import fr.norips.projettechno.server.Message;

public class GPS implements Sensor{
	private int id;
	private int currentMsgId;
	private Message bufList[] = new Message[MSG_BUFFER_SIZE];
	private String name;
	final static public String className = "GPS";
	
	public GPS(String name) {
		this.name = name;
		currentMsgId = 0;
		id = -1;
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
		obj.put("last_message_id", currentMsgId);
		return obj;
	}

	@Override
	public synchronized Message getLastMsg() {
		// TODO Auto-generated method stub
		return bufList[currentMsgId-1%MSG_BUFFER_SIZE];
	}

	@Override
	public synchronized Message getMsg(int msg_id) {
		if(msg_id < currentMsgId-MSG_BUFFER_SIZE) {
			return bufList[currentMsgId-MSG_BUFFER_SIZE%MSG_BUFFER_SIZE];
		} else if (msg_id > currentMsgId) {
			//TODO Raise exception, msg_id is in the future
			return null;
		} else {
			return bufList[msg_id%MSG_BUFFER_SIZE];
		}
	}

	@Override
	public synchronized void addMsg(Message msg) {
		msg.mId = currentMsgId;
		bufList[currentMsgId++%MSG_BUFFER_SIZE] = msg;
	}

}
