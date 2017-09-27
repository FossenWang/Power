package fossen.power.training_program_library;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import fossen.power.ComponentCreator;
import fossen.power.R;
import fossen.power.TPDBOpenHelper;
import fossen.power.TrainingProgram;

public class TrainingProgramContentActivity extends AppCompatActivity {
    private TPDBOpenHelper tpdbOpenHelper;
    private TrainingProgram trainingProgram;
    private TrainingProgramContentAdapter tpcAdapter;

    private Toolbar toolbar;
    private ExpandableListView list_tpc;
    private View header;
    private View layout;
    private TextView textTitle;
    private TextView textCircleGoal;
    private TextView textNote;
    private ImageView arrow;

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.tpc_modification, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training_program_content);

        //设置toolbar和返回键
        toolbar = ComponentCreator.createBackToolbar(this,R.id.toolbar_tpc);

        // 给listView添加headerView，用于显示训练方案的基本信息
        list_tpc = (ExpandableListView) findViewById(R.id.explist_tpc);
        header = getLayoutInflater().inflate(R.layout.header_training_program_content,null);
        list_tpc.addHeaderView(header);
        layout = findViewById(R.id.layout_tpc_header);
        textTitle = (TextView) findViewById(R.id.text_tpc_title);
        textCircleGoal = (TextView) findViewById(R.id.text_tpc_circle_goal);
        textNote = (TextView) findViewById(R.id.text_tpc_note);
        arrow = (ImageView) findViewById(R.id.arrow_tpc_note);

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

        //设置进入修改模式的按钮
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent intent = new Intent(TrainingProgramContentActivity.this,TrainingProgramModifyActivity.class);
                intent.putExtra("id",trainingProgram.getId());
                TrainingProgramContentActivity.this.startActivity(intent);
                return true;
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
        trainingProgram = tpdbOpenHelper.inputTrainingProgram(id);

        //设置列表头视图的内容
        textTitle.setText(trainingProgram.getName());
        textCircleGoal.setText(trainingProgram.getCircleGoal() );
        textNote.setText(trainingProgram.getNote().replace("\\n","\n"));

        //绑定配适器
        tpcAdapter = new TrainingProgramContentAdapter(trainingProgram, tpdbOpenHelper, this);
        list_tpc.setAdapter(tpcAdapter);
    }
}