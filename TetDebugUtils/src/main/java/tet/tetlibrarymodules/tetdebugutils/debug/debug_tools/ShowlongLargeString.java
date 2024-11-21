package tet.tetlibrarymodules.tetdebugutils.debug.debug_tools;

import android.util.Log;

/**
 * Created by oleg on 27.02.18.
 */

public class ShowlongLargeString {


    public static void showString(String str) {

        if (str.length() > 3000) {
            Log.i("ShowlongLargeString", str.substring(0, 3000));
            showString(str.substring(3000));
        } else
            Log.i("ShowlongLargeString", str);
    }

    public static void showeButes(byte[] bytes) {

        if (bytes.length > 3000) {
            Log.i("logLargeBytes", bytes.toString().substring(0, 3000));
            //    ShowlongLargeString(bytes.toString().substring(3000));
        } else
            Log.i("logLargeBytes", bytes.toString());
    }
}

