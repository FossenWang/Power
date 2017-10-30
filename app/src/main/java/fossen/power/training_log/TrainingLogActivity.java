package fossen.power.training_log;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;

import fossen.power.ComponentCreator;
import fossen.power.R;
import fossen.power.DBOpenHelper;

public class TrainingLogActivity extends AppCompatActivity {
    private ExpandableListView expList;
    private TrainingLogAdapter tlAdapter;
    private DBOpenHelper DBOpenHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training_log);

        ComponentCreator.createBackToolbar(this, R.id.toolbar_tl);

        DBOpenHelper = new DBOpenHelper(this);
        expList = (ExpandableListView) findViewById(R.id.explist_tl);
        tlAdapter = new TrainingLogAdapter(this, expList, DBOpenHelper);
        expList.setAdapter(tlAdapter);
        expList.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                return true;
            }
        });
        expList.expandGroup(0);
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        DBOpenHelper.close();
    }
}
