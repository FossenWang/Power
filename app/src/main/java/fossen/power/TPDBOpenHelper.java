package fossen.power;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.BufferedReader;
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
        String sql ="";
        String line;
        try{
            while((line = buffer.readLine())!=null){
                sql += line;
                if(sql.endsWith(";")){
                    db.execSQL(sql);
                    sql = "";
                }
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
            tp.setId(cursor.getString(cursor.getColumnIndex("id")));
            tp.setName(cursor.getString(cursor.getColumnIndex("name")));
            tp.setGoal(cursor.getString(cursor.getColumnIndex("goal")));
            tp.addTrainingDay(cursor.getString(cursor.getColumnIndex("day_name")).split(","));
            tp.setStart(cursor.getInt(cursor.getColumnIndex("start")));
            tp.setDate(cursor.getInt(cursor.getColumnIndex("year")),
                    cursor.getInt(cursor.getColumnIndex("month")),
                    cursor.getInt(cursor.getColumnIndex("day")));
            tp.setNote(cursor.getString(cursor.getColumnIndex("note")));
            programs.add(tp);
        }
        cursor.close();
        return programs;
    }

    //从数据库导入某个方案的全部数据
    public TrainingProgram inputTrainingProgram(String id){
        TrainingProgram tp = new TrainingProgram();
        TrainingDay td;
        Sets sets;
        SQLiteDatabase tpdb = this.getReadableDatabase();

        //导入训练方案的基本信息
        Cursor cursor = tpdb.rawQuery("SELECT * FROM program_list WHERE id=?",new String[]{id});
        while (cursor.moveToNext()) {
            tp = new TrainingProgram();
            tp.setId(cursor.getString(cursor.getColumnIndex("id")));
            tp.setName(cursor.getString(cursor.getColumnIndex("name")));
            tp.setGoal(cursor.getString(cursor.getColumnIndex("goal")));
            tp.addTrainingDay(cursor.getString(cursor.getColumnIndex("day_name")).split(","));
            tp.setStart(cursor.getInt(cursor.getColumnIndex("start")));
            tp.setDate(cursor.getInt(cursor.getColumnIndex("year")),
                    cursor.getInt(cursor.getColumnIndex("month")),
                    cursor.getInt(cursor.getColumnIndex("day")));
            tp.setNote(cursor.getString(cursor.getColumnIndex("note")));
        }
        cursor.close();

        //导入训练方案的全部内容
        for(int day = 1; day<=tp.circleDays();day++){
            cursor = tpdb.rawQuery("SELECT * FROM "+id+" WHERE day = ? ORDER BY number",new String[]{Integer.toString(day)});
            td = tp.getTrainingDay(day-1);
            while (cursor.moveToNext()) {
                sets = new Sets();
                sets.addSet(cursor.getInt(cursor.getColumnIndex("sets")));
                sets.setExerciseList(cursor.getString(cursor.getColumnIndex("exercise")).split(","));
                sets.setRest(cursor.getInt(cursor.getColumnIndex("rest")));
                sets.setRepmax(cursor.getString(cursor.getColumnIndex("reps")));
                sets.setStructure(cursor.getString(cursor.getColumnIndex("structure")));
                td.setRestDay(cursor.getInt(cursor.getColumnIndex("number"))==0);//待优化
                td.addSets(sets);
            }
            cursor.close();
        }
        return tp;
    }

    //将更改后的使用状态写入数据库
    public void outputStartDate(TrainingProgram trainingProgram){
        SQLiteDatabase tpdb = this.getReadableDatabase();
        String start = Integer.toString(trainingProgram.getStart());
        String year = Integer.toString(trainingProgram.getYear());
        String month = Integer.toString(trainingProgram.getMonth());
        String day = Integer.toString(trainingProgram.getDay());
        String id = trainingProgram.getId();
        tpdb.execSQL("UPDATE program_list " +
                        "SET start = ?, year = ?, month = ?, day = ? " +
                        "WHERE id = ?",
                new String[]{start,year,month,day,id});
    }
}
