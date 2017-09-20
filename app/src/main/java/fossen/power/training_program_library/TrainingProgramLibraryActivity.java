package fossen.power.training_program_library;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import fossen.power.R;
import fossen.power.TPDBOpenHelper;
import fossen.power.TrainingDay;
import fossen.power.TrainingProgram;

public class TrainingProgramLibraryActivity extends AppCompatActivity {

    private TPDBOpenHelper tpdbOpenHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training_program_library);


        //设置返回键
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_tpl);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //加载训练方案列表
        tpdbOpenHelper = new TPDBOpenHelper(this);
        ArrayList<TrainingProgram> programs = tpdbOpenHelper.inputTrainingProgramList();

        //绑定配适器
        ListView listView =(ListView) findViewById(R.id.list_tpl);
        TrainingProgramLibraryAdapter tplAdapter =
                new TrainingProgramLibraryAdapter(programs,tpdbOpenHelper,this);
        listView.setAdapter(tplAdapter);

    }
}
