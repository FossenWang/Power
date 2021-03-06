package fossen.power.training_program_library;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;

import fossen.power.ComponentCreator;
import fossen.power.R;
import fossen.power.DBOpenHelper;
import fossen.power.TrainingProgram;

public class TrainingProgramLibraryActivity extends AppCompatActivity {
    private ArrayList<TrainingProgram> programs;
    private DBOpenHelper DBOpenHelper;
    private TrainingProgramLibraryAdapter tplAdapter;

    private ListView listView;
    private Toolbar toolbar;

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.tpl, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training_program_library);

        listView =(ListView) findViewById(R.id.list_tpl);

        //设置toolbar和返回键
        toolbar = ComponentCreator.createBackToolbar(this,R.id.toolbar_tpl);

        //设置添加训练方案的按钮
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent intent = new Intent(TrainingProgramLibraryActivity.this,TrainingProgramModifyActivity.class);
                intent.putExtra("id", DBOpenHelper.createTrainingProgram());
                TrainingProgramLibraryActivity.this.startActivity(intent);
                return true;
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        //加载训练方案列表
        if(DBOpenHelper == null){
            DBOpenHelper = new DBOpenHelper(this);
        }
        programs = DBOpenHelper.inputTrainingProgramList();

        //绑定配适器
        tplAdapter = new TrainingProgramLibraryAdapter(programs, DBOpenHelper,this);
        listView.setAdapter(tplAdapter);
    }

    @Override
    protected void onStop(){
        super.onStop();
        DBOpenHelper.close();
    }
}
