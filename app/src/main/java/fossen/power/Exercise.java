package fossen.power;


import java.io.Serializable;

/**
 * Created by WXY on 2017/8/12.
 */

public class Exercise implements Serializable {
    private String id = "";//动作编号
    private String name = "";//动作名称
    private String type = "";//动作类别：自重，器械，力量举等
    private int icon;//图标
    private String sort = "";//动作细分
    private String record = "";//上次训练的记录

    public Exercise(){};
    public Exercise(String name, String sort, String type, String record){
        this.name = name;
        this.sort = sort;
        this.type = type;
        this.record = record;
    }
    public Exercise(String name){
        this.name = name;
    }

    //设置和返回动作名，类别，图标,动作细分
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
    public void setSort(String sort) {
        this.sort = sort;
    }
    public String getSort() {
        return sort;
    }
    public void setType(String str){
        type = str;
    }
    public String getType(){
        return type;
    }
    public void setRecord(String record) {
        this.record = record;
    }
    public String getRecord() {
        return record;
    }
    public void setIcon(int i){
        icon = i;
    }
    public int getIcon(){
        return icon;
    }
}