package tet.oleg_zhabko.tsp.ui.autonom;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import tet.oleg_zhabko.tsp.R;
import tet.oleg_zhabko.tsp.ThisApp;
import tet.oleg_zhabko.tsp.datas.GlobalDatas;
import tet.oleg_zhabko.tsp.ui.utils.points_and_maps.ActivityOsmOnLineAddPoint;
import tet.oleg_zhabko.tsp.ui.utils.adapters.AdapterEditRoute;
import tet.tetlibrarymodules.tetdebugutils.debug.CrashAppExceptionHandler;
import tet.tetlibrarymodules.tetdebugutils.debug.debug_tools.TetDebugUtil;
import tet.oleg_zhabko.tsp.ui.utils.isFieldEmptyOrWrited;
import tet.oleg_zhabko.tsp.datas.routeDbManipulation;

public class ShowAndEditRoute extends Activity {
    private String pseudo_tag = ShowAndEditRoute.class.getSimpleName();
    private ListView listView;
    private TextView tvOrgName;
    private EditText edRouteNewName;
    AdapterEditRoute adapter;
    private Button buttonNewPoint;
    private Button buttonSelectPoint;
    private Button buttonSaveRoute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Thread.setDefaultUncaughtExceptionHandler(new CrashAppExceptionHandler(this));
        ThisApp.getInstance().adjastFontScale();
        TetDebugUtil.e(pseudo_tag, "!================= START " + pseudo_tag + "============");

        setContentView(R.layout.activity_show_and_edit_route);

        listView = (ListView) findViewById(R.id.listViewR);
        tvOrgName = (TextView) findViewById(R.id.tvRoutActivOrg);
        tvOrgName.setText(GlobalDatas.getOrgName());
        edRouteNewName = (EditText) findViewById(R.id.edRouteName);
        buttonSelectPoint = (Button) findViewById(R.id.butSelectPoint);
        buttonNewPoint = (Button) findViewById(R.id.butNewPoint);
        buttonSaveRoute = (Button) findViewById(R.id.button_save);

        makeAdapter();


    }

    private void makeAdapter() {
        adapter = null;
        adapter = new AdapterEditRoute(this, GlobalDatas.pointChecked);
        listView.setAdapter(adapter);

        buttonSelectPoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), CreateNewRouteActivityAutonom.class));
                finish();
            }
        });

        buttonNewPoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ActivityOsmOnLineAddPoint.class));
                finish();
            }
        });

        buttonSaveRoute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // edRouteNewName
                if (isFieldEmptyOrWrited.isEditTextEmpty(edRouteNewName)) {
                    String msg = getResources().getString(R.string.txtAddRouteName);
                    Toast.makeText(ShowAndEditRoute.this, msg, Toast.LENGTH_LONG).show();
                    return;
                }
                if (GlobalDatas.getOrgName().isEmpty()){
                    String msg = getResources().getString(R.string.txtVhoiseOrganisatoin);
                    Toast.makeText(ShowAndEditRoute.this, msg, Toast.LENGTH_LONG).show();
                    return;
                }

                String routeName = edRouteNewName.getText().toString();
                if (routeDbManipulation.insertReplaceNewRouteToDb(ShowAndEditRoute.this,GlobalDatas.getOrgName(), routeName, GlobalDatas.pointChecked)){
                    startActivity(new Intent(getApplicationContext(),ChoiceRouteActivityAutonom.class));
                    finish();
                }
            }
        });


    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //  startActivity(new Intent(getApplicationContext(), ActivityOsmOnLineAddPoint.class));
        //  this.finish();
    }

}