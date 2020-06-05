package server;

import client.GameClient;
import data.Player;

import java.io.*;
import java.net.Socket;

public class GameServerClient implements Runnable {

    private Socket socket;
    private GameServer gameServer;
    private DataOutputStream out;
    private DataInputStream in;
    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;
    private String name;

    public GameServerClient(Socket socket, GameServer gameServer) {
        this.socket = socket;
        this.gameServer = gameServer;
    }

    public void sendObject ( Object object ) {
        System.out.println("Got object for client");
        try {
            this.objectOutputStream.writeObject(object);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            this.out = new DataOutputStream( this.socket.getOutputStream());
            this.objectOutputStream = new ObjectOutputStream( this.socket.getOutputStream());
            this.in  = new DataInputStream( this.socket.getInputStream());
            this.objectInputStream = new ObjectInputStream( this.socket.getInputStream());


            //Step 1: Send server details.
            out.writeUTF("Agar.io Gameserver 0.2");

            //Step 2: Get username and get player object.
            this.name = in.readUTF();
            System.out.println("#### " + this.name + " joined the game!");
            gameServer.getPlayers().add((Player) objectInputStream.readObject());

            //Step 3: Send current users and foods to all players.
            this.gameServer.updateAllClients();

//
//            String message = "";
//            while ( !message.equals("stop") ) {
//                message = in.readUTF();

//                System.out.println("avans.ti.chat.client.Client send: " + message);
//                this.gameServer.up("(" + this.name + "): " + message);
//            }

            this.socket.close();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
