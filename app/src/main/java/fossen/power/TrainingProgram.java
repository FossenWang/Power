package fossen.power;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Administrator on 2017/8/18.
 */

public class TrainingProgram implements Serializable {
    //训练日列表
    private ArrayList<TrainingDay> tList = new ArrayList<TrainingDay>();
    private String id = "";
    private String name = "" ;//方案名
    private String note = "";//说明
    private String goal = "";//训练目的
    //方案启用时的日期
    private int year = 2017;
    private int month = 1;
    private int day = 1;
    //start为0时表示方案未使用，非零时表示方案使用中，数字表示从周期中的第几天开始方案
    private int start = 0;

    //向列表尾端添加训练日
    public void addTrainingDay(TrainingDay trainingDay){
        tList.add(trainingDay);
    }
    //添加多个空训练日，记录循环周期
    public void addTrainingDay(int circle){
        for (int i = 0; i < circle;i++ ) {
            tList.add(new TrainingDay());
        }
    }
    public void addTrainingDay(String[] titles){
        for(String title : titles){
            tList.add(new TrainingDay(title));
        }
    }
    //将训练日插入列表中的某位置
    public void addTrainingDay(int index,TrainingDay trainingDay){
        tList.add(index,trainingDay);
    }
    //按下标返回一个训练日
    public TrainingDay getTrainingDay(int index){
        return tList.get(index);
    }
    //按下标移除一个训练日
    public TrainingDay removeTrainingDay(int index){
        return tList.remove(index);
    }
    //替换某位置的训练日
    public TrainingDay replaceTrainingDay(int index,TrainingDay trainingDay){
        return tList.set(index, trainingDay);
    }
    //返回循环天数
    public int circleDays(){
        return tList.size();
    }

    //存取方案名，目标，说明，开始日期，总天数,开始与否等信息
    public void setId(String id) {
        this.id = id;
    }
    public String getId() {
        return id;
    }
    public void setName(String str){
        name = str;
    }
    public String getName(){
        return name;
    }
    public void setGoal(String goal) {
        this.goal = goal;
    }
    public String getGoal() {
        return goal;
    }
    public void setNote(String str){
        note = str;
    }
    public String getNote(){
        return note;
    }
    public void setDate(Calendar d){
        year = d.get(Calendar.YEAR);
        month = d.get(Calendar.MONTH)+1;
        day = d.get(Calendar.DAY_OF_MONTH);
    }
    public void setDate(String date){
        //参数date格式应为yyyymmdd,如20170101,parseInt可以识别开头的0
        year = Integer.parseInt(date.substring(0,4));
        month = Integer.parseInt(date.substring(4,6));
        day = Integer.parseInt(date.substring(6,8));
    }
    public String getDate(){
        String m,d;
        if(month<10){
            m = "0"+month;
        }else{
            m = Integer.toString(month);
        }
        if (day<10){
            d = "0"+day;
        }else{
            d = Integer.toString(day);
        }
        return Integer.toString(year)+m+d;
    }
    public int countTodayInCircle(){
        Calendar begin = Calendar.getInstance();
        Calendar now = Calendar.getInstance();
        begin.set(year,month-1,day);
        int n =  (Math.round((now.getTimeInMillis()-begin.getTimeInMillis())/(1000*3600*24))
                + start)%circleDays();
        if(n==0){
            return circleDays();
        }else {
            return n;
        }
    }
    public String getCircleGoal(){
        String str;
        if (start!=0) {
            str = "目标: "+goal+"    周期: " + countTodayInCircle()+"/"+circleDays()+"天";
        }else {
            str = "目标: "+goal+"    周期: "+circleDays()+"天";
        }
        return str;
    }
    public void setStart(int start) {
        this.start = start;
    }
    public int getStart() {
        return start;
    }
}
