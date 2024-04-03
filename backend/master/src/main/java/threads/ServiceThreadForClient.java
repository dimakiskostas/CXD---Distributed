package threads;

import configuration.Config;
import gpxs.StatisticsCalculator;
import model.NetworkProtocol;
import model.Sample;
import model.SampleList;
import model.Statistics;
import server.ClientInfo;
import server.Server;
import server.WorkerInfo;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.attribute.UserPrincipal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ServiceThreadForClient extends Thread {
    private Server server;
    private NetworkProtocol protocol;
    private final String token;
    private final ObjectInputStream ois;
    private final ObjectOutputStream oos;
    private StatisticsCalculator statisticsCalculator = new StatisticsCalculator();
    private int request_id;
    private final ClientInfo clientInfo;

    public ServiceThreadForClient(Server server, NetworkProtocol protocol, ClientInfo info, int request_id, String token, ObjectInputStream ois, ObjectOutputStream oos) {
        this.server = server;
        this.protocol = protocol;
        this.token = token;
        this.ois = ois;
        this.oos = oos;
        this.request_id = request_id;
        this.clientInfo =info;
    }

    private List<SampleList> divide(final SampleList list, int totalChunks) {
        //
        // Divide lists to sublists
        //
        int chunkLength = list.size() / totalChunks;

        int remaining = list.size() % totalChunks;

        List<SampleList> sublists = new ArrayList<>();

        if (totalChunks >= 2) {
            for (int i = 0; i < totalChunks; i++) {
                sublists.add(new SampleList());
            }

            for (int i = 0, j = 0; i < totalChunks; i++) {
                int n = chunkLength + ((i < remaining) ? 1 : 0);

                for (int k = 0; k < n; k++) {
                    Sample sample = list.get(j);
                    sublists.get(i).add(sample);
                    j++;
                }
            }

            for (int i = 0; i < totalChunks -1; i++) {
                SampleList sample1 = sublists.get(i);

                if (sublists.get(i+1).size() > 0) {
                    Sample firstSampleOfNextList = sublists.get(i+1).get(0);
                    sample1.add(firstSampleOfNextList);
                }
            }
        } else {
            sublists.add(list);
        }
        return sublists;
    }

    @Override
    public void run() {
        super.run();

        try {
            if (token.equalsIgnoreCase("I AM CLIENT")) {
                SampleList list = new SampleList();

                protocol.receive(list, ois);

                HashMap<Integer, WorkerInfo> workerInfoList = server.getWorkers();

                int totalWorkers = -1;


                //Make sure that not a new worker gets added when we check how many are available
                synchronized (workerInfoList) {
                    totalWorkers = workerInfoList.size();
                }

                int totalChunks;

                //The number of chunks are the same as the totalWorkers, or it is changed
                if (Config.USE_CHUNK_FACTOR) {
                    totalChunks = Config.CHUNK_FACTOR * totalWorkers;
                } else {
                    totalChunks = totalWorkers;
                }

                List<SampleList> subLists = divide(list, totalChunks);

                //Stores all the clients that have connected
                HashMap<Integer, ClientInfo> clients = server.getClients();

                ClientInfo clientInfo = null;


                //Make sure that when the number of chunk gets updated, a new client gets added
                synchronized (clients) {
                    clientInfo = server.getClients().get(request_id);
                    clientInfo.setNumberOfChunks(totalChunks);
                }

                // send sublists to worker
                for (int i = 0; i < totalChunks; i++) {
                    SampleList workerlist = subLists.get(i);

                    WorkerInfo workerInfo = null;


                    //Make sure that while a chunk gets "sent" to a worker, a new worker can not get added(change)
                    synchronized (workerInfoList) {
                        if (!Config.USE_CHUNK_FACTOR) {
                            //Every worker gets the next waypoint
                            workerInfo = workerInfoList.get(i + 1);
                        } else {
                            int x = i % totalWorkers;
                            workerInfo = workerInfoList.get(x + 1);
                        }
                    }


                    //Make sure that while a chunk gets "sent" to a worker, another chunk can not be sent
                    synchronized (workerInfo) {
                        protocol.send(request_id, workerInfo.getOos());
                        protocol.send(workerlist, workerInfo.getOos());
                    }
                }

                //
                // Wait for result
                //

                //In order to call wait
                synchronized (clientInfo) {
                    while (clientInfo.getNumberOfChunksReceived() < clientInfo.getNumberOfChunks()) {
                        clientInfo.wait();//Waits for notifyAll function to unblock
                    }
                }

                List<Statistics> statisticsList = clientInfo.getStatisticsList();

                Statistics listResults = new Statistics();

                //
                // reduce results for the specific list of points
                //
                for (Statistics s : statisticsList) {
                    listResults.totalElevation += s.totalElevation;
                    listResults.totalTime += s.totalTime;
                    listResults.totalDistance += s.totalDistance;
                }

                if (listResults.totalTime > 0) {
                    listResults.averageSpeed = listResults.totalDistance / listResults.totalTime;
                }
//            list.print();

                protocol.send(listResults, oos);

                //
                // update and send results of user (total)
                //
                HashMap<String, Statistics> userResults = server.getUserResults();
                HashMap<String, Integer> userFrequency = server.getUserFrequency();


                //Make sure that 2 or more threads update the statistics
                synchronized (userResults) {
                    if (!userResults.containsKey(clientInfo.getUsername())) {
                        userResults.put(clientInfo.getUsername(), listResults);
                        userFrequency.put(clientInfo.getUsername(), 1);
                    } else {
                        Statistics userStatistics = userResults.get(clientInfo.getUsername());
                        userStatistics.totalElevation += listResults.totalElevation;
                        userStatistics.totalTime += listResults.totalTime;
                        userStatistics.totalDistance += listResults.totalDistance;

                        if (userStatistics.totalTime > 0) {
                            userStatistics.averageSpeed = userStatistics.totalDistance / userStatistics.totalTime;
                        }

                        Integer freq = userFrequency.get(clientInfo.getUsername());
                        freq++;
                        userFrequency.put(clientInfo.getUsername(), freq);
                    }

                    server.setAllUsersFrequency(server.getAllUsersFrequency() + 1);

                    Statistics allUserStatistics = server.getAllUserStatistics();

                    synchronized (allUserStatistics) {
                        allUserStatistics.totalElevation += listResults.totalElevation;
                        allUserStatistics.totalTime += listResults.totalTime;
                        allUserStatistics.totalDistance += listResults.totalDistance;

                        if (allUserStatistics.totalTime > 0) {
                            allUserStatistics.averageSpeed = allUserStatistics.totalDistance / allUserStatistics.totalTime;
                        }
                    }
                }

                oos.close();
            }

            if (token.equalsIgnoreCase("I AM CLIENT FOR USER STATISTICS")) {
                HashMap<String, Statistics> userResults = server.getUserResults();
                HashMap<String, Integer> userFrequency = server.getUserFrequency();

                Statistics userStatistics = server.getUserResults().get(clientInfo.getUsername());

                if (userStatistics != null) {
                    protocol.send(userStatistics, oos);

                    Integer freq = userFrequency.get(clientInfo.getUsername());

                    protocol.send(freq, oos);
                } else {
                    userStatistics = new Statistics();
                    protocol.send(userStatistics, oos);
                    Integer freq = 0;
                    protocol.send(freq, oos);
                }
            }

            if (token.equalsIgnoreCase("I AM CLIENT FOR GLOBAL STATISTICS")) {
                Statistics allUserStatistics = server.getAllUserStatistics();

                if (allUserStatistics != null) {
                    protocol.send(allUserStatistics, oos);

                    Integer freq = server.getAllUsersFrequency();

                    protocol.send(freq, oos);
                } else {
                    allUserStatistics = new Statistics();
                    protocol.send(allUserStatistics, oos);
                    Integer freq = 0;
                    protocol.send(freq, oos);
                }
            }
        } catch(Exception ex){
            ex.printStackTrace();
        }


    }
}
