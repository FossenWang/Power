package fossen.power.training_program_library;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import fossen.power.R;
import fossen.power.Sets;
import fossen.power.TPDBOpenHelper;
import fossen.power.TrainingDay;
import fossen.power.TrainingProgram;

/**
 * Created by Administrator on 2017/9/28.
 */

public class TrainingDayModifyAdapter extends BaseAdapter {

    private String id;
    private int dayIndex;
    private TrainingDay trainingDay;
    private TPDBOpenHelper tpdbOpenHelper;
    private Context mContext;
    //定义成员变量mTouchItemPosition,用来记录手指触摸的EditText的位置
    private int mTouchItemPosition = -1;
    private int mTouchItemEdit = -1;

    public TrainingDayModifyAdapter(String id, int dayIndex, TrainingDay trainingDay, TPDBOpenHelper tpdbOpenHelper, Context mContext){
        this.id = id;
        this.dayIndex = dayIndex;
        this.trainingDay = trainingDay;
        this.tpdbOpenHelper = tpdbOpenHelper;
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
        final int pos = position;
        final Sets sets = trainingDay.getSets(pos);
        final String rapmax[] = new String[]{"0","0"};
        if(sets.getRepmax()!=""){
            rapmax[0] = sets.getRepmax().split("~")[0];
            rapmax[1] = sets.getRepmax().split("~")[1];
        }
        final int[] rest = {0,0};//前一个是min后一个是s
        if (sets.getRest()<60){
            rest[1] = sets.getRest();
        }else if(sets.getRest()%60==0){
            rest[0] = sets.getRest()/60;
        }else {
            rest[0] = sets.getRest()/60;
            rest[1] = sets.getRest()%60;
        }

        if(convertView == null) {
            convertView =
                    LayoutInflater.from(mContext).inflate(
                            R.layout.item_tpci_modification, parent, false);
            holder = new ViewHolder();
            holder.edit_rm1 = (EditText) convertView.findViewById(R.id.edit_tpc_RM1_mod);
            holder.edit_rm2 = (EditText) convertView.findViewById(R.id.edit_tpc_RM2_mod);
            holder.edit_sets = (EditText) convertView.findViewById(R.id.edit_tpc_sets_mod);
            holder.edit_min = (EditText) convertView.findViewById(R.id.edit_tpc_min_mod);
            holder.edit_sec = (EditText) convertView.findViewById(R.id.edit_tpc_s_mod);
            holder.text_exercise = (TextView) convertView.findViewById(R.id.text_tpc_exercise_mod);
            holder.arrow = (ImageView) convertView.findViewById(R.id.arrow_tpc_exercise_mod);
            holder.button_delete = (ImageView) convertView.findViewById(R.id.button_tpc_delete_sets) ;
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        sets.addExercise("卧推");
        sets.addExercise("硬拉");
        sets.addExercise("深蹲");
        //有动作则显示
        if(sets.getExerciseList().size()!=0){
            holder.text_exercise.setText(sets.getExercise(0).getName());
        }

        //单击下箭头弹出菜单，选择替换动作
        holder.arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(mContext, v);
                Menu menu = popup.getMenu();
                menu.add("请选择替换的动作：");
                if(sets.getExerciseList().size()>1){
                    for(int i = 1; i<sets.getExerciseList().size(); i++){
                        menu.add(Menu.NONE,Menu.NONE,i,sets.getExercise(i).getName());
                    }
                }else {
                    menu.add("无");
                }
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        int i = item.getOrder();
                        if(i>0) {
                            sets.exchangeExercise(0, i);
                            tpdbOpenHelper.updateExercise(id, dayIndex + 1, pos + 1, sets);
                            notifyDataSetChanged();
                            Toast.makeText(mContext, "你选择了："+item.getTitle(), Toast.LENGTH_SHORT).show();
                        }
                        return true;
                    }
                });
                popup.show();
            }
        });

        //设置删除按钮
        holder.button_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    trainingDay.removeSets(pos);
                    notifyDataSetChanged();
            }
        });

        //如果EditText注册过监听器就注销
        if (holder.edit_sec.getTag() instanceof TextWatcher){
            holder.edit_rm1.removeTextChangedListener((TextWatcher) holder.edit_rm1.getTag());
            holder.edit_sets.removeTextChangedListener((TextWatcher) holder.edit_sets.getTag());
            holder.edit_min.removeTextChangedListener((TextWatcher) holder.edit_min.getTag());
            holder.edit_sec.removeTextChangedListener((TextWatcher) holder.edit_sec.getTag());
        }
        holder.edit_rm1.setText(rapmax[0]);
        holder.edit_rm2.setText(rapmax[1]);
        holder.edit_sets.setText(Integer.toString(sets.numberOfSingleSets()));
        holder.edit_min.setText(Integer.toString(rest[0]));
        holder.edit_sec.setText(Integer.toString(rest[1]));
        //监听编辑栏内容
        TextWatcher watcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString().isEmpty()){
                    rapmax[0] = "0";
                    sets.setRepmax(rapmax[0] + "~" + rapmax[1]);
                }else {
                    rapmax[0] = s.toString();
                    sets.setRepmax(rapmax[0] + "~" + rapmax[1]);
                }
            }
        };
        holder.edit_rm1.addTextChangedListener(watcher);
        holder.edit_rm1.setTag(watcher);
        watcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString().isEmpty()){
                    rapmax[1] = "0";
                    sets.setRepmax(rapmax[0] + "~" + rapmax[1]);
                }else{
                    rapmax[1] = s.toString();
                    sets.setRepmax(rapmax[0] + "~" + rapmax[1]);
                }
            }
        };
        holder.edit_rm2.addTextChangedListener(watcher);
        holder.edit_rm2.setTag(watcher);
        watcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString().isEmpty()){
                    sets.clearSets();
                }else {
                    sets.clearSets();
                    sets.addSet(Integer.parseInt(s.toString()));
                }
            }
        };
        holder.edit_sets.addTextChangedListener(watcher);
        holder.edit_sets.setTag(watcher);
        watcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString().isEmpty()){
                    rest[0] = 0;
                    sets.setRest(rest[0] * 60 + rest[1]);
                }else {
                    rest[0] = Integer.parseInt(s.toString());
                    sets.setRest(rest[0] * 60 + rest[1]);
                }
            }
        };
        holder.edit_min.addTextChangedListener(watcher);
        holder.edit_min.setTag(watcher);
        watcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString().isEmpty()){
                    rest[1] = 0;
                    sets.setRest(rest[0] * 60 + rest[1]);
                }else {
                    rest[1] = Integer.parseInt(s.toString());
                    sets.setRest(rest[0] * 60 + rest[1]);
                }
            }
        };
        holder.edit_sec.addTextChangedListener(watcher);
        holder.edit_sec.setTag(watcher);

        //记录焦点位置
        holder.edit_rm1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mTouchItemPosition = pos;
                mTouchItemEdit = 0;
                return false;
            }
        });
        holder.edit_rm2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mTouchItemPosition = pos;
                mTouchItemEdit = 1;
                return false;
            }
        });
        holder.edit_sets.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mTouchItemPosition = pos;
                mTouchItemEdit = 2;
                return false;
            }
        });
        holder.edit_min.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mTouchItemPosition = pos;
                mTouchItemEdit = 3;
                return false;
            }
        });
        holder.edit_sec.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mTouchItemPosition = pos;
                mTouchItemEdit = 4;
                return false;
            }
        });
        //判断焦点
        holder.edit_rm1.clearFocus();
        holder.edit_rm2.clearFocus();
        holder.edit_sets.clearFocus();
        holder.edit_min.clearFocus();
        holder.edit_sec.clearFocus();
        if (mTouchItemPosition == position) {
            switch (mTouchItemEdit){
                case 0 :
                    holder.edit_rm1.requestFocus();
                    holder.edit_rm1.setSelection(holder.edit_rm1.getText().length());
                    break;
                case 1 :
                    holder.edit_rm2.requestFocus();
                    holder.edit_rm2.setSelection(holder.edit_rm2.getText().length());
                    break;
                case 2 :
                    holder.edit_sets.requestFocus();
                    holder.edit_sets.setSelection(holder.edit_sets.getText().length());
                    break;
                case 3 :
                    holder.edit_min.requestFocus();
                    holder.edit_min.setSelection(holder.edit_min.getText().length());
                    break;
                case 4 :
                    holder.edit_sec.requestFocus();
                    holder.edit_sec.setSelection(holder.edit_sec.getText().length());
                    break;
            }
        }

        return convertView;
    }

    private static class ViewHolder{
        EditText edit_rm1;
        EditText edit_rm2;
        EditText edit_sets;
        EditText edit_min;
        EditText edit_sec;
        ImageView arrow;
        ImageView button_delete;
        TextView text_exercise;
    }

    //保存修改
    public void saveModification(){
        tpdbOpenHelper.updateTrainingDay(id, dayIndex + 1, trainingDay);
    }
}
