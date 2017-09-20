package fossen.power.training_program_library;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;

import fossen.power.R;
import fossen.power.TPDBOpenHelper;
import fossen.power.TrainingProgram;

/**
 * Created by Administrator on 2017/9/12.
 */

public class TrainingProgramLibraryAdapter extends BaseAdapter {
    private ArrayList<TrainingProgram> pList;
    private TPDBOpenHelper tpdbOpenHelper;
    private Context mContext;
    int choice = -1;

    public TrainingProgramLibraryAdapter(ArrayList<TrainingProgram> pList,TPDBOpenHelper tpdbOpenHelper,Context mContext){
        this.pList = pList;
        this.tpdbOpenHelper = tpdbOpenHelper;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return pList.size();
    }

    @Override
    public Object getItem(int position) {
        return pList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        if(convertView == null){
            convertView =
                    LayoutInflater.from(mContext).inflate(
                            R.layout.item_training_program_library,parent,false);
        }
        View in_tpc = convertView.findViewById(R.id.in_tpc);
        TextView text_title = (TextView) convertView.findViewById(R.id.text_itpl_title);
        TextView text_cg = (TextView) convertView.findViewById(R.id.text_itpl_circle_goal);
        Button button = (Button) convertView.findViewById(R.id.button_itpl);

        final TrainingProgram program = pList.get(position);
        text_title.setText(program.getName());
        if (program.getStart()!=0) {
            text_cg.setText(program.getCircleGoal());
            button.setText("使用中");
            button.setSelected(true);
        }else {
            text_cg.setText(program.getCircleGoal());
            button.setText("未使用");
            button.setSelected(false);
        }

        //设置点击事件，进入方案详情页
        in_tpc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,TrainingProgramContentActivity.class);
                intent.putExtra("id",program.getId());
                mContext.startActivity(intent);
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(program.getStart()!=0){
                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    builder.setTitle("确定要停止训练吗？")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    program.setStart(0);
                                    tpdbOpenHelper.outputStartDate(program);
                                    notifyDataSetChanged();
                                }})
                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }});
                    builder.create().show();
                }else {
                    showDialog();
                }
            }

            public void showDialog(){
                String[] days = new String[program.circleDays()];
                final int[] start = {1};
                for(int i = 0; i<program.circleDays(); i++){
                    days[i] = Integer.toString(i+1)+ "  " + program.getTrainingDay(i).getTitle();
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("请选择选择今天的训练：")
                        .setSingleChoiceItems(days, 0, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                start[0] = (which+1);
                            }})
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                program.setStart(start[0]);
                                program.setDate(Calendar.getInstance());
                                tpdbOpenHelper.outputStartDate(program);
                                notifyDataSetChanged();
                            }})
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                builder.create().show();
            }
        });

        return convertView;
    }

}
