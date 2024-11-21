package tet.tetlibrarymodules.tetdebugutils.debug;

import android.app.Activity;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.net.Socket;
import java.util.Calendar;

import tet.tetlibrarymodules.tetdebugutils.debug.debug_tools.AppNameGeter;
import tet.tetlibrarymodules.tetdebugutils.debug.debug_tools.TetDebugUtil;

public class CheckIsCrashReport {
    private Socket client;
    private BufferedInputStream bis;
    private BufferedOutputStream bos;


    public CheckIsCrashReport() {
    }


    public static boolean isCrashReport(Activity activity) {
        Thread.setDefaultUncaughtExceptionHandler(new CrashAppExceptionHandler(activity));
        String time = Calendar.getInstance().getTime().toString();
        String appName = AppNameGeter.getAppNameByActivity(activity);
        File file = new File(activity.getFilesDir() + "/" + appName);
        if (file.exists()) {
            TetDebugUtil.d("isCrashReport", "TRUE");
            return true;
        }
        TetDebugUtil.d("isCrashReport", "FALSE");
        return false;
    }
}
