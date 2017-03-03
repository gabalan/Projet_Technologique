package fr.norips.projettechno.server;


import java.net.ServerSocket;
import java.net.Socket;

public class MainAppServer {

	public static void main(String[] args) {
				try {
					//Création d'un socket serveur écoutant sur le port 1025
					ServerSocket server = new ServerSocket(7182);
					while(true) {
						Socket c = server.accept();
						new Thread(new ClientHandler(c)).start();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
	}

}
