package fossen.power.exercise_library;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;

import java.util.ArrayList;

import fossen.power.ComponentCreator;
import fossen.power.DBOpenHelper;
import fossen.power.Exercise;
import fossen.power.R;

public class ExerciseLibraryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_library);

        //设置toolbar和返回键
        ComponentCreator.createBackToolbar(this,R.id.toolbar_el);

        //从数据库导入动作分类数据
        Intent intent = getIntent();
        String type = intent.getStringExtra("type_name");
        DBOpenHelper dbOpenHelper = new DBOpenHelper(this);
        final ArrayList<ArrayList<Exercise>> sortedExercises = dbOpenHelper.inputExercises(type);

        //设置配适器
        ExpandableListView expListView = (ExpandableListView) findViewById(R.id.explist_el);
        ExerciseLibraryAdapter elAdapter = new ExerciseLibraryAdapter(sortedExercises, this);
        expListView.setAdapter(elAdapter);

        //为列表设置点击事件
        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                //进入动作形式Activity
                Intent intent = new Intent(ExerciseLibraryActivity.this,ExerciseFormActivity.class);
                intent.putExtra("name",sortedExercises.get(groupPosition).get(childPosition).getName());
                startActivity(intent);
                return true;
            }
        });
    }
}
