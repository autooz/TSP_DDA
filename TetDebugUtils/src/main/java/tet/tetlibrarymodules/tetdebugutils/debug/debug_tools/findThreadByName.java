package tet.tetlibrarymodules.tetdebugutils.debug.debug_tools;

import android.util.Log;

import java.util.Set;

public class findThreadByName {


    String pseudo_tag = "findThreadByName";

    public findThreadByName(String threadName) {


        Set<Thread> threadSet = Thread.getAllStackTraces().keySet();
        Thread[] threadArray = threadSet.toArray(new Thread[threadSet.size()]);

        for (int i = 0; i < threadArray.length; i++) {

            if (threadArray[i].getName().equals(threadName)) {

                TetDebugUtil.d(pseudo_tag, "Thread :[" + threadArray[i].getName() + "]");
                Log.e(pseudo_tag, "finishAffinity();");
            }
        }
    }


}
