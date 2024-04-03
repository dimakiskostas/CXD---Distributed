package server;

import configuration.Config;
import model.NetworkProtocol;
import model.Statistics;
import threads.ServiceThreadForClient;
import threads.ServiceThreadForWorker;
import threads.ServiceThreadForWorkerForResults;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Locale;

public class Server {

    private final NetworkProtocol protocol = new NetworkProtocol();
    private ServerSocket providerSocket = null;
    private int client_request_id = 1;
    private int worker_request_id = 0;
    private final HashMap<Integer, ClientInfo> clients = new HashMap<>();
    private final HashMap<Integer, WorkerInfo> workers = new HashMap<>();
    private final HashMap<String, Statistics> userResults = new HashMap<>();
    private final HashMap<String, Integer> userFrequency = new HashMap<>();
    private final Statistics allUserStatistics = new Statistics();
    private Integer allUsersFrequency = 0;

    public void start() {
        try {
            System.out.println("Creating server socket at port: " + Config.PORT);
            providerSocket = new ServerSocket(Config.PORT);

            while (true) {
                System.out.println("Waiting for connection from a client or worker ... ");

                Socket clientSocket = providerSocket.accept();

                System.out.println("Client or worker connected ... Identifing ");

                InputStream inputStream = clientSocket.getInputStream();
                OutputStream outputStream = clientSocket.getOutputStream();

                ObjectInputStream ois = new ObjectInputStream(inputStream);
                ObjectOutputStream oos = new ObjectOutputStream(outputStream);

                System.out.println("Handshake for connection");

                System.out.println("Sending welcome message ... ");

                oos.writeUTF("WHO ARE YOU");
                oos.flush();

                String token = ois.readUTF();

                System.out.println("Received: ... " + token);

                if (token.toUpperCase().startsWith("I AM CLIENT")) {
                    System.out.println("A client has connected");
                    oos.writeUTF("HELLO");
                    oos.flush();

                    String username = ois.readUTF();

                    client_request_id++;
                    ClientInfo info = new ClientInfo(username, client_request_id, ois, oos);
                    clients.put(client_request_id, info);

                    System.out.println("Client info created:" + info);

                    ServiceThreadForClient serviceThread = new ServiceThreadForClient(this, protocol, info, client_request_id, token, ois, oos);
                    serviceThread.start();
                } else if (token.equalsIgnoreCase("I AM WORKER")) {
                    System.out.println("A worker has connected");
                    oos.writeUTF("HELLO");
                    oos.flush();

                    worker_request_id++;
                    WorkerInfo info = new WorkerInfo(worker_request_id, ois, oos);
                    workers.put(worker_request_id, info);

                    System.out.println("Currently know workers: ");

                    for (WorkerInfo wi : workers.values()) {
                        System.out.println( " + " + wi);
                    }

                } else if (token.equalsIgnoreCase("I AM WORKER FOR RESULTS")) {
                    System.out.println("A worker has connected for results");
                    oos.writeUTF("HELLO");
                    oos.flush();

                    worker_request_id++;
                    ServiceThreadForWorkerForResults serviceThread = new ServiceThreadForWorkerForResults(this, protocol, worker_request_id, ois, oos);
                    serviceThread.start();
                } else {
                    System.out.println("Unkwown client tried to connect ...  (handshake failed) ");
                    oos.writeUTF("GO AWAY");
                    oos.flush();
                }

                /* Handle the request */

            }

        } catch (IOException ioException) {
            if (!ioException.getMessage().equalsIgnoreCase("Socket closed")) {
                ioException.printStackTrace();
            }
        }
    }

    public void stop() {
        try {
            if (providerSocket != null) {
                System.out.println("Closing server socket at port: " + Config.PORT);
                providerSocket.close();
            }
        } catch (IOException ioException) {
        }
    }

    public HashMap<Integer, ClientInfo> getClients() {
        return clients;
    }

    public HashMap<Integer, WorkerInfo> getWorkers() {
        return workers;
    }

    public HashMap<String, Statistics> getUserResults() {
        return userResults;
    }

    public HashMap<String, Integer> getUserFrequency() {
        return userFrequency;
    }

    public Statistics getAllUserStatistics() {
        return allUserStatistics;
    }

    public Integer getAllUsersFrequency() {
        return allUsersFrequency;
    }

    public void setAllUsersFrequency(Integer allUsersFrequency) {
        this.allUsersFrequency = allUsersFrequency;
    }
}
