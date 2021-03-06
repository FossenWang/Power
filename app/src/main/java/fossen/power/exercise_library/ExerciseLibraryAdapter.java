package fossen.power.exercise_library;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import fossen.power.Exercise;
import fossen.power.R;

/**
 * Created by Administrator on 2017/9/14.
 */

public class ExerciseLibraryAdapter extends BaseExpandableListAdapter {
    private ArrayList<ArrayList<Exercise>> sortedExercises;
    private Context mContext;

    public ExerciseLibraryAdapter(ArrayList<ArrayList<Exercise>> sortedExercises, Context mContext){
        this.sortedExercises = sortedExercises;
        this.mContext = mContext;
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
    //设置子列表是否可选中
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    //取得用于显示给定分组的视图. 这个方法仅返回分组的视图对象
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

    //取得显示给定分组给定子位置的数据用的视图
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildViewHolder childHolder;
        if(convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(
                    R.layout.item_exerlib_item, parent, false);
            childHolder = new ChildViewHolder();
            childHolder.text_exercise = (TextView) convertView.findViewById(R.id.text_el_exercise);
            convertView.setTag(childHolder);
        }else{
            childHolder = (ChildViewHolder) convertView.getTag();
        }
        childHolder.text_exercise.setText(getChild(groupPosition,childPosition).getName());
        return convertView;
    }

    private static class GroupViewHolder{
        TextView group_text;
    }
    private static class ChildViewHolder{
        TextView text_exercise;
    }
}
