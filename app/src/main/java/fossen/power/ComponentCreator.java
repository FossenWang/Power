package fossen.power;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.TextView;

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
    public static View createLoadRepsPickers(Context mContext, int order, final SingleSet set){
        View loadReps = LayoutInflater.from(mContext).inflate(R.layout.item_ttw_load_reps, null);
        TextView textOrder = (TextView) loadReps.findViewById(R.id.text_ttw_order);
        //TextView textUnit = (TextView) loadReps.findViewById(R.id.text_ttw_unit);
        NumberPicker loadPicker = (NumberPicker) loadReps.findViewById(R.id.picker_ttw_load);
        NumberPicker repsPicker = (NumberPicker) loadReps.findViewById(R.id.picker_ttw_reps);
        int loadValue, reps;
        loadValue = (int) (set.getLoad()/2.5);
        reps = set.getReps();
        textOrder.setText("第" + order + "组");
        loadPicker.setFormatter(new NumberPicker.Formatter() {
            @Override
            public String format(int value) {
                double kg = value * 2.5;
                String display;
                if(kg - (int) kg ==0){
                    display = Integer.toString((int) kg);
                }else {
                    display = Integer.toString((int) kg) + ".5";
                }
                return display;
            }
        });
        loadPicker.setMinValue(0);
        loadPicker.setMaxValue(200);
        loadPicker.setValue(loadValue);
        loadPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                set.setLoad(newVal * 2.5);
            }
        });
        loadPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);//关闭编辑模式
        repsPicker.setMinValue(0);
        repsPicker.setMaxValue(30);
        repsPicker.setValue(reps);
        repsPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                set.setReps(newVal);
            }
        });
        repsPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);//关闭编辑模式
        return loadReps;
    }
}