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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dhara.myfancycarsapp.R;
import com.dhara.myfancycarsapp.databinding.FancyCarsFragmentBinding;
import com.dhara.myfancycarsapp.fancycars.model.FancyCarDetails;
import com.dhara.myfancycarsapp.fancycars.viewmodel.FancyCarsViewModel;
import com.dhara.myfancycarsapp.utils.EndlessRecyclerViewScrollListener;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import timber.log.Timber;

/**
 * @link https://androidwave.com/android-data-binding-recyclerview/ (endless scrolling data requirements)
 */
public class FancyCarsFragment extends Fragment {

    private static final String TAG = FancyCarsFragment.class.getSimpleName();

    private FancyCarsViewModel mViewModel;
    private Context context;
    private FancyCarsFragmentBinding binding;

    private ConnectivityManager manager;
    private EndlessRecyclerViewScrollListener scrollListener;

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

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        binding.carList.setLayoutManager(linearLayoutManager);
        // Triggered only when new data needs to be appended to the list
        // Add whatever code is needed to append new items to the bottom of the list
        // Store a member variable for the listener
        scrollListener = new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to the bottom of the list
                Timber.d("onLoadMore: ");
                loadNextDataFromApi(page);
            }
        };

        // Adds the scroll listener to RecyclerView
        binding.carList.addOnScrollListener(scrollListener);
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
        scrollListener = null;
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

    // Append the next page of data into the adapter
    // This method probably sends out a network request and appends new data items to your adapter.
    private void loadNextDataFromApi(int offset) {
        // Send an API request to retrieve appropriate paginated data
        //  --> Send the request including an offset value (i.e `page`) as a query parameter.
        //  --> Deserialize and construct new model objects from the API response
        //  --> Append the new data objects to the existing set of items inside the array of items
        //  --> Notify the adapter of the new items made with `notifyItemRangeInserted()`


        // but since we are using the mock data with no page offset -
        // assumed here to call the same list again and again
        mViewModel.fetchList();
    }

    // Method to reset the endless scroll state - if required on sorting clicked?
    private void resetRecyclerView() {
        // 1. First, clear the array of data
        mViewModel.getCarsList().clear();
        mViewModel.setCarsInAdapter(mViewModel.getCarsList());
        // 2. Notify the adapter of the update
        mViewModel.getAdapter().notifyDataSetChanged(); // or notifyItemRangeRemoved
        // 3. Reset endless scroll listener when performing a new search
        scrollListener.resetState();
    }
}
