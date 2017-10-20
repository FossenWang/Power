package fossen.power.training_program_library;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
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
 * Created by Administrator on 2017/9/18.
 */

public class TrainingProgramContentAdapter extends BaseExpandableListAdapter {
    private TrainingProgram trainingProgram;
    private TPDBOpenHelper tpdbOpenHelper;
    private Context mContext;

    public TrainingProgramContentAdapter(TrainingProgram trainingProgram, TPDBOpenHelper tpdbOpenHelper, Context mContext){
        this.trainingProgram = trainingProgram;
        this.tpdbOpenHelper = tpdbOpenHelper;
        this.mContext = mContext;
    }

    @Override
    public int getGroupCount() {
        return trainingProgram.circleDays();
    }

    @Override
    public int getChildrenCount(int groupPosition){
        if(trainingProgram.getTrainingDay(groupPosition).isRestDay()){
            return 0;
        }else {
            return trainingProgram.getTrainingDay(groupPosition).numberOfSets();
        }
    }

    @Override
    public TrainingDay getGroup(int groupPosition){
        return trainingProgram.getTrainingDay(groupPosition);
    }

    @Override
    public Sets getChild(int groupPosition, int childPosition) {
        return trainingProgram.getTrainingDay(groupPosition).getSets(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition){
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupViewHolder groupHolder ;
        final TrainingDay trainingDay = trainingProgram.getTrainingDay(groupPosition);
        if(convertView == null){//判断是否加载视图
            convertView =
                    LayoutInflater.from(mContext)
                            .inflate(R.layout.item_tpc_group, parent, false);
            groupHolder = new GroupViewHolder();
            groupHolder.textDay = (TextView) convertView.findViewById(R.id.text_tpc_day);
            groupHolder.textCount = (TextView) convertView.findViewById(R.id.text_tpc_count);
            convertView.setTag(groupHolder);
        }else {
            groupHolder = (GroupViewHolder) convertView.getTag();
        }

        if(trainingDay.isRestDay()){
            groupHolder.textDay.setText((groupPosition+1) + "  休息");
            groupHolder.textCount.setText("");
        }else{
            groupHolder.textDay.setText((groupPosition+1) + "  训练  " + trainingDay.getTitle());
            groupHolder.textCount.setText(trainingDay.numberOfExercise() + "个动作  "
                    + trainingDay.numberOfSingleSets() + "组");
        }
        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        ChildViewHolder childHolder;
        if(convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(
                    R.layout.item_tpc_item, parent, false);
            childHolder = new ChildViewHolder();
            childHolder.layout_sets = convertView.findViewById(R.id.layout_tpc_sets);
            childHolder.arrow = (ImageView) convertView.findViewById(R.id.arrow_tpc_exercise);
            childHolder.text_exercise = (TextView) convertView.findViewById(R.id.text_tpc_exercise);
            childHolder.text_sets = (TextView) convertView.findViewById(R.id.text_tpc_sets);
            childHolder.text_rest = (TextView) convertView.findViewById(R.id.text_tpc_rest);
            convertView.setTag(childHolder);
        }else{
            childHolder = (ChildViewHolder) convertView.getTag();
        }
        final Sets sets = trainingProgram.getTrainingDay(groupPosition).getSets(childPosition);
        childHolder.text_exercise.setText(sets.getExercise(0).getName());
        childHolder.text_sets.setText(sets.getRepmax() + " RM × " + sets.numberOfSingleSets() + "组");
        childHolder.text_rest.setText("休息: " + sets.getRestString());

        //单击进入动作形式Activity
        childHolder.layout_sets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //进入动作形式Activity
                Intent intent = new Intent(mContext,ExerciseFormActivity.class);
                intent.putExtra("name",sets.getExercise(0).getName());
                mContext.startActivity(intent);
            }
        });

        //单击下箭头弹出菜单，选择替换动作
        childHolder.arrow.setOnClickListener(new View.OnClickListener() {
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
                                    groupPosition + 1, childPosition + 1, sets);
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

    private static class GroupViewHolder{
        TextView textDay;
        TextView textCount;
    }
    private static class ChildViewHolder {
        View layout_sets;
        ImageView arrow;
        TextView text_exercise;
        TextView text_sets;
        TextView text_rest;
    }
}
