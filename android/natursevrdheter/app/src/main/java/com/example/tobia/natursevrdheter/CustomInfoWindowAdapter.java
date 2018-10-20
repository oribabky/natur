package com.example.tobia.natursevrdheter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

public class CustomInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

    private final View mWindow;
    private Context mContext;

    CustomInfoWindowAdapter(Context context) {
        mContext = context;
        mWindow = LayoutInflater.from(context).inflate(R.layout.custom_info_window, null);
    }

    private void renderInfoWindow(Marker marker, View view) {
        String snippetContents = marker.getSnippet();
        String title = marker.getTitle();

        final ImageView imageHolder = view.findViewById(R.id.imageViewInfoWindow);
        TextView textHolder = view.findViewById(R.id.textViewInfoWindow);

        Picasso.get().load(snippetContents).into(imageHolder, new MarkerCallback(marker));
        //textHolder.setText(title);
    }

    @Override
    public View getInfoWindow(Marker marker) {
        //renderInfoWindow(marker, mWindow);
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        renderInfoWindow(marker, mWindow);
        return mWindow;
    }
}
