package tet.oleg_zhabko.tsp.ui.utils;
// Created: by PC BEST, OS Linux
// Copyright:  Copyright (c) 2008-2024 Best LLC & Oleg Zhabko. All rights reserved.
//License: ASK LICENSE TERMS AND CONDITIONS!
//             Oleg Zhabko, mailto:olegzhabko@gmail.com
//             phone +380 (67) 411-98-75
//              Berdichev, Ukraine

//

import android.content.Context;

import java.util.ArrayList;

import tet.oleg_zhabko.tsp.ThisApp;
import tet.tetlibrarymodules.alldbcontroller.AllDatabaseController;

public class uiUtils {
    private static AllDatabaseController dbContriller;

    public static boolean isAtListOneZoneAdded(Context context_or_null, String db_name) {

        if (context_or_null == null) {
            context_or_null = ThisApp.getInstance().getApplicationContext();
        }
        String query = null;
        ArrayList<ArrayList<String>> arrayArrayStr = dbContriller.executeQuery(context_or_null, db_name, query);

        return false;
    }

    public static String getUserDbNameByShare() {

        if (ThisApp.getSharedPreferenceManager().getBoolean("isUserOwn", true)) {
            return "user";
        }
        return "online";

    }
}
