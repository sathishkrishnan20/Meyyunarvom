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

    TextView doubtTitle11,doubtContent11,doubtTitle12,doubtContent12,doubtTitle13,doubtContent13,doubtTitle14,doubtContent14,doubtTitle15,doubtContent15,doubtTitle16,doubtContent16,doubtTitle17,doubtContent17,doubtTitle18,doubtContent18,doubtTitle19,doubtContent19,doubtTitle21,doubtContent21,doubtTitle22,doubtContent22,doubtTitle20,doubtContent20;
    TextView doubtTitle0,doubtTitle1,doubtTitle2,doubtTitle3,doubtTitle4,doubtTitle5,doubtTitle6,doubtTitle7,doubtTitle8,doubtTitle9,doubtTitle10;

    TextView doubtContent0,doubtContent1,doubtContent2,doubtContent3,doubtContent4,doubtContent5,doubtContent6,doubtContent7,doubtContent8,doubtContent9,doubtContent10;
    private final int COUNTDOWN_RUNNING_TIME = 500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doubts_manual);


        animationUp = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_up_doubt_manual);
        animationDown = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_down_doubt_manual);

        doubtTitle0 =(TextView)findViewById(R.id.doubt_title0);
        doubtContent0 = (TextView)findViewById(R.id.doubt_content0);
        doubtContent0.setVisibility(View.GONE);



        doubtTitle1 = (TextView) findViewById(R.id.doubt_title1);
        doubtContent1 = (TextView) findViewById(R.id.doubt_content1);
        doubtContent1.setVisibility(View.GONE);

        doubtTitle2 =(TextView)findViewById(R.id.doubt_title2);
        doubtContent2 = (TextView)findViewById(R.id.doubt_content2);
        doubtContent2.setVisibility(View.GONE);

        doubtTitle3 =(TextView)findViewById(R.id.doubt_title3);
        doubtContent3 = (TextView)findViewById(R.id.doubt_content3);
        doubtContent3.setVisibility(View.GONE);





        doubtTitle0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (doubtContent0.isShown()) {
                    //
                    doubtContent0.startAnimation(animationUp);

                    CountDownTimer countDownTimerStatic = new CountDownTimer(COUNTDOWN_RUNNING_TIME, 16) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                        }

                        @Override
                        public void onFinish() {
                            doubtTitle0.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_action_expand_doubt, 0);
                            doubtContent0.setVisibility(View.GONE);
                        }
                    };
                    countDownTimerStatic.start();
                } else {
                    doubtContent0.setVisibility(View.VISIBLE);
                    doubtContent0.startAnimation(animationDown);
                    doubtTitle0.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_action_collapse_doubt, 0);
                }

            }
        });






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

        doubtTitle11 =(TextView)findViewById(R.id.doubt_title11);
        doubtContent11 = (TextView)findViewById(R.id.doubt_content11);
        doubtContent11.setVisibility(View.GONE);



        doubtTitle11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (doubtContent11.isShown()) {
                    //
                    doubtContent11.startAnimation(animationUp);

                    CountDownTimer countDownTimerStatic = new CountDownTimer(COUNTDOWN_RUNNING_TIME, 16) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                        }

                        @Override
                        public void onFinish() {
                            doubtTitle11.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_action_expand_doubt, 0);
                            doubtContent11.setVisibility(View.GONE);
                        }
                    };
                    countDownTimerStatic.start();
                } else {
                    doubtContent11.setVisibility(View.VISIBLE);
                    doubtContent11.startAnimation(animationDown);
                    doubtTitle11.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_action_collapse_doubt, 0);
                }

            }
        });



        doubtTitle12 =(TextView)findViewById(R.id.doubt_title12);
        doubtContent12 = (TextView)findViewById(R.id.doubt_content12);
        doubtContent12.setVisibility(View.GONE);



        doubtTitle12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (doubtContent12.isShown()) {
                    //
                    doubtContent12.startAnimation(animationUp);

                    CountDownTimer countDownTimerStatic = new CountDownTimer(COUNTDOWN_RUNNING_TIME, 16) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                        }

                        @Override
                        public void onFinish() {
                            doubtTitle12.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_action_expand_doubt, 0);
                            doubtContent12.setVisibility(View.GONE);
                        }
                    };
                    countDownTimerStatic.start();
                } else {
                    doubtContent12.setVisibility(View.VISIBLE);
                    doubtContent12.startAnimation(animationDown);
                    doubtTitle12.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_action_collapse_doubt, 0);
                }

            }
        });


        doubtTitle13 =(TextView)findViewById(R.id.doubt_title13);
        doubtContent13 = (TextView)findViewById(R.id.doubt_content13);
        doubtContent13.setVisibility(View.GONE);



        doubtTitle13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (doubtContent13.isShown()) {
                    //
                    doubtContent13.startAnimation(animationUp);

                    CountDownTimer countDownTimerStatic = new CountDownTimer(COUNTDOWN_RUNNING_TIME, 16) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                        }

                        @Override
                        public void onFinish() {
                            doubtTitle13.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_action_expand_doubt, 0);
                            doubtContent13.setVisibility(View.GONE);
                        }
                    };
                    countDownTimerStatic.start();
                } else {
                    doubtContent13.setVisibility(View.VISIBLE);
                    doubtContent13.startAnimation(animationDown);
                    doubtTitle13.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_action_collapse_doubt, 0);
                }

            }
        });




        doubtTitle14 =(TextView)findViewById(R.id.doubt_title14);
        doubtContent14 = (TextView)findViewById(R.id.doubt_content14);
        doubtContent14.setVisibility(View.GONE);



        doubtTitle14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (doubtContent14.isShown()) {
                    //
                    doubtContent14.startAnimation(animationUp);

                    CountDownTimer countDownTimerStatic = new CountDownTimer(COUNTDOWN_RUNNING_TIME, 16) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                        }

                        @Override
                        public void onFinish() {
                            doubtTitle14.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_action_expand_doubt, 0);
                            doubtContent14.setVisibility(View.GONE);
                        }
                    };
                    countDownTimerStatic.start();
                } else {
                    doubtContent14.setVisibility(View.VISIBLE);
                    doubtContent14.startAnimation(animationDown);
                    doubtTitle14.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_action_collapse_doubt, 0);
                }

            }
        });





        doubtTitle15 =(TextView)findViewById(R.id.doubt_title15);
        doubtContent15 = (TextView)findViewById(R.id.doubt_content15);
        doubtContent15.setVisibility(View.GONE);



        doubtTitle15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (doubtContent15.isShown()) {
                    //
                    doubtContent15.startAnimation(animationUp);

                    CountDownTimer countDownTimerStatic = new CountDownTimer(COUNTDOWN_RUNNING_TIME, 16) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                        }

                        @Override
                        public void onFinish() {
                            doubtTitle15.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_action_expand_doubt, 0);
                            doubtContent15.setVisibility(View.GONE);
                        }
                    };
                    countDownTimerStatic.start();
                } else {
                    doubtContent15.setVisibility(View.VISIBLE);
                    doubtContent15.startAnimation(animationDown);
                    doubtTitle15.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_action_collapse_doubt, 0);
                }

            }
        });



        doubtTitle16 =(TextView)findViewById(R.id.doubt_title16);
        doubtContent16 = (TextView)findViewById(R.id.doubt_content16);
        doubtContent16.setVisibility(View.GONE);



        doubtTitle16.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (doubtContent16.isShown()) {
                    //
                    doubtContent16.startAnimation(animationUp);

                    CountDownTimer countDownTimerStatic = new CountDownTimer(COUNTDOWN_RUNNING_TIME, 16) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                        }

                        @Override
                        public void onFinish() {
                            doubtTitle16.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_action_expand_doubt, 0);
                            doubtContent16.setVisibility(View.GONE);
                        }
                    };
                    countDownTimerStatic.start();
                } else {
                    doubtContent16.setVisibility(View.VISIBLE);
                    doubtContent16.startAnimation(animationDown);
                    doubtTitle16.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_action_collapse_doubt, 0);
                }

            }
        });




        doubtTitle17 =(TextView)findViewById(R.id.doubt_title17);
        doubtContent17 = (TextView)findViewById(R.id.doubt_content17);
        doubtContent17.setVisibility(View.GONE);



        doubtTitle17.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (doubtContent17.isShown()) {
                    //
                    doubtContent17.startAnimation(animationUp);

                    CountDownTimer countDownTimerStatic = new CountDownTimer(COUNTDOWN_RUNNING_TIME, 16) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                        }

                        @Override
                        public void onFinish() {
                            doubtTitle17.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_action_expand_doubt, 0);
                            doubtContent17.setVisibility(View.GONE);
                        }
                    };
                    countDownTimerStatic.start();
                } else {
                    doubtContent17.setVisibility(View.VISIBLE);
                    doubtContent17.startAnimation(animationDown);
                    doubtTitle17.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_action_collapse_doubt, 0);
                }

            }
        });


        doubtTitle18 =(TextView)findViewById(R.id.doubt_title18);
        doubtContent18 = (TextView)findViewById(R.id.doubt_content18);
        doubtContent18.setVisibility(View.GONE);



        doubtTitle18.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (doubtContent18.isShown()) {
                    //
                    doubtContent18.startAnimation(animationUp);

                    CountDownTimer countDownTimerStatic = new CountDownTimer(COUNTDOWN_RUNNING_TIME, 16) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                        }

                        @Override
                        public void onFinish() {
                            doubtTitle18.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_action_expand_doubt, 0);
                            doubtContent18.setVisibility(View.GONE);
                        }
                    };
                    countDownTimerStatic.start();
                } else {
                    doubtContent18.setVisibility(View.VISIBLE);
                    doubtContent18.startAnimation(animationDown);
                    doubtTitle18.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_action_collapse_doubt, 0);
                }

            }
        });


        doubtTitle19 =(TextView)findViewById(R.id.doubt_title19);
        doubtContent19 = (TextView)findViewById(R.id.doubt_content19);
        doubtContent19.setVisibility(View.GONE);



        doubtTitle19.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (doubtContent19.isShown()) {
                    //
                    doubtContent19.startAnimation(animationUp);

                    CountDownTimer countDownTimerStatic = new CountDownTimer(COUNTDOWN_RUNNING_TIME, 16) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                        }

                        @Override
                        public void onFinish() {
                            doubtTitle19.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_action_expand_doubt, 0);
                            doubtContent19.setVisibility(View.GONE);
                        }
                    };
                    countDownTimerStatic.start();
                } else {
                    doubtContent19.setVisibility(View.VISIBLE);
                    doubtContent19.startAnimation(animationDown);
                    doubtTitle19.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_action_collapse_doubt, 0);
                }

            }
        });

        doubtTitle20 =(TextView)findViewById(R.id.doubt_title20);
        doubtContent20 = (TextView)findViewById(R.id.doubt_content20);
        doubtContent20.setVisibility(View.GONE);



        doubtTitle20.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (doubtContent20.isShown()) {
                    //
                    doubtContent20.startAnimation(animationUp);

                    CountDownTimer countDownTimerStatic = new CountDownTimer(COUNTDOWN_RUNNING_TIME, 16) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                        }

                        @Override
                        public void onFinish() {
                            doubtTitle20.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_action_expand_doubt, 0);
                            doubtContent20.setVisibility(View.GONE);
                        }
                    };
                    countDownTimerStatic.start();
                } else {
                    doubtContent20.setVisibility(View.VISIBLE);
                    doubtContent20.startAnimation(animationDown);
                    doubtTitle20.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_action_collapse_doubt, 0);
                }

            }
        });





        doubtTitle21 =(TextView)findViewById(R.id.doubt_title21);
        doubtContent21 = (TextView)findViewById(R.id.doubt_content21);
        doubtContent21.setVisibility(View.GONE);



        doubtTitle21.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (doubtContent21.isShown()) {
                    //
                    doubtContent21.startAnimation(animationUp);

                    CountDownTimer countDownTimerStatic = new CountDownTimer(COUNTDOWN_RUNNING_TIME, 16) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                        }

                        @Override
                        public void onFinish() {
                            doubtTitle21.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_action_expand_doubt, 0);
                            doubtContent21.setVisibility(View.GONE);
                        }
                    };
                    countDownTimerStatic.start();
                } else {
                    doubtContent21.setVisibility(View.VISIBLE);
                    doubtContent21.startAnimation(animationDown);
                    doubtTitle21.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_action_collapse_doubt, 0);
                }

            }
        });



        doubtTitle22 =(TextView)findViewById(R.id.doubt_title22);
        doubtContent22 = (TextView)findViewById(R.id.doubt_content22);
        doubtContent22.setVisibility(View.GONE);



        doubtTitle22.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (doubtContent22.isShown()) {
                    //
                    doubtContent22.startAnimation(animationUp);

                    CountDownTimer countDownTimerStatic = new CountDownTimer(COUNTDOWN_RUNNING_TIME, 16) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                        }

                        @Override
                        public void onFinish() {
                            doubtTitle22.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_action_expand_doubt, 0);
                            doubtContent22.setVisibility(View.GONE);
                        }
                    };
                    countDownTimerStatic.start();
                } else {
                    doubtContent22.setVisibility(View.VISIBLE);
                    doubtContent22.startAnimation(animationDown);
                    doubtTitle22.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_action_collapse_doubt, 0);
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
