package com.otbxsolutions.mobileapps.mooveyourbody.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.otbxsolutions.mobileapps.mooveyourbody.R;
import com.otbxsolutions.mobileapps.mooveyourbody.model.DBAdapter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created By: Neelan Joachimpillai
 * Date: 12/1/2016
 * Note: This is the Main class for the Instruction page.
 */
public class MainActivity extends AppCompatActivity {
    private Button mInstructionButton, mPlayButton, mScoreButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mInstructionButton=(Button)findViewById(R.id.btnInstructions);
        mPlayButton=(Button)findViewById(R.id.btnPlay);
        mScoreButton=(Button)findViewById(R.id.btnScore);

        mInstructionButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(MainActivity.this, InstructionActivity.class);
                startActivity(intent);
            }
        });

        mPlayButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(MainActivity.this, GameActivity.class);
                startActivity(intent);
            }
        });

        mScoreButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(MainActivity.this, ScoreActivity.class);
                startActivity(intent);
            }
        });

        DBAdapter db = new DBAdapter(this);
        db.open();

        try {
            String destPath = "/data/data/" + getPackageName() + "/databases/MooveYourBody";
            File f = new File(destPath);
            if (!f.exists()) {
                CopyDB( getBaseContext().getAssets().open("mydb"),
                        new FileOutputStream(destPath));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        db.close();


    }


    public void CopyDB(InputStream inputStream, OutputStream outputStream)
            throws IOException {
        //---copy 1K bytes at a time---
        byte[] buffer = new byte[1024];
        int length;
        while ((length = inputStream.read(buffer)) > 0) {
            outputStream.write(buffer, 0, length);
        }
        inputStream.close();
        outputStream.close();
    }
}
