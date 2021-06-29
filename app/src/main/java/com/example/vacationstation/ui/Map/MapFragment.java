package com.example.vacationstation.ui.Map;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.vacationstation.MapItemsExample;
import com.example.vacationstation.MapViewPinExample;
import com.example.vacationstation.PermissionsRequestor;
import com.here.sdk.core.GeoCoordinates;
//import com.here.sdk.core.OnEngineInitListener;
//import com.here.sdk.core.mapping.Map;
//import com.here.sdk.core.MapFragment;
import com.here.sdk.mapview.MapError;
import com.here.sdk.mapview.MapScene;
import com.here.sdk.mapview.MapScheme;
import com.here.sdk.mapview.MapView;

import com.example.vacationstation.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MapFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MapFragment extends Fragment {

    private static final String TAG = MapFragment.class.getSimpleName();

    private PermissionsRequestor permissionsRequestor;
    private MapView mapView;
    private MapViewPinExample mapViewPinExample; //TODO: entfernen, ist unnötig
    private com.example.vacationstation.MapItemsExample mapItemsExample;

    public MapFragment() {
        // Required empty public constructor
    }

    public static MapFragment newInstance(String param1, String param2) {
        MapFragment fragment = new MapFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        // Get a MapView instance from layout.
        mapView = (MapView ) view.findViewById(R.id.map_view);  //R.id.map_view
        mapView.onCreate(savedInstanceState);

        handleAndroidPermissions();

        return view;
    }

    private void handleAndroidPermissions() {
        permissionsRequestor = new PermissionsRequestor(getActivity() );
        permissionsRequestor.request(new PermissionsRequestor.ResultListener(){

            @Override
            public void permissionsGranted() {
                loadMapScene();

            }

            @Override
            public void permissionsDenied() {
                Log.e(TAG, "Permissions denied by user.");
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        permissionsRequestor.onRequestPermissionsResult(requestCode, grantResults);
    }

    private void loadMapScene() {
        // Load a scene from the SDK to render the map with a map style.
        /*mapView.getMapScene().loadScene(MapScheme.NORMAL_DAY, mapError -> {
            if (mapError == null) {
                mapViewPinExample = new MapViewPinExample( getActivity(), mapView);
                mapViewPinExample.showAnchoredMapViewPin();
            } else {
                Log.d(TAG, "Loading map scene failed: " + mapError.toString());
            }
        });*/

        mapView.getMapScene().loadScene(MapScheme.NORMAL_DAY, new MapScene.LoadSceneCallback() {
            @Override
            public void onLoadScene(@Nullable MapError mapError) {
                if (mapError == null) {
                    mapItemsExample = new MapItemsExample(getActivity(), mapView);
                    mapItemsExample.showAnchoredMapMarkers(); //normaler pin
                    mapItemsExample.showCenteredMapMarkers();
                } else {
                    Log.d(TAG, "onLoadScene failed: " + mapError.toString());
                }
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }
}