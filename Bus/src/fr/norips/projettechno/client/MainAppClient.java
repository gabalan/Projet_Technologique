package fr.norips.projettechno.client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import org.json.*;

public class MainAppClient {
	public static void main(String[] args) {
		try {
			Socket client = new Socket(args[0], 7182);
			PrintWriter w = new PrintWriter(client.getOutputStream(),true);
			w.flush();
			BufferedReader i = new BufferedReader(new InputStreamReader(client.getInputStream()));
			
			JSONObject request = new JSONObject();
			request.put("type", "register");
			request.put("sender_name", "GPS 2");
			request.put("sender_class", "GPS");
			
			w.println(request.toString());
			System.out.println(i.readLine());
			
			
			w.close();
			client.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
}
}
