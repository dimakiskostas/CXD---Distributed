package Client;

//Clients has a different request for every different result.

import configuration.Config;
import model.NetworkProtocol;
import model.SampleList;
import model.Statistics;

import java.io.*;
import java.net.Socket;

public class Client {
    private NetworkProtocol protocol = new NetworkProtocol();

    public Statistics [] askForUserStatistics(String serverIP, int serverPort) throws IOException {

        Socket clientSocket = new Socket(serverIP, serverPort);

        OutputStream outputStream = clientSocket.getOutputStream();
        InputStream inputStream = clientSocket.getInputStream();

        ObjectOutputStream oos = new ObjectOutputStream(outputStream);
        ObjectInputStream ois = new ObjectInputStream(inputStream);

        String prompt = ois.readUTF();

        oos.writeUTF("I AM CLIENT FOR USER STATISTICS");
        oos.flush();

        oos.writeUTF(Config.username);
        oos.flush();
        String response = ois.readUTF();

        if (!response.equalsIgnoreCase("HELLO")) {
            System.out.println("Server rejected the connection");
        }

        Statistics [] statistics = new Statistics[2];

        for (int i=0;i<2;i++) {
            statistics[i] = new Statistics();
        }

        protocol.receive(statistics[0], ois);

        Integer freq = protocol.receive(ois);

        System.out.println("Results for the user (since server boot):");

        statistics[0].freq = freq;

        if (freq > 0) {
            statistics[1].totalDistance = statistics[0].totalDistance / freq;
            statistics[1].totalElevation = statistics[0].totalElevation / freq;
            statistics[1].totalTime = statistics[0].totalTime / freq;
            statistics[1].averageSpeed = statistics[0].averageSpeed / freq;
            statistics[1].freq = statistics[0].freq;
        }
        return statistics;
    }

    public Statistics [] askForGlobalStatistics(String serverIP, int serverPort) throws IOException {
        Socket clientSocket = new Socket(serverIP, serverPort);

        OutputStream outputStream = clientSocket.getOutputStream();
        InputStream inputStream = clientSocket.getInputStream();

        ObjectOutputStream oos = new ObjectOutputStream(outputStream);
        ObjectInputStream ois = new ObjectInputStream(inputStream);

        String prompt = ois.readUTF();

        oos.writeUTF("I AM CLIENT FOR GLOBAL STATISTICS");
        oos.flush();

        oos.writeUTF(Config.username);
        oos.flush();
        String response = ois.readUTF();

        if (!response.equalsIgnoreCase("HELLO")) {
            System.out.println("Server rejected the connection");
        }

        Statistics [] statistics = new Statistics[2];

        //Store them
        for (int i=0;i<2;i++) {
            statistics[i] = new Statistics();
        }

        protocol.receive(statistics[0], ois);

        System.out.println("-----------------------------------------------");
        Integer freq = protocol.receive(ois);
        statistics[0].freq = freq;

        statistics[1] = new Statistics();

        if (freq > 0) {
            statistics[1].totalDistance = statistics[0].totalDistance / freq;
            statistics[1].totalElevation = statistics[0].totalElevation / freq;
            statistics[1].totalTime = statistics[0].totalTime / freq;
            statistics[1].averageSpeed = statistics[0].averageSpeed / freq;
            statistics[1].freq = statistics[0].freq;
        }

        return statistics;
    }

    public Statistics sendFile(String serverIP, int serverPort, SampleList list) throws IOException {
        Socket clientSocket = new Socket(serverIP, serverPort);

        OutputStream outputStream = clientSocket.getOutputStream();
        InputStream inputStream = clientSocket.getInputStream();

        ObjectOutputStream oos = new ObjectOutputStream(outputStream);
        ObjectInputStream ois = new ObjectInputStream(inputStream);

        String prompt = ois.readUTF();

        oos.writeUTF("I AM CLIENT");
        oos.flush();

        oos.writeUTF(Config.username);
        oos.flush();
        String response = ois.readUTF();

        if (!response.equalsIgnoreCase("HELLO")) {
            System.out.println("Server rejected the connection");
        }

        protocol.send(list, oos);

        Statistics [] statistics = new Statistics[2];

        for (int i=0;i<1;i++) {
            statistics[i] = new Statistics();
        }

        for (int i=0;i<1;i++) {
            protocol.receive(statistics[i], ois);
        }

        return statistics[0];
    }
}
