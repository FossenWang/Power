package fossen.power.training_program_library;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.Toast;

import java.util.ArrayList;

import fossen.power.ELDBOpenHelper;
import fossen.power.Exercise;
import fossen.power.R;

public class ChooseExerciseActivity extends AppCompatActivity {

    private ExpandableListView explist_ce;
    private Button button_cancel;
    private Button button_yes;
    private String names;
    private ArrayList<String> checkedExercises = new ArrayList<>();
    private ELDBOpenHelper eldbOpenHelper;
    private Intent result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_exercise);
        button_cancel = (Button) findViewById(R.id.button_ce_cancel);
        button_yes = (Button) findViewById(R.id.button_ce_yes);
        explist_ce = (ExpandableListView) findViewById(R.id.explist_ce);

        //准备数据
        result = getIntent();
        setResult(RESULT_CANCELED, result);
        names = result.getStringExtra("names");
        if(!names.equals("")) {
            for(String name: names.split(",")) {
                checkedExercises.add(name);
            }
        }
        ArrayList<String> sort = new ArrayList<String>();
        sort.add("自重");
        sort.add("器械");
        sort.add("拉伸");
        eldbOpenHelper = new ELDBOpenHelper(this);
        ArrayList<ArrayList<Exercise>> exerciseList = eldbOpenHelper.inputExercises();

        //绑定配适器
        ChooseExerciseAdapter ceAdapter = new ChooseExerciseAdapter(sort,exerciseList,checkedExercises,this);
        explist_ce.setAdapter(ceAdapter);

        //确认修改后的数据，传回上一个Activity
        button_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                result = new Intent();
                String names = "";
                for(String exercise: checkedExercises){
                    names += exercise + ",";
                }
                result.putExtra("names", names);
                setResult(RESULT_OK, result);
                ChooseExerciseActivity.this.finish();
            }
        });

        //取消返回上一个活动
        button_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChooseExerciseActivity.this.finish();
            }
        });
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        eldbOpenHelper.close();
    }
}
