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

            //Step 1: Get/Set player name.
            Player player = (Player) objectInputStream.readObject();
            player.setName(name);

            System.out.println(player.getName());
            System.out.println(player.getPosition());
            //Step 2: get the food from the server.
            while (true) {
                this.agarioServer.sentFoodToAllClients();
                System.out.println("Test");
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void writeObject(Object object){
        System.out.println("Sending objects to all players");
        try {
            this.objectOutputStream.writeObject(object);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
