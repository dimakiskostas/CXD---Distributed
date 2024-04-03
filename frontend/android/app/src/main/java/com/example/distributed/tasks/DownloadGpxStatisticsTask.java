package com.example.distributed.tasks;

import android.os.AsyncTask;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.example.distributed.ui.home.HomeViewModel;

import Client.Client;
import configuration.Config;
import model.SampleList;
import model.Statistics;

public class DownloadGpxStatisticsTask extends AsyncTask<Void, Void, Statistics> {
    private final HomeViewModel viewmodel;
    private final Button button;
    private final ProgressBar bar;
    private SampleList workoutSamples;

    public DownloadGpxStatisticsTask(HomeViewModel viewmodel, Button button, ProgressBar bar, SampleList workoutSamples) {
        this.viewmodel = viewmodel;
        this.button = button;
        this.bar = bar;
        this.workoutSamples = workoutSamples;
    }

    @Override
    protected void onPreExecute() {
        button.setEnabled(false);
        bar.setVisibility(View.VISIBLE);
    }

    @Override
    protected Statistics doInBackground(Void... params) {
        try {
            Client client = new Client();

            Statistics statistics = client.sendFile(Config.IP, Config.PORT, workoutSamples);

            return statistics;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(Statistics result) {
        bar.setVisibility(View.INVISIBLE);
        button.setEnabled(true);

        if (result != null) {
            viewmodel.getTotalDistance().postValue(result.totalDistance);
            viewmodel.getTotalElevation().postValue(result.totalElevation);
            viewmodel.getTotalTime().postValue(result.totalTime);
            viewmodel.getSpeed().postValue(result.averageSpeed);
        }
    }
}