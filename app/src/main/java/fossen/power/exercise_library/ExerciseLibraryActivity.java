package fossen.power.exercise_library;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

import fossen.power.ComponentCreator;
import fossen.power.ELDBOpenHelper;
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
        ELDBOpenHelper eldbOpenHelper = new ELDBOpenHelper(this);
        ArrayList<HashMap<String,String>> sort = eldbOpenHelper.inputExerciseSort();
        final ArrayList<ArrayList<Exercise>> sortedExercises = eldbOpenHelper.inputExercises(sort);

        //设置配适器
        ExpandableListView expListView = (ExpandableListView) findViewById(R.id.explist_el);
        ExerciseLibraryAdapter elAdapter = new ExerciseLibraryAdapter(sort,sortedExercises,this);
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
