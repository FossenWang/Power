package fossen.power.training_program_library;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

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
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupViewHolder groupHolder ;
        if(convertView == null){
            convertView =
                    LayoutInflater.from(mContext).inflate(
                            R.layout.item_tpc_group,parent,false);
            groupHolder = new GroupViewHolder();
            groupHolder.textDay =(TextView) convertView.findViewById(R.id.text_tpc_day);
            groupHolder.textCount =(TextView) convertView.findViewById(R.id.text_tpc_count);
            convertView.setTag(groupHolder);
        }else{
            groupHolder = (GroupViewHolder) convertView.getTag();
        }
        TrainingDay trainingDay = trainingProgram.getTrainingDay(groupPosition);
        if(trainingDay.isRestDay()){
            groupHolder.textDay.setText((groupPosition+1) + "  休息");
            groupHolder.textCount.setText("");
        }else{
            groupHolder.textDay.setText((groupPosition+1) + "  训练日: " + trainingDay.getTitle());
            groupHolder.textCount.setText(trainingDay.numberOfExercise() + "个动作  "
                    + trainingDay.numberOfSingleSets() + "组");
        }
        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        final ChildViewHolder childHolder;
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
        childHolder.text_sets.setText(sets.getRepmax() + " RM × " + sets.numberOfSingleSets());
        childHolder.text_rest.setText(sets.getRestSting());

        childHolder.layout_sets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //进入动作形式Activity
                Intent intent = new Intent(mContext,ExerciseFormActivity.class);
                intent.putExtra("name",sets.getExercise(0).getName());
                mContext.startActivity(intent);
            }
        });

        childHolder.arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int[] first = {0};
                String[] names = new String[sets.getExerciseList().size()];
                for(int i = 0; i<sets.getExerciseList().size(); i++){
                    names[i] = sets.getExercise(i).getName();
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("请选择替换的动作：")
                        .setSingleChoiceItems(names, 0, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                 first[0] = which;
                            }})
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                sets.exchangeExercise(0,first[0]);
                                tpdbOpenHelper.updateExercise(trainingProgram.getId(),
                                        groupPosition + 1, childPosition + 1, sets);
                                notifyDataSetChanged();
                            }})
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                builder.create().show();
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
