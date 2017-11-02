package fossen.power;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Administrator on 2017/9/12.
 */

public class Sets implements Serializable {

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
        for (int i = 0;i<n;i++) {
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
    //按格式输出有效组的重量与次数，参数为重量单位
    public String getAllSetsToFormat(String unit){
        String formatSets = "-";
        int i = 0;
        double lastLoad = 0.0;
        if (unit.equals("kg")){    //以kg为单位输出
            for (SingleSet set : setList) {
                if(set.getReps()!=0 || set.getLoad()!=0.0) {
                    if (set.getLoad()==lastLoad){
                        formatSets += set.getReps()+"-";
                    }else if (set.getLoad() == set.getLoad().intValue()) {//判断重量是否为整数，是则输出整数，否则保留一位小数输出
                        formatSets = formatSets.substring(0,formatSets.length()-1);
                        formatSets += "\n" + Math.round(set.getLoad()) + "kg  " + set.getReps()+"-";
                        lastLoad = set.getLoad();
                    }else {
                        formatSets = formatSets.substring(0,formatSets.length()-1);
                        formatSets += "\n" + Math.round(set.getLoad() * 10) / 10.0 + "kg  " + set.getReps()+"-";
                        lastLoad = set.getLoad();
                    }
                }
            }
            /*for (SingleSet set : setList) {
                if(set.getReps()!=0 || set.getLoad()!=0.0) {
                    i++;//判断重量是否为整数，是则输出整数，否则保留一位小数输出
                    if (set.getLoad() == set.getLoad().intValue()) {
                        formatSets += "第" + i + "组  " + Math.round(set.getLoad()) + " kg × " + set.getReps() + "次\n";
                    } else {
                        formatSets += "第" + i + "组  " + Math.round(set.getLoad() * 10) / 10.0 + " kg × " + set.getReps() + "次\n";
                    }
                }
            }*/
        }else {    //以lb为单位输出
            for (SingleSet set : setList) {
                if (set.getReps()!=0 || set.getLoad()!=0.0) {
                    if (set.getLoad()==lastLoad) {
                        formatSets += set.getReps() + "-";
                    }else {
                        formatSets = formatSets.substring(0,formatSets.length()-1);
                        formatSets += "\n" + set.getLoadToPound() + " lb  " + set.getReps()+"-";
                        lastLoad = set.getLoad();
                    }
                    //formatSets += "第" + (++i) + "组  " + set.getLoadToPound() + " lb × " + set.getReps() + "次\n";
                }
            }
        }
        if (formatSets.equals("-")){
            formatSets += "暂无 ";
        }
        return formatSets.substring(1,formatSets.length()-1);
    }
    public void clearSets(){
        setList.clear();
    }

    //添加，获取，移除，替换动作

    public void setExerciseList(ArrayList<Exercise> exerciseList) {
        this.exerciseList = exerciseList;
    }
    public void setExerciseList(String[] nameList){
        exerciseList.clear();
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
    public String getRestString(){
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
    public String getExerciseType(){
        String type = "";
        switch (getStructure()){
            case "bodyweight": type = "bodyweight";break;
            case "bodyweight_supersets": type = "bodyweight";break;
            case "equipment": type = "equipment";break;
            case "equipment_supersets": type = "equipment";break;
            case "stretching": type = "stretching";break;
        }
        return type;
    }
}