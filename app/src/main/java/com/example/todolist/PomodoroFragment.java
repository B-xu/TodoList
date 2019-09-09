package com.example.todolist;

import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


public class PomodoroFragment extends Fragment {
    private CountDownTimer mCountDownTimer;
    private LinearLayout mLayout;
    private TextView mMinuteDisplay;
    private TextView mSecondDisplay;
    private Button mStarter;
    private Button mStopper;
    private long mDuration;
    private int mMinutes;
    private int mSeconds;
    private boolean isPaused;
    private boolean isStarted=false;
    private boolean isBreak = false;
    private View v;

    long millisRemaining = 0;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.pomodoro_timer, container, false );



        v.setBackgroundResource(R.color.colorAccent);
        mMinuteDisplay = v.findViewById(R.id.minute_display);
        mSecondDisplay = v.findViewById(R.id.second_display);
        mStarter = v.findViewById(R.id.starter_pauser_resumer);
        mStopper = v.findViewById(R.id.stopper_done);
        isPaused = false;
        mDuration = 25*60000;

        mMinutes = (int) mDuration / 60000;
        mSeconds = (int) (mDuration %60000) /1000;

        setTime(mMinutes, mSeconds);
        mStarter.setText(R.string.start);
        mStopper.setText(R.string.stop);


        mStarter.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                if (!isStarted){
                    isStarted = true;
                    mCountDownTimer = new PausableCountdown
                            (mDuration,1000).start();
                    mStarter.setText(R.string.pause);
                } else {
                    isPaused = !isPaused;
                    if (isPaused) {
                        mCountDownTimer.cancel();
                        mStarter.setText(R.string.resume);

                    } else {
                        mStarter.setText(R.string.pause);
                        mCountDownTimer = new PausableCountdown(millisRemaining,
                                1000).start();
                    }
                }
            }
        });

        mStopper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    mCountDownTimer.cancel();
                    reset();
                } catch (NullPointerException e){
                    return;
                }
            }
        });



        return v;

    }

    private void setTime(int mMinutes, int mSeconds) {
        mMinuteDisplay.setText(mMinutes+"");
        if (mSeconds ==0){
            mSecondDisplay.setText(mSeconds+"0");
        }else if (mSeconds >= 10){

            mSecondDisplay.setText(mSeconds+"");
        } else {
            mSecondDisplay.setText("0"+mSeconds);
        }
    }

    private void reset(){
        mDuration = 25*60000;
        mMinuteDisplay.setText("25");
        mSecondDisplay.setText("00");
        mStarter.setText(R.string.start);
        isPaused = false;
        isStarted = false;

        v.setBackgroundResource(R.color.colorAccent);
    }

    private void setupBreak() {
        getView().setBackgroundResource(R.color.green);
        mCountDownTimer=new PausableCountdown(5*60000, 1000).start();
    }

    class PausableCountdown extends CountDownTimer {

        public PausableCountdown(long millisInFuture, long countDownInterval) {
            super(millisInFuture, 1000);
        }

        @Override
        public void onTick(long l) {

            millisRemaining = l;
            setTime((int)l/(60*1000),(int) l%60000/1000);
        }

        @Override
        public void onFinish() {
            try {
                MediaPlayer.create(getContext(), R.raw.bell).start();
            } catch (Exception e) {
                e.printStackTrace();
            }

            isBreak = !isBreak;
            if (isBreak) {
                setupBreak();
            } else {
                reset();


            }
        }

    }
}
