package tet.oleg_zhabko.tsp.ui.utils.adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;

import tet.oleg_zhabko.tsp.R;
import tet.oleg_zhabko.tsp.datas.GlobalDatas;
import tet.oleg_zhabko.tsp.ui.utils.AllertDialogCallbackInterface;
import tet.oleg_zhabko.tsp.ui.utils.AllertOneAndTwoAndThreeButton;
import tet.oleg_zhabko.tsp.ui.utils.CheckIsItemDubbed;
import tet.tetlibrarymodules.tetdebugutils.debug.debug_tools.ShowAllInArrayList;
import tet.tetlibrarymodules.tetdebugutils.debug.debug_tools.TetDebugUtil;

public class AdapterCustomForPointsList extends BaseAdapter implements AllertDialogCallbackInterface<String> {

    private static final String pseudo_tag = AdapterCustomForPointsList.class.getSimpleName();
    private final ArrayList<ArrayList<String>> dataList;
//    private final ArrayList<ArrayList<String>> pointChecked = new ArrayList<>();  // Список для сохранения отмеченных элементов
    boolean[] checkedStates;  // Массив состояний чекбоксов
    private final LayoutInflater inflater;
    private final Activity activity;
    private Context context;
    private ViewHolder holder;
    private int itemPosition;

    private static class ViewHolder {
        TextView pointIdTextView;
        TextView point_fiel1_TextView;
        TextView point_fiel2_TextView;
        CheckBox checkBox;
    }


    public AdapterCustomForPointsList(Activity activity, ArrayList<ArrayList<String>> dataList) {
        holder = null;
       // boolean[] checkedStates = null;
        this.dataList = dataList;
        checkedStates = new boolean[dataList.size()];  // Инициализация массива для состояний чекбоксов
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
            convertView = inflater.inflate(R.layout.item_point_list_layout, parent, false);
            holder = new ViewHolder();

            holder.pointIdTextView = convertView.findViewById(R.id.point_id);
            holder.point_fiel1_TextView = convertView.findViewById(R.id.point_fiel1);
            holder.point_fiel2_TextView = convertView.findViewById(R.id.point_fiel2);
            holder.checkBox = convertView.findViewById(R.id.checkbox);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        ArrayList<String> currentItem = dataList.get(position);


        holder.pointIdTextView.setText(currentItem.get(0));  // point_id
        holder.point_fiel1_TextView.setText(currentItem.get(1));  // sales_name
        holder.point_fiel2_TextView.setText(currentItem.get(2));  // zone
        // Устанавливаем состояние чекбокса
        if (CheckIsItemDubbed.checkIsDubbed(GlobalDatas.pointChecked,currentItem)){
            checkedStates[position] = true;
           // holder.checkBox.setChecked(true);
        } else {
            checkedStates[position] = false;
//              holder.checkBox.setChecked(checkedStates[position]);
//            holder.checkBox.setChecked(false);
        }
        holder.checkBox.setChecked(checkedStates[position]);

        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                checkedStates[position] = isChecked;  // Сохраняем состояние чекбокса

                TetDebugUtil.e(pseudo_tag, "onCheckedChanged position=[" + position+ "]  currentItem=["+ currentItem+"]");
                // Если чекбокс отмечен - добавляем элемент в pointChecked, иначе удаляем
                if (isChecked) {
                    if (CheckIsItemDubbed.checkIsDubbed(GlobalDatas.pointChecked, currentItem)) {
                        String title = context.getResources().getString(R.string.worning).toString();
                        String message = context.getResources().getString(R.string.allert_double_point);
                        String initialiserName = activity.getClass().getSimpleName();
                        AlertDialog allert = new AllertOneAndTwoAndThreeButton().createTwoButtonsAlertDialog(activity, title, message, null, 1, AdapterCustomForPointsList.this);
                        allert.show();
                    } else {
                        TetDebugUtil.e(pseudo_tag, "pointChecked.add(currentItem)=[" + currentItem + "]");
                    //   holder.checkBox.setChecked(checkedStates[position]);
                        GlobalDatas.pointChecked.add(currentItem);
                    }
                } else {
                    TetDebugUtil.e(pseudo_tag, "pointChecked.remove(currentItem)=[" + currentItem + "]");
                   // holder.checkBox.setChecked(checkedStates[position]);
                    GlobalDatas.pointChecked.remove(currentItem);
                }
              //  new ShowAllInArrayList(pseudo_tag, GlobalDatas.pointChecked);

            }
        });

        return convertView;
    }


    @Override
    public void allertDialogCallback(boolean ret) {
        TetDebugUtil.e(pseudo_tag,"From CALBACK recieved {"+ret+"}");
        if (ret == false){
            //  holder.checkBox.setChecked(false);
           // GlobalDatas.pointChecked.add(currentItem);
        } else {
            ArrayList<String> item = dataList.get(itemPosition);
            GlobalDatas.pointChecked.add(item);
        }

    }


    public ArrayList<ArrayList<String>> getCheckedItems() {
        return GlobalDatas.pointChecked;
    }
}
