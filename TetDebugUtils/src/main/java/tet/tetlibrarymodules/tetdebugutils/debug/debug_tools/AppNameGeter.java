package tet.tetlibrarymodules.tetdebugutils.debug.debug_tools;
// Created: by PC BEST, OS Linux
// Copyright:  Copyright (c) 2008-2024 Best LLC & Oleg Zhabko. All rights reserved.
//License: ASK LICENSE TERMS AND CONDITIONS!
//             Oleg Zhabko, mailto:olegzhabko@gmail.com
//             phone +380 (67) 411-98-75
//              Berdichev, Ukraine

//

import android.app.Activity;
import android.content.pm.ApplicationInfo;

import java.util.Calendar;

public final class AppNameGeter {

    public static String getAppNameByActivity (Activity activity){
        String time = Calendar.getInstance().getTime().toString();
        String appName = "";
        ApplicationInfo applicationInfo = activity.getApplicationContext().getApplicationInfo();
        int stringId = applicationInfo.labelRes;
        if (stringId == 0) {
            appName = applicationInfo.nonLocalizedLabel.toString();
        }
        else {
            appName = activity.getString(stringId);
        }
        return appName;
    }

}
