package fr.norips.projet.techno;
import org.json.JSONException;
import org.json.JSONObject;
import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.viewer.DefaultWaypoint;
import org.jxmapviewer.viewer.GeoPosition;
import fr.norips.busAPI.*;


public class MapClickListenerSend extends MapClickListener {
	private DefaultWaypoint pos;
	private GPS c;
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
			try {
				c.setLat(location.getLatitude());
				c.setLon(location.getLongitude());
				c.send();
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		pos.setPosition(location);
		viewer.updateUI();
	}
	
	public void setCapteur(GPS _c) {
		c=_c;
	}

}
