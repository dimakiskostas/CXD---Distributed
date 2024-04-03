package model;

import java.io.*;

public class NetworkProtocol {
    public void receive(SampleList list, ObjectInputStream ois) throws IOException {
        while (true) {
            String data = ois.readUTF();
            if (data.equalsIgnoreCase("END")) {
                break;
            }
            Sample s= new Sample(data);
            list.add(s);
        }
    }

    public void send(SampleList list, ObjectOutputStream oos) throws IOException {
        for (Sample s : list) {
            oos.writeUTF(s.serialize());
            oos.flush();
        }

        oos.writeUTF("END");
        oos.flush();
    }

    public void send(Statistics what, ObjectOutputStream oos) throws IOException {
        oos.writeUTF(what.serialize());
        oos.flush();
    }

    public void receive(Statistics what, ObjectInputStream ois) throws IOException {
        String data = ois.readUTF();
        what.deserialize(data);
    }

    public void send(int value, ObjectOutputStream oos) throws IOException {
        String s = String.valueOf(value);
        oos.writeUTF(s);
        oos.flush();
    }

    public int receive(ObjectInputStream ois) throws IOException {
        String data = ois.readUTF();
        return Integer.parseInt(data);
    }
}
