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
    public final static String ELDB_NAME = "exercise_library.db";//动作数据库的名字
    public final static String TABLE_TYPES = "types";
    public final static String FIELD_ID = "id";
    public final static String FIELD_NAME = "name";
    public final static String FIELD_SORT = "sort";
    public final static String FIELD_TYPE_NAME = "type_name";
    public final static String FIELD_TYPE_CHINESE = "type_chinese";

    public ELDBOpenHelper(Context context){
        super(context, ELDB_NAME, null, 1);
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
    public ArrayList<HashMap<String,String>> inputExerciseTypes(){
        SQLiteDatabase eldb = this.getReadableDatabase();
        ArrayList<HashMap<String,String>> types = new ArrayList<>();
        HashMap<String,String> map;
        Cursor cursor = eldb.rawQuery("SELECT * FROM "+ TABLE_TYPES +" ORDER BY " + FIELD_ID,null);
        while (cursor.moveToNext()){
            map = new HashMap<>();
            map.put(FIELD_TYPE_NAME, cursor.getString(cursor.getColumnIndex(FIELD_TYPE_NAME)));
            map.put(FIELD_TYPE_CHINESE, cursor.getString(cursor.getColumnIndex(FIELD_TYPE_CHINESE)));
            map.put(FIELD_SORT, cursor.getString(cursor.getColumnIndex(FIELD_SORT)));
            types.add(map);
        }
        cursor.close();
        return types;
    }

    //从数据库导入某类动作的数据
    public ArrayList<ArrayList<Exercise>> inputExercises(String type){
        SQLiteDatabase eldb = this.getReadableDatabase();
        ArrayList<ArrayList<Exercise>> sortedExercises = new ArrayList<>();
        ArrayList<Exercise> exercises;
        String[] sorts = {};
        Cursor cursor = eldb.rawQuery("SELECT * FROM "+ TABLE_TYPES
                + " WHERE " + FIELD_TYPE_NAME + " = ?"
                + " ORDER BY " + FIELD_ID, new String[]{type});
        if (cursor.moveToNext()){
            sorts = cursor.getString(cursor.getColumnIndex(FIELD_SORT)).split(",");
        }
        cursor.close();
        for(String sort : sorts){
            exercises = new ArrayList<Exercise>();
            cursor = eldb.rawQuery("SELECT * FROM "+ type
                    + " WHERE " + FIELD_SORT + " = ?"
                    +" ORDER BY " + FIELD_ID, new String[]{sort});
            while (cursor.moveToNext()){
                String name = cursor.getString(cursor.getColumnIndex(FIELD_NAME));
                exercises.add(new Exercise(name, sort));
            }
            if(exercises.size()!=0){
                sortedExercises.add(exercises);
            }
            cursor.close();
        }
        return sortedExercises;
    }
}
