package fossen.power.training_program_library;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;

import java.util.ArrayList;

import fossen.power.DBOpenHelper;
import fossen.power.Exercise;
import fossen.power.R;
import fossen.power.Sets;

public class ChooseExerciseActivity extends AppCompatActivity {

    private ExpandableListView explist_ce;
    private Button button_cancel;
    private Button button_yes;
    private DBOpenHelper dbOpenHelper;
    private Intent result;
    private Sets sets;
    private ArrayList<String> checkedExercises;
    private ArrayList<ArrayList<Exercise>> sortedExercises;

    private final String CHECKED_EXERCISES = "checkedExercises";
    private final String SETS = "sets";

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        String[] names = new String[checkedExercises.size()];
        for(int i = 0; i<checkedExercises.size(); i++){
            names[i] = checkedExercises.get(i);
        }
        sets.setExerciseList(names);
        outState.putSerializable(SETS, sets);
    }

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
        if (savedInstanceState==null) {
            sets = (Sets) result.getSerializableExtra(SETS);
        }else {
            sets = (Sets) savedInstanceState.getSerializable(SETS);
        }
        dbOpenHelper = new DBOpenHelper(this);
        sortedExercises = dbOpenHelper.inputExercises(sets.getExerciseType());
        checkedExercises = new ArrayList<>();
        for (Exercise exercise : sets.getExerciseList()) {
            checkedExercises.add(exercise.getName());
        }

        //绑定配适器
        ChooseExerciseAdapter ceAdapter = new ChooseExerciseAdapter(sortedExercises, checkedExercises, this);
        explist_ce.setAdapter(ceAdapter);

        //确认修改后的数据，传回上一个Activity
        button_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                result = new Intent();
                String[] names = new String[checkedExercises.size()];
                for(int i = 0; i<checkedExercises.size(); i++){
                    names[i] = checkedExercises.get(i);
                }
                sets.setExerciseList(names);
                result.putExtra(SETS, sets);
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
    protected void onStart() {
        super.onStart();
        for (Exercise exercise : sets.getExerciseList()) {
            for (int i = 0; i < sortedExercises.size(); i++) {
                if (exercise.getSort().equals(sortedExercises.get(i).get(0).getSort())) {
                    explist_ce.expandGroup(i);
                    break;}}}
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        dbOpenHelper.close();
    }
}
