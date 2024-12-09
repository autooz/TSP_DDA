package tet.oleg_zhabko.tsp.ui.utils.edit_point_maps;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.Criteria;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;


import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.events.MapListener;
import org.osmdroid.events.ScrollEvent;
import org.osmdroid.events.ZoomEvent;
import org.osmdroid.library.BuildConfig;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.OverlayItem;
import org.osmdroid.views.overlay.compass.CompassOverlay;
import org.osmdroid.views.overlay.compass.InternalCompassOrientationProvider;
import org.osmdroid.views.overlay.gridlines.LatLonGridlineOverlay2;

import java.util.ArrayList;

import tet.oleg_zhabko.tsp.MainActivity;
import tet.oleg_zhabko.tsp.R;
import tet.oleg_zhabko.tsp.datas.GlobalDatas;
import tet.oleg_zhabko.tsp.ui.utils.edit_point_maps.gpsTools.GpsManager;
import tet.oleg_zhabko.tsp.ui.utils.edit_point_maps.osmTools.osmToolsAddNecessaryItems;
import tet.oleg_zhabko.tsp.ui.utils.edit_point_maps.osmTools.osmToolsCreateAndMoveMarkers;
import tet.oleg_zhabko.tsp.ui.utils.edit_point_maps.osmTools.osmToolsSetNecessaryParams;
import tet.oleg_zhabko.tsp.ui.utils.PermissionUtils;
import tet.tetlibrarymodules.tetdebugutils.debug.CrashAppExceptionHandler;
import tet.tetlibrarymodules.tetdebugutils.debug.debug_tools.TetDebugUtil;

public class ActivityOsmOnline extends Activity implements GpsManager.OnLocationReceivedListener {
 private String pseudo_tag = ActivityOsmOnline.class.getSimpleName();
    private MapView mMapView = null;
    private IMapController mapController;
    private ArrayList<OverlayItem> items = new ArrayList<OverlayItem>();
    private static final int REQUEST_CODE_PERMISSIONS = 1;
    private static final int POINT_CENTER = 0;
    private static final int POINT_START = 1;
    private static final int POINT_INTERMEDIATE = 2;
    private static final int POINT_FINISH = 3;
    private static final int POINT_CURRENT = 4;
    private LocationManager locationManager;
    private Criteria criteria;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Thread.setDefaultUncaughtExceptionHandler(new CrashAppExceptionHandler(this));
        Context ctx = getApplicationContext();
        Configuration.getInstance().setUserAgentValue(BuildConfig.APPLICATION_ID);

        setContentView(R.layout.activity_main_osm);


        mMapView = (MapView) findViewById(R.id.mapOsm);
        mapController = mMapView.getController();

        PermissionUtils.checkAndRequestPermissions(this, this);
        /* -----------------------------------------------------------------------------*/
//        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions((Activity) this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 101);
//
//
//        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
//        }
//        startGpsMonitoring();
        /* -----------------------------------------------------------------------------*/



        mMapView = osmToolsSetNecessaryParams.addParams(mMapView, mapController);

        GeoPoint startPoint = GlobalDatas.startGeoPoint;
        mMapView = osmToolsCreateAndMoveMarkers.setCenterOnPositionMarker(mMapView, startPoint);
        mMapView = osmToolsAddNecessaryItems.add(mMapView, this);

        // addNecessaryItems();

    }

//    private void startGpsMonitoring() {
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
//            return;
//        }
//
//    }
   // https://www.howtodoandroid.com/get-current-location-android/




    private void addNecessaryItems(){
        CompassOverlay mCompassOverlay = new CompassOverlay(this, new InternalCompassOrientationProvider(this), mMapView);
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


        LatLonGridlineOverlay2 overlay = new LatLonGridlineOverlay2();
        mMapView.getOverlays().add(overlay);
        // loadKml();
    }


    public void onResume() {
        super.onResume();
        //this will refresh the osmdroid configuration on resuming.
        //if you make changes to the configuration, use
        //SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        //Configuration.getInstance().load(this, PreferenceManager.getDefaultSharedPreferences(this));
        if (mMapView != null)
            mMapView.onResume(); //needed for compass, my location overlays, v6.0.0 and up
    }

    public void onPause() {
        super.onPause();
        //this will refresh the osmdroid configuration on resuming.
        //if you make changes to the configuration, use
        //SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        //Configuration.getInstance().save(this, prefs);
        if (mMapView != null)
            mMapView.onPause();  //needed for compass, my location overlays, v6.0.0 and up
    }

    @Override
    public void onDestroy(){
        https://stackoverflow.com/questions/52431299/how-can-i-use-osm-in-android-studio-to-show-a-map
        super.onDestroy();//`enter code here`
//        if (locationManager != null){
//            locationManager.removeUpdates(this);
//        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        this.finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (GpsManager.handleGpsSettingsResult(requestCode, resultCode, this)){

        } else {
            Toast.makeText(this, "GPS is still disabled.", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        TetDebugUtil.e(pseudo_tag," permissions.lenz =="+ permissions.length+"");
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        TetDebugUtil.e(pseudo_tag,"Start  handlerOnRequestPermissionsResult "+permissions+" and grantResults "+grantResults+"");
        PermissionUtils.handlerOnRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onGetLocation(Location location) {
        float accuracy = location.getAccuracy();
        double lat = location.getLatitude();
        double lon = location.getLongitude();

        GeoPoint coordinates = new GeoPoint(lat,lon);
       Toast.makeText(this,"NEW GPS \n  Lat ="+lat+" lon="+lon+"",Toast.LENGTH_LONG);

        osmToolsCreateAndMoveMarkers.setCenterOnPositionMarker(mMapView,coordinates);
    }

    @Override
    public void onGpsStatusChanged(String provider, int status) {

    }

    @Override
    public void GpsDisabled(String provider) {

    }

}