package fr.projet.techno;

import javax.swing.JLabel;
import javax.swing.JTextField;

import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.viewer.DefaultWaypoint;
import org.jxmapviewer.viewer.GeoPosition;
import org.jxmapviewer.viewer.Waypoint;

public class MapClickListenerLabel extends MapClickListener {
	private JTextField lat, lon;
	private DefaultWaypoint pos;
	public MapClickListenerLabel(JXMapViewer viewer, JTextField lat, JTextField lon,DefaultWaypoint pos) {
		super(viewer);
		this.lat = lat;
		this.lon = lon;
		this.pos = pos;
		// TODO Auto-generated constructor stub
	}
	@Override
	public void mapClicked(GeoPosition location) {
		// TODO Auto-generated method stub
		pos.setPosition(location);
		lat.setText(String.valueOf(location.getLatitude()));
		lon.setText(String.valueOf(location.getLongitude()));
		viewer.updateUI();
	}

}
