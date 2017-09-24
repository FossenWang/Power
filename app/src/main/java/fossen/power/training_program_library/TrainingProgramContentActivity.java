package fossen.power.training_program_library;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
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

import java.util.Calendar;

import fossen.power.ComponentCreator;
import fossen.power.R;
import fossen.power.TPDBOpenHelper;
import fossen.power.TrainingProgram;

public class TrainingProgramContentActivity extends AppCompatActivity {
    private TPDBOpenHelper tpdbOpenHelper;
    private TrainingProgram trainingProgram;
    private ViewHolder viewHolder;

    private static class ViewHolder{
        Toolbar toolbar;
        ExpandableListView list_tpc;
        View header;
        View layout;
        TextView textTitle;
        TextView textCircleGoal;
        TextView textNote;
        ImageView arrow;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.tpc_modification, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training_program_content);

        viewHolder = new ViewHolder();
        //设置toolbar和返回键
        viewHolder.toolbar = ComponentCreator.createBackToolbar(this,R.id.toolbar_tpc);

        // 给listView添加headerView，用于显示训练方案的基本信息
        viewHolder.list_tpc = (ExpandableListView) findViewById(R.id.explist_tpc);
        viewHolder.header = getLayoutInflater().inflate(R.layout.header_training_program_content,null);
        viewHolder.list_tpc.addHeaderView(viewHolder.header);
        viewHolder.layout = findViewById(R.id.layout_tpc_header);
        viewHolder.textTitle = (TextView) findViewById(R.id.text_tpc_title);
        viewHolder.textCircleGoal = (TextView) findViewById(R.id.text_tpc_circle_goal);
        viewHolder.textNote = (TextView) findViewById(R.id.text_tpc_note);
        viewHolder.arrow = (ImageView) findViewById(R.id.arrow_tpc_note);

        //设置监听器，实现点击缩放说明栏文字
        viewHolder.layout.setOnClickListener(new View.OnClickListener() {
            Boolean flag = true;
            @Override
            public void onClick(View v) {
                if(flag){
                    flag = false;
                    viewHolder.textNote.setMaxLines(30);
                    viewHolder.arrow.setRotation(180);
                }else {
                    flag = true;
                    viewHolder.textNote.setMaxLines(1);
                    viewHolder.arrow.setRotation(0);
                }
            }
        });

        //设置监听器，长按弹出修改对话框
        viewHolder.layout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(TrainingProgramContentActivity.this, "长按该视图弹出修改对话窗", Toast.LENGTH_SHORT).show();
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
        viewHolder.textTitle.setText(trainingProgram.getName());
        viewHolder.textCircleGoal.setText(trainingProgram.getCircleGoal() );
        viewHolder.textNote.setText(trainingProgram.getNote().replace("\\n","\n"));

        //绑定配适器
        final TrainingProgramContentAdapter tpcAdapter =
                new TrainingProgramContentAdapter(trainingProgram, tpdbOpenHelper, this);
        viewHolder.list_tpc.setAdapter(tpcAdapter);

        //设置修改模式按钮
        viewHolder.toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent intent = new Intent(TrainingProgramContentActivity.this,TrainingProgramModifyActivity.class);
                intent.putExtra("id",trainingProgram.getId());
                TrainingProgramContentActivity.this.startActivity(intent);
                Toast.makeText(TrainingProgramContentActivity.this, item.getTitle(), Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }
}