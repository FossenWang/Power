package fossen.power.training_program_library;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

import fossen.power.ComponentCreator;
import fossen.power.R;
import fossen.power.TPDBOpenHelper;
import fossen.power.TrainingDay;
import fossen.power.TrainingProgram;

public class TrainingProgramLibraryActivity extends AppCompatActivity {
    private ArrayList<TrainingProgram> programs;
    private TPDBOpenHelper tpdbOpenHelper;
    private TrainingProgramLibraryAdapter tplAdapter;
    private ViewHolder viewHolder;

    private static class ViewHolder {
        ListView listView;
        Toolbar toolbar;
        View dialogView;
        EditText editTitle;
        EditText editGoal;
        EditText editNote;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.tpl, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training_program_library);

        viewHolder = new ViewHolder();
        viewHolder.listView =(ListView) findViewById(R.id.list_tpl);

        //设置toolbar和返回键
        viewHolder.toolbar = ComponentCreator.createBackToolbar(this,R.id.toolbar_tpl);

        //设置添加训练方案的按钮，弹出对话框
        viewHolder.toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            private TrainingProgram tp = new TrainingProgram();
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                tp.setName("新建训练方案");
                tp.addTrainingDay(new String[]{""});
                tp.addTrainingDay(new String[]{""});
                tp.addTrainingDay("".split(","));///////////!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                tp.setId(tpdbOpenHelper.createTrainingProgram(tp));
                Intent intent = new Intent(TrainingProgramLibraryActivity.this,TrainingProgramModifyActivity.class);
                intent.putExtra("id",tp.getId());
                TrainingProgramLibraryActivity.this.startActivity(intent);
                return true;
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        //加载训练方案列表
        tpdbOpenHelper = new TPDBOpenHelper(this);
        programs = tpdbOpenHelper.inputTrainingProgramList();

        //绑定配适器
        tplAdapter = new TrainingProgramLibraryAdapter(programs,tpdbOpenHelper,this);
        viewHolder.listView.setAdapter(tplAdapter);

    }
}
