package server;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class WorkerInfo {
    private final int worker_request_id;
    private final ObjectInputStream ois;
    private final ObjectOutputStream oos;

    public WorkerInfo(int worker_request_id, ObjectInputStream ois, ObjectOutputStream oos) {

        this.worker_request_id = worker_request_id;
        this.ois = ois;
        this.oos = oos;
    }

    public int getWorker_request_id() {
        return worker_request_id;
    }

    public ObjectInputStream getOis() {
        return ois;
    }

    public ObjectOutputStream getOos() {
        return oos;
    }

    @Override
    public String toString() {
        return "WorkerInfo{" + "worker_request_id=" + worker_request_id + '}';
    }
}
