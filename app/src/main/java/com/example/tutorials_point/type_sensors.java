package com.example.tutorials_point;

import android.content.Intent;
import android.os.Bundle;

import com.example.sairamkrishna.myapplication.R;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;

public class type_sensors extends AppCompatActivity {
private Button b1,b2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type_sensors);
        b1=findViewById(R.id.btn1);
   b2=findViewById(R.id.btn2);


           b1.setOnClickListener(new View.OnClickListener()

        {

            @Override
            public void onClick (View v)
            {
                // TODO Auto-generated method stub
                Intent i = new Intent(getApplicationContext(),proximity_light.class);
                startActivity(i);

            }
        });


        b2.setOnClickListener(new View.OnClickListener()

        {

            @Override
            public void onClick (View v)
            {
                // TODO Auto-generated method stub
                Intent i = new Intent(getApplicationContext(),accelerometer.class);
                startActivity(i);

            }
        });

    }

}
