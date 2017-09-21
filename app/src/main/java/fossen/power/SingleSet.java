package fossen.power;

/**
 * Created by Administrator on 2017/8/13.
 */

public class SingleSet {
    private Double load;//负重，单位为公斤
    private int reps;//次数
    private int duration;//持续时间，单位为s

    public SingleSet(){}
    //！！！输入一个参数则记录持续时间，两个参数则记录负重与次数
    //！！！输入的重量为Double型时单位为kg，int型时单位为lb
    //！！！重量单位的判断应只在创建视图时执行
    public SingleSet(Double load_kg, int reps){
        setLoad(load_kg);
        setReps(reps);
    }
    public SingleSet(int load_lb, int reps){
        setLoad(load_lb);
        setReps(reps);
    }
    public SingleSet(int duration){
        setDuration(duration);
    }

    //Double型参数单位为kg
    public void setLoad(Double load_kg) {
        load = load_kg;
    }
    //int型参数单位为lb，换算成kg后保存
    public void setLoad(int load_lb) {
        load = load_lb*0.45359237;
    }
    //取出负重，单位kg
    public Double getLoad() {
        return load;
    }
    //取出负重，单位换算为lb
    public long getLoadToPound() {
        return Math.round(load*2.20462262);
    }
    //设置、取出次数
    public void setReps(int reps) {
        this.reps = reps;
    }
    public int getReps() {
        return reps;
    }
    public void setDuration(int duration) {
        this.duration = duration;
    }
    public int getDuration() {
        return duration;
    }
}