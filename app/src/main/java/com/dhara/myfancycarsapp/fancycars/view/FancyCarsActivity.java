package com.dhara.myfancycarsapp.fancycars.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.dhara.myfancycarsapp.R;

public class FancyCarsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fancy_cars_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, FancyCarsFragment.newInstance())
                    .commitNow();
        }
    }
}
