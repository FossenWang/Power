package fossen.power.training_today;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(
                    R.layout.item_tpc_item, parent, false);
            holder = new ViewHolder();
            holder.layout_sets = convertView.findViewById(R.id.layout_tpc_sets);
            holder.arrow = (ImageView) convertView.findViewById(R.id.arrow_tpc_exercise);
            holder.text_exercise = (TextView) convertView.findViewById(R.id.text_tpc_exercise);
            holder.text_sets = (TextView) convertView.findViewById(R.id.text_tpc_sets);
            holder.text_rest = (TextView) convertView.findViewById(R.id.text_tpc_rest);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        final int pos = position;
        final Sets sets = trainingDay.getSets(position);
        holder.text_exercise.setText(sets.getExercise(0).getName());
        holder.text_sets.setText(sets.getRepmax() + " RM × " + sets.numberOfSingleSets());
        holder.text_rest.setText("休息: " + sets.getRestSting());

        //单击进入动作形式Activity
        holder.layout_sets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //进入动作形式Activity
                Intent intent = new Intent(mContext,ExerciseFormActivity.class);
                intent.putExtra("name",sets.getExercise(0).getName());
                mContext.startActivity(intent);
            }
        });

        //单击下箭头弹出菜单，选择替换动作
        holder.arrow.setOnClickListener(new View.OnClickListener() {
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
        return convertView;
    }

    private static class ViewHolder{
        View layout_sets;
        ImageView arrow;
        TextView text_exercise;
        TextView text_sets;
        TextView text_rest;
    }
}