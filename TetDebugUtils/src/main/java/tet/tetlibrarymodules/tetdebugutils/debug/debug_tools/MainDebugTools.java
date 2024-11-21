package tet.tetlibrarymodules.tetdebugutils.debug.debug_tools;

import android.util.Log;

import java.util.ArrayList;

/**
 * Created by oleg on 13.07.18.
 */

public class MainDebugTools {


    private static String pseudo_tag = "MainDebugTools";

    public MainDebugTools() {

    }

    public boolean showAllParamsFromArrayString(ArrayList<String> row) {
        if (row.isEmpty()) {
            Log.e(pseudo_tag, "In showAllParamsFromArrayString Nothing in ArrayList<String>");
        } else {
            int size = row.size();
            Log.i(pseudo_tag, "size[" + size + "]");
            int i = 0;
            while (size >= i) {
                String st = row.get(i);
                TetDebugUtil.d(pseudo_tag, "showAllParamsFromArrayString param[" + i + "]=[" + st + "]");
                i++;
                if (size == i) {
                    TetDebugUtil.d(pseudo_tag, "showAllParamsFromArrayString finish");
                    return true;
                }
            }
        }
        return false;
    }
}
