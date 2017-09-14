package fossen.power.exercise_library;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ExpandableListView;

import java.util.ArrayList;

import fossen.power.Exercise;
import fossen.power.R;

public class ExerciseLibraryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_library);

        ArrayList<String> sort = new ArrayList<String>();
        sort.add("自重");
        sort.add("器械");
        sort.add("拉伸");
        sort.add("有氧");
        ArrayList<ArrayList<Exercise>> exerList = new ArrayList<ArrayList<Exercise>>();
        ArrayList<Exercise> exer = new ArrayList<Exercise>();
        exer.add(new Exercise("引体向上"));
        exer.add(new Exercise("俯卧撑"));
        

        ExpandableListView expListView = (ExpandableListView) findViewById(R.id.explist_el);
        ExerciseLibraryAdapter elAdapter = new ExerciseLibraryAdapter(sort,exerList,this);

    }
}
