package gpxs;

import DistanceCalculator.DistanceCalculator;
import model.Sample;
import model.SampleList;
import model.Statistics;

import java.time.Duration;
import java.time.LocalDateTime;

public class StatisticsCalculator {
    private double calculateTotalTime(SampleList list) {
        String t1 = list.get(0).timestamp.replaceFirst("Z", "");
        String t2 = list.get(list.size() - 1).timestamp.replaceFirst("Z", "");
        ;

        System.out.println("t1 = " + t1);
        System.out.println("t2 = " + t2);

        LocalDateTime start = LocalDateTime.parse(t1);
        LocalDateTime stop = LocalDateTime.parse(t2);

        Duration duration = Duration.between(start, stop);

        Long dt = duration.getSeconds();

        return dt.longValue();
    }

    private double calculateTotalDistance(SampleList list) {
        DistanceCalculator calculator = new DistanceCalculator();

        double totalDistance = 0.0;

        for (int i = 0; i < list.size() - 1; i++) {
            double ele1 = list.get(i).elevation;
            double ele2 = list.get(i + 1).elevation;

            double lat1 = list.get(i).latitude;
            double lat2 = list.get(i + 1).latitude;

            double lon1 = list.get(i).longitude;
            double lon2 = list.get(i + 1).longitude;

            double d = calculator.distance(lat1, lon1, ele1, lat2, lon2, ele2);
            totalDistance += d;
        }

        return totalDistance;
    }

    private double calculateTotalElevation(SampleList list) {
        double totalElevation = 0.0;

        for (int i = 0; i < list.size() - 1; i++) {
            double ele1 = list.get(i).elevation;
            double ele2 = list.get(i + 1).elevation;

            if (ele2 > ele1) {
                totalElevation += ele2 - ele1;
            }
        }

        return totalElevation;
    }

    public Statistics calculateStatistics(SampleList list) {
        Statistics result = new Statistics();

        if (list.isEmpty()) {
            return result;
        }

        result.totalTime = calculateTotalTime(list);
        result.totalDistance = calculateTotalDistance(list);
        result.averageSpeed = result.totalDistance / result.totalTime;
        result.totalElevation = calculateTotalElevation(list);
        return result;
    }
}
