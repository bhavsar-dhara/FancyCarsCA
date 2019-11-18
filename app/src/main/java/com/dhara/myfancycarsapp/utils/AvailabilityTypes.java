package com.dhara.myfancycarsapp.utils;

import androidx.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class AvailabilityTypes {

    @StringDef({
            IN_DEALERSHIP,
            OUT_OF_STOCK,
            UNAVAILABLE
    })

    @Retention(RetentionPolicy.SOURCE)
    public @interface AvailabilityType { }
    public static final String IN_DEALERSHIP = "In Dealership";
    public static final String OUT_OF_STOCK = "Out Of Stock";
    public static final String UNAVAILABLE = "Unavailable";
}
