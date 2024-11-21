package tet.tetlibrarymodules.tetdebugutils.debug.debug_tools;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;


public final class TetDebugUtil {

    private static ArrayList<String> switchOFbyPseudoTag = new ArrayList<>();

    private static void makeList() {

        //////////////////////// CRITICAL /////////////
        /////////////////////////////////////////////
        switchOFbyPseudoTag.add("ThisApp.getInstance().class");

    }





    public static void d(String tag, String body){
        if (isDebugOff(tag)){
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                public void run() {
                    Log.d(tag,""+tag+" : "+body);
                }
            });

           // Log.d(tag,body);
        }
    }
    public static void ls(String body){
        ShowlongLargeString.showString(body);
    }

    public static void lb(byte[] bytes){
        ShowlongLargeString.showeButes(bytes);
    }

    public static void e(String tag, String body){
        if (isDebugOff(tag)){
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                public void run() {
                    Log.e(tag,""+tag+" : "+body);
                }
            });
           // Log.e(tag,body);
        }
    }


    private static boolean isDebugOff(String tag) {
        if (switchOFbyPseudoTag.isEmpty()){
            makeList();
        }
        int size = switchOFbyPseudoTag.size();
        for(int i = 0;size > i; i++){
            String pseudo_tag = switchOFbyPseudoTag.get(i);
            if (pseudo_tag.equals(tag)){
                return false;
            }
        }
        return true;
    }


    public static void e(String pseudo_tag, Exception exception) {
        StringWriter errors = new StringWriter();
        exception.printStackTrace(new PrintWriter(errors));
        String eString = errors.toString();
        e(pseudo_tag, eString);
    }
}
