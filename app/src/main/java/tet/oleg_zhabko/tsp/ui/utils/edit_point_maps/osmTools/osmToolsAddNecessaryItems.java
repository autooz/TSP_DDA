package tet.oleg_zhabko.tsp.ui.utils.edit_point_maps.osmTools;

import android.content.Context;

import org.osmdroid.events.MapListener;
import org.osmdroid.events.ScrollEvent;
import org.osmdroid.events.ZoomEvent;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.compass.CompassOverlay;
import org.osmdroid.views.overlay.compass.InternalCompassOrientationProvider;

import tet.tetlibrarymodules.tetdebugutils.debug.debug_tools.TetDebugUtil;

public class osmToolsAddNecessaryItems {

    public static MapView add(MapView mMapView, Context context) {

        CompassOverlay mCompassOverlay = new CompassOverlay(context, new InternalCompassOrientationProvider(context), mMapView);
        mCompassOverlay.enableCompass();
        mMapView.getOverlays().add(mCompassOverlay);


        mMapView.setMapListener(new MapListener() {
            @Override
            public boolean onScroll(ScrollEvent event) {
                TetDebugUtil.d("Script", "onScroll()");
                return false;
            }

            @Override
            public boolean onZoom(ZoomEvent event) {
                TetDebugUtil.d("Script", "onZoom()");
                return false;
            }
        });
      //  LatLonGridlineOverlay2 overlay = new LatLonGridlineOverlay2();
      //  mMapView.getOverlays().add(overlay);

        return mMapView;
    }


}
