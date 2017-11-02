package fossen.power.training_today;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import fossen.power.R;
import fossen.power.Sets;
import fossen.power.DBOpenHelper;
import fossen.power.SingleSet;
import fossen.power.TrainingDay;
import fossen.power.TrainingProgram;

/**
 * Created by Administrator on 2017/10/6.
 */

public class TrainingTodayAdapter extends BaseAdapter {
    private DBOpenHelper DBOpenHelper;
    private TrainingProgram trainingProgram;
    private TrainingDay trainingDay;
    private int dayIndex;
    private Context mContext;
    private ViewGroup actionLayout;
    private View actionView;

    private int writingItem = -1;//-1表示无编辑中的组集，>-1时表示启用修改模式的组集序号,

    public TrainingTodayAdapter(DBOpenHelper DBOpenHelper,
                                TrainingProgram trainingProgram,
                                TrainingDay trainingDay ,
                                int day,
                                Context mContext,
                                ViewGroup actionLayout,
                                View actionView
                                ) {
        this.DBOpenHelper = DBOpenHelper;
        this.trainingProgram = trainingProgram;
        this.trainingDay = trainingDay;
        dayIndex = day-1;
        this.mContext = mContext;
        this.actionLayout = actionLayout;
        this.actionView = actionView;
    }

    public void setWritingItem(int position){
        writingItem = position;
        notifyDataSetChanged();
    }
    public int getWritingItem(){
        return writingItem;
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
        Sets lastTrainingSets = trainingProgram.getTrainingDay(dayIndex).getSets(pos);

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
            holder.text_record.setText("上次训练\n"+lastTrainingSets.getAllSetsToFormat("kg"));
            //创建可编辑的训练记录
            for(int i = 0; i < sets.numberOfSingleSets(); i++){
                holder.layout_record.addView(createLoadRepsPickers(i, sets, lastTrainingSets));
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
                    if (writingItem == -1) {
                        actionLayout.removeAllViews();
                        actionLayout.addView(actionView);
                    }
                    setWritingItem(pos);
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
                                    dayIndex+1, pos + 1, sets);
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

    //创建一行带有负重，次数选择器的视图
    private View createLoadRepsPickers(int index, Sets sets, Sets lastTrainingSets){
        View loadRepsView = LayoutInflater.from(mContext).inflate(R.layout.item_ttw_load_reps, null);
        TextView textOrder = (TextView) loadRepsView.findViewById(R.id.text_ttw_order);
        //TextView textUnit = (TextView) loadRepsView.findViewById(R.id.text_ttw_unit);
        NumberPicker loadPicker = (NumberPicker) loadRepsView.findViewById(R.id.picker_ttw_load);
        NumberPicker repsPicker = (NumberPicker) loadRepsView.findViewById(R.id.picker_ttw_reps);
        final CheckBox checkBox = (CheckBox) loadRepsView.findViewById(R.id.check_ttw);

        final SingleSet set = sets.getSet(index);
        SingleSet lastSet = lastTrainingSets.getSet(index);
        int loadValue, reps;
        //已有记录则显示记录，没有则显示上个周期内训练的记录，没有则显示最大次数
        if(set.getReps()==0){
            if (lastSet.getReps()==0) {
                reps = Integer.parseInt(sets.getRepmax().split("~")[1]);
                loadValue = 1;
            }else {
                reps = lastSet.getReps();
                loadValue = (int) (lastSet.getLoad()/2.5);
            }
        }else {
            reps = set.getReps();
            loadValue = (int) (set.getLoad()/2.5);
            checkBox.setChecked(true);
        }

        final SingleSet bufferSet = new SingleSet(loadValue * 2.5, reps);

        textOrder.setText("第" + (index+1) + "组");

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    if(bufferSet.getReps() == 0 || bufferSet.getLoad() == 0){
                        buttonView.setChecked(false);
                        Toast.makeText(mContext, "完成次数或重量不能为0", Toast.LENGTH_SHORT).show();
                    }else {
                        set.setLoad(bufferSet.getLoad());
                        set.setReps(bufferSet.getReps());
                    }
                }else {
                    set.setLoad(0.0);
                    set.setReps(0);
                }
            }
        });

        String[] display = new String[200];
        for(int i = 0; i < display.length; i++){
            double kg = (i+1) * 2.5;
            if(kg - (int) kg ==0){
                display[i] = Integer.toString((int) kg);
            }else {
                display[i] = Integer.toString((int) kg) + ".5";
            }
        }
        loadPicker.setDisplayedValues(display);
        loadPicker.setMinValue(1);
        loadPicker.setMaxValue(display.length);
        loadPicker.setValue(loadValue);
        loadPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                bufferSet.setLoad(newVal * 2.5);
                if(checkBox.isChecked()){
                    checkBox.setChecked(false);
                }
            }
        });
        loadPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);//关闭编辑模式

        repsPicker.setMinValue(1);
        repsPicker.setMaxValue(30);
        repsPicker.setValue(reps);
        repsPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                bufferSet.setReps(newVal);
                if(checkBox.isChecked()){
                    checkBox.setChecked(false);
                }
            }
        });
        repsPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);//关闭编辑模
        return loadRepsView;
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
