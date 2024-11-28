package tet.oleg_zhabko.tsp.datas;// Created: by PC BEST, OS Linux

import android.content.Context;

import org.osmdroid.util.GeoPoint;

import java.util.ArrayList;
import java.util.List;

import tet.oleg_zhabko.tsp.ThisApp;
import tet.oleg_zhabko.tsp.ui.utils.appAndMaps.workWithApkNaviOnDevice;
import tet.oleg_zhabko.tsp.ui.utils.points_and_maps.osmTools.osmToolsAddNecessaryItems;
import tet.tetlibrarymodules.alldbcontroller.AllDatabaseController;
import tet.tetlibrarymodules.tetdebugutils.debug.debug_tools.TetDebugUtil;

// Copyright:  Copyright (c) 2008-2024 Best LLC & Oleg Zhabko. All rights reserved.
//License: ASK LICENSE TERMS AND CONDITIONS!
//             Oleg Zhabko, mailto:olegzhabko@gmail.com
//             phone/WhataApp/Viber/Telegram: +38(067) 411-98-75
//              Berdichev, Ukraine
//
public class GlobalDatas {

    public static ArrayList<ArrayList<String>> pointChecked = new ArrayList<>();
    public static ArrayList<workWithApkNaviOnDevice.appButtonExistencePakageClass> appsSupportedList = new ArrayList<>();

    private static String pseudo_tag = GlobalDatas.class.getSimpleName();

    private static AllDatabaseController allDbController = AllDatabaseController.getSingleControllerInstance();
    public static String db_name = "routing.db";
    private static String organisation = new String();
    public static String orgId = new String();
    public static String saleManName = new String();
    public static String saleManId = new String();
    public static String zoneName = new String();
    public static String zoneId = new String();
    public static String setingMapsActivity = "mapsActivity";
    public static String navigationAPP = new String();
    public static GeoPoint startGeoPoint = new GeoPoint(49.8992800, 28.6023500);
    public static float minGpsAccurracy = 10.1f;
    public static boolean gpsPaused = false;

  public static String getOrgName(){
      return organisation;
  }
  public static String getOrgId(){
      return orgId;
  }
    public static void setOrgNameAndOrgId(String orgName){
        Context context = ThisApp.getContextApp();
        ArrayList<ArrayList<String>> idArAr = allDbController.executeQuery(context, db_name, "SELECT org_id FROM organisations WHERE organisation_name='" + orgName + "'");
        TetDebugUtil.e(pseudo_tag,"org_id = ] "+idArAr+" [");
       if (idArAr.isEmpty()){
           allDbController.executeQuery(context, db_name, "INSERT INTO organisations (organisation_name) VALUES ('" + orgName + "'");
           idArAr = allDbController.executeQuery(context, db_name, "SELECT org_id FROM organisations WHERE organisation_name='" + orgName + "'");
       }

       if (!idArAr.isEmpty()){
           ArrayList<String> idAr = idArAr.get(0);
           if(!idAr.isEmpty()){
               orgId=idAr.get(0);
               allDbController.executeQuery(context, db_name, "UPDATE organisations SET is_active='false'");

               allDbController.executeQuery(context, db_name, "UPDATE organisations SET is_active='true' WHERE org_id="+orgId+"");
               TetDebugUtil.e(pseudo_tag, "Updating orgId=["+orgId+"  idAr.get(0)=["+idAr.get(0)+"]");
           }

       }
        organisation = orgName;
    }
}
