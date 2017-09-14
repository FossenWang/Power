package fossen.power;

import java.util.ArrayList;

/**
 * Created by WXY on 2017/8/12.
 */

public class Exercise {
    private String name;//动作名称
    private String type;//动作类别：自重，器械，力量举等
    private int icon;//图标

    public Exercise(String name){
        this.name = name;
    }
    //设置和返回动作名，类别，图标
    public void setName(String str){
        name = str;
    }
    public String getName(){
        return name;
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
