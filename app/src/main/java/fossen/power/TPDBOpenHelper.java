package fossen.power;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by Administrator on 2017/9/16.
 */

public class TPDBOpenHelper extends SQLiteOpenHelper {
    private Context tpContext;

    public TPDBOpenHelper(Context context){
        super(context, "training_program.db", null, 1);
        tpContext = context;
    }

    @Override
    //数据库第一次创建时被调用
    public void onCreate(SQLiteDatabase db) {
        //导入SQL文件中的语句，直接生成数据库
        InputStream input = tpContext.getResources().openRawResource(R.raw.training_program);
        BufferedReader buffer = new BufferedReader(new InputStreamReader(input));
        String sql;
        try{
            while((sql = buffer.readLine())!=null){
                db.execSQL(sql);
            }
            input.close();
        }catch(IOException e){}
    }

    //软件版本号发生改变时调用
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}

    //从数据库导入训练方案列表数据
    public ArrayList<TrainingProgram> inputTrainingProgramList(){
        ArrayList<TrainingProgram> programs = new ArrayList<>();
        TrainingProgram tp;
        SQLiteDatabase tpdb = this.getReadableDatabase();
        Cursor cursor = tpdb.rawQuery("SELECT * FROM program_list ORDER BY id",null);
        while (cursor.moveToNext()) {
            tp = new TrainingProgram();
            String name = cursor.getString(cursor.getColumnIndex("name"));
            String goal = cursor.getString(cursor.getColumnIndex("goal"));
            int circle = cursor.getInt(cursor.getColumnIndex("circle"));
            int start = cursor.getInt(cursor.getColumnIndex("start"));
            int year = cursor.getInt(cursor.getColumnIndex("year"));
            int month = cursor.getInt(cursor.getColumnIndex("month"));
            int day = cursor.getInt(cursor.getColumnIndex("day"));
            String note = cursor.getString(cursor.getColumnIndex("note"));
            tp.setName(name);
            tp.setGoal(goal);
            tp.addTrainingDay(circle);
            tp.setStart(start!=0);
            tp.setDate(year,month,day);
            tp.setNote(note);
            programs.add(tp);
        }
        cursor.close();
        return programs;
    }

    //从数据库导入某个方案的全部数据
    public TrainingProgram inputTrainingProgram(int id, int circle, String[] day_name){
        TrainingProgram tp = new TrainingProgram();
        TrainingDay td;
        Sets sets;
        SQLiteDatabase tpdb = this.getReadableDatabase();
        for(int day = 1; day<=circle ; ){
            Cursor cursor = tpdb.rawQuery("SELECT * FROM "+id+" ORDER BY order WHERE day="+day,null);
            td = new TrainingDay();
            td.setTitle(day_name[day-1]);
            while (cursor.moveToNext()) {
                sets = new Sets();
                sets.addSet(cursor.getInt(cursor.getColumnIndex("sets")));
                sets.setExerciseList(cursor.getString(cursor.getColumnIndex("exercise")).split(","));
                sets.setRest(cursor.getInt(cursor.getColumnIndex("rest")));
                sets.setRepmax(cursor.getString(cursor.getColumnIndex("repmax")));
                sets.setStructure(cursor.getString(cursor.getColumnIndex("structure")));
                td.addSets(sets);
            }
            cursor.close();
            tp.addTrainingDay(td);
        }
        return tp;
    }
}
