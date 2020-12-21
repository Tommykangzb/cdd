package com.example.cdd;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.cdd.gamepackge.PlayWithRobot;

public class EnterGameActivity extends AppCompatActivity {

    private Button btn_back;
    private Button btn_withPlayer;
    private Button btn_withRobot;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entergame);
        btn_withPlayer=findViewById(R.id.bt_WithPlayer);
        btn_withPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(EnterGameActivity.this, BlueToothMatch.class);
                startActivity(intent);
            }
        });
        btn_withRobot=findViewById(R.id.bt_WithRobot);
        btn_withRobot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(EnterGameActivity.this, PlayWithRobot.class);
                startActivity(intent);
            }
        });
        btn_back=findViewById(R.id.bt_Enter_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(EnterGameActivity.this, MainActivity.class);
                startActivity(intent);
                EnterGameActivity.this.finish();
            }
        });
    }
}