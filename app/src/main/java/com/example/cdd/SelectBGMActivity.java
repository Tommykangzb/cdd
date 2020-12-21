package com.example.cdd;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SelectBGMActivity extends AppCompatActivity {

    private Button btn_cccp;
    private Button btn_hometown;
    private Button btn_moonoverfountain;
    private Button btn_mountain_water;
    private Button btn_summer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selectbgm);
        btn_cccp=findViewById(R.id.music_cccp);
        btn_hometown=findViewById(R.id.music_hometown);
        btn_moonoverfountain=findViewById(R.id.music_moonoverfountain);
        btn_summer=findViewById(R.id.music_summer);
        btn_mountain_water=findViewById(R.id.music_mountain_water);
        btn_moonoverfountain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlayMusic.callnumber=2;
                Intent intent=new Intent(SelectBGMActivity.this, MainActivity.class);
                PlayMusic.numbercode="0001";
                startActivity(intent);
            }
        });
        btn_hometown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlayMusic.callnumber=2;
                Intent intent=new Intent(SelectBGMActivity.this, MainActivity.class);
                PlayMusic.numbercode="0002";
                startActivity(intent);
            }
        });
        btn_summer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlayMusic.callnumber=2;
                Intent intent=new Intent(SelectBGMActivity.this, MainActivity.class);
                PlayMusic.numbercode="0003";
                startActivity(intent);
            }
        });
        btn_mountain_water.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlayMusic.callnumber=2;
                Intent intent=new Intent(SelectBGMActivity.this, MainActivity.class);
                PlayMusic.numbercode="0004";
                startActivity(intent);
            }
        });
        btn_cccp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlayMusic.callnumber=2;
                Intent intent=new Intent(SelectBGMActivity.this, MainActivity.class);
                PlayMusic.numbercode="0005";
                startActivity(intent);
            }
        });
    }
}