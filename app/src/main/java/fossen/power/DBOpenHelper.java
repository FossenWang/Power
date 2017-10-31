package fossen.power;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Administrator on 2017/9/16.
 */

public class DBOpenHelper extends SQLiteOpenHelper {
    private Context dbContext;

    public DBOpenHelper(Context context){
        super(context, "fitness.db", null, 1);
        dbContext = context;
    }

    //数据库第一次创建时被调用
    @Override
    public void onCreate(SQLiteDatabase db) {
        //导入SQL文件中的语句，直接生成数据库
        InputStream input = dbContext.getResources().openRawResource(R.raw.fitness);
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
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM program_list ORDER BY id",null);
        while (cursor.moveToNext()) {
            tp = new TrainingProgram();
            tp.setId(cursor.getInt(cursor.getColumnIndex("id")));
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
    public TrainingProgram inputTrainingProgram(int id){
        TrainingProgram tp = new TrainingProgram();
        TrainingDay td;
        Sets sets;
        SQLiteDatabase db = this.getReadableDatabase();

        //导入训练方案的基本信息
        Cursor cursor = db.rawQuery("SELECT * FROM program_list WHERE id = ?", new String[]{Integer.toString(id)});
        while (cursor.moveToNext()) {
            tp = new TrainingProgram();
            tp.setId(cursor.getInt(cursor.getColumnIndex("id")));
            tp.setName(cursor.getString(cursor.getColumnIndex("name")));
            tp.setGoal(cursor.getString(cursor.getColumnIndex("goal")));
            //训练日标题可以为空，split第二个参数为-1时，会保留最后的空字符串
            tp.addTrainingDay(cursor.getString(cursor.getColumnIndex("day_name")).split(",",-1));
            tp.setStart(cursor.getInt(cursor.getColumnIndex("start")));
            tp.setDate(cursor.getString(cursor.getColumnIndex("date")));
            tp.setNote(cursor.getString(cursor.getColumnIndex("note")));
        }
        cursor.close();

        //导入训练方案的全部内容
        for(int day = 1; day<=tp.circleDays();day++){
            cursor = db.rawQuery("SELECT * FROM programs WHERE id = ? AND day = ? ORDER BY number",
                    new String[]{Integer.toString(id), Integer.toString(day)});
            td = tp.getTrainingDay(day-1);
            while (cursor.moveToNext()) {//有组集Sets记录则为训练日，无则为休息日
                sets = new Sets();
                sets.addSet(cursor.getInt(cursor.getColumnIndex("sets")));
                sets.setExerciseList(cursor.getString(cursor.getColumnIndex("exercise")).split(","));
                sets.setRest(cursor.getInt(cursor.getColumnIndex("rest")));
                sets.setRepmax(cursor.getString(cursor.getColumnIndex("reps")));
                sets.setStructure(cursor.getString(cursor.getColumnIndex("structure")));
                String[] detail = cursor.getString(cursor.getColumnIndex("record")).split(",");
                for (int i = 0; i<detail.length; i++){
                    String[] strings = detail[i].split("×");
                    if (strings.length==2){
                        sets.getSet(i).setLoad(Double.parseDouble(strings[0]));
                        sets.getSet(i).setReps(Integer.parseInt(strings[1]));
                    }
                }
                td.addSets(sets);
            }
            cursor.close();
        }
        return tp;
    }

    //从数据库导入某个方案的某个训练日的数据
    public TrainingDay inputTrainingDay(int id, String dayName, int day){
        TrainingDay td;
        Sets sets;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM programs WHERE id = ? AND day = ? ORDER BY number",
                new String[]{Integer.toString(id),Integer.toString(day)});
        td = new TrainingDay(dayName);
        while (cursor.moveToNext()) {//有组集Sets记录则为训练日，无则为休息日
            sets = new Sets();
            sets.addSet(cursor.getInt(cursor.getColumnIndex("sets")));
            sets.setExerciseList(cursor.getString(cursor.getColumnIndex("exercise")).split(","));
            sets.setRest(cursor.getInt(cursor.getColumnIndex("rest")));
            sets.setRepmax(cursor.getString(cursor.getColumnIndex("reps")));
            sets.setStructure(cursor.getString(cursor.getColumnIndex("structure")));
            String[] detail = cursor.getString(cursor.getColumnIndex("record")).split(",");
            for (int i = 0; i<detail.length; i++){
                String[] strings = detail[i].split("×");
                if (strings.length==2){
                    sets.getSet(i).setLoad(Double.parseDouble(strings[0]));
                    sets.getSet(i).setReps(Integer.parseInt(strings[1]));
                }
            }
            td.addSets(sets);
        }
        Toast.makeText(dbContext, td.getSets(0).getAllSetsToFormat("kg"), Toast.LENGTH_SHORT).show();
        cursor.close();
        return td;
    }

    //更新训练方案的某个训练日中储存的上次训练的记录
    public void updateRecordInProgram(int id, int day, TrainingDay trainingDay){
        SQLiteDatabase db = this.getWritableDatabase();

        if(!trainingDay.isRestDay()){  //不保存休息日
            for(int i = 0; i<trainingDay.numberOfSets(); i++) {
                Sets sets = trainingDay.getSets(i);
                if (!sets.getAllSetsToFormat("kg").equals("暂无")) {//有记录才保存
                    String record = "";
                    for(int j = 0; j < sets.numberOfSingleSets(); j++){
                        if(sets.getSet(j).getReps() != 0) {
                            record += sets.getSet(j).getLoad() +"×"+ sets.getSet(j).getReps() + ",";
                        }
                    }record = record.substring(0, record.length() - 1);
                    String[] values = {
                            record,
                            Integer.toString(id),
                            Integer.toString(day),
                            Integer.toString(i + 1)
                    };
                    db.execSQL("UPDATE programs SET record = ? WHERE id = ? AND day = ? AND number = ?", values);
                    Toast.makeText(dbContext, record, Toast.LENGTH_SHORT).show();
                }
            }
        }else {
            Toast.makeText(dbContext, trainingDay.numberOfSets()+"keng", Toast.LENGTH_SHORT).show();
        }
    }

    //更新训练方案的使用状态
    public void updateStartDate(TrainingProgram trainingProgram){
        SQLiteDatabase db = this.getWritableDatabase();
        String start = Integer.toString(trainingProgram.getStart());
        String date = trainingProgram.getDate();
        int id = trainingProgram.getId();
        db.execSQL("UPDATE program_list " +
                        "SET start = ?, date = ? WHERE id = ?",
                new String[]{start,date,Integer.toString(id)});
    }

    //更新组集中的动作
    public void updateExercise(int id, int day, int number, Sets sets){
        SQLiteDatabase db = this.getWritableDatabase();
        String names = "";
        for(Exercise exercise : sets.getExerciseList()){
            names += (exercise.getName() + ",");
        }
        names = names.substring(0,names.length()-1);
        db.execSQL("UPDATE programs SET exercise = ? WHERE id = ? AND day = ? AND number = ?",
                new String[]{names,Integer.toString(id),Integer.toString(day),Integer.toString(number)});
    }

    //保存更新后的训练计划
    public void updateTrainingProgram(TrainingProgram trainingProgram){
        SQLiteDatabase db = this.getWritableDatabase();
        String name = trainingProgram.getName();
        String goal = trainingProgram.getGoal();
        String note = trainingProgram.getNote();
        String start = Integer.toString(trainingProgram.getStart());
        int id = trainingProgram.getId();
        String dayNames = "";
        for(int i = 0; i < trainingProgram.circleDays(); i++){
            dayNames += (trainingProgram.getTrainingDay(i).getTitle() + ",");
        }//训练日标题可以为空，split第二个参数为-1时，会保留最后的空字符串
        //考虑训练日全空时，仅用逗号区分训练日，则训练日数为逗号数加一
        dayNames = dayNames.substring(0,dayNames.length()-1);

        //更新program_list
        db.execSQL("UPDATE program_list SET name = ?, goal = ?, note = ?, day_name = ?, start = ? WHERE id = ?",
                new String[]{name, goal, note, dayNames, start,Integer.toString(id)});

        //更新programs，先删除所有记录，再写入所有记录
        db.execSQL("DELETE FROM programs WHERE id = ?", new String[]{Integer.toString(id)});
        for(int day = 1; day<=trainingProgram.circleDays();day++){
            TrainingDay trainingDay = trainingProgram.getTrainingDay(day-1);
            if(!trainingDay.isRestDay()){  //不保存休息日
                for(int i = 0; i<trainingDay.numberOfSets(); i++) {
                    Sets sets = trainingDay.getSets(i);
                    if (sets.getExerciseList().size() != 0) {//有动作才保存
                        String names = "";
                        for (Exercise exercise : sets.getExerciseList()) {
                            names += (exercise.getName() + ",");
                        }names = names.substring(0, names.length() - 1);
                        String record = "";
                        for(int j = 0; j < sets.numberOfSingleSets(); j++){
                            if(sets.getSet(j).getReps() != 0) {
                                record += sets.getSet(j).getLoad() +"×"+ sets.getSet(j).getReps() + ",";
                            }
                        }record = record.substring(0, record.length() - 1);
                        String[] values = {
                                Integer.toString(id),
                                Integer.toString(day),
                                Integer.toString(i + 1),
                                names,
                                Integer.toString(sets.numberOfSingleSets()),
                                sets.getRepmax(),
                                Integer.toString(sets.getRest()),
                                sets.getStructure(),
                                record
                        };
                        db.execSQL("INSERT INTO programs VALUES (?,?,?,?,?,?,?,?,?)", values);
                    }
                }
            }
        }
    }

    //创建新的训练计划
    public int createTrainingProgram(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("INSERT INTO program_list (name,goal,day_name,start,date,note) VALUES ('新建训练计划','','',0,20170101,'')");
        Cursor cursor = db.rawQuery("SELECT last_insert_rowid() FROM program_list",null);
        int id = 0;
        if (cursor.moveToNext()){
            id = cursor.getInt(0);
        }
        cursor.close();
        return id;
    }

    //删除指定训练计划
    public void deleteTrainingProgram(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM program_list WHERE id = ?", new String[]{Integer.toString(id)});
    }

    //取出所有有训练记录的日期
    public ArrayList<String> inputTrainingCalendar(){
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<String> calendar = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT date FROM log_catalog ORDER BY date DESC", null);
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
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<TrainingProgram> catalog = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM log_catalog WHERE date = ?", new String[]{date});
        while (cursor.moveToNext()){
            TrainingProgram record = new TrainingProgram();
            record.setId(cursor.getInt(cursor.getColumnIndex("program_id")));
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
        SQLiteDatabase db = this.getWritableDatabase();
        int programId = record.getId();
        int id = 0;
        String date = record.getDate();
        Cursor cursor = db.rawQuery("SELECT id FROM log_catalog WHERE date = ? AND program_id = ?",
                new String[]{date,Integer.toString(programId)});
        if (cursor.moveToFirst()){
            id = cursor.getInt(0);
        }
        cursor.close();
        cursor = db.rawQuery("SELECT * FROM logs WHERE id = ? ORDER BY number",
                new String[]{Integer.toString(id)});
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

    //保存训练记录
    public void saveTrainingRecord(String date, TrainingProgram program, TrainingDay trainingDay){
        SQLiteDatabase db = this.getWritableDatabase();
        int programId = program.getId();
        db.execSQL("INSERT INTO log_catalog (date,program_id,program_name,day_name) VALUES (?,?,?,?)",
                new String[]{date, Integer.toString(programId), program.getName(), trainingDay.getTitle()});
        Cursor cursor = db.rawQuery("SELECT last_insert_rowid() FROM log_catalog",null);
        int id = 0;
        if (cursor.moveToNext()){
            id = cursor.getInt(0);
        }
        cursor.close();
        //更新该日的记录
        db.execSQL("DELETE FROM logs WHERE id = ?",new String[]{Integer.toString(programId)});
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
                            Integer.toString(id),
                            Integer.toString(i + 1),
                            sets.getExercise(0).getName(),
                            record,
                            sets.getStructure()
                    };
                    db.execSQL("INSERT INTO logs (id,number,exercise,record,structure) VALUES (?,?,?,?,?)", values);
                }
            }
        }
    }

    //删除一条训练记录
    public void deleteTrainingRecord(String date, int id){
        SQLiteDatabase tpdb = this.getWritableDatabase();
        tpdb.execSQL("DELETE FROM log_catalog WHERE date = ? AND program_id = ?", new String[]{date,Integer.toString(id)});
    }

    //从数据库导入所有动作类别
    public ArrayList<HashMap<String,String>> inputExerciseTypes(){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<HashMap<String,String>> types = new ArrayList<>();
        HashMap<String,String> map;
        Cursor cursor = db.rawQuery("SELECT * FROM exercise_types ORDER BY number",null);
        while (cursor.moveToNext()){
            map = new HashMap<>();
            map.put("type_name", cursor.getString(cursor.getColumnIndex("type_name")));
            map.put("type_chinese", cursor.getString(cursor.getColumnIndex("type_chinese")));
            map.put("sort", cursor.getString(cursor.getColumnIndex("sort")));
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
        Cursor cursor = eldb.rawQuery("SELECT * FROM exercise_types WHERE type_name = ? ORDER BY number",
                new String[]{type});
        if (cursor.moveToNext()){
            sorts = cursor.getString(cursor.getColumnIndex("sort")).split(",");
        }
        cursor.close();
        for(String sort : sorts){
            exercises = new ArrayList<Exercise>();
            cursor = eldb.rawQuery("SELECT * FROM exercises WHERE type = ? AND sort = ? ORDER BY id", new String[]{type,sort});
            while (cursor.moveToNext()){
                String name = cursor.getString(cursor.getColumnIndex("name"));
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
