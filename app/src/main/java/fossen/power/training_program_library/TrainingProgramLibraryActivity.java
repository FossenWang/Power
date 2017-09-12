package fossen.power.training_program_library;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

import fossen.power.R;
import fossen.power.TrainingDay;
import fossen.power.TrainingProgram;

public class TrainingProgramLibraryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training_program_library);


        //设置返回键
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_tpl);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //加载训练方案列表
        ArrayList<TrainingProgram> programs = createProgramList();

        //绑定配适器
        ListView listView =(ListView) findViewById(R.id.list_tpl);
        TrainingProgramLibraryAdapter tplAdapter =
                new TrainingProgramLibraryAdapter(programs,this);
        listView.setAdapter(tplAdapter);
    }

    public ArrayList<TrainingProgram> createProgramList(){
        ArrayList<TrainingProgram> tpList = new ArrayList<TrainingProgram>();
        TrainingProgram tp1 = new TrainingProgram();
        tp1.setTitle("推拉腿式训练");
        tp1.setGoal("增肌");
        tp1.addTrainingDay(new TrainingDay());
        tp1.addTrainingDay(new TrainingDay());
        tp1.addTrainingDay(new TrainingDay());
        tp1.addTrainingDay(new TrainingDay());
        tp1.addTrainingDay(new TrainingDay());
        tp1.addTrainingDay(new TrainingDay());
        tp1.addTrainingDay(new TrainingDay());
        tpList.add(tp1);

        TrainingProgram tp2 = new TrainingProgram();
        tp2.setTitle("入门式训练");
        tp2.setGoal("增肌");
        tp2.addTrainingDay(new TrainingDay());
        tp2.addTrainingDay(new TrainingDay());
        tp2.addTrainingDay(new TrainingDay());
        tp2.addTrainingDay(new TrainingDay());
        tp2.addTrainingDay(new TrainingDay());
        tp2.setStart(true);
        tp2.setDate(2017,8,16);
        tpList.add(tp2);


        tpList.add(tp1);
        tpList.add(tp1);
        tpList.add(tp1);
        tpList.add(tp1);
        tpList.add(tp1);
        tpList.add(tp1);
        tpList.add(tp1);
        tpList.add(tp1);
        return tpList;
    }
}
