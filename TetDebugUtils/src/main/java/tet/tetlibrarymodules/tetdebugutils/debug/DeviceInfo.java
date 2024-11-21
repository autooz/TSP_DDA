package tet.tetlibrarymodules.tetdebugutils.debug;

import android.os.Build;

/**
 * Created by oleg on 25.10.16.
 */

public class DeviceInfo {


    public String getDeviceInfo() {
        /* Add API version to MutGlobalData */
        String manufacturer = Build.MANUFACTURER;

        String model = Build.MODEL;
        int version = Build.VERSION.SDK_INT;
        String versionRelease = Build.VERSION.RELEASE;

        String info = " In DeviceInfo manufacturer " + manufacturer
                + " \n model " + model
                + " \n version " + version
                + " \n versionRelease " + versionRelease
                + "\n\n";
        ;
        return info;
    }

    public interface DeviceInfoReturn {

        void onReadCompleted(String manufacturer, String model, int version, String versionRelease);
    }

}
