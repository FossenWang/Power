package fossen.power.exercise_library;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.Toast;

import java.util.ArrayList;

import fossen.power.Exercise;
import fossen.power.MainActivity;
import fossen.power.R;

public class ExerciseLibraryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_library);

        //准备数据
        ArrayList<String> sort = new ArrayList<String>();
        sort.add("自重");
        sort.add("器械");
        sort.add("拉伸");
        sort.add("有氧");
        final ArrayList<ArrayList<Exercise>> exerList = new ArrayList<ArrayList<Exercise>>();
        ArrayList<Exercise> exer = new ArrayList<Exercise>();
        exer.add(new Exercise("引体向上"));
        exer.add(new Exercise("俯卧撑"));
        exerList.add(exer);
        exer = new ArrayList<Exercise>();
        exer.add(new Exercise("卧推"));
        exer.add(new Exercise("硬拉"));
        exer.add(new Exercise("深蹲"));
        exerList.add(exer);
        exer = new ArrayList<Exercise>();
        exer.add(new Exercise("胸肌拉伸"));
        exer.add(new Exercise("背部拉伸"));
        exer.add(new Exercise("股四头拉伸"));
        exerList.add(exer);
        exer = new ArrayList<Exercise>();
        exer.add(new Exercise("跳箱子"));
        exer.add(new Exercise("跳绳"));
        exer.add(new Exercise("战绳"));
        exerList.add(exer);

        ExpandableListView expListView = (ExpandableListView) findViewById(R.id.explist_el);
        ExerciseLibraryAdapter elAdapter = new ExerciseLibraryAdapter(sort,exerList,this);
        expListView.setAdapter(elAdapter);
        //为列表设置点击事件
        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Toast.makeText(ExerciseLibraryActivity.this,
                        "你点击了：" + exerList.get(groupPosition).get(childPosition).getName(),
                        Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }
}
