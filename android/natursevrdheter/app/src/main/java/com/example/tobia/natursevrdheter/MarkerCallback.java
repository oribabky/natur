package com.example.tobia.natursevrdheter;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.google.android.gms.maps.model.Marker;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import javax.security.auth.callback.Callback;

public class MarkerCallback implements com.squareup.picasso.Callback {

    Marker marker = null;

    MarkerCallback(Marker marker) {
        this.marker = marker;
    }

    @Override
    public void onSuccess() {
        if (marker != null && marker.isInfoWindowShown()) {
            marker.hideInfoWindow();
            marker.showInfoWindow();
        }
    }

    @Override
    public void onError(Exception e) {
        Log.e(getClass().getSimpleName(), "Error loading thumbnail!");
    }
}
