package fossen.power;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import fossen.power.training_program_library.TrainingProgramLibraryActivity;
import fossen.power.training_today.TrainingTodayActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //设置TrainingTodayActivity的入口
        View inTrainingToday = findViewById(R.id.in_training_today);
        inTrainingToday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),TrainingTodayActivity.class);
                startActivity(intent);
            }
        });

        //设置TrainingProgramLibraryActivity的入口
        View inTrainingProgramLibrary = findViewById(R.id.in_training_program_library);
        inTrainingProgramLibrary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), TrainingProgramLibraryActivity.class);
                startActivity(intent);
            }
        });
    }
}
