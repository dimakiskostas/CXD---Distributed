package com.example.distributed.ui.user_statistics;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class UserStatisticsViewModel extends ViewModel {
    //Values for each one is set to 0.0
    private final MutableLiveData<Double> totalDistance = new MutableLiveData<>(0.0);
    private final MutableLiveData<Double> totalElevation = new MutableLiveData<>(0.0);
    private final MutableLiveData<Double> totalTime = new MutableLiveData<>(0.0);
    private final MutableLiveData<Double> speed = new MutableLiveData<>(0.0);
    private final MutableLiveData<Integer> freq = new MutableLiveData<>(0);

    private final MutableLiveData<Double> averageDistance = new MutableLiveData<>(0.0);
    private final MutableLiveData<Double> averageElevation = new MutableLiveData<>(0.0);
    private final MutableLiveData<Double> averageTime = new MutableLiveData<>(0.0);
    private final MutableLiveData<Double> averageSpeed = new MutableLiveData<>(0.0);


    private final MutableLiveData<Double> dailyGoal = new MutableLiveData<>(0.0);

    public UserStatisticsViewModel() {

    }

    public MutableLiveData<Double> getTotalDistance() {
        return totalDistance;
    }

    public MutableLiveData<Double> getTotalTime() {
        return totalTime;
    }

    public MutableLiveData<Double> getTotalElevation() {
        return totalElevation;
    }

    public MutableLiveData<Integer> getFreq() {
        return freq;
    }

    public MutableLiveData<Double> getSpeed() {
        return speed;
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

    public MutableLiveData<Double> getDailyGoal() {
        return dailyGoal;
    }
}