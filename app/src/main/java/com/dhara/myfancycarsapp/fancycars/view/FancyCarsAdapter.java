package com.dhara.myfancycarsapp.fancycars.view;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import com.dhara.myfancycarsapp.BR;
import com.dhara.myfancycarsapp.R;
import com.dhara.myfancycarsapp.fancycars.model.FancyCarDetails;
import com.dhara.myfancycarsapp.fancycars.viewmodel.FancyCarsViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import timber.log.Timber;

public class FancyCarsAdapter extends RecyclerView.Adapter<FancyCarsAdapter.GenericViewHolder> {

    private static final String TAG = FancyCarsAdapter.class.getSimpleName();

    private int layoutId;
    private List<FancyCarDetails> cars = new ArrayList<>();
    private FancyCarsViewModel viewModel;

    public FancyCarsAdapter(@LayoutRes int layoutId, FancyCarsViewModel viewModel) {
        this.layoutId = layoutId;
        this.viewModel = viewModel;
    }

    @NotNull
    public GenericViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ViewDataBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.car_item, parent, false);

        return new GenericViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull GenericViewHolder holder, int position) {
        holder.bind(viewModel, position);
    }

    class GenericViewHolder extends RecyclerView.ViewHolder {
        final ViewDataBinding binding;

        GenericViewHolder(ViewDataBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(FancyCarsViewModel viewModel, Integer position) {
            binding.setVariable(BR.viewModel, viewModel);
            binding.setVariable(BR.position, position);
            String availability = viewModel.getCarAvailability(position);
            binding.setVariable(BR.carAvailability, availability);
            cars.get(position).setAvailability(availability);
            binding.executePendingBindings();
        }
    }

    @Override
    public int getItemCount() {
        return cars == null ? 0 : cars.size();
    }

    @Override
    public int getItemViewType(int position) {
        return getLayoutIdForPosition(position);
    }

    public void setFancyCars(List<FancyCarDetails> cars) {
        this.cars = cars;
    }

    private int getLayoutIdForPosition(int position) {
        return layoutId;
    }

    /**
     * Comparator for Ascending Order based on Name
     */
    private Comparator<FancyCarDetails> NameAscComparator = new Comparator<FancyCarDetails>() {
        public int compare(FancyCarDetails name1, FancyCarDetails name2) {
            return (name1.getName()).compareToIgnoreCase(name2.getName());
        }
    };

    public void sortBasedOnName() {
        Timber.d("sortBasedOnName: ");
        Collections.sort(cars, NameAscComparator);
        notifyDataSetChanged();
    }

    /**
     * Comparator for Ascending Order based on Availability
     */
    private Comparator<FancyCarDetails> AvailabilityAscComparator = new Comparator<FancyCarDetails>() {
        public int compare(FancyCarDetails availability1, FancyCarDetails availability2) {
            return (availability1.getAvailability()).compareToIgnoreCase(availability2.getAvailability());
        }
    };

    public void sortBasedOnAvailability() {
        Timber.d("sortBasedOnAvailability: ");
        Collections.sort(cars, AvailabilityAscComparator);
        notifyDataSetChanged();
    }
}
