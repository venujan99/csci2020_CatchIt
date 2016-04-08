package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.Chansey_Game;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class DesktopLauncher {
	public static void main(String[] args) throws IOException, InterruptedException {
		// write your code here
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Catch It - Game";
		config.height = 720;
		config.width = 1080;

		new LwjglApplication(new Chansey_Game(), config);

		ServerSocket serverSocket = new ServerSocket(9655);
		while( true) {
			System.out.println("Server Open");

			Socket clientSocket = serverSocket.accept();
			PrintWriter out =
					new PrintWriter(clientSocket.getOutputStream(), true);
			BufferedReader in = new BufferedReader(
					new InputStreamReader(clientSocket.getInputStream()));
			String line = in.readLine();
			System.out.println(line);
			if ( line != null && !line.equals( "") ) {
				PrintWriter fileOut = new PrintWriter(new BufferedWriter(new FileWriter("Server/globalScores.txt", true)));
				fileOut.println(line);
				fileOut.close();
			}

			Thread.sleep(1000);

		}

	}
}