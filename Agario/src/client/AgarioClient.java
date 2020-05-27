package client;

import Data.Player;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class AgarioClient {

    private Socket socket;
    private String hostname;
    private int port;
    private String name;
    private Player player;
    private DataOutputStream dataOutputStream;
    private DataInputStream dataInputStream;
    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;

    public static void main(String[] args) {
        AgarioClient agarioClient = new AgarioClient("localhost", 10000, "Someone");
        agarioClient.connect();
    }

    public AgarioClient(String hostname, int port, String name) {
        this.hostname = hostname;
        this.port = port;
        this.name = name;
        this.player = new Player(name);
        dataInputStream = null;
        dataOutputStream = null;
        objectInputStream = null;
        objectOutputStream = null;
    }

    public void connect() {
        Scanner scanner = new Scanner(System.in);

        try {
            this.socket = new Socket(this.hostname, this.port);

            dataOutputStream = new DataOutputStream(this.socket.getOutputStream());
            objectOutputStream = new ObjectOutputStream(this.socket.getOutputStream());
            dataInputStream = new DataInputStream(this.socket.getInputStream());
            objectInputStream = new ObjectInputStream(this.socket.getInputStream());

            System.out.print("What is your username: ");
            name = scanner.nextLine();
            dataOutputStream.writeUTF(name);
            objectOutputStream.writeObject(new Player(name));

            socket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
