package fossen.power.training_program_library;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import fossen.power.ComponentCreator;
import fossen.power.R;
import fossen.power.TPDBOpenHelper;
import fossen.power.TrainingProgram;

public class TrainingProgramContentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training_program_content);

        //设置toolbar和返回键
        ComponentCreator.createBackToolbar(this,R.id.toolbar_tpc);

        //加载训练方案
        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        TPDBOpenHelper tpdbOpenHelper = new TPDBOpenHelper(this);
        TrainingProgram trainingProgram = tpdbOpenHelper.inputTrainingProgram(id);

        // 给listView添加headerView，用于显示训练方案的基本信息
        ExpandableListView list_tpc = (ExpandableListView) findViewById(R.id.explist_tpc);
        View header = getLayoutInflater().inflate(R.layout.header_training_program_content,null);
        list_tpc.addHeaderView(header);
        View layout = findViewById(R.id.layout_tpc_header);
        TextView textTitle = (TextView) findViewById(R.id.text_tpc_title);
        TextView textCircleGoal = (TextView) findViewById(R.id.text_tpc_circle_goal);
        final TextView textNote = (TextView) findViewById(R.id.text_tpc_note);
        final ImageView arrow = (ImageView) findViewById(R.id.arrow_tpc_note);
        textTitle.setText(trainingProgram.getName());
        textCircleGoal.setText(trainingProgram.getCircleGoal() );
        textNote.setText(trainingProgram.getNote().replace("\\n","\n"));

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

        layout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(TrainingProgramContentActivity.this, "长按该视图弹出修改对话窗", Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        //绑定配适器
        TrainingProgramContentAdapter tpcAdapter =
                new TrainingProgramContentAdapter(trainingProgram, tpdbOpenHelper, this);
        list_tpc.setAdapter(tpcAdapter);
    }
}
