package fossen.power.training_program_library;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import fossen.power.R;
import fossen.power.TPDBOpenHelper;
import fossen.power.TrainingProgram;

public class TrainingProgramModifyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training_program_modify);

        //加载训练方案
        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        TPDBOpenHelper tpdbOpenHelper = new TPDBOpenHelper(this);
        final TrainingProgram tp = tpdbOpenHelper.inputTrainingProgram(id);

        // 给listView添加headerView，用于显示训练方案的基本信息
        final ListView list_tpm = (ListView) findViewById(R.id.list_tpm);
        View header = getLayoutInflater().inflate(R.layout.header_training_program_content,null);
        list_tpm.addHeaderView(header);
        View layout = findViewById(R.id.layout_tpc_header);
        TextView textTitle = (TextView) findViewById(R.id.text_tpc_title);
        TextView textCircleGoal = (TextView) findViewById(R.id.text_tpc_circle_goal);
        final TextView textNote = (TextView) findViewById(R.id.text_tpc_note);
        final ImageView arrow = (ImageView) findViewById(R.id.arrow_tpc_note);
        textTitle.setText(tp.getName());
        textCircleGoal.setText(tp.getCircleGoal() );
        textNote.setText(tp.getNote().replace("\\n","\n"));

        //设置监听器，实现点击缩放说明栏文字
        layout.setOnClickListener(new View.OnClickListener() {
            Boolean flag = true;
            @Override
            public void onClick(View v) {
                if(flag){
                    flag = false;
                    textNote.setMaxLines(30);
                    arrow.setRotation(180);
                }else {
                    flag = true;
                    textNote.setMaxLines(1);
                    arrow.setRotation(0);
                }
            }
        });

        //绑定配适器
        final TrainingProgramModifyAdapter tpmAdapter = new TrainingProgramModifyAdapter(tp, tpdbOpenHelper, this);
        list_tpm.setAdapter(tpmAdapter);

        //保存修改后的数据
        Button button_save = (Button) findViewById(R.id.button_tpm_save);
        button_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tpmAdapter.saveModification();
                TrainingProgramModifyActivity.this.finish();
            }
        });

        Button button_cancel = (Button) findViewById(R.id.button_tpm_cancel);
        button_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TrainingProgramModifyActivity.this.finish();
            }
        });
    }
}
