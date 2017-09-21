package fossen.power;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/9/12.
 */

public class Sets {

    private ArrayList<SingleSet> setList = new ArrayList<SingleSet>();
    //组的列表，训练记录中使用
    private ArrayList<Exercise> exerciseList =  new ArrayList<Exercise>();
    //第一个为首选动作，其余为备选动作，最多5个
    private String repmax = "";
    private int rest = 0;//休息时间，单位s
    private String structure = "";//组结构

    //在末尾添加组
    public void addSet(SingleSet set){
        setList.add(set);
    }
    //添加n个空组
    public void addSet(int n) {
        for (int i = 0;i<n;i+=1) {
            setList.add(new SingleSet());
        }
    }
    //按下标添加组
    public void addSet(int index,SingleSet set){
        setList.add(index,set);
    }
    //按下标返回一个组
    public SingleSet getSet(int index){
        return setList.get(index);
    }
    //按下标移除一个组
    public SingleSet removeSet(int index){
        return setList.remove(index);
    }
    //替换某位置的组
    public SingleSet replaceSet(int index, SingleSet set){
        return setList.set(index, set);
    }
    //返回总组数
    public int numberOfSingleSets(){
        return setList.size();
    }
    //返回所有组数，重量，次数，参数为重量单位
    public String getAllSets(String unit){
        String allSets = "";
        int i = 0;
        if (unit == "kg"){    //以kg为单位输出
            for (SingleSet set : setList) {
                i+=1;
                //判断重量是否为整数，是则输出整数，否则保留一位小数输出
                if(set.getLoad()==set.getLoad().intValue()){
                    allSets+="#"+i+":  "+Math.round(set.getLoad())+" kg × "+set.getReps()+"\n";
                }else {
                    allSets+="#"+i+":  "+Math.round(set.getLoad()*10)/10.0+" kg × "+set.getReps()+"\n";
                }
            }
        }else {    //以lb为单位输出
            for (SingleSet set : setList) {
                i+=1;
                allSets+="#"+i+":  "+set.getLoadToPound()+" lb × "+set.getReps()+"\n";
            }
        }
        return allSets.substring(0,allSets.length()-1);
    }

    //添加，获取，移除，替换动作

    public void setExerciseList(ArrayList<Exercise> exerciseList) {
        this.exerciseList = exerciseList;
    }
    public void setExerciseList(String[] nameList){
        for(String name : nameList){
            addExercise(name);
        }
    }
    public void addExercise(String name){
        exerciseList.add(new Exercise(name));
    }
    public void addExercise(Exercise exercise){
        exerciseList.add(exercise);
    }
    public Exercise getExercise(int index){
        return exerciseList.get(index);
    }
    public Exercise removeExercise(int index){
        return exerciseList.remove(index);
    }
    public Exercise replaceExercise(int index, Exercise exercise){
        return exerciseList.set(index,exercise);
    }
    public ArrayList<Exercise> getExerciseList(){
        return exerciseList;
    }
    public void exchangeExercise(int n, int m){
        Exercise x = exerciseList.get(n);
        exerciseList.set(n,exerciseList.get(m));
        exerciseList.set(m,x);
    }

    //设置和返回休息时间,RM，组结构
    public void setRest(int rest) {
        this.rest = rest;
    }
    public int getRest() {
        return rest;
    }
    public String getRestSting(){
        if (rest<60){
            return rest + "s";
        }else if(rest%60==0){
            return rest/60 + "min";
        }else {
            return rest/60 + "min" + rest%60 + "s";
        }
    }
    public void setRepmax(String repmax){
        this.repmax = repmax;
    }
    public String getRepmax(){
        return repmax;
    }
    public void setStructure(String structure) {
        this.structure = structure;
    }
    public String getStructure() {
        return structure;
    }
}