package tet.tetlibrarymodules.tetdebugutils.debug.debug_tools;

import static android.content.Context.ACTIVITY_SERVICE;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;

import java.util.List;

public class ListActivities {


    private final Context mContext;
    private String pseudo_tag = "ListActivities";

    public ListActivities(Context context) {
        mContext = context;
    }

    public void showListAllAppActivities() {
        PackageManager pManager = mContext.getPackageManager();
        String packageName = mContext.getApplicationContext().getPackageName();

        try {
            ActivityInfo[] list = pManager.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES).activities;
            for (ActivityInfo activityInfo : list) {
                TetDebugUtil.d(pseudo_tag, "ActivityInfo = " + activityInfo.name);
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void showCurrentlyRunningActivity() {
        ActivityManager am = (ActivityManager) mContext.getSystemService(ACTIVITY_SERVICE);

        List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(100);

        TetDebugUtil.d(pseudo_tag, "Running Activity ::" + taskInfo.get(0).topActivity.getClassName());

        ComponentName componentInfo = taskInfo.get(0).topActivity;

        componentInfo.getPackageName();
    }


}
