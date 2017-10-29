package fossen.power.training_program_library;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;

import fossen.power.R;
import fossen.power.Sets;
import fossen.power.TrainingDay;

public class TrainingDayModifyActivity extends AppCompatActivity {
    private TrainingDay trainingDay;
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
        trainingDay = (TrainingDay) result.getSerializableExtra("trainingDay");

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
        if(trainingDay.isRestDay()){
            text_day.setText((dayIndex +1) + "  休息  " + trainingDay.getTitle());
            text_count.setText("");
        }else{
            text_day.setText((dayIndex +1) + "  训练  " + trainingDay.getTitle());
            text_count.setText(trainingDay.numberOfExercise() + "个动作  "
                    + trainingDay.numberOfSingleSets() + "组");
        }

        //绑定配适器
        tdmAdapter = new TrainingDayModifyAdapter(dayIndex, trainingDay, text_day, text_count, this);
        list_tdm.setAdapter(tdmAdapter);

        //设置添加新组集按钮
        final String[] structures = getString(R.string.sets_structure).split(",");
        final String[] types = getString(R.string.exercise_type).split(",");
        button_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(TrainingDayModifyActivity.this, v);
                Menu menu = popup.getMenu();
                menu.add("添加一组动作：");
                for (int i = 0; i<structures.length; i++){
                    menu.add(Menu.NONE,Menu.NONE,i+1,structures[i]);
                }
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getOrder()>0) {
                            Sets newSets = new Sets();
                            newSets.addSet(5);
                            newSets.setRepmax("8~12");
                            newSets.setRest(120);
                            newSets.setStructure(types[item.getOrder() - 1]);
                            trainingDay.addSets(newSets);
                            if (trainingDay.isRestDay()) {
                                text_day.setText((dayIndex + 1) + "  休息  " + trainingDay.getTitle());
                                text_count.setText("");
                            } else {
                                text_day.setText((dayIndex + 1) + "  训练  " + trainingDay.getTitle());
                                text_count.setText(trainingDay.numberOfExercise() + "个动作  "
                                        + trainingDay.numberOfSingleSets() + "组");
                            }
                            Intent intent = new Intent(TrainingDayModifyActivity.this, ChooseExerciseActivity.class);
                            intent.putExtra("sets", newSets);
                            TrainingDayModifyActivity.this.startActivityForResult(intent, trainingDay.numberOfSets() - 1);
                        }
                        return true;
                    }
                });
                popup.show();
            }
        });

        //确认修改后的数据，传回上一个Activity
        button_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                result = new Intent();
                result.putExtra("dayIndex", dayIndex);
                result.putExtra("trainingDay", trainingDay);
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
                Sets sets = (Sets) data.getSerializableExtra("sets");
                trainingDay.replaceSets(requestCode, sets);
                break;
            case RESULT_CANCELED : default:
        }
    }
}
