package com.example.distributed.ui.home;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.distributed.R;
import com.example.distributed.databinding.FragmentHomeBinding;
import com.example.distributed.tasks.DownloadGpxStatisticsTask;
import com.example.distributed.tasks.DownloadUserStatisticsTask;

import java.io.InputStream;

import configuration.Config;
import gpxs.GpxsParser;
import model.SampleList;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    private double round(double value) {
        return (double)Math.round(value * 1000d) / 1000d;
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel viewmodel = new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);

        View view = inflater.inflate(R.layout.fragment_user_statistics, container, false);
        View root = binding.getRoot();

        //
        //All the tools
        //
        final Spinner spinner = binding.spinner;
        final Button button = binding.buttonSendFile;
        final ProgressBar bar = binding.progressBarForGpx;
        TextView textViewUsername = binding.textViewUsername;

        textViewUsername.setText(Config.username);


        TextView textViewTotalDistanceValueGpxStatistics = binding.textViewTotalDistanceValue;
        TextView textViewTotalElevationValueGpxStatistics = binding.textViewTotalElevationValue;
        TextView textViewTotalTimeValueGpxStatistics = binding.textViewTotalTimeValue;
        TextView textViewAverageSpeedValueGpxStatistics = binding.textViewAverageSpeedValue;


        textViewUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String s= charSequence.toString();
                if (!s.trim().isEmpty()) {
                    Config.username = s;
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String data = spinner.getSelectedItem().toString();
                viewmodel.getSelectFilename().postValue(data);

                Log.i("HomeFragment", "Selected filename updated to view model");
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //
        // Totals
        //
        viewmodel.getTotalDistance().observe(getViewLifecycleOwner(), new Observer<Double>() {
            @Override
            public void onChanged(Double value) {
                String text = String.valueOf(round(value));
                textViewTotalDistanceValueGpxStatistics.setText(text);
            }
        });

        viewmodel.getTotalElevation().observe(getViewLifecycleOwner(), new Observer<Double>() {
            @Override
            public void onChanged(Double value) {
                String text = String.valueOf(round(value));
                textViewTotalElevationValueGpxStatistics.setText(text);
            }
        });

        viewmodel.getTotalTime().observe(getViewLifecycleOwner(), new Observer<Double>() {
            @Override
            public void onChanged(Double value) {
                String text = String.valueOf(round(value));
                textViewTotalTimeValueGpxStatistics.setText(text);
            }
        });

        viewmodel.getSpeed().observe(getViewLifecycleOwner(), new Observer<Double>() {
            @Override
            public void onChanged(Double value) {
                String text = String.valueOf(round(value));
                textViewAverageSpeedValueGpxStatistics.setText(text);
            }
        });

        //
        // Buttons
        //

        button.setOnClickListener(view1 -> {
            Log.i("HomeFragment", "Selected filename retrieved from view model");
            String data = viewmodel.getSelectFilename().getValue();
            Toast.makeText(getContext(), "You selected: "  + data, Toast.LENGTH_LONG).show();

            try {
                InputStream inputStream = null;

                if (data.equalsIgnoreCase("Route 1")) {
                    inputStream = this.getResources().openRawResource(R.raw.route1);
                }

                if (data.equalsIgnoreCase("Route 2")) {
                    inputStream = this.getResources().openRawResource(R.raw.route2);
                }

                if (data.equalsIgnoreCase("Route 3")) {
                    inputStream = this.getResources().openRawResource(R.raw.route3);
                }

                if (data.equals("Route 4")) {
                    inputStream = this.getResources().openRawResource(R.raw.route4);
                }

                if (data.equalsIgnoreCase("Route 5")) {
                    inputStream = this.getResources().openRawResource(R.raw.route5);
                }

                if (data.equalsIgnoreCase("Route 6")) {
                    inputStream = this.getResources().openRawResource(R.raw.route6);
                }

                if (data.equalsIgnoreCase("Segment 1")) {
                    inputStream = this.getResources().openRawResource(R.raw.segment1);
                }

                if (data.equalsIgnoreCase("Segment 2")) {
                    inputStream = this.getResources().openRawResource(R.raw.segment2);
                }

                if (inputStream == null) {
                    return;
                }

                GpxsParser parser = new GpxsParser();
                SampleList workoutSamples = parser.loadFromList(inputStream);

                DownloadGpxStatisticsTask runner = new DownloadGpxStatisticsTask(viewmodel, button, bar, workoutSamples);
                runner.execute();
            } catch (Exception ex) {
                Toast.makeText(getContext(), ex.getMessage(), Toast.LENGTH_LONG).show();
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