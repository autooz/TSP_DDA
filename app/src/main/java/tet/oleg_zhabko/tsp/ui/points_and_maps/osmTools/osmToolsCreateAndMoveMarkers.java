package tet.oleg_zhabko.tsp.ui.points_and_maps.osmTools;

import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;

import tet.oleg_zhabko.tsp.R;
import tet.oleg_zhabko.tsp.ThisApp;

public class osmToolsCreateAndMoveMarkers {

    private static MapView addMarker(MapView mMapView, Marker marker) {
        mMapView.getOverlays().clear();
        mMapView.getOverlays().add(marker);
        mMapView.invalidate();
        marker.setTitle("Center");
        return mMapView;
    }


    public static MapView setCenterOnPositionMarker(MapView mMapView, GeoPoint geopoint){

        Marker marker = new Marker(mMapView);
        marker.setPosition(geopoint);
        marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        marker.setIcon(ThisApp.getInstance().getResources().getDrawable(R.drawable.center_of_map));
       addMarker(mMapView, marker);
        return addMarker(mMapView, marker);
    }

    public static MapView  getViewWithCupentPositionMarker(MapView mMapView,  GeoPoint geopoint){
        Marker marker = new Marker(mMapView);
        marker.setPosition(geopoint);
        marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        marker.setIcon(ThisApp.getInstance().getResources().getDrawable(R.drawable.center_of_map));
        return mMapView;
    }

    public static MapView  getViewWithStartMarker(MapView mMapView, GeoPoint geopoint){
        Marker marker = new Marker(mMapView);
        marker.setPosition(geopoint);
        marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);

        return mMapView;
    }

    public static MapView  getViewWithFinishMarker(MapView mMapView,GeoPoint geopoint){
        Marker marker = new Marker(mMapView);
        marker.setPosition(geopoint);
        marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);

        return mMapView;
    }

    public static MapView  getViewWithSeriaMarkers(MapView mMapView, GeoPoint geopoint){
        Marker marker = new Marker(mMapView);
        marker.setPosition(geopoint);
        marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);


        return mMapView;
    }

}
