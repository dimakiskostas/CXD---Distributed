package threads;

import gpxs.StatisticsCalculator;
import model.NetworkProtocol;
import model.Sample;
import model.SampleList;
import model.Statistics;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ServiceThreadForMaster extends Thread {
    private NetworkProtocol protocol = new NetworkProtocol();
    private StatisticsCalculator statisticsCalculator = new StatisticsCalculator();
    private String serverIP;
    private int serverPort;
    private int request_id;
    private SampleList list;

    public ServiceThreadForMaster(String serverIP, int serverPort, int request_id, SampleList list) {
        this.serverIP = serverIP;
        this.serverPort = serverPort;

        this.request_id = request_id;
        this.list = list;
    }

    @Override
    public void run() {
        super.run();

        try {
            Statistics s = statisticsCalculator.calculateStatistics(list);

            list.print();

            Socket clientSocket = new Socket(serverIP, serverPort);

            OutputStream outputStream = clientSocket.getOutputStream();
            InputStream inputStream = clientSocket.getInputStream();

            ObjectOutputStream oos = new ObjectOutputStream(outputStream);
            ObjectInputStream ois = new ObjectInputStream(inputStream);

            String prompt = ois.readUTF();

            oos.writeUTF("I AM WORKER FOR RESULTS");
            oos.flush();
            String response = ois.readUTF();

            if (!response.equalsIgnoreCase("HELLO")) {
                System.out.println("Server rejected the connection");
            } else {
                protocol.send(request_id, oos);
                protocol.send(s, oos);
            }

            oos.close();
            ois.close();
            clientSocket.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
