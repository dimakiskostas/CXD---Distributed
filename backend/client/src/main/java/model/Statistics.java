package model;

public class Statistics {
    public double averageSpeed = 0;
    public double totalTime = 0;
    public double totalDistance = 0;
    public double totalElevation = 0;
    public int freq = 0;

    public Statistics() {
    }

    public Statistics(double averageSpeed, double totalTime, double totalDistance, double totalElevation) {
        this.averageSpeed = averageSpeed;
        this.totalTime = totalTime;
        this.totalDistance = totalDistance;
        this.totalElevation = totalElevation;
    }

    public Statistics(String data) {
        deserialize(data);
    }

    public void deserialize(String data) {
        String [] fields = data.split("#");
        averageSpeed = Double.parseDouble(fields[0]);
        totalTime = Double.parseDouble(fields[1]);
        totalDistance = Double.parseDouble(fields[2]);
        totalElevation = Double.parseDouble(fields[3]);
    }

    @Override
    public String toString() {
        return "Statistics{" +
                "averageSpeed=" + averageSpeed +
                ", totalTime=" + totalTime +
                ", totalDistance=" + totalDistance +
                ", totalElevation=" + totalElevation +
                '}';
    }

    public String serialize() {
        return serialize("#");
    }

    public String serialize(String sep) {
        return averageSpeed + sep + totalTime + sep + totalDistance + sep + totalElevation;
    }

    public void print() {
        System.out.println(serialize(" "));
    }
}

