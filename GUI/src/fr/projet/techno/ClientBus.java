package fr.projet.techno;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

public class ClientBus implements Runnable {
	
	private String url;
	MyGLEventListener glListener;
	public ClientBus(String _url,MyGLEventListener _glListener) {
		url = _url;
		glListener = _glListener;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		System.out.println("Thread started");
		Socket client = null;
		try {
			client = new Socket(url, 7182);
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return;
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return;
		}
		if(client != null) {
			PrintWriter w = null;
			BufferedReader i = null;
			try {
				w = new PrintWriter(client.getOutputStream(),true);
				w.flush();
				i = new BufferedReader(new InputStreamReader(client.getInputStream()));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return;
			}
			
			JSONObject request = new JSONObject();
			request.put("type", "get_last");
			request.put("sender_id", 1);
			while(true) {
			w.println(request);
				try {
					JSONObject response = new JSONObject(i.readLine());
					synchronized(glListener) {
						glListener.angleX = response.getJSONObject("contents").getDouble("x");
						glListener.angleY = -response.getJSONObject("contents").getDouble("y");
						glListener.angleZ = -response.getJSONObject("contents").getDouble("z");
					}
					long dateMsg = response.getLong("date");
					Date d = new Date();
					System.out.println("Latency : " + (d.getTime() - dateMsg));
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return;
				}
			}
			
		}
	}

}
