package fossen.power.exercise_library;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

import fossen.power.R;

public class ExerciseFormActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_form);

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        TextView ef_name =(TextView) findViewById(R.id.text_ef_name);
        ef_name.setText(name);
    }
}
