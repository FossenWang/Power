package fossen.power.training_program_library;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import fossen.power.R;
import fossen.power.TrainingDay;
import fossen.power.TrainingProgram;

/**
 * Created by Administrator on 2017/9/18.
 */

public class TrainingProgramContentAdapter extends BaseAdapter {
    private TrainingProgram trainingProgram;
    private Context mContext;

    public TrainingProgramContentAdapter(TrainingProgram trainingProgram, Context mContext){
        this.trainingProgram = trainingProgram;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return trainingProgram.circleDays();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder ;
        if(convertView == null){
            convertView =
                    LayoutInflater.from(mContext).inflate(
                            R.layout.item_training_program_content,parent,false);
            holder = new ViewHolder();
            holder.textDay =(TextView) convertView.findViewById(R.id.text_tpc_day);
            holder.textCount =(TextView) convertView.findViewById(R.id.text_tpc_count);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        TrainingDay trainingDay = trainingProgram.getTrainingDay(position);
        if(trainingDay.isRestDay()){
            holder.textDay.setText((position+1) + "  休息");
            holder.textCount.setText("");
        }else{
            holder.textDay.setText((position+1) + "  训练日: " + trainingDay.getTitle());
            holder.textCount.setText(trainingDay.numberOfExercise() + "个动作  "
                    + trainingDay.numberOfSingleSets() + "组");
        }
        return convertView;
    }

    private static class ViewHolder{
        TextView textDay;
        TextView textCount;
    }
}
