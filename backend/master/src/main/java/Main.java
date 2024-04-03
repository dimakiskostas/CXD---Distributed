import server.Server;
import threads.AcceptThread;

import java.util.Scanner;

public class Main {
    public static void main(String [] args) {
        Server server = new Server();

        AcceptThread thread = new AcceptThread(server);
        thread.start();

        System.out.println("Press enter to shutdown the server");

        new Scanner(System.in).nextLine();

        thread.end();

        System.out.println("Shutdown complete");
    }
}
