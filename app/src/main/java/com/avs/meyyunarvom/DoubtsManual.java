package com.avs.meyyunarvom;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.ToggleButton;

public class DoubtsManual extends AppCompatActivity {


    private Animation animationUp;
    private Animation animationDown;

    TextView doubtTitle1,doubtTitle2,doubtTitle3,doubtTitle4,doubtTitle5,doubtTitle6,doubtTitle7,doubtTitle8,doubtTitle9,doubtTitle10;

    TextView doubtContent1,doubtContent2,doubtContent3,doubtContent4,doubtContent5,doubtContent6,doubtContent7,doubtContent8,doubtContent9,doubtContent10;
    private final int COUNTDOWN_RUNNING_TIME = 500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doubts_manual);


        animationUp = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_up_doubt_manual);
        animationDown = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_down_doubt_manual);




        doubtTitle1 = (TextView) findViewById(R.id.doubt_title1);
        doubtContent1 = (TextView) findViewById(R.id.doubt_content1);
        doubtContent1.setVisibility(View.GONE);

        doubtTitle2 =(TextView)findViewById(R.id.doubt_title2);
        doubtContent2 = (TextView)findViewById(R.id.doubt_content2);
        doubtContent2.setVisibility(View.GONE);

        doubtTitle3 =(TextView)findViewById(R.id.doubt_title3);
        doubtContent3 = (TextView)findViewById(R.id.doubt_content3);
        doubtContent3.setVisibility(View.GONE);




        doubtTitle1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(doubtContent1.isShown()){
                   //
                    doubtContent1.startAnimation(animationUp);

                    CountDownTimer countDownTimerStatic = new CountDownTimer(COUNTDOWN_RUNNING_TIME, 16) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                        }

                        @Override
                        public void onFinish() {
                            doubtTitle1.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_action_expand_doubt,0);
                            doubtContent1.setVisibility(View.GONE);
                        }
                    };
                    countDownTimerStatic.start();
                }
                else{
                    doubtContent1.setVisibility(View.VISIBLE);
                    doubtContent1.startAnimation(animationDown);
                    doubtTitle1.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_action_collapse_doubt,0);
                }
            }
        });

        doubtTitle2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (doubtContent2.isShown()) {
                    //
                    doubtContent2.startAnimation(animationUp);

                    CountDownTimer countDownTimerStatic = new CountDownTimer(COUNTDOWN_RUNNING_TIME, 16) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                        }

                        @Override
                        public void onFinish() {
                            doubtTitle2.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_action_expand_doubt, 0);
                            doubtContent2.setVisibility(View.GONE);
                        }
                    };
                    countDownTimerStatic.start();
                } else {
                    doubtContent2.setVisibility(View.VISIBLE);
                    doubtContent2.startAnimation(animationDown);
                    doubtTitle2.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_action_collapse_doubt, 0);
                }

            }
        });


        doubtTitle3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (doubtContent3.isShown()) {
                    //
                    doubtContent3.startAnimation(animationUp);

                    CountDownTimer countDownTimerStatic = new CountDownTimer(COUNTDOWN_RUNNING_TIME, 16) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                        }

                        @Override
                        public void onFinish() {
                            doubtTitle3.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_action_expand_doubt, 0);
                            doubtContent3.setVisibility(View.GONE);
                        }
                    };
                    countDownTimerStatic.start();
                } else {
                    doubtContent3.setVisibility(View.VISIBLE);
                    doubtContent3.startAnimation(animationDown);
                    doubtTitle3.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_action_collapse_doubt, 0);
                }

            }
        });



        doubtTitle4 =(TextView)findViewById(R.id.doubt_title4);
        doubtContent4 = (TextView)findViewById(R.id.doubt_content4);
        doubtContent4.setVisibility(View.GONE);



        doubtTitle4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (doubtContent4.isShown()) {
                    //
                    doubtContent4.startAnimation(animationUp);

                    CountDownTimer countDownTimerStatic = new CountDownTimer(COUNTDOWN_RUNNING_TIME, 16) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                        }

                        @Override
                        public void onFinish() {
                            doubtTitle4.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_action_expand_doubt, 0);
                            doubtContent4.setVisibility(View.GONE);
                        }
                    };
                    countDownTimerStatic.start();
                } else {
                    doubtContent4.setVisibility(View.VISIBLE);
                    doubtContent4.startAnimation(animationDown);
                    doubtTitle4.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_action_collapse_doubt, 0);
                }

            }
        });


        doubtTitle5 =(TextView)findViewById(R.id.doubt_title5);
        doubtContent5 = (TextView)findViewById(R.id.doubt_content5);
        doubtContent5.setVisibility(View.GONE);



        doubtTitle5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (doubtContent5.isShown()) {
                    //
                    doubtContent5.startAnimation(animationUp);

                    CountDownTimer countDownTimerStatic = new CountDownTimer(COUNTDOWN_RUNNING_TIME, 16) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                        }

                        @Override
                        public void onFinish() {
                            doubtTitle5.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_action_expand_doubt, 0);
                            doubtContent5.setVisibility(View.GONE);
                        }
                    };
                    countDownTimerStatic.start();
                } else {
                    doubtContent5.setVisibility(View.VISIBLE);
                    doubtContent5.startAnimation(animationDown);
                    doubtTitle5.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_action_collapse_doubt, 0);
                }

            }
        });


        doubtTitle6 =(TextView)findViewById(R.id.doubt_title6);
        doubtContent6 = (TextView)findViewById(R.id.doubt_content6);
        doubtContent6.setVisibility(View.GONE);



        doubtTitle6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (doubtContent6.isShown()) {
                    //
                    doubtContent6.startAnimation(animationUp);

                    CountDownTimer countDownTimerStatic = new CountDownTimer(COUNTDOWN_RUNNING_TIME, 16) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                        }

                        @Override
                        public void onFinish() {
                            doubtTitle6.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_action_expand_doubt, 0);
                            doubtContent6.setVisibility(View.GONE);
                        }
                    };
                    countDownTimerStatic.start();
                } else {
                    doubtContent6.setVisibility(View.VISIBLE);
                    doubtContent6.startAnimation(animationDown);
                    doubtTitle6.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_action_collapse_doubt, 0);
                }

            }
        });



        doubtTitle7 =(TextView)findViewById(R.id.doubt_title7);
        doubtContent7 = (TextView)findViewById(R.id.doubt_content7);
        doubtContent7.setVisibility(View.GONE);



        doubtTitle7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (doubtContent7.isShown()) {
                    //
                    doubtContent7.startAnimation(animationUp);

                    CountDownTimer countDownTimerStatic = new CountDownTimer(COUNTDOWN_RUNNING_TIME, 16) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                        }

                        @Override
                        public void onFinish() {
                            doubtTitle7.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_action_expand_doubt, 0);
                            doubtContent7.setVisibility(View.GONE);
                        }
                    };
                    countDownTimerStatic.start();
                } else {
                    doubtContent7.setVisibility(View.VISIBLE);
                    doubtContent7.startAnimation(animationDown);
                    doubtTitle7.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_action_collapse_doubt, 0);
                }

            }
        });



        doubtTitle8 =(TextView)findViewById(R.id.doubt_title8);
        doubtContent8 = (TextView)findViewById(R.id.doubt_content8);
        doubtContent8.setVisibility(View.GONE);



        doubtTitle8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (doubtContent8.isShown()) {
                    //
                    doubtContent8.startAnimation(animationUp);

                    CountDownTimer countDownTimerStatic = new CountDownTimer(COUNTDOWN_RUNNING_TIME, 16) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                        }

                        @Override
                        public void onFinish() {
                            doubtTitle8.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_action_expand_doubt, 0);
                            doubtContent8.setVisibility(View.GONE);
                        }
                    };
                    countDownTimerStatic.start();
                } else {
                    doubtContent8.setVisibility(View.VISIBLE);
                    doubtContent8.startAnimation(animationDown);
                    doubtTitle8.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_action_collapse_doubt, 0);
                }

            }
        });

        doubtTitle9 =(TextView)findViewById(R.id.doubt_title9);
        doubtContent9 = (TextView)findViewById(R.id.doubt_content9);
        doubtContent9.setVisibility(View.GONE);



        doubtTitle9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (doubtContent9.isShown()) {
                    //
                    doubtContent9.startAnimation(animationUp);

                    CountDownTimer countDownTimerStatic = new CountDownTimer(COUNTDOWN_RUNNING_TIME, 16) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                        }

                        @Override
                        public void onFinish() {
                            doubtTitle9.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_action_expand_doubt, 0);
                            doubtContent9.setVisibility(View.GONE);
                        }
                    };
                    countDownTimerStatic.start();
                } else {
                    doubtContent9.setVisibility(View.VISIBLE);
                    doubtContent9.startAnimation(animationDown);
                    doubtTitle9.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_action_collapse_doubt, 0);
                }

            }
        });


        doubtTitle10 =(TextView)findViewById(R.id.doubt_title10);
        doubtContent10 = (TextView)findViewById(R.id.doubt_content10);
        doubtContent10.setVisibility(View.GONE);



        doubtTitle10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (doubtContent10.isShown()) {
                    //
                    doubtContent10.startAnimation(animationUp);

                    CountDownTimer countDownTimerStatic = new CountDownTimer(COUNTDOWN_RUNNING_TIME, 16) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                        }

                        @Override
                        public void onFinish() {
                            doubtTitle10.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_action_expand_doubt, 0);
                            doubtContent10.setVisibility(View.GONE);
                        }
                    };
                    countDownTimerStatic.start();
                } else {
                    doubtContent10.setVisibility(View.VISIBLE);
                    doubtContent10.startAnimation(animationDown);
                    doubtTitle10.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_action_collapse_doubt, 0);
                }

            }
        });




            }
