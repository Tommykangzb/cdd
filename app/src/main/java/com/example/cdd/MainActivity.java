package com.example.cdd;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    private Button btn_exit;
    private Button btn_setting;
    private Button btn_entergame;

    private PlayMusic player;
    private static boolean moniter=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_exit=findViewById(R.id.bt_ExitGame);
        btn_setting=findViewById(R.id.bt_Setting);
        btn_entergame=findViewById(R.id.bt_EnterGame);
        // 启动服务播放背景音乐

        player=new PlayMusic(this);
        player.play();

        btn_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // player.onDestroy();
                System.exit(0);

            }
        });


        btn_entergame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,EnterGameActivity.class);
                startActivity(intent);
            }
        });
        btn_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,SettingActivity.class);
                startActivity(intent);
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        if (PlayMusic.callnumber != 0) {
            switch (PlayMusic.callnumber) {
                case 1:
                    if (!PlayMusic.monitor) {
                        player.pause();
                        Toast toast = Toast.makeText(MainActivity.this, "已经暂停播放背景音乐", Toast.LENGTH_SHORT);
                        PlayMusic.callnumber=0;
                        toast.show();
                    } else {
                        player.Go_on();
                        Toast toast = Toast.makeText(MainActivity.this, "继续播放背景音乐", Toast.LENGTH_SHORT);
                        PlayMusic.callnumber=0;
                        toast.show();
                    }
                    break;
                case 2:
                    String number= PlayMusic.numbercode;
                    PlayMusic.changeMusic(this, number);
                    Toast toast = Toast.makeText(MainActivity.this, "已经切歌", Toast.LENGTH_SHORT);
                    toast.show();
                    PlayMusic.callnumber=0;
                    break;
                default:
                    PlayMusic.callnumber=0;
                    break;
            }
        }
    }
}
