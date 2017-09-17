package fossen.power.training_program_library;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import fossen.power.R;
import fossen.power.TrainingProgram;

/**
 * Created by Administrator on 2017/9/12.
 */

public class TrainingProgramLibraryAdapter extends BaseAdapter {
    private ArrayList<TrainingProgram> pList;
    private Context mContext;

    public TrainingProgramLibraryAdapter(ArrayList<TrainingProgram> pList,Context mContext){
        this.pList = pList;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return pList.size();
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
                        R.layout.item_training_program_library,parent,false);
        TextView text_title = (TextView) convertView.findViewById(R.id.text_itpl_title);
        TextView text_cg = (TextView) convertView.findViewById(R.id.text_itpl_circle_goal);
        Button button = (Button) convertView.findViewById(R.id.button_itpl);

        TrainingProgram program = pList.get(position);
        final boolean start = program.isStart();
        text_title.setText(program.getName());
        if (start) {
            text_cg.setText("目标: "+program.getGoal()+"    循环: "
                    +program.countNumberOfDays()+"/"+program.circleDays()+"天");
            button.setText("使用中");
            button.setSelected(start);
        }else {
            text_cg.setText("目标: "+program.getGoal()+"    循环: 0/"+program.circleDays()+"天");
            button.setText("未使用");
            button.setSelected(start);
        }

        /*button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(start){

                }
            }
        });*/
        return convertView;
    }
}
