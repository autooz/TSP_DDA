package tet.oleg_zhabko.tsp;
// Created: by PC BEST, OS Linux
// Copyright:  Copyright (c) 2008-2024 Best LLC & Oleg Zhabko. All rights reserved.
//License: ASK LICENSE TERMS AND CONDITIONS!
//             Oleg Zhabko, mailto:olegzhabko@gmail.com
//             phone +380 (67) 411-98-75
//              Berdichev, Ukraine

//


import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.LocaleList;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import androidx.annotation.Nullable;

import java.util.Locale;

import tet.oleg_zhabko.tsp.ui.autonom.AddNewPointToCurrentRoute;
import tet.oleg_zhabko.tsp.ui.route.OnRouteMainActivity;
import tet.oleg_zhabko.tsp.ui.utils.CheckCanDrawOverlays;
import tet.oleg_zhabko.tsp.ui.utils.FloatigButton.FloatingButtonService;
import tet.tetlibrarymodules.tetdebugutils.debug.debug_tools.TetDebugUtil;
import tet.oleg_zhabko.tsp.ui.utils.SettingsUtils;
import tet.oleg_zhabko.tsp.ui.utils.edit_point_maps.ActivityPointInfo;

public class ThisApp extends Application {
    public static int API;
    public static AudioManager appAudioManager;
    private static ThisApp instance;
    private static String pseudo_tag = "ThisApp instance";
    private static MediaPlayer appMediaPlayer;
    private static SharedPreferences preferenceManager;
    private static String appLanguage;
    private static boolean isAppInForeground = true;


    public static ThisApp getInstance() {
        return instance;
    }

    @Nullable
    public static Context getContextApp() {
        //return instance;
        return instance.getApplicationContext();
    }

    public static AudioManager geAppAudioManager() {
        if (appAudioManager == null) {
            appAudioManager = (AudioManager) getContextApp().getSystemService(AUDIO_SERVICE);
        }
        return appAudioManager;
    }

    public static MediaPlayer getAppMediaPlayer() {
        if (appMediaPlayer == null) {
            appMediaPlayer = new MediaPlayer();
        }
        return appMediaPlayer;
    }


    public static void adjastFontScale() {
        String scaleFromDb = new SettingsUtils(instance).getScaleTextFromDriversDb();
        if (scaleFromDb.isEmpty()) {
            scaleFromDb = "100";
        }
        int scale = Integer.parseInt(scaleFromDb);
        float scaleFloat = (float) scale / (float) 100;

        Resources resources = instance.getResources();
        Configuration configuration = resources.getConfiguration();
        if (configuration.fontScale != scaleFloat) {
            configuration.fontScale = scaleFloat;
            DisplayMetrics metrics = resources.getDisplayMetrics();
            WindowManager wm = (WindowManager) instance.getSystemService(WINDOW_SERVICE);
            wm.getDefaultDisplay().getMetrics(metrics);
            metrics.scaledDensity = configuration.fontScale * metrics.density;
            instance.getBaseContext().getResources().updateConfiguration(configuration, metrics);
        }
    }

    public static void setThisAppLocale(String newlanguage) {
        String sysLanguage = Locale.getDefault().getLanguage();
        TetDebugUtil.d(pseudo_tag, "sysLanguage = [" + sysLanguage + "]");
        String languageToSet = new String();
        if (newlanguage == null) {
            languageToSet = preferenceManager.getString("language", sysLanguage).toString();
            TetDebugUtil.d(pseudo_tag, " preferenceManager get lenguage/locale = [" + languageToSet + "]");
        } else {
            languageToSet = newlanguage;
        }
        languageToSet = "ru";

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            LocaleList defaultAll = LocaleList.getDefault();
            defaultAll.toLanguageTags();
            int size = defaultAll.size();
            TetDebugUtil.d(pseudo_tag, "LocaleList.getDefault().size() = [" + size + "] defaultAll.toLanguageTags()=[" + defaultAll.toLanguageTags() + "]");
            for (int i = 0; size > i; i++) {
                TetDebugUtil.d(pseudo_tag, defaultAll.get(i).getLanguage());
            }
        }
        //  Locale.setDefault();
        TetDebugUtil.d(pseudo_tag, "Locale.getDefault().getDisplayLanguage(); = [" + Locale.getDefault().getDisplayLanguage() + "]");


