package fossen.power;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Administrator on 2017/9/20.
 */

public class ComponentCreator {

    //创建带返回键的标题栏
    public static Toolbar createBackToolbar(final AppCompatActivity activity, int id){
        Toolbar toolbar = (Toolbar) activity.findViewById(id);
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.finish();
            }
        });
        return toolbar;
    }

    //创建一行带有负重，次数选择器的视图
    public static View createLoadRepsPickers(final Context mContext, int order, final SingleSet set){
        View loadRepsView = LayoutInflater.from(mContext).inflate(R.layout.item_ttw_load_reps, null);
        TextView textOrder = (TextView) loadRepsView.findViewById(R.id.text_ttw_order);
        //TextView textUnit = (TextView) loadRepsView.findViewById(R.id.text_ttw_unit);
        NumberPicker loadPicker = (NumberPicker) loadRepsView.findViewById(R.id.picker_ttw_load);
        NumberPicker repsPicker = (NumberPicker) loadRepsView.findViewById(R.id.picker_ttw_reps);
        final CheckBox checkBox = (CheckBox) loadRepsView.findViewById(R.id.check_ttw);

        int loadValue, reps;
        loadValue = (int) (set.getLoad()/2.5);
        reps = set.getReps();
        textOrder.setText("第" + order + "组");
        if(set.getReps()!=0){
            checkBox.setChecked(true);
        }
        final SingleSet bufferSet = new SingleSet(set.getLoad(), set.getReps());
        final SingleSet initialSet = new SingleSet(set.getLoad(), set.getReps());

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    if(bufferSet.getReps() == 0){
                        buttonView.setChecked(false);
                        Toast.makeText(mContext, "完成次数不能为0", Toast.LENGTH_SHORT).show();
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

        String[] display = new String[201];
        for(int i = 0; i < display.length; i++){
            double kg = i * 2.5;
            if(kg - (int) kg ==0){
                display[i] = Integer.toString((int) kg);
            }else {
                display[i] = Integer.toString((int) kg) + ".5";
            }
        }
        loadPicker.setDisplayedValues(display);
        loadPicker.setMinValue(0);
        loadPicker.setMaxValue(display.length-1);
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

        repsPicker.setMinValue(0);
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
}