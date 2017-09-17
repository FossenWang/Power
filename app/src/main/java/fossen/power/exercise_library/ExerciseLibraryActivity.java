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

import fossen.power.ELDBOpenHelper;
import fossen.power.Exercise;
import fossen.power.R;

public class ExerciseLibraryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_library);

        //设置返回键
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_el);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //准备数据
        ArrayList<String> sort = new ArrayList<String>();
        sort.add("自重");
        sort.add("器械");
        sort.add("拉伸");

        //从数据库导入动作分类数据
        ELDBOpenHelper eldbOpenHelper = new ELDBOpenHelper(this);
        eldbOpenHelper.getWritableDatabase().close();
        final ArrayList<ArrayList<Exercise>> exerLists = eldbOpenHelper.inputExercises();

        //设置配适器
        ExpandableListView expListView = (ExpandableListView) findViewById(R.id.explist_el);
        ExerciseLibraryAdapter elAdapter = new ExerciseLibraryAdapter(sort,exerLists,this);
        expListView.setAdapter(elAdapter);

        //为列表设置点击事件
        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                //进入动作形式Activity
                Intent intent = new Intent(ExerciseLibraryActivity.this,ExerciseFormActivity.class);
                intent.putExtra("name",exerLists.get(groupPosition).get(childPosition).getName());
                startActivity(intent);
                return true;
            }
        });
    }
}
