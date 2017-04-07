package fr.norips.projet.techno;
import org.json.JSONException;
import org.json.JSONObject;
import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.viewer.DefaultWaypoint;
import org.jxmapviewer.viewer.GeoPosition;
import fr.norips.busAPI.*;


public class MapClickListenerSend extends MapClickListener {
	private DefaultWaypoint pos;
	private Capteur c;
	public MapClickListenerSend(JXMapViewer viewer,DefaultWaypoint pos) {
		super(viewer);
		c = null;
		this.pos = pos;
		// TODO Auto-generated constructor stub
	}
	@Override
	public void mapClicked(GeoPosition location) {
		// TODO Auto-generated method stub
		if(c != null) {
			JSONObject obj = new JSONObject();
	        try {
	            obj.put("lat", location.getLatitude());
	            obj.put("lng", location.getLongitude());
	        }catch (JSONException e) {
	            e.printStackTrace();
	            return;
	        }

			c.send(obj);
		}
		pos.setPosition(location);
		viewer.updateUI();
	}
	
	public void setCapteur(Capteur _c) {
		c=_c;
	}

}
