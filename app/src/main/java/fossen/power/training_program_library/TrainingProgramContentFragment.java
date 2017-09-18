package fossen.power.training_program_library;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import fossen.power.R;
import fossen.power.TPDBOpenHelper;
import fossen.power.TrainingProgram;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TrainingProgramContentFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TrainingProgramContentFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TrainingProgramContentFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public TrainingProgramContentFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TrainingProgramContentFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TrainingProgramContentFragment newInstance(String param1, String param2) {
        TrainingProgramContentFragment fragment = new TrainingProgramContentFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_training_program_content, container, false);
        ListView list_tpc = (ListView) view.findViewById(R.id.list_tpc);

        //加载训练方案
        TPDBOpenHelper tpdbOpenHelper = new TPDBOpenHelper(view.getContext());
        TrainingProgram trainingProgram = new TrainingProgram();

        //给listView添加headerView，用于显示训练方案的基本信息
        View header = inflater.inflate(R.layout.header_training_program_content,null);
        list_tpc.addHeaderView(header);
        View layout = (View) view.findViewById(R.id.layout_tpc);
        TextView textTitle = (TextView) view.findViewById(R.id.text_tpc_title);
        TextView textCircleGoal = (TextView) view.findViewById(R.id.text_tpc_circle_goal);
        final TextView textNote = (TextView) view.findViewById(R.id.text_tpc_note);
        final ImageView arrow = (ImageView) view.findViewById(R.id.arrow_tpc_note);
        textTitle.setText(trainingProgram.getName());
        textCircleGoal.setText(trainingProgram.getCircleGoal() );
        textNote.setText(trainingProgram.getNote());
        //设置监听器，实现点击缩放说明栏文字
        layout.setOnClickListener(new View.OnClickListener() {
            Boolean flag = true;
            @Override
            public void onClick(View v) {
                if(flag){
                    flag = false;
                    textNote.setMaxLines(20);
                    arrow.setRotation(180);
                }else {
                    flag = true;
                    textNote.setMaxLines(1);
                    arrow.setRotation(0);
                }
            }
        });

        //绑定配适器
        TrainingProgramContentAdapter tpcAdapter =
                new TrainingProgramContentAdapter(trainingProgram, view.getContext());
        list_tpc.setAdapter(tpcAdapter);

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
