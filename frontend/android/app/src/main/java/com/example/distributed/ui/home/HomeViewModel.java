package com.example.distributed.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeViewModel extends ViewModel {
    //Values for each on a set on 0.0
    private final MutableLiveData<String> selectFilename = new MutableLiveData<>("");
    private final MutableLiveData<Double> totalDistance = new MutableLiveData<>(0.0);
    private final MutableLiveData<Double> totalElevation = new MutableLiveData<>(0.0);
    private final MutableLiveData<Double> totalTime = new MutableLiveData<>(0.0);
    private final MutableLiveData<Double> speed = new MutableLiveData<>(0.0);

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

    public MutableLiveData<String> getSelectFilename() {
        return selectFilename;
    }
}