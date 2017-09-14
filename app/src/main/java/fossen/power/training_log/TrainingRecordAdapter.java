package fossen.power.training_log;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import fossen.power.TrainingDay;
import fossen.power.R;

/**
 * Created by WXY on 2017/8/11.
 */

public class TrainingRecordAdapter extends BaseAdapter {
    private TrainingDay trainingDay;//自建类TrainingDay包含一个训练日的训练内容
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
        convertView =
                LayoutInflater.from(mContext).inflate(
                        R.layout.item_training_record,parent,false);
        ImageButton img_exercise = (ImageButton) convertView.findViewById(R.id.imageExercise);
        TextView txt_exercise = (TextView) convertView.findViewById(R.id.textExercise);
        TextView txt_setLoadRep = (TextView) convertView.findViewById(R.id.textSetLoadRep);

        img_exercise.setBackgroundResource(trainingDay.getSets(position).getExercise(0).getIcon());
        txt_exercise.setText(trainingDay.getSets(position).getExercise(0).getName());
        txt_setLoadRep.setText(trainingDay.getSets(position).getAllSets("kg"));
        return convertView;
    }
}
