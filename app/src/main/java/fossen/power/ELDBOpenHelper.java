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
 * Created by Administrator on 2017/9/15.
 */

public class ELDBOpenHelper extends SQLiteOpenHelper {

    private Context elContext;
    private static String eldbName="exercise_library.db";//动作数据库的名字

    public ELDBOpenHelper(Context context){
        super(context, eldbName, null, 1);
        elContext = context;
    }

    @Override
    //数据库第一次创建时被调用
    public void onCreate(SQLiteDatabase db) {
        //导入SQL文件中的语句，直接生成数据库
        InputStream input = elContext.getResources().openRawResource(R.raw.exercise_library);
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

    //从数据库导入动作分类数据
    public ArrayList<ArrayList<Exercise>> inputExercises(){
        //ELDBOpenHelper eldbOpenHelper = new ELDBOpenHelper(this);
        SQLiteDatabase eldb = this.getReadableDatabase();
        ArrayList<ArrayList<Exercise>> exerLists = new ArrayList<>();
        ArrayList<Exercise> exerList;
        for(String table : new String[]{"bodyweight","equipment","stretching"}){
            exerList = new ArrayList<Exercise>();
            Cursor cursor = eldb.rawQuery("SELECT * FROM "+table+" ORDER BY id",null);
            while (cursor.moveToNext()){
                String name = cursor.getString(cursor.getColumnIndex("name"));
                String muscle = cursor.getString(cursor.getColumnIndex("muscle"));
                exerList.add(new Exercise(name, muscle));
            }
            exerLists.add(exerList);
            cursor.close();
        }
        return exerLists;
    }
}
