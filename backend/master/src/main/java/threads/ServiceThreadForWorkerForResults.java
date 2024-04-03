package threads;

import gpxs.StatisticsCalculator;
import model.NetworkProtocol;
import model.Statistics;
import server.ClientInfo;
import server.Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

public class ServiceThreadForWorkerForResults extends Thread {
    private Server server;
    private NetworkProtocol protocol;
    private final ObjectInputStream ois;
    private final ObjectOutputStream oos;
    private StatisticsCalculator statisticsCalculator = new StatisticsCalculator();
    private int request_id;

    public ServiceThreadForWorkerForResults(Server server, NetworkProtocol protocol, int worker_request_id, ObjectInputStream ois, ObjectOutputStream oos) {
        this.server = server;
        this.protocol = protocol;
        this.ois = ois;
        this.oos = oos;
        this.request_id = worker_request_id;
    }

    @Override
    public void run() {
        super.run();

        try {
            int client_request_id = protocol.receive(ois);

            Statistics statistics = new Statistics();

            // Collect
            protocol.receive(statistics, ois);

            HashMap<Integer, ClientInfo> clients = server.getClients();

            ClientInfo clientInfo = null;

            //Every thread result of a chunk is sent back
            //Locked so clientInfo wont be used by 2 or more threads

            //Every time we get a result we make sure that a new client wont be added

            synchronized (clients) {
                clientInfo = server.getClients().get(client_request_id);
            }


            //For receiving chunks
            //To make sure not 2 threads sent back at the same time

            synchronized (clientInfo) {
                clientInfo.getStatisticsList().add(statistics);
                clientInfo.setNumberOfChunksReceived(clientInfo.getNumberOfChunksReceived()+1);

                if (clientInfo.getNumberOfChunksReceived() >= clientInfo.getNumberOfChunks()) {
                    clientInfo.notifyAll();//Unblocks wait function
                }
            }

            System.out.println("Result received for request id: " + client_request_id);
        } catch (Exception ex ) {
            ex.printStackTrace();
        } finally {
            try {
                oos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
