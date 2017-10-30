package fossen.power.training_program_library;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.TextView;

import fossen.power.R;
import fossen.power.DBOpenHelper;
import fossen.power.TrainingDay;
import fossen.power.TrainingProgram;

public class TrainingProgramModifyActivity extends AppCompatActivity {
    private DBOpenHelper DBOpenHelper;
    private TrainingProgram tp;
    private TrainingProgramModifyAdapter tpmAdapter;
    private int id;

    private ListView list_tpm;
    private View header;
    private EditText editTitle;
    private EditText editNote;
    private ImageView button_add;
    private TextView text_circle;
    private Button button_save;
    private Button button_cancel;
    private AlertDialog dialog_cancel;
    private NumberPicker picker_goal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training_program_modify);

        //获取训练方案id
        Intent intent = getIntent();
        id = intent.getIntExtra("id",0);
        DBOpenHelper = new DBOpenHelper(this);
        tp = DBOpenHelper.inputTrainingProgram(id);

        // 给listView添加headerView，用于显示训练方案的基本信息
        list_tpm = (ListView) findViewById(R.id.list_tpm);
        header = getLayoutInflater().inflate(R.layout.header_tpc_modification,null);
        list_tpm.addHeaderView(header);
        editTitle = (EditText) findViewById(R.id.edit_tpc_title_mod);
        editNote = (EditText) findViewById(R.id.edit_tpc_note_mod);
        button_add = (ImageView) findViewById(R.id.button_tpcm_add);
        text_circle = (TextView) findViewById(R.id.text_tpcm_circle);
        button_save = (Button) findViewById(R.id.button_tpm_save);
        button_cancel = (Button) findViewById(R.id.button_tpm_cancel);
        picker_goal = (NumberPicker) findViewById(R.id.picker_tpcm_goal);

        //设置列表头视图的内容
        editTitle.setText(tp.getName());
        editNote.setText(tp.getNote().replace("\\n","\n"));
        text_circle.setText("周期: " + tp.circleDays() + "天");

        //绑定配适器
        tpmAdapter = new TrainingProgramModifyAdapter(tp, text_circle, this);
        list_tpm.setAdapter(tpmAdapter);

        //添加新训练日
        button_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tp.addTrainingDay(1);
                tpmAdapter.notifyDataSetChanged();
                text_circle.setText("周期: " + tp.circleDays() + "天");
            }
        });

        //保存修改后的数据
        button_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tp.setStart(0);
                DBOpenHelper.updateTrainingProgram(tp);
                TrainingProgramModifyActivity.this.finish();
            }
        });

        //建立确认不保存的对话框
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("直接退出将丢失所有已更改的方案内容，确定不保存吗？")
                .setPositiveButton("不保存", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        TrainingProgramModifyActivity.this.finish();
                    }})
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }});
        dialog_cancel = builder.create();

        //取消返回上一个活动
        button_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_cancel.show();
            }
        });

        //监听编辑栏内容
        editTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                tp.setName(s.toString());
            }
        });
        editNote.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                tp.setNote(s.toString());
            }
        });

        //设置目标选择框
        final String[] display = this.getString(R.string.goals).split(",");
        picker_goal.setDisplayedValues(display);
        picker_goal.setMinValue(0);
        picker_goal.setMaxValue(display.length-1);
        picker_goal.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        if(tp.getGoal().isEmpty()){
            picker_goal.setValue(0);
            tp.setGoal(display[0]);
        }else {
            for(int i = 0; i<(display.length); i++){
                if(tp.getGoal().equals(display[i])){
                    picker_goal.setValue(i);
                }
            }
        }
        picker_goal.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                tp.setGoal(display[newVal]);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        tpmAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode){
            case RESULT_OK :
                tp.replaceTrainingDay(requestCode, (TrainingDay) data.getSerializableExtra("trainingDay"));
                break;
            case RESULT_CANCELED : default:
        }
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        DBOpenHelper.close();
    }
}
