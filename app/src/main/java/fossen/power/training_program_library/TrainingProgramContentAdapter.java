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
        return trainingProgram.countNumberOfCircle();
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
        if(convertView == null){
            convertView =
                    LayoutInflater.from(mContext).inflate(
                            R.layout.item_training_program_content,parent,false);
        }
        TextView textDay =(TextView) convertView.findViewById(R.id.text_tpc_day);
        TextView textCount =(TextView) convertView.findViewById(R.id.text_tpc_count);
        TrainingDay trainingDay = trainingProgram.getTrainingDay(position);
        if(trainingDay.isRestDay()){
            textDay.setText(position + "休息");
            textCount.setText("");
        }else{
            textDay.setText(position + "  训练日: " + trainingDay.getTitle());
        }
        return convertView;
    }
}
