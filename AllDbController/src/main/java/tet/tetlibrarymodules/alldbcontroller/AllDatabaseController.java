package tet.tetlibrarymodules.alldbcontroller;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.StringTokenizer;

import tet.tetlibrarymodules.tetdebugutils.debug.debug_tools.ShowAllInArrayList;
import tet.tetlibrarymodules.tetdebugutils.debug.debug_tools.TetDebugUtil;


/**
 * Created by oleg on 07.02.17.
 */

public class AllDatabaseController {

    private static volatile AllDatabaseController SINGLE_CONTROLLER;
    private final String TAG = AllDatabaseController.class.getSimpleName();

    protected void delete(Context context, String db_name, String tableName, String statement) {
        TetDebugUtil.d(TAG, "delete");
        AllDBHelperSQLite dbhelper = new AllDBHelperSQLite(context, db_name);
        SQLiteDatabase sqliteDB = dbhelper.getWritableDatabase();
        sqliteDB.delete(tableName, statement, null);
        sqliteDB.close();
        dbhelper.close();
    }


    private AllDatabaseController() {

    }


    protected void execute(Context context, String db_name, String query) {

        AllDBHelperSQLite dbhelper = new AllDBHelperSQLite(context, db_name);
        SQLiteDatabase sqliteDB = dbhelper.getWritableDatabase();
        sqliteDB.enableWriteAheadLogging();
        //TetDebugUtil.d(TAG, "protected void execute db_name=[" + db_name + "]  query=[" + query + "]");
        try {
            try {
                sqliteDB.execSQL(query);
            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (SQLiteException e) {
            Log.e(TAG, "Failed open database. ", e);
        } catch (SQLException e) {
            Log.e(TAG, "Failed to update Names. ", e);
        } finally {
            sqliteDB.close();
            dbhelper.close();
        }
    }

    public ArrayList<ArrayList<String>> executeQuery(Context context, String db_name, String query) {

         TetDebugUtil.d(TAG,"For db_name=["+db_name+"] query=["+query+"]");

        ArrayList<ArrayList<String>> list = new ArrayList<ArrayList<String>>();
        StringTokenizer st = new StringTokenizer(query, " ");
        if (st.hasMoreTokens()) {
            String sqlCom = st.nextToken().toLowerCase();
            if (!sqlCom.equals("select")) {
                TetDebugUtil.d(TAG, "db_name=[" + db_name + "] execute query not select=[" + query + "]");
                execute(context, db_name, query);
                return list;
            }
        }
      //  TetDebugUtil.d(TAG, "db=[" + db_name + "] SELECT executeQuery=[" + query + "]");
        AllDBHelperSQLite dbhelper = new AllDBHelperSQLite(context, db_name);
        SQLiteDatabase sqliteDB = null;
        try {
            sqliteDB = dbhelper.getReadableDatabase();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                sqliteDB = dbhelper.getWritableDatabase();
            } catch (SQLiteException se) {
                se.printStackTrace();
            }
        }
        if (sqliteDB == null) {
            return list;
        }
        //sqliteDB.enableWriteAheadLogging();
        Cursor c = null;
        try {
            c = sqliteDB.rawQuery(query, null);
        } catch (SQLiteException e) {
            e.printStackTrace();
            return list;
        } finally {

            if (c != null) {
                if (c.moveToFirst()) {
                    do {
                        ArrayList<String> subList = new ArrayList<String>();
                        for (int i = 0; i < c.getColumnCount(); i++) {
                            subList.add(c.getString(i));
                        }
                        list.add(subList);
                    } while (c.moveToNext());
                } else {
                    TetDebugUtil.d(TAG, "0 rows");
                }
                c.close();
            }
            sqliteDB.close();
            dbhelper.close();
            return list;
        }
    }

    public ArrayList<varsForAdapter> getVarArrayListForAdapter(Context context, String db_name, String query) {
//        if (context_or_null == null) {
//            context_or_null = ThisApp.getInstance().getContextApp();
//        }

        TetDebugUtil.d(TAG, "db_name = [" + db_name + "]");
        AllDBHelperSQLite dbhelper = new AllDBHelperSQLite(context, db_name);
        SQLiteDatabase database = dbhelper.getWritableDatabase();
        database.enableWriteAheadLogging();

        ArrayList<varsForAdapter> data = new ArrayList<varsForAdapter>();
        varsForAdapter temp;
        //курсор для обхода информации, полученной из бд
        Cursor cursor = null;
        try {
            cursor = database.rawQuery(query, null);
        } catch (Exception e) {
            e.printStackTrace();
            return data;
        }

        if (!cursor.moveToFirst()) {
            TetDebugUtil.d(TAG, "нет ни одной записи, удовл данному запросу");
            database.close();
            return data;
        }

        do {
            temp = new varsForAdapter();

            for (String str : cursor.getColumnNames()) {
                temp.varArrayList.add(cursor.getString(cursor.getColumnIndex(str)));
            }

            data.add(temp);

        } while (cursor.moveToNext());
        cursor.close();
        database.close();
        return data;

    }

    public static AllDatabaseController getSingleControllerInstance() {
        if (SINGLE_CONTROLLER == null) {
            synchronized (AllDatabaseController.class) {
                if (SINGLE_CONTROLLER == null) {
                    SINGLE_CONTROLLER = new AllDatabaseController();
                }
            }
        }
        return SINGLE_CONTROLLER;
    }


    public boolean isDbCreated(Context context, String dbName) {
            File dbFile = context.getDatabasePath(dbName);
            return dbFile.exists();
    }


    public void createNewDb(Context context,String db_name) {
        AllDBHelperSQLite dbhelper = new AllDBHelperSQLite(context, db_name);
    }

}