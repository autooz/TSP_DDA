package tet.oleg_zhabko.tsp.ui.utils.points_and_maps;

import android.content.Context;
import android.content.Intent;

import tet.oleg_zhabko.tsp.datas.GlobalDatas;
import tet.oleg_zhabko.tsp.ui.utils.SettingsUtils;
import tet.tetlibrarymodules.tetdebugutils.debug.debug_tools.TetDebugUtil;

public class MapsOpenerReturnIntent {


 public static Intent getIntentPreferedMap(Context applicationContext){

     TetDebugUtil.e(MapsOpenerReturnIntent.class.getSimpleName(),"-----------START MapsOpener.class.getSimpleName()");

     Intent intent = null;


     String activitySimpleName = new SettingsUtils().getSettingValue(GlobalDatas.db_name, GlobalDatas.setingMapsActivity);

     if(activitySimpleName.equals(ActivityOsmOnline.class.getSimpleName())){
        intent = new Intent(applicationContext, ActivityOsmOnline.class);
     } else if (activitySimpleName.equals(ActivityGoogleMapWebView.class.getSimpleName())){
         intent = new Intent(applicationContext,ActivityGoogleMapWebView.class);
     }
     return intent;
 }

}
