package fossen.power.training_today;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Calendar;

import fossen.power.ComponentCreator;
import fossen.power.DBOpenHelper;
import fossen.power.TrainingDay;
import fossen.power.R;
import fossen.power.TrainingProgram;

public class TrainingTodayActivity extends AppCompatActivity {
    private DBOpenHelper DBOpenHelper;
    private TrainingProgram trainingProgram;
    private TrainingDay trainingToday;
    private TrainingTodayAdapter ttAdapter;
    private String date;
    private int today;

    private Toolbar topBar;
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
    private AlertDialog dialog_finish1;
    private AlertDialog dialog_finish2;
    private AlertDialog dialog_quit;

    private final String TRAINING_PROGRAM = "trainingProgram";
    private final String DATE = "date";
    private final String WRITING_ITEM = "writingItem";

    @Override
    protected void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putSerializable(TRAINING_PROGRAM, trainingProgram);
        outState.putString(DATE,date);
        outState.putInt(WRITING_ITEM, ttAdapter.getWritingItem());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training_today);

        //设置toolbar和返回键
        topBar = ComponentCreator.createBackToolbar(this,R.id.toolbar_tt);
        topBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ttAdapter.getWritingItem()>-1) {
                    openQuitDialog();
                }else {
                    finish();
                }
            }
        });

        //加载训练日数据
        DBOpenHelper = new DBOpenHelper(this);
        if(savedInstanceState==null) {
            date = TrainingProgram.formatDate(Calendar.getInstance());
            Intent intent = getIntent();
            trainingProgram = (TrainingProgram) intent.getSerializableExtra(TRAINING_PROGRAM);
        }else {
            date = savedInstanceState.getString(DATE);
            trainingProgram = (TrainingProgram) savedInstanceState.getSerializable(TRAINING_PROGRAM);
        }
        today = trainingProgram.countTodayInCircle();
        trainingToday = trainingProgram.getTrainingDay(today-1);

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

        //添加底部操作栏
        actionLayout = (ViewGroup) findViewById(R.id.action_tt);
        actionView = getLayoutInflater().inflate(R.layout.item_tt_action, null);
        buttonPrevious = (ImageButton) actionView.findViewById(R.id.button_tt_previous);
        buttonNext = (ImageButton) actionView.findViewById(R.id.button_tt_next);
        buttonDone = (Button) actionView.findViewById(R.id.button_tt_done);
        startTraining = (TextView) findViewById(R.id.text_tt_start);

        //设置列表头视图的内容
        textTitle.setText(trainingProgram.getName());
        textCircleGoal.setText(trainingProgram.getCircleGoal() );
        textNote.setText(trainingProgram.getNote().replace("\\n","\n"));
        if(trainingToday.isRestDay()){
            textDay.setText("休息");
            textCount.setText("");
        }else{
            textDay.setText("训练  " + trainingToday.getTitle());
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
        ttAdapter = new TrainingTodayAdapter(DBOpenHelper, trainingProgram.getId(), trainingToday, today, this, actionLayout, actionView);
        trainingList.setAdapter(ttAdapter);

        //设置底部操作栏
        if (trainingToday.isRestDay()){
            startTraining.setText("今日休息");
        }else {
            startTraining.setText("开始训练");
            startTraining.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ttAdapter.setWritingItem(0);
                    actionLayout.removeAllViews();
                    actionLayout.addView(actionView);
                    trainingList.smoothScrollToPosition(ttAdapter.getWritingItem() + 2);
                }
            });
            if (savedInstanceState!=null) {
                if (savedInstanceState.getInt(WRITING_ITEM) > -1) {
                    ttAdapter.setWritingItem(savedInstanceState.getInt(WRITING_ITEM));
                    actionLayout.removeAllViews();
                    actionLayout.addView(actionView);
                }
            }
            actionView.setClickable(true);//拦截点击事件，否则会点中后面的View
            buttonDone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean have = false;
                    out: for (int i = 0; i < trainingToday.numberOfSets(); i++) {
                        for(int j = 0; j < trainingToday.getSets(i).numberOfSingleSets(); j++) {
                            if (trainingToday.getSets(i).getSet(j).getReps() != 0) {
                                have = true;
                                break out;}}}
                    if (have){//判断是否有记录
                        openFinish1Dialog();
                    }else {
                        openFinish2Dialog();
                    }
                }
            });
            buttonPrevious.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int item = ttAdapter.getWritingItem();
                    if (item > 0) {
                        ttAdapter.setWritingItem(item - 1);
                        trainingList.smoothScrollToPosition(ttAdapter.getWritingItem() + 2);
                    }
                }
            });
            buttonNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int item = ttAdapter.getWritingItem();
                    if (item < (trainingToday.numberOfSets() - 1)) {
                        ttAdapter.setWritingItem(item + 1);
                        trainingList.smoothScrollToPosition(ttAdapter.getWritingItem() + 2);
                    }
                }
            });
        }
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        DBOpenHelper.close();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        if(keyCode == KeyEvent.KEYCODE_BACK && ttAdapter.getWritingItem()>-1){
            openQuitDialog();
        }else {
            finish();
        }
        return false;
    }

    public void openFinish1Dialog(){
        if (dialog_finish1==null){
            AlertDialog.Builder builder = new AlertDialog.Builder(TrainingTodayActivity.this);
            builder.setTitle("确定结束训练并保存？")
                    .setPositiveButton("保存", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            DBOpenHelper.saveTrainingRecord(date, trainingProgram.getId(), trainingProgram.getName(), trainingToday);
                            TrainingTodayActivity.this.finish();
                        }})
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }});
            dialog_finish1 = builder.create();
        }
        dialog_finish1.show();
    }

    public void openFinish2Dialog(){
        if (dialog_finish2==null){
            AlertDialog.Builder builder = new AlertDialog.Builder(TrainingTodayActivity.this);
            builder.setTitle("本次训练还没有记录，确定要结束训练吗？")
                    .setPositiveButton("结束", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            TrainingTodayActivity.this.finish();
                        }})
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }});
            dialog_finish2 = builder.create();
        }
        dialog_finish2.show();
    }

    public void openQuitDialog(){
        if (dialog_quit==null){
            AlertDialog.Builder builder = new AlertDialog.Builder(TrainingTodayActivity.this);
            builder.setTitle("本次训练未保存，确定要结束吗？")
                    .setPositiveButton("结束", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            TrainingTodayActivity.this.finish();
                        }})
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }});
            dialog_quit = builder.create();
        }
        dialog_quit.show();
    }
}