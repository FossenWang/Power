package fossen.power.training_today;

import android.content.Context;
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

import fossen.power.ComponentCreator;
import fossen.power.R;
import fossen.power.Sets;
import fossen.power.DBOpenHelper;
import fossen.power.TrainingDay;
import fossen.power.TrainingProgram;

/**
 * Created by Administrator on 2017/10/6.
 */

public class TrainingTodayAdapter extends BaseAdapter {
    private DBOpenHelper DBOpenHelper;
    private TrainingProgram trainingProgram;
    private TrainingDay trainingDay;
    private Context mContext;
    private ViewGroup actionLayout;
    private View actionView;

    private int writingItem = -1;//-1表示无编辑中的组集，>-1时表示启用修改模式的组集序号,
    private int recordingSets = 0;//表示第一个未记录的组集

    public TrainingTodayAdapter(DBOpenHelper DBOpenHelper,
                                TrainingProgram trainingProgram,
                                TrainingDay trainingDay ,
                                Context mContext,
                                ViewGroup actionLayout,
                                View actionView
                                ) {
        this.DBOpenHelper = DBOpenHelper;
        this.trainingProgram = trainingProgram;
        this.trainingDay = trainingDay;
        this.mContext = mContext;
        this.actionLayout = actionLayout;
        this.actionView = actionView;
        recordingSets = setRecordingSets();
    }

    public void setWritingItem(int position){
        writingItem = position;
        recordingSets = setRecordingSets();//每次切换在编辑的组集时都要重新计算未完成组
        notifyDataSetChanged();
    }
    public int getWritingItem(){
        return writingItem;
    }
    public int getRecordingSets(){
        return recordingSets;
    }
    public int setRecordingSets() {
        for(int i = 0; i < trainingDay.numberOfSets(); i++){
            for(int j = 0; j < trainingDay.getSets(i).numberOfSingleSets(); j++){
                if(trainingDay.getSets(i).getSet(j).getReps() == 0){
                    return i;
                }
            }
        }
        return -1;//第一个有空SingleSet的Sets记为recordingSets，没有则是-1，表示全部完成
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
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        final int pos = position;
        final Sets sets = trainingDay.getSets(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(
                    R.layout.item_tt_display, parent, false);
            holder = new ViewHolder();
            holder.layout_sets = convertView.findViewById(R.id.layout_ttd_sets);
            holder.layout_record = (ViewGroup) convertView.findViewById(R.id.layout_ttd_record);
            holder.arrow = (ImageView) convertView.findViewById(R.id.arrow_ttd_exercise);
            holder.text_exercise = (TextView) convertView.findViewById(R.id.text_ttd_exercise);
            holder.text_sets = (TextView) convertView.findViewById(R.id.text_ttd_sets);
            holder.text_rest = (TextView) convertView.findViewById(R.id.text_ttd_rest);
            holder.text_record = (TextView) convertView.findViewById(R.id.text_ttd_load_reps);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.text_exercise.setText(sets.getExercise(0).getName());
        holder.text_sets.setText(sets.getRepmax() + " RM × " + sets.numberOfSingleSets() + "组");
        holder.text_rest.setText("休息: " + sets.getRestString());

        //判断展示记录或者修改记录
        holder.layout_record.removeAllViews();
        if(position == writingItem){
            holder.text_record.setText("");

            //创建可编辑的训练记录
            for(int i = 0; i < sets.numberOfSingleSets(); i++){
                holder.layout_record.addView(ComponentCreator.createLoadRepsPickers(mContext, i, sets));
            }

            //屏蔽点击事件
            holder.layout_sets.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {}
            });
        }else {
            holder.text_record.setText(sets.getAllSetsToFormat("kg"));

            //单击进入组集修改模式
            holder.layout_sets.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setWritingItem(pos);
                    actionLayout.removeAllViews();
                    actionLayout.addView(actionView);
                }
            });
        }


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
                            DBOpenHelper.updateExercise(trainingProgram.getId(),
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

    private static class ViewHolder {
        View layout_sets;
        ViewGroup layout_record;
        ImageView arrow;
        TextView text_exercise;
        TextView text_sets;
        TextView text_rest;
        TextView text_record;
    }
}
