package tet.oleg_zhabko.tsp.ui;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.app.Activity; // Импортируем класс Activity
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


import tet.oleg_zhabko.tsp.R;
import tet.oleg_zhabko.tsp.ThisApp;
import tet.oleg_zhabko.tsp.ui.utils.PdfUtil;

public class MainActivityWiaServer extends Activity {
    private static final int REQUEST_WRITE_STORAGE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main_wia_server);

    //     Button r = (Button) findViewById(R.id.toread);
    //     Button d = (Button) findViewById(R.id.toDownload);

    //     r.setOnClickListener(new View.OnClickListener() {
    //         @Override
    //         public void onClick(View v) {

    //         }
    //     });

    //     d.setOnClickListener(new View.OnClickListener() {
    //         @Override
    //         public void onClick(View v) {
    //             // PdfUtil.copyPdfToDownloads(MainActivityWiaServer.this, "commersCompile.uk.pdf");
    //             checkPermissionAndCopyPdf();
    //         }
    //     });


    // }
    // private void checkPermissionAndCopyPdf() {
    //     if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
    //             != PackageManager.PERMISSION_GRANTED) {
    //         ActivityCompat.requestPermissions(this,
    //                 new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_STORAGE);
    //     } else {
    //         PdfUtil.copyPdfToDownloads(this, "commersCompile.uk.pdf");
    //     }
    // }

    // @Override
    // public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
    //     super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    //     if (requestCode == REQUEST_WRITE_STORAGE) {
    //         if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
    //             PdfUtil.copyPdfToDownloads(this, "commersCompile.uk.pdf");;
    //         } else {
    //             Toast.makeText(this, "Разрешение на запись отклонено", Toast.LENGTH_SHORT).show();
    //         }
    //     }
    // }
}
