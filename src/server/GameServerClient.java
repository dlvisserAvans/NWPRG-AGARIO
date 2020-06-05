package server;

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
            this.in  = new DataInputStream( this.socket.getInputStream());
            this.out = new DataOutputStream( this.socket.getOutputStream());
            this.objectInputStream = new ObjectInputStream( this.socket.getInputStream());
            this.objectOutputStream = new ObjectOutputStream( this.socket.getOutputStream());

            out.writeUTF("Agar.io Gameserver 0.2");

            this.name = in.readUTF();
            System.out.println("#### " + this.name + " joined the game!");
            this.gameServer.updateAllClients();

//
//            String message = "";
//            while ( !message.equals("stop") ) {
//                message = in.readUTF();
//                out.writeUTF(message);
//                System.out.println("avans.ti.chat.client.Client send: " + message);
//                this.server.up("(" + this.name + "): " + message);
//            }

            this.socket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
