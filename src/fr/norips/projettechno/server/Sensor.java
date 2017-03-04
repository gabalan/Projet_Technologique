package fr.norips.projettechno.server;

import org.json.JSONObject;

public interface Sensor {
		public final static int MSG_BUFFER_SIZE = 30;
		public void setId(int id);
		public int getId();
		public String getClassName();
		public String getName();
		public void addMsg(Message msg);
		public Message getLastMsg();
		public Message getMsg(int msg_id);
		public JSONObject toJson();
}
