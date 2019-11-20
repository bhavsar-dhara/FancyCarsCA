package com.dhara.myfancycarsapp.fancycars.viewmodel;

import android.app.Application;
import android.content.Context;
import android.view.View;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableInt;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.dhara.myfancycarsapp.R;
import com.dhara.myfancycarsapp.fancycars.model.FancyCarDetails;
import com.dhara.myfancycarsapp.fancycars.model.FancyCars;
import com.dhara.myfancycarsapp.fancycars.view.FancyCarsAdapter;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

public class FancyCarsViewModel extends AndroidViewModel {

    private static final String TAG = FancyCarsViewModel.class.getSimpleName();

    private static Context context;
    private FancyCarsAdapter adapter;
    private FancyCars fancyCars;
    public ObservableInt loading;
    public ObservableInt showEmpty;
    public ObservableInt showNoInternetConnection;
    public ObservableInt showList;

    public FancyCarsViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
    }

    /**
     * function to initiate the viewModel
     */
    public void init() {
        adapter = new FancyCarsAdapter(R.layout.car_item, this);
        fancyCars = new FancyCars();
        loading = new ObservableInt(View.GONE);
        showEmpty = new ObservableInt(View.GONE);
        showNoInternetConnection = new ObservableInt(View.GONE);
        showList = new ObservableInt((View.VISIBLE));
    }

    /**
     * function to fetch the cars after making the API call
     */
    public void fetchList() {
        fancyCars.fetchList();
    }

    /**
     * getter function
     * @return MutableLiveData<List<FancyCarDetails>>
     */
    public MutableLiveData<List<FancyCarDetails>> getCars() {
        return fancyCars.getCars();
    }

    /**
     * getter function
     * @return List<FancyCarDetails>
     */
    public List<FancyCarDetails> getCarsList() {
        return fancyCars.getCarsCopyList();
    }

    /**
     * function to access the adapter object
     * @return FancyCarsAdapter
     */
    public FancyCarsAdapter getAdapter() {
        return adapter;
    }

    /**
     * function to set the updated carsList to the adapter for viewing
     * @param cars List<FancyCarDetails>
     */
    public void setCarsInAdapter(List<FancyCarDetails> cars) {
        this.adapter.setFancyCars(fancyCars.getCarsCopyList());
        this.adapter.notifyDataSetChanged();
    }

    /**
     * function to fetch details of a particular car from the list
     * @param index Integer (position of the item in the list)
     * @return FancyCarDetails
     */
    public FancyCarDetails getCarDetailsAt(Integer index) {
        if (fancyCars.getCars().getValue() != null &&
                index != null &&
                fancyCars.getCars().getValue().size() > index) {
            return fancyCars.getCars().getValue().get(index);
        }
        return null;
    }

    /**
     * function to set the visibility of the "buy" button
     * @param index Integer (position of the item in the list)
     * @return int View.VISIBLE or View.GONE
     */
    public int buyBtnVisibility(Integer index) {
        if (getCarDetailsAt(index) != null &&
                getCarDetailsAt(index).getAvailability().equals("In Dealership")) {
            return View.VISIBLE;
        } else {
            return View.GONE;
        }
    }

    /**
     * function call on click on the spinner item
     * @param parent AdapterView<?>
     * @param view View
     * @param pos int
     * @param id long
     */
    public void onSelectItem(AdapterView<?> parent, View view, int pos, long id) {
//        Timber.d("position = %s", pos);
//        Timber.d("selected = %s", parent.getAdapter().getItem(pos).toString());
        if (parent.getAdapter().getItem(pos).toString().equals("Sort by Name")) {
            Timber.d("Sort by name");
            adapter.sortBasedOnName();
        } else if (parent.getAdapter().getItem(pos).toString().equals("Sort by Availability")) {
            Timber.d("Sort by availability");
            adapter.sortBasedOnAvailability();
        } else {
            Timber.d("Sort List");
            // Hint - do nothing
        }
    }
}
