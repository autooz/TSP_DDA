package tet.oleg_zhabko.tsp.ui.utils;
// Created: by PC BEST, OS Linux
// Copyright:  Copyright (c) 2008-2024 Best LLC & Oleg Zhabko. All rights reserved.
//License: ASK LICENSE TERMS AND CONDITIONS!
//             Oleg Zhabko, mailto:olegzhabko@gmail.com
//             phone +380 (67) 411-98-75
//              Berdichev, Ukraine

//

import android.app.Application;
import android.content.Context;

import java.util.ArrayList;

import tet.oleg_zhabko.tsp.ThisApp;
import tet.oleg_zhabko.tsp.datas.GlobalDatas;
import tet.tetlibrarymodules.alldbcontroller.AllDatabaseController;
import tet.tetlibrarymodules.tetdebugutils.debug.debug_tools.TetDebugUtil;

public class SettingsUtils {
    private static AllDatabaseController dbController;
    private static String pseudo_tag = SettingsUtils.class.getSimpleName();
    private final Context mContext;


    public SettingsUtils(Context context) {
        mContext = context;
        dbController = AllDatabaseController.getSingleControllerInstance();
    }

    public SettingsUtils(Application thisApp) {
        mContext = thisApp;
        dbController = AllDatabaseController.getSingleControllerInstance();
    }

    public SettingsUtils(){
        mContext = ThisApp.getContextApp();
        dbController = AllDatabaseController.getSingleControllerInstance();
    }

    public String getScaleTextFromDriversDb() {
        TetDebugUtil.d(pseudo_tag, "Try to get DriverOfLineTaxDate.getdbName(pseudo_tag)");
//        Context context = ThisApp.getInstance().getApplicationContext();
        ArrayList<ArrayList<String>> valuesArray = dbController.executeQuery(ThisApp.getInstance().getContextApp(), GlobalDatas.db_name, "SELECT `value` FROM `settings` WHERE `variable`='scaleText'");
        if (!valuesArray.isEmpty()) {
            ArrayList<String> row = valuesArray.get(0);
            if (!row.isEmpty()) {
                String value = row.get(0);
                //  TetDebugUtil.d(pseudo_tag, "currencyFromDb=[" + value + "]");
                return value;
            } else {
                dbController.executeQuery(ThisApp.getInstance().getContextApp(), "settings.db", "INSERT INTO Settings (Setting_id, variable, value, description) VALUES (2, 'scaleText', '100', 'Масштаб шрифтов в процентах для всего приложения.')");
            }
        }
        return new String();
    }

    public String getSettingValue(String db_name, String variable) {
        String value = null;
        ArrayList<ArrayList<String>> ArrayListResult = dbController.executeQuery(mContext, db_name, "SELECT `value` FROM `settings` WHERE `variable`='" + variable + "'");
        TetDebugUtil.e(pseudo_tag,"ERROR ArrayListResult");
        if (!ArrayListResult.isEmpty()) {
            ArrayList<String> row = ArrayListResult.get(0);
            if (!row.isEmpty()) {
                value = row.get(0);
                TetDebugUtil.e(pseudo_tag,""+pseudo_tag+"    value ="+value+" ");
                return value;
            }
        } else {
            TetDebugUtil.e(pseudo_tag,""+pseudo_tag+"    ERROR ArrayListResult");
        }
        return value;
    }



    public boolean getZoneChoiseByDriver(String dbName) {
        String variable = "zoneChoiseByDriver";
        String value = getSettingValue(dbName, variable);
        if (value != null) {
            if (value.equals("true")) {
                return true;
            }
        }
        return false;
    }

    public boolean getZoneOnlyByDriver(String dbName) {
        String variable = "zoneOnlyByDriver";
        String value = getSettingValue(dbName, variable);
        if (value != null) {
            if (value.equals("true")) {
                return true;
            }
        }
        return false;
    }

    public boolean getWiFiScan(String dbName) {
        String variable = "WiFiScan";
        String value = getSettingValue(dbName, variable);
        if (value != null) {
            if (value.equals("true")) {
                return true;
            }
        }
        return false;
    }
}

