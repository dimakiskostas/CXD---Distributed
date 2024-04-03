package com.example.distributed.ui.Contact;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.distributed.R;
import com.example.distributed.databinding.ContentMainBinding;
import com.example.distributed.databinding.FragmentContactBinding;
import com.example.distributed.databinding.FragmentGlobalStatisticsBinding;
import com.example.distributed.ui.slideshow.GlobalStatisticsViewModel;

/**
 * A fragment representing a list of Items.
 */
public class ContactFragment extends Fragment {

    //private FragmentGlobalStatisticsBinding binding;
    private FragmentContactBinding binding;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ContactViewModel contactViewModel =
                new ViewModelProvider(this).get(ContactViewModel.class);

        binding = FragmentContactBinding.inflate(inflater, container, false);
        View root = binding.getRoot();



        //
        //Button for each sm platform
        //
        ImageButton twitter = binding.TwitterLogoButton;
        ImageButton instagram = binding.InstagramButton;
        ImageButton facebook = binding.FacebookButton;



        //Each button sends us to the sm app
        twitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoUrl("https://twitter.com/AUEB?ref_src=twsrc%5Egoogle%7Ctwcamp%5Eserp%7Ctwgr%5Eauthor");
            }

            private void gotoUrl(String s) {
                Uri uri = Uri.parse(s);
                startActivity(new Intent(Intent.ACTION_VIEW, uri));
            }
        });

        instagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoUrl("https://www.instagram.com/aueb.gr/?hl=el");
            }

            private void gotoUrl(String s) {
                Uri uri = Uri.parse(s);
                startActivity(new Intent(Intent.ACTION_VIEW, uri));
            }
        });

        facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoUrl("https://www.facebook.com/auebgreece/?locale=el_GR");
            }

            private void gotoUrl(String s) {
                Uri uri = Uri.parse(s);
                startActivity(new Intent(Intent.ACTION_VIEW, uri));
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