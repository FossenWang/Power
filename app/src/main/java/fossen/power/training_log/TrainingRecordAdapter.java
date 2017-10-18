package fossen.power.training_log;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import fossen.power.Sets;
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
    public Sets getItem(int position) {
        return trainingDay.getSets(position);
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
            holder.text_exercise = (TextView) convertView.findViewById(R.id.text_tr_exercise);
            holder.text_sets = (TextView) convertView.findViewById(R.id.text_tr_sets);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.text_exercise.setText(trainingDay.getSets(position).getExercise(0).getName());
        holder.text_sets.setText(trainingDay.getSets(position).getAllSetsToFormat("kg"));
        return convertView;
    }
    private static class ViewHolder{
        TextView text_exercise;
        TextView text_sets;
    }
}
