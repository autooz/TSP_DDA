package tet.oleg_zhabko.tsp.ui.autonom;

import android.app.Activity;
import android.os.Bundle;



import tet.oleg_zhabko.tsp.R;
import tet.oleg_zhabko.tsp.ThisApp;
import tet.tetlibrarymodules.tetdebugutils.debug.CrashAppExceptionHandler;
import tet.tetlibrarymodules.tetdebugutils.debug.debug_tools.TetDebugUtil;

public class EditPoint extends Activity {

private String pseudo_tag = EditPoint.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Thread.setDefaultUncaughtExceptionHandler(new CrashAppExceptionHandler(this));
        ThisApp.adjastFontScale();
        TetDebugUtil.e(pseudo_tag, "!================= START " + pseudo_tag + "============");

        setContentView(R.layout.activity_edit_point);

    }
}