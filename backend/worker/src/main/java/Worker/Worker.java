package Worker;

import gpxs.StatisticsCalculator;
import model.NetworkProtocol;
import model.SampleList;
import model.Statistics;
import threads.ServiceThreadForMaster;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Worker {
    private NetworkProtocol protocol = new NetworkProtocol();


    public void login(String serverIP, int serverPort)  throws IOException  {

        Socket clientSocket = new Socket(serverIP, serverPort);

        OutputStream outputStream = clientSocket.getOutputStream();
        InputStream inputStream = clientSocket.getInputStream();

        ObjectOutputStream oos = new ObjectOutputStream(outputStream);
        ObjectInputStream ois = new ObjectInputStream(inputStream);

        String prompt = ois.readUTF();

        oos.writeUTF("I AM WORKER");
        oos.flush();
        String response = ois.readUTF();

        if (!response.equalsIgnoreCase("HELLO")) {
            System.out.println("Server rejected the connection");
        } else {
            while (true) {
                System.out.println("Waiting for a chunk to process ... ");

                int request_id = protocol.receive(ois);

                SampleList list = new SampleList();
                protocol.receive(list, ois);

                System.out.println("Received sublist for request: " + request_id);

                //Thread for every accept
                ServiceThreadForMaster serviceThreadForMaster = new ServiceThreadForMaster(serverIP, serverPort, request_id, list);
                serviceThreadForMaster.start();
            }
        }
    }
}
