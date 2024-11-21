package tet.oleg_zhabko.tsp.ui.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.provider.Settings;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import tet.tetlibrarymodules.tetdebugutils.debug.debug_tools.TetDebugUtil;

public class PermissionUtils {

       private static String pseudo_tag = PermissionUtils.class.getSimpleName();



    // Method to check and request permissions
    public static void checkAndRequestPermissions(Context context, Activity activity) {

        TetDebugUtil.e(pseudo_tag,"Do checkAndRequestPermissions");
        PackageManager packageManager = context.getPackageManager();
        String packageName = context.getPackageName();
        List<String> permissionsToRequest = new ArrayList<>();
        List<String> permissions = getDeclaredPermissions(packageManager, packageName);
       // TetDebugUtil.e(pseudo_tag,"permissions ="+permissions+"");
        int size = permissions.size();
        TetDebugUtil.e(pseudo_tag,"permissions.size()="+size+"");
        int ip = 0;
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                permissionsToRequest.add(permission);
            }
        }

        if (!permissionsToRequest.isEmpty()) {
           //TetDebugUtil.e(pseudo_tag, "Befor requestPermissions du chesk");
            // Request permissions
           ActivityCompat.requestPermissions(activity, permissionsToRequest.toArray(new String[0]),1);
        } else {
            TetDebugUtil.e(pseudo_tag,"ERROR !permissionsToRequest.isEmpty()");
        }
    }

    // Method to get all permissions from manifest
    private static List<String> getDeclaredPermissions(PackageManager packageManager, String packageName) {
        TetDebugUtil.e(pseudo_tag,"Do getDeclaredPermissions");

        List<String> permissions = new ArrayList<>();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(packageName, PackageManager.GET_PERMISSIONS);
            String[] requestedPermissions = packageInfo.requestedPermissions;

            if (requestedPermissions != null) {
                for (String permission : requestedPermissions) {
                    permissions.add(permission);
                }
            }
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return permissions;
    }



    public static void handlerOnRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults, Context context) {

//TetDebugUtil.e(pseudo_tag,"Start  handlerOnRequestPermissionsResult "+permissions.toString()+" and grantResults "+grantResults.toString()+"");
         /*Usage:
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionUtils.handlerOnRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }
      */

        if (requestCode == 1) { // request code должен совпадать с тем, который используется при запросе разрешений
            boolean allPermissionsGranted = true;
            for (int result : grantResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    allPermissionsGranted = false;
                    break;
                }
            }

            if (!allPermissionsGranted) {
                // Permissions not granted, open application settings
                TetDebugUtil.e(pseudo_tag,"Permissions not granted, open application settings");
                openAppSettings(context);
            }
        }
    }

    //Method to open application settings
    private static void openAppSettings(Context context) {
        TetDebugUtil.e(pseudo_tag,"Do openAppSettings");
        TetDebugUtil.e(pseudo_tag, "openAppSettings");
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + context.getPackageName()));
        context.startActivity(intent);
    }

}
