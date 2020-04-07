package com.example.phoneauth;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class registration extends AppCompatActivity {
    Button bt , id;
    ImageView img;
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        bt = findViewById(R.id.custome);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customepage();
            }
        });

        id = findViewById(R.id.upload);
        img = findViewById(R.id.idcard);
    }

    private void customepage() {
        Intent i = new Intent(this,custome.class);
        startActivity(i);
    }
}
