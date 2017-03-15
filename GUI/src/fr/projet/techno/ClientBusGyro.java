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
		Capteur capteur = l.get(0);
		
		if(capteur != null) {
			while(true) {
				Message m = capteur.getLast();
				try {
					JSONObject response = m.msg;
					synchronized(glListener) {
						glListener.angleX = response.getJSONObject("contents").getDouble("x");
						glListener.angleY = -response.getJSONObject("contents").getDouble("y");
						glListener.angleZ = -response.getJSONObject("contents").getDouble("z");
					}
					long dateMsg = m.timestamp;
					Date d = new Date();
					System.out.println("Latency Gyro: " + (d.getTime() - dateMsg));
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}
	}

}
