package com.example.distributed.tasks;

import android.os.AsyncTask;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.example.distributed.ui.slideshow.GlobalStatisticsViewModel;
import com.example.distributed.ui.user_statistics.UserStatisticsViewModel;

import java.util.ArrayList;
import java.util.List;

import Client.Client;
import configuration.Config;
import model.Statistics;

public class DownloadGlobalStatisticsTask extends AsyncTask<Void, Void, List<Statistics>> {
    private final GlobalStatisticsViewModel viewmodel;
    private final Button button;
    private final ProgressBar bar;

    public DownloadGlobalStatisticsTask(GlobalStatisticsViewModel viewmodel, Button button, ProgressBar bar) {
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
    protected List<Statistics> doInBackground(Void... params) {
        try {
            Client client = new Client();

            List<Statistics> results = new ArrayList<Statistics>();

            Statistics [] statistics1 = client.askForUserStatistics(Config.IP, Config.PORT);

            Statistics [] statistics2 = client.askForGlobalStatistics(Config.IP, Config.PORT);

            results.add(statistics1[0]);
            results.add(statistics1[1]);
            results.add(statistics2[0]);
            results.add(statistics2[1]);

            return results;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(List<Statistics> results) {
        bar.setVisibility(View.INVISIBLE);
        button.setEnabled(true);

        if (results != null) {
            //
            // Global
            //
            if (results.get(1).freq > 0){
                //
                // Global sum
                //
                Statistics result = results.get(2);
                viewmodel.getFreq().postValue(result.freq);
                viewmodel.getTotalDistance().postValue(result.totalDistance);
                viewmodel.getTotalElevation().postValue(result.totalElevation);
                viewmodel.getTotalTime().postValue(result.totalTime);
                viewmodel.getSpeed().postValue(result.averageSpeed);
                viewmodel.getFreq().postValue(result.freq);

                //
                // Global avg
                //
                Statistics resultAvg = results.get(3);
                viewmodel.getAverageDistance().postValue(resultAvg.totalDistance);
                viewmodel.getAverageElevation().postValue(resultAvg.totalElevation);
                viewmodel.getAverageTime().postValue(resultAvg.totalTime);
                viewmodel.getAverageSpeed().postValue(resultAvg.averageSpeed);
            }
        }

        Statistics userAverage = results.get(1);
        Statistics globalAverage = results.get(3);

        viewmodel.getAverageUserDistance().postValue(userAverage.totalDistance);
        viewmodel.getAverageUserElevation().postValue(userAverage.totalElevation);
        viewmodel.getAverageUserTime().postValue(userAverage.totalTime);
        viewmodel.getAverageUserSpeed().postValue(userAverage.averageSpeed);

        double global_avg_distance = userAverage.totalDistance/globalAverage.totalDistance;
        viewmodel.getRelativeUserDistance().postValue(global_avg_distance);

        double global_avg_elevation = userAverage.totalElevation/globalAverage.totalElevation;
        viewmodel.getRelativeUserElevation().postValue(global_avg_elevation);

        double global_avg_time  = userAverage.totalTime/globalAverage.totalTime;
        viewmodel.getRelativeUserTime().postValue(global_avg_time);

        double global_avg_speed  = userAverage.averageSpeed/globalAverage.averageSpeed;
        viewmodel.getRelativeUserSpeed().postValue(global_avg_speed);
    }
}