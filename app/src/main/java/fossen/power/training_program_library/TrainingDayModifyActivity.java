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
import fossen.power.TrainingDay;

public class TrainingDayModifyActivity extends AppCompatActivity {
    private TrainingDay td;
    private TrainingDayModifyAdapter tdmAdapter;
    private int dayIndex;
    private Intent result;

    private ListView list_tdm;
    private Button button_cancel;
    private Button button_yes;
    private View header;
    private TextView text_day;
    private TextView text_count;
    private ImageView button_add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training_day_modify);

        //加载训练方案
        result = getIntent();
        setResult(RESULT_CANCELED, result);
        dayIndex = result.getIntExtra("dayIndex", 0);
        td = (TrainingDay) result.getSerializableExtra("trainingDay");

        // 给listView添加headerView，显示训练日的基本信息
        list_tdm = (ListView) findViewById(R.id.list_tdm);
        header = getLayoutInflater().inflate(R.layout.header_tdm, null);
        list_tdm.addHeaderView(header);
        text_day = (TextView) findViewById(R.id.text_tdm_day);
        text_count = (TextView) findViewById(R.id.text_tdm_count);
        button_add = (ImageView) findViewById(R.id.button_tdm_add);
        button_yes = (Button) findViewById(R.id.button_tdm_yes);
        button_cancel = (Button) findViewById(R.id.button_tdm_cancel);

        //设置列表头视图的内容
        if(td.isRestDay()){
            text_day.setText((dayIndex +1) + "  休息  " + td.getTitle());
            text_count.setText("");
        }else{
            text_day.setText((dayIndex +1) + "  训练  " + td.getTitle());
            text_count.setText(td.numberOfExercise() + "个动作  "
                    + td.numberOfSingleSets() + "组");
        }

        //绑定配适器
        tdmAdapter = new TrainingDayModifyAdapter(dayIndex, td, text_day, text_count, this);
        list_tdm.setAdapter(tdmAdapter);

        //设置添加新组集按钮
        button_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Sets newSets = new Sets();
                newSets.addSet(5);
                newSets.setRepmax("8~12");
                newSets.setRest(120);
                td.addSets(newSets);
                tdmAdapter.notifyDataSetChanged();
                if(td.isRestDay()){
                    text_day.setText((dayIndex +1) + "  休息  " + td.getTitle());
                    text_count.setText("");
                }else{
                    text_day.setText((dayIndex +1) + "  训练  " + td.getTitle());
                    text_count.setText(td.numberOfExercise() + "个动作  "
                            + td.numberOfSingleSets() + "组");
                }
            }
        });

        //确认修改后的数据，传回上一个Activity
        button_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                result = new Intent();
                result.putExtra("dayIndex", dayIndex);
                result.putExtra("trainingDay", td);
                setResult(RESULT_OK, result);
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
        tdmAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode){
            case RESULT_OK :
                String names = data.getStringExtra("names");
                if (!names.split(",")[0].equals("")) {
                    td.getSets(requestCode).setExerciseList(names.split(","));
                }break;
            case RESULT_CANCELED : default:
        }
    }
}
