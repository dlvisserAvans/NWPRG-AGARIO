package server;

import Data.Player;

import java.io.*;
import java.net.Socket;

public class Client implements Runnable{

    private Socket socket;
    private AgarioServer agarioServer;
    private DataOutputStream dataOutputStream;
    private DataInputStream dataInputStream;
    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;

    public Client(Socket socket, AgarioServer agarioServer){
        this.socket = socket;
        this.agarioServer = agarioServer;
        dataInputStream = null;
        dataOutputStream = null;
        objectInputStream = null;
        objectOutputStream = null;
    }


    @Override
    public void run() {
        try {
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            dataInputStream = new DataInputStream(socket.getInputStream());
            objectInputStream = new ObjectInputStream(socket.getInputStream());

            String name = dataInputStream.readUTF();
            System.out.println("Name :" + name);

            Player player = (Player) objectInputStream.readObject();
            player.setName(name);

            System.out.println(player.getName());
            System.out.println(player.getPosition());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
