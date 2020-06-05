import server.GameServer;

public class ServerMain {

    public static void main(String[] args) {

        int port = 10000;
        GameServer server = new GameServer(port);
        server.start();

        while ( true ) {
            try {
                Thread.sleep(10000);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Thread.yield();
        }
    }
}