/*
    @Override
    public void onClick(View v) {


        if(doubtContent1.getVisibility() == View.VISIBLE || doubtContent2.getVisibility() == View.VISIBLE || doubtContent3.getVisibility() == View.VISIBLE )
        {
            doubtContent1.setVisibility(View.GONE);
            doubtContent2.setVisibility(View.GONE);
            doubtContent3.setVisibility(View.GONE);
        }

        if(v== doubtTitle1)
        {
            if(doubtContent1.getVisibility() == View.VISIBLE){
                //
                doubtContent1.startAnimation(animationUp);

                CountDownTimer countDownTimerStatic = new CountDownTimer(COUNTDOWN_RUNNING_TIME, 16) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                    }

                    @Override
                    public void onFinish() {
                        doubtTitle1.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_action_expand_doubt,0);
                        doubtContent1.setVisibility(View.GONE);
                    }
                };
                countDownTimerStatic.start();
            }
            else {
                doubtContent1.setVisibility(View.VISIBLE);
                doubtContent1.startAnimation(animationDown);
                doubtTitle1.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_action_collapse_doubt,0);
            }
        }

        else if(v== doubtTitle2)
        {
            if(doubtContent2.getVisibility() == View.VISIBLE){
                //
                doubtContent2.startAnimation(animationUp);

                CountDownTimer countDownTimerStatic = new CountDownTimer(COUNTDOWN_RUNNING_TIME, 16) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                    }

                    @Override
                    public void onFinish() {
                        doubtTitle2.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_action_expand_doubt,0);
                        doubtContent2.setVisibility(View.GONE);
                    }
                };
                countDownTimerStatic.start();
            }
            else{
                doubtContent2.setVisibility(View.VISIBLE);
                doubtContent2.startAnimation(animationDown);
                doubtTitle2.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_action_collapse_doubt,0);
            }

        }

        else if(v== doubtTitle3)
        {
            if(doubtContent3.getVisibility() == View.VISIBLE){
                //
                doubtContent3.startAnimation(animationUp);

                CountDownTimer countDownTimerStatic = new CountDownTimer(COUNTDOWN_RUNNING_TIME, 16) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                    }

                    @Override
                    public void onFinish() {
                        doubtTitle3.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_action_expand_doubt,0);
                        doubtContent3.setVisibility(View.GONE);
                    }
                };
                countDownTimerStatic.start();
            }
            else{
                doubtContent3.setVisibility(View.VISIBLE);
                doubtContent3.startAnimation(animationDown);
                doubtTitle3.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_action_collapse_doubt,0);
            }
        }






    }

    */
}
