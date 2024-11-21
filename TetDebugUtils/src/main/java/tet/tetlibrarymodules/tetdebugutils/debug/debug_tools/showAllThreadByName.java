package tet.tetlibrarymodules.tetdebugutils.debug.debug_tools;

import java.util.Set;

/**
 * Created by oleg on 27.09.17.
 */

public class showAllThreadByName {

    String pseudo_tag = "showThreadByName";

    public showAllThreadByName() {


        Set<Thread> threadSet = Thread.getAllStackTraces().keySet();
        Thread[] threadArray = threadSet.toArray(new Thread[threadSet.size()]);

        for (int i = 0; i < threadArray.length; i++) {
            TetDebugUtil.d(pseudo_tag, "Thread :[" + threadArray[i].getName() + "]");
        }
    }
}
