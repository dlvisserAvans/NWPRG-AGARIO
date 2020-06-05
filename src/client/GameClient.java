package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class GameClient {

    private Socket socket;
    private String host;
    private int port;
    private String name;

    public GameClient( String host, int port ) {
        this.host = host;
        this.port = port;
    }

    public boolean connect () {
        try {
            this.socket = new Socket(this.host, this.port);

            DataInputStream in = new DataInputStream( this.socket.getInputStream() );
            DataOutputStream out = new DataOutputStream( this.socket.getOutputStream() );

            Scanner scanner = new Scanner( System.in );

            String server = in.readUTF();
            System.out.println(server);

            System.out.print("What is your name: ");
            this.name = scanner.nextLine();
            out.writeUTF(this.name);

            new Thread ( () -> {
                while ( true ) {
                    try {
                        System.out.println(in.readUTF());

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();

//            String message = "";
//            while ( !message.equals("stop" ) ) {
//                System.out.print("> ");
//                message = scanner.nextLine();
//                out.writeUTF(message);
//
//                //System.out.println("avans.ti.chat.server.ChatServer response: " + in.readUTF());
//            }

            this.socket.close();

        } catch (IOException e) {
            System.out.println("Could not connect with the server on " + this.host + " with port " + this.port + ": " + e.getMessage());
            return false;
        }

        return true;
    }

    public void createNewPlayer(String name){
        
    }

}
