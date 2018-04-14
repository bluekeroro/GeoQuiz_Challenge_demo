package com.bluekeroro.android.geoquiz;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.Button;
import android.widget.TextView;

public class CheatActivity extends AppCompatActivity {
    private boolean mAnswerIsTrue;
    private TextView mAnswerTextView;
    private TextView mShowAPIlevel;
    private Button mShowAnswer;
    private static final String TAG="CheatActivity";
    private static final String KEY_ISCHEATER="IsCheat";
    private boolean mIsCheater=false;

    private static final String EXTRA_ANSWER_IS_TRUE="com.bluekeroro.android.geoquiz.answer_is_true";
    private static final String EXTRA_ANSWER_SHOW="com.bluekeroro.android.geoquiz.answer_show";
    public static Intent newIntent(Context packageContext, boolean answerIsTure){
        Intent i=new Intent(packageContext,CheatActivity.class);
        i.putExtra(EXTRA_ANSWER_IS_TRUE,answerIsTure);
        return i;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);

        mAnswerIsTrue=getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE,false);
        mAnswerTextView=(TextView)findViewById(R.id.answerTextView);
        mShowAPIlevel=(TextView)findViewById(R.id.showAPIlevel);
        mShowAPIlevel.setText("API level "+Build.VERSION.SDK_INT);

        mShowAnswer=(Button)findViewById(R.id.showAnswerButton);
        mShowAnswer.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                mAnswerTextView.setText(mAnswerIsTrue?R.string.true_button:R.string.false_button);
                mIsCheater=true;
                setAnswerShowResult(mIsCheater);
                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
                    int cx = mShowAnswer.getWidth() / 2;
                    int cy = mShowAnswer.getHeight() / 2;
                    float radius = mShowAnswer.getWidth();
                    Animator anim = ViewAnimationUtils.createCircularReveal(mShowAnswer, cx, cy, radius, 0);
                    anim.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            mShowAnswer.setVisibility(View.INVISIBLE);
                        }
                    });
                    anim.start();
                }else{
                    mShowAnswer.setVisibility(View.INVISIBLE);
                }

            }
        });
        if(savedInstanceState!=null){
            mAnswerTextView.setText(mAnswerIsTrue?R.string.true_button:R.string.false_button);
            mIsCheater=savedInstanceState.getBoolean(KEY_ISCHEATER,false);
            setAnswerShowResult(mIsCheater);
            mShowAnswer.setVisibility(View.INVISIBLE);
        }
    }
    private void setAnswerShowResult(boolean isAnswerShow){
        Intent data=new Intent();
        data.putExtra(EXTRA_ANSWER_SHOW,isAnswerShow);
        setResult(RESULT_OK,data);
    }
    public static boolean wasAnswerShow(Intent result){
        return result.getBooleanExtra(EXTRA_ANSWER_SHOW,false);
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG,"onSaveInstanceState");
        savedInstanceState.putBoolean(KEY_ISCHEATER,mIsCheater);
    }
}
