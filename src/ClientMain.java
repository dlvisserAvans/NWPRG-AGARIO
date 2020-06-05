import client.GameClient;

public class ClientMain {

    public static void main(String[] args) {

        GameClient client = new GameClient("localhost", 10000);

        client.connect();
    }
}
