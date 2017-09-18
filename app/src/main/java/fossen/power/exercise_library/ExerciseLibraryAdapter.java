package fossen.power.exercise_library;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.security.acl.Group;
import java.util.ArrayList;

import fossen.power.Exercise;
import fossen.power.R;

/**
 * Created by Administrator on 2017/9/14.
 */

public class ExerciseLibraryAdapter extends BaseExpandableListAdapter {
    private ArrayList<String> sort;
    private ArrayList<ArrayList<Exercise>> exerList;
    private Context mContext;

    public ExerciseLibraryAdapter(ArrayList<String> sort,
                                  ArrayList<ArrayList<Exercise>> exerList,
                                  Context mContext){
        this.sort = sort;
        this.exerList = exerList;
        this.mContext = mContext;
    }

    @Override
    public int getGroupCount() {
        return sort.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return exerList.get(groupPosition).size();
    }

    @Override
    public String getGroup(int groupPosition) {
        return sort.get(groupPosition);
    }

    @Override
    public Exercise getChild(int groupPosition, int childPosition) {
        return exerList.get(groupPosition).get(childPosition);
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

        if(convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(
                    R.layout.item_exerlib_group, parent, false);
        }
        TextView group_text = (TextView) convertView.findViewById(R.id.text_el_sort);
        group_text.setText(sort.get(groupPosition));
        return convertView;
    }

    //取得显示给定分组给定子位置的数据用的视图
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        if(convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(
                    R.layout.item_exerlib_item, parent, false);
        }
        TextView text_exercise = (TextView) convertView.findViewById(R.id.text_el_exercise);
        TextView text_muscle = (TextView) convertView.findViewById(R.id.text_el_muscle);
        text_exercise.setText(exerList.get(groupPosition).get(childPosition).getName());
        text_muscle.setText(exerList.get(groupPosition).get(childPosition).getMuscle());
        return convertView;
    }
}
