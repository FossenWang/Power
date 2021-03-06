package fossen.power;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by WXY on 2017/8/11.
 */
//自建数据类TrainingDay包含一个训练日的训练内容
public class TrainingDay implements Serializable{
    //声明组集列表,空列表即为休息日
    private ArrayList<Sets> sList = new ArrayList<Sets>();
    private String title = "";//训练日标题

    public TrainingDay(){}
    public TrainingDay(String title){
        this.title = title;
    }

    //向列表尾端添加组集
    public void addSets(Sets sets){
        sList.add(sets);
    }
    //将动作插入列表中的某位置
    public void addSets(int index,Sets sets){
        sList.add(index,sets);
    }
    //按下标返回一个组集
    public Sets getSets(int index){
        return sList.get(index);
    }
    //按下标移除一个组集
    public Sets removeSets(int index){
        return sList.remove(index);
    }
    //替换某位置的组集
    public Sets replaceSets(int index,Sets sets){
        return sList.set(index, sets);
    }
    //清空组集
    public void clearSets(){
        sList.clear();
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
    public boolean isRestDay() {
        return sList.size()==0;
    }
}
