package com.dhara.myfancycarsapp.fancycars.viewmodel;

import android.app.Application;
import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;
import androidx.databinding.InverseBindingAdapter;
import androidx.databinding.ObservableInt;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.dhara.myfancycarsapp.R;
import com.dhara.myfancycarsapp.fancycars.model.FancyCarDetails;
import com.dhara.myfancycarsapp.fancycars.model.FancyCars;
import com.dhara.myfancycarsapp.fancycars.view.FancyCarsAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
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
    public static String sortOn;

    public FancyCarsViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
    }

    public void init() {
        adapter = new FancyCarsAdapter(R.layout.car_item, this);
        fancyCars = new FancyCars();
        loading = new ObservableInt(View.GONE);
        showEmpty = new ObservableInt(View.GONE);
        showNoInternetConnection = new ObservableInt(View.GONE);
        showList = new ObservableInt((View.VISIBLE));
    }

    public void fetchList() {
        fancyCars.fetchList();
    }

    public MutableLiveData<List<FancyCarDetails>> getCars() {
        return fancyCars.getCars();
    }

    public FancyCarsAdapter getAdapter() {
        return adapter;
    }

    public void setCarsInAdapter(List<FancyCarDetails> cars) {
        this.adapter.setFancyCars(cars);
        this.adapter.notifyDataSetChanged();
    }

    public FancyCarDetails getCarDetailsAt(Integer index) {
        if (fancyCars.getCars().getValue() != null &&
                index != null &&
                fancyCars.getCars().getValue().size() > index) {
            return fancyCars.getCars().getValue().get(index);
        }
        return null;
    }

    public int buyBtnVisibility(Integer index) {
        if (getCarDetailsAt(index).getAvailability().equals("In Dealership")) {
            return View.VISIBLE;
        } else {
            return View.GONE;
        }
    }

    public void onSortClick() {
        Timber.d("SortOn = %s", sortOn != null ? sortOn : "null");
        if (sortOn != null) {
            if (sortOn.equals("Sort by Name")) {
                Timber.d("Sort by name");
                adapter.sortBasedOnName();
            } else {
                Timber.d("Sort by availability");
                adapter.sortBasedOnAvailability();
            }
        }
    }

    @BindingAdapter({"entries"})
    public static void setEntries(Spinner spinner, List entries) {
        if (spinner != null && entries != null) {
            ArrayAdapter arrayAdapter = new ArrayAdapter(context, android.R.layout.simple_spinner_item, entries);
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(arrayAdapter);
        }
    }

    @BindingAdapter({"onItemSelected"})
    public static void setOnItemSelectedListener(final Spinner spinner, final ItemSelectedListener itemSelectedListener) {
        if (spinner != null) {
            if (itemSelectedListener == null) {
                spinner.setOnItemSelectedListener(null);
            } else {
                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(@NotNull AdapterView parent, @NotNull View view, int position, long id) {
                        Intrinsics.checkParameterIsNotNull(parent, "parent");
                        Intrinsics.checkParameterIsNotNull(view, "view");
                        if (!Intrinsics.areEqual(spinner.getTag(), position)) {
                            ItemSelectedListener listener = itemSelectedListener;
                            Object obj = parent.getItemAtPosition(position);
                            Intrinsics.checkExpressionValueIsNotNull(obj, "parent.getItemAtPosition(position)");
                            listener.onItemSelected(obj);
                        }
                    }

                    public void onNothingSelected(@NotNull AdapterView parent) {
                        Intrinsics.checkParameterIsNotNull(parent, "parent");
                    }
                });
            }
        }
    }

    @BindingAdapter({"newValue"})
    public static void setNewValue(Spinner spinner, String newValue) {
        if (spinner != null) {
            if (spinner.getAdapter() != null) {
                SpinnerAdapter adapter1 = spinner.getAdapter();
                if (adapter1 == null) {
                    throw new TypeCastException("null cannot be cast to non-null type android.widget.ArrayAdapter<kotlin.Any>");
                }

                int position = ((ArrayAdapter)adapter1).getPosition(newValue);
                spinner.setSelection(position, false);
                spinner.setTag(position);
            }
        }
    }

    @BindingAdapter({"selectedValue"})
    public static void setSelectedValue(Spinner spinner, String selectedValue) {
        if (spinner != null) {
            if (spinner.getAdapter() != null) {
                SpinnerAdapter adapter1 = spinner.getAdapter();
                if (adapter1 == null) {
                    throw new TypeCastException("null cannot be cast to non-null type android.widget.ArrayAdapter<kotlin.Any>");
                }

                int position = ((ArrayAdapter)adapter1).getPosition(selectedValue);
                spinner.setSelection(position, false);
                spinner.setTag(position);
                sortOn = selectedValue;
                Timber.d("sortOn = %s", sortOn != null ? sortOn : "null");
            }
        }
    }

    @InverseBindingAdapter(
            attribute = "selectedValue",
            event = "selectedValueAttrChanged"
    )
    public static Object getSelectedValue(Spinner spinner) {
        if (spinner != null) {
            return spinner.getSelectedItem();
        }
        return null;
    }

    public interface ItemSelectedListener {
        void onItemSelected(@NotNull Object obj1);
    }
}
