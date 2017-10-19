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
import java.util.HashMap;

/**
 * Created by Administrator on 2017/9/15.
 */

public class ELDBOpenHelper extends SQLiteOpenHelper {
    private Context elContext;
    private final static String ELDB_Name = "exercise_library.db";//动作数据库的名字

    public ELDBOpenHelper(Context context){
        super(context, ELDB_Name, null, 1);
        elContext = context;
    }

    @Override
    //数据库第一次创建时被调用
    public void onCreate(SQLiteDatabase db) {
        //导入SQL文件中的语句，直接生成数据库
        InputStream input = elContext.getResources().openRawResource(R.raw.exercise_library);
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

    //从数据库导入所有动作类别
    public ArrayList<HashMap<String,String>> inputExerciseSort(){
        SQLiteDatabase eldb = this.getReadableDatabase();
        ArrayList<HashMap<String,String>> sort = new ArrayList<>();
        HashMap<String,String> map;
        Cursor cursor = eldb.rawQuery("SELECT * FROM sort ORDER BY number",null);
        while (cursor.moveToNext()){
            map = new HashMap<>();
            map.put("sort_name", cursor.getString(cursor.getColumnIndex("sort_name")));
            map.put("sort_chinese", cursor.getString(cursor.getColumnIndex("sort_chinese")));
            sort.add(map);
        }
        cursor.close();
        return sort;
    }

    //从数据库导入某类动作的数据
    public ArrayList<ArrayList<Exercise>> inputExercises(ArrayList<HashMap<String,String>> sort){
        SQLiteDatabase eldb = this.getReadableDatabase();
        ArrayList<ArrayList<Exercise>> sortedExercises = new ArrayList<>();
        ArrayList<Exercise> exercises;
        String table;
        for(HashMap<String,String> map : sort){
            table = map.get("sort_name");
            exercises = new ArrayList<Exercise>();
            Cursor cursor = eldb.rawQuery("SELECT * FROM "+table+" ORDER BY id",null);
            while (cursor.moveToNext()){
                String name = cursor.getString(cursor.getColumnIndex("name"));
                String muscle = cursor.getString(cursor.getColumnIndex("muscle"));
                exercises.add(new Exercise(name, muscle));
            }
            sortedExercises.add(exercises);
            cursor.close();
        }
        return sortedExercises;
    }
}
