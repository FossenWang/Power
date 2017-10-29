package fossen.power.training_program_library;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ExpandableListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import fossen.power.Exercise;
import fossen.power.R;
import fossen.power.Sets;

/**
 * Created by Administrator on 2017/10/2.
 */

public class ChooseExerciseAdapter extends BaseExpandableListAdapter implements CompoundButton.OnCheckedChangeListener {
    private ArrayList<ArrayList<Exercise>> sortedExercises;
    private ArrayList<String> checkedExercises;
    private Context mContext;

    public ChooseExerciseAdapter(ArrayList<ArrayList<Exercise>> sortedExercises,
                                 ArrayList<String> checkedExercises,
                                 Context mContext){
        this.sortedExercises = sortedExercises;
        this.checkedExercises = checkedExercises;
        this.mContext = mContext;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(buttonView.isChecked()){
            if(!checkedExercises.contains( buttonView.getText().toString())) {
                checkedExercises.add(buttonView.getText().toString());
            }
        }else {
            if(checkedExercises.contains( buttonView.getText().toString())) {
                checkedExercises.remove(buttonView.getText().toString());
            }
        }
    }

    @Override
    public int getGroupCount() {
        return sortedExercises.size();
    }
    @Override
    public int getChildrenCount(int groupPosition) {
        return sortedExercises.get(groupPosition).size();
    }
    @Override
    public String getGroup(int groupPosition) {
        return sortedExercises.get(groupPosition).get(0).getSort();
    }
    @Override
    public Exercise getChild(int groupPosition, int childPosition) {
        return sortedExercises.get(groupPosition).get(childPosition);
    }
    @Override
    public long getGroupId(int groupPosition) {
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
        GroupViewHolder groupHolder;
        if(convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(
                    R.layout.item_exerlib_group, parent, false);
            groupHolder = new GroupViewHolder();
            groupHolder.group_text = (TextView) convertView.findViewById(R.id.text_el_sort);
            convertView.setTag(groupHolder);
        }else {
            groupHolder = (GroupViewHolder) convertView.getTag();
        }
        groupHolder.group_text.setText(getGroup(groupPosition));
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildViewHolder childHolder;
        if(convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(
                    R.layout.item_exercise_check, parent, false);
            childHolder = new ChildViewHolder();
            childHolder.check_exercise = (CheckBox) convertView.findViewById(R.id.check_cei_exercise);
            convertView.setTag(childHolder);
        }else{
            childHolder = (ChildViewHolder) convertView.getTag();
        }
        Exercise exercise = sortedExercises.get(groupPosition).get(childPosition);
        childHolder.check_exercise.setText(exercise.getName());
        childHolder.check_exercise.setOnCheckedChangeListener(this);
        if(checkedExercises.contains(exercise.getName())){
            childHolder.check_exercise.setChecked(true);
        }else {
            childHolder.check_exercise.setChecked(false);
        }
        return convertView;
    }

    private static class GroupViewHolder{
        TextView group_text;
    }
    private static class ChildViewHolder{
        CheckBox check_exercise;
    }
}
