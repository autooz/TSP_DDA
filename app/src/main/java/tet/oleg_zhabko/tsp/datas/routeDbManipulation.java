package tet.oleg_zhabko.tsp.datas;

import android.content.Context;

import java.util.ArrayList;

import tet.tetlibrarymodules.alldbcontroller.AllDatabaseController;
import tet.tetlibrarymodules.tetdebugutils.debug.debug_tools.TetDebugUtil;

public class routeDbManipulation {
    private static final String pseudo_tag = routeDbManipulation.class.getSimpleName();


    public static boolean insertReplaceNewRouteToDb(Context context, String orgName, String routeName, ArrayList<ArrayList<String>> pointChecked) {
        AllDatabaseController allDbController = AllDatabaseController.getSingleControllerInstance();
        String create = "CREATE TABLE IF NOT EXISTS owner_routes (route_id INTEGER PRIMARY KEY AUTOINCREMENT, org_name VARCHAR (60), route_name VARCHAR (250), route_points TEXT (500))";
        allDbController.executeQuery(context, GlobalDatas.db_name,create);
        int size = pointChecked.size();
        String pointByComa = new String();
        for (int i=0; i<size;i++){
            ArrayList<String> aRaR = pointChecked.get(i);
            if (!aRaR.isEmpty()){
                String point_id = aRaR.get(0);
                if (i < size -1){
                    pointByComa = pointByComa + point_id + ",";
                } else {
                    pointByComa = pointByComa + point_id ;
                }
            }
        }
        TetDebugUtil.e(pseudo_tag,"pointByComa =["+ pointByComa +"]");
        String req = "insert or replace into owner_routes(org_name, route_name, route_points) values ('"+orgName+"', '"+routeName+"', '"+pointByComa+"')";
        try {
            allDbController.executeQuery(context, GlobalDatas.db_name,req);
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }

     return true;
    }

    public static boolean replaceCurrentRouteInDb(Context applicationContext, String orgName, ArrayList<ArrayList<String>> newdataList) {
        TetDebugUtil.e(pseudo_tag,"newdataList=["+newdataList+"]");

        return true;
    }
}


//public static boolean replaceCurrentRouteInDb(Context context, String orgName, String routeName, ArrayList<ArrayList<String>> pointChecked) {
//    AllDatabaseController allDbController = AllDatabaseController.getSingleControllerInstance();
//    String create = "CREATE TABLE IF NOT EXISTS owner_routes (route_id INTEGER PRIMARY KEY AUTOINCREMENT, org_name VARCHAR (60), route_name VARCHAR (250), route_points TEXT (500))";
//    allDbController.executeQuery(context, GlobalDatas.db_name,create);
//    int size = pointChecked.size();
//    String pointByComa = new String();
//    for (int i=0; i<size;i++){
//        ArrayList<String> aRaR = pointChecked.get(i);
//        if (!aRaR.isEmpty()){
//            String point_id = aRaR.get(0);
//            if (i < size -1){
//                pointByComa = pointByComa + point_id + ",";
//            } else {
//                pointByComa = pointByComa + point_id ;
//            }
//        }
//    }
//    TetDebugUtil.e(pseudo_tag,"pointByComa =["+ pointByComa +"]");
//    String req = "insert or replace into owner_routes(org_name, route_name, route_points) values ('"+orgName+"', '"+routeName+"', '"+pointByComa+"')";
//    try {
//        allDbController.executeQuery(context, GlobalDatas.db_name,req);
//    } catch (Exception e){
//        e.printStackTrace();
//        return false;
//    }
//
//    return true;
//}