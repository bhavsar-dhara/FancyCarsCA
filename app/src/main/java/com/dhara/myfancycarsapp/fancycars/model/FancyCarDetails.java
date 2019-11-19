package com.dhara.myfancycarsapp.fancycars.model;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

public class FancyCarDetails {

    private int id;
    private String picture;
    private String name;
    private String make;
    private String model;
    private String availability;
    private int year;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @BindingAdapter({"picture"})
    public static void loadImage(ImageView view, String imageUrl) {
        Glide.with(view.getContext())
                .load(imageUrl)
//                .apply(RequestOptions.centerCropTransform())
                .into(view);
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}

/* JSON GENERATOR
[
  '{{repeat(45)}}',
  {
    id: '{{index()}}',
    picture: 'http://placehold.it/108x108?text=MyFancyCars.ca',
    name: '{{random("My Fancy Car 1", "My Fancy Car 2", "My Fancy Car 3", "My Fancy Car 4", "My Fancy Car 5")}}',
    make: '{{random("MyMake1", "MyMake2", "MyMake3", "MyMake4", "MyMake5")}}',
    model: '{{random("MyModel1", "MyModel2", "MyModel3", "MyModel4", "MyModel5")}}',
    availability: '{{random("In Dealership", "Out Of Stock", "Unavailable")}}',
    year: '{{date(new Date(2004, 0, 1), new Date(), "YYYY")}}'
  }
]
*/
