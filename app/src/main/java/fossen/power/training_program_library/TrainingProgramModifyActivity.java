package fossen.power.training_program_library;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import fossen.power.R;
import fossen.power.TPDBOpenHelper;
import fossen.power.TrainingProgram;

public class TrainingProgramModifyActivity extends AppCompatActivity {
    private TPDBOpenHelper tpdbOpenHelper;
    private TrainingProgram tp;
    private TrainingProgramModifyAdapter tpmAdapter;

    private ListView list_tpm;
    private View header;
    private EditText editTitle;
    private EditText editGoal;
    private EditText editNote;
    private ImageView add_day;
    private TextView text_circle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training_program_modify);

        // 给listView添加headerView，用于显示训练方案的基本信息
        list_tpm = (ListView) findViewById(R.id.list_tpm);
        header = getLayoutInflater().inflate(R.layout.header_tpc_modification,null);
        list_tpm.addHeaderView(header);
        editTitle = (EditText) findViewById(R.id.edit_tpc_title_mod);
        editGoal = (EditText) findViewById(R.id.edit_tpc_goal_mod);
        editNote = (EditText) findViewById(R.id.edit_tpc_note_mod);
        add_day = (ImageView) findViewById(R.id.button_tpcm_add);
        text_circle = (TextView) findViewById(R.id.text_tpcm_circle);

        //添加新训练日
        add_day.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tp.addTrainingDay(1);
                tpmAdapter.notifyDataSetChanged();
                text_circle.setText("周期: " + tp.circleDays() + "天");
            }
        });

        //保存修改后的数据
        Button button_save = (Button) findViewById(R.id.button_tpm_save);
        button_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tp.setName(editTitle.getText().toString());
                tp.setGoal(editGoal.getText().toString());
                tp.setNote(editNote.getText().toString());
                tpmAdapter.saveModification();
                TrainingProgramModifyActivity.this.finish();
            }
        });
        //取消返回上一个活动
        Button button_cancel = (Button) findViewById(R.id.button_tpm_cancel);
        button_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TrainingProgramModifyActivity.this.finish();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        //加载训练方案
        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        tpdbOpenHelper = new TPDBOpenHelper(this);
        tp = tpdbOpenHelper.inputTrainingProgram(id);

        //设置列表头视图的内容
        editTitle.setText(tp.getName());
        editGoal.setText(tp.getGoal() );
        editNote.setText(tp.getNote().replace("\\n","\n"));
        text_circle.setText("周期: " + tp.circleDays() + "天");

        //绑定配适器
        tpmAdapter = new TrainingProgramModifyAdapter(tp, tpdbOpenHelper, this);
        list_tpm.setAdapter(tpmAdapter);
    }
}
