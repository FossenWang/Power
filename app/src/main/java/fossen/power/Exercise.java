package fossen.power;

import java.util.ArrayList;

/**
 * Created by WXY on 2017/8/12.
 */

public class Exercise {
    private String id;//动作编号
    private String name;//动作名称
    private String type;//动作类别：自重，器械，力量举等
    private int icon;//图标
    private String muscle;//锻炼肌群

    public Exercise(){};
    public Exercise(String name,String muscle){
        this.name = name;
        this.muscle = muscle;
    }
    public Exercise(String id){
        this.id = id;
    }

    //设置和返回动作名，类别，图标,锻炼肌群
    public void setId(String id) {
        this.id = id;
    }
    public String  getId() {
        return id;
    }
    public void setName(String str){
        name = str;
    }
    public String getName(){
        return name;
    }
    public void setMuscle(String muscle) {
        this.muscle = muscle;
    }
    public String getMuscle() {
        return muscle;
    }
    public void setType(String str){
        type = str;
    }
    public String getType(){
        return type;
    }
    public void setIcon(int i){
        icon = i;
    }
    public int getIcon(){
        return icon;
    }
}
