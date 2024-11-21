package tet.oleg_zhabko.tsp.ui.points_and_maps.osmTools;



import org.osmdroid.api.IMapController;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;

public class osmToolsSetNecessaryParams {

    public static MapView addParams(MapView mMapView, IMapController mapController){
        mMapView.setTileSource(TileSourceFactory.DEFAULT_TILE_SOURCE);
        mMapView.setBuiltInZoomControls(true);
        mMapView.setMultiTouchControls(true);
        mapController = mMapView.getController();
        mapController.setZoom(18);
        GeoPoint startPoint = new GeoPoint(49.8992800, 28.6023500);
        mapController.setCenter(startPoint);
        mapController.animateTo(startPoint);

        return mMapView;
    }

}
