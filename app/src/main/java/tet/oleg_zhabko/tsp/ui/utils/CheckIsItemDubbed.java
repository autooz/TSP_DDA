package tet.oleg_zhabko.tsp.ui.utils;

import java.util.ArrayList;

import tet.tetlibrarymodules.tetdebugutils.debug.debug_tools.TetDebugUtil;

public class CheckIsItemDubbed {

    private static String pseudo_tag = CheckIsItemDubbed.class.getSimpleName();

    public static boolean checkIsDubbed(ArrayList<ArrayList<String>> AllPointChecked, ArrayList<String> currentItem) {
        String cur_id = currentItem.get(0);
        int size = AllPointChecked.size();

        for (int i = 0; size > i; i++){
            ArrayList<String> stAr=AllPointChecked.get(i);
            if (!stAr.isEmpty()){
                String point_id = stAr.get(0);
                if (cur_id.equals(point_id)){
                    return true;
                }
            }
        }
        return false;
    }




}
