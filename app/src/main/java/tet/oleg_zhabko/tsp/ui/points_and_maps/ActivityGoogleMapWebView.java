package tet.oleg_zhabko.tsp.ui.points_and_maps;

import static android.view.Gravity.CENTER;
import static android.view.Gravity.CENTER_HORIZONTAL;
import static android.view.Gravity.CENTER_VERTICAL;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.text.Layout;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.constraintlayout.helper.widget.Layer;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

import tet.oleg_zhabko.tsp.R;
import tet.oleg_zhabko.tsp.ui.MainActivityAutonom;
import tet.oleg_zhabko.tsp.ui.points_and_maps.gpsTools.GpsManager;
import tet.tetlibrarymodules.tetdebugutils.debug.debug_tools.TetDebugUtil;

public class ActivityGoogleMapWebView extends Activity implements GpsManager.OnLocationReceivedListener {

    private String pseudo_tag = ActivityGoogleMapWebView.class.getSimpleName();
    private WebView webView;
    private int height;
    private int width;
    private int currentHeight;
    private int currentWidth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_map_web_view);
        webView = (WebView) findViewById(R.id.webView);

        webView.setWebViewClient(new WebViewClient());
        webView.addJavascriptInterface(new WebAppInterface(this), "Android");
        webView.setKeepScreenOn(true);
        webView.setWebContentsDebuggingEnabled(true);

       // webView.loadUrl("https://www.google.com/maps/dir/49.9857896,28.5399744/49.9157613,28.7030527");
        webView.loadUrl("https://www.google.com/maps/dir/49.9857896,28.5399744/49.8857896,28.5399744/50.0857896,28.7399744/49.9157613,28.7030527");

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setBuiltInZoomControls(true);


        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        height = displaymetrics.heightPixels;
        width = displaymetrics.widthPixels;
        currentHeight=height;           //assuming that the phone
        currentWidth = width;
        //is held in portrait mode initially

        loadImage();

    }



    private void refreshMapPosition(LatLng pos, float angle) {

        CameraPosition.Builder positionBuilder = new CameraPosition.Builder();
        positionBuilder.target(pos);
//        positionBuilder.zoom(15f);
//        positionBuilder.bearing(angle);
        positionBuilder.tilt(60);
        CameraUpdate camFactory = CameraUpdateFactory.newCameraPosition(positionBuilder.build());

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), MainActivityAutonom.class));
        this.finish();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig){
        if(newConfig.equals(Configuration.ORIENTATION_LANDSCAPE)){

            currentHeight=width;
            loadImage();

        }if(newConfig.equals(Configuration.ORIENTATION_PORTRAIT)){

            currentHeight=height;
            loadImage();

        }
    }

    public void loadImage(){
        LinearLayout layOutCenter = new LinearLayout(this);
        layOutCenter.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        layOutCenter.setOrientation(LinearLayout.HORIZONTAL);

        ImageView iv = new ImageView(this);
        iv.setImageResource(R.drawable.center_of_map);

        layOutCenter.addView(iv);
        layOutCenter.setClickable(true);
        layOutCenter.setGravity(CENTER);
        layOutCenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TetDebugUtil.e(pseudo_tag, "Click on inage or layer");
            }
        });

    }

    @Override
    public void onGetLocation(@NonNull Location location) {
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        refreshMapPosition(latLng, 0);

    }

    @Override
    public void onGpsStatusChanged(String provider, int status) {

    }

    @Override
    public void GpsDisabled(String provider) {

    }

}