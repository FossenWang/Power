package fossen.power;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2017/9/15.
 */

public class ELDBOpenHelper extends SQLiteOpenHelper {
    public ELDBOpenHelper(Context context, String name,
                          SQLiteDatabase.CursorFactory factory, int version){
        super(context, "exercise_library.db", null, 1);
    }
    @Override
    //数据库第一次创建时被调用
    public void onCreate(SQLiteDatabase db) {}
    //软件版本号发生改变时调用
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}
}
