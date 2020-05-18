package server;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class AgarioServer {

    private final int port;
    private ServerSocket serverSocket;
    private Thread serverThread;


    public AgarioServer() {
        this.port = 10000;
    }

    public static void main(String[] args) {
        System.out.println("Server starting.....");
        AgarioServer server = new AgarioServer();
        server.connect();
    }

    public void connect(){
        try {
            this.serverSocket = new ServerSocket(port);
            boolean isRunning = true;
            while (isRunning){
                System.out.println("Waiting for users to connect....");
                Socket socket = this.serverSocket.accept();

                System.out.println("AgarioClient connected via address: " + socket.getInetAddress().getHostAddress());
                ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                DataInputStream in = new DataInputStream(socket.getInputStream());

                String test = in.readUTF();
                System.out.println(test);

            }
            this.serverSocket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void initializeGame(){

    }
}
