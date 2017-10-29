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
import java.util.Calendar;

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
            tp.addTrainingDay(cursor.getString(cursor.getColumnIndex("day_name")).split(",",-1));
            tp.setStart(cursor.getInt(cursor.getColumnIndex("start")));
            tp.setDate(cursor.getString(cursor.getColumnIndex("date")));
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
        Cursor cursor = tpdb.rawQuery("SELECT * FROM program_list WHERE id = ?", new String[]{id});
        while (cursor.moveToNext()) {
            tp = new TrainingProgram();
            tp.setId(cursor.getString(cursor.getColumnIndex("id")));
            tp.setName(cursor.getString(cursor.getColumnIndex("name")));
            tp.setGoal(cursor.getString(cursor.getColumnIndex("goal")));
            tp.addTrainingDay(cursor.getString(cursor.getColumnIndex("day_name")).split(",",-1));
            tp.setStart(cursor.getInt(cursor.getColumnIndex("start")));
            tp.setDate(cursor.getString(cursor.getColumnIndex("date")));
            tp.setNote(cursor.getString(cursor.getColumnIndex("note")));
        }
        cursor.close();

        //导入训练方案的全部内容
        for(int day = 1; day<=tp.circleDays();day++){
            cursor = tpdb.rawQuery("SELECT * FROM "+id+" WHERE day = ? ORDER BY number",new String[]{Integer.toString(day)});
            td = tp.getTrainingDay(day-1);
            while (cursor.moveToNext()) {//有组集Sets记录则为训练日，无则为休息日
                sets = new Sets();
                sets.addSet(cursor.getInt(cursor.getColumnIndex("sets")));
                sets.setExerciseList(cursor.getString(cursor.getColumnIndex("exercise")).split(","));
                sets.setRest(cursor.getInt(cursor.getColumnIndex("rest")));
                sets.setRepmax(cursor.getString(cursor.getColumnIndex("reps")));
                sets.setStructure(cursor.getString(cursor.getColumnIndex("structure")));
                td.addSets(sets);
            }
            cursor.close();
        }
        return tp;
    }

    //从数据库导入某个方案的某个训练日的数据
    public TrainingDay inputTrainingDay(String id, String dayName, int day){
        TrainingDay td;
        Sets sets;
        SQLiteDatabase tpdb = this.getReadableDatabase();

        Cursor cursor = tpdb.rawQuery("SELECT * FROM "+id+" WHERE day = ? ORDER BY number",new String[]{Integer.toString(day)});
        td = new TrainingDay(dayName);
        while (cursor.moveToNext()) {//有组集Sets记录则为训练日，无则为休息日
            sets = new Sets();
            sets.addSet(cursor.getInt(cursor.getColumnIndex("sets")));
            sets.setExerciseList(cursor.getString(cursor.getColumnIndex("exercise")).split(","));
            sets.setRest(cursor.getInt(cursor.getColumnIndex("rest")));
            sets.setRepmax(cursor.getString(cursor.getColumnIndex("reps")));
            sets.setStructure(cursor.getString(cursor.getColumnIndex("structure")));
            td.addSets(sets);
        }
        cursor.close();
        return td;
    }

    //更新训练方案的使用状态
    public void updateStartDate(TrainingProgram trainingProgram){
        SQLiteDatabase tpdb = this.getWritableDatabase();
        String start = Integer.toString(trainingProgram.getStart());
        String date = trainingProgram.getDate();
        String id = trainingProgram.getId();
        tpdb.execSQL("UPDATE program_list " +
                        "SET start = ?, date = ? WHERE id = ?",
                new String[]{start,date,id});
    }

    //更新组集中的动作
    public void updateExercise(String programId, int day, int number, Sets sets){
        SQLiteDatabase tpdb = this.getWritableDatabase();
        String names = "";
        for(Exercise exercise : sets.getExerciseList()){
            names += (exercise.getName() + ",");
        }
        names = names.substring(0,names.length()-1);
        tpdb.execSQL("UPDATE " + programId + " SET exercise = ?"
                        + " WHERE day = ? AND number = ?", new String[]{names,day+"",number+""});
    }

    //保存更新的训练计划
    public void updateTrainingProgram(TrainingProgram trainingProgram){
        SQLiteDatabase tpdb = this.getWritableDatabase();
        String name = trainingProgram.getName();
        String goal = trainingProgram.getGoal();
        String note = trainingProgram.getNote();
        String start = Integer.toString(trainingProgram.getStart());
        String id = trainingProgram.getId();
        String dayNames = "";
        for(int i = 0; i < trainingProgram.circleDays(); i++){
            dayNames += (trainingProgram.getTrainingDay(i).getTitle() + ",");
        }
        dayNames = dayNames.substring(0,dayNames.length()-1);

        //更新program_list
        tpdb.execSQL("UPDATE program_list " +
                        "SET name = ?, goal = ?, note = ?, day_name = ?, start = ? " +
                        "WHERE id = ?",
                new String[]{name, goal, note, dayNames, start,id});

        //更新训练日id表单，先删除所有记录，再写入所有记录
        tpdb.execSQL("DELETE FROM " + id);
        for(int day = 1; day<=trainingProgram.circleDays();day++){
            TrainingDay trainingDay = trainingProgram.getTrainingDay(day-1);
            if(!trainingDay.isRestDay()){  //不保存休息日
                for(int i = 0; i<trainingDay.numberOfSets(); i++) {
                    Sets sets = trainingDay.getSets(i);
                    if (sets.getExerciseList().size() != 0) {//有动作才保存
                        String names = "";
                        for (Exercise exercise : sets.getExerciseList()) {
                            names += (exercise.getName() + ",");
                        }
                        names = names.substring(0, names.length() - 1);
                        String[] values = {
                                Integer.toString(day),
                                Integer.toString(i + 1),
                                names,
                                Integer.toString(sets.numberOfSingleSets()),
                                sets.getRepmax(),
                                Integer.toString(sets.getRest()),
                                sets.getStructure()
                        };
                        tpdb.execSQL("INSERT INTO " + id + " VALUES (?,?,?,?,?,?,?)", values);
                    }
                }
            }
        }
    }

    //创建新的训练计划
    public String createTrainingProgram(){
        SQLiteDatabase tpdb = this.getWritableDatabase();
        String name = "新建训练计划";
        String id = "tp" + Calendar.getInstance().getTimeInMillis();
        tpdb.execSQL("INSERT INTO program_list VALUES (?,?,'','',0,20170101,'')",
                new String[]{id, name});
        tpdb.execSQL("CREATE TABLE " + id + "(day INTEGER, " +
                "number INTEGER, " +
                "exercise TEXT, " +
                "sets INTEGER, " +
                "reps TEXT, " +
                "rest INTEGER, " +
                "structure INTEGER)");
    return id;
    }

    //删除指定训练计划
    public void deleteTrainingProgram(String id){
        SQLiteDatabase tpdb = this.getWritableDatabase();
        tpdb.execSQL("DROP TABLE " + id);
        tpdb.execSQL("DELETE FROM program_list WHERE id = ?", new String[]{id});
    }

    //取出所有有训练记录的日期
    public ArrayList<String> inputTrainingCalendar(){
        SQLiteDatabase tpdb = this.getWritableDatabase();
        ArrayList<String> calendar = new ArrayList<>();
        Cursor cursor = tpdb.rawQuery("SELECT date FROM log_catalog ORDER BY date DESC", null);
        int i = -1;
        while (cursor.moveToNext()){
            if(i==-1||!calendar.get(i).equals(cursor.getString(cursor.getColumnIndex("date")))){
                calendar.add(cursor.getString(cursor.getColumnIndex("date")));
                i++;
            }
        }
        cursor.close();
        return calendar;
    }

    //导入某日的训练日志目录
    //训练记录保存在TrainingProgram内，有且只有一个TrainingDay
    public ArrayList<TrainingProgram> inputTrainingLog(String date){
        SQLiteDatabase tpdb = this.getWritableDatabase();
        ArrayList<TrainingProgram> catalog = new ArrayList<>();
        Cursor cursor = tpdb.rawQuery(
                "SELECT * FROM log_catalog WHERE date = ?", new String[]{date});
        while (cursor.moveToNext()){
            TrainingProgram record = new TrainingProgram();
            record.setId(cursor.getString(cursor.getColumnIndex("program_id")));
            record.setName(cursor.getString(cursor.getColumnIndex("program_name")));
            record.addTrainingDay(new TrainingDay(cursor.getString(cursor.getColumnIndex("day_name"))));
            record.setDate(date);
            catalog.add(record);
        }
        cursor.close();
        return catalog;
    }

    //读取某天某个方案的训练记录,该方法新建一个训练日，用于训练日志部分训练
    //训练记录保存在TrainingProgram内，有且只有一个TrainingDay
    public void inputTrainingRecord(TrainingProgram record){
        SQLiteDatabase tpdb = this.getWritableDatabase();
        String id = record.getId();
        String date = record.getDate();
        Cursor cursor = tpdb.rawQuery("SELECT * FROM tl" +date+ " WHERE program_id = ? ORDER BY number", new String[]{id});
        Sets sets;
        while (cursor.moveToNext()) {
            sets = new Sets();
            sets.setExerciseList(cursor.getString(cursor.getColumnIndex("exercise")).split(","));
            sets.setStructure(cursor.getString(cursor.getColumnIndex("structure")));
            String[] detail = cursor.getString(cursor.getColumnIndex("record")).split(",");
            for (String str : detail){
                double load = Double.parseDouble(str.split("×")[0]);
                int reps = Integer.parseInt(str.split("×")[1]);
                sets.addSet(new SingleSet(load, reps));
            }
            record.getTrainingDay(0).addSets(sets);
        }
        cursor.close();
    }

    //读取某天某个方案的训练记录，该方法将记录合并至训练日，用于今日训练部分
    public void inputTrainingRecord(String date, TrainingProgram program, TrainingDay trainingDay){
        SQLiteDatabase tpdb = this.getWritableDatabase();
        String id = program.getId();
        Cursor cursor = tpdb.rawQuery(
                "SELECT * FROM log_catalog WHERE date = ? AND program_id = ?",
                new String[]{date,id});
        if(cursor.moveToNext()){
            cursor.close();
            cursor = tpdb.rawQuery("SELECT * FROM tl" +date+ " WHERE program_id = ? ORDER BY number", new String[]{id});
            int i = 0;
            while (cursor.moveToNext()) {
                int index = cursor.getInt(cursor.getColumnIndex("number")) - 1;
                String[] detail = cursor.getString(cursor.getColumnIndex("record")).split(",");
                int j= 0;
                for (String str : detail){
                    double load = Double.parseDouble(str.split("×")[0]);
                    int reps = Integer.parseInt(str.split("×")[1]);
                    trainingDay.getSets(index).replaceSet(j, new SingleSet(load,reps));
                    j++;
                }
                i++;
            }
            cursor.close();
        }
    }

    //保存训练记录
    public void saveTrainingRecord(String date, TrainingProgram program, TrainingDay trainingDay){
        SQLiteDatabase tpdb = this.getWritableDatabase();
        String id = program.getId();
        Cursor cursor = tpdb.rawQuery(
                "SELECT * FROM log_catalog WHERE date = ?",
                new String[]{date});
        boolean have = false;//判断有无该日的记录无则创建记录及表单
        while (cursor.moveToNext()){
            if (id.equals(cursor.getString(cursor.getColumnIndex("program_id")))){
                have = true;
                break;
            }
        }
        if (!have){
            tpdb.execSQL("INSERT INTO log_catalog VALUES (?,?,?,?)",
                    new String[]{date, id, program.getName(), trainingDay.getTitle()});
            tpdb.execSQL("CREATE TABLE IF NOT EXISTS tl" + date +
                    " (program_id TEXT, " +
                    "number INTEGER, " +
                    "exercise TEXT, " +
                    "record TEXT, " +
                    "structure TEXT)");
        }
        cursor.close();
        //更新该日的记录
        tpdb.execSQL("DELETE FROM tl" + date + " WHERE program_id = ?",new String[]{id});
        if(!trainingDay.isRestDay()){  //不保存休息日
            for(int i = 0; i<trainingDay.numberOfSets(); i++) {
                Sets sets = trainingDay.getSets(i);
                if (!sets.getAllSetsToFormat("kg").equals("暂无")) {//有记录才保存
                    String record = "";
                    for(int j = 0; j < sets.numberOfSingleSets(); j++){
                        if(sets.getSet(j).getReps() != 0) {
                            record += sets.getSet(j).getLoad() +"×"+ sets.getSet(j).getReps() + ",";
                        }
                    }
                    String[] values = {
                            id,
                            Integer.toString(i + 1),
                            sets.getExercise(0).getName(),
                            record,
                            sets.getStructure()
                    };
                    tpdb.execSQL("INSERT INTO tl" + date + " VALUES (?,?,?,?,?)", values);
                }
            }
        }
    }

    //删除一条训练记录
    public void deleteTrainingRecord(String date, String id){
        SQLiteDatabase tpdb = this.getWritableDatabase();
        tpdb.execSQL("DELETE FROM tl" + date + " WHERE program_id = ?", new String[]{id});
        tpdb.execSQL("DELETE FROM log_catalog WHERE date = ? AND program_id = ?", new String[]{date,id});
    }
}
