package fossen.power;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Administrator on 2017/8/18.
 */

public class TrainingProgram {
    //训练日列表
    private ArrayList<TrainingDay> tList = new ArrayList<TrainingDay>();
    private String title;//标题
    private String note;//说明
    private String goal;//训练目的
    //方案启用时的日期
    private int year;
    private int month;
    private int day;
    //循环周期中的第几天
    private int numberOfDays = 0;
    private boolean start = false;

    //向列表尾端添加训练日
    public void addTrainingDay(TrainingDay trainingDay){
        tList.add(trainingDay);
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

    //存取标题，目标，说明，开始日期，总天数,开始与否等信息
    public void setTitle(String str){
        title = str;
    }
    public String getTitle(){
        return title;
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
    public void setDate(int year,int month,int day){
        this.year = year;
        this.month = month;
        this.day = day;
    }
    public String getDate(){
        return year+"/"+month+"/"+day;
    }
    public int getYear(){
        return year;
    }
    public int getMonth(){
        return month;
    }
    public int getDay(){
        return day;
    }
    public int countNumberOfDays(){
        Calendar begin = Calendar.getInstance();
        Calendar now = Calendar.getInstance();
        begin.set(year,month-1,day);
        int n = (Math.round((now.getTimeInMillis()-begin.getTimeInMillis())/(1000*3600*24))
                + 1)%circleDays();
        if (n == 0){
            numberOfDays = circleDays();
        }else {
            numberOfDays = n;
        }
        return numberOfDays;
    }
    public int getNumberOfDays(){
        return numberOfDays;
    }
    public void setStart(boolean start) {
        this.start = start;
    }
    public boolean isStart() {
        return start;
    }
}