        Configuration config = new Configuration();
        TetDebugUtil.d(pseudo_tag, "Befor set lang languageToSet= [" + languageToSet + "] sysLanguage= [" + sysLanguage + "]");
        if (!languageToSet.equals(sysLanguage)) {
            TetDebugUtil.d(pseudo_tag, "Set new Locale =[" + newlanguage + "] languageToSet=  [" + languageToSet + "]");
            Locale newlocale = new Locale(languageToSet);
            Locale.setDefault(newlocale);
            config.locale = newlocale;
            DisplayMetrics displayMetrics = getContextApp().getResources().getDisplayMetrics();
            displayMetrics.setToDefaults();
            instance.getContextApp().getResources().updateConfiguration(config, instance.getContextApp().getResources().getDisplayMetrics());
            instance.getBaseContext().getResources().updateConfiguration(config, instance.getBaseContext().getResources().getDisplayMetrics());
        }
        Configuration configuration = instance.getBaseContext().getResources().getConfiguration();
        String lang = configuration.locale.getLanguage();
        TetDebugUtil.d(pseudo_tag, "Check configuration.locale.getLanguage = [" + lang + "] getDisplayLanguage(" + Locale.getDefault().getDisplayLanguage() + ")");
        if (lang.equals(languageToSet)) {
            appLanguage = lang;
            SharedPreferences.Editor editor = preferenceManager.edit();
            editor.putString("language", appLanguage);
            editor.apply();
        }

    }

    public static String getApplicationName() {
        ApplicationInfo applicationInfo = instance.getApplicationInfo();
        int stringId = applicationInfo.labelRes;
        return stringId == 0 ? applicationInfo.nonLocalizedLabel.toString() : instance.getString(stringId);
    }

    public static SharedPreferences getSharedPreferenceManager() {
        return preferenceManager;
    }

    private static String getDefaultSharedPreferencesName(Context context) {
        return context.getPackageName() + "_preferences";
    }

    private static int getDefaultSharedPreferencesMode() {
        return Context.MODE_PRIVATE;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        API = ThisApp.getInstance().API;
        this.registerActivityLifecycleCallbacks(new MyActivityLifecycleCallbacks());


        if (Build.VERSION.SDK_INT >=Build.VERSION_CODES.P){
            preferenceManager = this.getSharedPreferences("shared_preference", this.MODE_PRIVATE); getSharedPreferences(getDefaultSharedPreferencesName(this), getDefaultSharedPreferencesMode());
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            preferenceManager = getSharedPreferences(getDefaultSharedPreferencesName(this), getDefaultSharedPreferencesMode());

        } else {
            preferenceManager = PreferenceManager.getDefaultSharedPreferences(this);
        }

        setThisAppLocale(null);
        // Activity status tracking
        activityLifeCycleMonitor();

    }

    private void activityLifeCycleMonitor() {
        instance.registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityResumed(Activity activity) {
                TetDebugUtil.e(pseudo_tag," onActivityResumed "+activity.getClass().getSimpleName()+"");
                isAppInForeground = true;
                stopFloatingButtonService();
            }

            @Override
            public void onActivityPaused(Activity activity) {
                TetDebugUtil.e(pseudo_tag," onActivityPaused "+activity.getClass().getSimpleName()+" ");
                isAppInForeground = false;
                String simpleName = activity.getClass().getSimpleName();
                startFloatingButtonService(simpleName);
            }

            // Optional lifecycle methods that can be left empty
            @Override public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                TetDebugUtil.e(pseudo_tag, " onActivityCreated ["+activity.getClass().getSimpleName()+"] ");
            }
            @Override public void onActivityStarted(Activity activity) {
                TetDebugUtil.e(pseudo_tag, " onActivityStarted ["+activity.getClass().getSimpleName()+"] ");
            }
            @Override public void onActivityStopped(Activity activity) {
                TetDebugUtil.e(pseudo_tag, " onActivityStoped ["+activity.getClass().getSimpleName()+"] ");
            }
            @Override public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
                TetDebugUtil.e(pseudo_tag, " onActivitySaveInstance ["+activity.getClass().getSimpleName()+"] ");
            }
            @Override public void onActivityDestroyed(Activity activity) {
                TetDebugUtil.e(pseudo_tag, " onActivityestoyed ["+activity.getClass().getSimpleName()+"] ");
            }
        });

    }

    public static boolean isAppInForeground() {
        return isAppInForeground;
    }

    private void startFloatingButtonService(String simpleName) {
        boolean doIt = false;
            Context context = getApplicationContext();
            if (!CheckCanDrawOverlays.canDrawOverlays(context)) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
                intent.setData(Uri.parse("package:" + context.getPackageName()));
                intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            } else if (!isAppInForeground) {
                if ( simpleName.equals(OnRouteMainActivity.class.getSimpleName())){
                    doIt = true;
                } else if (simpleName.equals(AddNewPointToCurrentRoute.class.getSimpleName())) {
                    doIt = true;
                } else if (simpleName.equals(ActivityPointInfo.class.getSimpleName())) {
                    doIt = true;
                }

                if (doIt) {
                    Intent intent = new Intent(this, FloatingButtonService.class);
                    startService(intent);
                }
        }
    }

    private void stopFloatingButtonService() {
        if (isAppInForeground) {
            Intent intent = new Intent(this, FloatingButtonService.class);
            stopService(intent);
        }
    }



    public String getDisplayLanguage() {
        Locale.getDefault().getDisplayLanguage();
        return Locale.getDefault().getDisplayLanguage();
    }

    public String getLanguage() {
        return appLanguage;
    }

    private static final class MyActivityLifecycleCallbacks implements ActivityLifecycleCallbacks {
        public void onActivityCreated(Activity activity, Bundle bundle) {
            TetDebugUtil.d("TAG", "onActivityCreated:" + activity.getLocalClassName());
        }

        public void onActivityDestroyed(Activity activity) {
            TetDebugUtil.d("TAG", "onActivityDestroyed:" + activity.getLocalClassName());
        }

        public void onActivityPaused(Activity activity) {
            TetDebugUtil.d("TAG", "onActivityPaused:" + activity.getLocalClassName());
        }

        public void onActivityResumed(Activity activity) {
            TetDebugUtil.d("TAG", "onActivityResumed:" + activity.getLocalClassName());
        }

        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
            TetDebugUtil.d("TAG", "onActivitySaveInstanceState:" + activity.getLocalClassName());
        }

        public void onActivityStarted(Activity activity) {
            TetDebugUtil.d("TAG", "onActivityStarted:" + activity.getLocalClassName());
        }

        public void onActivityStopped(Activity activity) {
            TetDebugUtil.d("TAG", "onActivityStopped:" + activity.getLocalClassName());
        }
    }





}
