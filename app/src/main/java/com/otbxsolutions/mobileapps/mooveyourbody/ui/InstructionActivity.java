package com.otbxsolutions.mobileapps.mooveyourbody.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.otbxsolutions.mobileapps.mooveyourbody.R;
import com.otbxsolutions.mobileapps.mooveyourbody.model.Instruction;
import com.otbxsolutions.mobileapps.mooveyourbody.model.Page;

public class InstructionActivity extends AppCompatActivity {
    private Instruction mStory = new Instruction();

    private TextView mTextView;
    private Button mPrev;
    private Button mNext;
    private Page mCurrentPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instruction);

        mTextView = (TextView)findViewById(R.id.txtInstructions);
        mPrev = (Button)findViewById(R.id.btnPrev);
        mNext = (Button)findViewById(R.id.btnNext);

        loadPage(0);
    }

    private void loadPage(int choice){
        mCurrentPage = mStory.getPage(choice);

        String pageText = mCurrentPage.getText();
        mTextView.setText(pageText);

        if(mCurrentPage.isFinal()) {
            //mPrev.setVisibility(View.INVISIBLE);
            mNext.setText("Back to Main Menu");

            mNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        } else {
            mPrev.setText(mCurrentPage.getChoice1().getText());
            mNext.setText(mCurrentPage.getChoice2().getText());

            mPrev.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int nextPage = mCurrentPage.getChoice1().getNextPage();
                    loadPage(nextPage);
                }
            });

            mNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int nextPage = mCurrentPage.getChoice2().getNextPage();
                    loadPage(nextPage);
                }
            });
        }
    }
}
