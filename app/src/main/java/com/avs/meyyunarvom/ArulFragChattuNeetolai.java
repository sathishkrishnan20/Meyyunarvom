package com.avs.meyyunarvom;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.avs.db.ArulThingalPathamDAO;
import com.avs.db.ChattuDAO;
import com.avs.db.MeyyunarvomDB;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ArulFragChattuNeetolai.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ArulFragChattuNeetolai#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ArulFragChattuNeetolai extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ArulFragChattuNeetolai() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ArulFragChattuNeetolai.
     */
    // TODO: Rename and change types and number of parameters
    public static ArulFragChattuNeetolai newInstance(String param1, String param2) {
        ArulFragChattuNeetolai fragment = new ArulFragChattuNeetolai();
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


    private TextView title, content;
    private Button next, previous;

    private int track =1;


    ScrollView scrollView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_arul_frag_chattu_neetolai, container, false);
        scrollView =(ScrollView)view.findViewById(R.id.scrollView_chattu);


        title = (TextView) view.findViewById(R.id.chatu_titleid1);
        content = (TextView) view.findViewById(R.id.chatu_contentid1);
        next = (Button) view.findViewById(R.id.chatunextbtn);
        previous = (Button) view.findViewById(R.id.chatupreviousbtn);

        try {
            MeyyunarvomDB db = new MeyyunarvomDB(getActivity());
            ChattuDAO dao = db.getChattuContents(track);
            title.setText(dao.getTitle());
            content.setText(dao.getContent());


            next.setOnClickListener(new View.OnClickListener() {

                public void onClick(View view) {

                    if (track < 11) {
                        track = track + 1;
                    }
                    scrollView.fullScroll(ScrollView.FOCUS_UP);
                    MeyyunarvomDB db = new MeyyunarvomDB(getActivity());


                    ChattuDAO dao = db.getChattuContents(track);
                    title.setText(dao.getTitle());
                    content.setText(dao.getContent());

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
                    ChattuDAO dao = db.getChattuContents(track);
                    title.setText(dao.getTitle());
                    content.setText(dao.getContent());

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
