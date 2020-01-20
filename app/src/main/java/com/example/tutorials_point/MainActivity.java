package com.example.tutorials_point;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sairamkrishna.myapplication.R;

import java.util.ArrayList;
import java.util.Timer;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    private Button b1,b2,b3,b4,b5;
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
    int i=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
 //All the songs are present in the raw folder.
        play_list = new ArrayList<>();
        play_list.add(R.raw.harry_potter_theme);
        play_list.add(R.raw.girls_like);
        play_list.add(R.raw.friends);
        play_list.add(R.raw.jurassic_park);
        play_list.add(R.raw.senorita);
        play_list.add(R.raw.mission);

        // play_list.add(R.raw.);

        //Sensor creation
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        myProximitySensor = sensorManager.getDefaultSensor(
                Sensor.TYPE_PROXIMITY);
        accelerometer = sensorManager.getDefaultSensor(
                Sensor.TYPE_ACCELEROMETER);


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        clickme=(ImageButton) findViewById(R.id.clickme);

        mediaPlayer = MediaPlayer.create(this, play_list.get(i));


        clickme.setOnClickListener(new View.OnClickListener()

        {

            @Override
            public void onClick (View v)
            {
                // TODO Auto-generated method stub
                Intent i = new Intent(getApplicationContext(),select.class);
                startActivity(i);

            }
        });

    }
    @Override
    public void onDestroy () {
        if (mediaPlayer.isPlaying())
            mediaPlayer.stop();

        super.onDestroy();
    }
    private Runnable UpdateSongTime = new Runnable() {
        public void run() {
            startTime = mediaPlayer.getCurrentPosition();
            tx1.setText(String.format("%d min, %d sec",
                    TimeUnit.MILLISECONDS.toMinutes((long) startTime),
                    TimeUnit.MILLISECONDS.toSeconds((long) startTime) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.
                                    toMinutes((long) startTime)))
            );
            seekbar.setProgress((int)startTime);
            myHandler.postDelayed(this, 100);
        }
    };


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
                    if(i<=5) {
                        mediaPlayer.reset();
                        i=i%5;
                        mediaPlayer = MediaPlayer.create(MainActivity.this, play_list.get(i));
                        i++;
                        mediaPlayer.start();
                    }

                }
        }




        if (event.sensor.getType() == Sensor.TYPE_PROXIMITY) {

            if (event.values[0] == 0) {


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
 //       sensorManager.registerListener(this,  lightSensor, SensorManager.SENSOR_DELAY_NORMAL);
   //     sensorManager.registerListener(this, myProximitySensor, SensorManager.SENSOR_DELAY_NORMAL);
     //   sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }         @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

}



