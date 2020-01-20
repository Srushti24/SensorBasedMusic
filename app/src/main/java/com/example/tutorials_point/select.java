package com.example.tutorials_point;

import android.content.Intent;
import android.os.Bundle;

import com.example.sairamkrishna.myapplication.R;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;

public class select extends AppCompatActivity {
 Button musictype,norm_music;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);
        musictype = (Button) findViewById(R.id.prox_btn);
        norm_music = (Button) findViewById(R.id.btn2);


        musictype.setOnClickListener(new View.OnClickListener()

        {

            @Override
            public void onClick (View v)
            {
                // TODO Auto-generated method stub
                Intent i = new Intent(getApplicationContext(),type_sensors.class);
                startActivity(i);

            }
        });
    norm_music.setOnClickListener(new View.OnClickListener()

        {

            @Override
            public void onClick (View v)
            {
                // TODO Auto-generated method stub
                Intent i = new Intent(getApplicationContext(),Normal_musicapp.class);
                startActivity(i);

            }
        });

    }


}
