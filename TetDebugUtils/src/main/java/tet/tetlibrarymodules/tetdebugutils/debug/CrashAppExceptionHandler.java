package tet.tetlibrarymodules.tetdebugutils.debug;

import android.app.Activity;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import tet.tetlibrarymodules.tetdebugutils.debug.debug_tools.AppNameGeter;


/* Usage ;
@Override
public void onCreate(Bundle savedInstanceState) {
super.onCreate(savedInstanceState);
Thread.setDefaultUncaughtExceptionHandler(new TopExceptionHandler(this)); */


public class CrashAppExceptionHandler implements Thread.UncaughtExceptionHandler {
    private Thread.UncaughtExceptionHandler defaultUEH;
    private Activity app = null;
    private CPUInformation cpuInfo = new CPUInformation();
    private DeviceInfo deviceInfo = new DeviceInfo();

    public CrashAppExceptionHandler(Activity app) {
        this.defaultUEH = Thread.getDefaultUncaughtExceptionHandler();
        this.app = app;
    }

    public void uncaughtException(Thread t, Throwable e) {
        StackTraceElement[] arr = e.getStackTrace();
        String report = "";
        report += e.toString() + "\n\n";

        for (int i = 0; i < arr.length; i++) {
            report += "    " + arr[i].toString() + "\n";
        }

        // If the exception was thrown in a background thread inside
        // AsyncTask, then the actual exception can be found with getCause

        report += "--------- Cause ---------\n\n";
        Throwable cause = e.getCause();
        if (cause != null) {
            report += cause.toString() + "\n\n";
            arr = cause.getStackTrace();
            for (int i = 0; i < arr.length; i++) {
                report += "    " + arr[i].toString() + "\n";
            }
        }
        report += "!!!!!!!!!!\n";
        report += "-------------------------------!";

        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
        String header = "";

        String fileName = AppNameGeter.getAppNameByActivity(app);

        if (!new File(app.getFilesDir() + "/" + fileName).exists()) {
          //  header += fileName + TetGlobalData.TOKEN_SEPARATOR + TetGlobalData.CRASH_REPORT + TetGlobalData.TOKEN_SEPARATOR + "\n";
            header += fileName + " !!!!!!!!!!!!!!!!!!!!!!!! Device Info \n" + deviceInfo.getDeviceInfo() + "\n !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!    CPU Info !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!\n " + cpuInfo.getInfo() + "\n !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!\n\n\n";
        }
        header += "\n\n--------- Stack trace Day = [" + currentDate + "] Time = [" + currentTime + "]---------\n\n";
        ;

        String recordToFile = "";
        recordToFile = header + report;


        try {
            PrintWriter prWr = new PrintWriter(new BufferedWriter(new FileWriter(app.getFilesDir() + "/" + fileName, true)));
            prWr.println(recordToFile);
            prWr.close();
            prWr.flush();
        } catch (IOException ioe) {
            //  e.printStackTrace();
        }

        defaultUEH.uncaughtException(t, e);
    }
}
