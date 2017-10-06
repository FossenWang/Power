package fossen.power.training_log;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import fossen.power.TrainingDay;
import fossen.power.R;

/**
 * Created by WXY on 2017/8/11.
 */

public class TrainingRecordAdapter extends BaseAdapter {
    private TrainingDay trainingDay;
    private Context mContext;

    public TrainingRecordAdapter(TrainingDay trainingDay , Context mContext) {
        this.trainingDay = trainingDay;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return trainingDay.numberOfSets();
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
        ViewHolder holder;
        if(convertView == null) {
            convertView =
                    LayoutInflater.from(mContext).inflate(
                            R.layout.item_training_record, parent, false);
            holder = new ViewHolder();
            holder.img_exercise = (ImageButton) convertView.findViewById(R.id.imageExercise);
            holder.txt_exercise = (TextView) convertView.findViewById(R.id.textExercise);
            holder.txt_setLoadRep = (TextView) convertView.findViewById(R.id.textSetLoadRep);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.img_exercise.setBackgroundResource(trainingDay.getSets(position).getExercise(0).getIcon());
        holder.txt_exercise.setText(trainingDay.getSets(position).getExercise(0).getName());
        holder.txt_setLoadRep.setText(trainingDay.getSets(position).getAllSets("kg"));
        return convertView;
    }
    private static class ViewHolder{
        ImageButton img_exercise;
        TextView txt_exercise;
        TextView txt_setLoadRep;
    }
}
