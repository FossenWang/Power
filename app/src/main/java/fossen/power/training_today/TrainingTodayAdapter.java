package fossen.power.training_today;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import fossen.power.R;
import fossen.power.Sets;
import fossen.power.TPDBOpenHelper;
import fossen.power.TrainingDay;
import fossen.power.TrainingProgram;
import fossen.power.exercise_library.ExerciseFormActivity;

/**
 * Created by Administrator on 2017/10/6.
 */

public class TrainingTodayAdapter extends BaseAdapter {
    private TPDBOpenHelper tpdbOpenHelper;
    private TrainingProgram trainingProgram;
    private TrainingDay trainingDay;
    private Context mContext;
    private int writingItem = -1;
    private static final int TYPE_ITEM = 0;
    private static final int TYPE_WRITE = 1;

    public TrainingTodayAdapter(TPDBOpenHelper tpdbOpenHelper, TrainingProgram trainingProgram, TrainingDay trainingDay , Context mContext) {
        this.tpdbOpenHelper = tpdbOpenHelper;
        this.trainingProgram = trainingProgram;
        this.trainingDay = trainingDay;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return trainingDay.numberOfSets();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position){
        if(position == writingItem){
            return TYPE_WRITE;
        }else {
            return TYPE_ITEM;
        }
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int type = getItemViewType(position);
        ViewHolder0 holder0 = null;
        ViewHolder1 holder1 = null;
        final int pos = position;
        final Sets sets = trainingDay.getSets(position);

        switch (type) {
            case TYPE_ITEM://加载显示模式的训练组集
                if (convertView == null) {
                    convertView = LayoutInflater.from(mContext).inflate(
                            R.layout.item_tpc_item, parent, false);
                    holder0 = new ViewHolder0();
                    holder0.layout_sets = convertView.findViewById(R.id.layout_tpc_sets);
                    holder0.arrow = (ImageView) convertView.findViewById(R.id.arrow_tpc_exercise);
                    holder0.text_exercise = (TextView) convertView.findViewById(R.id.text_tpc_exercise);
                    holder0.text_sets = (TextView) convertView.findViewById(R.id.text_tpc_sets);
                    holder0.text_rest = (TextView) convertView.findViewById(R.id.text_tpc_rest);
                    convertView.setTag(holder0);
                } else {
                    holder0 = (ViewHolder0) convertView.getTag();
                }

                holder0.text_exercise.setText(sets.getExercise(0).getName());
                holder0.text_sets.setText(sets.getRepmax() + " RM × " + sets.numberOfSingleSets());
                holder0.text_rest.setText("休息: " + sets.getRestSting());

                //单击进入动作形式Activity
                holder0.layout_sets.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //进入动作形式Activity
                        Intent intent = new Intent(mContext,ExerciseFormActivity.class);
                        intent.putExtra("name",sets.getExercise(0).getName());
                        mContext.startActivity(intent);
                    }
                });

                //单击下箭头弹出菜单，选择替换动作
                holder0.arrow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PopupMenu popup = new PopupMenu(mContext, v);
                        Menu menu = popup.getMenu();
                        menu.add("请选择替换的动作：");
                        if(sets.getExerciseList().size()>1){
                            for(int i = 1; i<sets.getExerciseList().size(); i++){
                                menu.add(Menu.NONE,Menu.NONE,i,sets.getExercise(i).getName());
                            }
                        }else {
                            menu.add("无");
                        }
                        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem item) {
                                int i = item.getOrder();
                                if(i>0) {
                                    sets.exchangeExercise(0, i);
                                    tpdbOpenHelper.updateExercise(trainingProgram.getId(),
                                            trainingProgram.countTodayInCircle(), pos + 1, sets);
                                    notifyDataSetChanged();
                                    Toast.makeText(mContext, "你选择了："+item.getTitle(), Toast.LENGTH_SHORT).show();
                                }
                                return true;
                            }
                        });
                        popup.show();
                    }
                });
                break;

            case TYPE_WRITE://加载可编辑模式的训练组集
                if (convertView == null) {
                    convertView = LayoutInflater.from(mContext).inflate(
                            R.layout.item_tt_write, parent, false);
                    holder1 = new ViewHolder1();
                    holder1.layout_volume = (ViewGroup) convertView.findViewById(R.id.layout_ttw_volume);
                    holder1.text_exercise = (TextView) convertView.findViewById(R.id.text_ttw_exercise);
                    holder1.text_sets = (TextView) convertView.findViewById(R.id.text_ttw_sets);
                    holder1.text_rest = (TextView) convertView.findViewById(R.id.text_ttw_rest);
                    holder1.delete = (Button) convertView.findViewById(R.id.button_ttw_delete);
                    holder1.add = (Button) convertView.findViewById(R.id.button_ttw_add);
                    convertView.setTag(holder1);
                } else {
                    holder1 = (ViewHolder1) convertView.getTag();
                }

                //加载组集数据
                holder1.text_exercise.setText(sets.getExercise(0).getName());
                holder1.text_sets.setText(sets.getRepmax() + " RM × " + sets.numberOfSingleSets());
                holder1.text_rest.setText("休息: " + sets.getRestSting());

                break;
        }
        return convertView;
    }

    public void setWritingItem(int position){
        writingItem = position;
    }
    public int getWritingItem(){
        return writingItem;
    }

    private static class ViewHolder0 {
        View layout_sets;
        ImageView arrow;
        TextView text_exercise;
        TextView text_sets;
        TextView text_rest;
    }
    private static class ViewHolder1{
        ViewGroup layout_volume;
        TextView text_exercise;
        TextView text_sets;
        TextView text_rest;
        Button delete;
        Button add;
    }
}
