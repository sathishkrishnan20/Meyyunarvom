package com.avs.meyyunarvom;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by SATHISH on 1/2/2017.
 */

public class AnswerPopup extends Activity
{

    private TextView answerTitle , answerExplain;


    private JSONObject jsonObject;
    private JSONArray result;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup_answer);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        Intent intent = getIntent();
        String responseData = intent.getStringExtra("Response");
        int position = intent.getIntExtra("Position",1);

        answerTitle =(TextView)findViewById(R.id.question_textview);
        answerExplain =(TextView)findViewById(R.id.answer_textview);

        answerTitle.setText(getQuestion(position,responseData));
        answerExplain.setText(getAnswers(position,responseData));

        int width= displayMetrics.widthPixels;
        int height =displayMetrics.heightPixels;

        getWindow().setLayout((int)(width * 0.8), (int)(height* 0.6));

    }


    private String getQuestion(int position ,String response) {
        String quetionDbText = "";
        try {


            //Getting object of given index
            jsonObject = new JSONObject(response);
            result = jsonObject.getJSONArray("result");


            JSONObject json = result.getJSONObject(position);

            //Fetching name from that object
            quetionDbText = json.getString("question");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //Returning the name
        return quetionDbText;
    }



    private String getAnswers(int position, String response) {
        String answerDbText = "";
        try {
            //Getting object of given index

            jsonObject = new JSONObject(response);
            result = jsonObject.getJSONArray("result");



            JSONObject json = result.getJSONObject(position);

            //Fetching name from that object
            answerDbText = json.getString("answer");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //Returning the name
        return answerDbText;
    }



}
