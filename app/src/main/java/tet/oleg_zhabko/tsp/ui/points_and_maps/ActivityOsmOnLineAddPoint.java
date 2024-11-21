package tet.oleg_zhabko.tsp.ui.points_and_maps;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.osmdroid.config.Configuration;
import org.osmdroid.library.BuildConfig;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;

import tet.oleg_zhabko.tsp.R;
import tet.oleg_zhabko.tsp.ThisApp;
import tet.oleg_zhabko.tsp.datas.GlobalDatas;
import tet.oleg_zhabko.tsp.ui.autonom.AddNewPointOwnPoint;
import tet.oleg_zhabko.tsp.ui.autonom.SaleManActivity;
import tet.oleg_zhabko.tsp.ui.autonom.ZoneActivity;
import tet.oleg_zhabko.tsp.ui.points_and_maps.gpsTools.GpsManager;
import tet.oleg_zhabko.tsp.ui.utils.AddPointDialogClass;
import tet.oleg_zhabko.tsp.ui.utils.PermissionUtils;
import tet.tetlibrarymodules.tetdebugutils.debug.CrashAppExceptionHandler;
import tet.tetlibrarymodules.tetdebugutils.debug.debug_tools.TetDebugUtil;

public class ActivityOsmOnLineAddPoint extends Activity implements GpsManager.OnLocationReceivedListener {

    private String pseudo_tag = ActivityOsmOnLineAddPoint.class.getSimpleName();

    private MapView mapView;
    private Button btn_stop;
    private Button saveButton;
    private Button btn_start;
    private float acurracy = 0.0f;
    private TextView minAccurracyTX;
    private TextView stopedGPS;
    private String who;
    private String org;
    private String saleman;
    private String zone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Thread.setDefaultUncaughtExceptionHandler(new CrashAppExceptionHandler(this));
        ThisApp.getInstance().adjastFontScale();

        TetDebugUtil.e(pseudo_tag, "!== START " + pseudo_tag + " Activ zone="+GlobalDatas.zoneName+" ActivSale="+GlobalDatas.saleManName+"============");
        Configuration.getInstance().setUserAgentValue(BuildConfig.APPLICATION_ID);
        Intent intent = getIntent();

        intentManipulation(intent);

        setContentView(R.layout.activity_osm_on_line_add_point);

        mapView = findViewById(R.id.mapViewOsmAddPoint);
        btn_stop = (Button) findViewById(R.id.stop_coordinates);
        saveButton = (Button) findViewById(R.id.btn_save_coordinates);
        btn_start = (Button) findViewById(R.id.start_current_coord);
        minAccurracyTX = (TextView) findViewById(R.id.tvLowAccurracy);
        stopedGPS = (TextView) findViewById(R.id.txtGpsIsOff);
     setVisibilityAllertGpsStoped();

        // Initializing the card
        mapView.setTileSource(TileSourceFactory.MAPNIK);
        mapView.setMultiTouchControls(true);
        mapView.getController().setZoom(21.0);
        GeoPoint startPoint = new GeoPoint(48.8583, 2.2944);
        mapView.getController().setCenter(startPoint);

        if(acurracy > GlobalDatas.minGpsAccurracy || acurracy == 0.0f){
            minAccurracyTX.setVisibility(View.VISIBLE);
            CharSequence chars = getResources().getString(R.string.txtLowAccurracy)+" = "+acurracy+"";
            minAccurracyTX.setText(chars);
        } else {
            minAccurracyTX.setVisibility(View.GONE);
        }



        PermissionUtils.checkAndRequestPermissions(this,this);

        if (GpsManager.checkAndEnableGps(this)){

            GpsManager.startMonitoring(this, this, 0.1f);
        } else {
            Toast.makeText(this," ERROR checkAndEnableGps",Toast.LENGTH_LONG);
        }

        btn_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GlobalDatas.gpsPaused = true;
                setVisibilityAllertGpsStoped();

            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GeoPoint centerPoint = (GeoPoint) mapView.getMapCenter();
//                AddPointDialogClass cdd = new AddPointDialogClass(ActivityOsmOnLineAddPoint.this, mapView);
//                cdd.show();
                TetDebugUtil.e(pseudo_tag, "Lat "+centerPoint.getLatitude()+" "+"lon "+centerPoint.getLongitude()+"");

