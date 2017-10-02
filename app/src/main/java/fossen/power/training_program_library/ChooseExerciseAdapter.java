package fossen.power.training_program_library;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import fossen.power.Exercise;
import fossen.power.R;

/**
 * Created by Administrator on 2017/10/2.
 */

public class ChooseExerciseAdapter extends BaseExpandableListAdapter implements CompoundButton.OnCheckedChangeListener {
    private ArrayList<String> sort;
    private ArrayList<ArrayList<Exercise>> exerciseList;
    private ArrayList<String> checkedExercises;
    private Context mContext;

    public ChooseExerciseAdapter(ArrayList<String> sort,
                                 ArrayList<ArrayList<Exercise>> exerciseList,
                                 ArrayList<String> checkedExercises,
                                 Context mContext){
        this.sort = sort;
        this.exerciseList = exerciseList;
        this.checkedExercises = checkedExercises;
        this.mContext = mContext;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        TextView text = (TextView) buttonView.getTag();
        if(buttonView.isChecked()){
            checkedExercises.add((String) buttonView.getText());
            text.setText(Integer.toString(checkedExercises.size()));
            Toast.makeText(mContext, checkedExercises.get(0) + checkedExercises.get(1), Toast.LENGTH_SHORT).show();
        }else {
            checkedExercises.remove(Integer.parseInt(text.getText().toString())-1);
            text.setText("");
        }
    }

    @Override
    public int getGroupCount() {
        return sort.size();
    }
    @Override
    public int getChildrenCount(int groupPosition) {
        return exerciseList.get(groupPosition).size();
    }
    @Override
    public String getGroup(int groupPosition) {
        return sort.get(groupPosition);
    }
    @Override
    public Exercise getChild(int groupPosition, int childPosition) {
        return exerciseList.get(groupPosition).get(childPosition);
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
        groupHolder.group_text.setText(sort.get(groupPosition));
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
            childHolder.text_order = (TextView) convertView.findViewById(R.id.text_cei_order);
            childHolder.text_muscle = (TextView) convertView.findViewById(R.id.text_cei_muscle);
            convertView.setTag(childHolder);
        }else{
            childHolder = (ChildViewHolder) convertView.getTag();
        }
        Exercise exercise = exerciseList.get(groupPosition).get(childPosition);
        childHolder.text_muscle.setText(exercise.getMuscle());
        childHolder.check_exercise.setText(exercise.getName());
        //childHolder.check_exercise.setTag(exercise);
        childHolder.check_exercise.setTag(childHolder.text_order);
        childHolder.check_exercise.setOnCheckedChangeListener(this);
        if(checkedExercises.contains(exercise.getName()))
            childHolder.check_exercise.setChecked(true);
        return convertView;
    }

    private static class GroupViewHolder{
        TextView group_text;
    }
    private static class ChildViewHolder{
        CheckBox check_exercise;
        TextView text_order;
        TextView text_muscle;
    }
}
