package tet.oleg_zhabko.tsp.ui.utils.appAndMaps;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.widget.Toast;

public class isAPKInstalledOnDEvice {
        private final Context context;

        public isAPKInstalledOnDEvice(Context context) {
            this.context = context;
        }

        // Метод для проверки установки и открытия Play Store, если приложение не установлено
        public void checkAndOpenApp(String packageName, String appName) {
            if (isAppInstalled(packageName)) {
                Toast.makeText(context, appName + " установлено", Toast.LENGTH_LONG).show();
            } else {
                openAppInPlayStore(packageName);
            }
        }

        // Проверяет, установлено ли приложение по его пакету
        private boolean isAppInstalled(String packageName) {
            PackageManager pm = context.getPackageManager();
            try {
                pm.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
                return true;
            } catch (PackageManager.NameNotFoundException e) {
                return false;
            }
        }

        // Открывает страницу приложения в Google Play
        private void openAppInPlayStore(String packageName) {
            try {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + packageName));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            } catch (android.content.ActivityNotFoundException e) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + packageName));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        }

        // Метод для проверки всех приложений из списка
        public void checkAllApps() {
            checkAndOpenApp("com.waze", "Waze Navigation & Live Traffic");
            checkAndOpenApp("net.osmand", "OsmAnd - Maps & GPS Offline");
            checkAndOpenApp("com.sygic.aura", "Sygic GPS Navigation & Maps");
            checkAndOpenApp("com.huawei.maps.app", "Petal Maps - GPS & Navigation");
            checkAndOpenApp("com.mapswithme.maps.pro", "MAPS.ME");
            checkAndOpenApp("ru.dublgis.dgismobile", "2GIS");
            checkAndOpenApp("com.navitel", "NAVITEL");
            checkAndOpenApp("com.google.android.apps.maps", "Google Maps");
            checkAndOpenApp("com.nng.igo.primong.igoworld", "iGO Navigation");
        }

    public void navigateToLocation( double latitude, double longitude, String appPackageName) {
        Uri uri;
        Intent intent;

        if (isAppInstalled(appPackageName)) {
        switch (appPackageName) {
            case "com.waze":
                uri = Uri.parse("waze://?ll=" + latitude + "," + longitude + "&navigate=yes");
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
            case "com.tomtom.gplay.navapp":
                uri = Uri.parse("google.navigation:q=" + latitude + "," + longitude + "&mode=d");
                break;
            case "com.mapswithme.maps.pro":
                uri = Uri.parse("geo:" + latitude + "," + longitude + "?q=" + latitude + "," + longitude);
                break;
            case "ru.dublgis.dgismobile":
                uri = Uri.parse("dgis://2gis.ru/routeSearch/rsType/car/to/" + longitude + "," + latitude);
                break;
            case "com.navitel":
                uri = Uri.parse("geo:" + latitude + "," + longitude + "?q=" + latitude + "," + longitude);
                break;
            case "com.google.android.apps.maps":
                uri = Uri.parse("geo:" + latitude + "," + longitude + "?q=" + latitude + "," + longitude);
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
        k;lkkk;lk

            context.startActivity(intent);
        } else {
            Toast.makeText(context, "Приложение не установлено", Toast.LENGTH_LONG).show();
        }
    }

}
