package fossen.power;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by WXY on 2017/8/11.
 */
//自建数据类TrainingDay包含一个训练日的训练内容
public class TrainingDay {
    private ArrayList<Sets> sList = new ArrayList<Sets>();//声明动作列表
    private String title = "";//训练日标题
    private int year = 0;
    private int month = 0;
    private int day = 0;
    private boolean restDay = true;

    public TrainingDay(){}
    public TrainingDay(String title){
        this.title = title;
    }

    //向列表尾端添加动作
    public void addSets(Sets sets){
        sList.add(sets);
    }
    //将动作插入列表中的某位置
    public void addSets(int index,Sets sets){
        sList.add(index,sets);
    }
    //按下标返回一个动作
    public Sets getSets(int index){
        return sList.get(index);
    }
    //按下标移除一个动作
    public Sets removeSets(int index){
        return sList.remove(index);
    }
    //替换某位置的动作
    public Sets replaceSets(int index,Sets sets){
        return sList.set(index, sets);
    }
    //返回组集总数
    public int numberOfSets(){
        return sList.size();
    }
    public int numberOfExercise(){
        return sList.size();
    }
    public int numberOfSingleSets(){
        int n = 0;
        for(Sets sets : sList){
            n+=sets.numberOfSingleSets();
        }
        return n;
    }

    //设置与返回标题，附注，日期，训练方案,休息等参数
    public void setTitle(String str){
        title = str;
    }
    public String getTitle(){
        return title;
    }
    public void setDate(Calendar d){
        year = d.get(Calendar.YEAR);
        month = d.get(Calendar.MONTH)+1;
        day = d.get(Calendar.DAY_OF_MONTH);
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
    public void setRestDay(boolean restDay) {
        this.restDay = restDay;
    }
    public boolean isRestDay() {
        return restDay;
    }
}
