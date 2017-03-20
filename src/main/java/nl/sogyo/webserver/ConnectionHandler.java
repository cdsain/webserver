package nl.sogyo.webserver;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ConnectionHandler implements Runnable {
    private Socket socket;

    public ConnectionHandler(Socket toHandle) {
        this.socket = toHandle;
    }

    public void run() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String line = reader.readLine();
            RequestMessage request = new RequestMessage();
            request.handleInput(line);
            System.out.println();

            while (!line.isEmpty()) {
                System.out.println(line);
                line = reader.readLine();
                request.handleInput(line);
            }

            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            ResponseMessage response = new ResponseMessage();
            response.generateContent(request);
            writer.write(response.getContent());
            writer.flush();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String... args) {
        try {
            ServerSocket socket = new ServerSocket(9090);
            while(true) {
                Socket newConnection = socket.accept();
                Thread t = new Thread(new ConnectionHandler(newConnection));
                t.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}