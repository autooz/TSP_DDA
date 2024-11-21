package tet.oleg_zhabko.tsp.ui.route;

import android.content.Context;

import java.util.ArrayList;
import java.util.StringTokenizer;

import tet.oleg_zhabko.tsp.datas.GlobalDatas;
import tet.oleg_zhabko.tsp.ui.utils.adapters.AdapterChoiseRoutes;
import tet.tetlibrarymodules.alldbcontroller.AllDatabaseController;
import tet.tetlibrarymodules.tetdebugutils.debug.debug_tools.TetDebugUtil;

public class CreateTableForCurrentRoute {

    private static final String pseudo_tag = CreateTableForCurrentRoute.class.getSimpleName();
    private static AllDatabaseController allDatabaseController = AllDatabaseController.getSingleControllerInstance();
    private static String variable = " point_id, organisation_name, zone, folder_color, sales_name," +
            " phone_number, point_owner, latitude, longitude, landmarks, description, phone_ambar," +
            " description_office, work_from_h, work_from_m, work_to_h, work_to_m, strike_from_h," +
            " strike_from_m, strike_to_h, strike_to_m, color, pin_icon_code, color1";

    public static void createByStringWithCommas(Context context, String points) {

        makeTableCurrentRoute(context);

        StringTokenizer tokenizer = new StringTokenizer(points, ",");
        int size = tokenizer.countTokens();
        for (int i = 0; i < size; i++) {
            String id = tokenizer.nextToken();
            ArrayList<ArrayList<String>> aRaR = null;
            try {
                aRaR = allDatabaseController.executeQuery(context, GlobalDatas.db_name, "SELECT * FROM owner_points WHERE point_id=" + id + ";");
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (aRaR.isEmpty()) {
                return;
            }
            ArrayList<String> valList = aRaR.get(0);
            if (!makeAddToDbValueString(context, valList)) {
                TetDebugUtil.e(pseudo_tag, "ERROR makeAddToDbValueString(valList);");
                return;
            }
        }
    }

    private static void makeTableCurrentRoute(Context context) {

        try {
            allDatabaseController.executeQuery(context, GlobalDatas.db_name, "DROP TABLE IF EXISTS current_route;");
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            allDatabaseController.executeQuery(context, GlobalDatas.db_name, "CREATE TABLE IF NOT EXISTS current_route (id INTEGER PRIMARY KEY AUTOINCREMENT, point_id INTEGER (6), organisation_name VARCHAR (50), zone VARCHAR (100), folder_color VARCHAR (50), sales_name VARCHAR (50), phone_number VARCHAR (50), point_owner VARCHAR (50), latitude REAL, longitude REAL, landmarks VARCHAR (150), description VARCHAR (50), phone_ambar VARCHAR (50), description_office VARCHAR (350), work_from_h VARCHAR (10), work_from_m VARCHAR (10), work_to_h VARCHAR (10), work_to_m VARCHAR (10), strike_from_h VARCHAR (10), strike_from_m VARCHAR (10), strike_to_h VARCHAR (10), strike_to_m VARCHAR (10), color VARCHAR (50), pin_icon_code INTEGER, color1 VARCHAR (50));");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private static boolean makeAddToDbValueString(Context context, ArrayList<String> valList) {
        if (valList.isEmpty()) {
            return false;
        }
        int sizeColum = valList.size();
        String val = new String();
        for (int i = 0; i < sizeColum; i++) {
            if (i == sizeColum - 1) {
                val = val + "'" + valList.get(i) + "'";
            } else {
                val = val + "'" + valList.get(i) + "',";
            }
        }
        TetDebugUtil.e(pseudo_tag, "makeAddToDbValueString {" + val + "}");
        try {
            allDatabaseController.executeQuery(context, GlobalDatas.db_name, "INSERT INTO current_route (" + variable + ") VALUES (" + val + ")");
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


    public static boolean createFromArayAray(Context context, ArrayList<ArrayList<String>> newdataList) {
        if (!newdataList.isEmpty()) {
            TetDebugUtil.e(pseudo_tag, " ERROR ArrayList<ArrayList<String>> newdataList isEmpty()");


            ArrayList<String> valList = new ArrayList<>();
            int size = newdataList.size();
            for (int i = 0; i < size; i++) {
                ArrayList<String> ar = newdataList.get(i);
                if (!ar.isEmpty()) {
                    valList.add(ar.get(i));
                }
            }
            if (!valList.isEmpty()) {
                if (makeAddToDbValueString(context, valList)) {
                    return true;
                }
            }
        }

        return false;
    }
}
