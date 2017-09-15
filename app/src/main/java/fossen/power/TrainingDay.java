package fossen.power;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by WXY on 2017/8/11.
 */
//自建数据类TrainingDay包含一个训练日的训练内容
public class TrainingDay {
    private ArrayList<Sets> sList = new ArrayList<Sets>();//声明动作列表
    private String program;//训练方案名称
    private String title;//训练日标题
    private String note;//附注
    private int year;
    private int month;
    private int day;
    private boolean rest = false;

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

    //设置与返回标题，附注，日期，训练方案,休息等参数
    public void setTitle(String str){
        title = str;
    }
    public String getTitle(){
        return title;
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
    public void setProgram(String str){
        program = str;
    }
    public String getProgram(){
        return program;
    }
    public void setRest(boolean rest) {
        this.rest = rest;
    }
    public boolean isRest() {
        return rest;
    }
}