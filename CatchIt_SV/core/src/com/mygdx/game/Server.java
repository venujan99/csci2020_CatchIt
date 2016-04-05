package com.mygdx.game;

/**
 * Created by sindiya on 05/04/16.
 */

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static void main(String[] args) throws IOException, InterruptedException {
        // write your code here

        ServerSocket serverSocket = new ServerSocket(9655);
        while( true) {
            //to check if server is working correctly
            System.out.println("Server Is Open");
            Socket clientSocket = serverSocket.accept();
            PrintWriter out =
                    new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream()));
            String line = in.readLine();
            System.out.println(line);
            if ( line != null && !line.equals( "") ) {
                //writing to the scores.txt on server
                PrintWriter fileOut = new PrintWriter(new BufferedWriter(new FileWriter("globalScores.txt", true)));
                fileOut.println(line);
                fileOut.close();
            }

            Thread.sleep(1000);

        }

    }
}
