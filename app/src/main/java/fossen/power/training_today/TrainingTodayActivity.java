package fossen.power.training_today;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Calendar;

import fossen.power.Exercise;
import fossen.power.OneSet;
import fossen.power.Sets;
import fossen.power.TrainingDay;
import fossen.power.R;
import fossen.power.training_log.TrainingRecordAdapter;

public class TrainingTodayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training_today);

        //设置返回键
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_training_today);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //加载训练日数据
        TrainingDay trainingToday = createTrainingDay();

        //给listView添加headerView，用于显示训练的基本信息
        ListView listTrainingToday = (ListView) findViewById(R.id.list_training_today);
        View header = getLayoutInflater().inflate(R.layout.header_training_record,null);
        listTrainingToday.addHeaderView(header);
        TextView trainingTitle = (TextView) findViewById(R.id.text_htr_title);
        trainingTitle.setText(trainingToday.getProgram()+": "+trainingToday.getTitle());
        final TextView trainingNote = (TextView) findViewById(R.id.text_htr_note);
        final ImageView arrow = (ImageView) findViewById(R.id.arrow_htr_note);
        trainingNote.setText(trainingToday.getNote());
        //设置监听器，实现点击缩放说明栏文字
        trainingNote.setOnClickListener(new View.OnClickListener() {
            Boolean flag = true;
            @Override
            public void onClick(View v) {
                if(flag){
                    flag = false;
                    trainingNote.setMaxLines(20);
                    arrow.setRotation(180);
                }else {
                    flag = true;
                    trainingNote.setMaxLines(1);
                    arrow.setRotation(0);
                }
            }
        });

        //绑定配适器
        TrainingRecordAdapter trainingRecordAdapter =
                new TrainingRecordAdapter(trainingToday,this);
        listTrainingToday.setAdapter(trainingRecordAdapter);
    }

    public TrainingDay createTrainingDay(){
        TrainingDay tD = new TrainingDay();
        Calendar date = Calendar.getInstance();
        Sets exer1 = new Sets();
        Sets exer2 = new Sets();
        exer1.addSet(new OneSet(50.0,10));
        exer1.addSet(new OneSet(50.0,10));
        exer1.addSet(new OneSet(50.0,9));
        exer1.addSet(new OneSet(50.0,8));
        exer1.addSet(new OneSet(110,7));
        Exercise benchPress = new Exercise();
        benchPress.setName("卧推");
        benchPress.setIcon(R.drawable.bench_press);
        exer1.addExercise(benchPress);
        exer2.addSet(new OneSet(40.0,10));
        exer2.addSet(new OneSet(40.0,10));
        exer2.addSet(new OneSet(40.0,9));
        Exercise incline_bench_press = new Exercise();
        incline_bench_press.setName("上斜卧推");
        incline_bench_press.setIcon(R.drawable.incline_bench_press);
        exer2.addExercise(incline_bench_press);
        tD.addSets(exer1);
        tD.addSets(exer2);
        tD.addSets(exer2);
        tD.addSets(exer2);
        tD.addSets(exer2);
        tD.addSets(exer2);
        tD.addSets(exer2);
        tD.setDate(date);
        tD.setNote("今日训练胸肌和肱三头肌\n今日训练胸肌和肱三头肌\n今日训练胸肌和肱三头肌\n今日训练胸肌和肱三头肌\n今日训练胸肌和肱三头肌");
        tD.setProgram("推拉腿式训练");
        tD.setTitle("练胸日");
        return tD;
    }
}
