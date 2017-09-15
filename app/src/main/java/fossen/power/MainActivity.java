package fossen.power;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import fossen.power.exercise_library.ExerciseLibraryActivity;
import fossen.power.training_program_library.TrainingProgramLibraryActivity;
import fossen.power.training_today.TrainingTodayActivity;

public class MainActivity extends AppCompatActivity {

    public static String dbName="exercise_library.db";//数据库的名字
    private static String DATABASE_PATH="/data/data/fossen.power/databases/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //设置TrainingTodayActivity的入口
        View inTrainingToday = findViewById(R.id.in_training_today);
        inTrainingToday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), TrainingTodayActivity.class);
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

        //设置ExerciseLibraryActivity的入口
        View inExerciseLibrary = findViewById(R.id.in_exercise_library);
        inExerciseLibrary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ExerciseLibraryActivity.class);
                startActivity(intent);
            }
        });

        //判断数据库是否存在
        boolean dbExist = checkDataBase();
        if (dbExist) {
        } else {//不存在就把raw里的数据库写入手机
            try {
                copyDataBase();
            } catch (IOException e) {
                throw new Error("Error copying database");
            }
        }
    }

    //检查数据库是否存在，返回布尔值
    public boolean checkDataBase() {
        SQLiteDatabase checkDB = null;
        try {
            String databaseFilename = DATABASE_PATH + dbName;
            checkDB = SQLiteDatabase.openDatabase(databaseFilename, null,
                    SQLiteDatabase.OPEN_READONLY);
        } catch (SQLiteException e) {
        }
        if (checkDB != null) {
            checkDB.close();
        }
        return checkDB != null ? true : false;
    }

    //复制数据库到文件夹
    public void copyDataBase() throws IOException{
        String databaseFilename = DATABASE_PATH+dbName;
        File dir = new File(DATABASE_PATH);
        if(!dir.exists())//判断文件夹是否存在，不存在就新建一个
            dir.mkdir();
        FileOutputStream output = null;
        try{
            output = new FileOutputStream(databaseFilename);//得到数据库文件的写入流
        }catch(FileNotFoundException e){
            e.printStackTrace();
        }
        InputStream input = this.getResources().openRawResource(R.raw.exercise_library);//得到数据库文件的数据流
        byte[] buffer = new byte[8192];
        int count = 0;
        try{
            while((count=input.read(buffer))>0){
                output.write(buffer, 0, count);
                output.flush();
            }
        }catch(IOException e){}
        try{
            input.close();
            output.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
