package com.otbxsolutions.mobileapps.mooveyourbody.ui;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.otbxsolutions.mobileapps.mooveyourbody.R;
import com.otbxsolutions.mobileapps.mooveyourbody.model.DBAdapter;

public class ScoreActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        TextView txtUser, txtScore;
        TableLayout tblLayout = (TableLayout)findViewById(R.id.tlScore);
        TableRow tblRow;
        DBAdapter db = new DBAdapter(this);
        db.open();
        Cursor c = db.getAllRecords();

        if(c.moveToNext()){
            do{
                tblRow = new TableRow(this);
                txtUser = new TextView(this);
                txtScore = new TextView(this);
                txtUser.setText(c.getString(1));
                txtScore.setText(c.getString(2));
                tblRow.addView(txtUser);
                tblRow.addView(txtScore);
                assert tblLayout != null;
                tblLayout.addView(tblRow);
            } while(c.moveToNext());
        }
        db.close();
    }

}
