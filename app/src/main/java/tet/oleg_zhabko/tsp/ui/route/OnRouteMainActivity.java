package tet.oleg_zhabko.tsp.ui.route;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import tet.oleg_zhabko.tsp.R;
import tet.oleg_zhabko.tsp.ThisApp;
import tet.oleg_zhabko.tsp.datas.GlobalDatas;
import tet.oleg_zhabko.tsp.datas.routeDbManipulation;
import tet.oleg_zhabko.tsp.ui.autonom.AddNewPointOwnPoint;
import tet.oleg_zhabko.tsp.ui.autonom.AddNewPointToCurrentRoute;
import tet.oleg_zhabko.tsp.ui.autonom.ChoiceRouteActivityAutonom;
import tet.oleg_zhabko.tsp.ui.autonom.ShowAndEditRoute;
import tet.oleg_zhabko.tsp.ui.utils.adapters.AdapterCurrentRoute;
import tet.oleg_zhabko.tsp.ui.utils.adapters.AdapterEditRoute;
import tet.tetlibrarymodules.alldbcontroller.AllDatabaseController;
import tet.tetlibrarymodules.tetdebugutils.debug.CrashAppExceptionHandler;
import tet.tetlibrarymodules.tetdebugutils.debug.debug_tools.ShowAllInArrayList;
import tet.tetlibrarymodules.tetdebugutils.debug.debug_tools.TetDebugUtil;

public class OnRouteMainActivity extends Activity {

    private String pseudo_tag = OnRouteMainActivity.class.getSimpleName();
    private AllDatabaseController allDatabaseController = AllDatabaseController.getSingleControllerInstance();
    Context context = this;
    ListView listViewCarrentR;
    AdapterCurrentRoute adapterOfCurrent;
    private AdapterEditRoute adapterCorrection;
    ListView listViewCorrection;
    private TextView orgName;
    private TextView routeNameV;
    private LinearLayout linearLayout;
    private Button butn_correct;
    private Button butSaveCuren;
    private Button butAddPoint;
    private ArrayList<ArrayList<String>> newdataList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Thread.setDefaultUncaughtExceptionHandler(new CrashAppExceptionHandler(this));
        ThisApp.getInstance().adjastFontScale();
        TetDebugUtil.e(pseudo_tag, "!================= START " + pseudo_tag + "============");

        setContentView(R.layout.activity_on_route_main);

        Bundle bundle = getIntent().getExtras();
        String organisation = bundle.getString("org");
        String idRoute = bundle.getString("id");
        String routeMame = bundle.getString("route");

        listViewCarrentR = (ListView) findViewById(R.id.listViewCarrentRoute);

        listViewCorrection = (ListView) findViewById(R.id.listViewRC);

       orgName = (TextView) findViewById(R.id.tvOrgOnRout);
       routeNameV = (TextView) findViewById(R.id.tvRouteNameCR);
        linearLayout = (LinearLayout) findViewById(R.id.linerLayRC);
        butn_correct = (Button) findViewById(R.id.butto_correct);
        butSaveCuren = (Button) findViewById(R.id.butSaveCurentRoute);
        butAddPoint = (Button)  findViewById(R.id.but_addPoint);

        if (listViewCarrentR.getVisibility() == View.VISIBLE){
            butn_correct.setVisibility(View.VISIBLE);
            butSaveCuren.setVisibility(View.GONE);
            butAddPoint.setVisibility(View.GONE);
        }


        butn_correct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toCorrectinRoute();
            }
        });
        butSaveCuren.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (routeDbManipulation.replaceCurrentRouteInDb(getApplicationContext(),GlobalDatas.getOrgName(), newdataList)){
                    CreateTableForCurrentRoute.createFromArayAray(context, newdataList);
                    newListCurrentRoot();
                }


            }
        });

         if (!GlobalDatas.getOrgName().isEmpty()){
             orgName.setText(GlobalDatas.getOrgName());
         } else {
             orgName.setText(organisation);
         }
        routeNameV.setText(routeMame);
        newListCurrentRoot();
        butAddPoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddNewPointOwnPoint.class);
                startActivity(intent);

            }
        });

    }

    private void toCorrectinRoute() {

        if (listViewCarrentR.getVisibility() == View.VISIBLE) {
            listViewCarrentR.setVisibility(View.GONE);
            listViewCorrection.setVisibility(View.VISIBLE);
            butn_correct.setVisibility(View.GONE);
            butSaveCuren.setVisibility(View.VISIBLE);
            butAddPoint.setVisibility(View.VISIBLE);

            newdataList = allDatabaseController.executeQuery(this, GlobalDatas.db_name, "SELECT point_id, zone, point_owner FROM current_route");
            new ShowAllInArrayList(pseudo_tag, newdataList);

            if (newdataList.isEmpty()){
                //ToDo Add resolver if data/empty

                return;
            }
            adapterCorrection = null;
            adapterCorrection = new AdapterEditRoute(this, newdataList);
            listViewCorrection.setAdapter(adapterCorrection);
        }
    }

    private void newListCurrentRoot() {
        if (listViewCarrentR.getVisibility() == View.GONE) {
            listViewCarrentR.setVisibility(View.VISIBLE);
            listViewCorrection.setVisibility(View.GONE);
            butn_correct.setVisibility(View.VISIBLE);
            butSaveCuren.setVisibility(View.GONE);
            butAddPoint.setVisibility(View.GONE);
        }
        ArrayList<ArrayList<String>> dataList = allDatabaseController.executeQuery(this, GlobalDatas.db_name, "SELECT point_id, zone, point_owner FROM current_route");

        new ShowAllInArrayList(pseudo_tag, dataList);

        if (dataList.isEmpty()){
            //ToDo Add resolver if data/empty

            return;
        }

        adapterOfCurrent = null;
        if (!newdataList.isEmpty()){
            adapterOfCurrent = new AdapterCurrentRoute(this, newdataList);
            newdataList = new ArrayList<>();
        } else {
            adapterOfCurrent = new AdapterCurrentRoute(this, dataList);
        }
        listViewCarrentR.setAdapter(adapterOfCurrent);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), ChoiceRouteActivityAutonom.class));
        this.finish();
    }

}