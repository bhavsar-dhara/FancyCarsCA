package com.dhara.myfancycarsapp.fancycars.view;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.dhara.myfancycarsapp.R;
import com.dhara.myfancycarsapp.databinding.FancyCarsFragmentBinding;
import com.dhara.myfancycarsapp.fancycars.viewmodel.FancyCarsViewModel;

public class FancyCarsFragment extends Fragment {

    private FancyCarsViewModel mViewModel;
    private Context context;
    private FancyCarsFragmentBinding binding;


    public static FancyCarsFragment newInstance() {
        return new FancyCarsFragment();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil
                .inflate(inflater, R.layout.fancy_cars_fragment, container, false);

        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders
                .of(this)
                .get(FancyCarsViewModel.class);

        if (savedInstanceState == null) {
            mViewModel.init();
        }

        binding.setModel(mViewModel);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        // To remove any leaks from happening
        context = null;

    }

}
