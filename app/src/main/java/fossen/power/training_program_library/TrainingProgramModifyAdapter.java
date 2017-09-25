package fossen.power.training_program_library;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;

import fossen.power.R;
import fossen.power.TPDBOpenHelper;
import fossen.power.TrainingProgram;

/**
 * Created by Administrator on 2017/9/24.
 */

public class TrainingProgramModifyAdapter extends BaseAdapter {
    private TrainingProgram trainingProgram;
    private TrainingProgram trainingProgramMod;
    private TPDBOpenHelper tpdbOpenHelper;
    private Context mContext;
    //定义成员变量mTouchItemPosition,用来记录手指触摸的EditText的位置
    private int mTouchItemPosition = -1;

    public TrainingProgramModifyAdapter(TrainingProgram trainingProgram, TPDBOpenHelper tpdbOpenHelper, Context mContext) {
        this.trainingProgram = trainingProgram;
        this.trainingProgramMod = trainingProgram;
        this.tpdbOpenHelper = tpdbOpenHelper;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null) {
            convertView =
                    LayoutInflater.from(mContext).inflate(
                            R.layout.item_tpcg_modification, parent, false);
            holder = new ViewHolder();
            holder.edit_day = (EditText) convertView.findViewById(R.id.edit_tpc_day_mod);
            holder.edit_day.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    mTouchItemPosition = position;
                    return false;
                }
            });
            holder.edit_day.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {}
                @Override
                public void afterTextChanged(Editable s) {
                    trainingProgramMod.getTrainingDay(position).setTitle(s.toString());
                }
            });
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.edit_day.setText(trainingProgram.getTrainingDay(position).getTitle());

        //判断焦点
        if (mTouchItemPosition == position) {
            holder.edit_day.requestFocus();
            holder.edit_day.setSelection(holder.edit_day.getText().length());
        } else {
            holder.edit_day.clearFocus();
        }
        return convertView;
    }

    private static class ViewHolder{
        EditText edit_day;
    }

    public void saveModification(){
        tpdbOpenHelper.updateTrainingProgram(trainingProgramMod);
    }
}
