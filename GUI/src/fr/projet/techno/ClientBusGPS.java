package fr.projet.techno;

import java.util.Date;
import java.util.List;

import javax.swing.JTextField;

import org.json.JSONException;
import org.json.JSONObject;
import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.viewer.DefaultWaypoint;
import org.jxmapviewer.viewer.GeoPosition;

import fr.norips.busAPI.Bus;
import fr.norips.busAPI.Capteur;
import fr.norips.busAPI.Message;

public class ClientBusGPS implements Runnable {
	
	private Bus bus;
	DefaultWaypoint waypoint;
	JXMapViewer viewer;
	JTextField lati, longi;
	public ClientBusGPS(Bus _b,DefaultWaypoint _waypoint,JXMapViewer _viewer, JTextField lat, JTextField lon) {
		bus = _b;
		waypoint = _waypoint;
		viewer = _viewer;
		lati = lat;
		longi = lon;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		List<Capteur> l = bus.list("GPS", null);
		Capteur capteur = null;
		try {
			capteur = l.get(0);
		} catch (IndexOutOfBoundsException e) {
			System.err.println("GPS can't be retrieved");
		}
		
		if(capteur != null) {
			while(true) {
				Message m = capteur.getLast();
				try {
					if(m != null) {
						JSONObject response = m.msg;
						double lat_d = response.getDouble("lat");
						double lon_d = response.getDouble("lng");
						synchronized(waypoint) {
							waypoint.setPosition(new GeoPosition(lat_d,lon_d));
							viewer.repaint();
						}
						synchronized(lati) {
							lati.setText(String.valueOf(lat_d));
						}
						synchronized(longi) {
							longi.setText(String.valueOf(lon_d));
						}
						long dateMsg = m.timestamp;
						Date d = new Date();
						System.out.println("Latency GPS : " + (d.getTime() - dateMsg));
						Thread.sleep(100);
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}
	}

}
