package tet.oleg_zhabko.tsp.ui.utils;

import android.content.Context;
import android.os.Environment;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import tet.tetlibrarymodules.tetdebugutils.debug.debug_tools.TetDebugUtil;

/*
* How to use:
* button.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        PdfUtil.copyPdfToDownloads(MainActivity.this);
    }
});
*
* Check permition if Android 6.0 (API 23),
* if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        != PackageManager.PERMISSION_GRANTED) {
    ActivityCompat.requestPermissions(this,
            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
}
*
* */


public class PdfUtil {
    private static String pseudo_tag = PdfUtil.class.getSimpleName();


    public static void copyPdfToDownloads(Context context,  String pdfFileName) {
        File outputDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        File outputFile = new File(outputDir, pdfFileName);


        // Checking if the file exists
        TetDebugUtil.e(pseudo_tag, "doGet file frov assets "+pdfFileName+"");
        if (!outputFile.exists()) {
            try {
                InputStream inputStream = context.getAssets().open(pdfFileName);

                FileOutputStream outputStream = new FileOutputStream(outputFile);

                byte[] buffer = new byte[1024];
                int length;

                while ((length = inputStream.read(buffer)) > 0) {
                    outputStream.write(buffer, 0, length);
                }

                outputStream.close();
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

