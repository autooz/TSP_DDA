package tet.oleg_zhabko.tsp.ui.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;

import tet.oleg_zhabko.tsp.R;
import tet.oleg_zhabko.tsp.ui.utils.points_and_maps.ActivityOsmOnLineAddPoint;
import tet.tetlibrarymodules.tetdebugutils.debug.debug_tools.TetDebugUtil;

public class AddPointDialogClass extends Dialog implements android.view.View.OnClickListener {
    public String pseudo_tag = AddPointDialogClass.class.getSimpleName();
    public Activity activity;
    public Dialog dialog;
    public Button butoonFromMap, buttonCancel,ButtonCurrentPos;
    public MapView mapView;

    public AddPointDialogClass(Activity a, MapView mv) {
        super(a);
        // TODO Auto-generated constructor stub
        this.activity = a;
        mapView = mv;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_add_point);
        butoonFromMap = (Button) findViewById(R.id.btn_from_map);
        ButtonCurrentPos = (Button) findViewById(R.id.btn_current_coord);
        buttonCancel = (Button) findViewById(R.id.btn_cancel);
        butoonFromMap.setOnClickListener(this);
        buttonCancel.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
       int vID = v.getId();
       if (vID == R.id.btn_from_map){
           if (!activity.getClass().getSimpleName().equals(ActivityOsmOnLineAddPoint.class.getSimpleName())){
               activity.startActivity(new Intent(activity.getApplicationContext(), ActivityOsmOnLineAddPoint.class ));
           }

           //mapView = findViewById(R.id.mapViewOsmAddPoint);
           GeoPoint centerPoint = (GeoPoint) mapView.getMapCenter();
                double latitude = centerPoint.getLatitude();
                double longitude = centerPoint.getLongitude();
           TetDebugUtil.e(pseudo_tag,"latitude="+latitude+" longitude="+longitude+"");
       } else if (vID == R.id.btn_current_coord){

       } else if (vID == R.id.btn_cancel){

       }
        dismiss();
    }
}
