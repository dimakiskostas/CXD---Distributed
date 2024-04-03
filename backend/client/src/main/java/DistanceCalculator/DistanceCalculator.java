package DistanceCalculator;

public class DistanceCalculator {

    public static final double EARTH_RADIUS = 6371; // Radius of the earth in kilometers

    public double distance(double lat1, double lon1, double ele1, double lat2, double lon2, double ele2) {
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);

        // convert to radians
        lat1 = Math.toRadians(lat1);
        lat2 = Math.toRadians(lat2);

        // apply formulae
        double a = Math.pow(Math.sin(dLat / 2), 2) +
                Math.pow(Math.sin(dLon / 2), 2) *
                        Math.cos(lat1) *
                        Math.cos(lat2);
        double rad = 6371;
        double c = 2 * Math.asin(Math.sqrt(a));
        double distance = rad * c;

        return Math.sqrt(distance * distance + (ele2 - ele1) * (ele2 - ele1));
    }


}


