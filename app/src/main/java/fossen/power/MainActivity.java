package fossen.power;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;
import android.widget.TextView;


import java.util.ArrayList;

import fossen.power.exercise_library.ExerciseLibraryActivity;
import fossen.power.training_program_library.TrainingProgramLibraryActivity;
import fossen.power.training_today.TrainingTodayActivity;

public class MainActivity extends AppCompatActivity {
    private TPDBOpenHelper tpdbOpenHelper;
    private ViewGroup layoutTT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //设置TrainingProgramLibraryActivity的入口
        View inTrainingProgramLibrary = findViewById(R.id.in_training_program_library);
        inTrainingProgramLibrary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), TrainingProgramLibraryActivity.class);
                startActivity(intent);
            }
        });

        //设置ExerciseLibraryActivity的入口
        View inExerciseLibrary = findViewById(R.id.in_exercise_library);
        inExerciseLibrary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ExerciseLibraryActivity.class);
                startActivity(intent);
            }
        });

        layoutTT = (ViewGroup) findViewById(R.id.layout_tt);
        NumberPicker picker1 = (NumberPicker) findViewById(R.id.numberPicker);
        NumberPicker picker2 = (NumberPicker) findViewById(R.id.numberPicker2);
        picker1.setFormatter(new NumberPicker.Formatter() {
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
        picker1.setMinValue(0);
        picker1.setMaxValue(200);
        picker1.setValue(0);
        picker1.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);//关闭编辑模式
        picker2.setMinValue(0);
        picker2.setMaxValue(30);
        picker2.setValue(12);
        picker2.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);//关闭编辑模式
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (tpdbOpenHelper == null) {
            tpdbOpenHelper = new TPDBOpenHelper(this);
        }
        ArrayList<TrainingProgram> tpList = tpdbOpenHelper.inputTrainingProgramList();
        layoutTT.removeAllViews();
        boolean training = false;
        for(int i = 0; i < tpList.size(); i++){
            if(tpList.get(i).getStart()>0){
                addTrainingItem(tpList.get(i));
                training = true;
            }
        }
        if(!training){
            addTrainingItem();
        }
    }

    //动态添加今日训练的项目
    protected void addTrainingItem(TrainingProgram trainingProgram){
        View trainingItem = getLayoutInflater().inflate(R.layout.item_training_today, null);
        layoutTT.addView(trainingItem);
        TextView text_itt_title = (TextView) trainingItem.findViewById(R.id.text_itt_title);
        TextView text_itt_circle = (TextView) trainingItem.findViewById(R.id.text_itt_circle_goal);
        TextView text_itt_day = (TextView) trainingItem.findViewById(R.id.text_itt_day);
        TextView text_itt_count = (TextView) trainingItem.findViewById(R.id.text_itt_count);

        text_itt_title.setText(trainingProgram.getName());
        text_itt_circle.setText(trainingProgram.getCircleGoal());
        final TrainingProgram tp = trainingProgram;
        int day = trainingProgram.countTodayInCircle();
        TrainingDay today = tpdbOpenHelper.inputTrainingDay(trainingProgram.getId(),
                trainingProgram.getTrainingDay(day - 1).getTitle(), day);
        if(today.isRestDay()){
            text_itt_day.setText("休息: " + today.getTitle());
            text_itt_count.setText("");
        }else {
            text_itt_day.setText("训练: " + today.getTitle());
            text_itt_count.setText(today.numberOfExercise() + "个动作  "
                    + today.numberOfSingleSets() + "组");
        }

        trainingItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), TrainingTodayActivity.class);
                intent.putExtra("trainingProgram", tp);
                startActivity(intent);
            }
        });
    }
    //无训练计划时添加进入训练方案库的视图
    protected void addTrainingItem() {
        View trainingItem = getLayoutInflater().inflate(R.layout.item_training_today, null);
        layoutTT.addView(trainingItem);
        trainingItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), TrainingProgramLibraryActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStop(){
        super.onStop();
        tpdbOpenHelper.close();
    }
}
