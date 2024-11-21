package tet.tetlibrarymodules.alldbcontroller;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import tet.tetlibrarymodules.tetdebugutils.debug.debug_tools.TetDebugUtil;

/**
 * Created by oleg on 25.10.16.
 */

public class AllDBHelperSQLite extends SQLiteOpenHelper {


    private static final int DATABASE_VERSION = 1;
    private static final String TAG = AllDBHelperSQLite.class.getSimpleName();

    public AllDBHelperSQLite(Context context, String db_name) {
        // private String DATABASE_NAME;
        super(context, db_name, null, DATABASE_VERSION);
        TetDebugUtil.d(TAG, "constructor");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(TAG, "Update database from version  " + oldVersion
                + " to " + newVersion + ", which remove all old records");
        onCreate(db);
    }

}
