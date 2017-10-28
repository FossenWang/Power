package fossen.power.exercise_library;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;

import fossen.power.ComponentCreator;
import fossen.power.ELDBOpenHelper;
import fossen.power.R;

public class ExerciseTypesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_types);

        //设置toolbar和返回键
        ComponentCreator.createBackToolbar(this, R.id.toolbar_et);

        //从数据库导入动作分类数据
        ELDBOpenHelper eldbOpenHelper = new ELDBOpenHelper(this);
        final ArrayList<HashMap<String,String>> types = eldbOpenHelper.inputExerciseTypes();

        //设置配适器
        ListView typeList = (ListView) findViewById(R.id.list_et);
        ExerciseTypesAdapter etAdapter = new ExerciseTypesAdapter(types, this);
        typeList.setAdapter(etAdapter);

        //为列表设置点击事件
        typeList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //进入动作库Activity
                Intent intent = new Intent(ExerciseTypesActivity.this,ExerciseLibraryActivity.class);
                intent.putExtra("type_name",types.get(position).get("type_name"));
                intent.putExtra("sort",types.get(position).get("sort"));
                startActivity(intent);
            }
        });
    }
}
