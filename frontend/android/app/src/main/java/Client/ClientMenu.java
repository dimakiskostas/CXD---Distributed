package Client;

import configuration.Config;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ClientMenu {
    private Client client = new Client();
    private final String PATH = "src" + File.separator + "main" +  File.separator + "resources";

    private Scanner scanner = new Scanner(System.in);
    private List<String> collect = new ArrayList<>();

    public ClientMenu() {
        System.out.println("Working Directory = " + System.getProperty("user.dir"));
    }

    public void loadAvailableFiles() {
        collect = Stream.of(new File(PATH).listFiles())
                .filter(file -> !file.isDirectory())
                .map(File::getName).sorted()
                .collect(Collectors.toList());
    }

    public void printAvailableFiles() {
        int i = 0;
        for (String s : collect) {
             System.out.println("\t" + ++i + ". " + s);
        }
    }

    public int askForFile() {
        int choice = -1;

        do {
            int min = 0;
            int max = collect.size();
            System.out.print("Hello " + Config.username + ", please the number of the file: (" + min + "-" + max + " - type 0 to exit):");
            String answer = scanner.nextLine();
            try {
                choice = Integer.parseInt(answer);
            } catch (Exception ex) {
                choice = -1;
                System.out.println("ERROR: please type an integer");
                continue;
            }
        } while  (choice < 0);

        return choice;
    }

    public List<String> loadFile(int choice) {
        if (choice < 1 || choice > collect.size()) {
            return null;
        }
        choice--;

        String filepath = PATH + File.separatorChar + collect.get(choice);

        System.out.println("Sending file: " + filepath);

        Path path = Paths.get(filepath);

        try {
            List<String> lines = Files.readAllLines(path);

            return lines;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void printTitle() {
        System.out.println("================================================");
        System.out.println("                  Fake client                   ");
        System.out.println("================================================");
    }

    public void askForUsername() {
        System.out.println("Type the username you want to use (enter for Bob): ");
        String username = scanner.nextLine();

        if (!username.trim().isEmpty()) {
            Config.username = username;
        }
    }

    public List<String> getAvailableFiles() {
        return collect;
    }
}
