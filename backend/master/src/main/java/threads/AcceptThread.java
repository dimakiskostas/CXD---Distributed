package threads;

import server.Server;

public class AcceptThread extends Thread {
    private Server server;

    public AcceptThread(Server server) {
        this.server = server;
    }

    @Override
    public void run() {
        super.run();

        server.start();
    }

    public void end() {
        server.stop();
    }
}
