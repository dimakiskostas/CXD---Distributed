package server;

import model.Statistics;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class ClientInfo {
    private final int client_request_id;
    private final ObjectInputStream ois;
    private final ObjectOutputStream oos;
    private int numberOfChunks = 0;
    private int numberOfChunksReceived = 0;
    private final List<Statistics> statisticsList = new ArrayList<>();
    private String username;

    public ClientInfo(String username, int client_request_id, ObjectInputStream ois, ObjectOutputStream oos) {
        this.username = username;
        this.client_request_id = client_request_id;
        this.ois = ois;
        this.oos = oos;
    }

    public int getClient_request_id() {
        return client_request_id;
    }

    public ObjectInputStream getOis() {
        return ois;
    }

    public ObjectOutputStream getOos() {
        return oos;
    }

    public int getNumberOfChunks() {
        return numberOfChunks;
    }

    public int getNumberOfChunksReceived() {
        return numberOfChunksReceived;
    }

    public List<Statistics> getStatisticsList() {
        return statisticsList;
    }

    public void setNumberOfChunks(int numberOfChunks) {
        this.numberOfChunks = numberOfChunks;
    }

    public void setNumberOfChunksReceived(int numberOfChunksReceived) {
        this.numberOfChunksReceived = numberOfChunksReceived;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "ClientInfo{" +
                "client_request_id=" + client_request_id +
                ", username='" + username + '\'' + '}';
    }
}