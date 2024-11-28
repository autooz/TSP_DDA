package tet.oleg_zhabko.tsp.ui.utils.appAndMaps;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import java.util.ArrayList;

import tet.oleg_zhabko.tsp.R;
import tet.oleg_zhabko.tsp.datas.GlobalDatas;
import tet.tetlibrarymodules.tetdebugutils.debug.debug_tools.ShowAllInArrayList;
import tet.tetlibrarymodules.tetdebugutils.debug.debug_tools.TetDebugUtil;

public class workWithApkNaviOnDevice {
    private final Activity activity;
    private String pseudo_tag = workWithApkNaviOnDevice.class.getSimpleName();
    private final Context context;


    public class appButtonExistencePakageClass {
        private int radioButtonID;
        private RadioButton radioButton;
        private String appPackageName;
        private String packageDescription;

        public <T extends View> appButtonExistencePakageClass(RadioButton radioButton, String appPackageName, String packageDescription) {
            this.radioButtonID = radioButton.getId();
            this.radioButton = radioButton;
            this.appPackageName = appPackageName;
            this.packageDescription = packageDescription;
        }

        private RadioButton getRadioBatoon() {
            return radioButton;
        }
        private int getRadioBatoonID() {
            return radioButtonID;
        }

        public String getAppPackageName() {
            return appPackageName;
        }

        public String getPackageDescription() {
            return packageDescription;
        }
//
//         public void addDates(RadioButton radioButton, String appPackageName, String packageDescription) {
//                this.radioButton =  radioButton;
//                this.appPackageName = appPackageName;
//                this.packageDescription = packageDescription;
//            }

    }


    public workWithApkNaviOnDevice(Activity activity) {
        this.context = activity.getApplicationContext();
        this.activity = activity;
        if (GlobalDatas.appsSupportedList.isEmpty()) {
            makeAppButtonExistencePakage(activity);
        }
    }

    private void makeAppButtonExistencePakage(Activity activity) {

        ArrayList<appButtonExistencePakageClass> ButtonExistencePakage = new ArrayList<>();

       TetDebugUtil.e(pseudo_tag,"Doing makeAppButtonExistencePakage" );

        appButtonExistencePakageClass cApsBitton;

        cApsBitton = new appButtonExistencePakageClass(activity.findViewById(R.id.rbWaze), "com.waze", "Waze Navigation & Live Traffic");
        GlobalDatas.appsSupportedList.add(cApsBitton);
        cApsBitton = new appButtonExistencePakageClass(activity.findViewById(R.id.rbGoogleNavi), "com.google.android.apps.maps", "Google Maps");
        GlobalDatas.appsSupportedList.add(cApsBitton);
        cApsBitton = new appButtonExistencePakageClass(activity.findViewById(R.id.rbNavitel), "com.navitel", "NAVITEL");
        GlobalDatas.appsSupportedList.add(cApsBitton);
        cApsBitton = new appButtonExistencePakageClass(activity.findViewById(R.id.rbOsmand), "net.osmand", "OsmAnd - Maps & GPS Offline");
        GlobalDatas.appsSupportedList.add(cApsBitton);
        cApsBitton = new appButtonExistencePakageClass(activity.findViewById(R.id.rbSygic), "com.sygic.aura", "Sygic GPS Navigation & Maps");
        GlobalDatas.appsSupportedList.add(cApsBitton);
        cApsBitton = new appButtonExistencePakageClass(activity.findViewById(R.id.rbhHuawei), "com.huawei.maps.app", "Petal Maps - GPS & Navigation");
        GlobalDatas.appsSupportedList.add(cApsBitton);
        GlobalDatas.appsSupportedList.add(cApsBitton);
        cApsBitton = new appButtonExistencePakageClass(activity.findViewById(R.id.rbTomtom), "com.tomtom.gplay.navapp", "TomTom Go Navigation");
        GlobalDatas.appsSupportedList.add(cApsBitton);
        cApsBitton = new appButtonExistencePakageClass(activity.findViewById(R.id.rbMapsWithMe), "com.mapswithme.maps.pro", "MAPS.ME");
        GlobalDatas.appsSupportedList.add(cApsBitton);
        cApsBitton = new appButtonExistencePakageClass(activity.findViewById(R.id.rb2gis), "ru.dublgis.dgismobile", "2GIS");
        GlobalDatas.appsSupportedList.add(cApsBitton);
        cApsBitton = new appButtonExistencePakageClass(activity.findViewById(R.id.rbIgo), "com.nng.igo.primong.igoworld", "IGO Navigation");
        GlobalDatas.appsSupportedList.add(cApsBitton);
    }

