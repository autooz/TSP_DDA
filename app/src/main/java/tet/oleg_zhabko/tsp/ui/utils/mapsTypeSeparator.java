package tet.oleg_zhabko.tsp.ui.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import java.util.ArrayList;

import tet.oleg_zhabko.tsp.ThisApp;
import tet.oleg_zhabko.tsp.datas.GlobalDatas;
import tet.tetlibrarymodules.alldbcontroller.AllDatabaseController;
import tet.tetlibrarymodules.tetdebugutils.debug.debug_tools.TetDebugUtil;

public class mapsTypeSeparator {
    private static String pseudo_tag = mapsTypeSeparator.class.getSimpleName();

    public static Intent getPreferedEditMaps(Activity activity) {
        Context context = activity.getApplicationContext();
        AllDatabaseController dbController = AllDatabaseController.getSingleControllerInstance();
        dbController.executeQuery(context, GlobalDatas.db_name, "SELECT value FROM settings WHERE variable='mapsActivity'; ");
        return null;
    }

    public static String getPreferedNavigationMap(Activity activity) {
        Context context = activity.getApplicationContext();
        AllDatabaseController dbController = AllDatabaseController.getSingleControllerInstance();
        ArrayList<ArrayList<String>> arar = dbController.executeQuery(context, GlobalDatas.db_name, "SELECT value FROM settings WHERE variable='nav_map'; ");
       return fetchString(arar);
    }

    private static String fetchString(ArrayList<ArrayList<String>> arar) {
        String error = "ERROR in getPreferedNavigationMap";
        if (arar.isEmpty()){
            TetDebugUtil.e(pseudo_tag,"ERROR getPreferedNavigationMap arar.isEmpty()");
            return error;
        }
        ArrayList<String> ar = arar.get(0);
        if (ar.isEmpty()){
            TetDebugUtil.e(pseudo_tag,"ERROR getPreferedNavigationMap arar.isEmpty()");
            return error;
        }
        return ar.get(0);
    }
}
