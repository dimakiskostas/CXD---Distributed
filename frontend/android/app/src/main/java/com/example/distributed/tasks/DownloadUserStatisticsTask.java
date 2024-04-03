package com.example.distributed.tasks;

import android.os.AsyncTask;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.example.distributed.ui.user_statistics.UserStatisticsViewModel;

import Client.Client;
import configuration.Config;
import model.Statistics;

public class DownloadUserStatisticsTask extends AsyncTask<Void, Void, Statistics[]> {
    private final UserStatisticsViewModel viewmodel;
    private final Button button;
    private final ProgressBar bar;

    public DownloadUserStatisticsTask(UserStatisticsViewModel viewmodel, Button button, ProgressBar bar) {
        this.viewmodel = viewmodel;
        this.button = button;
        this.bar = bar;
    }

    @Override
    protected void onPreExecute() {
        button.setEnabled(false);
        bar.setVisibility(View.VISIBLE);
    }

    @Override
    protected Statistics [] doInBackground(Void... params) {
        try {
            Client client = new Client();

            Statistics [] statistics = client.askForUserStatistics(Config.IP, Config.PORT);

            return statistics;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(Statistics [] result) {
        bar.setVisibility(View.INVISIBLE);
        button.setEnabled(true);
        ProgressBar barfordaily;

        if (result != null) {

            Statistics sum = result[0];
            Statistics average = result[1];

            //
            // Total
            //
            viewmodel.getTotalDistance().postValue(sum.totalDistance);
            viewmodel.getTotalElevation().postValue(sum.totalElevation);
            viewmodel.getTotalTime().postValue(sum.totalTime);
            viewmodel.getSpeed().postValue(sum.averageSpeed);
            viewmodel.getFreq().postValue(sum.freq);

            //
            // User average
            //
            viewmodel.getAverageDistance().postValue(average.totalDistance);
            viewmodel.getAverageElevation().postValue(average.totalElevation);
            viewmodel.getAverageTime().postValue(average.totalTime);
            viewmodel.getAverageSpeed().postValue(average.averageSpeed);

            //
            //Bar with dailey goal of 4000 meters
            //
            double value = sum.totalDistance / 4000;
            Double newdouble = new Double(value);
            int val = newdouble.intValue();
            bar.setProgress(val);

        }
    }
}