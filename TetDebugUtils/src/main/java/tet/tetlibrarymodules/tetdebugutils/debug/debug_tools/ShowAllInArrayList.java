package tet.tetlibrarymodules.tetdebugutils.debug.debug_tools;

import java.util.ArrayList;

public class ShowAllInArrayList {
    String pseudo_tag = getClass().getSimpleName();

    public ShowAllInArrayList(String tag, ArrayList arrayList){
        TetDebugUtil.d(tag, "||||||||| INITIALIZER "+tag+"");
        if (!arrayList.isEmpty()){
            int i = 0;
            int size = arrayList.size();
            while (i < size) {

                TetDebugUtil.d(pseudo_tag, "ShowAllInArrayList = ["+i+"] :" + arrayList.get(i));
                //System.out.print("I = ["+i+"] :" + iterator.next());
                i++;
            }
        } else {
            TetDebugUtil.e(pseudo_tag,"ERROR arrayList.isEmpty()");
        }

    }
}
