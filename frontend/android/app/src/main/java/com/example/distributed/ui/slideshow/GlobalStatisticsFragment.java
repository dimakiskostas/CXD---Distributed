package com.example.distributed.ui.slideshow;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.distributed.databinding.FragmentGlobalStatisticsBinding;
import com.example.distributed.tasks.DownloadGlobalStatisticsTask;

public class GlobalStatisticsFragment extends Fragment {

    private FragmentGlobalStatisticsBinding binding;


    private double round(double value) {
        return (double)Math.round(value * 1000d) / 1000d;
    }

    private double round_percent(double value) {
        return (double)Math.round(value * 100d) / 100d;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        GlobalStatisticsViewModel viewmodel = new ViewModelProvider(this).get(GlobalStatisticsViewModel.class);

        binding = FragmentGlobalStatisticsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        TextView textViewTotalDistanceGlobalStatistics = binding.textViewTotalDistanceGlobalStatistics;
        TextView textViewTotalElevationGlobalStatistics = binding.textViewTotalElevationGlobalStatistics;
        TextView textViewTotalTimeGlobalStatistics = binding.textViewTotalTimeGlobalStatistics;
        TextView textViewSpeedGlobalStatistics = binding.textViewSpeedGlobalStatistics;

        TextView textViewGlobalFrequency = binding.textViewGlobalFrequency;

        TextView textViewAverageDistanceGlobalStatistics = binding.textViewAverageDistanceGlobalStatistics;
        TextView textViewAverageElevationGlobalStatistics = binding.textViewAverageElevationGlobalStatistics;
        TextView textViewAverageTimeGlobalStatistics = binding.textViewAverageTimeGlobalStatistics;
        TextView textViewAverageSpeedGlobalStatistics = binding.textViewAverageSpeedGlobalStatistics;

        TextView textViewUserDistancePerformance = binding.textViewUserDistancePerformance;
        TextView textViewUserElevationPerformance = binding.textViewUserElevationPerformance;
        TextView textViewUserTimePerformance = binding.textViewUserTimePerformance;
        TextView textViewUserSpeedPerformance = binding.textViewUserSpeedPerformance;

        Button button = binding.buttonshowglobalstatistics;
        ProgressBar bar = binding.progressBarForGlobalStatistics;

        //
        // Totals
        //
        viewmodel.getTotalDistance().observe(getViewLifecycleOwner(), new Observer<Double>() {
            @Override
            public void onChanged(Double value) {
                String text = String.valueOf(round(value));
                textViewTotalDistanceGlobalStatistics.setText(text);
            }
        });

        viewmodel.getTotalElevation().observe(getViewLifecycleOwner(), new Observer<Double>() {
            @Override
            public void onChanged(Double value) {
                String text = String.valueOf(round(value));
                textViewTotalElevationGlobalStatistics.setText(text);
            }
        });

        viewmodel.getTotalTime().observe(getViewLifecycleOwner(), new Observer<Double>() {
            @Override
            public void onChanged(Double value) {
                String text = String.valueOf(round(value));
                textViewTotalTimeGlobalStatistics.setText(text);
            }
        });

        viewmodel.getSpeed().observe(getViewLifecycleOwner(), new Observer<Double>() {
            @Override
            public void onChanged(Double value) {
                String text = String.valueOf(round(value));
                textViewSpeedGlobalStatistics.setText(text);
            }
        });

        //
        // Averages
        //
        viewmodel.getAverageDistance().observe(getViewLifecycleOwner(), new Observer<Double>() {
            @Override
            public void onChanged(Double value) {
                String text = String.valueOf(round(value));
                textViewAverageDistanceGlobalStatistics.setText(text);
            }
        });

        viewmodel.getAverageElevation().observe(getViewLifecycleOwner(), new Observer<Double>() {
            @Override
            public void onChanged(Double value) {
                String text = String.valueOf(round(value));
                textViewAverageElevationGlobalStatistics.setText(text);
            }
        });

        viewmodel.getAverageTime().observe(getViewLifecycleOwner(), new Observer<Double>() {
            @Override
            public void onChanged(Double value) {
                String text = String.valueOf(round(value));
                textViewAverageTimeGlobalStatistics.setText(text);
            }
        });

        viewmodel.getAverageSpeed().observe(getViewLifecycleOwner(), new Observer<Double>() {
            @Override
            public void onChanged(Double value) {
                String text = String.valueOf(round(value));
                textViewAverageSpeedGlobalStatistics.setText(text);
            }
        });

        //
        // User performance
        //
        viewmodel.getRelativeUserDistance().observe(getViewLifecycleOwner(), new Observer<Double>() {
            @Override
            public void onChanged(Double value) {
                String text = String.valueOf(round_percent(value));
                textViewUserDistancePerformance.setText(text);
            }
        });

        viewmodel.getRelativeUserDistance().observe(getViewLifecycleOwner(), new Observer<Double>() {
            @Override
            public void onChanged(Double value) {
                String text = String.valueOf(round(value));
                textViewUserElevationPerformance.setText(text);
            }
        });

        viewmodel.getRelativeUserTime().observe(getViewLifecycleOwner(), new Observer<Double>() {
            @Override
            public void onChanged(Double value) {
                String text = String.valueOf(round_percent(value));
                textViewUserTimePerformance.setText(text);
            }
        });

        viewmodel.getRelativeUserSpeed().observe(getViewLifecycleOwner(), new Observer<Double>() {
            @Override
            public void onChanged(Double value) {
                String text = String.valueOf(round_percent(value));
                textViewUserSpeedPerformance.setText(text);
            }
        });

        viewmodel.getFreq().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer value) {
                String text = String.valueOf(value);
                textViewGlobalFrequency.setText(text);
            }
        });



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DownloadGlobalStatisticsTask runner = new DownloadGlobalStatisticsTask(viewmodel, button, bar);
                runner.execute();
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