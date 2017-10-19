package fossen.power.exercise_library;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import fossen.power.Exercise;
import fossen.power.R;

/**
 * Created by Administrator on 2017/9/14.
 */

public class ExerciseLibraryAdapter extends BaseExpandableListAdapter {
    private ArrayList<HashMap<String,String>> sort;
    private ArrayList<ArrayList<Exercise>> exerciseList;
    private Context mContext;

    public ExerciseLibraryAdapter(ArrayList<HashMap<String,String>> sort,
                                  ArrayList<ArrayList<Exercise>> exerciseList,
                                  Context mContext){
        this.sort = sort;
        this.exerciseList = exerciseList;
        this.mContext = mContext;
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
        return sort.get(groupPosition).get("sort_chinese");
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
        groupHolder.group_text.setText(sort.get(groupPosition).get("sort_chinese"));
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
            childHolder.text_muscle = (TextView) convertView.findViewById(R.id.text_el_muscle);
            convertView.setTag(childHolder);
        }else{
            childHolder = (ChildViewHolder) convertView.getTag();
        }
        childHolder.text_exercise.setText(exerciseList.get(groupPosition).get(childPosition).getName());
        childHolder.text_muscle.setText(exerciseList.get(groupPosition).get(childPosition).getMuscle());
        return convertView;
    }

    private static class GroupViewHolder{
        TextView group_text;
    }
    private static class ChildViewHolder{
        TextView text_exercise;
        TextView text_muscle;
    }
}
