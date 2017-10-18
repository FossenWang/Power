package fossen.power.training_log;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import fossen.power.R;
import fossen.power.TPDBOpenHelper;
import fossen.power.TrainingProgram;

/**
 * Created by Administrator on 2017/10/17.
 */

public class TrainingLogAdapter extends BaseExpandableListAdapter {
    private Context mContext;
    private ExpandableListView expList;
    private TPDBOpenHelper tpdbOpenHelper;
    private ArrayList<ArrayList<TrainingProgram>> trainingLog = new ArrayList<>();
    private ArrayList<String> trainingCalendar;

    public TrainingLogAdapter(Context mContext, ExpandableListView expList, TPDBOpenHelper tpdbOpenHelper) {
        this.mContext = mContext;
        this.expList = expList;
        this.tpdbOpenHelper = tpdbOpenHelper;
        trainingCalendar = tpdbOpenHelper.inputTrainingCalendar();
        inputNextRecordDay();
    }

    //当trainingLog的最后一个成员调用getView()时，检查训练日是否全部导出
    //未全部导出则从数据库中读取下一个训练日的记录，更新trainingLog
    //若所有训练日都读取完毕，则向二维列表末尾添加一个空列表，表示没有更多记录
    //以此实现动态加载训练日，没有浏览到的训练日就不用加载
    private void inputNextRecordDay(){
        if(getGroupCount()<trainingCalendar.size()){
            trainingLog.add(tpdbOpenHelper.inputTrainingLog(trainingCalendar.get(getGroupCount())));
        }else {
            trainingLog.add(new ArrayList<TrainingProgram>());
        }
        notifyDataSetChanged();
    }

    @Override
    public int getGroupCount() {
        return trainingLog.size();
    }
    @Override
    public int getChildrenCount(int groupPosition) {
        return trainingLog.get(groupPosition).size();
    }
    @Override
    public String getGroup(int groupPosition) {
        return trainingLog.get(groupPosition).get(0).getDate();
    }
    @Override
    public TrainingProgram getChild(int groupPosition, int childPosition) {
        return trainingLog.get(groupPosition).get(childPosition);
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
                    R.layout.item_tl_group, parent, false);
            groupHolder = new GroupViewHolder();
            groupHolder.text_group = (TextView) convertView.findViewById(R.id.text_tl_date);
            convertView.setTag(groupHolder);
        }else {
            groupHolder = (GroupViewHolder) convertView.getTag();
        }
        if(trainingLog.get(groupPosition).isEmpty()){
            groupHolder.text_group.setText("没有更多记录了");
        }else {
            String date = getGroup(groupPosition);
            String str = Integer.parseInt(date.substring(4,6)) + "月" + Integer.parseInt(date.substring(6,8)) + "日";
            groupHolder.text_group.setText(str);
            if(groupPosition==trainingLog.size()-1){
                inputNextRecordDay();
            }//判断当前记录是否是最后一条且非空，如果是，则意味着记录未导入完全，故再导入一条新纪录
        }
        expList.expandGroup(groupPosition);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildViewHolder childHolder;
        if(convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(
                    R.layout.item_tl_item, parent, false);
            childHolder = new ChildViewHolder();
            childHolder.text_title = (TextView) convertView.findViewById(R.id.text_tl_title);
            childHolder.text_count = (TextView) convertView.findViewById(R.id.text_tl_count);
            convertView.setTag(childHolder);
        }else{
            childHolder = (ChildViewHolder) convertView.getTag();
        }

        final TrainingProgram record = trainingLog.get(groupPosition).get(childPosition);
        final String id = record.getId();
        final String date = record.getDate();
        final int gPos = groupPosition;
        final int cPos = childPosition;
        if(record.getTrainingDay(0).numberOfSets()==0){
            tpdbOpenHelper.inputTrainingRecord(record);
        }//防止重复导入
        childHolder.text_title.setText(record.getName()+"  "+record.getTrainingDay(0).getTitle());
        childHolder.text_count.setText(record.getTrainingDay(0).numberOfExercise() + "个动作  "
                + record.getTrainingDay(0).numberOfSingleSets() + "组");

        //单击查看训练记录详情
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, TrainingRecordActivity.class);
                intent.putExtra("record", record);
                mContext.startActivity(intent);
            }
        });
        //长按删除记录
        convertView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                PopupMenu popup = new PopupMenu(mContext, v);
                Menu menu = popup.getMenu();
                menu.add("删除");
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        trainingLog.get(gPos).remove(cPos);
                        if (trainingLog.get(gPos).isEmpty()){
                            trainingLog.remove(gPos);
                            trainingCalendar.remove(gPos);
                        }
                        tpdbOpenHelper.deleteTrainingRecord(date, id);
                        notifyDataSetChanged();
                        return true;
                    }
                });
                popup.show();
                return true;
            }
        });
        return convertView;
    }

    private static class GroupViewHolder{
        TextView text_group;
    }
    private static class ChildViewHolder{
        TextView text_title;
        TextView text_count;
    }
}
