package fr.projet.techno;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.List;
import javax.swing.JSpinner;

import org.json.JSONException;
import org.json.JSONObject;

import fr.norips.busAPI.Bus;
import fr.norips.busAPI.Capteur;
import fr.norips.busAPI.Message;


public class ClientBusGyro implements Runnable {
	
	private Bus bus;
	MyGLEventListener glListener;
	JSpinner spin_x,spin_y,spin_z;
	public ClientBusGyro(Bus _b,MyGLEventListener _glListener, JSpinner spinner_x,JSpinner spinner_y,JSpinner spinner_z) {
		bus = _b;
		glListener = _glListener;
		spin_x = spinner_x;
		spin_y = spinner_y;
		spin_z = spinner_z;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		List<Capteur> l = bus.list("Gyroscope", null);
		Capteur capteur = null;
		try {
			capteur = l.get(0);
		} catch (IndexOutOfBoundsException e) {
			System.err.println("Gyroscope can't be retrieved");
		}
		
		if(capteur != null) {
			while(true) {
				Message m = capteur.getLast();
				try {
					if(m != null) {
						JSONObject response = m.msg;
						double angleX = response.getDouble("x");
						double angleY = response.getDouble("y");
						double angleZ = response.getDouble("z");
						synchronized(glListener) {
							glListener.angleX = angleX;
							glListener.angleY = -angleY;
							glListener.angleZ = -angleZ;
							synchronized(spin_x) {
								spin_x.setValue(angleX);
							}
							synchronized(spin_y) {
								spin_y.setValue(-angleY);
							}
							synchronized(spin_z) {
								spin_z.setValue(-angleZ);
							}
						}
						long dateMsg = m.timestamp;
						Date d = new Date();
						Thread.sleep(100);
						System.out.println("Latency Gyro: " + (d.getTime() - dateMsg));
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
