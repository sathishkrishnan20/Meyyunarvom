package com.avs.meyyunarvom;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

public class DoubtsManual extends AppCompatActivity {

    private TextView txtContent,txtContent1;
    private Animation animationUp;
    private Animation animationDown;

    TextView txtTitle;

    private final int COUNTDOWN_RUNNING_TIME = 500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doubts_manual);


        animationUp = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_up_doubt_manual);
        animationDown = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_down_doubt_manual);

        txtContent = (TextView) findViewById(R.id.title_text);
        txtTitle = (TextView) findViewById(R.id.content_text);
        txtContent.setVisibility(View.GONE);

        txtTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(txtContent.isShown()){

                    txtTitle.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.ic_action_expand,0);
                    txtContent.startAnimation(animationUp);


                    CountDownTimer countDownTimerStatic = new CountDownTimer(COUNTDOWN_RUNNING_TIME, 16) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                        }

                        @Override
                        public void onFinish() {
                            txtContent.setVisibility(View.GONE);
                        }
                    };
                    countDownTimerStatic.start();

                }
                else{
                    txtTitle.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.ic_action_collapse,0);
                    txtContent.setVisibility(View.VISIBLE);
                    txtContent.startAnimation(animationDown);
                }
            }
        });



        txtContent1 = (TextView) findViewById(R.id.title1_text);
        TextView txtTitle1 = (TextView) findViewById(R.id.content1_text);
        txtContent1.setVisibility(View.GONE);


        txtTitle1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(txtContent1.isShown()){
                   //
                    txtContent1.startAnimation(animationUp);

                    CountDownTimer countDownTimerStatic = new CountDownTimer(COUNTDOWN_RUNNING_TIME, 16) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                        }

                        @Override
                        public void onFinish() {
                            //holder.contentLayout.setVisibility(View.GONE);
                            txtContent1.setVisibility(View.GONE);
                        }
                    };
                    countDownTimerStatic.start();
                }
                else{
                    txtContent1.setVisibility(View.VISIBLE);
                    txtContent1.startAnimation(animationDown);
                }
            }
        });

    }
}
