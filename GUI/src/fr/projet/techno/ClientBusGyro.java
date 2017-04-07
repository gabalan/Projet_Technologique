package fr.projet.techno;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import fr.norips.busAPI.Bus;
import fr.norips.busAPI.Capteur;
import fr.norips.busAPI.Message;


public class ClientBusGyro implements Runnable {
	
	private Bus bus;
	MyGLEventListener glListener;
	public ClientBusGyro(Bus _b,MyGLEventListener _glListener) {
		bus = _b;
		glListener = _glListener;
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
						synchronized(glListener) {
							glListener.angleX = response.getDouble("x");
							glListener.angleY = -response.getDouble("y");
							glListener.angleZ = -response.getDouble("z");
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
