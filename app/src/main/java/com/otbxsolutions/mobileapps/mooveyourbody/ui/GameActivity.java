package com.otbxsolutions.mobileapps.mooveyourbody.ui;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.otbxsolutions.mobileapps.mooveyourbody.R;
import com.otbxsolutions.mobileapps.mooveyourbody.model.Arrow;
import com.otbxsolutions.mobileapps.mooveyourbody.model.DBAdapter;
import com.otbxsolutions.mobileapps.mooveyourbody.model.Game;

import java.util.concurrent.TimeUnit;

public class GameActivity extends Activity {
    private final int SHAKE_THRESHOLD = 10;
    private SensorManager sensorManager;
    private boolean color = false;
    private View view;
    private TextView timerTextView, scoreTextView, accelTextView;
    private ImageView arrowImageView;
    private float lastUpdate;
    private static Game game;
    private static Arrow arrow;
    private Drawable drawable;
    private String force, currentScore;
    private onCount timer;
    private boolean isRunning;
    private float last_x, last_y, last_z;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient mClient;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        isRunning=false;
        view = findViewById(R.id.vwMain);
        view.setBackgroundColor(Color.GREEN);
        scoreTextView = (TextView) findViewById(R.id.txtScore);
        timerTextView = (TextView) findViewById(R.id.txtTimer);
        accelTextView = (TextView) findViewById(R.id.txtAccel);

        arrowImageView = (ImageView)findViewById(R.id.imgArrow);
        arrowImageView.setRotation(0);

        game = new Game();
        arrow = new Arrow();
        timer = new onCount(180000, 1000);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensorManager.registerListener(mSensorListener,
                sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);
        lastUpdate = System.currentTimeMillis();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        mClient = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    public class onCount extends CountDownTimer {
        public onCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            long millis = millisUntilFinished;
            String hms = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis),
                    TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
                    TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
            timerTextView.setText(hms);
        }

        @Override
        public void onFinish() {
            String message ="";
            game.endGame();
            isRunning=false;
            drawable=getResources().getDrawable(getResources()
                    .getIdentifier("timesup", "drawable", getPackageName()));

            arrowImageView.setRotation(0);
            arrowImageView.setImageDrawable(drawable);

            DBAdapter db = new DBAdapter(GameActivity.this);
            db.open();

            if(db.highScore(game.getScore())) message = "You Got "+currentScore+" Points! \nHigh Score!";
            else message = "You Got "+currentScore+" Points!";

            db.insertRecord("Player",currentScore);
            db.close();

            accelTextView.setText(message);
            accelTextView.setTextSize(30);
        }
    }

    private final SensorEventListener mSensorListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            arrowImageView.setVisibility(View.VISIBLE);
            if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER && game.getState()) {
                getAccelerometer(event);
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };

    private void getAccelerometer(SensorEvent event) {
        float[] values = event.values;
        // Movement
        float x = values[0];
        float y = values[1];
        float z = values[2];
        float acceleration = 0;
        int pointIncrease;
        float actualTime = event.timestamp;
        float diffTime = actualTime - lastUpdate;

        if (diffTime > 100) {
            // float speed = Math.abs(x + y + z - last_x - last_y - last_z)/ diffTime * 10000;
            float speed = (float)Math.sqrt(Math.pow(x-last_x, 2) + Math.pow(y-last_y, 2) + Math.pow(z-last_z, 2));

            if (speed > SHAKE_THRESHOLD) {
                lastUpdate = actualTime;
                timerTextView.setText(""+speed);

                if (Math.abs(x-last_x) > Math.abs(y-last_y)) {
                    force = "x";
                    acceleration = x;
                }
                if (Math.abs(y-last_y) > Math.abs(x-last_x)) {
                    force = "y";
                    acceleration = y;
                }
                if (!isRunning) {
                    timer.start();
                    isRunning = true;
                }

                pointIncrease = arrow.getPoints();
                if (arrow.getDirection() == "UP" && force == "y" && y > 0.00) {
                    game.incrementScore(pointIncrease);
                } else if (arrow.getDirection() == "DOWN" && force == "y" && y < 0.00) {
                    game.incrementScore(pointIncrease);
                } else if (arrow.getDirection() == "RIGHT" && force == "x" && x > 0.00) {
                    game.incrementScore(pointIncrease);
                } else if (arrow.getDirection() == "LEFT" && force == "x" && x < 0.00) {
                    game.incrementScore(pointIncrease);
                } else {
                    game.decrementScore();
                }
                currentScore = game.getScore() + "";
                scoreTextView.setText("Points: " + currentScore);

                accelTextView.setText("You acceleration was " + acceleration + " " + force + " m/s2! " + arrow.getDirection() + " You got " + pointIncrease);

                arrow.generateArrow();

                drawable = getResources().getDrawable(getResources()
                        .getIdentifier(arrow.getImageFileName(), "drawable", getPackageName()));
                arrowImageView.setImageDrawable(drawable);
                arrowImageView.setRotation(arrow.getRotation());

                last_x = x;
                last_y = y;
                last_z = z;
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(mSensorListener,
                sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_FASTEST);
    }

    @Override
    protected void onPause() {
        sensorManager.unregisterListener(mSensorListener);
        super.onPause();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() { super.onStop(); }
}