               Intent intent = new Intent(getApplicationContext(), AddNewPointOwnPoint.class);
                float lat = (float) centerPoint.getLatitude();
                float lon = (float) centerPoint.getLongitude();
                intent.putExtra("who", who);
               intent.putExtra("lat",lat);
               intent.putExtra("lon",lon);
               intent.putExtra("org", org);
               intent.putExtra("saleman", saleman);
               intent.putExtra("zone", zone);

                startActivity(intent);
            }

        });

//            saveButton.setOnClickListener(view -> {
//                GeoPoint centerPoint = (GeoPoint) mapView.getMapCenter();
//                double latitude = centerPoint.getLatitude();
//                double longitude = centerPoint.getLongitude();
//
//                // Открытие диалогового окна с координатами
//                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(ActivityOsmOnLineAddPoint.this);
//                dialogBuilder.setTitle("Coordinates");
//                dialogBuilder.setMessage("Latitude: " + latitude + "\nLongitude: " + longitude);
//                dialogBuilder.setPositiveButton("OK", (dialog, which) -> dialog.dismiss());
//                dialogBuilder.show();
//            }

        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
                public void onClick(View v) {
                GlobalDatas.gpsPaused = false;
                    setVisibilityAllertGpsStoped();
                }
        });
    }

    private void intentManipulation(Intent intent) {

            who = intent.getStringExtra("who");
        if (who != null) {
            if (who.equals(SaleManActivity.class.getSimpleName()) || who.equals(ZoneActivity.class.getSimpleName())) {
                org = intent.getStringExtra("org");
                saleman = intent.getStringExtra("saleman");
                zone = intent.getStringExtra("zone");
                TetDebugUtil.e(pseudo_tag, "intentManipulation who=[" + who + "] org=[" + org + "] saleman=[" + saleman + "] zone=[" + zone + "]");
            }
        }
    }

    private void setVisibilityAllertGpsStoped() {
        if (GlobalDatas.gpsPaused == true){
            stopedGPS.setVisibility(View.VISIBLE);
        } else {
            GlobalDatas.gpsPaused = false;
            stopedGPS.setVisibility(View.GONE);
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
   //     if (gpsMapStoped.equals("false")) {
        float accuracy = location.getAccuracy();
        double lat = location.getLatitude();
        double lon = location.getLongitude();
        acurracy = location.getAccuracy();
        if(acurracy > GlobalDatas.minGpsAccurracy || accuracy == 0.0f){
            minAccurracyTX.setVisibility(View.VISIBLE);
            CharSequence chars = getResources().getString(R.string.txtLowAccurracy)+" = "+accuracy+"";
            minAccurracyTX.setText(chars);
        } else {
            minAccurracyTX.setVisibility(View.GONE);
        }
        TetDebugUtil.d(pseudo_tag, "gpsMapStoped = "+ GlobalDatas.gpsPaused +" onGetLocation from calback latitude ="+location.getLatitude()+" longetude="+location.getLongitude()+"");
        GeoPoint coordinates = new GeoPoint(lat, lon);
//       if (gpsMapStoped.equals("false")) {

           mapView.getController().setCenter(coordinates);
 //      }

    }

    @Override
    public void onGpsStatusChanged(String provider, int status) {
   TetDebugUtil.d(pseudo_tag,"onGpsStatusChanged "+provider+" status="+status+"");
    }

    @Override
    public void GpsDisabled(String provider) {
TetDebugUtil.d(pseudo_tag,"GpsDisabled "+provider+"");
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (who != null) {
            if (who.equals(SaleManActivity.class.getSimpleName())) {
                startActivity(new Intent(getApplicationContext(), SaleManActivity.class));
                this.finish();
            } else if (who.equals(ZoneActivity.class.getSimpleName())) {
                startActivity(new Intent(getApplicationContext(), ZoneActivity.class));
                this.finish();
            }
    }
    }
}