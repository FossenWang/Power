package fossen.power.training_log;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import fossen.power.ComponentCreator;
import fossen.power.R;

public class TrainingLogActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training_log);

        ComponentCreator.createBackToolbar(this, R.id.toolbar_tl);

        ViewGroup layout = (ViewGroup) findViewById(R.id.layout_tl);
        View view = getLayoutInflater().inflate(R.layout.item_tl_day,null);
        View view1 = getLayoutInflater().inflate(R.layout.item_tl_day,null);
        View view2 = getLayoutInflater().inflate(R.layout.item_tl_day,null);
        layout.addView(view);
        layout.addView(view1);
        layout.addView(view2);
    }
}
