package fossen.power;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.Calendar;

import fossen.power.exercise_library.ExerciseTypesActivity;
import fossen.power.training_log.TrainingLogActivity;
import fossen.power.training_log.TrainingRecordActivity;
import fossen.power.training_program_library.TrainingProgramLibraryActivity;
import fossen.power.training_today.TrainingTodayActivity;

public class MainActivity extends AppCompatActivity {
    private DBOpenHelper DBOpenHelper;
    private ViewGroup layoutTT;
    private String date;
    private ArrayList<TrainingProgram> tpList;
    private ArrayList<TrainingProgram> logList;

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

        //设置TrainingLogActivity的入口
        View inTrainingLog = findViewById(R.id.in_training_log);
        inTrainingLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), TrainingLogActivity.class);
                startActivity(intent);
            }
        });

        //设置ExerciseTypesActivity的入口
        View inExerciseLibrary = findViewById(R.id.in_exercise_library);
        inExerciseLibrary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ExerciseTypesActivity.class);
                startActivity(intent);
            }
        });

        layoutTT = (ViewGroup) findViewById(R.id.layout_tt);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (DBOpenHelper == null) {
            DBOpenHelper = new DBOpenHelper(this);
        }
        date = TrainingProgram.formatDate(Calendar.getInstance());
        tpList = DBOpenHelper.inputTrainingProgramList();
        logList = DBOpenHelper.inputTrainingLog(date);
        layoutTT.removeAllViews();
        boolean haveTraining = false;
        boolean haveRecord = false;
        out : for(int i = 0; i < tpList.size(); i++){
            if(tpList.get(i).getStart()>0){
                for (TrainingProgram log : logList){
                    if(log.getId()==tpList.get(i).getId()){
                        continue out;
                    }//判断训练方案是否有今日的记录，有就跳过
                }
                addTrainingItem(tpList.get(i));
                haveTraining = true;
            }
        }
        if(haveTraining){addText(0,"今日训练");}
        int pos = layoutTT.getChildCount();
        for (TrainingProgram log : logList){
            addRecordItem(log);
            haveRecord = true;
        }
        if (haveRecord){addText(pos, "今日完成的训练");}
        if(!haveTraining && !haveRecord){addTrainingItem();}
    }

    //在item间添加文字“今日训练”
    protected void addText(int index, String string){
        View view = getLayoutInflater().inflate(R.layout.item_training_today_text, null);
        layoutTT.addView(view, index);
        TextView text = (TextView) view.findViewById(R.id.text_itt_text);
        text.setText(string);
    }
    //动态添加今日训练的项目
    protected void addTrainingItem(TrainingProgram trainingProgram){
        View trainingItem = getLayoutInflater().inflate(R.layout.item_training_today, null);
        layoutTT.addView(trainingItem);
        TextView text_itt_title = (TextView) trainingItem.findViewById(R.id.text_itt_title);
        TextView text_itt_circle = (TextView) trainingItem.findViewById(R.id.text_itt_circle);
        TextView text_itt_day = (TextView) trainingItem.findViewById(R.id.text_itt_day);
        TextView text_itt_count = (TextView) trainingItem.findViewById(R.id.text_itt_count);

        text_itt_title.setText(trainingProgram.getName());
        text_itt_circle.setText(trainingProgram.getCircleToFormat());
        final TrainingProgram tp = trainingProgram;
        int day = trainingProgram.countTodayInCircle();
        TrainingDay today = DBOpenHelper.inputTrainingDay(trainingProgram, day);
        if(today.isRestDay()){
            text_itt_day.setText("休息");
            text_itt_count.setText("");
        }else {
            text_itt_day.setText(today.getTitle());
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
    //动态添加今日记录的项目
    protected void addRecordItem(TrainingProgram trainingRecord){
        final View trainingItem = getLayoutInflater().inflate(R.layout.item_training_today, null);
        layoutTT.addView(trainingItem);
        TextView text_itt_title = (TextView) trainingItem.findViewById(R.id.text_itt_title);
        TextView text_itt_circle = (TextView) trainingItem.findViewById(R.id.text_itt_circle);
        TextView text_itt_day = (TextView) trainingItem.findViewById(R.id.text_itt_day);
        TextView text_itt_count = (TextView) trainingItem.findViewById(R.id.text_itt_count);

        final int id = trainingRecord.getId();
        final TrainingProgram record = trainingRecord;
        if(record.getTrainingDay(0).numberOfSets()==0){
            DBOpenHelper.inputTrainingRecord(record);
        }//防止重复导入

        //设置内容
        text_itt_title.setText(trainingRecord.getName());
        text_itt_circle.setText("已完成");
        text_itt_day.setText(trainingRecord.getTrainingDay(0).getTitle());
        text_itt_count.setText(trainingRecord.getTrainingDay(0).numberOfExercise() + "个动作  "
                + trainingRecord.getTrainingDay(0).numberOfSingleSets() + "组");

        //单击查看记录详情
        trainingItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TrainingRecordActivity.class);
                intent.putExtra("record", record);
                MainActivity.this.startActivity(intent);
            }
        });
        //长按删除记录
        trainingItem.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                PopupMenu popup = new PopupMenu(MainActivity.this, v);
                Menu menu = popup.getMenu();
                menu.add("删除");
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        DBOpenHelper.deleteTrainingRecord(date, id);
                        onStart();
                        return true;
                    }
                });
                popup.show();
                return true;
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
        DBOpenHelper.close();
    }
}
