package fr.norips.projettechno.server;

import org.json.JSONObject;

public interface Sensor {
		public void setId(int id);
		public int getId();
		public String getClassName();
		public String getName();
		public JSONObject toJson();
}
