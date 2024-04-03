package com.example.distributed.ui.user_statistics;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.distributed.databinding.FragmentUserStatisticsBinding;
import com.example.distributed.tasks.DownloadUserStatisticsTask;
import com.google.android.material.button.MaterialButton;

public class UserStatisticsFragment extends Fragment {

    private FragmentUserStatisticsBinding binding;


    private double round(double value) {
        return (double)Math.round(value * 1000d) / 1000d;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        UserStatisticsViewModel viewmodel = new ViewModelProvider(this).get(UserStatisticsViewModel.class);



        binding = FragmentUserStatisticsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        TextView textViewTotalDistanceValueUserStatistics = binding.textViewTotalDistanceValueUserStatistics;
        TextView textViewTotalElevationValueUserStatistics = binding.textViewTotalElevationValueUserStatistics;
        TextView textViewTotalTimeValueUserStatistics = binding.textViewTotalTimeValueUserStatistics;
        TextView textViewAverageSpeedValueUserStatistics = binding.textViewAverageSpeedValueUserStatistics;
        TextView textViewFrequency = binding.textViewFrequency;

        TextView textViewAvgDistanceValueUserStatistics = binding.textViewAverageDistanceUserStatistics;
        TextView textViewAvgElevationValueUserStatistics = binding.textViewAverageElevationUserStatistics;
        TextView textViewAvgTimeValueUserStatistics = binding.textViewAverageTimeUserStatistics;
        TextView textViewAverageAverageSpeedValueUserStatistics = binding.textViewAverageAverageSpeedValueUserStatistics;

        ProgressBar dailyGoal = binding.progressBarForDailyStats;
        
        Button button = binding.buttonshowuserstatistics;
        ProgressBar bar = binding.progressBarForDailyUser;

        //
        // Totals
        //
        viewmodel.getTotalDistance().observe(getViewLifecycleOwner(), new Observer<Double>() {
            @Override
            public void onChanged(Double value) {
                String text = String.valueOf(round(value));
                textViewTotalDistanceValueUserStatistics.setText(text);
            }
        });

        viewmodel.getTotalElevation().observe(getViewLifecycleOwner(), new Observer<Double>() {
            @Override
            public void onChanged(Double value) {
                String text = String.valueOf(round(value));
                textViewTotalElevationValueUserStatistics.setText(text);
            }
        });

        viewmodel.getTotalTime().observe(getViewLifecycleOwner(), new Observer<Double>() {
            @Override
            public void onChanged(Double value) {
                String text = String.valueOf(round(value));
                textViewTotalTimeValueUserStatistics.setText(text);
            }
        });

        viewmodel.getSpeed().observe(getViewLifecycleOwner(), new Observer<Double>() {
            @Override
            public void onChanged(Double value) {
                String text = String.valueOf(round(value));
                textViewAverageSpeedValueUserStatistics.setText(text);
            }
        });

        viewmodel.getFreq().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer value) {
                String text = String.valueOf(value);
                textViewFrequency.setText(text);
            }
        });

        //
        // Averages
        //
        viewmodel.getAverageDistance().observe(getViewLifecycleOwner(), new Observer<Double>() {
            @Override
            public void onChanged(Double value) {
                String text = String.valueOf(round(value));
                textViewAvgDistanceValueUserStatistics.setText(text);
            }
        });

        viewmodel.getAverageElevation().observe(getViewLifecycleOwner(), new Observer<Double>() {
            @Override
            public void onChanged(Double value) {
                String text = String.valueOf(round(value));
                textViewAvgElevationValueUserStatistics.setText(text);
            }
        });

        viewmodel.getAverageTime().observe(getViewLifecycleOwner(), new Observer<Double>() {
            @Override
            public void onChanged(Double value) {
                String text = String.valueOf(round(value));
                textViewAvgTimeValueUserStatistics.setText(text);
            }
        });

        viewmodel.getAverageSpeed().observe(getViewLifecycleOwner(), new Observer<Double>() {
            @Override
            public void onChanged(Double value) {
                String text = String.valueOf(round(value));
                textViewAverageAverageSpeedValueUserStatistics.setText(text);
            }
        });


        viewmodel.getDailyGoal().observe(getViewLifecycleOwner(), new Observer<Double>() {
            @Override
            public void onChanged(Double value) {
//                int progress = viewmodel.getTotalDistance().getValue().intValue();
//                dailyGoal.setProgress(progress);
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DownloadUserStatisticsTask runner = new DownloadUserStatisticsTask(viewmodel, button, bar);
                runner.execute();

                //
                //Daily goal
                //
                int progress = viewmodel.getTotalDistance().getValue().intValue();
                dailyGoal.setProgress(progress);

            }
        });



        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}