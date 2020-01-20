package com.example.tutorials_point;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;

import com.example.sairamkrishna.myapplication.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class proximity_light extends AppCompatActivity implements SensorEventListener {
    int i = 0;
    Button playBtn,playback;
    SeekBar positionBar;
    SeekBar volumeBar;
    TextView elapsedTimeLabel;
    TextView remainingTimeLabel;
    MediaPlayer mp;
    int totalTime;
    boolean c = false;

    private ListView listview_songs;
    //My variable

    SensorManager sensorManager;
    Sensor lightSensor;
    Sensor myProximitySensor;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proximity_light);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        playBtn = (Button) findViewById(R.id.playBtn);
       playback = (Button) findViewById(R.id.back);
        elapsedTimeLabel = (TextView) findViewById(R.id.elapsedTimeLabel);
        remainingTimeLabel = (TextView) findViewById(R.id.remainingTimeLabel);
        myProximitySensor = sensorManager.getDefaultSensor(
                Sensor.TYPE_PROXIMITY);

        // Media Player
        mp = MediaPlayer.create(this, R.raw.senorita);
        mp.setLooping(true);
        mp.seekTo(0);
        mp.setVolume(0.5f, 0.5f);
        totalTime = mp.getDuration();

        // Position Bar
        positionBar = (SeekBar) findViewById(R.id.positionBar);
        positionBar.setMax(totalTime);
       /* playback.setOnClickListener(new View.OnClickListener()

        {

            @Override
            public void onClick (View v)
            {
                Toast.makeText(getApplicationContext(), "Inside button", Toast.LENGTH_SHORT).show();
                // TODO Auto-generated method stub
                mp.pause();

                Intent i = new Intent(getApplicationContext(),select.class);
                startActivity(i);



            }
        });*/

        positionBar.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        if (fromUser) {
                            mp.seekTo(progress);
                            positionBar.setProgress(progress);
                        }
                    }




                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }
                }
        );


        // Volume Bar
        volumeBar = (SeekBar) findViewById(R.id.volumeBar);
        volumeBar.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        float volumeNum = progress / 100f;
                        mp.setVolume(volumeNum, volumeNum);
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }
                }
        );

        // Thread (Update positionBar & timeLabel)
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (mp != null) {
                    try {
                        Message msg = new Message();
                        msg.what = mp.getCurrentPosition();
                        handler.sendMessage(msg);
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                    }
                }
            }
        }).start();


        /*back.setOnClickListener(new View.OnClickListener()

        {

            @Override
            public void onClick (View v)
            {
                // TODO Auto-generated method stub
                mp.stop();

                Intent i = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(i);



            }
        });*/
    }


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            int currentPosition = msg.what;
            // Update positionBar.
            positionBar.setProgress(currentPosition);

            // Update Labels.
            String elapsedTime = createTimeLabel(currentPosition);
            elapsedTimeLabel.setText(elapsedTime);

            String remainingTime = createTimeLabel(totalTime - currentPosition);
            remainingTimeLabel.setText("- " + remainingTime);
        }
    };

    public String createTimeLabel(int time) {
        String timeLabel = "";
        int min = time / 1000 / 60;
        int sec = time / 1000 % 60;

        timeLabel = min + ":";
        if (sec < 10) timeLabel += "0";
        timeLabel += sec;

        return timeLabel;
    }


    public void playBtnClick(View view) {

        if (!mp.isPlaying()) {
            // Stopping
            mp.start();
            playBtn.setBackgroundResource(R.drawable.stop);

        } else {
            // Playing
            mp.pause();
            playBtn.setBackgroundResource(R.drawable.play);
        }
    }

    public void onClickback (View v)
    {
     //   Toast.makeText(getApplicationContext(), "Inside button", Toast.LENGTH_SHORT).show();
        // TODO Auto-generated method stub
        mp.stop();

        Intent i = new Intent(getApplicationContext(),select.class);
        startActivity(i);


    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        i++;

        float value = event.values[0];
        setContentView(R.layout.activity_proximity_light);

        if (event.sensor.getType() == Sensor.TYPE_PROXIMITY) {

            if (event.values[0] == 0) {


                if (!mp.isPlaying()) {
                    mp.setLooping(true);
                    mp.start();
                }
            } else {

                if (mp.isPlaying()) {
                    mp.pause();

                }
            }
        }    if(event.sensor.getType()==Sensor.TYPE_LIGHT){
            if(value<20)
            {
                Toast.makeText(getApplicationContext(), "LightSensorActivated_STOP", Toast.LENGTH_SHORT).show();
                Toast.makeText(getApplicationContext(), String.valueOf(value), Toast.LENGTH_SHORT).show();


                if (mp.isPlaying()) {
                    mp.pause();

                }

            }

            else{

                Toast.makeText(getApplicationContext(), "LightSensorActivated_Start", Toast.LENGTH_SHORT).show();
                Toast.makeText(getApplicationContext(), String.valueOf(value), Toast.LENGTH_SHORT).show();
                if (!mp.isPlaying()) {
                    mp.setLooping(true);
                    mp.start();
                    event.values[0]=0;
                }
                playBtn.setBackgroundResource(R.drawable.play);

            }
        }
    }



    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }   @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this,  lightSensor, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, myProximitySensor, SensorManager.SENSOR_DELAY_NORMAL);
    }         @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

}