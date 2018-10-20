package com.example.tobia.natursevrdheter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private final String NATUR_BACKEND = "http://192.168.10.112:8080";
    private final String WATERFALL_RESOURCE = NATUR_BACKEND + "/waterfall";
    // private String naturBackend = "http://192.168.10.112:8080/?attractionType=";

    // arrays to hold google maps markers
    ArrayList<Marker> waterfallMarkers = new ArrayList<>();
    ArrayList<Marker> canyonMarkers = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // add action listeners to the switch buttons
        addSwitchListeners();
    }

    // adds action listeners to the switch buttons
    public void addSwitchListeners() {
        final Switch waterfallsSwitch = (Switch) findViewById(R.id.waterfallSwitch);
        final Switch canyonsSwitch = (Switch) findViewById(R.id.canyonSwitch);

        waterfallsSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            public void onCheckedChanged (CompoundButton buttonView,boolean isChecked){
                // do something, the isChecked will be
                // true if the switch is in the On position
                for (int i = 0; i < waterfallMarkers.size(); i++) {
                    if (isChecked) {
                        waterfallMarkers.get(i).setVisible(true);
                    }
                    else {
                        waterfallMarkers.get(i).setVisible(false);
                    }
                }
            }
        });

        canyonsSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            public void onCheckedChanged (CompoundButton buttonView,boolean isChecked){
                // do something, the isChecked will be
                // true if the switch is in the On position
                for (int i = 0; i < canyonMarkers.size(); i++) {
                    if (isChecked) {
                        canyonMarkers.get(i).setVisible(true);
                    }
                    else {
                        canyonMarkers.get(i).setVisible(false);
                    }
                }
            }
        });
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.getUiSettings().setRotateGesturesEnabled(false);
        mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
        // @TODO MOVE THIS?
        mMap.setInfoWindowAdapter(new CustomInfoWindowAdapter(MapsActivity.this));
        //mMap.setInfoWindowAdapter(new CustomInfoWindowAdapter(MapsActivity.this));

        final TextView mTextView = (TextView) findViewById(R.id.textView);
        mTextView.setText(NATUR_BACKEND);

        // set the attraction markers on the map
        setWaterfallMarkers(mMap, WATERFALL_RESOURCE);

        // turn on the switches
        final Switch waterfallsSwitch = (Switch) findViewById(R.id.waterfallSwitch);
        final Switch canyonsSwitch = (Switch) findViewById(R.id.canyonSwitch);
        waterfallsSwitch.setChecked(true);
        canyonsSwitch.setChecked(true);

        // @TODO NECESSARY?
        openInfoWindows();
    }

    // @TODO remove?
    public String getAttractionsUrl () {
        String res = NATUR_BACKEND;
        String waterfallQueryName = "Waterfall";
        String canyonQueryName = "Canyon";

        // get information on the switches
        final Switch waterfalls = (Switch) findViewById(R.id.waterfallSwitch);
        final Switch canyons = (Switch) findViewById(R.id.canyonSwitch);

        ArrayList<Switch> switches = new ArrayList<>();
        switches.add(waterfalls);
        switches.add(canyons);

        // go through the switches and build the query parameter
        boolean isFirst = true; // used to not set a comma on the first query name
        for (int i = 0; i < switches.size(); i++) {
            if (switches.get(i).isChecked()) {
                if (isFirst) {
                    res += switches.get(i).getContentDescription();
                    isFirst = false;
                }
                else {
                    res += "," + switches.get(i).getContentDescription();
                }
            }
        }
        return res;
    }

    public void setWaterfallMarkers(GoogleMap googleMap, String url) {
        mMap = googleMap;

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);

        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(Request.Method.GET,url,null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Double lat;
                double lon;
                String attractionName;
                String imageURL;
                try {
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject waterfall = response.getJSONObject(i);

                        lat = waterfall.getDouble("latitude");
                        lon = waterfall.getDouble("longitude");
                        attractionName = waterfall.getString("name");
                        imageURL = waterfall.getString("imageURL");

                        // Add a marker on the map
                        LatLng pos = new LatLng(lat, lon);
                        waterfallMarkers.add(
                                mMap.addMarker(new MarkerOptions()
                                        .position(pos)
                                        .title(attractionName)
                                        .snippet(imageURL))
                        );
                    }
                } catch (JSONException e) {
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("BackendConnectioError", error.getMessage());
                error.getMessage();
            }
        });

        // Add the request to the RequestQueue.
        queue.add(jsonArrayRequest);
    }

    public void openInfoWindows() {
        for (int i = 0; i < waterfallMarkers.size(); i++) {
            waterfallMarkers.get(i).showInfoWindow();
            //waterfallMarkers.get(i).hideInfoWindow();
        }
        for (int i = 0; i < canyonMarkers.size(); i++) {
            canyonMarkers.get(i).showInfoWindow();
            //canyonMarkers.get(i).hideInfoWindow();
        }
    }
}
