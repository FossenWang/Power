package fossen.power.training_program_library;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import fossen.power.R;
import fossen.power.TPDBOpenHelper;
import fossen.power.TrainingProgram;

public class TrainingProgramModifyActivity extends AppCompatActivity {
    private TPDBOpenHelper tpdbOpenHelper;
    private TrainingProgram tp;
    private ViewHolder viewHolder;
    private TrainingProgramModifyAdapter tpmAdapter;

    private static class ViewHolder{
        ListView list_tpm;
        View header;
        EditText editTitle;
        EditText editGoal;
        EditText editNote;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training_program_modify);

        viewHolder = new ViewHolder();

        // 给listView添加headerView，用于显示训练方案的基本信息
        viewHolder.list_tpm = (ListView) findViewById(R.id.list_tpm);
        viewHolder.header = getLayoutInflater().inflate(R.layout.header_tpc_modification,null);
        viewHolder.list_tpm.addHeaderView(viewHolder.header);
        viewHolder.editTitle = (EditText) findViewById(R.id.edit_tpc_title_mod);
        viewHolder.editGoal = (EditText) findViewById(R.id.edit_tpc_goal_mod);
        viewHolder.editNote = (EditText) findViewById(R.id.edit_tpc_note_mod);

        //监听编辑文字内容
        viewHolder.editTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                tp.setName(s.toString());
            }
        });
        viewHolder.editGoal.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                tp.setGoal(s.toString());
            }
        });
        viewHolder.editNote.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                tp.setNote(s.toString());
            }
        });

        //保存修改后的数据
        Button button_save = (Button) findViewById(R.id.button_tpm_save);
        button_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
        viewHolder.editTitle.setText(tp.getName());
        viewHolder.editGoal.setText(tp.getGoal() );
        viewHolder.editNote.setText(tp.getNote().replace("\\n","\n"));

        //绑定配适器
        tpmAdapter = new TrainingProgramModifyAdapter(tp, tpdbOpenHelper, this);
        viewHolder.list_tpm.setAdapter(tpmAdapter);
    }
}
