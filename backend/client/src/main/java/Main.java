import Client.Client;
import Client.ClientMenu;
import configuration.Config;
import gpxs.GpxsParser;
import gpxs.StatisticsCalculator;
import model.Sample;
import model.SampleList;
import model.Statistics;

import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String [] args) throws IOException {
        ClientMenu menu = new ClientMenu();
        GpxsParser parser = new GpxsParser();
        String serverIP = Config.IP;
        int serverPort = Config.PORT;

        menu.printTitle();
        menu.loadAvailableFiles();

        if (Config.ASK_FOR_FILE) {
            menu.printAvailableFiles();
        }

        if (Config.ASK_FOR_USERNAME) {
            menu.askForUsername();
        }

        System.out.println("Username set as: " + Config.username);

        int choice = 0;

        while (choice < menu.getAvailableFiles().size()) {
            if (Config.ASK_FOR_FILE) {
                choice = menu.askForFile();
            }  else {
                choice++;
            }

            if (choice == 0) {
                break;
            }

            List<String> data = menu.loadFile(choice);

            SampleList workoutSamples = parser.loadFromList(data);

            Client client = new Client();
            client.sendFile(serverIP, serverPort, workoutSamples);
            Statistics[] userStatistics = client.askForUserStatistics(serverIP, serverPort);

            System.out.println("--------------- USER --------------");
            for (int i=0;i<userStatistics.length;i++) {
                if (i == 0) {
                    System.out.print("SUM: ");
                }
                if (i == 1) {
                    System.out.print("AVG: ");
                }
                userStatistics[i].print();
            }

            System.out.println("--------------- GLOBAL --------------");
            Statistics[] globalStatistics = client.askForGlobalStatistics(serverIP, serverPort);

            for (int i=0;i<globalStatistics.length;i++) {
                if (i == 0) {
                    System.out.print("SUM: ");
                }
                if (i == 1) {
                    System.out.print("AVG: ");
                }
                globalStatistics[i].print();
            }

        }
    }
}
