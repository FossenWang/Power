package fossen.power.exercise_library;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import fossen.power.R;

/**
 * Created by Administrator on 2017/10/28.
 */

public class ExerciseTypesAdapter extends BaseAdapter {
    private ArrayList<HashMap<String,String>> types;
    private Context mContext;

    public ExerciseTypesAdapter(ArrayList<HashMap<String,String>> types, Context mContext){
        this.types = types;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return types.size();
    }

    @Override
    public HashMap getItem(int position) {
        return types.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(
                    R.layout.item_exerlib_group, parent, false);
            holder = new ViewHolder();
            holder.type_text = (TextView) convertView.findViewById(R.id.text_el_sort);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.type_text.setText(types.get(position).get("type_chinese"));
        return convertView;
    }

    private static class ViewHolder{
        TextView type_text;
    }
}
