package fossen.power.training_program_library;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import fossen.power.R;
import fossen.power.Sets;
import fossen.power.TPDBOpenHelper;
import fossen.power.TrainingDay;
import fossen.power.TrainingProgram;

public class TrainingDayModifyActivity extends AppCompatActivity {
    private TPDBOpenHelper tpdbOpenHelper;
    private TrainingProgram tp;
    private TrainingDay td;
    private TrainingDayModifyAdapter tdmAdapter;

    ListView list_tdm;
    Button button_cancel;
    Button button_save;
    View header;
    TextView text_day;
    TextView text_count;
    ImageView button_add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training_day_modify);

        // 给listView添加headerView，显示训练日的基本信息
        list_tdm = (ListView) findViewById(R.id.list_tdm);
        header = getLayoutInflater().inflate(R.layout.header_tdm, null);
        list_tdm.addHeaderView(header);
        text_day = (TextView) findViewById(R.id.text_tdm_day);
        text_count = (TextView) findViewById(R.id.text_tdm_count);
        button_add = (ImageView) findViewById(R.id.button_tdm_add);
        button_save = (Button) findViewById(R.id.button_tdm_save);
        button_cancel = (Button) findViewById(R.id.button_tdm_cancel);

        //保存修改后的数据
        button_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tdmAdapter.saveModification();
                TrainingDayModifyActivity.this.finish();
            }
        });

        //取消返回上一个活动
        button_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TrainingDayModifyActivity.this.finish();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        //加载训练方案
        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        final int index = intent.getIntExtra("dayIndex", 0);
        tpdbOpenHelper = new TPDBOpenHelper(this);
        tp = tpdbOpenHelper.inputTrainingProgram(id);
        td = tp.getTrainingDay(index);

        //设置列表头视图的内容
        if(td.isRestDay()){
            text_day.setText((index+1) + "  休息: " + td.getTitle());
            text_count.setText("");
        }else{
            text_day.setText((index+1) + "  训练: " + td.getTitle());
            text_count.setText(td.numberOfExercise() + "个动作  "
                    + td.numberOfSingleSets() + "组");
        }
        //添加新组集
        button_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                td.addSets(new Sets());
                tdmAdapter.notifyDataSetChanged();
                if(td.isRestDay()){
                    text_day.setText((index+1) + "  休息: " + td.getTitle());
                    text_count.setText("");
                }else{
                    text_day.setText((index+1) + "  训练: " + td.getTitle());
                    text_count.setText(td.numberOfExercise() + "个动作  "
                            + td.numberOfSingleSets() + "组");
                }
            }
        });
        //绑定配适器
        tdmAdapter = new TrainingDayModifyAdapter(id, index, td, tpdbOpenHelper, text_day, text_count, this);
        list_tdm.setAdapter(tdmAdapter);
    }
}
