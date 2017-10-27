package fossen.power.training_program_library;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;

import fossen.power.Exercise;
import fossen.power.R;
import fossen.power.Sets;
import fossen.power.TrainingDay;

/**
 * Created by Administrator on 2017/9/28.
 */

public class TrainingDayModifyAdapter extends BaseAdapter {

    private int dayIndex;
    private TrainingDay trainingDay;
    private TextView text_day;
    private TextView text_count;
    private Activity tdmActivity;
    private Context mContext;
    //定义成员变量mTouchItemPosition,用来记录手指触摸的EditText的位置
    private int mTouchItemPosition = -1;
    private int mTouchItemEdit = -1;

    public TrainingDayModifyAdapter(int dayIndex, TrainingDay trainingDay,
                                    TextView text_day, TextView text_count,
                                    Activity activity){
        this.dayIndex = dayIndex;
        this.trainingDay = trainingDay;
        this.text_day = text_day;
        this.text_count = text_count;
        tdmActivity = activity;
        this.mContext = activity;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        //导入数据
        final int pos = position;
        final Sets sets = trainingDay.getSets(pos);
        final String rapmax[] = new String[]{"8","12"};
        if(!sets.getRepmax().equals("")){
            rapmax[0] = sets.getRepmax().split("~")[0];
            rapmax[1] = sets.getRepmax().split("~")[1];
        }
        final int[] rest = {2,0};//前一个是min后一个是s
        if (sets.getRest()<60){
            rest[1] = sets.getRest();
        }else if(sets.getRest()%60==0){
            rest[0] = sets.getRest()/60;
        }else {
            rest[0] = sets.getRest()/60;
            rest[1] = sets.getRest()%60;
        }

        if(convertView == null) {
            convertView =
                    LayoutInflater.from(mContext).inflate(
                            R.layout.item_tpci_modification, parent, false);
            holder = new ViewHolder();
            holder.text_exercise = (TextView) convertView.findViewById(R.id.text_tpc_exercise_mod);
            holder.button_delete = (ImageView) convertView.findViewById(R.id.button_tpc_delete_sets);
            holder.in_choose_exercise = convertView.findViewById(R.id.in_choose_exercise);
            holder.picker_rm1 = (NumberPicker) convertView.findViewById(R.id.picker_tpc_rm1_mod);
            holder.picker_rm2 = (NumberPicker) convertView.findViewById(R.id.picker_tpc_rm2_mod);
            holder.picker_sets = (NumberPicker) convertView.findViewById(R.id.picker_tpc_sets_mod);
            holder.picker_min = (NumberPicker) convertView.findViewById(R.id.picker_tpc_min_mod);
            holder.picker_sec = (NumberPicker) convertView.findViewById(R.id.picker_tpc_sec_mod);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        //有动作则显示
        if(sets.getExerciseList().size()!=0){
            holder.text_exercise.setText(sets.getExercise(0).getName());
        }else {
            holder.text_exercise.setText("选择训练动作");
        }
        //设置选择动作活动的入口
        holder.in_choose_exercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ChooseExerciseActivity.class);
                String names = "";
                for(Exercise exercise: sets.getExerciseList()){
                    names += exercise.getName() + ",";
                }
                intent.putExtra("names", names);
                tdmActivity.startActivityForResult(intent,pos);
            }
        });

        //设置删除按钮
        holder.button_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                trainingDay.removeSets(pos);
                notifyDataSetChanged();
                //设置列表头视图的内容
                if(trainingDay.isRestDay()){
                    text_day.setText((dayIndex+1) + "  休息: " + trainingDay.getTitle());
                    text_count.setText("");
                }else{
                    text_day.setText((dayIndex+1) + "  训练: " + trainingDay.getTitle());
                    text_count.setText(trainingDay.numberOfExercise() + "个动作  "
                            + trainingDay.numberOfSingleSets() + "组");
                }
            }
        });

        //设置每个选择框
        holder.picker_rm1.setMinValue(1);
        holder.picker_rm1.setMaxValue(50);
        holder.picker_rm1.setValue(Integer.parseInt(rapmax[0]));
        holder.picker_rm1.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        holder.picker_rm1.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                rapmax[0] = Integer.toString(newVal);
                sets.setRepmax(rapmax[0] + "~" + rapmax[1]);
            }
        });
        holder.picker_rm2.setMinValue(1);
        holder.picker_rm2.setMaxValue(50);
        holder.picker_rm2.setValue(Integer.parseInt(rapmax[1]));
        holder.picker_rm2.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        holder.picker_rm2.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                rapmax[1] = Integer.toString(newVal);
                sets.setRepmax(rapmax[0] + "~" + rapmax[1]);
            }
        });
        holder.picker_sets.setMinValue(1);
        holder.picker_sets.setMaxValue(25);
        holder.picker_sets.setValue(sets.numberOfSingleSets());
        holder.picker_sets.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        holder.picker_sets.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                sets.clearSets();
                sets.addSet(newVal);
                text_count.setText(trainingDay.numberOfExercise() + "个动作  "
                        + trainingDay.numberOfSingleSets() + "组");
            }
        });
        holder.picker_min.setMinValue(0);
        holder.picker_min.setMaxValue(10);
        holder.picker_min.setValue(rest[0]);
        holder.picker_min.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        holder.picker_min.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                rest[0] = newVal;
                sets.setRest(rest[0] * 60 + rest[1]);
            }
        });
        holder.picker_sec.setMinValue(0);
        holder.picker_sec.setMaxValue(59);
        holder.picker_sec.setValue(rest[1]);
        holder.picker_sec.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        holder.picker_sec.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                rest[1] = newVal;
                sets.setRest(rest[0] * 60 + rest[1]);
            }
        });
        return convertView;
    }

    private static class ViewHolder{
        ImageView button_delete;
        TextView text_exercise;
        View in_choose_exercise;
        NumberPicker picker_rm1;
        NumberPicker picker_rm2;
        NumberPicker picker_sets;
        NumberPicker picker_min;
        NumberPicker picker_sec;
    }
}
