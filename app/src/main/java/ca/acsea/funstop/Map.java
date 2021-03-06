package ca.acsea.funstop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import android.os.Bundle;

import android.text.Html;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

//import java.util.ArrayList;


public class Map extends Fragment implements OnMapReadyCallback, GoogleMap.OnMapClickListener {

    private MapView mapView;
    protected int mapCode;


    public Map() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(Html.fromHtml("<font color='#e6b773'>Map</font>"));

        View view = inflater.inflate(R.layout.fragment_map, container, false);

        // Gets the MapView from the XML layout and creates it
        mapView = (MapView) view.findViewById(R.id.googleMap);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        return view;
    }

    public void onMapReady(GoogleMap googleMap) {
        mapCode = 0; // 0 is Vancouver, 1 is Toronto

        MarkerOptions eventMarker = new MarkerOptions();
        if (Globals.getInstance().getData() == 0){ //Vancouver
            eventMarker.position(new LatLng(49.283157, -123.119871));
            eventMarker.title("Vancouver Art Gallery - LunarFest Celebrations");
            eventMarker.snippet("Jan 25 ~ Jan 26");
            googleMap.addMarker(eventMarker);

            eventMarker.position(new LatLng(49.232469, -123.117416));
            eventMarker.title("Oakridge Centre - LunarFest Visual Arts");
            eventMarker.snippet("Jan 16 ~ Feb 10");
            googleMap.addMarker(eventMarker);

            eventMarker.position(new LatLng(49.289448, -123.117141));
            eventMarker.title("Jack Poole Plaza/Lot19 - Coastal Lunar Lanterns");
            eventMarker.snippet("Jan 18 ~ Feb 9");
            googleMap.addMarker(eventMarker);

            eventMarker.position(new LatLng(49.280505, -123.112767));
            eventMarker.title("Queen Elizabeth Theatre - A Musical Banquet");
            eventMarker.snippet("Jan 25, 7:30PM");
            googleMap.addMarker(eventMarker);

            LatLng centerLocation = new LatLng(49.260572, -123.127250);
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(centerLocation, 13));
        } else { //Toronto
            eventMarker.position(new LatLng(43.589701, -79.645842));
            eventMarker.title("Living Arts Centre - LunarFest Celebrations");
            eventMarker.snippet("Feb 1, 1PM ~ 6PM");
            googleMap.addMarker(eventMarker);

            eventMarker.position(new LatLng(43.869716, -79.312400));
            eventMarker.title("Varley Art Gallery of Markham - LunarFest Celebrations");
            eventMarker.snippet("Feb 2, 11AM ~ 4PM");
            googleMap.addMarker(eventMarker);

            LatLng centerLocation = new LatLng(43.708336, -79.491058);
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(centerLocation, 10));
        }
    }

    @Override
    public void onResume() {
        mapView.onResume();
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onMapClick(LatLng latLng) {

    }
}