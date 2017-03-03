package fr.norips.projettechno.server;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
public class ClientHandler implements Runnable {
	private Socket s;
	public ClientHandler(Socket s){
		this.s = s;
	}
	@Override
	public void run() {
		//On recupere les flux entrée/sortie du client
		try {
			System.out.println("Un client se connecte");
			PrintWriter w = new PrintWriter(s.getOutputStream(),true);
			BufferedReader i = new BufferedReader(new InputStreamReader(s.getInputStream())); 
			
			String input = i.readLine();
			System.out.println("Read msg :" + input);
			RequestHandler req = new RequestHandler(input);
			
			w.println(req.execute());
			
			w.close();
			s.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
