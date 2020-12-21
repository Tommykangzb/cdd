package com.example.cdd;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SettingActivity extends AppCompatActivity {

    private Button btn_back;
    private Button btn_domusic;
    private Button btn_selectBGM;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        btn_back=findViewById(R.id.bt_setting_back);
        btn_selectBGM=findViewById(R.id.bt_selectBGM);

        btn_domusic=findViewById(R.id.bt_Domusic);
        btn_domusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlayMusic.callnumber=1;
                Intent intent=new Intent(SettingActivity.this, MainActivity.class);
                PlayMusic.changestate();
                startActivity(intent);
            }
        });
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SettingActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        btn_selectBGM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SettingActivity.this,SelectBGMActivity.class);
                startActivity(intent);
            }
        });
    }
}