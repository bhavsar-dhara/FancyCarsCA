package com.dhara.myfancycarsapp.fancycars.view;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import com.dhara.myfancycarsapp.BR;
import com.dhara.myfancycarsapp.fancycars.model.FancyCarDetails;
import com.dhara.myfancycarsapp.fancycars.viewmodel.FancyCarsViewModel;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class FancyCarsAdapter extends RecyclerView.Adapter<FancyCarsAdapter.GenericViewHolder> {

    private static final String TAG = FancyCarsAdapter.class.getSimpleName();

    private int layoutId;
    private List<FancyCarDetails> cars;
    private FancyCarsViewModel viewModel;

    public FancyCarsAdapter(@LayoutRes int layoutId, FancyCarsViewModel viewModel) {
        this.layoutId = layoutId;
        this.viewModel = viewModel;
    }

    private int getLayoutIdForPosition(int position) {
        return layoutId;
    }

    @Override
    public int getItemCount() {
        return cars == null ? 0 : cars.size();
    }

    public GenericViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ViewDataBinding binding = DataBindingUtil.inflate(layoutInflater, viewType, parent, false);

        return new GenericViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull GenericViewHolder holder, int position) {
        holder.bind(viewModel, position);
    }

    @Override
    public int getItemViewType(int position) {
        return getLayoutIdForPosition(position);
    }

    public void setFancyCars(List<FancyCarDetails> cars) {
        this.cars = cars;
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
            binding.executePendingBindings();
        }
    }

    // Comparator for Ascending Order based on Name
    private Comparator<FancyCarDetails> NameAscComparator = new Comparator<FancyCarDetails>() {
        public int compare(FancyCarDetails name1, FancyCarDetails name2) {
            return (name1.getName()).compareToIgnoreCase(name2.getName());
        }
    };

    public void sortBasedOnName() {
        Collections.sort(cars, NameAscComparator);
        notifyDataSetChanged();
    }

    // Comparator for Ascending Order based on Availability
    public Comparator<FancyCarDetails> AvailabilityAscComparator = new Comparator<FancyCarDetails>() {
        public int compare(FancyCarDetails availability1, FancyCarDetails availability2) {
            return (availability1.getAvailability()).compareToIgnoreCase(availability2.getAvailability());
        }
    };

    public void sortBasedOnAvailability() {
        Collections.sort(cars, AvailabilityAscComparator);
        notifyDataSetChanged();
    }
}
