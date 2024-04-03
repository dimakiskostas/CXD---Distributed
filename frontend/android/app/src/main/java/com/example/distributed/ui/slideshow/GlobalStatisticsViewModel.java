package com.example.distributed.ui.slideshow;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class GlobalStatisticsViewModel extends ViewModel {
    //Values for each on a set on 0.0

    //
    // Global
    //
    private final MutableLiveData<Double> totalDistance = new MutableLiveData<>(0.0);
    private final MutableLiveData<Double> totalElevation = new MutableLiveData<>(0.0);
    private final MutableLiveData<Double> totalTime = new MutableLiveData<>(0.0);
    private final MutableLiveData<Double> speed = new MutableLiveData<>(0.0);
    private final MutableLiveData<Integer> freq = new MutableLiveData<>(0);

    private final MutableLiveData<Double> averageDistance = new MutableLiveData<>(0.0);
    private final MutableLiveData<Double> averageElevation = new MutableLiveData<>(0.0);
    private final MutableLiveData<Double> averageTime = new MutableLiveData<>(0.0);
    private final MutableLiveData<Double> averageSpeed = new MutableLiveData<>(0.0);

    //
    // User
    //
    private final MutableLiveData<Double> averageUserDistance = new MutableLiveData<>(0.0);
    private final MutableLiveData<Double> averageUserElevation = new MutableLiveData<>(0.0);
    private final MutableLiveData<Double> averageUserTime = new MutableLiveData<>(0.0);
    private final MutableLiveData<Double> averageUserSpeed = new MutableLiveData<>(0.0);

    //
    // Compare user to global
    //
    private final MutableLiveData<Double> relativeUserDistance = new MutableLiveData<>(0.0);
    private final MutableLiveData<Double> relativeUserElevation = new MutableLiveData<>(0.0);
    private final MutableLiveData<Double> relativeUserTime = new MutableLiveData<>(0.0);
    private final MutableLiveData<Double> relativeUserSpeed = new MutableLiveData<>(0.0);



    public GlobalStatisticsViewModel() {
    }

    public MutableLiveData<Double> getTotalDistance() {
        return totalDistance;
    }

    public MutableLiveData<Double> getTotalElevation() {
        return totalElevation;
    }

    public MutableLiveData<Double> getTotalTime() {
        return totalTime;
    }

    public MutableLiveData<Double> getSpeed() {
        return speed;
    }

    public MutableLiveData<Integer> getFreq() {
        return freq;
    }

    public MutableLiveData<Double> getAverageDistance() {
        return averageDistance;
    }

    public MutableLiveData<Double> getAverageElevation() {
        return averageElevation;
    }

    public MutableLiveData<Double> getAverageTime() {
        return averageTime;
    }

    public MutableLiveData<Double> getAverageSpeed() {
        return averageSpeed;
    }

    public MutableLiveData<Double> getAverageUserDistance() {
        return averageUserDistance;
    }

    public MutableLiveData<Double> getAverageUserElevation() {
        return averageUserElevation;
    }

    public MutableLiveData<Double> getAverageUserTime() {
        return averageUserTime;
    }

    public MutableLiveData<Double> getAverageUserSpeed() {
        return averageUserSpeed;
    }

    public MutableLiveData<Double> getRelativeUserDistance() {
        return relativeUserDistance;
    }

    public MutableLiveData<Double> getRelativeUserElevation() {
        return relativeUserElevation;
    }

    public MutableLiveData<Double> getRelativeUserTime() {
        return relativeUserTime;
    }

    public MutableLiveData<Double> getRelativeUserSpeed() {
        return relativeUserSpeed;
    }
}