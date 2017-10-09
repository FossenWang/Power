package fossen.power.training_today;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import fossen.power.ComponentCreator;
import fossen.power.TPDBOpenHelper;
import fossen.power.TrainingDay;
import fossen.power.R;
import fossen.power.TrainingProgram;

public class TrainingTodayActivity extends AppCompatActivity {
    private TPDBOpenHelper tpdbOpenHelper;
    private TrainingProgram trainingProgram;
    private TrainingDay trainingToday;
    private TrainingTodayAdapter ttAdapter;
    private ListView trainingList;

    private View header;
    private View header2;
    private ViewGroup headerLayout;
    private TextView textTitle;
    private TextView textCircleGoal;
    private TextView textNote;
    private ImageView arrow;
    private TextView textDay;
    private TextView textCount;
    private TextView startTraining;
    private ViewGroup actionLayout;
    private View actionView;
    private ImageButton buttonPrevious;
    private ImageButton buttonNext;
    private Button buttonDone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training_today);

        //设置toolbar和返回键
        ComponentCreator.createBackToolbar(this,R.id.toolbar_tt);

        //加载训练日数据
        Intent intent = getIntent();
        trainingProgram = (TrainingProgram) intent.getSerializableExtra("trainingProgram");
        int day = trainingProgram.countTodayInCircle();
        tpdbOpenHelper = new TPDBOpenHelper(this);
        trainingToday = tpdbOpenHelper.inputTrainingDay(trainingProgram.getId(),
                trainingProgram.getTrainingDay(day - 1).getTitle(), day);

        //给listView添加headerView，用于显示训练的基本信息
        trainingList = (ListView) findViewById(R.id.list_tt);
        header = getLayoutInflater().inflate(R.layout.header_training_program_content,null);
        trainingList.addHeaderView(header);
        header2 = getLayoutInflater().inflate(R.layout.item_tpc_group, null);
        trainingList.addHeaderView(header2);

        headerLayout = (ViewGroup) findViewById(R.id.layout_tpc_header);
        textTitle = (TextView) findViewById(R.id.text_tpc_title);
        textCircleGoal = (TextView) findViewById(R.id.text_tpc_circle_goal);
        textNote = (TextView) findViewById(R.id.text_tpc_note);
        arrow = (ImageView) findViewById(R.id.arrow_tpc_note);
        textDay = (TextView) findViewById(R.id.text_tpc_day);
        textCount = (TextView) findViewById(R.id.text_tpc_count);

        //设置列表头视图的内容
        textTitle.setText(trainingProgram.getName());
        textCircleGoal.setText(trainingProgram.getCircleGoal() );
        textNote.setText(trainingProgram.getNote().replace("\\n","\n"));
        if(trainingToday.isRestDay()){
            textDay.setText((day) + "  休息: " + trainingToday.getTitle());
            textCount.setText("");
        }else{
            textDay.setText((day) + "  训练: " + trainingToday.getTitle());
            textCount.setText(trainingToday.numberOfExercise() + "个动作  "
                    + trainingToday.numberOfSingleSets() + "组");
        }

        //设置监听器，实现点击缩放说明栏文字
        headerLayout.setOnClickListener(new View.OnClickListener() {
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
        ttAdapter = new TrainingTodayAdapter(tpdbOpenHelper, trainingProgram, trainingToday, this);
        trainingList.setAdapter(ttAdapter);

        //设置底部操作栏
        actionLayout = (ViewGroup) findViewById(R.id.action_tt);
        actionView = getLayoutInflater().inflate(R.layout.item_tt_action, null);
        buttonPrevious = (ImageButton) actionView.findViewById(R.id.button_tt_previous);
        buttonNext = (ImageButton) actionView.findViewById(R.id.button_tt_next);
        buttonDone = (Button) actionView.findViewById(R.id.button_tt_done);
        startTraining = (TextView) findViewById(R.id.text_tt_start);
        startTraining.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ttAdapter.setWritingItem(ttAdapter.getRecordingSets());
                ttAdapter.notifyDataSetChanged();
                actionLayout.removeAllViews();
                actionLayout.addView(actionView);
                trainingList.smoothScrollToPosition(2);
            }
        });
        buttonDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ttAdapter.setWritingItem(-1);
                ttAdapter.notifyDataSetChanged();
                actionLayout.removeAllViews();
                actionLayout.addView(startTraining);
                trainingList.smoothScrollToPosition(ttAdapter.getWritingItem() + 2);
            }
        });
        buttonPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int item = ttAdapter.getWritingItem();
                if(item > 0){
                    ttAdapter.setWritingItem(item-1);
                    ttAdapter.notifyDataSetChanged();
                    trainingList.smoothScrollToPosition(ttAdapter.getWritingItem() + 2);
                }
            }
        });
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int item = ttAdapter.getWritingItem();
                if (item < (trainingToday.numberOfSets() - 1)){
                    ttAdapter.setWritingItem(item+1);
                    ttAdapter.notifyDataSetChanged();
                    trainingList.smoothScrollToPosition(ttAdapter.getWritingItem() + 2);
                }
            }
        });
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        tpdbOpenHelper.close();
    }
}
