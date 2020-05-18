package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class AgarioClient {

    private Socket socket;
    private String hostname;
    private int port;
    private String name;

    public static void main(String[] args) {
        AgarioClient agarioClient = new AgarioClient("localhost", 10000);
        agarioClient.connect();
    }

    public AgarioClient(String hostname, int port) {
        this.hostname = hostname;
        this.port = port;
    }

    public void connect() {
        Scanner scanner = new Scanner(System.in);

        try {
            this.socket = new Socket(this.hostname, this.port);

            DataOutputStream out = new DataOutputStream( this.socket.getOutputStream() );
            DataInputStream in = new DataInputStream( this.socket.getInputStream() );

            System.out.print("What is your name: ");
            this.name = scanner.nextLine();
            out.writeUTF(this.name);

            socket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
