package com.avs.meyyunarvom;

import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.avs.db.AkilathirattuDAO;
import com.avs.db.AyyaHistoryDAO;
import com.avs.db.MeyyunarvomDB;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HistoryAyyaVaikundar.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HistoryAyyaVaikundar#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HistoryAyyaVaikundar extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public HistoryAyyaVaikundar() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HistoryAyyaVaikundar.
     */
    // TODO: Rename and change types and number of parameters
    public static HistoryAyyaVaikundar newInstance(String param1, String param2) {
        HistoryAyyaVaikundar fragment = new HistoryAyyaVaikundar();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    private TextView ayyaHistorytitle, ayyaHistoryContent;
    private Button next, previous;

    private int track =1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    ScrollView scrollView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_history_ayya_vaikundar, container, false);

        scrollView =(ScrollView)view.findViewById(R.id.scroll_frag_history_ayya);

        ayyaHistorytitle = (TextView) view.findViewById(R.id.ayya_histry_title);
        ayyaHistoryContent = (TextView) view.findViewById(R.id.ayya_history_content);
        next = (Button) view.findViewById(R.id.ayya_history_nextbtn);
        previous = (Button) view.findViewById(R.id.ayya_history_previousbtn);

try {
    MeyyunarvomDB db = new MeyyunarvomDB(getContext());
    AyyaHistoryDAO dao = db.getayyaHistoryContent(track);
    ayyaHistorytitle.setText(dao.getTitle());
    ayyaHistoryContent.setText(dao.getContent());


    next.setOnClickListener(new View.OnClickListener() {

        public void onClick(View view) {

            if (track < 9) {
                track = track + 1;
            }

            scrollView.fullScroll(ScrollView.FOCUS_UP);
            MeyyunarvomDB db = new MeyyunarvomDB(getActivity());

            AyyaHistoryDAO dao = db.getayyaHistoryContent(track);
            ayyaHistorytitle.setText(dao.getTitle());
            ayyaHistoryContent.setText(dao.getContent());

        }

    });

    previous.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {


            if (track > 1) {
                track = track - 1;
            }
            scrollView.fullScroll(ScrollView.FOCUS_UP);
            MeyyunarvomDB db = new MeyyunarvomDB(getActivity());
            AyyaHistoryDAO dao = db.getayyaHistoryContent(track);
            ayyaHistorytitle.setText(dao.getTitle());
            ayyaHistoryContent.setText(dao.getContent());

        }
    });


}catch (Exception e)
{
    Toast.makeText(getActivity(),e.toString(),Toast.LENGTH_LONG).show();
}



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
