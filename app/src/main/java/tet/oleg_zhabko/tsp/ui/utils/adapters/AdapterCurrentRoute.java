package tet.oleg_zhabko.tsp.ui.utils.adapters;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

import tet.oleg_zhabko.tsp.R;
import tet.oleg_zhabko.tsp.datas.GlobalDatas;
import tet.oleg_zhabko.tsp.ui.utils.appAndNaviMaps.workWithApkNaviOnDevice;
import tet.oleg_zhabko.tsp.ui.utils.edit_point_maps.ActivityPointInfo;
import tet.tetlibrarymodules.alldbcontroller.AllDatabaseController;
import tet.tetlibrarymodules.tetdebugutils.debug.debug_tools.TetDebugUtil;

public class AdapterCurrentRoute extends BaseAdapter {
    private static final String pseudo_tag = AdapterCurrentRoute.class.getSimpleName();
    private AllDatabaseController allDatabaseController = AllDatabaseController.getSingleControllerInstance();
    private final ArrayList<ArrayList<String>> dataList;
    //    private final ArrayList<ArrayList<String>> pointChecked = new ArrayList<>();  // Список для сохранения отмеченных элементов
    private final LayoutInflater inflater;
    private final Activity activity;
    private Context context;
    private ViewHolder holder;
    private int itemPosition;

    private static class ViewHolder {
        ImageButton pointInfoButton;
        TextView pointIdTextView;
        TextView point_fiel1_TextView;
        TextView point_fiel2_TextView;
        Button buttonGo;
    }

    public AdapterCurrentRoute(Activity activity, ArrayList<ArrayList<String>> dataList) {
        holder = null;
        // boolean[] checkedStates = null;
        this.dataList = dataList;
        this.activity = activity;
        context = activity.getApplicationContext();
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        itemPosition = position;


        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_current_route_goahead, parent, false);
            holder = new ViewHolder();

            holder.pointInfoButton = convertView.findViewById(R.id.point_imfo);
            holder.pointIdTextView = convertView.findViewById(R.id.tv_idRC);
            holder.point_fiel1_TextView = convertView.findViewById(R.id.point_currentZ);
            holder.point_fiel2_TextView = convertView.findViewById(R.id.point_currentOw);
            holder.buttonGo = (Button) convertView.findViewById(R.id.btnGoAhead);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        ArrayList<String> currentItem = dataList.get(position);


        holder.pointIdTextView.setText(currentItem.get(0));  // point_id
        holder.point_fiel1_TextView.setText(currentItem.get(1));  // sales_name
        holder.point_fiel2_TextView.setText(currentItem.get(2));// zone

        holder.pointInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pointId = currentItem.get(0);
                Intent intent = new Intent(context, ActivityPointInfo.class);
                intent.putExtra("point_id", pointId);
                intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

        holder.buttonGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pointId = currentItem.get(0);
                GlobalDatas.directPointID = pointId;
                Double latitude = Double.parseDouble(currentItem.get(3));
                Double longitude = Double.parseDouble(currentItem.get(4));

                workWithApkNaviOnDevice mapsManipulation = new workWithApkNaviOnDevice(activity);
                if (mapsManipulation.isAppInstalled(GlobalDatas.navigationAPP)) {
                    mapsManipulation.navigateToLocation(latitude, longitude, GlobalDatas.navigationAPP);
                } else {
                    TetDebugUtil.e(pseudo_tag, "Not installed ["+GlobalDatas.navigationAPP+"]");
                }

            }
        });

        return convertView;
    }
}