    // Method to check installation and open Play Store if the application is not installed
    public void checkAndOpenApp(String packageName, String appName_or_null) {
        if (isAppInstalled(packageName)) {
            openAppInPlayStore(packageName);
            if (appName_or_null != null) {
                Toast.makeText(context, "APP " + appName_or_null + " is instaled", Toast.LENGTH_LONG);
            }
        }
    }

    // Checks whether an application is installed by its package
    public boolean isAppInstalled(String packageName) {
        PackageManager pm = context.getPackageManager();
        try {
            pm.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
            TetDebugUtil.d(pseudo_tag, "[" + packageName + "] isAppInstalled [true]");
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            TetDebugUtil.d(pseudo_tag, "[" + packageName + "] isAppInstalled [false]");
            return false;
        }
    }

    // Opens the application page on Google Play
    public Intent openAppInPlayStore(String packageName) {
        Intent intent = new Intent();
        try {
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + packageName));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //context.startActivity(intent);
        } catch (android.content.ActivityNotFoundException e) {
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + packageName));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //context.startActivity(intent);
        }
        return intent;
    }

    // Method for checking all applications from the list (for debug tests)
    public void checkAllApps() {

//        checkAndOpenApp("com.waze", "Waze Navigation & Live Traffic");
//        checkAndOpenApp("com.google.android.apps.maps", "Google Maps");
//        checkAndOpenApp("com.navitel", "NAVITEL");
//        checkAndOpenApp("net.osmand", "OsmAnd - Maps & GPS Offline");
//        checkAndOpenApp("com.sygic.aura", "Sygic GPS Navigation & Maps");
//        checkAndOpenApp("com.huawei.maps.app", "Petal Maps - GPS & Navigation");
//        checkAndOpenApp("com.mapswithme.maps.pro", "MAPS.ME");
//        checkAndOpenApp("ru.dublgis.dgismobile", "2GIS");


        checkAndOpenApp("com.nng.igo.primong.igoworld", "iGO Navigation");
    }

    public void cheskSupportedAPP(){
        if (GlobalDatas.appsSupportedList.isEmpty()){
            TetDebugUtil.e(pseudo_tag,"GlobalDatas.appsSupportedList.isEmpty()");
            makeAppButtonExistencePakage(activity);
            if (GlobalDatas.appsSupportedList.isEmpty()){
                TetDebugUtil.e(pseudo_tag,"ERROR with makeAppButtonExistencePakage(activity) at cheskSupportedAPP");
            }
        }
    }

    public void navigateToLocation(double latitude, double longitude, String appPackageName) {
        Uri uri;
        Intent intent;

        if (isAppInstalled(appPackageName)) {
            switch (appPackageName) {
                case "com.waze":
                    uri = Uri.parse("waze://?ll=" + latitude + "," + longitude + "&navigate=yes");
                    break;
                case "com.google.android.apps.maps":
                    uri = Uri.parse("geo:" + latitude + "," + longitude + "?q=" + latitude + "," + longitude);
                    break;
                case "com.navitel":
                    uri = Uri.parse("geo:" + latitude + "," + longitude + "?q=" + latitude + "," + longitude);
                    break;
                case "net.osmand":
                    uri = Uri.parse("geo:" + latitude + "," + longitude + "?q=" + latitude + "," + longitude);
                    break;
                case "com.sygic.aura":
                    uri = Uri.parse("com.sygic.aura://coordinate|" + longitude + "|" + latitude + "|drive");
                    break;
                case "com.huawei.maps.app":
                    uri = Uri.parse("petalmaps://route?daddr=" + latitude + "," + longitude);
                    break;
//            case "com.tomtom.gplay.navapp":
//                uri = Uri.parse("google.navigation:q=" + latitude + "," + longitude + "&mode=d");
//                break;
                case "com.tomtom.gplay.navapp":
                    uri = Uri.parse("tomtomgo://x-callback-url/navigate?destination=" + latitude + "," + longitude);
                    break;
                case "com.mapswithme.maps.pro":
                    uri = Uri.parse("geo:" + latitude + "," + longitude + "?q=" + latitude + "," + longitude);
                    break;
                case "ru.dublgis.dgismobile":
                    uri = Uri.parse("dgis://2gis.ru/routeSearch/rsType/car/to/" + longitude + "," + latitude);
                    break;
                case "com.nng.igo.primong.igoworld":
                    uri = Uri.parse("igo://coordinate|" + longitude + "|" + latitude + "|drive");
                    break;

                default:
                    Toast.makeText(context, "Приложение не поддерживается", Toast.LENGTH_SHORT).show();
                    return;
            }

            intent = new Intent(Intent.ACTION_VIEW, uri);
            intent.setPackage(appPackageName);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        } else {
            Toast.makeText(context, "Приложение не установлено", Toast.LENGTH_LONG).show();
        }
    }



    public String getPaketNameByRadioButtonID(int radioButtonID){
        if (GlobalDatas.appsSupportedList.isEmpty()) {
            TetDebugUtil.e(pseudo_tag, " ERROR GlobalDatas.appsSupportedList.isEmpty()");
        }
        String pak = new String();
            int size = GlobalDatas.appsSupportedList.size();
            for (int i =0; size > i; i++){
                appButtonExistencePakageClass cl = GlobalDatas.appsSupportedList.get(i);
                TetDebugUtil.e(pseudo_tag,"in  getPaketNameByRadioButtonID radioButtonID=["+radioButtonID+"] cl=["+cl.getRadioBatoonID()+"]" );
                if (radioButtonID == cl.getRadioBatoonID()){
                   pak =  cl.appPackageName;
                }
            }

        TetDebugUtil.e(pseudo_tag,"getPaketNameByRadioButton pak =["+pak+"]");
        return pak;
    }

    public String  getPaketDecripByRadioButton(int radioButtonID){
        if (GlobalDatas.appsSupportedList.isEmpty()) {
            TetDebugUtil.e(pseudo_tag, " ERROR GlobalDatas.appsSupportedList.isEmpty()");
        }
        String desk = new String();
        int size = GlobalDatas.appsSupportedList.size();
        for (int i =0; size > i; i++){
            appButtonExistencePakageClass cl = GlobalDatas.appsSupportedList.get(i);
            if (radioButtonID == cl.getRadioBatoonID()){
                desk =  cl.appPackageName;
            }
        }

        TetDebugUtil.e(pseudo_tag,"getPaketNameByRadioButton pak =["+desk+"]");
        return desk;
    }

    public int  getRadioButtonIDByPakageName(String appPackageName){
        if (GlobalDatas.appsSupportedList.isEmpty()) {
            TetDebugUtil.e(pseudo_tag, " ERROR GlobalDatas.appsSupportedList.isEmpty()");
        }

        int size = GlobalDatas.appsSupportedList.size();
        int radioButton = 0;
        for (int i =0; size > i; i++){
            appButtonExistencePakageClass cl = GlobalDatas.appsSupportedList.get(i);
            if (appPackageName.equals(cl.getAppPackageName())){
                radioButton =  cl.getRadioBatoonID();
            }
        }

        TetDebugUtil.e(pseudo_tag,"getPaketNameByRadioButton pak =["+radioButton+"]");
        return radioButton;
    }

    public RadioButton  getRadioButtonByPakageName(String appPackageName){
        if (GlobalDatas.appsSupportedList.isEmpty()) {
            TetDebugUtil.e(pseudo_tag, " ERROR GlobalDatas.appsSupportedList.isEmpty()");
        }

        int size = GlobalDatas.appsSupportedList.size();
        RadioButton radioButton = null;
        for (int i =0; size > i; i++){
            appButtonExistencePakageClass cl = GlobalDatas.appsSupportedList.get(i);
            if (appPackageName.equals(cl.getAppPackageName())){
                radioButton =  cl.getRadioBatoon();
            }
        }

        TetDebugUtil.e(pseudo_tag,"getPaketNameByRadioButton pak =["+radioButton.getId()+"]");
        return radioButton;
    }

    public String getPaketDecripByPackageName(String appPackageName) {
        if (GlobalDatas.appsSupportedList.isEmpty()) {
            TetDebugUtil.e(pseudo_tag, " ERROR GlobalDatas.appsSupportedList.isEmpty()");
        }
        int size = GlobalDatas.appsSupportedList.size();
        String packageDescription = new String();
        for (int i =0; size > i; i++){
            appButtonExistencePakageClass cl = GlobalDatas.appsSupportedList.get(i);
            if (appPackageName.equals(cl.getAppPackageName())){
                packageDescription =  cl.getPackageDescription();
            }
        }

        TetDebugUtil.e(pseudo_tag,"packageDescription =["+packageDescription+"]");
        return packageDescription;
    }

}
