package com.dhara.myfancycarsapp.fancycars.view;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.dhara.myfancycarsapp.R;
import com.dhara.myfancycarsapp.databinding.FancyCarsFragmentBinding;
import com.dhara.myfancycarsapp.fancycars.model.FancyCarDetails;
import com.dhara.myfancycarsapp.fancycars.viewmodel.FancyCarsViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import timber.log.Timber;

public class FancyCarsFragment extends Fragment {

    private static final String TAG = FancyCarsFragment.class.getSimpleName();

    private FancyCarsViewModel mViewModel;
    private Context context;
    private FancyCarsFragmentBinding binding;

    private ConnectivityManager manager;

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

        manager = (ConnectivityManager) requireActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

        mViewModel.loading.set(View.VISIBLE);
        mViewModel.fetchList();
        mViewModel.getCars().observe(this, new Observer<List<FancyCarDetails>>() {
            @Override
            public void onChanged(List<FancyCarDetails> cars) {
                mViewModel.loading.set(View.GONE);
                if (cars.size() == 0) {
                    mViewModel.showEmpty.set(View.VISIBLE);
                } else {
                    mViewModel.showEmpty.set(View.GONE);
                    mViewModel.setCarsInAdapter(cars);
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if (manager != null) {
            manager.registerDefaultNetworkCallback(networkCallback);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        manager.unregisterNetworkCallback(networkCallback);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        // To remove any leaks from happening
        context = null;
    }

    /**
     * handle app when no network connection present -
     *      assumption that the app will show message that no connection available
     *      and hide the car list
     */
    private final ConnectivityManager.NetworkCallback networkCallback = new ConnectivityManager.NetworkCallback() {
        @Override
        public void onAvailable(@NotNull Network network) {
            super.onAvailable(network);
            // this ternary operation is not quite true, because non-metered doesn't yet mean, that it's wifi
            // nevertheless, for simplicity let's assume that's true
            Timber.i(TAG, "Connected to %s", (manager.isActiveNetworkMetered() ? "LTE" : "WIFI"));
            // show the list and hide the lost connection message
            mViewModel.showNoInternetConnection.set(View.GONE);
            mViewModel.showList.set(View.VISIBLE);
        }

        @Override
        public void onLost(@NotNull Network network) {
            super.onLost(network);
            Timber.i(TAG, "Losing active connection");
            // show lost connection message and hide the list
            mViewModel.showNoInternetConnection.set(View.VISIBLE);
            mViewModel.showList.set(View.GONE);
        }
    };
}
