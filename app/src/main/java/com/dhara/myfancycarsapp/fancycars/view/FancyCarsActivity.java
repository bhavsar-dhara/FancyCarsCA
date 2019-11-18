package com.dhara.myfancycarsapp.fancycars.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import com.dhara.myfancycarsapp.R;
import com.dhara.myfancycarsapp.fancycars.viewmodel.FancyCarsViewModel;

public class FancyCarsActivity extends AppCompatActivity {

    FancyCarsViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.fancy_cars_activity);

        viewModel = ViewModelProviders
                .of(this)
                .get(FancyCarsViewModel.class);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, FancyCarsFragment.newInstance())
                    .commitNow();
        }
    }

    // TODO: on back press exit app; do not show the MainActivity loading screen


    // TODO: handle app when no network connection present
}
