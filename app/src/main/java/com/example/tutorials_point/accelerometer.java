package com.example.tutorials_point;

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
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Timer;

public class accelerometer extends AppCompatActivity implements SensorEventListener {
    private Button b1,b2,b3,b4,b5,b6;
    private ImageButton clickme;
    private ImageView iv;
    private MediaPlayer mediaPlayer;

    private double startTime = 0;
    private double finalTime = 0;

    private Handler myHandler = new Handler();;
    private int forwardTime = 5000;
    private int backwardTime = 5000;
    private SeekBar seekbar;
    private TextView tx1,tx2,tx3;
    SensorManager sensorManager;
    Sensor lightSensor;
    Sensor myProximitySensor;
    Sensor accelerometer;
    public static int oneTimeOnly = 0;
    Timer timer;
    MediaPlayer mp;
    ArrayList <Integer> play_list;
    private static   int i=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accelerometer);
        play_list = new ArrayList<>();
        play_list.add(R.raw.harry_potter_theme);
        play_list.add(R.raw.girls_like);
        play_list.add(R.raw.friends);
        play_list.add(R.raw.jurassic_park);
        play_list.add(R.raw.senorita);
        play_list.add(R.raw.mission);
        b6=findViewById(R.id.playBtn);
        // play_list.add(R.raw.);

        //Sensor creation
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        myProximitySensor = sensorManager.getDefaultSensor(
                Sensor.TYPE_PROXIMITY);
        accelerometer = sensorManager.getDefaultSensor(
                Sensor.TYPE_ACCELEROMETER);

        mediaPlayer = MediaPlayer.create(this, play_list.get(i));



    }

    public void playBtnClick(View view) {


        if (!mediaPlayer.isPlaying()) {
            // Stopping
            mediaPlayer.start();
            b6.setBackgroundResource(R.drawable.stop);

        } else {
            // Playing
            mediaPlayer.pause();
            b6.setBackgroundResource(R.drawable.play);
        }
    }
    @Override
    public void onDestroy () {
        if (mediaPlayer.isPlaying())
            mediaPlayer.stop();

        super.onDestroy();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {


        float value = event.values[0];
        //setContentView(R.layout.music_play);




        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {


            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];
            if(Math.sqrt(Math.pow(x,2.0)+Math.pow(y,2.0)+Math.pow(z,2.0))>30 && x>4) {
                //   playNext();
                if(i<=6) {
                    mediaPlayer.reset();

                    i++;
                    i=i%6;
                    mediaPlayer = MediaPlayer.create(accelerometer.this, play_list.get(i));

                    mediaPlayer.start();
                }

            }
        }




        if (event.sensor.getType() == Sensor.TYPE_PROXIMITY) {

            if (event.values[0]!=0) {


                if (!mediaPlayer.isPlaying()) {
                    mediaPlayer.setLooping(true);
                    mediaPlayer.start();
                }
            } else {

                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                }
            }
        }

        if(event.sensor.getType()==Sensor.TYPE_LIGHT){
            if(value<50)
            {
                Toast.makeText(getApplicationContext(), "LightSensorActivated_STOP", Toast.LENGTH_SHORT).show();
                Toast.makeText(getApplicationContext(), String.valueOf(value), Toast.LENGTH_SHORT).show();


                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();

                }

            }

            else{

                Toast.makeText(getApplicationContext(), "LightSensorActivated_Start", Toast.LENGTH_SHORT).show();
                Toast.makeText(getApplicationContext(), String.valueOf(value), Toast.LENGTH_SHORT).show();
                if (!mediaPlayer.isPlaying()) {
                    mediaPlayer.setLooping(true);
                    mediaPlayer.start();
                    event.values[0]=0;
                }


            }
        }
    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }   @Override
    protected void onResume() {
        super.onResume();
        //sensorManager.registerListener(this,  lightSensor, SensorManager.SENSOR_DELAY_NORMAL);
     //   sensorManager.registerListener(this, myProximitySensor, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }         @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }



}
