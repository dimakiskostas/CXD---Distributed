import Worker.Worker;
import configuration.Config;
import gpxs.GpxsParser;
import model.SampleList;

import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String [] args) throws IOException {
        GpxsParser parser = new GpxsParser();
        String serverIP = Config.IP;
        int serverPort = Config.PORT;

        Worker worker = new Worker();

        worker.login(serverIP, serverPort);

    }
}
