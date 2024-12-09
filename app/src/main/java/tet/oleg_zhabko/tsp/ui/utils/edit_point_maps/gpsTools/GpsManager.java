package tet.oleg_zhabko.tsp.ui.utils.edit_point_maps.gpsTools;
/*
 HOWTO USE:
 public class GpsActivity extends AppCompatActivity implements GpsManager.OnLocationReceivedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gps);

        // Запустить GPS мониторинг
        GpsManager.startMonitoring(this, this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Остановить мониторинг
        GpsManager.stopMonitoring();
    }

    @Override
    public void onGetLocation(Location location) {
        // Действия с полученными координатами
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();
        // Ваш код для обработки координат
    }
}
 */


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import tet.oleg_zhabko.tsp.datas.GlobalDatas;
import tet.tetlibrarymodules.tetdebugutils.debug.debug_tools.TetDebugUtil;

public class GpsManager {

    private static LocationManager locationManager;
    private static LocationListener locationListener;
    private static OnLocationReceivedListener callback;
    private static String pseudo_tag = GpsManager.class.getSimpleName();

    private GpsManager() {
        // Приватный конструктор для статического класса
    }

    private static final int GPS_SETTINGS_REQUEST_CODE = 1001;

    // Method to check is a GPS sensor on device's board
    private static boolean isGpsSensorAvailable(Context context) {
        TetDebugUtil.d(pseudo_tag,"Call isGpsSensorAvailable");
        PackageManager packageManager = context.getPackageManager();
        return packageManager.hasSystemFeature(PackageManager.FEATURE_LOCATION_GPS);
    }

    // Method for checking and managing GPS status
    public static boolean checkAndEnableGps(@NonNull Activity activity) {
        TetDebugUtil.d(pseudo_tag,"Call ckAndEnableGps");
        if (isGpsSensorAvailable(activity)) {
            if (isGpsEnabled(activity)) {
                Toast.makeText(activity, "GPS is enabled.", Toast.LENGTH_SHORT).show();
                return true;
            } else {
                Toast.makeText(activity, "GPS is disabled. Opening GPS settings.", Toast.LENGTH_SHORT).show();
                openGpsSettings(activity);

            }
        } else {
            Toast.makeText(activity, "GPS sensor is not available on this device.", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    // Method to check if GPS is turned on
    private static boolean isGpsEnabled(Context context) {
        TetDebugUtil.d(pseudo_tag,"Call isGpsEnabled");
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if (locationManager != null) {
            return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        }
        return false;
    }

    // Method for opening GPS settings
    private static void openGpsSettings(Activity activity) {
        TetDebugUtil.d(pseudo_tag,"Call openGpsSettings");
        Context context = activity.getApplicationContext();
        turnGPSOn(context);
    }


    public static boolean handleGpsSettingsResult(int requestCode, int resultCode, AppCompatActivity activity) {
        TetDebugUtil.d(pseudo_tag,"Call handleGpsSettingsResult");
        /*Usage:
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (GpsManager.handleGpsSettingsResult(requestCode, resultCode, this)){

        } else {
            Toast.makeText(this, "GPS is still disabled.", Toast.LENGTH_LONG).show();
        }
    }
    * */


        if (requestCode == GPS_SETTINGS_REQUEST_CODE) {
            if (isGpsEnabled(activity)) {
                return true;
            }
        }
        return false;
    }


    public static boolean handleGpsSettingsResult(int requestCode, int resultCode, Activity activity) {
        TetDebugUtil.d(pseudo_tag,"Call handleGpsSettingsResult");
        /*Usage:
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (GpsManager.handleGpsSettingsResult(requestCode, resultCode, this)){

        } else {
            Toast.makeText(this, "GPS is still disabled.", Toast.LENGTH_LONG).show();
        }
    }
    * */


        if (requestCode == GPS_SETTINGS_REQUEST_CODE) {
            if (isGpsEnabled(activity)) {
                return true;
            }
        }
        return false;
    }

    private static void turnGPSOn(Context context) {
        TetDebugUtil.d(pseudo_tag,"Call turnGPSOn");
        try {
            String provider = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);


            if (!provider.contains("gps")) { //if gps is disabled
                final Intent intent = new Intent();
                intent.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider");
                intent.addCategory(Intent.CATEGORY_ALTERNATIVE);
                intent.setData(Uri.parse("3"));
                context.sendBroadcast(intent);
            }
        } catch (Exception e) {

        }
    }

    // Method to turn off the GPS
    public static void turnGPSOff(Context context) {
        TetDebugUtil.d(pseudo_tag,"Call turnGPSOff");
        String provider = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);

        if (provider.contains("gps")) { //if gps is enabled
            final Intent poke = new Intent();
            poke.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider");
            poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
            poke.setData(Uri.parse("3"));
            context.sendBroadcast(poke);
        }
    }
/*------------------------------------------LISTNER--------------------------------------------------*/
public static void startMonitoring(Context context, OnLocationReceivedListener listener, float minDistanceForUpdate) {
    TetDebugUtil.d(pseudo_tag,"Call startMonitoring");
    callback = listener;

    locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
    locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            if (location.getAccuracy() <= 12) {
                // Передаем координаты в активити GpsActivity
//                Intent intent = new Intent(context, GpsActivity.class);
//                intent.putExtra("latitude", location.getLatitude());
//                intent.putExtra("longitude", location.getLongitude());
//                context.startActivity(intent);

                // Вызываем колбек
                if (callback != null) {
                    if (GlobalDatas.gpsPaused){
                        return;
                    }
                    callback.onGetLocation(location);
                    TetDebugUtil.d(pseudo_tag, "Call calback latitude ="+location.getLatitude()+" longetude="+location.getLongitude()+"");
                }
            }
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            TetDebugUtil.d(pseudo_tag,"Call onStatusChanged");
            callback.onGpsStatusChanged(provider, status);
        }

        @Override
        public void onProviderEnabled(String provider) {
            TetDebugUtil.d(pseudo_tag,"Call onProviderEnabled");
        }

        @Override
        public void onProviderDisabled(String provider) {
            TetDebugUtil.d(pseudo_tag,"Call onProviderDisabled");
            callback.GpsDisabled(provider);
        }
    };

    try {
        // Запуск GPS мониторинга с обновлением каждые 2 секунд и на расстоянии 2 метров
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, minDistanceForUpdate, locationListener);
    } catch (SecurityException e) {
        e.printStackTrace();
    }
}

    public static void stopMonitoring() {
        TetDebugUtil.d(pseudo_tag,"Call stopMonitoring");
        if (locationManager != null && locationListener != null) {
            locationManager.removeUpdates(locationListener);
        }
        callback = null; // Очистка колбека после остановки
    }

    public interface OnLocationReceivedListener {
        void onGetLocation(Location location);

        void onGpsStatusChanged(String provider, int status);

        void GpsDisabled(String provider);

    }


}

