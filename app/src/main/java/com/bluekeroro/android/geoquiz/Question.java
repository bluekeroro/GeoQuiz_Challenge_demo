package com.bluekeroro.android.geoquiz;

/**
 * Created by BlueKeroro on 2018/3/22.
 */
public class Question {
    private int mTextResID;
    private boolean mAnswerTrue;

    public Question(int textResID,boolean answerTrue){
        mTextResID=textResID;
        mAnswerTrue=answerTrue;
    }

    public int getTextResID() {
        return mTextResID;
    }

    public boolean isAnswerTrue() {
        return mAnswerTrue;
    }
}
