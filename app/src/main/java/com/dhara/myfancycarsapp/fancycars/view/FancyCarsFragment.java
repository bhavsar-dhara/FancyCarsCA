package com.dhara.myfancycarsapp.fancycars.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.dhara.myfancycarsapp.R;
import com.dhara.myfancycarsapp.fancycars.viewmodel.FancyCarsViewModel;

public class FancyCarsFragment extends Fragment {

    private FancyCarsViewModel mViewModel;

    public static FancyCarsFragment newInstance() {
        return new FancyCarsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fancy_cars_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(FancyCarsViewModel.class);
        // TODO: Use the ViewModel
    }

}
