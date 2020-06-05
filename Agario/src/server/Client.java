package server;

import Data.Player;

import java.io.*;
import java.net.Socket;
import java.util.ConcurrentModificationException;

public class Client implements Runnable{

    private Socket socket;
    private AgarioServer agarioServer;
    private DataOutputStream dataOutputStream;
    private DataInputStream dataInputStream;
    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;
    private Player player;
    String name;

    public Client(Socket socket, AgarioServer agarioServer){
        this.socket = socket;
        this.agarioServer = agarioServer;
        dataInputStream = null;
        dataOutputStream = null;
        objectInputStream = null;
        objectOutputStream = null;
        this.name = "";
        this.player = null;
    }


    @Override
    public void run() {
        try {
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            dataInputStream = new DataInputStream(socket.getInputStream());
            objectInputStream = new ObjectInputStream(socket.getInputStream());

            this.name = dataInputStream.readUTF();
            System.out.println("Name :" + this.name);

            //Step 1: Get/Set player name.
            this.player = (Player) objectInputStream.readObject();
            player.setName(name);
            this.agarioServer.addPlayer(this.player);

            System.out.println(this.player.getName());
            System.out.println(this.player.getPosition());

            //Step 2: get the food from the server.
            String start = dataInputStream.readUTF();
            System.out.println(start);

            if (start.equals("start")){
                while (true) {
                    this.agarioServer.sentFoodToAllClients();
                    this.agarioServer.sentPlayersToAllClients();
                    this.agarioServer.update(this.player);
                }
            }


        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (ConcurrentModificationException e){
            System.out.println("Name: " + this.name);
            e.printStackTrace();
        }
    }

    public void writeObject(Object object){
        try {
            this.objectOutputStream.writeObject(object);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getName(){
        return this.name;
    }

}
