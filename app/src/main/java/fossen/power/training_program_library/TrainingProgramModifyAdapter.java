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
import android.widget.ImageView;
import android.widget.TextView;

import fossen.power.R;
import fossen.power.TPDBOpenHelper;
import fossen.power.TrainingProgram;

/**
 * Created by Administrator on 2017/9/24.
 */

public class TrainingProgramModifyAdapter extends BaseAdapter {
    private TrainingProgram trainingProgram;
    private TPDBOpenHelper tpdbOpenHelper;
    private Context mContext;
    //定义成员变量mTouchItemPosition,用来记录手指触摸的EditText的位置
    private int mTouchItemPosition = -1;

    public TrainingProgramModifyAdapter(TrainingProgram trainingProgram, TPDBOpenHelper tpdbOpenHelper, Context mContext) {
        this.trainingProgram = trainingProgram;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        final int pos = position;

        if(convertView == null) {
            convertView =
                    LayoutInflater.from(mContext).inflate(
                            R.layout.item_tpcg_modification, parent, false);
            holder = new ViewHolder();
            holder.edit_day = (EditText) convertView.findViewById(R.id.edit_tpc_day_mod);
            holder.delete_day = (ImageView) convertView.findViewById(R.id.button_tpcm_delete);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        //设置删除按钮
        holder.delete_day.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                trainingProgram.removeTrainingDay(pos);
                notifyDataSetChanged();
            }
        });

        //如果注册过监听器就注销
        if (holder.edit_day.getTag() instanceof TextWatcher){
            holder.edit_day.removeTextChangedListener((TextWatcher) holder.edit_day.getTag());
        }
        holder.edit_day.setText(trainingProgram.getTrainingDay(position).getTitle());
        //监听编辑栏内容
        TextWatcher watcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                trainingProgram.getTrainingDay(pos).setTitle(s.toString());
            }
        };
        holder.edit_day.addTextChangedListener(watcher);
        holder.edit_day.setTag(watcher);


        //记录焦点位置
        holder.edit_day.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mTouchItemPosition = pos;
                return false;
            }
        });
        //判断焦点
        holder.edit_day.clearFocus();
        if (mTouchItemPosition == position) {
            holder.edit_day.requestFocus();
            holder.edit_day.setSelection(holder.edit_day.getText().length());
        }
        return convertView;
    }

    private static class ViewHolder{
        EditText edit_day;
        ImageView delete_day;
    }

    //保存修改
    public void saveModification(){
        tpdbOpenHelper.updateTrainingProgram(trainingProgram);
    }
}
