package model;

public class Sample {
    public final double latitude;
    public final double longitude;
    public final double elevation;
    public final String timestamp;

    public Sample(double latitude, double longitude, double elevation, String timestamp) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.elevation = elevation;
        this.timestamp = timestamp;
    }
    
    public Sample(String data) {
        String [] fields = data.split("#");
        latitude = Double.parseDouble(fields[0]);
        longitude = Double.parseDouble(fields[1]);
        elevation = Double.parseDouble(fields[2]);
        timestamp = fields[3];
    }

    @Override
    public String toString() {
        return "Sample{" +
                "latitude=" + latitude +
                ", longitude=" + longitude +
                ", elevation=" + elevation +
                ", timestamp='" + timestamp + '\'' +
                '}';
    }

    public String serialize() {
        return serialize("#");
    }

    public String serialize(String sep) {
        return latitude + sep + longitude + sep + elevation + sep + timestamp;
    }
}
