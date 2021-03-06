package fr.norips.projettechno.server.sensors;

import org.json.JSONObject;

import fr.norips.projettechno.server.Message;

public abstract class Sensor {
	protected final static int MSG_BUFFER_SIZE = 30;
	private int id;
	private int currentMsgId;
	private Message bufList[] = new Message[MSG_BUFFER_SIZE];
	private String name;
	
	/**
	 * Constructor of Sensor. 
	 * @param name of the sensor.
	 */
	protected Sensor(String name) {
		this.name = name;
		currentMsgId = 0;
		id = -1;
	}

	
	public void setId(int id) {
		this.id = id;
		
	}


	public int getId() {
		return id;
	}


	abstract public String getClassName() ;

	public String getName() {
		return name;
	}


	public JSONObject toJson() {
		JSONObject obj = new JSONObject();
		obj.put("sender_id", getId());
		obj.put("sender_class", getClassName());
		obj.put("sender_name", getName());
		obj.put("last_message_id", currentMsgId);
		return obj;
	}

	/**
	 * Gets last message on the bus 
	 * @return bufList 
	 */
	public synchronized Message getLastMsg() {
		// TODO Auto-generated method stub
		if(currentMsgId-1<0) return null;
		return bufList[(currentMsgId-1)%MSG_BUFFER_SIZE];
	}

	/**
	 * Returns message associated to msg_id 
	 * @param msg_id message id.
	 * @return bufList
	 */
	public synchronized Message getMsg(int msg_id) {
		if(msg_id < currentMsgId-MSG_BUFFER_SIZE) {
			return bufList[(currentMsgId-MSG_BUFFER_SIZE)%MSG_BUFFER_SIZE];
		} else if (msg_id > currentMsgId) {
			//TODO Raise exception, msg_id is in the future
			return null;
		} else {
			return bufList[msg_id%MSG_BUFFER_SIZE];
		}
	}

	/**
	 * Concatenates the message passed as parameter to bufList
	 * @param msg
	 * @return bufList
	 */
	public synchronized void addMsg(Message msg) {
		msg.mId = currentMsgId;
		bufList[(currentMsgId++)%MSG_BUFFER_SIZE] = msg;
	}

}
