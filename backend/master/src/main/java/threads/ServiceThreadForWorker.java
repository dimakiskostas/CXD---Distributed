package threads;

import gpxs.StatisticsCalculator;
import model.NetworkProtocol;
import model.SampleList;
import model.Statistics;
import server.Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

public class ServiceThreadForWorker extends Thread {
    private Server server;
    private NetworkProtocol protocol;
    private final ObjectInputStream ois;
    private final ObjectOutputStream oos;
    private StatisticsCalculator statisticsCalculator = new StatisticsCalculator();
    private int request_id;

    public ServiceThreadForWorker(Server server, NetworkProtocol protocol, int worker_request_id,  ObjectInputStream ois, ObjectOutputStream oos) {
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

            while (true) {

            }

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
