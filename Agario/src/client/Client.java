package client;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    private String hostname;
    private int port;

    public static void main(String[] args) {
        Client client = new Client("localhost", 10000);
        client.connect();
    }

    public Client(String hostname, int port) {
        this.hostname = hostname;
        this.port = port;
    }

    public void connect() {
        Scanner scanner = new Scanner(System.in);

        try {
            Socket socket = new Socket(this.hostname, this.port);

            DataOutputStream out = new DataOutputStream(socket.getOutputStream());

            System.out.println("Enter Test:");
            String Test = scanner.next();

            out.writeUTF(Test);
            socket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
