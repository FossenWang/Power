package fossen.power.training_log;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import fossen.power.ComponentCreator;
import fossen.power.R;
import fossen.power.TPDBOpenHelper;
import fossen.power.TrainingProgram;

public class TrainingRecordActivity extends AppCompatActivity {
    private TPDBOpenHelper tpdbOpenHelper;
    private TrainingProgram record;

    private TrainingRecordAdapter trAdapter;
    private ListView trList;
    private View header;
    private TextView text_title;
    private TextView text_count;
    private TextView text_date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training_record);

        ComponentCreator.createBackToolbar(this, R.id.toolbar_tr);

        //加载训练记录数据
        tpdbOpenHelper = new TPDBOpenHelper(this);
        Intent intent = getIntent();
        record = (TrainingProgram) intent.getSerializableExtra("record");

        // 给listView添加headerView，用于显示训练方案的基本信息
        trList = (ListView) findViewById(R.id.list_tr);
        header = getLayoutInflater().inflate(R.layout.header_training_record,null);
        trList.addHeaderView(header);
        text_title = (TextView) findViewById(R.id.text_tr_title);
        text_count = (TextView) findViewById(R.id.text_tr_count);
        text_date = (TextView) findViewById(R.id.text_tr_date);

                //绑定配适器
        trAdapter = new TrainingRecordAdapter(record.getTrainingDay(0), this);
        trList.setAdapter(trAdapter);

        //设置列表头视图的内容
        text_title.setText(record.getName()+"  "+record.getTrainingDay(0).getTitle());
        text_count.setText(record.getTrainingDay(0).numberOfExercise() + "个动作  "
                + record.getTrainingDay(0).numberOfSingleSets() + "组");
        String date = record.getDate();
        text_date.setText(Integer.parseInt(date.substring(0,4))+"年"
                +Integer.parseInt(date.substring(4,6))+"月"
                +Integer.parseInt(date.substring(6,8))+"日");
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        tpdbOpenHelper.close();
    }
}
