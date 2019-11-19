package com.dhara.myfancycarsapp.fancycars.view;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.dhara.myfancycarsapp.R;
import com.dhara.myfancycarsapp.fancycars.viewmodel.FancyCarsViewModel;

public class FancyCarsActivity extends AppCompatActivity {

    private static final String TAG = FancyCarsActivity.class.getSimpleName();

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
            viewModel.init();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
