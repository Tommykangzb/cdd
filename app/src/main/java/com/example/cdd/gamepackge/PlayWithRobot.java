package com.example.cdd.gamepackge;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;


import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Button;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnMultiChoiceClickListener;


import com.example.cdd.EnterGameActivity;
import com.example.cdd.MainActivity;
import com.example.cdd.R;




//方块 1-13，梅花 14-26，红桃27-39，黑桃 40-52
public class PlayWithRobot extends AppCompatActivity {
    private ImageButton imageButton_pass;
    private ImageButton imageButton_showcard;
    private ImageButton imageButton_exit;
    private TextView CardNumber1;
    private TextView CardNumber2;
    private TextView CardNumber3;
    private ImageView image1;
    private ImageView image2;
    private ImageView image3;
    private ImageView image4;
    private ImageView image5;
    private ImageView image6;
    private ImageView image7;
    private ImageView image8;
    private ImageView image9;
    private ImageView image10;
    private ImageView image11;
    private ImageView image12;
    private ImageView image13;
    private ImageView imageView_show1;
    private ImageView imageView_show2;
    private ImageView imageView_show3;
    private ImageView imageView_show4;
    private ImageView imageView_show5;
    private ImageView imageView_robot1_show1;
    private ImageView imageView_robot1_show2;
    private ImageView imageView_robot1_show3;
    private ImageView imageView_robot1_show4;
    private ImageView imageView_robot1_show5;
    private ImageView imageView_robot2_show1;
    private ImageView imageView_robot2_show2;
    private ImageView imageView_robot2_show3;
    private ImageView imageView_robot2_show4;
    private ImageView imageView_robot2_show5;
    private ImageView imageView_robot3_show1;
    private ImageView imageView_robot3_show2;
    private ImageView imageView_robot3_show3;
    private ImageView imageView_robot3_show4;
    private ImageView imageView_robot3_show5;
    private ImageView imageView_robot1_pass;
    private ImageView imageView_robot2_pass;
    private ImageView imageView_robot3_pass;
    private ImageButton Imagebtn_exit;
    private Button button_ready;
    private Ruler ruler;
    private Handler mhandle;
    private Handler mhandle_pass;
    //count为 展示出了的牌的控件的使用情况
    private boolean flag=true;
    private int[] cardID;
    private int[][] RobotCardID;
    private  boolean location = true, location_two = true, location_three = true, location_four = true, location_five = true,
            location_six = true, location_seven = true, location_eight = true, location_nine = true, location_ten = true, location_eleven = true,
            location_twelve = true, location_thirteen = true;
    public volatile boolean exit = false;

    AlertDialog a,b,c,d;
    ThreadFlag thread = new ThreadFlag();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        RobotCardID=new int[3][13];
        ruler = new Ruler(1);
        cardID = new int[13];

        for (int i = 0; i < 13; i++) {
            cardID[i] = Ruler.getHumanPlayerArray()[0].getCardsAtHand().get(i).getCardId();
        }

        for (int j = 0; j< 3; j++) {
            for (int i = 0; i < 13; i++) {
                RobotCardID[j][i]=Ruler.getPlayerArray()[j+1].getCardsAtHand().get(i).getCardId();
            }
        }

        Imagebtn_exit=findViewById(R.id.imageButton_exit);
        Imagebtn_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PlayWithRobot.this, MainActivity.class);
                startActivity(intent);
            }
        });
        CardNumber3=findViewById(R.id.textView_robot3_CardNumber);
        CardNumber2=findViewById(R.id.textView_robot2_CardNumber);
        CardNumber1=findViewById(R.id.textView_robot1_CardNumber);
        imageButton_pass = findViewById(R.id.imageButton_pass);
        imageButton_showcard = findViewById(R.id.imageButton_show);
        imageView_show1=findViewById(R.id.imageView_show1);
        imageView_show2=findViewById(R.id.imageView_show2);
        imageView_show3=findViewById(R.id.imageView_show3);
        imageView_show4=findViewById(R.id.imageView_show4);
        imageView_show5=findViewById(R.id.imageView_show5);
        imageView_robot1_show1=findViewById(R.id.imageView_robot1_show1);
        imageView_robot1_show2=findViewById(R.id.imageView_robot1_show2);
        imageView_robot1_show3=findViewById(R.id.imageView_robot1_show3);
        imageView_robot1_show4=findViewById(R.id.imageView_robot1_show4);
        imageView_robot1_show5=findViewById(R.id.imageView_robot1_show5);
        imageView_robot2_show1=findViewById(R.id.imageView_robot2_show1);
        imageView_robot2_show2=findViewById(R.id.imageView_robot2_show2);
        imageView_robot2_show3=findViewById(R.id.imageView_robot2_show3);
        imageView_robot2_show4=findViewById(R.id.imageView_robot2_show4);
        imageView_robot2_show5=findViewById(R.id.imageView_robot2_show5);
        imageView_robot3_show1=findViewById(R.id.imageView_robot3_show1);
        imageView_robot3_show2=findViewById(R.id.imageView_robot3_show2);
        imageView_robot3_show3=findViewById(R.id.imageView_robot3_show3);
        imageView_robot3_show4=findViewById(R.id.imageView_robot3_show4);
        imageView_robot3_show5=findViewById(R.id.imageView_robot3_show5);
        imageView_robot1_pass=findViewById(R.id.imageView_robot1_pass);
        imageView_robot2_pass=findViewById(R.id.imageView_robot2_pass);
        imageView_robot3_pass=findViewById(R.id.imageView_robot3_pass);
        button_ready=findViewById(R.id.Button_ready);

//        imageButton_pass.setVisibility(View.INVISIBLE);
//        imageButton_showcard.setVisibility(View.INVISIBLE);

        image1 = findViewById(R.id.imageView1);
        switch (cardID[0]) {
            case 0:
                image1.setImageResource(R.drawable.c40);
                break;
            case 1:
                image1.setImageResource(R.drawable.c41);
                break;
            case 2:
                image1.setImageResource(R.drawable.c42);
                break;
            case 3:
                image1.setImageResource(R.drawable.c43);
                break;
            case 4:
                image1.setImageResource(R.drawable.c44);
                break;
            case 5:
                image1.setImageResource(R.drawable.c45);
                break;
            case 6:
                image1.setImageResource(R.drawable.c46);
                break;
            case 7:
                image1.setImageResource(R.drawable.c47);
                break;
            case 8:
                image1.setImageResource(R.drawable.c48);
                break;
            case 9:
                image1.setImageResource(R.drawable.c49);
                break;
            case 10:
                image1.setImageResource(R.drawable.c50);
                break;
            case 11:
                image1.setImageResource(R.drawable.c51);
                break;
            case 12:
                image1.setImageResource(R.drawable.c52);
                break;
            case 13:
                image1.setImageResource(R.drawable.c27);
                break;
            case 14:
                image1.setImageResource(R.drawable.c28);
                break;
            case 15:
                image1.setImageResource(R.drawable.c29);
                break;
            case 16:
                image1.setImageResource(R.drawable.c30);
                break;
            case 17:
                image1.setImageResource(R.drawable.c31);
                break;
            case 18:
                image1.setImageResource(R.drawable.c32);
                break;
            case 19:
                image1.setImageResource(R.drawable.c33);
                break;
            case 20:
                image1.setImageResource(R.drawable.c34);
                break;
            case 21:
                image1.setImageResource(R.drawable.c35);
                break;
            case 22:
                image1.setImageResource(R.drawable.c36);
                break;
            case 23:
                image1.setImageResource(R.drawable.c37);
                break;
            case 24:
                image1.setImageResource(R.drawable.c38);
                break;
            case 25:
                image1.setImageResource(R.drawable.c39);
                break;
            case 26:
                image1.setImageResource(R.drawable.c14);
                break;
            case 27:
                image1.setImageResource(R.drawable.c15);
                break;
            case 28:
                image1.setImageResource(R.drawable.c16);
                break;
            case 29:
                image1.setImageResource(R.drawable.c17);
                break;
            case 30:
                image1.setImageResource(R.drawable.c18);
                break;
            case 31:
                image1.setImageResource(R.drawable.c19);
                break;
            case 32:
                image1.setImageResource(R.drawable.c20);
                break;
            case 33:
                image1.setImageResource(R.drawable.c21);
                break;
            case 34:
                image1.setImageResource(R.drawable.c22);
                break;
            case 35:
                image1.setImageResource(R.drawable.c23);
                break;
            case 36:
                image1.setImageResource(R.drawable.c24);
                break;
            case 37:
                image1.setImageResource(R.drawable.c25);
                break;
            case 38:
                image1.setImageResource(R.drawable.c26);
                break;
            case 39:
                image1.setImageResource(R.drawable.c1);
                break;
            case 40:
                image1.setImageResource(R.drawable.c2);
                break;
            case 41:
                image1.setImageResource(R.drawable.c3);
                break;
            case 42:
                image1.setImageResource(R.drawable.c4);
                break;
            case 43:
                image1.setImageResource(R.drawable.c5);
                break;
            case 44:
                image1.setImageResource(R.drawable.c6);
                break;
            case 45:
                image1.setImageResource(R.drawable.c7);
                break;
            case 46:
                image1.setImageResource(R.drawable.c8);
                break;
            case 47:
                image1.setImageResource(R.drawable.c9);
                break;
            case 48:
                image1.setImageResource(R.drawable.c10);
                break;
            case 49:
                image1.setImageResource(R.drawable.c11);
                break;
            case 50:
                image1.setImageResource(R.drawable.c12);
                break;
            case 51:
                image1.setImageResource(R.drawable.c13);
                break;
            default:
                break;
        }
        image2 = findViewById(R.id.imageView2);
        switch (cardID[1]) {
            case 0:
                image2.setImageResource(R.drawable.c40);
                break;
            case 1:
                image2.setImageResource(R.drawable.c41);
                break;
            case 2:
                image2.setImageResource(R.drawable.c42);
                break;
            case 3:
                image2.setImageResource(R.drawable.c43);
                break;
            case 4:
                image2.setImageResource(R.drawable.c44);
                break;
            case 5:
                image2.setImageResource(R.drawable.c45);
                break;
            case 6:
                image2.setImageResource(R.drawable.c46);
                break;
            case 7:
                image2.setImageResource(R.drawable.c47);
                break;
            case 8:
                image2.setImageResource(R.drawable.c48);
                break;
            case 9:
                image2.setImageResource(R.drawable.c49);
                break;
            case 10:
                image2.setImageResource(R.drawable.c50);
                break;
            case 11:
                image2.setImageResource(R.drawable.c51);
                break;
            case 12:
                image2.setImageResource(R.drawable.c52);
                break;
            case 13:
                image2.setImageResource(R.drawable.c27);
                break;
            case 14:
                image2.setImageResource(R.drawable.c28);
                break;
            case 15:
                image2.setImageResource(R.drawable.c29);
                break;
            case 16:
                image2.setImageResource(R.drawable.c30);
                break;
            case 17:
                image2.setImageResource(R.drawable.c31);
                break;
            case 18:
                image2.setImageResource(R.drawable.c32);
                break;
            case 19:
                image2.setImageResource(R.drawable.c33);
                break;
            case 20:
                image2.setImageResource(R.drawable.c34);
                break;
            case 21:
                image2.setImageResource(R.drawable.c35);
                break;
            case 22:
                image2.setImageResource(R.drawable.c36);
                break;
            case 23:
                image2.setImageResource(R.drawable.c37);
                break;
            case 24:
                image2.setImageResource(R.drawable.c38);
                break;
            case 25:
                image2.setImageResource(R.drawable.c39);
                break;
            case 26:
                image2.setImageResource(R.drawable.c14);
                break;
            case 27:
                image2.setImageResource(R.drawable.c15);
                break;
            case 28:
                image2.setImageResource(R.drawable.c16);
                break;
            case 29:
                image2.setImageResource(R.drawable.c17);
                break;
            case 30:
                image2.setImageResource(R.drawable.c18);
                break;
            case 31:
                image2.setImageResource(R.drawable.c19);
                break;
            case 32:
                image2.setImageResource(R.drawable.c20);
                break;
            case 33:
                image2.setImageResource(R.drawable.c21);
                break;
            case 34:
                image2.setImageResource(R.drawable.c22);
                break;
            case 35:
                image2.setImageResource(R.drawable.c23);
                break;
            case 36:
                image2.setImageResource(R.drawable.c24);
                break;
            case 37:
                image2.setImageResource(R.drawable.c25);
                break;
            case 38:
                image2.setImageResource(R.drawable.c26);
                break;
            case 39:
                image2.setImageResource(R.drawable.c1);
                break;
            case 40:
                image2.setImageResource(R.drawable.c2);
                break;
            case 41:
                image2.setImageResource(R.drawable.c3);
                break;
            case 42:
                image2.setImageResource(R.drawable.c4);
                break;
            case 43:
                image2.setImageResource(R.drawable.c5);
                break;
            case 44:
                image2.setImageResource(R.drawable.c6);
                break;
            case 45:
                image2.setImageResource(R.drawable.c7);
                break;
            case 46:
                image2.setImageResource(R.drawable.c8);
                break;
            case 47:
                image2.setImageResource(R.drawable.c9);
                break;
            case 48:
                image2.setImageResource(R.drawable.c10);
                break;
            case 49:
                image2.setImageResource(R.drawable.c11);
                break;
            case 50:
                image2.setImageResource(R.drawable.c12);
                break;
            case 51:
                image2.setImageResource(R.drawable.c13);
                break;
            default:
                break;
        }
        image3 = findViewById(R.id.imageView3);
        switch (cardID[2]) {
            case 0:
                image3.setImageResource(R.drawable.c40);
                break;
            case 1:
                image3.setImageResource(R.drawable.c41);
                break;
            case 2:
                image3.setImageResource(R.drawable.c42);
                break;
            case 3:
                image3.setImageResource(R.drawable.c43);
                break;
            case 4:
                image3.setImageResource(R.drawable.c44);
                break;
            case 5:
                image3.setImageResource(R.drawable.c45);
                break;
            case 6:
                image3.setImageResource(R.drawable.c46);
                break;
            case 7:
                image3.setImageResource(R.drawable.c47);
                break;
            case 8:
                image3.setImageResource(R.drawable.c48);
                break;
            case 9:
                image3.setImageResource(R.drawable.c49);
                break;
            case 10:
                image3.setImageResource(R.drawable.c50);
                break;
            case 11:
                image3.setImageResource(R.drawable.c51);
                break;
            case 12:
                image3.setImageResource(R.drawable.c52);
                break;
            case 13:
                image3.setImageResource(R.drawable.c27);
                break;
            case 14:
                image3.setImageResource(R.drawable.c28);
                break;
            case 15:
                image3.setImageResource(R.drawable.c29);
                break;
            case 16:
                image3.setImageResource(R.drawable.c30);
                break;
            case 17:
                image3.setImageResource(R.drawable.c31);
                break;
            case 18:
                image3.setImageResource(R.drawable.c32);
                break;
            case 19:
                image3.setImageResource(R.drawable.c33);
                break;
            case 20:
                image3.setImageResource(R.drawable.c34);
                break;
            case 21:
                image3.setImageResource(R.drawable.c35);
                break;
            case 22:
                image3.setImageResource(R.drawable.c36);
                break;
            case 23:
                image3.setImageResource(R.drawable.c37);
                break;
            case 24:
                image3.setImageResource(R.drawable.c38);
                break;
            case 25:
                image3.setImageResource(R.drawable.c39);
                break;
            case 26:
                image3.setImageResource(R.drawable.c14);
                break;
            case 27:
                image3.setImageResource(R.drawable.c15);
                break;
            case 28:
                image3.setImageResource(R.drawable.c16);
                break;
            case 29:
                image3.setImageResource(R.drawable.c17);
                break;
            case 30:
                image3.setImageResource(R.drawable.c18);
                break;
            case 31:
                image3.setImageResource(R.drawable.c19);
                break;
            case 32:
                image3.setImageResource(R.drawable.c20);
                break;
            case 33:
                image3.setImageResource(R.drawable.c21);
                break;
            case 34:
                image3.setImageResource(R.drawable.c22);
                break;
            case 35:
                image3.setImageResource(R.drawable.c23);
                break;
            case 36:
                image3.setImageResource(R.drawable.c24);
                break;
            case 37:
                image3.setImageResource(R.drawable.c25);
                break;
            case 38:
                image3.setImageResource(R.drawable.c26);
                break;
            case 39:
                image3.setImageResource(R.drawable.c1);
                break;
            case 40:
                image3.setImageResource(R.drawable.c2);
                break;
            case 41:
                image3.setImageResource(R.drawable.c3);
                break;
            case 42:
                image3.setImageResource(R.drawable.c4);
                break;
            case 43:
                image3.setImageResource(R.drawable.c5);
                break;
            case 44:
                image3.setImageResource(R.drawable.c6);
                break;
            case 45:
                image3.setImageResource(R.drawable.c7);
                break;
            case 46:
                image3.setImageResource(R.drawable.c8);
                break;
            case 47:
                image3.setImageResource(R.drawable.c9);
                break;
            case 48:
                image3.setImageResource(R.drawable.c10);
                break;
            case 49:
                image3.setImageResource(R.drawable.c11);
                break;
            case 50:
                image3.setImageResource(R.drawable.c12);
                break;
            case 51:
                image3.setImageResource(R.drawable.c13);
                break;
            default:
                break;
        }
        image4 = findViewById(R.id.imageView4);
        switch (cardID[3]) {
            case 0:
                image4.setImageResource(R.drawable.c40);
                break;
            case 1:
                image4.setImageResource(R.drawable.c41);
                break;
            case 2:
                image4.setImageResource(R.drawable.c42);
                break;
            case 3:
                image4.setImageResource(R.drawable.c43);
                break;
            case 4:
                image4.setImageResource(R.drawable.c44);
                break;
            case 5:
                image4.setImageResource(R.drawable.c45);
                break;
            case 6:
                image4.setImageResource(R.drawable.c46);
                break;
            case 7:
                image4.setImageResource(R.drawable.c47);
                break;
            case 8:
                image4.setImageResource(R.drawable.c48);
                break;
            case 9:
                image4.setImageResource(R.drawable.c49);
                break;
            case 10:
                image4.setImageResource(R.drawable.c50);
                break;
            case 11:
                image4.setImageResource(R.drawable.c51);
                break;
            case 12:
                image4.setImageResource(R.drawable.c52);
                break;
            case 13:
                image4.setImageResource(R.drawable.c27);
                break;
            case 14:
                image4.setImageResource(R.drawable.c28);
                break;
            case 15:
                image4.setImageResource(R.drawable.c29);
                break;
            case 16:
                image4.setImageResource(R.drawable.c30);
                break;
            case 17:
                image4.setImageResource(R.drawable.c31);
                break;
            case 18:
                image4.setImageResource(R.drawable.c32);
                break;
            case 19:
                image4.setImageResource(R.drawable.c33);
                break;
            case 20:
                image4.setImageResource(R.drawable.c34);
                break;
            case 21:
                image4.setImageResource(R.drawable.c35);
                break;
            case 22:
                image4.setImageResource(R.drawable.c36);
                break;
            case 23:
                image4.setImageResource(R.drawable.c37);
                break;
            case 24:
                image4.setImageResource(R.drawable.c38);
                break;
            case 25:
                image4.setImageResource(R.drawable.c39);
                break;
            case 26:
                image4.setImageResource(R.drawable.c14);
                break;
            case 27:
                image4.setImageResource(R.drawable.c15);
                break;
            case 28:
                image4.setImageResource(R.drawable.c16);
                break;
            case 29:
                image4.setImageResource(R.drawable.c17);
                break;
            case 30:
                image4.setImageResource(R.drawable.c18);
                break;
            case 31:
                image4.setImageResource(R.drawable.c19);
                break;
            case 32:
                image4.setImageResource(R.drawable.c20);
                break;
            case 33:
                image4.setImageResource(R.drawable.c21);
                break;
            case 34:
                image4.setImageResource(R.drawable.c22);
                break;
            case 35:
                image4.setImageResource(R.drawable.c23);
                break;
            case 36:
                image4.setImageResource(R.drawable.c24);
                break;
            case 37:
                image4.setImageResource(R.drawable.c25);
                break;
            case 38:
                image4.setImageResource(R.drawable.c26);
                break;
            case 39:
                image4.setImageResource(R.drawable.c1);
                break;
            case 40:
                image4.setImageResource(R.drawable.c2);
                break;
            case 41:
                image4.setImageResource(R.drawable.c3);
                break;
            case 42:
                image4.setImageResource(R.drawable.c4);
                break;
            case 43:
                image4.setImageResource(R.drawable.c5);
                break;
            case 44:
                image4.setImageResource(R.drawable.c6);
                break;
            case 45:
                image4.setImageResource(R.drawable.c7);
                break;
            case 46:
                image4.setImageResource(R.drawable.c8);
                break;
            case 47:
                image4.setImageResource(R.drawable.c9);
                break;
            case 48:
                image4.setImageResource(R.drawable.c10);
                break;
            case 49:
                image4.setImageResource(R.drawable.c11);
                break;
            case 50:
                image4.setImageResource(R.drawable.c12);
                break;
            case 51:
                image4.setImageResource(R.drawable.c13);
                break;
            default:
                break;
        }
        image5 = findViewById(R.id.imageView5);
        switch (cardID[4]) {
            case 0:
                image5.setImageResource(R.drawable.c40);
                break;
            case 1:
                image5.setImageResource(R.drawable.c41);
                break;
            case 2:
                image5.setImageResource(R.drawable.c42);
                break;
            case 3:
                image5.setImageResource(R.drawable.c43);
                break;
            case 4:
                image5.setImageResource(R.drawable.c44);
                break;
            case 5:
                image5.setImageResource(R.drawable.c45);
                break;
            case 6:
                image5.setImageResource(R.drawable.c46);
                break;
            case 7:
                image5.setImageResource(R.drawable.c47);
                break;
            case 8:
                image5.setImageResource(R.drawable.c48);
                break;
            case 9:
                image5.setImageResource(R.drawable.c49);
                break;
            case 10:
                image5.setImageResource(R.drawable.c50);
                break;
            case 11:
                image5.setImageResource(R.drawable.c51);
                break;
            case 12:
                image5.setImageResource(R.drawable.c52);
                break;
            case 13:
                image5.setImageResource(R.drawable.c27);
                break;
            case 14:
                image5.setImageResource(R.drawable.c28);
                break;
            case 15:
                image5.setImageResource(R.drawable.c29);
                break;
            case 16:
                image5.setImageResource(R.drawable.c30);
                break;
            case 17:
                image5.setImageResource(R.drawable.c31);
                break;
            case 18:
                image5.setImageResource(R.drawable.c32);
                break;
            case 19:
                image5.setImageResource(R.drawable.c33);
                break;
            case 20:
                image5.setImageResource(R.drawable.c34);
                break;
            case 21:
                image5.setImageResource(R.drawable.c35);
                break;
            case 22:
                image5.setImageResource(R.drawable.c36);
                break;
            case 23:
                image5.setImageResource(R.drawable.c37);
                break;
            case 24:
                image5.setImageResource(R.drawable.c38);
                break;
            case 25:
                image5.setImageResource(R.drawable.c39);
                break;
            case 26:
                image5.setImageResource(R.drawable.c14);
                break;
            case 27:
                image5.setImageResource(R.drawable.c15);
                break;
            case 28:
                image5.setImageResource(R.drawable.c16);
                break;
            case 29:
                image5.setImageResource(R.drawable.c17);
                break;
            case 30:
                image5.setImageResource(R.drawable.c18);
                break;
            case 31:
                image5.setImageResource(R.drawable.c19);
                break;
            case 32:
                image5.setImageResource(R.drawable.c20);
                break;
            case 33:
                image5.setImageResource(R.drawable.c21);
                break;
            case 34:
                image5.setImageResource(R.drawable.c22);
                break;
            case 35:
                image5.setImageResource(R.drawable.c23);
                break;
            case 36:
                image5.setImageResource(R.drawable.c24);
                break;
            case 37:
                image5.setImageResource(R.drawable.c25);
                break;
            case 38:
                image5.setImageResource(R.drawable.c26);
                break;
            case 39:
                image5.setImageResource(R.drawable.c1);
                break;
            case 40:
                image5.setImageResource(R.drawable.c2);
                break;
            case 41:
                image5.setImageResource(R.drawable.c3);
                break;
            case 42:
                image5.setImageResource(R.drawable.c4);
                break;
            case 43:
                image5.setImageResource(R.drawable.c5);
                break;
            case 44:
                image5.setImageResource(R.drawable.c6);
                break;
            case 45:
                image5.setImageResource(R.drawable.c7);
                break;
            case 46:
                image5.setImageResource(R.drawable.c8);
                break;
            case 47:
                image5.setImageResource(R.drawable.c9);
                break;
            case 48:
                image5.setImageResource(R.drawable.c10);
                break;
            case 49:
                image5.setImageResource(R.drawable.c11);
                break;
            case 50:
                image5.setImageResource(R.drawable.c12);
                break;
            case 51:
                image5.setImageResource(R.drawable.c13);
                break;
            default:
                break;
        }

        image6 = findViewById(R.id.imageView6);
        switch (cardID[5]) {
            case 0:
                image6.setImageResource(R.drawable.c40);
                break;
            case 1:
                image6.setImageResource(R.drawable.c41);
                break;
            case 2:
                image6.setImageResource(R.drawable.c42);
                break;
            case 3:
                image6.setImageResource(R.drawable.c43);
                break;
            case 4:
                image6.setImageResource(R.drawable.c44);
                break;
            case 5:
                image6.setImageResource(R.drawable.c45);
                break;
            case 6:
                image6.setImageResource(R.drawable.c46);
                break;
            case 7:
                image6.setImageResource(R.drawable.c47);
                break;
            case 8:
                image6.setImageResource(R.drawable.c48);
                break;
            case 9:
                image6.setImageResource(R.drawable.c49);
                break;
            case 10:
                image6.setImageResource(R.drawable.c50);
                break;
            case 11:
                image6.setImageResource(R.drawable.c51);
                break;
            case 12:
                image6.setImageResource(R.drawable.c52);
                break;
            case 13:
                image6.setImageResource(R.drawable.c27);
                break;
            case 14:
                image6.setImageResource(R.drawable.c28);
                break;
            case 15:
                image6.setImageResource(R.drawable.c29);
                break;
            case 16:
                image6.setImageResource(R.drawable.c30);
                break;
            case 17:
                image6.setImageResource(R.drawable.c31);
                break;
            case 18:
                image6.setImageResource(R.drawable.c32);
                break;
            case 19:
                image6.setImageResource(R.drawable.c33);
                break;
            case 20:
                image6.setImageResource(R.drawable.c34);
                break;
            case 21:
                image6.setImageResource(R.drawable.c35);
                break;
            case 22:
                image6.setImageResource(R.drawable.c36);
                break;
            case 23:
                image6.setImageResource(R.drawable.c37);
                break;
            case 24:
                image6.setImageResource(R.drawable.c38);
                break;
            case 25:
                image6.setImageResource(R.drawable.c39);
                break;
            case 26:
                image6.setImageResource(R.drawable.c14);
                break;
            case 27:
                image6.setImageResource(R.drawable.c15);
                break;
            case 28:
                image6.setImageResource(R.drawable.c16);
                break;
            case 29:
                image6.setImageResource(R.drawable.c17);
                break;
            case 30:
                image6.setImageResource(R.drawable.c18);
                break;
            case 31:
                image6.setImageResource(R.drawable.c19);
                break;
            case 32:
                image6.setImageResource(R.drawable.c20);
                break;
            case 33:
                image6.setImageResource(R.drawable.c21);
                break;
            case 34:
                image6.setImageResource(R.drawable.c22);
                break;
            case 35:
                image6.setImageResource(R.drawable.c23);
                break;
            case 36:
                image6.setImageResource(R.drawable.c24);
                break;
            case 37:
                image6.setImageResource(R.drawable.c25);
                break;
            case 38:
                image6.setImageResource(R.drawable.c26);
                break;
            case 39:
                image6.setImageResource(R.drawable.c1);
                break;
            case 40:
                image6.setImageResource(R.drawable.c2);
                break;
            case 41:
                image6.setImageResource(R.drawable.c3);
                break;
            case 42:
                image6.setImageResource(R.drawable.c4);
                break;
            case 43:
                image6.setImageResource(R.drawable.c5);
                break;
            case 44:
                image6.setImageResource(R.drawable.c6);
                break;
            case 45:
                image6.setImageResource(R.drawable.c7);
                break;
            case 46:
                image6.setImageResource(R.drawable.c8);
                break;
            case 47:
                image6.setImageResource(R.drawable.c9);
                break;
            case 48:
                image6.setImageResource(R.drawable.c10);
                break;
            case 49:
                image6.setImageResource(R.drawable.c11);
                break;
            case 50:
                image6.setImageResource(R.drawable.c12);
                break;
            case 51:
                image6.setImageResource(R.drawable.c13);
                break;
            default:
                break;
        }

        image7 = findViewById(R.id.imageView7);
        switch (cardID[6]) {
            case 0:
                image7.setImageResource(R.drawable.c40);
                break;
            case 1:
                image7.setImageResource(R.drawable.c41);
                break;
            case 2:
                image7.setImageResource(R.drawable.c42);
                break;
            case 3:
                image7.setImageResource(R.drawable.c43);
                break;
            case 4:
                image7.setImageResource(R.drawable.c44);
                break;
            case 5:
                image7.setImageResource(R.drawable.c45);
                break;
            case 6:
                image7.setImageResource(R.drawable.c46);
                break;
            case 7:
                image7.setImageResource(R.drawable.c47);
                break;
            case 8:
                image7.setImageResource(R.drawable.c48);
                break;
            case 9:
                image7.setImageResource(R.drawable.c49);
                break;
            case 10:
                image7.setImageResource(R.drawable.c50);
                break;
            case 11:
                image7.setImageResource(R.drawable.c51);
                break;
            case 12:
                image7.setImageResource(R.drawable.c52);
                break;
            case 13:
                image7.setImageResource(R.drawable.c27);
                break;
            case 14:
                image7.setImageResource(R.drawable.c28);
                break;
            case 15:
                image7.setImageResource(R.drawable.c29);
                break;
            case 16:
                image7.setImageResource(R.drawable.c30);
                break;
            case 17:
                image7.setImageResource(R.drawable.c31);
                break;
            case 18:
                image7.setImageResource(R.drawable.c32);
                break;
            case 19:
                image7.setImageResource(R.drawable.c33);
                break;
            case 20:
                image7.setImageResource(R.drawable.c34);
                break;
            case 21:
                image7.setImageResource(R.drawable.c35);
                break;
            case 22:
                image7.setImageResource(R.drawable.c36);
                break;
            case 23:
                image7.setImageResource(R.drawable.c37);
                break;
            case 24:
                image7.setImageResource(R.drawable.c38);
                break;
            case 25:
                image7.setImageResource(R.drawable.c39);
                break;
            case 26:
                image7.setImageResource(R.drawable.c14);
                break;
            case 27:
                image7.setImageResource(R.drawable.c15);
                break;
            case 28:
                image7.setImageResource(R.drawable.c16);
                break;
            case 29:
                image7.setImageResource(R.drawable.c17);
                break;
            case 30:
                image7.setImageResource(R.drawable.c18);
                break;
            case 31:
                image7.setImageResource(R.drawable.c19);
                break;
            case 32:
                image7.setImageResource(R.drawable.c20);
                break;
            case 33:
                image7.setImageResource(R.drawable.c21);
                break;
            case 34:
                image7.setImageResource(R.drawable.c22);
                break;
            case 35:
                image7.setImageResource(R.drawable.c23);
                break;
            case 36:
                image7.setImageResource(R.drawable.c24);
                break;
            case 37:
                image7.setImageResource(R.drawable.c25);
                break;
            case 38:
                image7.setImageResource(R.drawable.c26);
                break;
            case 39:
                image7.setImageResource(R.drawable.c1);
                break;
            case 40:
                image7.setImageResource(R.drawable.c2);
                break;
            case 41:
                image7.setImageResource(R.drawable.c3);
                break;
            case 42:
                image7.setImageResource(R.drawable.c4);
                break;
            case 43:
                image7.setImageResource(R.drawable.c5);
                break;
            case 44:
                image7.setImageResource(R.drawable.c6);
                break;
            case 45:
                image7.setImageResource(R.drawable.c7);
                break;
            case 46:
                image7.setImageResource(R.drawable.c8);
                break;
            case 47:
                image7.setImageResource(R.drawable.c9);
                break;
            case 48:
                image7.setImageResource(R.drawable.c10);
                break;
            case 49:
                image7.setImageResource(R.drawable.c11);
                break;
            case 50:
                image7.setImageResource(R.drawable.c12);
                break;
            case 51:
                image7.setImageResource(R.drawable.c13);
                break;
            default:
                break;
        }

        image8 = findViewById(R.id.imageView8);
        switch (cardID[7]) {
            case 0:
                image8.setImageResource(R.drawable.c40);
                break;
            case 1:
                image8.setImageResource(R.drawable.c41);
                break;
            case 2:
                image8.setImageResource(R.drawable.c42);
                break;
            case 3:
                image8.setImageResource(R.drawable.c43);
                break;
            case 4:
                image8.setImageResource(R.drawable.c44);
                break;
            case 5:
                image8.setImageResource(R.drawable.c45);
                break;
            case 6:
                image8.setImageResource(R.drawable.c46);
                break;
            case 7:
                image8.setImageResource(R.drawable.c47);
                break;
            case 8:
                image8.setImageResource(R.drawable.c48);
                break;
            case 9:
                image8.setImageResource(R.drawable.c49);
                break;
            case 10:
                image8.setImageResource(R.drawable.c50);
                break;
            case 11:
                image8.setImageResource(R.drawable.c51);
                break;
            case 12:
                image8.setImageResource(R.drawable.c52);
                break;
            case 13:
                image8.setImageResource(R.drawable.c27);
                break;
            case 14:
                image8.setImageResource(R.drawable.c28);
                break;
            case 15:
                image8.setImageResource(R.drawable.c29);
                break;
            case 16:
                image8.setImageResource(R.drawable.c30);
                break;
            case 17:
                image8.setImageResource(R.drawable.c31);
                break;
            case 18:
                image8.setImageResource(R.drawable.c32);
                break;
            case 19:
                image8.setImageResource(R.drawable.c33);
                break;
            case 20:
                image8.setImageResource(R.drawable.c34);
                break;
            case 21:
                image8.setImageResource(R.drawable.c35);
                break;
            case 22:
                image8.setImageResource(R.drawable.c36);
                break;
            case 23:
                image8.setImageResource(R.drawable.c37);
                break;
            case 24:
                image8.setImageResource(R.drawable.c38);
                break;
            case 25:
                image8.setImageResource(R.drawable.c39);
                break;
            case 26:
                image8.setImageResource(R.drawable.c14);
                break;
            case 27:
                image8.setImageResource(R.drawable.c15);
                break;
            case 28:
                image8.setImageResource(R.drawable.c16);
                break;
            case 29:
                image8.setImageResource(R.drawable.c17);
                break;
            case 30:
                image8.setImageResource(R.drawable.c18);
                break;
            case 31:
                image8.setImageResource(R.drawable.c19);
                break;
            case 32:
                image8.setImageResource(R.drawable.c20);
                break;
            case 33:
                image8.setImageResource(R.drawable.c21);
                break;
            case 34:
                image8.setImageResource(R.drawable.c22);
                break;
            case 35:
                image8.setImageResource(R.drawable.c23);
                break;
            case 36:
                image8.setImageResource(R.drawable.c24);
                break;
            case 37:
                image8.setImageResource(R.drawable.c25);
                break;
            case 38:
                image8.setImageResource(R.drawable.c26);
                break;
            case 39:
                image8.setImageResource(R.drawable.c1);
                break;
            case 40:
                image8.setImageResource(R.drawable.c2);
                break;
            case 41:
                image8.setImageResource(R.drawable.c3);
                break;
            case 42:
                image8.setImageResource(R.drawable.c4);
                break;
            case 43:
                image8.setImageResource(R.drawable.c5);
                break;
            case 44:
                image8.setImageResource(R.drawable.c6);
                break;
            case 45:
                image8.setImageResource(R.drawable.c7);
                break;
            case 46:
                image8.setImageResource(R.drawable.c8);
                break;
            case 47:
                image8.setImageResource(R.drawable.c9);
                break;
            case 48:
                image8.setImageResource(R.drawable.c10);
                break;
            case 49:
                image8.setImageResource(R.drawable.c11);
                break;
            case 50:
                image8.setImageResource(R.drawable.c12);
                break;
            case 51:
                image8.setImageResource(R.drawable.c13);
                break;
            default:
                break;
        }

        image9 = findViewById(R.id.imageView9);
        switch (cardID[8]) {
            case 0:
                image9.setImageResource(R.drawable.c40);
                break;
            case 1:
                image9.setImageResource(R.drawable.c41);
                break;
            case 2:
                image9.setImageResource(R.drawable.c42);
                break;
            case 3:
                image9.setImageResource(R.drawable.c43);
                break;
            case 4:
                image9.setImageResource(R.drawable.c44);
                break;
            case 5:
                image9.setImageResource(R.drawable.c45);
                break;
            case 6:
                image9.setImageResource(R.drawable.c46);
                break;
            case 7:
                image9.setImageResource(R.drawable.c47);
                break;
            case 8:
                image9.setImageResource(R.drawable.c48);
                break;
            case 9:
                image9.setImageResource(R.drawable.c49);
                break;
            case 10:
                image9.setImageResource(R.drawable.c50);
                break;
            case 11:
                image9.setImageResource(R.drawable.c51);
                break;
            case 12:
                image9.setImageResource(R.drawable.c52);
                break;
            case 13:
                image9.setImageResource(R.drawable.c27);
                break;
            case 14:
                image9.setImageResource(R.drawable.c28);
                break;
            case 15:
                image9.setImageResource(R.drawable.c29);
                break;
            case 16:
                image9.setImageResource(R.drawable.c30);
                break;
            case 17:
                image9.setImageResource(R.drawable.c31);
                break;
            case 18:
                image9.setImageResource(R.drawable.c32);
                break;
            case 19:
                image9.setImageResource(R.drawable.c33);
                break;
            case 20:
                image9.setImageResource(R.drawable.c34);
                break;
            case 21:
                image9.setImageResource(R.drawable.c35);
                break;
            case 22:
                image9.setImageResource(R.drawable.c36);
                break;
            case 23:
                image9.setImageResource(R.drawable.c37);
                break;
            case 24:
                image9.setImageResource(R.drawable.c38);
                break;
            case 25:
                image9.setImageResource(R.drawable.c39);
                break;
            case 26:
                image9.setImageResource(R.drawable.c14);
                break;
            case 27:
                image9.setImageResource(R.drawable.c15);
                break;
            case 28:
                image9.setImageResource(R.drawable.c16);
                break;
            case 29:
                image9.setImageResource(R.drawable.c17);
                break;
            case 30:
                image9.setImageResource(R.drawable.c18);
                break;
            case 31:
                image9.setImageResource(R.drawable.c19);
                break;
            case 32:
                image9.setImageResource(R.drawable.c20);
                break;
            case 33:
                image9.setImageResource(R.drawable.c21);
                break;
            case 34:
                image9.setImageResource(R.drawable.c22);
                break;
            case 35:
                image9.setImageResource(R.drawable.c23);
                break;
            case 36:
                image9.setImageResource(R.drawable.c24);
                break;
            case 37:
                image9.setImageResource(R.drawable.c25);
                break;
            case 38:
                image9.setImageResource(R.drawable.c26);
                break;
            case 39:
                image9.setImageResource(R.drawable.c1);
                break;
            case 40:
                image9.setImageResource(R.drawable.c2);
                break;
            case 41:
                image9.setImageResource(R.drawable.c3);
                break;
            case 42:
                image9.setImageResource(R.drawable.c4);
                break;
            case 43:
                image9.setImageResource(R.drawable.c5);
                break;
            case 44:
                image9.setImageResource(R.drawable.c6);
                break;
            case 45:
                image9.setImageResource(R.drawable.c7);
                break;
            case 46:
                image9.setImageResource(R.drawable.c8);
                break;
            case 47:
                image9.setImageResource(R.drawable.c9);
                break;
            case 48:
                image9.setImageResource(R.drawable.c10);
                break;
            case 49:
                image9.setImageResource(R.drawable.c11);
                break;
            case 50:
                image9.setImageResource(R.drawable.c12);
                break;
            case 51:
                image9.setImageResource(R.drawable.c13);
                break;
            default:
                break;
        }

        image10 = findViewById(R.id.imageView10);
        switch (cardID[9]) {
            case 0:
                image10.setImageResource(R.drawable.c40);
                break;
            case 1:
                image10.setImageResource(R.drawable.c41);
                break;
            case 2:
                image10.setImageResource(R.drawable.c42);
                break;
            case 3:
                image10.setImageResource(R.drawable.c43);
                break;
            case 4:
                image10.setImageResource(R.drawable.c44);
                break;
            case 5:
                image10.setImageResource(R.drawable.c45);
                break;
            case 6:
                image10.setImageResource(R.drawable.c46);
                break;
            case 7:
                image10.setImageResource(R.drawable.c47);
                break;
            case 8:
                image10.setImageResource(R.drawable.c48);
                break;
            case 9:
                image10.setImageResource(R.drawable.c49);
                break;
            case 10:
                image10.setImageResource(R.drawable.c50);
                break;
            case 11:
                image10.setImageResource(R.drawable.c51);
                break;
            case 12:
                image10.setImageResource(R.drawable.c52);
                break;
            case 13:
                image10.setImageResource(R.drawable.c27);
                break;
            case 14:
                image10.setImageResource(R.drawable.c28);
                break;
            case 15:
                image10.setImageResource(R.drawable.c29);
                break;
            case 16:
                image10.setImageResource(R.drawable.c30);
                break;
            case 17:
                image10.setImageResource(R.drawable.c31);
                break;
            case 18:
                image10.setImageResource(R.drawable.c32);
                break;
            case 19:
                image10.setImageResource(R.drawable.c33);
                break;
            case 20:
                image10.setImageResource(R.drawable.c34);
                break;
            case 21:
                image10.setImageResource(R.drawable.c35);
                break;
            case 22:
                image10.setImageResource(R.drawable.c36);
                break;
            case 23:
                image10.setImageResource(R.drawable.c37);
                break;
            case 24:
                image10.setImageResource(R.drawable.c38);
                break;
            case 25:
                image10.setImageResource(R.drawable.c39);
                break;
            case 26:
                image10.setImageResource(R.drawable.c14);
                break;
            case 27:
                image10.setImageResource(R.drawable.c15);
                break;
            case 28:
                image10.setImageResource(R.drawable.c16);
                break;
            case 29:
                image10.setImageResource(R.drawable.c17);
                break;
            case 30:
                image10.setImageResource(R.drawable.c18);
                break;
            case 31:
                image10.setImageResource(R.drawable.c19);
                break;
            case 32:
                image10.setImageResource(R.drawable.c20);
                break;
            case 33:
                image10.setImageResource(R.drawable.c21);
                break;
            case 34:
                image10.setImageResource(R.drawable.c22);
                break;
            case 35:
                image10.setImageResource(R.drawable.c23);
                break;
            case 36:
                image10.setImageResource(R.drawable.c24);
                break;
            case 37:
                image10.setImageResource(R.drawable.c25);
                break;
            case 38:
                image10.setImageResource(R.drawable.c26);
                break;
            case 39:
                image10.setImageResource(R.drawable.c1);
                break;
            case 40:
                image10.setImageResource(R.drawable.c2);
                break;
            case 41:
                image10.setImageResource(R.drawable.c3);
                break;
            case 42:
                image10.setImageResource(R.drawable.c4);
                break;
            case 43:
                image10.setImageResource(R.drawable.c5);
                break;
            case 44:
                image10.setImageResource(R.drawable.c6);
                break;
            case 45:
                image10.setImageResource(R.drawable.c7);
                break;
            case 46:
                image10.setImageResource(R.drawable.c8);
                break;
            case 47:
                image10.setImageResource(R.drawable.c9);
                break;
            case 48:
                image10.setImageResource(R.drawable.c10);
                break;
            case 49:
                image10.setImageResource(R.drawable.c11);
                break;
            case 50:
                image10.setImageResource(R.drawable.c12);
                break;
            case 51:
                image10.setImageResource(R.drawable.c13);
                break;
            default:
                break;
        }

        image11 = findViewById(R.id.imageView11);
        switch (cardID[10]) {
            case 0:
                image11.setImageResource(R.drawable.c40);
                break;
            case 1:
                image11.setImageResource(R.drawable.c41);
                break;
            case 2:
                image11.setImageResource(R.drawable.c42);
                break;
            case 3:
                image11.setImageResource(R.drawable.c43);
                break;
            case 4:
                image11.setImageResource(R.drawable.c44);
                break;
            case 5:
                image11.setImageResource(R.drawable.c45);
                break;
            case 6:
                image11.setImageResource(R.drawable.c46);
                break;
            case 7:
                image11.setImageResource(R.drawable.c47);
                break;
            case 8:
                image11.setImageResource(R.drawable.c48);
                break;
            case 9:
                image11.setImageResource(R.drawable.c49);
                break;
            case 10:
                image11.setImageResource(R.drawable.c50);
                break;
            case 11:
                image11.setImageResource(R.drawable.c51);
                break;
            case 12:
                image11.setImageResource(R.drawable.c52);
                break;
            case 13:
                image11.setImageResource(R.drawable.c27);
                break;
            case 14:
                image11.setImageResource(R.drawable.c28);
                break;
            case 15:
                image11.setImageResource(R.drawable.c29);
                break;
            case 16:
                image11.setImageResource(R.drawable.c30);
                break;
            case 17:
                image11.setImageResource(R.drawable.c31);
                break;
            case 18:
                image11.setImageResource(R.drawable.c32);
                break;
            case 19:
                image11.setImageResource(R.drawable.c33);
                break;
            case 20:
                image11.setImageResource(R.drawable.c34);
                break;
            case 21:
                image11.setImageResource(R.drawable.c35);
                break;
            case 22:
                image11.setImageResource(R.drawable.c36);
                break;
            case 23:
                image11.setImageResource(R.drawable.c37);
                break;
            case 24:
                image11.setImageResource(R.drawable.c38);
                break;
            case 25:
                image11.setImageResource(R.drawable.c39);
                break;
            case 26:
                image11.setImageResource(R.drawable.c14);
                break;
            case 27:
                image11.setImageResource(R.drawable.c15);
                break;
            case 28:
                image11.setImageResource(R.drawable.c16);
                break;
            case 29:
                image11.setImageResource(R.drawable.c17);
                break;
            case 30:
                image11.setImageResource(R.drawable.c18);
                break;
            case 31:
                image11.setImageResource(R.drawable.c19);
                break;
            case 32:
                image11.setImageResource(R.drawable.c20);
                break;
            case 33:
                image11.setImageResource(R.drawable.c21);
                break;
            case 34:
                image11.setImageResource(R.drawable.c22);
                break;
            case 35:
                image11.setImageResource(R.drawable.c23);
                break;
            case 36:
                image11.setImageResource(R.drawable.c24);
                break;
            case 37:
                image11.setImageResource(R.drawable.c25);
                break;
            case 38:
                image11.setImageResource(R.drawable.c26);
                break;
            case 39:
                image11.setImageResource(R.drawable.c1);
                break;
            case 40:
                image11.setImageResource(R.drawable.c2);
                break;
            case 41:
                image11.setImageResource(R.drawable.c3);
                break;
            case 42:
                image11.setImageResource(R.drawable.c4);
                break;
            case 43:
                image11.setImageResource(R.drawable.c5);
                break;
            case 44:
                image11.setImageResource(R.drawable.c6);
                break;
            case 45:
                image11.setImageResource(R.drawable.c7);
                break;
            case 46:
                image11.setImageResource(R.drawable.c8);
                break;
            case 47:
                image11.setImageResource(R.drawable.c9);
                break;
            case 48:
                image11.setImageResource(R.drawable.c10);
                break;
            case 49:
                image11.setImageResource(R.drawable.c11);
                break;
            case 50:
                image11.setImageResource(R.drawable.c12);
                break;
            case 51:
                image11.setImageResource(R.drawable.c13);
                break;
            default:
                break;
        }

        image12 = findViewById(R.id.imageView12);
        switch (cardID[11]) {
            case 0:
                image12.setImageResource(R.drawable.c40);
                break;
            case 1:
                image12.setImageResource(R.drawable.c41);
                break;
            case 2:
                image12.setImageResource(R.drawable.c42);
                break;
            case 3:
                image12.setImageResource(R.drawable.c43);
                break;
            case 4:
                image12.setImageResource(R.drawable.c44);
                break;
            case 5:
                image12.setImageResource(R.drawable.c45);
                break;
            case 6:
                image12.setImageResource(R.drawable.c46);
                break;
            case 7:
                image12.setImageResource(R.drawable.c47);
                break;
            case 8:
                image12.setImageResource(R.drawable.c48);
                break;
            case 9:
                image12.setImageResource(R.drawable.c49);
                break;
            case 10:
                image12.setImageResource(R.drawable.c50);
                break;
            case 11:
                image12.setImageResource(R.drawable.c51);
                break;
            case 12:
                image12.setImageResource(R.drawable.c52);
                break;
            case 13:
                image12.setImageResource(R.drawable.c27);
                break;
            case 14:
                image12.setImageResource(R.drawable.c28);
                break;
            case 15:
                image12.setImageResource(R.drawable.c29);
                break;
            case 16:
                image12.setImageResource(R.drawable.c30);
                break;
            case 17:
                image12.setImageResource(R.drawable.c31);
                break;
            case 18:
                image12.setImageResource(R.drawable.c32);
                break;
            case 19:
                image12.setImageResource(R.drawable.c33);
                break;
            case 20:
                image12.setImageResource(R.drawable.c34);
                break;
            case 21:
                image12.setImageResource(R.drawable.c35);
                break;
            case 22:
                image12.setImageResource(R.drawable.c36);
                break;
            case 23:
                image12.setImageResource(R.drawable.c37);
                break;
            case 24:
                image12.setImageResource(R.drawable.c38);
                break;
            case 25:
                image12.setImageResource(R.drawable.c39);
                break;
            case 26:
                image12.setImageResource(R.drawable.c14);
                break;
            case 27:
                image12.setImageResource(R.drawable.c15);
                break;
            case 28:
                image12.setImageResource(R.drawable.c16);
                break;
            case 29:
                image12.setImageResource(R.drawable.c17);
                break;
            case 30:
                image12.setImageResource(R.drawable.c18);
                break;
            case 31:
                image12.setImageResource(R.drawable.c19);
                break;
            case 32:
                image12.setImageResource(R.drawable.c20);
                break;
            case 33:
                image12.setImageResource(R.drawable.c21);
                break;
            case 34:
                image12.setImageResource(R.drawable.c22);
                break;
            case 35:
                image12.setImageResource(R.drawable.c23);
                break;
            case 36:
                image12.setImageResource(R.drawable.c24);
                break;
            case 37:
                image12.setImageResource(R.drawable.c25);
                break;
            case 38:
                image12.setImageResource(R.drawable.c26);
                break;
            case 39:
                image12.setImageResource(R.drawable.c1);
                break;
            case 40:
                image12.setImageResource(R.drawable.c2);
                break;
            case 41:
                image12.setImageResource(R.drawable.c3);
                break;
            case 42:
                image12.setImageResource(R.drawable.c4);
                break;
            case 43:
                image12.setImageResource(R.drawable.c5);
                break;
            case 44:
                image12.setImageResource(R.drawable.c6);
                break;
            case 45:
                image12.setImageResource(R.drawable.c7);
                break;
            case 46:
                image12.setImageResource(R.drawable.c8);
                break;
            case 47:
                image12.setImageResource(R.drawable.c9);
                break;
            case 48:
                image12.setImageResource(R.drawable.c10);
                break;
            case 49:
                image12.setImageResource(R.drawable.c11);
                break;
            case 50:
                image12.setImageResource(R.drawable.c12);
                break;
            case 51:
                image12.setImageResource(R.drawable.c13);
                break;
            default:
                break;
        }

        image13 = findViewById(R.id.imageView13);
        switch (cardID[12]) {
            case 0:
                image13.setImageResource(R.drawable.c40);
                break;
            case 1:
                image13.setImageResource(R.drawable.c41);
                break;
            case 2:
                image13.setImageResource(R.drawable.c42);
                break;
            case 3:
                image13.setImageResource(R.drawable.c43);
                break;
            case 4:
                image13.setImageResource(R.drawable.c44);
                break;
            case 5:
                image13.setImageResource(R.drawable.c45);
                break;
            case 6:
                image13.setImageResource(R.drawable.c46);
                break;
            case 7:
                image13.setImageResource(R.drawable.c47);
                break;
            case 8:
                image13.setImageResource(R.drawable.c48);
                break;
            case 9:
                image13.setImageResource(R.drawable.c49);
                break;
            case 10:
                image13.setImageResource(R.drawable.c50);
                break;
            case 11:
                image13.setImageResource(R.drawable.c51);
                break;
            case 12:
                image13.setImageResource(R.drawable.c52);
                break;
            case 13:
                image13.setImageResource(R.drawable.c27);
                break;
            case 14:
                image13.setImageResource(R.drawable.c28);
                break;
            case 15:
                image13.setImageResource(R.drawable.c29);
                break;
            case 16:
                image13.setImageResource(R.drawable.c30);
                break;
            case 17:
                image13.setImageResource(R.drawable.c31);
                break;
            case 18:
                image13.setImageResource(R.drawable.c32);
                break;
            case 19:
                image13.setImageResource(R.drawable.c33);
                break;
            case 20:
                image13.setImageResource(R.drawable.c34);
                break;
            case 21:
                image13.setImageResource(R.drawable.c35);
                break;
            case 22:
                image13.setImageResource(R.drawable.c36);
                break;
            case 23:
                image13.setImageResource(R.drawable.c37);
                break;
            case 24:
                image13.setImageResource(R.drawable.c38);
                break;
            case 25:
                image13.setImageResource(R.drawable.c39);
                break;
            case 26:
                image13.setImageResource(R.drawable.c14);
                break;
            case 27:
                image13.setImageResource(R.drawable.c15);
                break;
            case 28:
                image13.setImageResource(R.drawable.c16);
                break;
            case 29:
                image13.setImageResource(R.drawable.c17);
                break;
            case 30:
                image13.setImageResource(R.drawable.c18);
                break;
            case 31:
                image13.setImageResource(R.drawable.c19);
                break;
            case 32:
                image13.setImageResource(R.drawable.c20);
                break;
            case 33:
                image13.setImageResource(R.drawable.c21);
                break;
            case 34:
                image13.setImageResource(R.drawable.c22);
                break;
            case 35:
                image13.setImageResource(R.drawable.c23);
                break;
            case 36:
                image13.setImageResource(R.drawable.c24);
                break;
            case 37:
                image13.setImageResource(R.drawable.c25);
                break;
            case 38:
                image13.setImageResource(R.drawable.c26);
                break;
            case 39:
                image13.setImageResource(R.drawable.c1);
                break;
            case 40:
                image13.setImageResource(R.drawable.c2);
                break;
            case 41:
                image13.setImageResource(R.drawable.c3);
                break;
            case 42:
                image13.setImageResource(R.drawable.c4);
                break;
            case 43:
                image13.setImageResource(R.drawable.c5);
                break;
            case 44:
                image13.setImageResource(R.drawable.c6);
                break;
            case 45:
                image13.setImageResource(R.drawable.c7);
                break;
            case 46:
                image13.setImageResource(R.drawable.c8);
                break;
            case 47:
                image13.setImageResource(R.drawable.c9);
                break;
            case 48:
                image13.setImageResource(R.drawable.c10);
                break;
            case 49:
                image13.setImageResource(R.drawable.c11);
                break;
            case 50:
                image13.setImageResource(R.drawable.c12);
                break;
            case 51:
                image13.setImageResource(R.drawable.c13);
                break;
            default:
                break;
        }
        setListeners();
        thread.start();
        mhandle=new Handler(){
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
            @SuppressLint("HandlerLeak")
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                int temp=0;
                int temp1[] = new int[3];
                temp1= (int[]) msg.obj;
                switch (temp1[2]){
                    case 0 :
                        switch (temp1[0]){
                            case 1:
                                Log.d("s","收到机器人出牌消息");
                                switch (temp1[1]){
                                    case 1:
                                        Log.d("s","收到一号机器人出牌消息");
                                        imageView_robot1_show1.setImageDrawable(null);
                                        imageView_robot1_show2.setImageDrawable(null);
                                        imageView_robot1_show3.setImageDrawable(null);
                                        imageView_robot1_show4.setImageDrawable(null);
                                        imageView_robot1_show5.setImageDrawable(null);
                                        temp = Ruler.getCardsShowedByPreviousPlayer().size();
                                        temp = Integer.valueOf(CardNumber1.getText().toString()) - temp;
                                        CardNumber1.setText(String.valueOf(temp));
                                        for (int t = 0; t < Ruler.getCardsShowedByPreviousPlayer().size(); t++) {
                                            for (int k = 0; k < cardID.length; k++) {
                                                if (Ruler.getCardsShowedByPreviousPlayer().get(t).getCardId() == RobotCardID[0][k]) {
                                                    switch (t) {
                                                        case 0:
                                                            switch (RobotCardID[0][k]) {
                                                                case 0:
                                                                    imageView_robot1_show1.setImageResource(R.drawable.c40);
                                                                    break;
                                                                case 1:
                                                                    imageView_robot1_show1.setImageResource(R.drawable.c41);
                                                                    break;
                                                                case 2:
                                                                    imageView_robot1_show1.setImageResource(R.drawable.c42);
                                                                    break;
                                                                case 3:
                                                                    imageView_robot1_show1.setImageResource(R.drawable.c43);
                                                                    break;
                                                                case 4:
                                                                    imageView_robot1_show1.setImageResource(R.drawable.c44);
                                                                    break;
                                                                case 5:
                                                                    imageView_robot1_show1.setImageResource(R.drawable.c45);
                                                                    break;
                                                                case 6:
                                                                    imageView_robot1_show1.setImageResource(R.drawable.c46);
                                                                    break;
                                                                case 7:
                                                                    imageView_robot1_show1.setImageResource(R.drawable.c47);
                                                                    break;
                                                                case 8:
                                                                    imageView_robot1_show1.setImageResource(R.drawable.c48);
                                                                    break;
                                                                case 9:
                                                                    imageView_robot1_show1.setImageResource(R.drawable.c49);
                                                                    break;
                                                                case 10:
                                                                    imageView_robot1_show1.setImageResource(R.drawable.c50);
                                                                    break;
                                                                case 11:
                                                                    imageView_robot1_show1.setImageResource(R.drawable.c51);
                                                                    break;
                                                                case 12:
                                                                    imageView_robot1_show1.setImageResource(R.drawable.c52);
                                                                    break;
                                                                case 13:
                                                                    imageView_robot1_show1.setImageResource(R.drawable.c27);
                                                                    break;
                                                                case 14:
                                                                    imageView_robot1_show1.setImageResource(R.drawable.c28);
                                                                    break;
                                                                case 15:
                                                                    imageView_robot1_show1.setImageResource(R.drawable.c29);
                                                                    break;
                                                                case 16:
                                                                    imageView_robot1_show1.setImageResource(R.drawable.c30);
                                                                    break;
                                                                case 17:
                                                                    imageView_robot1_show1.setImageResource(R.drawable.c31);
                                                                    break;
                                                                case 18:
                                                                    imageView_robot1_show1.setImageResource(R.drawable.c32);
                                                                    break;
                                                                case 19:
                                                                    imageView_robot1_show1.setImageResource(R.drawable.c33);
                                                                    break;
                                                                case 20:
                                                                    imageView_robot1_show1.setImageResource(R.drawable.c34);
                                                                    break;
                                                                case 21:
                                                                    imageView_robot1_show1.setImageResource(R.drawable.c35);
                                                                    break;
                                                                case 22:
                                                                    imageView_robot1_show1.setImageResource(R.drawable.c36);
                                                                    break;
                                                                case 23:
                                                                    imageView_robot1_show1.setImageResource(R.drawable.c37);
                                                                    break;
                                                                case 24:
                                                                    imageView_robot1_show1.setImageResource(R.drawable.c38);
                                                                    break;
                                                                case 25:
                                                                    imageView_robot1_show1.setImageResource(R.drawable.c39);
                                                                    break;
                                                                case 26:
                                                                    imageView_robot1_show1.setImageResource(R.drawable.c14);
                                                                    break;
                                                                case 27:
                                                                    imageView_robot1_show1.setImageResource(R.drawable.c15);
                                                                    break;
                                                                case 28:
                                                                    imageView_robot1_show1.setImageResource(R.drawable.c16);
                                                                    break;
                                                                case 29:
                                                                    imageView_robot1_show1.setImageResource(R.drawable.c17);
                                                                    break;
                                                                case 30:
                                                                    imageView_robot1_show1.setImageResource(R.drawable.c18);
                                                                    break;
                                                                case 31:
                                                                    imageView_robot1_show1.setImageResource(R.drawable.c19);
                                                                    break;
                                                                case 32:
                                                                    imageView_robot1_show1.setImageResource(R.drawable.c20);
                                                                    break;
                                                                case 33:
                                                                    imageView_robot1_show1.setImageResource(R.drawable.c21);
                                                                    break;
                                                                case 34:
                                                                    imageView_robot1_show1.setImageResource(R.drawable.c22);
                                                                    break;
                                                                case 35:
                                                                    imageView_robot1_show1.setImageResource(R.drawable.c23);
                                                                    break;
                                                                case 36:
                                                                    imageView_robot1_show1.setImageResource(R.drawable.c24);
                                                                    break;
                                                                case 37:
                                                                    imageView_robot1_show1.setImageResource(R.drawable.c25);
                                                                    break;
                                                                case 38:
                                                                    imageView_robot1_show1.setImageResource(R.drawable.c26);
                                                                    break;
                                                                case 39:
                                                                    imageView_robot1_show1.setImageResource(R.drawable.c1);
                                                                    break;
                                                                case 40:
                                                                    imageView_robot1_show1.setImageResource(R.drawable.c2);
                                                                    break;
                                                                case 41:
                                                                    imageView_robot1_show1.setImageResource(R.drawable.c3);
                                                                    break;
                                                                case 42:
                                                                    imageView_robot1_show1.setImageResource(R.drawable.c4);
                                                                    break;
                                                                case 43:
                                                                    imageView_robot1_show1.setImageResource(R.drawable.c5);
                                                                    break;
                                                                case 44:
                                                                    imageView_robot1_show1.setImageResource(R.drawable.c6);
                                                                    break;
                                                                case 45:
                                                                    imageView_robot1_show1.setImageResource(R.drawable.c7);
                                                                    break;
                                                                case 46:
                                                                    imageView_robot1_show1.setImageResource(R.drawable.c8);
                                                                    break;
                                                                case 47:
                                                                    imageView_robot1_show1.setImageResource(R.drawable.c9);
                                                                    break;
                                                                case 48:
                                                                    imageView_robot1_show1.setImageResource(R.drawable.c10);
                                                                    break;
                                                                case 49:
                                                                    imageView_robot1_show1.setImageResource(R.drawable.c11);
                                                                    break;
                                                                case 50:
                                                                    imageView_robot1_show1.setImageResource(R.drawable.c12);
                                                                    break;
                                                                case 51:
                                                                    imageView_robot1_show1.setImageResource(R.drawable.c13);
                                                                    break;
                                                                default:
                                                                    break;
                                                            }
                                                            break;
                                                        case 1:
                                                            switch (RobotCardID[0 ][k]) {
                                                                case 0:
                                                                    imageView_robot1_show2.setImageResource(R.drawable.c40);
                                                                    break;
                                                                case 1:
                                                                    imageView_robot1_show2.setImageResource(R.drawable.c41);
                                                                    break;
                                                                case 2:
                                                                    imageView_robot1_show2.setImageResource(R.drawable.c42);
                                                                    break;
                                                                case 3:
                                                                    imageView_robot1_show2.setImageResource(R.drawable.c43);
                                                                    break;
                                                                case 4:
                                                                    imageView_robot1_show2.setImageResource(R.drawable.c44);
                                                                    break;
                                                                case 5:
                                                                    imageView_robot1_show2.setImageResource(R.drawable.c45);
                                                                    break;
                                                                case 6:
                                                                    imageView_robot1_show2.setImageResource(R.drawable.c46);
                                                                    break;
                                                                case 7:
                                                                    imageView_robot1_show2.setImageResource(R.drawable.c47);
                                                                    break;
                                                                case 8:
                                                                    imageView_robot1_show2.setImageResource(R.drawable.c48);
                                                                    break;
                                                                case 9:
                                                                    imageView_robot1_show2.setImageResource(R.drawable.c49);
                                                                    break;
                                                                case 10:
                                                                    imageView_robot1_show2.setImageResource(R.drawable.c50);
                                                                    break;
                                                                case 11:
                                                                    imageView_robot1_show2.setImageResource(R.drawable.c51);
                                                                    break;
                                                                case 12:
                                                                    imageView_robot1_show2.setImageResource(R.drawable.c52);
                                                                    break;
                                                                case 13:
                                                                    imageView_robot1_show2.setImageResource(R.drawable.c27);
                                                                    break;
                                                                case 14:
                                                                    imageView_robot1_show2.setImageResource(R.drawable.c28);
                                                                    break;
                                                                case 15:
                                                                    imageView_robot1_show2.setImageResource(R.drawable.c29);
                                                                    break;
                                                                case 16:
                                                                    imageView_robot1_show2.setImageResource(R.drawable.c30);
                                                                    break;
                                                                case 17:
                                                                    imageView_robot1_show2.setImageResource(R.drawable.c31);
                                                                    break;
                                                                case 18:
                                                                    imageView_robot1_show2.setImageResource(R.drawable.c32);
                                                                    break;
                                                                case 19:
                                                                    imageView_robot1_show2.setImageResource(R.drawable.c33);
                                                                    break;
                                                                case 20:
                                                                    imageView_robot1_show2.setImageResource(R.drawable.c34);
                                                                    break;
                                                                case 21:
                                                                    imageView_robot1_show2.setImageResource(R.drawable.c35);
                                                                    break;
                                                                case 22:
                                                                    imageView_robot1_show2.setImageResource(R.drawable.c36);
                                                                    break;
                                                                case 23:
                                                                    imageView_robot1_show2.setImageResource(R.drawable.c37);
                                                                    break;
                                                                case 24:
                                                                    imageView_robot1_show2.setImageResource(R.drawable.c38);
                                                                    break;
                                                                case 25:
                                                                    imageView_robot1_show2.setImageResource(R.drawable.c39);
                                                                    break;
                                                                case 26:
                                                                    imageView_robot1_show2.setImageResource(R.drawable.c14);
                                                                    break;
                                                                case 27:
                                                                    imageView_robot1_show2.setImageResource(R.drawable.c15);
                                                                    break;
                                                                case 28:
                                                                    imageView_robot1_show2.setImageResource(R.drawable.c16);
                                                                    break;
                                                                case 29:
                                                                    imageView_robot1_show2.setImageResource(R.drawable.c17);
                                                                    break;
                                                                case 30:
                                                                    imageView_robot1_show2.setImageResource(R.drawable.c18);
                                                                    break;
                                                                case 31:
                                                                    imageView_robot1_show2.setImageResource(R.drawable.c19);
                                                                    break;
                                                                case 32:
                                                                    imageView_robot1_show2.setImageResource(R.drawable.c20);
                                                                    break;
                                                                case 33:
                                                                    imageView_robot1_show2.setImageResource(R.drawable.c21);
                                                                    break;
                                                                case 34:
                                                                    imageView_robot1_show2.setImageResource(R.drawable.c22);
                                                                    break;
                                                                case 35:
                                                                    imageView_robot1_show2.setImageResource(R.drawable.c23);
                                                                    break;
                                                                case 36:
                                                                    imageView_robot1_show2.setImageResource(R.drawable.c24);
                                                                    break;
                                                                case 37:
                                                                    imageView_robot1_show2.setImageResource(R.drawable.c25);
                                                                    break;
                                                                case 38:
                                                                    imageView_robot1_show2.setImageResource(R.drawable.c26);
                                                                    break;
                                                                case 39:
                                                                    imageView_robot1_show2.setImageResource(R.drawable.c1);
                                                                    break;
                                                                case 40:
                                                                    imageView_robot1_show2.setImageResource(R.drawable.c2);
                                                                    break;
                                                                case 41:
                                                                    imageView_robot1_show2.setImageResource(R.drawable.c3);
                                                                    break;
                                                                case 42:
                                                                    imageView_robot1_show2.setImageResource(R.drawable.c4);
                                                                    break;
                                                                case 43:
                                                                    imageView_robot1_show2.setImageResource(R.drawable.c5);
                                                                    break;
                                                                case 44:
                                                                    imageView_robot1_show2.setImageResource(R.drawable.c6);
                                                                    break;
                                                                case 45:
                                                                    imageView_robot1_show2.setImageResource(R.drawable.c7);
                                                                    break;
                                                                case 46:
                                                                    imageView_robot1_show2.setImageResource(R.drawable.c8);
                                                                    break;
                                                                case 47:
                                                                    imageView_robot1_show2.setImageResource(R.drawable.c9);
                                                                    break;
                                                                case 48:
                                                                    imageView_robot1_show2.setImageResource(R.drawable.c10);
                                                                    break;
                                                                case 49:
                                                                    imageView_robot1_show2.setImageResource(R.drawable.c11);
                                                                    break;
                                                                case 50:
                                                                    imageView_robot1_show2.setImageResource(R.drawable.c12);
                                                                    break;
                                                                case 51:
                                                                    imageView_robot1_show2.setImageResource(R.drawable.c13);
                                                                    break;
                                                                default:
                                                                    break;
                                                            }
                                                            break;
                                                        case 2:
                                                            switch (RobotCardID[0][k]) {
                                                                case 0:
                                                                    imageView_robot1_show3.setImageResource(R.drawable.c40);
                                                                    break;
                                                                case 1:
                                                                    imageView_robot1_show3.setImageResource(R.drawable.c41);
                                                                    break;
                                                                case 2:
                                                                    imageView_robot1_show3.setImageResource(R.drawable.c42);
                                                                    break;
                                                                case 3:
                                                                    imageView_robot1_show3.setImageResource(R.drawable.c43);
                                                                    break;
                                                                case 4:
                                                                    imageView_robot1_show3.setImageResource(R.drawable.c44);
                                                                    break;
                                                                case 5:
                                                                    imageView_robot1_show3.setImageResource(R.drawable.c45);
                                                                    break;
                                                                case 6:
                                                                    imageView_robot1_show3.setImageResource(R.drawable.c46);
                                                                    break;
                                                                case 7:
                                                                    imageView_robot1_show3.setImageResource(R.drawable.c47);
                                                                    break;
                                                                case 8:
                                                                    imageView_robot1_show3.setImageResource(R.drawable.c48);
                                                                    break;
                                                                case 9:
                                                                    imageView_robot1_show3.setImageResource(R.drawable.c49);
                                                                    break;
                                                                case 10:
                                                                    imageView_robot1_show3.setImageResource(R.drawable.c50);
                                                                    break;
                                                                case 11:
                                                                    imageView_robot1_show3.setImageResource(R.drawable.c51);
                                                                    break;
                                                                case 12:
                                                                    imageView_robot1_show3.setImageResource(R.drawable.c52);
                                                                    break;
                                                                case 13:
                                                                    imageView_robot1_show3.setImageResource(R.drawable.c27);
                                                                    break;
                                                                case 14:
                                                                    imageView_robot1_show3.setImageResource(R.drawable.c28);
                                                                    break;
                                                                case 15:
                                                                    imageView_robot1_show3.setImageResource(R.drawable.c29);
                                                                    break;
                                                                case 16:
                                                                    imageView_robot1_show3.setImageResource(R.drawable.c30);
                                                                    break;
                                                                case 17:
                                                                    imageView_robot1_show3.setImageResource(R.drawable.c31);
                                                                    break;
                                                                case 18:
                                                                    imageView_robot1_show3.setImageResource(R.drawable.c32);
                                                                    break;
                                                                case 19:
                                                                    imageView_robot1_show3.setImageResource(R.drawable.c33);
                                                                    break;
                                                                case 20:
                                                                    imageView_robot1_show3.setImageResource(R.drawable.c34);
                                                                    break;
                                                                case 21:
                                                                    imageView_robot1_show3.setImageResource(R.drawable.c35);
                                                                    break;
                                                                case 22:
                                                                    imageView_robot1_show3.setImageResource(R.drawable.c36);
                                                                    break;
                                                                case 23:
                                                                    imageView_robot1_show3.setImageResource(R.drawable.c37);
                                                                    break;
                                                                case 24:
                                                                    imageView_robot1_show3.setImageResource(R.drawable.c38);
                                                                    break;
                                                                case 25:
                                                                    imageView_robot1_show3.setImageResource(R.drawable.c39);
                                                                    break;
                                                                case 26:
                                                                    imageView_robot1_show3.setImageResource(R.drawable.c14);
                                                                    break;
                                                                case 27:
                                                                    imageView_robot1_show3.setImageResource(R.drawable.c15);
                                                                    break;
                                                                case 28:
                                                                    imageView_robot1_show3.setImageResource(R.drawable.c16);
                                                                    break;
                                                                case 29:
                                                                    imageView_robot1_show3.setImageResource(R.drawable.c17);
                                                                    break;
                                                                case 30:
                                                                    imageView_robot1_show3.setImageResource(R.drawable.c18);
                                                                    break;
                                                                case 31:
                                                                    imageView_robot1_show3.setImageResource(R.drawable.c19);
                                                                    break;
                                                                case 32:
                                                                    imageView_robot1_show3.setImageResource(R.drawable.c20);
                                                                    break;
                                                                case 33:
                                                                    imageView_robot1_show3.setImageResource(R.drawable.c21);
                                                                    break;
                                                                case 34:
                                                                    imageView_robot1_show3.setImageResource(R.drawable.c22);
                                                                    break;
                                                                case 35:
                                                                    imageView_robot1_show3.setImageResource(R.drawable.c23);
                                                                    break;
                                                                case 36:
                                                                    imageView_robot1_show3.setImageResource(R.drawable.c24);
                                                                    break;
                                                                case 37:
                                                                    imageView_robot1_show3.setImageResource(R.drawable.c25);
                                                                    break;
                                                                case 38:
                                                                    imageView_robot1_show3.setImageResource(R.drawable.c26);
                                                                    break;
                                                                case 39:
                                                                    imageView_robot1_show3.setImageResource(R.drawable.c1);
                                                                    break;
                                                                case 40:
                                                                    imageView_robot1_show3.setImageResource(R.drawable.c2);
                                                                    break;
                                                                case 41:
                                                                    imageView_robot1_show3.setImageResource(R.drawable.c3);
                                                                    break;
                                                                case 42:
                                                                    imageView_robot1_show3.setImageResource(R.drawable.c4);
                                                                    break;
                                                                case 43:
                                                                    imageView_robot1_show3.setImageResource(R.drawable.c5);
                                                                    break;
                                                                case 44:
                                                                    imageView_robot1_show3.setImageResource(R.drawable.c6);
                                                                    break;
                                                                case 45:
                                                                    imageView_robot1_show3.setImageResource(R.drawable.c7);
                                                                    break;
                                                                case 46:
                                                                    imageView_robot1_show3.setImageResource(R.drawable.c8);
                                                                    break;
                                                                case 47:
                                                                    imageView_robot1_show3.setImageResource(R.drawable.c9);
                                                                    break;
                                                                case 48:
                                                                    imageView_robot1_show3.setImageResource(R.drawable.c10);
                                                                    break;
                                                                case 49:
                                                                    imageView_robot1_show3.setImageResource(R.drawable.c11);
                                                                    break;
                                                                case 50:
                                                                    imageView_robot1_show3.setImageResource(R.drawable.c12);
                                                                    break;
                                                                case 51:
                                                                    imageView_robot1_show3.setImageResource(R.drawable.c13);
                                                                    break;
                                                                default:
                                                                    break;
                                                            }
                                                            break;
                                                        case 3:
                                                            switch (RobotCardID[0][k]) {
                                                                case 0:
                                                                    imageView_robot1_show4.setImageResource(R.drawable.c40);
                                                                    break;
                                                                case 1:
                                                                    imageView_robot1_show4.setImageResource(R.drawable.c41);
                                                                    break;
                                                                case 2:
                                                                    imageView_robot1_show4.setImageResource(R.drawable.c42);
                                                                    break;
                                                                case 3:
                                                                    imageView_robot1_show4.setImageResource(R.drawable.c43);
                                                                    break;
                                                                case 4:
                                                                    imageView_robot1_show4.setImageResource(R.drawable.c44);
                                                                    break;
                                                                case 5:
                                                                    imageView_robot1_show4.setImageResource(R.drawable.c45);
                                                                    break;
                                                                case 6:
                                                                    imageView_robot1_show4.setImageResource(R.drawable.c46);
                                                                    break;
                                                                case 7:
                                                                    imageView_robot1_show4.setImageResource(R.drawable.c47);
                                                                    break;
                                                                case 8:
                                                                    imageView_robot1_show4.setImageResource(R.drawable.c48);
                                                                    break;
                                                                case 9:
                                                                    imageView_robot1_show4.setImageResource(R.drawable.c49);
                                                                    break;
                                                                case 10:
                                                                    imageView_robot1_show4.setImageResource(R.drawable.c50);
                                                                    break;
                                                                case 11:
                                                                    imageView_robot1_show4.setImageResource(R.drawable.c51);
                                                                    break;
                                                                case 12:
                                                                    imageView_robot1_show4.setImageResource(R.drawable.c52);
                                                                    break;
                                                                case 13:
                                                                    imageView_robot1_show4.setImageResource(R.drawable.c27);
                                                                    break;
                                                                case 14:
                                                                    imageView_robot1_show4.setImageResource(R.drawable.c28);
                                                                    break;
                                                                case 15:
                                                                    imageView_robot1_show4.setImageResource(R.drawable.c29);
                                                                    break;
                                                                case 16:
                                                                    imageView_robot1_show4.setImageResource(R.drawable.c30);
                                                                    break;
                                                                case 17:
                                                                    imageView_robot1_show4.setImageResource(R.drawable.c31);
                                                                    break;
                                                                case 18:
                                                                    imageView_robot1_show4.setImageResource(R.drawable.c32);
                                                                    break;
                                                                case 19:
                                                                    imageView_robot1_show4.setImageResource(R.drawable.c33);
                                                                    break;
                                                                case 20:
                                                                    imageView_robot1_show4.setImageResource(R.drawable.c34);
                                                                    break;
                                                                case 21:
                                                                    imageView_robot1_show4.setImageResource(R.drawable.c35);
                                                                    break;
                                                                case 22:
                                                                    imageView_robot1_show4.setImageResource(R.drawable.c36);
                                                                    break;
                                                                case 23:
                                                                    imageView_robot1_show4.setImageResource(R.drawable.c37);
                                                                    break;
                                                                case 24:
                                                                    imageView_robot1_show4.setImageResource(R.drawable.c38);
                                                                    break;
                                                                case 25:
                                                                    imageView_robot1_show4.setImageResource(R.drawable.c39);
                                                                    break;
                                                                case 26:
                                                                    imageView_robot1_show4.setImageResource(R.drawable.c14);
                                                                    break;
                                                                case 27:
                                                                    imageView_robot1_show4.setImageResource(R.drawable.c15);
                                                                    break;
                                                                case 28:
                                                                    imageView_robot1_show4.setImageResource(R.drawable.c16);
                                                                    break;
                                                                case 29:
                                                                    imageView_robot1_show4.setImageResource(R.drawable.c17);
                                                                    break;
                                                                case 30:
                                                                    imageView_robot1_show4.setImageResource(R.drawable.c18);
                                                                    break;
                                                                case 31:
                                                                    imageView_robot1_show4.setImageResource(R.drawable.c19);
                                                                    break;
                                                                case 32:
                                                                    imageView_robot1_show4.setImageResource(R.drawable.c20);
                                                                    break;
                                                                case 33:
                                                                    imageView_robot1_show4.setImageResource(R.drawable.c21);
                                                                    break;
                                                                case 34:
                                                                    imageView_robot1_show4.setImageResource(R.drawable.c22);
                                                                    break;
                                                                case 35:
                                                                    imageView_robot1_show4.setImageResource(R.drawable.c23);
                                                                    break;
                                                                case 36:
                                                                    imageView_robot1_show4.setImageResource(R.drawable.c24);
                                                                    break;
                                                                case 37:
                                                                    imageView_robot1_show4.setImageResource(R.drawable.c25);
                                                                    break;
                                                                case 38:
                                                                    imageView_robot1_show4.setImageResource(R.drawable.c26);
                                                                    break;
                                                                case 39:
                                                                    imageView_robot1_show4.setImageResource(R.drawable.c1);
                                                                    break;
                                                                case 40:
                                                                    imageView_robot1_show4.setImageResource(R.drawable.c2);
                                                                    break;
                                                                case 41:
                                                                    imageView_robot1_show4.setImageResource(R.drawable.c3);
                                                                    break;
                                                                case 42:
                                                                    imageView_robot1_show4.setImageResource(R.drawable.c4);
                                                                    break;
                                                                case 43:
                                                                    imageView_robot1_show4.setImageResource(R.drawable.c5);
                                                                    break;
                                                                case 44:
                                                                    imageView_robot1_show4.setImageResource(R.drawable.c6);
                                                                    break;
                                                                case 45:
                                                                    imageView_robot1_show4.setImageResource(R.drawable.c7);
                                                                    break;
                                                                case 46:
                                                                    imageView_robot1_show4.setImageResource(R.drawable.c8);
                                                                    break;
                                                                case 47:
                                                                    imageView_robot1_show4.setImageResource(R.drawable.c9);
                                                                    break;
                                                                case 48:
                                                                    imageView_robot1_show4.setImageResource(R.drawable.c10);
                                                                    break;
                                                                case 49:
                                                                    imageView_robot1_show4.setImageResource(R.drawable.c11);
                                                                    break;
                                                                case 50:
                                                                    imageView_robot1_show4.setImageResource(R.drawable.c12);
                                                                    break;
                                                                case 51:
                                                                    imageView_robot1_show4.setImageResource(R.drawable.c13);
                                                                    break;
                                                                default:
                                                                    break;
                                                            }
                                                            break;
                                                        case 4:
                                                            switch (RobotCardID[0][k]) {
                                                                case 0:
                                                                    imageView_robot1_show5.setImageResource(R.drawable.c40);
                                                                    break;
                                                                case 1:
                                                                    imageView_robot1_show5.setImageResource(R.drawable.c41);
                                                                    break;
                                                                case 2:
                                                                    imageView_robot1_show5.setImageResource(R.drawable.c42);
                                                                    break;
                                                                case 3:
                                                                    imageView_robot1_show5.setImageResource(R.drawable.c43);
                                                                    break;
                                                                case 4:
                                                                    imageView_robot1_show5.setImageResource(R.drawable.c44);
                                                                    break;
                                                                case 5:
                                                                    imageView_robot1_show5.setImageResource(R.drawable.c45);
                                                                    break;
                                                                case 6:
                                                                    imageView_robot1_show5.setImageResource(R.drawable.c46);
                                                                    break;
                                                                case 7:
                                                                    imageView_robot1_show5.setImageResource(R.drawable.c47);
                                                                    break;
                                                                case 8:
                                                                    imageView_robot1_show5.setImageResource(R.drawable.c48);
                                                                    break;
                                                                case 9:
                                                                    imageView_robot1_show5.setImageResource(R.drawable.c49);
                                                                    break;
                                                                case 10:
                                                                    imageView_robot1_show5.setImageResource(R.drawable.c50);
                                                                    break;
                                                                case 11:
                                                                    imageView_robot1_show5.setImageResource(R.drawable.c51);
                                                                    break;
                                                                case 12:
                                                                    imageView_robot1_show5.setImageResource(R.drawable.c52);
                                                                    break;
                                                                case 13:
                                                                    imageView_robot1_show5.setImageResource(R.drawable.c27);
                                                                    break;
                                                                case 14:
                                                                    imageView_robot1_show5.setImageResource(R.drawable.c28);
                                                                    break;
                                                                case 15:
                                                                    imageView_robot1_show5.setImageResource(R.drawable.c29);
                                                                    break;
                                                                case 16:
                                                                    imageView_robot1_show5.setImageResource(R.drawable.c30);
                                                                    break;
                                                                case 17:
                                                                    imageView_robot1_show5.setImageResource(R.drawable.c31);
                                                                    break;
                                                                case 18:
                                                                    imageView_robot1_show5.setImageResource(R.drawable.c32);
                                                                    break;
                                                                case 19:
                                                                    imageView_robot1_show5.setImageResource(R.drawable.c33);
                                                                    break;
                                                                case 20:
                                                                    imageView_robot1_show5.setImageResource(R.drawable.c34);
                                                                    break;
                                                                case 21:
                                                                    imageView_robot1_show5.setImageResource(R.drawable.c35);
                                                                    break;
                                                                case 22:
                                                                    imageView_robot1_show5.setImageResource(R.drawable.c36);
                                                                    break;
                                                                case 23:
                                                                    imageView_robot1_show5.setImageResource(R.drawable.c37);
                                                                    break;
                                                                case 24:
                                                                    imageView_robot1_show5.setImageResource(R.drawable.c38);
                                                                    break;
                                                                case 25:
                                                                    imageView_robot1_show5.setImageResource(R.drawable.c39);
                                                                    break;
                                                                case 26:
                                                                    imageView_robot1_show5.setImageResource(R.drawable.c14);
                                                                    break;
                                                                case 27:
                                                                    imageView_robot1_show5.setImageResource(R.drawable.c15);
                                                                    break;
                                                                case 28:
                                                                    imageView_robot1_show5.setImageResource(R.drawable.c16);
                                                                    break;
                                                                case 29:
                                                                    imageView_robot1_show5.setImageResource(R.drawable.c17);
                                                                    break;
                                                                case 30:
                                                                    imageView_robot1_show5.setImageResource(R.drawable.c18);
                                                                    break;
                                                                case 31:
                                                                    imageView_robot1_show5.setImageResource(R.drawable.c19);
                                                                    break;
                                                                case 32:
                                                                    imageView_robot1_show5.setImageResource(R.drawable.c20);
                                                                    break;
                                                                case 33:
                                                                    imageView_robot1_show5.setImageResource(R.drawable.c21);
                                                                    break;
                                                                case 34:
                                                                    imageView_robot1_show5.setImageResource(R.drawable.c22);
                                                                    break;
                                                                case 35:
                                                                    imageView_robot1_show5.setImageResource(R.drawable.c23);
                                                                    break;
                                                                case 36:
                                                                    imageView_robot1_show5.setImageResource(R.drawable.c24);
                                                                    break;
                                                                case 37:
                                                                    imageView_robot1_show5.setImageResource(R.drawable.c25);
                                                                    break;
                                                                case 38:
                                                                    imageView_robot1_show5.setImageResource(R.drawable.c26);
                                                                    break;
                                                                case 39:
                                                                    imageView_robot1_show5.setImageResource(R.drawable.c1);
                                                                    break;
                                                                case 40:
                                                                    imageView_robot1_show5.setImageResource(R.drawable.c2);
                                                                    break;
                                                                case 41:
                                                                    imageView_robot1_show5.setImageResource(R.drawable.c3);
                                                                    break;
                                                                case 42:
                                                                    imageView_robot1_show5.setImageResource(R.drawable.c4);
                                                                    break;
                                                                case 43:
                                                                    imageView_robot1_show5.setImageResource(R.drawable.c5);
                                                                    break;
                                                                case 44:
                                                                    imageView_robot1_show5.setImageResource(R.drawable.c6);
                                                                    break;
                                                                case 45:
                                                                    imageView_robot1_show5.setImageResource(R.drawable.c7);
                                                                    break;
                                                                case 46:
                                                                    imageView_robot1_show5.setImageResource(R.drawable.c8);
                                                                    break;
                                                                case 47:
                                                                    imageView_robot1_show5.setImageResource(R.drawable.c9);
                                                                    break;
                                                                case 48:
                                                                    imageView_robot1_show5.setImageResource(R.drawable.c10);
                                                                    break;
                                                                case 49:
                                                                    imageView_robot1_show5.setImageResource(R.drawable.c11);
                                                                    break;
                                                                case 50:
                                                                    imageView_robot1_show5.setImageResource(R.drawable.c12);
                                                                    break;
                                                                case 51:
                                                                    imageView_robot1_show5.setImageResource(R.drawable.c13);
                                                                    break;
                                                                default:
                                                                    break;
                                                            }
                                                        default:
                                                            break;
                                                    }
                                                }
                                            }
                                        }
                                        if(temp == 0){

                                        }

                                        break;
                                    case 2:
                                        Log.d("s","收到二号机器人出牌消息");
                                        imageView_robot1_pass.setImageDrawable(null);
                                        imageView_robot2_show1.setImageDrawable(null);
                                        imageView_robot2_show2.setImageDrawable(null);
                                        imageView_robot2_show3.setImageDrawable(null);
                                        imageView_robot2_show4.setImageDrawable(null);
                                        imageView_robot2_show5.setImageDrawable(null);
                                        temp = Ruler.getCardsShowedByPreviousPlayer().size();
                                        temp = Integer.valueOf(CardNumber2.getText().toString()) - temp;
                                        CardNumber2.setText(String.valueOf(temp));
                                        for (int t = 0; t < Ruler.getCardsShowedByPreviousPlayer().size(); t++) {
                                            for (int k = 0; k < cardID.length; k++) {
                                                if (Ruler.getCardsShowedByPreviousPlayer().get(t).getCardId() == RobotCardID[1][k]) {
                                                    switch (t) {
                                                        case 0:
                                                            switch (RobotCardID[1][k]) {
                                                                case 0:
                                                                    imageView_robot2_show1.setImageResource(R.drawable.c40);
                                                                    break;
                                                                case 1:
                                                                    imageView_robot2_show1.setImageResource(R.drawable.c41);
                                                                    break;
                                                                case 2:
                                                                    imageView_robot2_show1.setImageResource(R.drawable.c42);
                                                                    break;
                                                                case 3:
                                                                    imageView_robot2_show1.setImageResource(R.drawable.c43);
                                                                    break;
                                                                case 4:
                                                                    imageView_robot2_show1.setImageResource(R.drawable.c44);
                                                                    break;
                                                                case 5:
                                                                    imageView_robot2_show1.setImageResource(R.drawable.c45);
                                                                    break;
                                                                case 6:
                                                                    imageView_robot2_show1.setImageResource(R.drawable.c46);
                                                                    break;
                                                                case 7:
                                                                    imageView_robot2_show1.setImageResource(R.drawable.c47);
                                                                    break;
                                                                case 8:
                                                                    imageView_robot2_show1.setImageResource(R.drawable.c48);
                                                                    break;
                                                                case 9:
                                                                    imageView_robot2_show1.setImageResource(R.drawable.c49);
                                                                    break;
                                                                case 10:
                                                                    imageView_robot2_show1.setImageResource(R.drawable.c50);
                                                                    break;
                                                                case 11:
                                                                    imageView_robot2_show1.setImageResource(R.drawable.c51);
                                                                    break;
                                                                case 12:
                                                                    imageView_robot2_show1.setImageResource(R.drawable.c52);
                                                                    break;
                                                                case 13:
                                                                    imageView_robot2_show1.setImageResource(R.drawable.c27);
                                                                    break;
                                                                case 14:
                                                                    imageView_robot2_show1.setImageResource(R.drawable.c28);
                                                                    break;
                                                                case 15:
                                                                    imageView_robot2_show1.setImageResource(R.drawable.c29);
                                                                    break;
                                                                case 16:
                                                                    imageView_robot2_show1.setImageResource(R.drawable.c30);
                                                                    break;
                                                                case 17:
                                                                    imageView_robot2_show1.setImageResource(R.drawable.c31);
                                                                    break;
                                                                case 18:
                                                                    imageView_robot2_show1.setImageResource(R.drawable.c32);
                                                                    break;
                                                                case 19:
                                                                    imageView_robot2_show1.setImageResource(R.drawable.c33);
                                                                    break;
                                                                case 20:
                                                                    imageView_robot2_show1.setImageResource(R.drawable.c34);
                                                                    break;
                                                                case 21:
                                                                    imageView_robot2_show1.setImageResource(R.drawable.c35);
                                                                    break;
                                                                case 22:
                                                                    imageView_robot2_show1.setImageResource(R.drawable.c36);
                                                                    break;
                                                                case 23:
                                                                    imageView_robot2_show1.setImageResource(R.drawable.c37);
                                                                    break;
                                                                case 24:
                                                                    imageView_robot2_show1.setImageResource(R.drawable.c38);
                                                                    break;
                                                                case 25:
                                                                    imageView_robot2_show1.setImageResource(R.drawable.c39);
                                                                    break;
                                                                case 26:
                                                                    imageView_robot2_show1.setImageResource(R.drawable.c14);
                                                                    break;
                                                                case 27:
                                                                    imageView_robot2_show1.setImageResource(R.drawable.c15);
                                                                    break;
                                                                case 28:
                                                                    imageView_robot2_show1.setImageResource(R.drawable.c16);
                                                                    break;
                                                                case 29:
                                                                    imageView_robot2_show1.setImageResource(R.drawable.c17);
                                                                    break;
                                                                case 30:
                                                                    imageView_robot2_show1.setImageResource(R.drawable.c18);
                                                                    break;
                                                                case 31:
                                                                    imageView_robot2_show1.setImageResource(R.drawable.c19);
                                                                    break;
                                                                case 32:
                                                                    imageView_robot2_show1.setImageResource(R.drawable.c20);
                                                                    break;
                                                                case 33:
                                                                    imageView_robot2_show1.setImageResource(R.drawable.c21);
                                                                    break;
                                                                case 34:
                                                                    imageView_robot2_show1.setImageResource(R.drawable.c22);
                                                                    break;
                                                                case 35:
                                                                    imageView_robot2_show1.setImageResource(R.drawable.c23);
                                                                    break;
                                                                case 36:
                                                                    imageView_robot2_show1.setImageResource(R.drawable.c24);
                                                                    break;
                                                                case 37:
                                                                    imageView_robot2_show1.setImageResource(R.drawable.c25);
                                                                    break;
                                                                case 38:
                                                                    imageView_robot2_show1.setImageResource(R.drawable.c26);
                                                                    break;
                                                                case 39:
                                                                    imageView_robot2_show1.setImageResource(R.drawable.c1);
                                                                    break;
                                                                case 40:
                                                                    imageView_robot2_show1.setImageResource(R.drawable.c2);
                                                                    break;
                                                                case 41:
                                                                    imageView_robot2_show1.setImageResource(R.drawable.c3);
                                                                    break;
                                                                case 42:
                                                                    imageView_robot2_show1.setImageResource(R.drawable.c4);
                                                                    break;
                                                                case 43:
                                                                    imageView_robot2_show1.setImageResource(R.drawable.c5);
                                                                    break;
                                                                case 44:
                                                                    imageView_robot2_show1.setImageResource(R.drawable.c6);
                                                                    break;
                                                                case 45:
                                                                    imageView_robot2_show1.setImageResource(R.drawable.c7);
                                                                    break;
                                                                case 46:
                                                                    imageView_robot2_show1.setImageResource(R.drawable.c8);
                                                                    break;
                                                                case 47:
                                                                    imageView_robot2_show1.setImageResource(R.drawable.c9);
                                                                    break;
                                                                case 48:
                                                                    imageView_robot2_show1.setImageResource(R.drawable.c10);
                                                                    break;
                                                                case 49:
                                                                    imageView_robot2_show1.setImageResource(R.drawable.c11);
                                                                    break;
                                                                case 50:
                                                                    imageView_robot2_show1.setImageResource(R.drawable.c12);
                                                                    break;
                                                                case 51:
                                                                    imageView_robot2_show1.setImageResource(R.drawable.c13);
                                                                    break;
                                                                default:
                                                                    break;
                                                            }
                                                            break;
                                                        case 1:
                                                            switch (RobotCardID[1][k]) {
                                                                case 0:
                                                                    imageView_robot2_show2.setImageResource(R.drawable.c40);
                                                                    break;
                                                                case 1:
                                                                    imageView_robot2_show2.setImageResource(R.drawable.c41);
                                                                    break;
                                                                case 2:
                                                                    imageView_robot2_show2.setImageResource(R.drawable.c42);
                                                                    break;
                                                                case 3:
                                                                    imageView_robot2_show2.setImageResource(R.drawable.c43);
                                                                    break;
                                                                case 4:
                                                                    imageView_robot2_show2.setImageResource(R.drawable.c44);
                                                                    break;
                                                                case 5:
                                                                    imageView_robot2_show2.setImageResource(R.drawable.c45);
                                                                    break;
                                                                case 6:
                                                                    imageView_robot2_show2.setImageResource(R.drawable.c46);
                                                                    break;
                                                                case 7:
                                                                    imageView_robot2_show2.setImageResource(R.drawable.c47);
                                                                    break;
                                                                case 8:
                                                                    imageView_robot2_show2.setImageResource(R.drawable.c48);
                                                                    break;
                                                                case 9:
                                                                    imageView_robot2_show2.setImageResource(R.drawable.c49);
                                                                    break;
                                                                case 10:
                                                                    imageView_robot2_show2.setImageResource(R.drawable.c50);
                                                                    break;
                                                                case 11:
                                                                    imageView_robot2_show2.setImageResource(R.drawable.c51);
                                                                    break;
                                                                case 12:
                                                                    imageView_robot2_show2.setImageResource(R.drawable.c52);
                                                                    break;
                                                                case 13:
                                                                    imageView_robot2_show2.setImageResource(R.drawable.c27);
                                                                    break;
                                                                case 14:
                                                                    imageView_robot2_show2.setImageResource(R.drawable.c28);
                                                                    break;
                                                                case 15:
                                                                    imageView_robot2_show2.setImageResource(R.drawable.c29);
                                                                    break;
                                                                case 16:
                                                                    imageView_robot2_show2.setImageResource(R.drawable.c30);
                                                                    break;
                                                                case 17:
                                                                    imageView_robot2_show2.setImageResource(R.drawable.c31);
                                                                    break;
                                                                case 18:
                                                                    imageView_robot2_show2.setImageResource(R.drawable.c32);
                                                                    break;
                                                                case 19:
                                                                    imageView_robot2_show2.setImageResource(R.drawable.c33);
                                                                    break;
                                                                case 20:
                                                                    imageView_robot2_show2.setImageResource(R.drawable.c34);
                                                                    break;
                                                                case 21:
                                                                    imageView_robot2_show2.setImageResource(R.drawable.c35);
                                                                    break;
                                                                case 22:
                                                                    imageView_robot2_show2.setImageResource(R.drawable.c36);
                                                                    break;
                                                                case 23:
                                                                    imageView_robot2_show2.setImageResource(R.drawable.c37);
                                                                    break;
                                                                case 24:
                                                                    imageView_robot2_show2.setImageResource(R.drawable.c38);
                                                                    break;
                                                                case 25:
                                                                    imageView_robot2_show2.setImageResource(R.drawable.c39);
                                                                    break;
                                                                case 26:
                                                                    imageView_robot2_show2.setImageResource(R.drawable.c14);
                                                                    break;
                                                                case 27:
                                                                    imageView_robot2_show2.setImageResource(R.drawable.c15);
                                                                    break;
                                                                case 28:
                                                                    imageView_robot2_show2.setImageResource(R.drawable.c16);
                                                                    break;
                                                                case 29:
                                                                    imageView_robot2_show2.setImageResource(R.drawable.c17);
                                                                    break;
                                                                case 30:
                                                                    imageView_robot2_show2.setImageResource(R.drawable.c18);
                                                                    break;
                                                                case 31:
                                                                    imageView_robot2_show2.setImageResource(R.drawable.c19);
                                                                    break;
                                                                case 32:
                                                                    imageView_robot2_show2.setImageResource(R.drawable.c20);
                                                                    break;
                                                                case 33:
                                                                    imageView_robot2_show2.setImageResource(R.drawable.c21);
                                                                    break;
                                                                case 34:
                                                                    imageView_robot2_show2.setImageResource(R.drawable.c22);
                                                                    break;
                                                                case 35:
                                                                    imageView_robot2_show2.setImageResource(R.drawable.c23);
                                                                    break;
                                                                case 36:
                                                                    imageView_robot2_show2.setImageResource(R.drawable.c24);
                                                                    break;
                                                                case 37:
                                                                    imageView_robot2_show2.setImageResource(R.drawable.c25);
                                                                    break;
                                                                case 38:
                                                                    imageView_robot2_show2.setImageResource(R.drawable.c26);
                                                                    break;
                                                                case 39:
                                                                    imageView_robot2_show2.setImageResource(R.drawable.c1);
                                                                    break;
                                                                case 40:
                                                                    imageView_robot2_show2.setImageResource(R.drawable.c2);
                                                                    break;
                                                                case 41:
                                                                    imageView_robot2_show2.setImageResource(R.drawable.c3);
                                                                    break;
                                                                case 42:
                                                                    imageView_robot2_show2.setImageResource(R.drawable.c4);
                                                                    break;
                                                                case 43:
                                                                    imageView_robot2_show2.setImageResource(R.drawable.c5);
                                                                    break;
                                                                case 44:
                                                                    imageView_robot2_show2.setImageResource(R.drawable.c6);
                                                                    break;
                                                                case 45:
                                                                    imageView_robot2_show2.setImageResource(R.drawable.c7);
                                                                    break;
                                                                case 46:
                                                                    imageView_robot2_show2.setImageResource(R.drawable.c8);
                                                                    break;
                                                                case 47:
                                                                    imageView_robot2_show2.setImageResource(R.drawable.c9);
                                                                    break;
                                                                case 48:
                                                                    imageView_robot2_show2.setImageResource(R.drawable.c10);
                                                                    break;
                                                                case 49:
                                                                    imageView_robot2_show2.setImageResource(R.drawable.c11);
                                                                    break;
                                                                case 50:
                                                                    imageView_robot2_show2.setImageResource(R.drawable.c12);
                                                                    break;
                                                                case 51:
                                                                    imageView_robot2_show2.setImageResource(R.drawable.c13);
                                                                    break;
                                                                default:
                                                                    break;
                                                            }
                                                            break;
                                                        case 2:
                                                            switch (RobotCardID[1][k]) {
                                                                case 0:
                                                                    imageView_robot2_show3.setImageResource(R.drawable.c40);
                                                                    break;
                                                                case 1:
                                                                    imageView_robot2_show3.setImageResource(R.drawable.c41);
                                                                    break;
                                                                case 2:
                                                                    imageView_robot2_show3.setImageResource(R.drawable.c42);
                                                                    break;
                                                                case 3:
                                                                    imageView_robot2_show3.setImageResource(R.drawable.c43);
                                                                    break;
                                                                case 4:
                                                                    imageView_robot2_show3.setImageResource(R.drawable.c44);
                                                                    break;
                                                                case 5:
                                                                    imageView_robot2_show3.setImageResource(R.drawable.c45);
                                                                    break;
                                                                case 6:
                                                                    imageView_robot2_show3.setImageResource(R.drawable.c46);
                                                                    break;
                                                                case 7:
                                                                    imageView_robot2_show3.setImageResource(R.drawable.c47);
                                                                    break;
                                                                case 8:
                                                                    imageView_robot2_show3.setImageResource(R.drawable.c48);
                                                                    break;
                                                                case 9:
                                                                    imageView_robot2_show3.setImageResource(R.drawable.c49);
                                                                    break;
                                                                case 10:
                                                                    imageView_robot2_show3.setImageResource(R.drawable.c50);
                                                                    break;
                                                                case 11:
                                                                    imageView_robot2_show3.setImageResource(R.drawable.c51);
                                                                    break;
                                                                case 12:
                                                                    imageView_robot2_show3.setImageResource(R.drawable.c52);
                                                                    break;
                                                                case 13:
                                                                    imageView_robot2_show3.setImageResource(R.drawable.c27);
                                                                    break;
                                                                case 14:
                                                                    imageView_robot2_show3.setImageResource(R.drawable.c28);
                                                                    break;
                                                                case 15:
                                                                    imageView_robot2_show3.setImageResource(R.drawable.c29);
                                                                    break;
                                                                case 16:
                                                                    imageView_robot2_show3.setImageResource(R.drawable.c30);
                                                                    break;
                                                                case 17:
                                                                    imageView_robot2_show3.setImageResource(R.drawable.c31);
                                                                    break;
                                                                case 18:
                                                                    imageView_robot2_show3.setImageResource(R.drawable.c32);
                                                                    break;
                                                                case 19:
                                                                    imageView_robot2_show3.setImageResource(R.drawable.c33);
                                                                    break;
                                                                case 20:
                                                                    imageView_robot2_show3.setImageResource(R.drawable.c34);
                                                                    break;
                                                                case 21:
                                                                    imageView_robot2_show3.setImageResource(R.drawable.c35);
                                                                    break;
                                                                case 22:
                                                                    imageView_robot2_show3.setImageResource(R.drawable.c36);
                                                                    break;
                                                                case 23:
                                                                    imageView_robot2_show3.setImageResource(R.drawable.c37);
                                                                    break;
                                                                case 24:
                                                                    imageView_robot2_show3.setImageResource(R.drawable.c38);
                                                                    break;
                                                                case 25:
                                                                    imageView_robot2_show3.setImageResource(R.drawable.c39);
                                                                    break;
                                                                case 26:
                                                                    imageView_robot2_show3.setImageResource(R.drawable.c14);
                                                                    break;
                                                                case 27:
                                                                    imageView_robot2_show3.setImageResource(R.drawable.c15);
                                                                    break;
                                                                case 28:
                                                                    imageView_robot2_show3.setImageResource(R.drawable.c16);
                                                                    break;
                                                                case 29:
                                                                    imageView_robot2_show3.setImageResource(R.drawable.c17);
                                                                    break;
                                                                case 30:
                                                                    imageView_robot2_show3.setImageResource(R.drawable.c18);
                                                                    break;
                                                                case 31:
                                                                    imageView_robot2_show3.setImageResource(R.drawable.c19);
                                                                    break;
                                                                case 32:
                                                                    imageView_robot2_show3.setImageResource(R.drawable.c20);
                                                                    break;
                                                                case 33:
                                                                    imageView_robot2_show3.setImageResource(R.drawable.c21);
                                                                    break;
                                                                case 34:
                                                                    imageView_robot2_show3.setImageResource(R.drawable.c22);
                                                                    break;
                                                                case 35:
                                                                    imageView_robot2_show3.setImageResource(R.drawable.c23);
                                                                    break;
                                                                case 36:
                                                                    imageView_robot2_show3.setImageResource(R.drawable.c24);
                                                                    break;
                                                                case 37:
                                                                    imageView_robot2_show3.setImageResource(R.drawable.c25);
                                                                    break;
                                                                case 38:
                                                                    imageView_robot2_show3.setImageResource(R.drawable.c26);
                                                                    break;
                                                                case 39:
                                                                    imageView_robot2_show3.setImageResource(R.drawable.c1);
                                                                    break;
                                                                case 40:
                                                                    imageView_robot2_show3.setImageResource(R.drawable.c2);
                                                                    break;
                                                                case 41:
                                                                    imageView_robot2_show3.setImageResource(R.drawable.c3);
                                                                    break;
                                                                case 42:
                                                                    imageView_robot2_show3.setImageResource(R.drawable.c4);
                                                                    break;
                                                                case 43:
                                                                    imageView_robot2_show3.setImageResource(R.drawable.c5);
                                                                    break;
                                                                case 44:
                                                                    imageView_robot2_show3.setImageResource(R.drawable.c6);
                                                                    break;
                                                                case 45:
                                                                    imageView_robot2_show3.setImageResource(R.drawable.c7);
                                                                    break;
                                                                case 46:
                                                                    imageView_robot2_show3.setImageResource(R.drawable.c8);
                                                                    break;
                                                                case 47:
                                                                    imageView_robot2_show3.setImageResource(R.drawable.c9);
                                                                    break;
                                                                case 48:
                                                                    imageView_robot2_show3.setImageResource(R.drawable.c10);
                                                                    break;
                                                                case 49:
                                                                    imageView_robot2_show3.setImageResource(R.drawable.c11);
                                                                    break;
                                                                case 50:
                                                                    imageView_robot2_show3.setImageResource(R.drawable.c12);
                                                                    break;
                                                                case 51:
                                                                    imageView_robot2_show3.setImageResource(R.drawable.c13);
                                                                    break;
                                                                default:
                                                                    break;
                                                            }
                                                            break;
                                                        case 3:
                                                            switch (RobotCardID[1][k]) {
                                                                case 0:
                                                                    imageView_robot2_show4.setImageResource(R.drawable.c40);
                                                                    break;
                                                                case 1:
                                                                    imageView_robot2_show4.setImageResource(R.drawable.c41);
                                                                    break;
                                                                case 2:
                                                                    imageView_robot2_show4.setImageResource(R.drawable.c42);
                                                                    break;
                                                                case 3:
                                                                    imageView_robot2_show4.setImageResource(R.drawable.c43);
                                                                    break;
                                                                case 4:
                                                                    imageView_robot2_show4.setImageResource(R.drawable.c44);
                                                                    break;
                                                                case 5:
                                                                    imageView_robot2_show4.setImageResource(R.drawable.c45);
                                                                    break;
                                                                case 6:
                                                                    imageView_robot2_show4.setImageResource(R.drawable.c46);
                                                                    break;
                                                                case 7:
                                                                    imageView_robot2_show4.setImageResource(R.drawable.c47);
                                                                    break;
                                                                case 8:
                                                                    imageView_robot2_show4.setImageResource(R.drawable.c48);
                                                                    break;
                                                                case 9:
                                                                    imageView_robot2_show4.setImageResource(R.drawable.c49);
                                                                    break;
                                                                case 10:
                                                                    imageView_robot2_show4.setImageResource(R.drawable.c50);
                                                                    break;
                                                                case 11:
                                                                    imageView_robot2_show4.setImageResource(R.drawable.c51);
                                                                    break;
                                                                case 12:
                                                                    imageView_robot2_show4.setImageResource(R.drawable.c52);
                                                                    break;
                                                                case 13:
                                                                    imageView_robot2_show4.setImageResource(R.drawable.c27);
                                                                    break;
                                                                case 14:
                                                                    imageView_robot2_show4.setImageResource(R.drawable.c28);
                                                                    break;
                                                                case 15:
                                                                    imageView_robot2_show4.setImageResource(R.drawable.c29);
                                                                    break;
                                                                case 16:
                                                                    imageView_robot2_show4.setImageResource(R.drawable.c30);
                                                                    break;
                                                                case 17:
                                                                    imageView_robot2_show4.setImageResource(R.drawable.c31);
                                                                    break;
                                                                case 18:
                                                                    imageView_robot2_show4.setImageResource(R.drawable.c32);
                                                                    break;
                                                                case 19:
                                                                    imageView_robot2_show4.setImageResource(R.drawable.c33);
                                                                    break;
                                                                case 20:
                                                                    imageView_robot2_show4.setImageResource(R.drawable.c34);
                                                                    break;
                                                                case 21:
                                                                    imageView_robot2_show4.setImageResource(R.drawable.c35);
                                                                    break;
                                                                case 22:
                                                                    imageView_robot2_show4.setImageResource(R.drawable.c36);
                                                                    break;
                                                                case 23:
                                                                    imageView_robot2_show4.setImageResource(R.drawable.c37);
                                                                    break;
                                                                case 24:
                                                                    imageView_robot2_show4.setImageResource(R.drawable.c38);
                                                                    break;
                                                                case 25:
                                                                    imageView_robot2_show4.setImageResource(R.drawable.c39);
                                                                    break;
                                                                case 26:
                                                                    imageView_robot2_show4.setImageResource(R.drawable.c14);
                                                                    break;
                                                                case 27:
                                                                    imageView_robot2_show4.setImageResource(R.drawable.c15);
                                                                    break;
                                                                case 28:
                                                                    imageView_robot2_show4.setImageResource(R.drawable.c16);
                                                                    break;
                                                                case 29:
                                                                    imageView_robot2_show4.setImageResource(R.drawable.c17);
                                                                    break;
                                                                case 30:
                                                                    imageView_robot2_show4.setImageResource(R.drawable.c18);
                                                                    break;
                                                                case 31:
                                                                    imageView_robot2_show4.setImageResource(R.drawable.c19);
                                                                    break;
                                                                case 32:
                                                                    imageView_robot2_show4.setImageResource(R.drawable.c20);
                                                                    break;
                                                                case 33:
                                                                    imageView_robot2_show4.setImageResource(R.drawable.c21);
                                                                    break;
                                                                case 34:
                                                                    imageView_robot2_show4.setImageResource(R.drawable.c22);
                                                                    break;
                                                                case 35:
                                                                    imageView_robot2_show4.setImageResource(R.drawable.c23);
                                                                    break;
                                                                case 36:
                                                                    imageView_robot2_show4.setImageResource(R.drawable.c24);
                                                                    break;
                                                                case 37:
                                                                    imageView_robot2_show4.setImageResource(R.drawable.c25);
                                                                    break;
                                                                case 38:
                                                                    imageView_robot2_show4.setImageResource(R.drawable.c26);
                                                                    break;
                                                                case 39:
                                                                    imageView_robot2_show4.setImageResource(R.drawable.c1);
                                                                    break;
                                                                case 40:
                                                                    imageView_robot2_show4.setImageResource(R.drawable.c2);
                                                                    break;
                                                                case 41:
                                                                    imageView_robot2_show4.setImageResource(R.drawable.c3);
                                                                    break;
                                                                case 42:
                                                                    imageView_robot2_show4.setImageResource(R.drawable.c4);
                                                                    break;
                                                                case 43:
                                                                    imageView_robot2_show4.setImageResource(R.drawable.c5);
                                                                    break;
                                                                case 44:
                                                                    imageView_robot2_show4.setImageResource(R.drawable.c6);
                                                                    break;
                                                                case 45:
                                                                    imageView_robot2_show4.setImageResource(R.drawable.c7);
                                                                    break;
                                                                case 46:
                                                                    imageView_robot2_show4.setImageResource(R.drawable.c8);
                                                                    break;
                                                                case 47:
                                                                    imageView_robot2_show4.setImageResource(R.drawable.c9);
                                                                    break;
                                                                case 48:
                                                                    imageView_robot2_show4.setImageResource(R.drawable.c10);
                                                                    break;
                                                                case 49:
                                                                    imageView_robot2_show4.setImageResource(R.drawable.c11);
                                                                    break;
                                                                case 50:
                                                                    imageView_robot2_show4.setImageResource(R.drawable.c12);
                                                                    break;
                                                                case 51:
                                                                    imageView_robot2_show4.setImageResource(R.drawable.c13);
                                                                    break;
                                                                default:
                                                                    break;
                                                            }
                                                            break;
                                                        case 4:
                                                            switch (RobotCardID[1][k]) {
                                                                case 0:
                                                                    imageView_robot2_show5.setImageResource(R.drawable.c40);
                                                                    break;
                                                                case 1:
                                                                    imageView_robot2_show5.setImageResource(R.drawable.c41);
                                                                    break;
                                                                case 2:
                                                                    imageView_robot2_show5.setImageResource(R.drawable.c42);
                                                                    break;
                                                                case 3:
                                                                    imageView_robot2_show5.setImageResource(R.drawable.c43);
                                                                    break;
                                                                case 4:
                                                                    imageView_robot2_show5.setImageResource(R.drawable.c44);
                                                                    break;
                                                                case 5:
                                                                    imageView_robot2_show5.setImageResource(R.drawable.c45);
                                                                    break;
                                                                case 6:
                                                                    imageView_robot2_show5.setImageResource(R.drawable.c46);
                                                                    break;
                                                                case 7:
                                                                    imageView_robot2_show5.setImageResource(R.drawable.c47);
                                                                    break;
                                                                case 8:
                                                                    imageView_robot2_show5.setImageResource(R.drawable.c48);
                                                                    break;
                                                                case 9:
                                                                    imageView_robot2_show5.setImageResource(R.drawable.c49);
                                                                    break;
                                                                case 10:
                                                                    imageView_robot2_show5.setImageResource(R.drawable.c50);
                                                                    break;
                                                                case 11:
                                                                    imageView_robot2_show5.setImageResource(R.drawable.c51);
                                                                    break;
                                                                case 12:
                                                                    imageView_robot2_show5.setImageResource(R.drawable.c52);
                                                                    break;
                                                                case 13:
                                                                    imageView_robot2_show5.setImageResource(R.drawable.c27);
                                                                    break;
                                                                case 14:
                                                                    imageView_robot2_show5.setImageResource(R.drawable.c28);
                                                                    break;
                                                                case 15:
                                                                    imageView_robot2_show5.setImageResource(R.drawable.c29);
                                                                    break;
                                                                case 16:
                                                                    imageView_robot2_show5.setImageResource(R.drawable.c30);
                                                                    break;
                                                                case 17:
                                                                    imageView_robot2_show5.setImageResource(R.drawable.c31);
                                                                    break;
                                                                case 18:
                                                                    imageView_robot2_show5.setImageResource(R.drawable.c32);
                                                                    break;
                                                                case 19:
                                                                    imageView_robot2_show5.setImageResource(R.drawable.c33);
                                                                    break;
                                                                case 20:
                                                                    imageView_robot2_show5.setImageResource(R.drawable.c34);
                                                                    break;
                                                                case 21:
                                                                    imageView_robot2_show5.setImageResource(R.drawable.c35);
                                                                    break;
                                                                case 22:
                                                                    imageView_robot2_show5.setImageResource(R.drawable.c36);
                                                                    break;
                                                                case 23:
                                                                    imageView_robot2_show5.setImageResource(R.drawable.c37);
                                                                    break;
                                                                case 24:
                                                                    imageView_robot2_show5.setImageResource(R.drawable.c38);
                                                                    break;
                                                                case 25:
                                                                    imageView_robot2_show5.setImageResource(R.drawable.c39);
                                                                    break;
                                                                case 26:
                                                                    imageView_robot2_show5.setImageResource(R.drawable.c14);
                                                                    break;
                                                                case 27:
                                                                    imageView_robot2_show5.setImageResource(R.drawable.c15);
                                                                    break;
                                                                case 28:
                                                                    imageView_robot2_show5.setImageResource(R.drawable.c16);
                                                                    break;
                                                                case 29:
                                                                    imageView_robot2_show5.setImageResource(R.drawable.c17);
                                                                    break;
                                                                case 30:
                                                                    imageView_robot2_show5.setImageResource(R.drawable.c18);
                                                                    break;
                                                                case 31:
                                                                    imageView_robot2_show5.setImageResource(R.drawable.c19);
                                                                    break;
                                                                case 32:
                                                                    imageView_robot2_show5.setImageResource(R.drawable.c20);
                                                                    break;
                                                                case 33:
                                                                    imageView_robot2_show5.setImageResource(R.drawable.c21);
                                                                    break;
                                                                case 34:
                                                                    imageView_robot2_show5.setImageResource(R.drawable.c22);
                                                                    break;
                                                                case 35:
                                                                    imageView_robot2_show5.setImageResource(R.drawable.c23);
                                                                    break;
                                                                case 36:
                                                                    imageView_robot2_show5.setImageResource(R.drawable.c24);
                                                                    break;
                                                                case 37:
                                                                    imageView_robot2_show5.setImageResource(R.drawable.c25);
                                                                    break;
                                                                case 38:
                                                                    imageView_robot2_show5.setImageResource(R.drawable.c26);
                                                                    break;
                                                                case 39:
                                                                    imageView_robot2_show5.setImageResource(R.drawable.c1);
                                                                    break;
                                                                case 40:
                                                                    imageView_robot2_show5.setImageResource(R.drawable.c2);
                                                                    break;
                                                                case 41:
                                                                    imageView_robot2_show5.setImageResource(R.drawable.c3);
                                                                    break;
                                                                case 42:
                                                                    imageView_robot2_show5.setImageResource(R.drawable.c4);
                                                                    break;
                                                                case 43:
                                                                    imageView_robot2_show5.setImageResource(R.drawable.c5);
                                                                    break;
                                                                case 44:
                                                                    imageView_robot2_show5.setImageResource(R.drawable.c6);
                                                                    break;
                                                                case 45:
                                                                    imageView_robot2_show5.setImageResource(R.drawable.c7);
                                                                    break;
                                                                case 46:
                                                                    imageView_robot2_show5.setImageResource(R.drawable.c8);
                                                                    break;
                                                                case 47:
                                                                    imageView_robot2_show5.setImageResource(R.drawable.c9);
                                                                    break;
                                                                case 48:
                                                                    imageView_robot2_show5.setImageResource(R.drawable.c10);
                                                                    break;
                                                                case 49:
                                                                    imageView_robot2_show5.setImageResource(R.drawable.c11);
                                                                    break;
                                                                case 50:
                                                                    imageView_robot2_show5.setImageResource(R.drawable.c12);
                                                                    break;
                                                                case 51:
                                                                    imageView_robot2_show5.setImageResource(R.drawable.c13);
                                                                    break;
                                                                default:
                                                                    break;
                                                            }
                                                        default:
                                                            break;
                                                    }
                                                }
                                            }
                                        }
                                        break;
                                    case 3:
                                        Log.d("s","收到三号机器人出牌消息");
                                        imageView_robot2_pass.setImageDrawable(null);
                                        imageView_robot3_show1.setImageDrawable(null);
                                        imageView_robot3_show2.setImageDrawable(null);
                                        imageView_robot3_show3.setImageDrawable(null);
                                        imageView_robot3_show4.setImageDrawable(null);
                                        imageView_robot3_show5.setImageDrawable(null);
                                        temp = Ruler.getCardsShowedByPreviousPlayer().size();
                                        temp = Integer.valueOf(CardNumber3.getText().toString()) - temp;
                                        CardNumber3.setText(String.valueOf(temp));
                                        for (int t = 0; t < Ruler.getCardsShowedByPreviousPlayer().size(); t++) {
                                            for (int k = 0; k < cardID.length; k++) {
                                                if (Ruler.getCardsShowedByPreviousPlayer().get(t).getCardId() == RobotCardID[2][k]) {
                                                    switch (t) {
                                                        case 0:
                                                            switch (RobotCardID[2][k]) {
                                                                case 0:
                                                                    imageView_robot3_show1.setImageResource(R.drawable.c40);
                                                                    break;
                                                                case 1:
                                                                    imageView_robot3_show1.setImageResource(R.drawable.c41);
                                                                    break;
                                                                case 2:
                                                                    imageView_robot3_show1.setImageResource(R.drawable.c42);
                                                                    break;
                                                                case 3:
                                                                    imageView_robot3_show1.setImageResource(R.drawable.c43);
                                                                    break;
                                                                case 4:
                                                                    imageView_robot3_show1.setImageResource(R.drawable.c44);
                                                                    break;
                                                                case 5:
                                                                    imageView_robot3_show1.setImageResource(R.drawable.c45);
                                                                    break;
                                                                case 6:
                                                                    imageView_robot3_show1.setImageResource(R.drawable.c46);
                                                                    break;
                                                                case 7:
                                                                    imageView_robot3_show1.setImageResource(R.drawable.c47);
                                                                    break;
                                                                case 8:
                                                                    imageView_robot3_show1.setImageResource(R.drawable.c48);
                                                                    break;
                                                                case 9:
                                                                    imageView_robot3_show1.setImageResource(R.drawable.c49);
                                                                    break;
                                                                case 10:
                                                                    imageView_robot3_show1.setImageResource(R.drawable.c50);
                                                                    break;
                                                                case 11:
                                                                    imageView_robot3_show1.setImageResource(R.drawable.c51);
                                                                    break;
                                                                case 12:
                                                                    imageView_robot3_show1.setImageResource(R.drawable.c52);
                                                                    break;
                                                                case 13:
                                                                    imageView_robot3_show1.setImageResource(R.drawable.c27);
                                                                    break;
                                                                case 14:
                                                                    imageView_robot3_show1.setImageResource(R.drawable.c28);
                                                                    break;
                                                                case 15:
                                                                    imageView_robot3_show1.setImageResource(R.drawable.c29);
                                                                    break;
                                                                case 16:
                                                                    imageView_robot3_show1.setImageResource(R.drawable.c30);
                                                                    break;
                                                                case 17:
                                                                    imageView_robot3_show1.setImageResource(R.drawable.c31);
                                                                    break;
                                                                case 18:
                                                                    imageView_robot3_show1.setImageResource(R.drawable.c32);
                                                                    break;
                                                                case 19:
                                                                    imageView_robot3_show1.setImageResource(R.drawable.c33);
                                                                    break;
                                                                case 20:
                                                                    imageView_robot3_show1.setImageResource(R.drawable.c34);
                                                                    break;
                                                                case 21:
                                                                    imageView_robot3_show1.setImageResource(R.drawable.c35);
                                                                    break;
                                                                case 22:
                                                                    imageView_robot3_show1.setImageResource(R.drawable.c36);
                                                                    break;
                                                                case 23:
                                                                    imageView_robot3_show1.setImageResource(R.drawable.c37);
                                                                    break;
                                                                case 24:
                                                                    imageView_robot3_show1.setImageResource(R.drawable.c38);
                                                                    break;
                                                                case 25:
                                                                    imageView_robot3_show1.setImageResource(R.drawable.c39);
                                                                    break;
                                                                case 26:
                                                                    imageView_robot3_show1.setImageResource(R.drawable.c14);
                                                                    break;
                                                                case 27:
                                                                    imageView_robot3_show1.setImageResource(R.drawable.c15);
                                                                    break;
                                                                case 28:
                                                                    imageView_robot3_show1.setImageResource(R.drawable.c16);
                                                                    break;
                                                                case 29:
                                                                    imageView_robot3_show1.setImageResource(R.drawable.c17);
                                                                    break;
                                                                case 30:
                                                                    imageView_robot3_show1.setImageResource(R.drawable.c18);
                                                                    break;
                                                                case 31:
                                                                    imageView_robot3_show1.setImageResource(R.drawable.c19);
                                                                    break;
                                                                case 32:
                                                                    imageView_robot3_show1.setImageResource(R.drawable.c20);
                                                                    break;
                                                                case 33:
                                                                    imageView_robot3_show1.setImageResource(R.drawable.c21);
                                                                    break;
                                                                case 34:
                                                                    imageView_robot3_show1.setImageResource(R.drawable.c22);
                                                                    break;
                                                                case 35:
                                                                    imageView_robot3_show1.setImageResource(R.drawable.c23);
                                                                    break;
                                                                case 36:
                                                                    imageView_robot3_show1.setImageResource(R.drawable.c24);
                                                                    break;
                                                                case 37:
                                                                    imageView_robot3_show1.setImageResource(R.drawable.c25);
                                                                    break;
                                                                case 38:
                                                                    imageView_robot3_show1.setImageResource(R.drawable.c26);
                                                                    break;
                                                                case 39:
                                                                    imageView_robot3_show1.setImageResource(R.drawable.c1);
                                                                    break;
                                                                case 40:
                                                                    imageView_robot3_show1.setImageResource(R.drawable.c2);
                                                                    break;
                                                                case 41:
                                                                    imageView_robot3_show1.setImageResource(R.drawable.c3);
                                                                    break;
                                                                case 42:
                                                                    imageView_robot3_show1.setImageResource(R.drawable.c4);
                                                                    break;
                                                                case 43:
                                                                    imageView_robot3_show1.setImageResource(R.drawable.c5);
                                                                    break;
                                                                case 44:
                                                                    imageView_robot3_show1.setImageResource(R.drawable.c6);
                                                                    break;
                                                                case 45:
                                                                    imageView_robot3_show1.setImageResource(R.drawable.c7);
                                                                    break;
                                                                case 46:
                                                                    imageView_robot3_show1.setImageResource(R.drawable.c8);
                                                                    break;
                                                                case 47:
                                                                    imageView_robot3_show1.setImageResource(R.drawable.c9);
                                                                    break;
                                                                case 48:
                                                                    imageView_robot3_show1.setImageResource(R.drawable.c10);
                                                                    break;
                                                                case 49:
                                                                    imageView_robot3_show1.setImageResource(R.drawable.c11);
                                                                    break;
                                                                case 50:
                                                                    imageView_robot3_show1.setImageResource(R.drawable.c12);
                                                                    break;
                                                                case 51:
                                                                    imageView_robot3_show1.setImageResource(R.drawable.c13);
                                                                    break;
                                                                default:
                                                                    break;
                                                            }
                                                            break;
                                                        case 1:
                                                            switch (RobotCardID[2][k]) {
                                                                case 0:
                                                                    imageView_robot3_show2.setImageResource(R.drawable.c40);
                                                                    break;
                                                                case 1:
                                                                    imageView_robot3_show2.setImageResource(R.drawable.c41);
                                                                    break;
                                                                case 2:
                                                                    imageView_robot3_show2.setImageResource(R.drawable.c42);
                                                                    break;
                                                                case 3:
                                                                    imageView_robot3_show2.setImageResource(R.drawable.c43);
                                                                    break;
                                                                case 4:
                                                                    imageView_robot3_show2.setImageResource(R.drawable.c44);
                                                                    break;
                                                                case 5:
                                                                    imageView_robot3_show2.setImageResource(R.drawable.c45);
                                                                    break;
                                                                case 6:
                                                                    imageView_robot3_show2.setImageResource(R.drawable.c46);
                                                                    break;
                                                                case 7:
                                                                    imageView_robot3_show2.setImageResource(R.drawable.c47);
                                                                    break;
                                                                case 8:
                                                                    imageView_robot3_show2.setImageResource(R.drawable.c48);
                                                                    break;
                                                                case 9:
                                                                    imageView_robot3_show2.setImageResource(R.drawable.c49);
                                                                    break;
                                                                case 10:
                                                                    imageView_robot3_show2.setImageResource(R.drawable.c50);
                                                                    break;
                                                                case 11:
                                                                    imageView_robot3_show2.setImageResource(R.drawable.c51);
                                                                    break;
                                                                case 12:
                                                                    imageView_robot3_show2.setImageResource(R.drawable.c52);
                                                                    break;
                                                                case 13:
                                                                    imageView_robot3_show2.setImageResource(R.drawable.c27);
                                                                    break;
                                                                case 14:
                                                                    imageView_robot3_show2.setImageResource(R.drawable.c28);
                                                                    break;
                                                                case 15:
                                                                    imageView_robot3_show2.setImageResource(R.drawable.c29);
                                                                    break;
                                                                case 16:
                                                                    imageView_robot3_show2.setImageResource(R.drawable.c30);
                                                                    break;
                                                                case 17:
                                                                    imageView_robot3_show2.setImageResource(R.drawable.c31);
                                                                    break;
                                                                case 18:
                                                                    imageView_robot3_show2.setImageResource(R.drawable.c32);
                                                                    break;
                                                                case 19:
                                                                    imageView_robot3_show2.setImageResource(R.drawable.c33);
                                                                    break;
                                                                case 20:
                                                                    imageView_robot3_show2.setImageResource(R.drawable.c34);
                                                                    break;
                                                                case 21:
                                                                    imageView_robot3_show2.setImageResource(R.drawable.c35);
                                                                    break;
                                                                case 22:
                                                                    imageView_robot3_show2.setImageResource(R.drawable.c36);
                                                                    break;
                                                                case 23:
                                                                    imageView_robot3_show2.setImageResource(R.drawable.c37);
                                                                    break;
                                                                case 24:
                                                                    imageView_robot3_show2.setImageResource(R.drawable.c38);
                                                                    break;
                                                                case 25:
                                                                    imageView_robot3_show2.setImageResource(R.drawable.c39);
                                                                    break;
                                                                case 26:
                                                                    imageView_robot3_show2.setImageResource(R.drawable.c14);
                                                                    break;
                                                                case 27:
                                                                    imageView_robot3_show2.setImageResource(R.drawable.c15);
                                                                    break;
                                                                case 28:
                                                                    imageView_robot3_show2.setImageResource(R.drawable.c16);
                                                                    break;
                                                                case 29:
                                                                    imageView_robot3_show2.setImageResource(R.drawable.c17);
                                                                    break;
                                                                case 30:
                                                                    imageView_robot3_show2.setImageResource(R.drawable.c18);
                                                                    break;
                                                                case 31:
                                                                    imageView_robot3_show2.setImageResource(R.drawable.c19);
                                                                    break;
                                                                case 32:
                                                                    imageView_robot3_show2.setImageResource(R.drawable.c20);
                                                                    break;
                                                                case 33:
                                                                    imageView_robot3_show2.setImageResource(R.drawable.c21);
                                                                    break;
                                                                case 34:
                                                                    imageView_robot3_show2.setImageResource(R.drawable.c22);
                                                                    break;
                                                                case 35:
                                                                    imageView_robot3_show2.setImageResource(R.drawable.c23);
                                                                    break;
                                                                case 36:
                                                                    imageView_robot3_show2.setImageResource(R.drawable.c24);
                                                                    break;
                                                                case 37:
                                                                    imageView_robot3_show2.setImageResource(R.drawable.c25);
                                                                    break;
                                                                case 38:
                                                                    imageView_robot3_show2.setImageResource(R.drawable.c26);
                                                                    break;
                                                                case 39:
                                                                    imageView_robot3_show2.setImageResource(R.drawable.c1);
                                                                    break;
                                                                case 40:
                                                                    imageView_robot3_show2.setImageResource(R.drawable.c2);
                                                                    break;
                                                                case 41:
                                                                    imageView_robot3_show2.setImageResource(R.drawable.c3);
                                                                    break;
                                                                case 42:
                                                                    imageView_robot3_show2.setImageResource(R.drawable.c4);
                                                                    break;
                                                                case 43:
                                                                    imageView_robot3_show2.setImageResource(R.drawable.c5);
                                                                    break;
                                                                case 44:
                                                                    imageView_robot3_show2.setImageResource(R.drawable.c6);
                                                                    break;
                                                                case 45:
                                                                    imageView_robot3_show2.setImageResource(R.drawable.c7);
                                                                    break;
                                                                case 46:
                                                                    imageView_robot3_show2.setImageResource(R.drawable.c8);
                                                                    break;
                                                                case 47:
                                                                    imageView_robot3_show2.setImageResource(R.drawable.c9);
                                                                    break;
                                                                case 48:
                                                                    imageView_robot3_show2.setImageResource(R.drawable.c10);
                                                                    break;
                                                                case 49:
                                                                    imageView_robot3_show2.setImageResource(R.drawable.c11);
                                                                    break;
                                                                case 50:
                                                                    imageView_robot3_show2.setImageResource(R.drawable.c12);
                                                                    break;
                                                                case 51:
                                                                    imageView_robot3_show2.setImageResource(R.drawable.c13);
                                                                    break;
                                                                default:
                                                                    break;
                                                            }
                                                            break;
                                                        case 2:
                                                            switch (RobotCardID[2][k]) {
                                                                case 0:
                                                                    imageView_robot3_show3.setImageResource(R.drawable.c40);
                                                                    break;
                                                                case 1:
                                                                    imageView_robot3_show3.setImageResource(R.drawable.c41);
                                                                    break;
                                                                case 2:
                                                                    imageView_robot3_show3.setImageResource(R.drawable.c42);
                                                                    break;
                                                                case 3:
                                                                    imageView_robot3_show3.setImageResource(R.drawable.c43);
                                                                    break;
                                                                case 4:
                                                                    imageView_robot3_show3.setImageResource(R.drawable.c44);
                                                                    break;
                                                                case 5:
                                                                    imageView_robot3_show3.setImageResource(R.drawable.c45);
                                                                    break;
                                                                case 6:
                                                                    imageView_robot3_show3.setImageResource(R.drawable.c46);
                                                                    break;
                                                                case 7:
                                                                    imageView_robot3_show3.setImageResource(R.drawable.c47);
                                                                    break;
                                                                case 8:
                                                                    imageView_robot3_show3.setImageResource(R.drawable.c48);
                                                                    break;
                                                                case 9:
                                                                    imageView_robot3_show3.setImageResource(R.drawable.c49);
                                                                    break;
                                                                case 10:
                                                                    imageView_robot3_show3.setImageResource(R.drawable.c50);
                                                                    break;
                                                                case 11:
                                                                    imageView_robot3_show3.setImageResource(R.drawable.c51);
                                                                    break;
                                                                case 12:
                                                                    imageView_robot3_show3.setImageResource(R.drawable.c52);
                                                                    break;
                                                                case 13:
                                                                    imageView_robot3_show3.setImageResource(R.drawable.c27);
                                                                    break;
                                                                case 14:
                                                                    imageView_robot3_show3.setImageResource(R.drawable.c28);
                                                                    break;
                                                                case 15:
                                                                    imageView_robot3_show3.setImageResource(R.drawable.c29);
                                                                    break;
                                                                case 16:
                                                                    imageView_robot3_show3.setImageResource(R.drawable.c30);
                                                                    break;
                                                                case 17:
                                                                    imageView_robot3_show3.setImageResource(R.drawable.c31);
                                                                    break;
                                                                case 18:
                                                                    imageView_robot3_show3.setImageResource(R.drawable.c32);
                                                                    break;
                                                                case 19:
                                                                    imageView_robot3_show3.setImageResource(R.drawable.c33);
                                                                    break;
                                                                case 20:
                                                                    imageView_robot3_show3.setImageResource(R.drawable.c34);
                                                                    break;
                                                                case 21:
                                                                    imageView_robot3_show3.setImageResource(R.drawable.c35);
                                                                    break;
                                                                case 22:
                                                                    imageView_robot3_show3.setImageResource(R.drawable.c36);
                                                                    break;
                                                                case 23:
                                                                    imageView_robot3_show3.setImageResource(R.drawable.c37);
                                                                    break;
                                                                case 24:
                                                                    imageView_robot3_show3.setImageResource(R.drawable.c38);
                                                                    break;
                                                                case 25:
                                                                    imageView_robot3_show3.setImageResource(R.drawable.c39);
                                                                    break;
                                                                case 26:
                                                                    imageView_robot3_show3.setImageResource(R.drawable.c14);
                                                                    break;
                                                                case 27:
                                                                    imageView_robot3_show3.setImageResource(R.drawable.c15);
                                                                    break;
                                                                case 28:
                                                                    imageView_robot3_show3.setImageResource(R.drawable.c16);
                                                                    break;
                                                                case 29:
                                                                    imageView_robot3_show3.setImageResource(R.drawable.c17);
                                                                    break;
                                                                case 30:
                                                                    imageView_robot3_show3.setImageResource(R.drawable.c18);
                                                                    break;
                                                                case 31:
                                                                    imageView_robot3_show3.setImageResource(R.drawable.c19);
                                                                    break;
                                                                case 32:
                                                                    imageView_robot3_show3.setImageResource(R.drawable.c20);
                                                                    break;
                                                                case 33:
                                                                    imageView_robot3_show3.setImageResource(R.drawable.c21);
                                                                    break;
                                                                case 34:
                                                                    imageView_robot3_show3.setImageResource(R.drawable.c22);
                                                                    break;
                                                                case 35:
                                                                    imageView_robot3_show3.setImageResource(R.drawable.c23);
                                                                    break;
                                                                case 36:
                                                                    imageView_robot3_show3.setImageResource(R.drawable.c24);
                                                                    break;
                                                                case 37:
                                                                    imageView_robot3_show3.setImageResource(R.drawable.c25);
                                                                    break;
                                                                case 38:
                                                                    imageView_robot3_show3.setImageResource(R.drawable.c26);
                                                                    break;
                                                                case 39:
                                                                    imageView_robot3_show3.setImageResource(R.drawable.c1);
                                                                    break;
                                                                case 40:
                                                                    imageView_robot3_show3.setImageResource(R.drawable.c2);
                                                                    break;
                                                                case 41:
                                                                    imageView_robot3_show3.setImageResource(R.drawable.c3);
                                                                    break;
                                                                case 42:
                                                                    imageView_robot3_show3.setImageResource(R.drawable.c4);
                                                                    break;
                                                                case 43:
                                                                    imageView_robot3_show3.setImageResource(R.drawable.c5);
                                                                    break;
                                                                case 44:
                                                                    imageView_robot3_show3.setImageResource(R.drawable.c6);
                                                                    break;
                                                                case 45:
                                                                    imageView_robot3_show3.setImageResource(R.drawable.c7);
                                                                    break;
                                                                case 46:
                                                                    imageView_robot3_show3.setImageResource(R.drawable.c8);
                                                                    break;
                                                                case 47:
                                                                    imageView_robot3_show3.setImageResource(R.drawable.c9);
                                                                    break;
                                                                case 48:
                                                                    imageView_robot3_show3.setImageResource(R.drawable.c10);
                                                                    break;
                                                                case 49:
                                                                    imageView_robot3_show3.setImageResource(R.drawable.c11);
                                                                    break;
                                                                case 50:
                                                                    imageView_robot3_show3.setImageResource(R.drawable.c12);
                                                                    break;
                                                                case 51:
                                                                    imageView_robot3_show3.setImageResource(R.drawable.c13);
                                                                    break;
                                                                default:
                                                                    break;
                                                            }
                                                            break;
                                                        case 3:
                                                            switch (RobotCardID[2][k]) {
                                                                case 0:
                                                                    imageView_robot3_show4.setImageResource(R.drawable.c40);
                                                                    break;
                                                                case 1:
                                                                    imageView_robot3_show4.setImageResource(R.drawable.c41);
                                                                    break;
                                                                case 2:
                                                                    imageView_robot3_show4.setImageResource(R.drawable.c42);
                                                                    break;
                                                                case 3:
                                                                    imageView_robot3_show4.setImageResource(R.drawable.c43);
                                                                    break;
                                                                case 4:
                                                                    imageView_robot3_show4.setImageResource(R.drawable.c44);
                                                                    break;
                                                                case 5:
                                                                    imageView_robot3_show4.setImageResource(R.drawable.c45);
                                                                    break;
                                                                case 6:
                                                                    imageView_robot3_show4.setImageResource(R.drawable.c46);
                                                                    break;
                                                                case 7:
                                                                    imageView_robot3_show4.setImageResource(R.drawable.c47);
                                                                    break;
                                                                case 8:
                                                                    imageView_robot3_show4.setImageResource(R.drawable.c48);
                                                                    break;
                                                                case 9:
                                                                    imageView_robot3_show4.setImageResource(R.drawable.c49);
                                                                    break;
                                                                case 10:
                                                                    imageView_robot3_show4.setImageResource(R.drawable.c50);
                                                                    break;
                                                                case 11:
                                                                    imageView_robot3_show4.setImageResource(R.drawable.c51);
                                                                    break;
                                                                case 12:
                                                                    imageView_robot3_show4.setImageResource(R.drawable.c52);
                                                                    break;
                                                                case 13:
                                                                    imageView_robot3_show4.setImageResource(R.drawable.c27);
                                                                    break;
                                                                case 14:
                                                                    imageView_robot3_show4.setImageResource(R.drawable.c28);
                                                                    break;
                                                                case 15:
                                                                    imageView_robot3_show4.setImageResource(R.drawable.c29);
                                                                    break;
                                                                case 16:
                                                                    imageView_robot3_show4.setImageResource(R.drawable.c30);
                                                                    break;
                                                                case 17:
                                                                    imageView_robot3_show4.setImageResource(R.drawable.c31);
                                                                    break;
                                                                case 18:
                                                                    imageView_robot3_show4.setImageResource(R.drawable.c32);
                                                                    break;
                                                                case 19:
                                                                    imageView_robot3_show4.setImageResource(R.drawable.c33);
                                                                    break;
                                                                case 20:
                                                                    imageView_robot3_show4.setImageResource(R.drawable.c34);
                                                                    break;
                                                                case 21:
                                                                    imageView_robot3_show4.setImageResource(R.drawable.c35);
                                                                    break;
                                                                case 22:
                                                                    imageView_robot3_show4.setImageResource(R.drawable.c36);
                                                                    break;
                                                                case 23:
                                                                    imageView_robot3_show4.setImageResource(R.drawable.c37);
                                                                    break;
                                                                case 24:
                                                                    imageView_robot3_show4.setImageResource(R.drawable.c38);
                                                                    break;
                                                                case 25:
                                                                    imageView_robot3_show4.setImageResource(R.drawable.c39);
                                                                    break;
                                                                case 26:
                                                                    imageView_robot3_show4.setImageResource(R.drawable.c14);
                                                                    break;
                                                                case 27:
                                                                    imageView_robot3_show4.setImageResource(R.drawable.c15);
                                                                    break;
                                                                case 28:
                                                                    imageView_robot3_show4.setImageResource(R.drawable.c16);
                                                                    break;
                                                                case 29:
                                                                    imageView_robot3_show4.setImageResource(R.drawable.c17);
                                                                    break;
                                                                case 30:
                                                                    imageView_robot3_show4.setImageResource(R.drawable.c18);
                                                                    break;
                                                                case 31:
                                                                    imageView_robot3_show4.setImageResource(R.drawable.c19);
                                                                    break;
                                                                case 32:
                                                                    imageView_robot3_show4.setImageResource(R.drawable.c20);
                                                                    break;
                                                                case 33:
                                                                    imageView_robot3_show4.setImageResource(R.drawable.c21);
                                                                    break;
                                                                case 34:
                                                                    imageView_robot3_show4.setImageResource(R.drawable.c22);
                                                                    break;
                                                                case 35:
                                                                    imageView_robot3_show4.setImageResource(R.drawable.c23);
                                                                    break;
                                                                case 36:
                                                                    imageView_robot3_show4.setImageResource(R.drawable.c24);
                                                                    break;
                                                                case 37:
                                                                    imageView_robot3_show4.setImageResource(R.drawable.c25);
                                                                    break;
                                                                case 38:
                                                                    imageView_robot3_show4.setImageResource(R.drawable.c26);
                                                                    break;
                                                                case 39:
                                                                    imageView_robot3_show4.setImageResource(R.drawable.c1);
                                                                    break;
                                                                case 40:
                                                                    imageView_robot3_show4.setImageResource(R.drawable.c2);
                                                                    break;
                                                                case 41:
                                                                    imageView_robot3_show4.setImageResource(R.drawable.c3);
                                                                    break;
                                                                case 42:
                                                                    imageView_robot3_show4.setImageResource(R.drawable.c4);
                                                                    break;
                                                                case 43:
                                                                    imageView_robot3_show4.setImageResource(R.drawable.c5);
                                                                    break;
                                                                case 44:
                                                                    imageView_robot3_show4.setImageResource(R.drawable.c6);
                                                                    break;
                                                                case 45:
                                                                    imageView_robot3_show4.setImageResource(R.drawable.c7);
                                                                    break;
                                                                case 46:
                                                                    imageView_robot3_show4.setImageResource(R.drawable.c8);
                                                                    break;
                                                                case 47:
                                                                    imageView_robot3_show4.setImageResource(R.drawable.c9);
                                                                    break;
                                                                case 48:
                                                                    imageView_robot3_show4.setImageResource(R.drawable.c10);
                                                                    break;
                                                                case 49:
                                                                    imageView_robot3_show4.setImageResource(R.drawable.c11);
                                                                    break;
                                                                case 50:
                                                                    imageView_robot3_show4.setImageResource(R.drawable.c12);
                                                                    break;
                                                                case 51:
                                                                    imageView_robot3_show4.setImageResource(R.drawable.c13);
                                                                    break;
                                                                default:
                                                                    break;
                                                            }
                                                            break;
                                                        case 4:
                                                            switch (RobotCardID[2][k]) {
                                                                case 0:
                                                                    imageView_robot3_show5.setImageResource(R.drawable.c40);
                                                                    break;
                                                                case 1:
                                                                    imageView_robot3_show5.setImageResource(R.drawable.c41);
                                                                    break;
                                                                case 2:
                                                                    imageView_robot3_show5.setImageResource(R.drawable.c42);
                                                                    break;
                                                                case 3:
                                                                    imageView_robot3_show5.setImageResource(R.drawable.c43);
                                                                    break;
                                                                case 4:
                                                                    imageView_robot3_show5.setImageResource(R.drawable.c44);
                                                                    break;
                                                                case 5:
                                                                    imageView_robot3_show5.setImageResource(R.drawable.c45);
                                                                    break;
                                                                case 6:
                                                                    imageView_robot3_show5.setImageResource(R.drawable.c46);
                                                                    break;
                                                                case 7:
                                                                    imageView_robot3_show5.setImageResource(R.drawable.c47);
                                                                    break;
                                                                case 8:
                                                                    imageView_robot3_show5.setImageResource(R.drawable.c48);
                                                                    break;
                                                                case 9:
                                                                    imageView_robot3_show5.setImageResource(R.drawable.c49);
                                                                    break;
                                                                case 10:
                                                                    imageView_robot3_show5.setImageResource(R.drawable.c50);
                                                                    break;
                                                                case 11:
                                                                    imageView_robot3_show5.setImageResource(R.drawable.c51);
                                                                    break;
                                                                case 12:
                                                                    imageView_robot3_show5.setImageResource(R.drawable.c52);
                                                                    break;
                                                                case 13:
                                                                    imageView_robot3_show5.setImageResource(R.drawable.c27);
                                                                    break;
                                                                case 14:
                                                                    imageView_robot3_show5.setImageResource(R.drawable.c28);
                                                                    break;
                                                                case 15:
                                                                    imageView_robot3_show5.setImageResource(R.drawable.c29);
                                                                    break;
                                                                case 16:
                                                                    imageView_robot3_show5.setImageResource(R.drawable.c30);
                                                                    break;
                                                                case 17:
                                                                    imageView_robot3_show5.setImageResource(R.drawable.c31);
                                                                    break;
                                                                case 18:
                                                                    imageView_robot3_show5.setImageResource(R.drawable.c32);
                                                                    break;
                                                                case 19:
                                                                    imageView_robot3_show5.setImageResource(R.drawable.c33);
                                                                    break;
                                                                case 20:
                                                                    imageView_robot3_show5.setImageResource(R.drawable.c34);
                                                                    break;
                                                                case 21:
                                                                    imageView_robot3_show5.setImageResource(R.drawable.c35);
                                                                    break;
                                                                case 22:
                                                                    imageView_robot3_show5.setImageResource(R.drawable.c36);
                                                                    break;
                                                                case 23:
                                                                    imageView_robot3_show5.setImageResource(R.drawable.c37);
                                                                    break;
                                                                case 24:
                                                                    imageView_robot3_show5.setImageResource(R.drawable.c38);
                                                                    break;
                                                                case 25:
                                                                    imageView_robot3_show5.setImageResource(R.drawable.c39);
                                                                    break;
                                                                case 26:
                                                                    imageView_robot3_show5.setImageResource(R.drawable.c14);
                                                                    break;
                                                                case 27:
                                                                    imageView_robot3_show5.setImageResource(R.drawable.c15);
                                                                    break;
                                                                case 28:
                                                                    imageView_robot3_show5.setImageResource(R.drawable.c16);
                                                                    break;
                                                                case 29:
                                                                    imageView_robot3_show5.setImageResource(R.drawable.c17);
                                                                    break;
                                                                case 30:
                                                                    imageView_robot3_show5.setImageResource(R.drawable.c18);
                                                                    break;
                                                                case 31:
                                                                    imageView_robot3_show5.setImageResource(R.drawable.c19);
                                                                    break;
                                                                case 32:
                                                                    imageView_robot3_show5.setImageResource(R.drawable.c20);
                                                                    break;
                                                                case 33:
                                                                    imageView_robot3_show5.setImageResource(R.drawable.c21);
                                                                    break;
                                                                case 34:
                                                                    imageView_robot3_show5.setImageResource(R.drawable.c22);
                                                                    break;
                                                                case 35:
                                                                    imageView_robot3_show5.setImageResource(R.drawable.c23);
                                                                    break;
                                                                case 36:
                                                                    imageView_robot3_show5.setImageResource(R.drawable.c24);
                                                                    break;
                                                                case 37:
                                                                    imageView_robot3_show5.setImageResource(R.drawable.c25);
                                                                    break;
                                                                case 38:
                                                                    imageView_robot3_show5.setImageResource(R.drawable.c26);
                                                                    break;
                                                                case 39:
                                                                    imageView_robot3_show5.setImageResource(R.drawable.c1);
                                                                    break;
                                                                case 40:
                                                                    imageView_robot3_show5.setImageResource(R.drawable.c2);
                                                                    break;
                                                                case 41:
                                                                    imageView_robot3_show5.setImageResource(R.drawable.c3);
                                                                    break;
                                                                case 42:
                                                                    imageView_robot3_show5.setImageResource(R.drawable.c4);
                                                                    break;
                                                                case 43:
                                                                    imageView_robot3_show5.setImageResource(R.drawable.c5);
                                                                    break;
                                                                case 44:
                                                                    imageView_robot3_show5.setImageResource(R.drawable.c6);
                                                                    break;
                                                                case 45:
                                                                    imageView_robot3_show5.setImageResource(R.drawable.c7);
                                                                    break;
                                                                case 46:
                                                                    imageView_robot3_show5.setImageResource(R.drawable.c8);
                                                                    break;
                                                                case 47:
                                                                    imageView_robot3_show5.setImageResource(R.drawable.c9);
                                                                    break;
                                                                case 48:
                                                                    imageView_robot3_show5.setImageResource(R.drawable.c10);
                                                                    break;
                                                                case 49:
                                                                    imageView_robot3_show5.setImageResource(R.drawable.c11);
                                                                    break;
                                                                case 50:
                                                                    imageView_robot3_show5.setImageResource(R.drawable.c12);
                                                                    break;
                                                                case 51:
                                                                    imageView_robot3_show5.setImageResource(R.drawable.c13);
                                                                    break;
                                                                default:
                                                                    break;
                                                            }
                                                        default:
                                                            break;
                                                    }
                                                }
                                            }
                                        }
                                        break;
                                    default:
                                        break;
                                }
                                break;
                            case -1:
                                switch (temp1[1]){
                                    case 1:
                                        imageView_robot1_pass.setImageResource(R.drawable.btnpass);
                                        break;
                                    case 2:
                                        imageView_robot1_pass.setImageDrawable(null);
                                        imageView_robot2_pass.setImageResource(R.drawable.btnpass);
                                        break;
                                    case 3:
                                        imageView_robot2_pass.setImageDrawable(null);
                                        imageView_robot3_pass.setImageResource(R.drawable.btnpass);
                                        break;
                                }
                                break;
                            default:
                                break;
                        }
                        break;
                    case 1:
                        CardNumber1.setText("0");
                        final AlertDialog.Builder builder1=new Builder(PlayWithRobot.this);
                        //builder.setIcon(R.drawable.ic_launcher);//设置图标
                        builder1.setTitle("获胜的是1号机器人");//设置对话框的标题
                        builder1.setMessage("你的分数是:"+Ruler.getHumanPlayerArray()[0].getScore());//设置对话框的内容
                        builder1.setPositiveButton("返回主菜单", new OnClickListener() {  //返回主菜单
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                Intent intent = new Intent(PlayWithRobot.this, MainActivity.class);
                                startActivity(intent);
                                }
                        });
                        builder1.setNegativeButton("下一局游戏", new OnClickListener() {  //下一局
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                Intent intent = getIntent();
//                                finish();
                                startActivity(intent);
                            }
                        });
                        b=builder1.create();
                        b.show();  //必须show一下才能看到对话框，跟Toast一样的道理
                        break;
                    case 2:
                        CardNumber2.setText("0");
                        AlertDialog.Builder builder2=new Builder(PlayWithRobot.this);
                        //builder.setIcon(R.drawable.ic_launcher);//设置图标
                        builder2.setTitle("获胜的是2号机器人");//设置对话框的标题
                        builder2.setMessage("你的分数是:"+Ruler.getHumanPlayerArray()[0].getScore());//设置对话框的内容
                        builder2.setPositiveButton("返回主菜单", new OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                Intent intent = new Intent(PlayWithRobot.this, MainActivity.class);
                                startActivity(intent);
                            }
                        });
                        builder2.setNegativeButton("下一局游戏", new OnClickListener() {  //下一局
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                Intent intent = getIntent();
//                                finish();
                                startActivity(intent);
                            }
                        });
                        c=builder2.create();
                        c.show();  //必须show一下才能看到对话框，跟Toast一样的道理
                        break;
                    case 3:
                        CardNumber3.setText("0");
                        AlertDialog.Builder builder3=new Builder(PlayWithRobot.this);
                        //builder.setIcon(R.drawable.ic_launcher);//设置图标
                        builder3.setTitle("获胜的是3号机器人");//设置对话框的标题
                        builder3.setMessage("你的分数是:"+Ruler.getHumanPlayerArray()[0].getScore());//设置对话框的内容
                        builder3.setPositiveButton("返回主菜单", new OnClickListener() {  //返回主菜单
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                Intent intent = new Intent(PlayWithRobot.this, MainActivity.class);
                                startActivity(intent);
                            }
                        });

                        builder3.setNegativeButton("下一局游戏", new OnClickListener() {  //下一局
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                Intent intent = getIntent();
//                                finish();
                                startActivity(intent);
                            }
                        });
                        d=builder3.create();
                        d.show();  //必须show一下才能看到对话框，跟Toast一样的道理
                        break;
                    default:
                        System.exit(0);
                        break;
                }

            }
        };
    }

    @Override
    protected void onPause() {
        Log.d("a","删除对话框");
        if (a!=null){
            a.dismiss();
        }
        if (b!=null){
            b.dismiss();
        }
        if (c!=null){
            c.dismiss();
        }
        if (d!=null){
            d.dismiss();
        }
        mhandle.removeCallbacks(thread);
        super.onPause();
    }
    @Override
    protected void onDestroy() {

        if (a!=null){
            a.dismiss();
        }
        if (b!=null){
            b.dismiss();
        }
        if (c!=null){
            c.dismiss();
        }
        if (d!=null){
            d.dismiss();
        }
        if (thread!=null){
            mhandle.removeCallbacks(thread);
        }
        super.onDestroy();
    }

    class ThreadFlag extends Thread{

        @Override
        public void run(){
            super.run();
            //做你需要的操作
            int temp=1;
            int pk=0;
            int []message_date=new int[3];
            message_date[2] = 0;
            while (flag) {
                while (exit) {
                    for (int i = 1; i < Ruler.getPlayerArray().length; i++) {
                        temp = 1;
                        pk++;
                        Log.d("pk的次数", String.valueOf(pk));
                        if (Ruler.getPlayerArray()[i].getPlayerState() == PlayerState.PlayerRound) {
                            if (Ruler.getIdOfPlayerWhoShowCardSuccessfully() == Ruler.getPlayerArray()[i].getPlayerId()) {
                                Ruler.getPlayerArray()[i].randomCardShow();
                                Log.d("s", "随机出一张牌");
                            } else {
                                Log.d("s", "按规则出一张牌");
                                temp = Ruler.getPlayerArray()[i].RobotShowCard();
                            }
                            if (Ruler.getPlayerArray()[i].getCardsToShow()!=null){
                                Ruler.getPlayerArray()[i].showCards();
                            }
                            Log.d("s", "出牌后");
                            Ruler.setTypeOfCards(Ruler.getCardsShowedByPreviousPlayer());
                            if (Ruler.WillWin(Ruler.getPlayerArray()[i])) {
                                ruler.caculateScore();
                                message_date[2] = i;
                                exit = false;
                                flag=false;
                            }
                            if (temp == 1) {
                                Ruler.toTheTurnOfNextPlayer(Ruler.getPlayerArray()[i]);
                                message_date[0] = 1;
                                Log.d("s", "机器人出牌");
                            } else if (temp == 0) {
                                Ruler.toTheTurnOfNextPlayer(Ruler.getPlayerArray()[i]);
                                message_date[0] = -1;
                                Log.d("s", "机器人PASS");
                            }
                            Log.d("s", "发送消息前");
                            message_date[1] = i;
                            Message message = Message.obtain();
                            message.obj = message_date;
                            mhandle.sendMessage(message);
                            Log.d("s", "发送消息后");
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            Log.d("s", "在睡觉之后");
                        }
                    }
                }
            }
        }
    }


    private void setListeners() {
        OnClick onClick = new OnClick();
        image1.setOnClickListener(onClick);
        image2.setOnClickListener(onClick);
        image3.setOnClickListener(onClick);
        image4.setOnClickListener(onClick);
        image5.setOnClickListener(onClick);
        image6.setOnClickListener(onClick);
        image7.setOnClickListener(onClick);
        image8.setOnClickListener(onClick);
        image9.setOnClickListener(onClick);
        image10.setOnClickListener(onClick);
        image11.setOnClickListener(onClick);
        image12.setOnClickListener(onClick);
        image13.setOnClickListener(onClick);
        imageButton_pass.setOnClickListener(onClick);
        imageButton_showcard.setOnClickListener(onClick);
        button_ready.setOnClickListener(onClick);
    }



    private class OnClick implements View.OnClickListener {
        //选出要打的牌
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.imageView1:
                     (location) {
                        image1.offsetTopAndBottom(-50);
                        location = false;
                        Ruler.getHumanPlayerArray()[0].addCardToShow(cardID[0]);
                    } else {
                        image1.offsetTopAndBottom(50);
                        location = true;
                        if    Ruler.getHumanPlayerArray()[0].removeCardsToShow(cardID[0]);
                    }
                    break;
                case R.id.imageView2:
                    if (location_two) {
                        image2.offsetTopAndBottom(-50);
                        location_two = false;
                        Ruler.getHumanPlayerArray()[0].addCardToShow(cardID[1]);

                    } else {
                        image2.offsetTopAndBottom(50);
                        location_two = true;
                        Ruler.getHumanPlayerArray()[0].removeCardsToShow(cardID[1]);
                    }
                    break;
                case R.id.imageView3:
                    if (location_three) {
                        image3.offsetTopAndBottom(-50);
                        location_three = false;
                        Ruler.getHumanPlayerArray()[0].addCardToShow(cardID[2]);

                    } else {
                        image3.offsetTopAndBottom(50);
                        location_three = true;
                        Ruler.getHumanPlayerArray()[0].removeCardsToShow(cardID[2]);
                    }
                    break;
                case R.id.imageView4:
                    if (location_four) {
                        image4.offsetTopAndBottom(-50);
                        location_four = false;
                        Ruler.getHumanPlayerArray()[0].addCardToShow(cardID[3]);

                    } else {
                        image4.offsetTopAndBottom(50);
                        location_four = true;
                        Ruler.getHumanPlayerArray()[0].removeCardsToShow(cardID[3]);
                    }
                    break;
                case R.id.imageView5:
                    if (location_five) {
                        image5.offsetTopAndBottom(-50);
                        location_five = false;
                        Ruler.getHumanPlayerArray()[0].addCardToShow(cardID[4]);
                    } else {
                        image5.offsetTopAndBottom(50);
                        location_five = true;
                        Ruler.getHumanPlayerArray()[0].removeCardsToShow(cardID[4]);
                    }
                    break;
                case R.id.imageView6:
                    if (location_six) {
                        image6.offsetTopAndBottom(-50);
                        location_six = false;
                        Ruler.getHumanPlayerArray()[0].addCardToShow(cardID[5]);
                    } else {
                        image6.offsetTopAndBottom(50);
                        location_six = true;
                        Ruler.getHumanPlayerArray()[0].removeCardsToShow(cardID[5]);
                    }
                    break;
                case R.id.imageView7:
                    if (location_seven) {
                        image7.offsetTopAndBottom(-50);
                        location_seven = false;
                        Ruler.getHumanPlayerArray()[0].addCardToShow(cardID[6]);
                    } else {
                        image7.offsetTopAndBottom(50);
                        location_seven = true;
                        Ruler.getHumanPlayerArray()[0].removeCardsToShow(cardID[6]);
                    }
                    break;
                case R.id.imageView8:
                    if (location_eight) {
                        image8.offsetTopAndBottom(-50);
                        location_eight = false;
                        Ruler.getHumanPlayerArray()[0].addCardToShow(cardID[7]);
                    } else {
                        image8.offsetTopAndBottom(50);
                        location_eight = true;
                        Ruler.getHumanPlayerArray()[0].removeCardsToShow(cardID[7]);
                    }
                    break;
                case R.id.imageView9:
                    if (location_nine) {
                        image9.offsetTopAndBottom(-50);
                        location_nine = false;
                        Ruler.getHumanPlayerArray()[0].addCardToShow(cardID[8]);

                    } else {
                        image9.offsetTopAndBottom(50);
                        location_nine = true;
                        Ruler.getHumanPlayerArray()[0].removeCardsToShow(cardID[8]);
                    }
                    break;
                case R.id.imageView10:
                    if (location_ten) {
                        image10.offsetTopAndBottom(-50);
                        location_ten = false;
                        Ruler.getHumanPlayerArray()[0].addCardToShow(cardID[9]);
                    } else {
                        image10.offsetTopAndBottom(50);
                        location_ten = true;
                        Ruler.getHumanPlayerArray()[0].removeCardsToShow(cardID[9]);
                    }
                    break;
                case R.id.imageView11:
                    if (location_eleven) {
                        image11.offsetTopAndBottom(-50);
                        location_eleven = false;
                        Ruler.getHumanPlayerArray()[0].addCardToShow(cardID[10]);

                    } else {
                        image11.offsetTopAndBottom(50);
                        location_eleven = true;
                        Ruler.getHumanPlayerArray()[0].removeCardsToShow(cardID[10]);
                    }
                    break;
                case R.id.imageView12:
                    if (location_twelve) {
                        image12.offsetTopAndBottom(-50);
                        location_twelve = false;
                        Ruler.getHumanPlayerArray()[0].addCardToShow(cardID[11]);
                    } else {
                        image12.offsetTopAndBottom(50);
                        location_twelve = true;
                        Ruler.getHumanPlayerArray()[0].removeCardsToShow(cardID[11]);
                    }
                    break;
                case R.id.imageView13:
                    if (location_thirteen) {
                        image13.offsetTopAndBottom(-50);
                        location_thirteen = false;
                        Ruler.getHumanPlayerArray()[0].addCardToShow(cardID[12]);
                    } else {
                        image13.offsetTopAndBottom(50);
                        location_thirteen = true;
                        Ruler.getHumanPlayerArray()[0].removeCardsToShow(cardID[12]);
                    }
                    break;
                case R.id.imageButton_pass:
                    Log.d("ss","出牌");
                    Log.d("ss",String.valueOf(Ruler.getPlayerArray()[1].getPlayerState()));
                    Log.d("ss",String.valueOf(Ruler.getPlayerArray()[2].getPlayerState()));
                    Log.d("ss",String.valueOf(Ruler.getPlayerArray()[3].getPlayerState()));
                    imageView_robot3_pass.setImageDrawable(null);
                    if(Ruler.getHumanPlayerArray()[0].getPlayerState()==PlayerState.PlayerRound){
                        Ruler.getHumanPlayerArray()[0].pass();
                        Toast toast=Toast.makeText(PlayWithRobot.this,"pass",Toast.LENGTH_SHORT);
                        toast.show();
                        /*
                        for(int i=1;i<Ruler.getPlayerArray().length;i++) {
                            int temp=0;
                            if (Ruler.getPlayerArray()[i].getPlayerState() == PlayerState.PlayerRound) {
                                    Ruler.getPlayerArray()[i].RobotShowCard();
                                    Ruler.getPlayerArray()[i].showCards();
                                    temp=Ruler.getCardsShowedByPreviousPlayer().size();
                                    Ruler.setTypeOfCards(Ruler.getCardsShowedByPreviousPlayer());
                                    Ruler.toTheTurnOfNextPlayer(Ruler.getPlayerArray()[i]);
                                    temp = Integer.valueOf(CardNumber1.getText().toString()) - temp;
                                    CardNumber3.setText(String.valueOf(temp));
                                    imageView_show1.setImageDrawable(null);
                                    imageView_show2.setImageDrawable(null);
                                    imageView_show3.setImageDrawable(null);
                                    imageView_show4.setImageDrawable(null);
                                    imageView_show5.setImageDrawable(null);
                                    for (int t = 0; t < Ruler.getCardsShowedByPreviousPlayer().size(); t++) {
                                        for (int k = 0; k < cardID.length; k++) {
                                            if (Ruler.getCardsShowedByPreviousPlayer().get(t).getCardId() == RobotCardID[i - 1][k]) {
                                                switch (t) {
                                                    case 0:
                                                        switch (RobotCardID[i - 1][k]) {
                                                            case 0:
                                                                imageView_show1.setImageResource(R.drawable.c40);
                                                                break;
                                                            case 1:
                                                                imageView_show1.setImageResource(R.drawable.c41);
                                                                break;
                                                            case 2:
                                                                imageView_show1.setImageResource(R.drawable.c42);
                                                                break;
                                                            case 3:
                                                                imageView_show1.setImageResource(R.drawable.c43);
                                                                break;
                                                            case 4:
                                                                imageView_show1.setImageResource(R.drawable.c44);
                                                                break;
                                                            case 5:
                                                                imageView_show1.setImageResource(R.drawable.c45);
                                                                break;
                                                            case 6:
                                                                imageView_show1.setImageResource(R.drawable.c46);
                                                                break;
                                                            case 7:
                                                                imageView_show1.setImageResource(R.drawable.c47);
                                                                break;
                                                            case 8:
                                                                imageView_show1.setImageResource(R.drawable.c48);
                                                                break;
                                                            case 9:
                                                                imageView_show1.setImageResource(R.drawable.c49);
                                                                break;
                                                            case 10:
                                                                imageView_show1.setImageResource(R.drawable.c50);
                                                                break;
                                                            case 11:
                                                                imageView_show1.setImageResource(R.drawable.c51);
                                                                break;
                                                            case 12:
                                                                imageView_show1.setImageResource(R.drawable.c52);
                                                                break;
                                                            case 13:
                                                                imageView_show1.setImageResource(R.drawable.c27);
                                                                break;
                                                            case 14:
                                                                imageView_show1.setImageResource(R.drawable.c28);
                                                                break;
                                                            case 15:
                                                                imageView_show1.setImageResource(R.drawable.c29);
                                                                break;
                                                            case 16:
                                                                imageView_show1.setImageResource(R.drawable.c30);
                                                                break;
                                                            case 17:
                                                                imageView_show1.setImageResource(R.drawable.c31);
                                                                break;
                                                            case 18:
                                                                imageView_show1.setImageResource(R.drawable.c32);
                                                                break;
                                                            case 19:
                                                                imageView_show1.setImageResource(R.drawable.c33);
                                                                break;
                                                            case 20:
                                                                imageView_show1.setImageResource(R.drawable.c34);
                                                                break;
                                                            case 21:
                                                                imageView_show1.setImageResource(R.drawable.c35);
                                                                break;
                                                            case 22:
                                                                imageView_show1.setImageResource(R.drawable.c36);
                                                                break;
                                                            case 23:
                                                                imageView_show1.setImageResource(R.drawable.c37);
                                                                break;
                                                            case 24:
                                                                imageView_show1.setImageResource(R.drawable.c38);
                                                                break;
                                                            case 25:
                                                                imageView_show1.setImageResource(R.drawable.c39);
                                                                break;
                                                            case 26:
                                                                imageView_show1.setImageResource(R.drawable.c14);
                                                                break;
                                                            case 27:
                                                                imageView_show1.setImageResource(R.drawable.c15);
                                                                break;
                                                            case 28:
                                                                imageView_show1.setImageResource(R.drawable.c16);
                                                                break;
                                                            case 29:
                                                                imageView_show1.setImageResource(R.drawable.c17);
                                                                break;
                                                            case 30:
                                                                imageView_show1.setImageResource(R.drawable.c18);
                                                                break;
                                                            case 31:
                                                                imageView_show1.setImageResource(R.drawable.c19);
                                                                break;
                                                            case 32:
                                                                imageView_show1.setImageResource(R.drawable.c20);
                                                                break;
                                                            case 33:
                                                                imageView_show1.setImageResource(R.drawable.c21);
                                                                break;
                                                            case 34:
                                                                imageView_show1.setImageResource(R.drawable.c22);
                                                                break;
                                                            case 35:
                                                                imageView_show1.setImageResource(R.drawable.c23);
                                                                break;
                                                            case 36:
                                                                imageView_show1.setImageResource(R.drawable.c24);
                                                                break;
                                                            case 37:
                                                                imageView_show1.setImageResource(R.drawable.c25);
                                                                break;
                                                            case 38:
                                                                imageView_show1.setImageResource(R.drawable.c26);
                                                                break;
                                                            case 39:
                                                                imageView_show1.setImageResource(R.drawable.c1);
                                                                break;
                                                            case 40:
                                                                imageView_show1.setImageResource(R.drawable.c2);
                                                                break;
                                                            case 41:
                                                                imageView_show1.setImageResource(R.drawable.c3);
                                                                break;
                                                            case 42:
                                                                imageView_show1.setImageResource(R.drawable.c4);
                                                                break;
                                                            case 43:
                                                                imageView_show1.setImageResource(R.drawable.c5);
                                                                break;
                                                            case 44:
                                                                imageView_show1.setImageResource(R.drawable.c6);
                                                                break;
                                                            case 45:
                                                                imageView_show1.setImageResource(R.drawable.c7);
                                                                break;
                                                            case 46:
                                                                imageView_show1.setImageResource(R.drawable.c8);
                                                                break;
                                                            case 47:
                                                                imageView_show1.setImageResource(R.drawable.c9);
                                                                break;
                                                            case 48:
                                                                imageView_show1.setImageResource(R.drawable.c10);
                                                                break;
                                                            case 49:
                                                                imageView_show1.setImageResource(R.drawable.c11);
                                                                break;
                                                            case 50:
                                                                imageView_show1.setImageResource(R.drawable.c12);
                                                                break;
                                                            case 51:
                                                                imageView_show1.setImageResource(R.drawable.c13);
                                                                break;
                                                            default:
                                                                break;
                                                        }
                                                        break;
                                                    case 1:
                                                        switch (RobotCardID[i - 1][k]) {
                                                            case 0:
                                                                imageView_show2.setImageResource(R.drawable.c40);
                                                                break;
                                                            case 1:
                                                                imageView_show2.setImageResource(R.drawable.c41);
                                                                break;
                                                            case 2:
                                                                imageView_show2.setImageResource(R.drawable.c42);
                                                                break;
                                                            case 3:
                                                                imageView_show2.setImageResource(R.drawable.c43);
                                                                break;
                                                            case 4:
                                                                imageView_show2.setImageResource(R.drawable.c44);
                                                                break;
                                                            case 5:
                                                                imageView_show2.setImageResource(R.drawable.c45);
                                                                break;
                                                            case 6:
                                                                imageView_show2.setImageResource(R.drawable.c46);
                                                                break;
                                                            case 7:
                                                                imageView_show2.setImageResource(R.drawable.c47);
                                                                break;
                                                            case 8:
                                                                imageView_show2.setImageResource(R.drawable.c48);
                                                                break;
                                                            case 9:
                                                                imageView_show2.setImageResource(R.drawable.c49);
                                                                break;
                                                            case 10:
                                                                imageView_show2.setImageResource(R.drawable.c50);
                                                                break;
                                                            case 11:
                                                                imageView_show2.setImageResource(R.drawable.c51);
                                                                break;
                                                            case 12:
                                                                imageView_show2.setImageResource(R.drawable.c52);
                                                                break;
                                                            case 13:
                                                                imageView_show2.setImageResource(R.drawable.c27);
                                                                break;
                                                            case 14:
                                                                imageView_show2.setImageResource(R.drawable.c28);
                                                                break;
                                                            case 15:
                                                                imageView_show2.setImageResource(R.drawable.c29);
                                                                break;
                                                            case 16:
                                                                imageView_show2.setImageResource(R.drawable.c30);
                                                                break;
                                                            case 17:
                                                                imageView_show2.setImageResource(R.drawable.c31);
                                                                break;
                                                            case 18:
                                                                imageView_show2.setImageResource(R.drawable.c32);
                                                                break;
                                                            case 19:
                                                                imageView_show2.setImageResource(R.drawable.c33);
                                                                break;
                                                            case 20:
                                                                imageView_show2.setImageResource(R.drawable.c34);
                                                                break;
                                                            case 21:
                                                                imageView_show2.setImageResource(R.drawable.c35);
                                                                break;
                                                            case 22:
                                                                imageView_show2.setImageResource(R.drawable.c36);
                                                                break;
                                                            case 23:
                                                                imageView_show2.setImageResource(R.drawable.c37);
                                                                break;
                                                            case 24:
                                                                imageView_show2.setImageResource(R.drawable.c38);
                                                                break;
                                                            case 25:
                                                                imageView_show2.setImageResource(R.drawable.c39);
                                                                break;
                                                            case 26:
                                                                imageView_show2.setImageResource(R.drawable.c14);
                                                                break;
                                                            case 27:
                                                                imageView_show2.setImageResource(R.drawable.c15);
                                                                break;
                                                            case 28:
                                                                imageView_show2.setImageResource(R.drawable.c16);
                                                                break;
                                                            case 29:
                                                                imageView_show2.setImageResource(R.drawable.c17);
                                                                break;
                                                            case 30:
                                                                imageView_show2.setImageResource(R.drawable.c18);
                                                                break;
                                                            case 31:
                                                                imageView_show2.setImageResource(R.drawable.c19);
                                                                break;
                                                            case 32:
                                                                imageView_show2.setImageResource(R.drawable.c20);
                                                                break;
                                                            case 33:
                                                                imageView_show2.setImageResource(R.drawable.c21);
                                                                break;
                                                            case 34:
                                                                imageView_show2.setImageResource(R.drawable.c22);
                                                                break;
                                                            case 35:
                                                                imageView_show2.setImageResource(R.drawable.c23);
                                                                break;
                                                            case 36:
                                                                imageView_show2.setImageResource(R.drawable.c24);
                                                                break;
                                                            case 37:
                                                                imageView_show2.setImageResource(R.drawable.c25);
                                                                break;
                                                            case 38:
                                                                imageView_show2.setImageResource(R.drawable.c26);
                                                                break;
                                                            case 39:
                                                                imageView_show2.setImageResource(R.drawable.c1);
                                                                break;
                                                            case 40:
                                                                imageView_show2.setImageResource(R.drawable.c2);
                                                                break;
                                                            case 41:
                                                                imageView_show2.setImageResource(R.drawable.c3);
                                                                break;
                                                            case 42:
                                                                imageView_show2.setImageResource(R.drawable.c4);
                                                                break;
                                                            case 43:
                                                                imageView_show2.setImageResource(R.drawable.c5);
                                                                break;
                                                            case 44:
                                                                imageView_show2.setImageResource(R.drawable.c6);
                                                                break;
                                                            case 45:
                                                                imageView_show2.setImageResource(R.drawable.c7);
                                                                break;
                                                            case 46:
                                                                imageView_show2.setImageResource(R.drawable.c8);
                                                                break;
                                                            case 47:
                                                                imageView_show2.setImageResource(R.drawable.c9);
                                                                break;
                                                            case 48:
                                                                imageView_show2.setImageResource(R.drawable.c10);
                                                                break;
                                                            case 49:
                                                                imageView_show2.setImageResource(R.drawable.c11);
                                                                break;
                                                            case 50:
                                                                imageView_show2.setImageResource(R.drawable.c12);
                                                                break;
                                                            case 51:
                                                                imageView_show2.setImageResource(R.drawable.c13);
                                                                break;
                                                            default:
                                                                break;
                                                        }
                                                        break;
                                                    case 2:
                                                        switch (RobotCardID[i - 1][k]) {
                                                            case 0:
                                                                imageView_show3.setImageResource(R.drawable.c40);
                                                                break;
                                                            case 1:
                                                                imageView_show3.setImageResource(R.drawable.c41);
                                                                break;
                                                            case 2:
                                                                imageView_show3.setImageResource(R.drawable.c42);
                                                                break;
                                                            case 3:
                                                                imageView_show3.setImageResource(R.drawable.c43);
                                                                break;
                                                            case 4:
                                                                imageView_show3.setImageResource(R.drawable.c44);
                                                                break;
                                                            case 5:
                                                                imageView_show3.setImageResource(R.drawable.c45);
                                                                break;
                                                            case 6:
                                                                imageView_show3.setImageResource(R.drawable.c46);
                                                                break;
                                                            case 7:
                                                                imageView_show3.setImageResource(R.drawable.c47);
                                                                break;
                                                            case 8:
                                                                imageView_show3.setImageResource(R.drawable.c48);
                                                                break;
                                                            case 9:
                                                                imageView_show3.setImageResource(R.drawable.c49);
                                                                break;
                                                            case 10:
                                                                imageView_show3.setImageResource(R.drawable.c50);
                                                                break;
                                                            case 11:
                                                                imageView_show3.setImageResource(R.drawable.c51);
                                                                break;
                                                            case 12:
                                                                imageView_show3.setImageResource(R.drawable.c52);
                                                                break;
                                                            case 13:
                                                                imageView_show3.setImageResource(R.drawable.c27);
                                                                break;
                                                            case 14:
                                                                imageView_show3.setImageResource(R.drawable.c28);
                                                                break;
                                                            case 15:
                                                                imageView_show3.setImageResource(R.drawable.c29);
                                                                break;
                                                            case 16:
                                                                imageView_show3.setImageResource(R.drawable.c30);
                                                                break;
                                                            case 17:
                                                                imageView_show3.setImageResource(R.drawable.c31);
                                                                break;
                                                            case 18:
                                                                imageView_show3.setImageResource(R.drawable.c32);
                                                                break;
                                                            case 19:
                                                                imageView_show3.setImageResource(R.drawable.c33);
                                                                break;
                                                            case 20:
                                                                imageView_show3.setImageResource(R.drawable.c34);
                                                                break;
                                                            case 21:
                                                                imageView_show3.setImageResource(R.drawable.c35);
                                                                break;
                                                            case 22:
                                                                imageView_show3.setImageResource(R.drawable.c36);
                                                                break;
                                                            case 23:
                                                                imageView_show3.setImageResource(R.drawable.c37);
                                                                break;
                                                            case 24:
                                                                imageView_show3.setImageResource(R.drawable.c38);
                                                                break;
                                                            case 25:
                                                                imageView_show3.setImageResource(R.drawable.c39);
                                                                break;
                                                            case 26:
                                                                imageView_show3.setImageResource(R.drawable.c14);
                                                                break;
                                                            case 27:
                                                                imageView_show3.setImageResource(R.drawable.c15);
                                                                break;
                                                            case 28:
                                                                imageView_show3.setImageResource(R.drawable.c16);
                                                                break;
                                                            case 29:
                                                                imageView_show3.setImageResource(R.drawable.c17);
                                                                break;
                                                            case 30:
                                                                imageView_show3.setImageResource(R.drawable.c18);
                                                                break;
                                                            case 31:
                                                                imageView_show3.setImageResource(R.drawable.c19);
                                                                break;
                                                            case 32:
                                                                imageView_show3.setImageResource(R.drawable.c20);
                                                                break;
                                                            case 33:
                                                                imageView_show3.setImageResource(R.drawable.c21);
                                                                break;
                                                            case 34:
                                                                imageView_show3.setImageResource(R.drawable.c22);
                                                                break;
                                                            case 35:
                                                                imageView_show3.setImageResource(R.drawable.c23);
                                                                break;
                                                            case 36:
                                                                imageView_show3.setImageResource(R.drawable.c24);
                                                                break;
                                                            case 37:
                                                                imageView_show3.setImageResource(R.drawable.c25);
                                                                break;
                                                            case 38:
                                                                imageView_show3.setImageResource(R.drawable.c26);
                                                                break;
                                                            case 39:
                                                                imageView_show3.setImageResource(R.drawable.c1);
                                                                break;
                                                            case 40:
                                                                imageView_show3.setImageResource(R.drawable.c2);
                                                                break;
                                                            case 41:
                                                                imageView_show3.setImageResource(R.drawable.c3);
                                                                break;
                                                            case 42:
                                                                imageView_show3.setImageResource(R.drawable.c4);
                                                                break;
                                                            case 43:
                                                                imageView_show3.setImageResource(R.drawable.c5);
                                                                break;
                                                            case 44:
                                                                imageView_show3.setImageResource(R.drawable.c6);
                                                                break;
                                                            case 45:
                                                                imageView_show3.setImageResource(R.drawable.c7);
                                                                break;
                                                            case 46:
                                                                imageView_show3.setImageResource(R.drawable.c8);
                                                                break;
                                                            case 47:
                                                                imageView_show3.setImageResource(R.drawable.c9);
                                                                break;
                                                            case 48:
                                                                imageView_show3.setImageResource(R.drawable.c10);
                                                                break;
                                                            case 49:
                                                                imageView_show3.setImageResource(R.drawable.c11);
                                                                break;
                                                            case 50:
                                                                imageView_show3.setImageResource(R.drawable.c12);
                                                                break;
                                                            case 51:
                                                                imageView_show3.setImageResource(R.drawable.c13);
                                                                break;
                                                            default:
                                                                break;
                                                        }
                                                        break;
                                                    case 3:
                                                        switch (RobotCardID[i - 1][k]) {
                                                            case 0:
                                                                imageView_show4.setImageResource(R.drawable.c40);
                                                                break;
                                                            case 1:
                                                                imageView_show4.setImageResource(R.drawable.c41);
                                                                break;
                                                            case 2:
                                                                imageView_show4.setImageResource(R.drawable.c42);
                                                                break;
                                                            case 3:
                                                                imageView_show4.setImageResource(R.drawable.c43);
                                                                break;
                                                            case 4:
                                                                imageView_show4.setImageResource(R.drawable.c44);
                                                                break;
                                                            case 5:
                                                                imageView_show4.setImageResource(R.drawable.c45);
                                                                break;
                                                            case 6:
                                                                imageView_show4.setImageResource(R.drawable.c46);
                                                                break;
                                                            case 7:
                                                                imageView_show4.setImageResource(R.drawable.c47);
                                                                break;
                                                            case 8:
                                                                imageView_show4.setImageResource(R.drawable.c48);
                                                                break;
                                                            case 9:
                                                                imageView_show4.setImageResource(R.drawable.c49);
                                                                break;
                                                            case 10:
                                                                imageView_show4.setImageResource(R.drawable.c50);
                                                                break;
                                                            case 11:
                                                                imageView_show4.setImageResource(R.drawable.c51);
                                                                break;
                                                            case 12:
                                                                imageView_show4.setImageResource(R.drawable.c52);
                                                                break;
                                                            case 13:
                                                                imageView_show4.setImageResource(R.drawable.c27);
                                                                break;
                                                            case 14:
                                                                imageView_show4.setImageResource(R.drawable.c28);
                                                                break;
                                                            case 15:
                                                                imageView_show4.setImageResource(R.drawable.c29);
                                                                break;
                                                            case 16:
                                                                imageView_show4.setImageResource(R.drawable.c30);
                                                                break;
                                                            case 17:
                                                                imageView_show4.setImageResource(R.drawable.c31);
                                                                break;
                                                            case 18:
                                                                imageView_show4.setImageResource(R.drawable.c32);
                                                                break;
                                                            case 19:
                                                                imageView_show4.setImageResource(R.drawable.c33);
                                                                break;
                                                            case 20:
                                                                imageView_show4.setImageResource(R.drawable.c34);
                                                                break;
                                                            case 21:
                                                                imageView_show4.setImageResource(R.drawable.c35);
                                                                break;
                                                            case 22:
                                                                imageView_show4.setImageResource(R.drawable.c36);
                                                                break;
                                                            case 23:
                                                                imageView_show4.setImageResource(R.drawable.c37);
                                                                break;
                                                            case 24:
                                                                imageView_show4.setImageResource(R.drawable.c38);
                                                                break;
                                                            case 25:
                                                                imageView_show4.setImageResource(R.drawable.c39);
                                                                break;
                                                            case 26:
                                                                imageView_show4.setImageResource(R.drawable.c14);
                                                                break;
                                                            case 27:
                                                                imageView_show4.setImageResource(R.drawable.c15);
                                                                break;
                                                            case 28:
                                                                imageView_show4.setImageResource(R.drawable.c16);
                                                                break;
                                                            case 29:
                                                                imageView_show4.setImageResource(R.drawable.c17);
                                                                break;
                                                            case 30:
                                                                imageView_show4.setImageResource(R.drawable.c18);
                                                                break;
                                                            case 31:
                                                                imageView_show4.setImageResource(R.drawable.c19);
                                                                break;
                                                            case 32:
                                                                imageView_show4.setImageResource(R.drawable.c20);
                                                                break;
                                                            case 33:
                                                                imageView_show4.setImageResource(R.drawable.c21);
                                                                break;
                                                            case 34:
                                                                imageView_show4.setImageResource(R.drawable.c22);
                                                                break;
                                                            case 35:
                                                                imageView_show4.setImageResource(R.drawable.c23);
                                                                break;
                                                            case 36:
                                                                imageView_show4.setImageResource(R.drawable.c24);
                                                                break;
                                                            case 37:
                                                                imageView_show4.setImageResource(R.drawable.c25);
                                                                break;
                                                            case 38:
                                                                imageView_show4.setImageResource(R.drawable.c26);
                                                                break;
                                                            case 39:
                                                                imageView_show4.setImageResource(R.drawable.c1);
                                                                break;
                                                            case 40:
                                                                imageView_show4.setImageResource(R.drawable.c2);
                                                                break;
                                                            case 41:
                                                                imageView_show4.setImageResource(R.drawable.c3);
                                                                break;
                                                            case 42:
                                                                imageView_show4.setImageResource(R.drawable.c4);
                                                                break;
                                                            case 43:
                                                                imageView_show4.setImageResource(R.drawable.c5);
                                                                break;
                                                            case 44:
                                                                imageView_show4.setImageResource(R.drawable.c6);
                                                                break;
                                                            case 45:
                                                                imageView_show4.setImageResource(R.drawable.c7);
                                                                break;
                                                            case 46:
                                                                imageView_show4.setImageResource(R.drawable.c8);
                                                                break;
                                                            case 47:
                                                                imageView_show4.setImageResource(R.drawable.c9);
                                                                break;
                                                            case 48:
                                                                imageView_show4.setImageResource(R.drawable.c10);
                                                                break;
                                                            case 49:
                                                                imageView_show4.setImageResource(R.drawable.c11);
                                                                break;
                                                            case 50:
                                                                imageView_show4.setImageResource(R.drawable.c12);
                                                                break;
                                                            case 51:
                                                                imageView_show4.setImageResource(R.drawable.c13);
                                                                break;
                                                            default:
                                                                break;
                                                        }
                                                        break;
                                                    case 4:
                                                        switch (RobotCardID[i - 1][k]) {
                                                            case 0:
                                                                imageView_show5.setImageResource(R.drawable.c40);
                                                                break;
                                                            case 1:
                                                                imageView_show5.setImageResource(R.drawable.c41);
                                                                break;
                                                            case 2:
                                                                imageView_show5.setImageResource(R.drawable.c42);
                                                                break;
                                                            case 3:
                                                                imageView_show5.setImageResource(R.drawable.c43);
                                                                break;
                                                            case 4:
                                                                imageView_show5.setImageResource(R.drawable.c44);
                                                                break;
                                                            case 5:
                                                                imageView_show5.setImageResource(R.drawable.c45);
                                                                break;
                                                            case 6:
                                                                imageView_show5.setImageResource(R.drawable.c46);
                                                                break;
                                                            case 7:
                                                                imageView_show5.setImageResource(R.drawable.c47);
                                                                break;
                                                            case 8:
                                                                imageView_show5.setImageResource(R.drawable.c48);
                                                                break;
                                                            case 9:
                                                                imageView_show5.setImageResource(R.drawable.c49);
                                                                break;
                                                            case 10:
                                                                imageView_show5.setImageResource(R.drawable.c50);
                                                                break;
                                                            case 11:
                                                                imageView_show5.setImageResource(R.drawable.c51);
                                                                break;
                                                            case 12:
                                                                imageView_show5.setImageResource(R.drawable.c52);
                                                                break;
                                                            case 13:
                                                                imageView_show5.setImageResource(R.drawable.c27);
                                                                break;
                                                            case 14:
                                                                imageView_show5.setImageResource(R.drawable.c28);
                                                                break;
                                                            case 15:
                                                                imageView_show5.setImageResource(R.drawable.c29);
                                                                break;
                                                            case 16:
                                                                imageView_show5.setImageResource(R.drawable.c30);
                                                                break;
                                                            case 17:
                                                                imageView_show5.setImageResource(R.drawable.c31);
                                                                break;
                                                            case 18:
                                                                imageView_show5.setImageResource(R.drawable.c32);
                                                                break;
                                                            case 19:
                                                                imageView_show5.setImageResource(R.drawable.c33);
                                                                break;
                                                            case 20:
                                                                imageView_show5.setImageResource(R.drawable.c34);
                                                                break;
                                                            case 21:
                                                                imageView_show5.setImageResource(R.drawable.c35);
                                                                break;
                                                            case 22:
                                                                imageView_show5.setImageResource(R.drawable.c36);
                                                                break;
                                                            case 23:
                                                                imageView_show5.setImageResource(R.drawable.c37);
                                                                break;
                                                            case 24:
                                                                imageView_show5.setImageResource(R.drawable.c38);
                                                                break;
                                                            case 25:
                                                                imageView_show5.setImageResource(R.drawable.c39);
                                                                break;
                                                            case 26:
                                                                imageView_show5.setImageResource(R.drawable.c14);
                                                                break;
                                                            case 27:
                                                                imageView_show5.setImageResource(R.drawable.c15);
                                                                break;
                                                            case 28:
                                                                imageView_show5.setImageResource(R.drawable.c16);
                                                                break;
                                                            case 29:
                                                                imageView_show5.setImageResource(R.drawable.c17);
                                                                break;
                                                            case 30:
                                                                imageView_show5.setImageResource(R.drawable.c18);
                                                                break;
                                                            case 31:
                                                                imageView_show5.setImageResource(R.drawable.c19);
                                                                break;
                                                            case 32:
                                                                imageView_show5.setImageResource(R.drawable.c20);
                                                                break;
                                                            case 33:
                                                                imageView_show5.setImageResource(R.drawable.c21);
                                                                break;
                                                            case 34:
                                                                imageView_show5.setImageResource(R.drawable.c22);
                                                                break;
                                                            case 35:
                                                                imageView_show5.setImageResource(R.drawable.c23);
                                                                break;
                                                            case 36:
                                                                imageView_show5.setImageResource(R.drawable.c24);
                                                                break;
                                                            case 37:
                                                                imageView_show5.setImageResource(R.drawable.c25);
                                                                break;
                                                            case 38:
                                                                imageView_show5.setImageResource(R.drawable.c26);
                                                                break;
                                                            case 39:
                                                                imageView_show5.setImageResource(R.drawable.c1);
                                                                break;
                                                            case 40:
                                                                imageView_show5.setImageResource(R.drawable.c2);
                                                                break;
                                                            case 41:
                                                                imageView_show5.setImageResource(R.drawable.c3);
                                                                break;
                                                            case 42:
                                                                imageView_show5.setImageResource(R.drawable.c4);
                                                                break;
                                                            case 43:
                                                                imageView_show5.setImageResource(R.drawable.c5);
                                                                break;
                                                            case 44:
                                                                imageView_show5.setImageResource(R.drawable.c6);
                                                                break;
                                                            case 45:
                                                                imageView_show5.setImageResource(R.drawable.c7);
                                                                break;
                                                            case 46:
                                                                imageView_show5.setImageResource(R.drawable.c8);
                                                                break;
                                                            case 47:
                                                                imageView_show5.setImageResource(R.drawable.c9);
                                                                break;
                                                            case 48:
                                                                imageView_show5.setImageResource(R.drawable.c10);
                                                                break;
                                                            case 49:
                                                                imageView_show5.setImageResource(R.drawable.c11);
                                                                break;
                                                            case 50:
                                                                imageView_show5.setImageResource(R.drawable.c12);
                                                                break;
                                                            case 51:
                                                                imageView_show5.setImageResource(R.drawable.c13);
                                                                break;
                                                            default:
                                                                break;
                                                        }
                                                    default:
                                                        break;
                                                }
                                            }
                                        }
                                    }
                                    try{
                                        Thread.sleep(3000);
                                    }catch(Exception e){
                                        System.exit(0);//退出程序
                                    }
                                }
                            }

                         */
                    }
                    break;
                case R.id.imageButton_show:
                    if(Ruler.getHumanPlayerArray()[0].getPlayerState()==PlayerState.PlayerRound&&!Ruler.getHumanPlayerArray()[0].getCardsToShow().isEmpty()){
                        if(Ruler.CanPlayerShowCard(Ruler.getHumanPlayerArray()[0])){
                            Log.d("s6","hahah");
                            imageView_show1.setImageDrawable(null);
                            imageView_show2.setImageDrawable(null);
                            imageView_show3.setImageDrawable(null);
                            imageView_show4.setImageDrawable(null);
                            imageView_show5.setImageDrawable(null);
                            imageView_robot3_pass.setImageDrawable(null);
                            for (int i=0;i<Ruler.getHumanPlayerArray()[0].getCardsToShow().size();i++){
                                for(int j=0;j<cardID.length;j++){
                                    if(Ruler.getHumanPlayerArray()[0].getCardsToShow().get(i).getCardId()==cardID[j]){
                                        switch (j){
                                            case 0:
                                                image1.setVisibility(View.INVISIBLE);
                                                break;
                 //可能会引起空指针，需将GPNE改为INVISIBLE
                                            case 1:
                                                image2.setVisibility(View.GONE);
                                                break;
                                            case 2:
                                                image3.setVisibility(View.GONE);
                                                break;
                                            case 3:
                                                image4.setVisibility(View.GONE);
                                                break;
                                            case 4:
                                                image5.setVisibility(View.GONE);
                                                break;
                                            case 5:
                                                image6.setVisibility(View.GONE);
                                                break;
                                            case 6:
                                                image7.setVisibility(View.GONE);
                                                break;
                                            case 7:
                                                image8.setVisibility(View.GONE);
                                                break;
                                            case 8:
                                                image9.setVisibility(View.GONE);
                                                break;
                                            case 9:
                                                image10.setVisibility(View.GONE);
                                                break;
                                            case 10:
                                                image11.setVisibility(View.GONE);
                                                break;
                                            case 11:
                                                image12.setVisibility(View.GONE);
                                                break;
                                            case 12:
                                                image13.setVisibility(View.GONE);
                                                break;
                                            default:
                                                break;
                                        }
                                        break;
                                    }
                                }
                            }
                            Ruler.getHumanPlayerArray()[0].showCards();

                            for (int t=0;t<Ruler.getCardsShowedByPreviousPlayer().size();t++){
                                for (int k=0;k<cardID.length;k++){
                                    if (Ruler.getCardsShowedByPreviousPlayer().get(t).getCardId()==cardID[k]){
                                        switch (t){
                                            case 0:
                                                switch (k){
                                                    case 0:
                                                        imageView_show1.setImageDrawable(image1.getDrawable());
                                                        break;
                                                    case 1:
                                                        imageView_show1.setImageDrawable(image2.getDrawable());
                                                        break;
                                                    case 2:
                                                        imageView_show1.setImageDrawable(image3.getDrawable());
                                                        break;
                                                    case 3:
                                                        imageView_show1.setImageDrawable(image4.getDrawable());
                                                        break;
                                                    case 4:
                                                        imageView_show1.setImageDrawable(image5.getDrawable());
                                                        break;
                                                    case 5:
                                                        imageView_show1.setImageDrawable(image6.getDrawable());
                                                        break;
                                                    case 6:
                                                        imageView_show1.setImageDrawable(image7.getDrawable());
                                                        break;
                                                    case 7:
                                                        imageView_show1.setImageDrawable(image8.getDrawable());
                                                        break;
                                                    case 8:
                                                        imageView_show1.setImageDrawable(image9.getDrawable());
                                                        break;
                                                    case 9:
                                                        imageView_show1.setImageDrawable(image10.getDrawable());
                                                        break;
                                                    case 10:
                                                        imageView_show1.setImageDrawable(image11.getDrawable());
                                                        break;
                                                    case 11:
                                                        imageView_show1.setImageDrawable(image12.getDrawable());
                                                        break;
                                                    case 12:
                                                        imageView_show1.setImageDrawable(image13.getDrawable());
                                                        break;
                                                    default:
                                                        break;
                                                }
                                                break;
                                            case 1:
                                                switch (k) {
                                                    case 0:
                                                        imageView_show2.setImageDrawable(image1.getDrawable());
                                                        break;
                                                    case 1:
                                                        imageView_show2.setImageDrawable(image2.getDrawable());
                                                        break;
                                                    case 2:
                                                        imageView_show2.setImageDrawable(image3.getDrawable());
                                                        break;
                                                    case 3:
                                                        imageView_show2.setImageDrawable(image4.getDrawable());
                                                        break;
                                                    case 4:
                                                        imageView_show2.setImageDrawable(image5.getDrawable());
                                                        break;
                                                    case 5:
                                                        imageView_show2.setImageDrawable(image6.getDrawable());
                                                        break;
                                                    case 6:
                                                        imageView_show2.setImageDrawable(image7.getDrawable());
                                                        break;
                                                    case 7:
                                                        imageView_show2.setImageDrawable(image8.getDrawable());
                                                        break;
                                                    case 8:
                                                        imageView_show2.setImageDrawable(image9.getDrawable());
                                                        break;
                                                    case 9:
                                                        imageView_show2.setImageDrawable(image10.getDrawable());
                                                        break;
                                                    case 10:
                                                        imageView_show2.setImageDrawable(image11.getDrawable());
                                                        break;
                                                    case 11:
                                                        imageView_show2.setImageDrawable(image12.getDrawable());
                                                        break;
                                                    case 12:
                                                        imageView_show2.setImageDrawable(image13.getDrawable());
                                                        break;
                                                    default:
                                                        break;
                                                }
                                                break;
                                            case 2:
                                                switch (k) {
                                                    case 0:
                                                        imageView_show3.setImageDrawable(image1.getDrawable());
                                                        break;
                                                    case 1:
                                                        imageView_show3.setImageDrawable(image2.getDrawable());
                                                        break;
                                                    case 2:
                                                        imageView_show3.setImageDrawable(image3.getDrawable());
                                                        break;
                                                    case 3:
                                                        imageView_show3.setImageDrawable(image4.getDrawable());
                                                        break;
                                                    case 4:
                                                        imageView_show3.setImageDrawable(image5.getDrawable());
                                                        break;
                                                    case 5:
                                                        imageView_show3.setImageDrawable(image6.getDrawable());
                                                        break;
                                                    case 6:
                                                        imageView_show3.setImageDrawable(image7.getDrawable());
                                                        break;
                                                    case 7:
                                                        imageView_show3.setImageDrawable(image8.getDrawable());
                                                        break;
                                                    case 8:
                                                        imageView_show3.setImageDrawable(image9.getDrawable());
                                                        break;
                                                    case 9:
                                                        imageView_show3.setImageDrawable(image10.getDrawable());
                                                        break;
                                                    case 10:
                                                        imageView_show3.setImageDrawable(image11.getDrawable());
                                                        break;
                                                    case 11:
                                                        imageView_show3.setImageDrawable(image12.getDrawable());
                                                        break;
                                                    case 12:
                                                        imageView_show3.setImageDrawable(image13.getDrawable());
                                                        break;
                                                    default:
                                                        break;
                                                }
                                                break;
                                            case 3:
                                                switch (k) {
                                                    case 0:
                                                        imageView_show4.setImageDrawable(image1.getDrawable());
                                                        break;
                                                    case 1:
                                                        imageView_show4.setImageDrawable(image2.getDrawable());
                                                        break;
                                                    case 2:
                                                        imageView_show4.setImageDrawable(image3.getDrawable());
                                                        break;
                                                    case 3:
                                                        imageView_show4.setImageDrawable(image4.getDrawable());
                                                        break;
                                                    case 4:
                                                        imageView_show4.setImageDrawable(image5.getDrawable());
                                                        break;
                                                    case 5:
                                                        imageView_show4.setImageDrawable(image6.getDrawable());
                                                        break;
                                                    case 6:
                                                        imageView_show4.setImageDrawable(image7.getDrawable());
                                                        break;
                                                    case 7:
                                                        imageView_show4.setImageDrawable(image8.getDrawable());
                                                        break;
                                                    case 8:
                                                        imageView_show4.setImageDrawable(image9.getDrawable());
                                                        break;
                                                    case 9:
                                                        imageView_show4.setImageDrawable(image10.getDrawable());
                                                        break;
                                                    case 10:
                                                        imageView_show4.setImageDrawable(image11.getDrawable());
                                                        break;
                                                    case 11:
                                                        imageView_show4.setImageDrawable(image12.getDrawable());
                                                        break;
                                                    case 12:
                                                        imageView_show4.setImageDrawable(image13.getDrawable());
                                                        break;
                                                    default:
                                                        break;
                                                }
                                                break;
                                            case 4:
                                                switch (k) {
                                                    case 0:
                                                        imageView_show5.setImageDrawable(image1.getDrawable());
                                                        break;
                                                    case 1:
                                                        imageView_show5.setImageDrawable(image2.getDrawable());
                                                        break;
                                                    case 2:
                                                        imageView_show5.setImageDrawable(image3.getDrawable());
                                                        break;
                                                    case 3:
                                                        imageView_show5.setImageDrawable(image4.getDrawable());
                                                        break;
                                                    case 4:
                                                        imageView_show5.setImageDrawable(image5.getDrawable());
                                                        break;
                                                    case 5:
                                                        imageView_show5.setImageDrawable(image6.getDrawable());
                                                        break;
                                                    case 6:
                                                        imageView_show5.setImageDrawable(image7.getDrawable());
                                                        break;
                                                    case 7:
                                                        imageView_show5.setImageDrawable(image8.getDrawable());
                                                        break;
                                                    case 8:
                                                        imageView_show5.setImageDrawable(image9.getDrawable());
                                                        break;
                                                    case 9:
                                                        imageView_show5.setImageDrawable(image10.getDrawable());
                                                        break;
                                                    case 10:
                                                        imageView_show5.setImageDrawable(image11.getDrawable());
                                                        break;
                                                    case 11:
                                                        imageView_show5.setImageDrawable(image12.getDrawable());
                                                        break;
                                                    case 12:
                                                        imageView_show5.setImageDrawable(image13.getDrawable());
                                                        break;
                                                    default:
                                                        break;
                                                }
                                                break;
                                            default:
                                                break;
                                        }
                                        break;
                                    }

                                }
                            }
                            if(Ruler.WillWin(Ruler.getHumanPlayerArray()[0])){
                                Toast toast=Toast.makeText(PlayWithRobot.this,"你已经获胜",Toast.LENGTH_SHORT);
                                toast.show();
                                ruler.caculateScore();
                                exit = false;
                                AlertDialog.Builder builder=new Builder(PlayWithRobot.this);
                                //builder.setIcon(R.drawable.ic_launcher);//设置图标
                                builder.setTitle("您获得了胜利！");//设置对话框的标题
                                builder.setMessage("你的分数是:"+Ruler.getHumanPlayerArray()[0].getScore());//设置对话框的内容
                                builder.setPositiveButton("返回主菜单", new OnClickListener() {  //返回主菜单
                                    @Override
                                    public void onClick(DialogInterface arg0, int arg1) {
                                        Intent intent = new Intent(PlayWithRobot.this, MainActivity.class);
                                        startActivity(intent);
                                    }
                                });

                                builder.setNegativeButton("下一局游戏", new OnClickListener() {  //下一局
                                    @Override
                                    public void onClick(DialogInterface arg0, int arg1) {
                                        Intent intent = getIntent();
                                        finish();
                                        startActivity(intent);
                                    }
                                });
                                AlertDialog a=builder.create();
                                a.show();  //必须show一下才能看到对话框，跟Toast一样的道理
                            }
                            else{
                                Ruler.toTheTurnOfNextPlayer(Ruler.getHumanPlayerArray()[0]);
                                Ruler.setIdOfPlayerWhoShowCardSucessfully(Ruler.getHumanPlayerArray()[0].getPlayerId());
                                long id=Thread.currentThread().hashCode();
                                Log.d("s3",String.valueOf(id));
                            }
                        }
                    }
                    break;
                case R.id.Button_ready:
                    button_ready.setVisibility(View.INVISIBLE);
                    imageButton_pass.setImageResource(R.drawable.pass);
                    imageButton_showcard.setImageResource(R.drawable.show);
                    //机器人出牌
                    boolean isFirst = true;
                    for(int i=0;i<Ruler.getPlayerArray().length;i++) {
                        int temp=0;
                        if (i == 0 && Ruler.getPlayerArray()[i].getPlayerState() == PlayerState.PlayerRound) {
                            break;
                        }
                        else if (Ruler.getPlayerArray()[i].getPlayerState() == PlayerState.PlayerRound) {
                            if (isFirst) {
                                Ruler.getPlayerArray()[i].RobotFirstShow();
                                Ruler.toTheTurnOfNextPlayer(Ruler.getPlayerArray()[i]);
                                isFirst = false;
                                int a = 0;
                                switch (i) {
                                    case 1:
                                        a = Integer.valueOf(CardNumber1.getText().toString()) - 1;
                                        CardNumber1.setText(String.valueOf(a));
                                        imageView_robot1_show1.setImageResource(R.drawable.c3);
                                        break;
                                    case 2:
                                        a = Integer.valueOf(CardNumber2.getText().toString()) - 1;
                                        CardNumber2.setText(String.valueOf(a));
                                        imageView_robot2_show1.setImageResource(R.drawable.c3);
                                        break;
                                    case 3:
                                        a = Integer.valueOf(CardNumber3.getText().toString()) - 1;
                                        CardNumber3.setText(String.valueOf(a));
                                        imageView_robot3_show1.setImageResource(R.drawable.c3);
                                        break;
                                    default:
                                        break;
                                }
                            }
                            else {
                                Ruler.getPlayerArray()[i].RobotShowCard();

                                Ruler.getPlayerArray()[i].showCards();
                                //没有CardToshow,更新了previous
                                temp=Ruler.getCardsShowedByPreviousPlayer().size();
//                    Ruler.setTypeOfCards(Ruler.getCardsShowedByPreviousPlayer());
                                Ruler.toTheTurnOfNextPlayer(Ruler.getPlayerArray()[i]);
                                switch (i){
                                    case 1:
                                        temp = Integer.valueOf(CardNumber1.getText().toString()) - temp;
                                        CardNumber1.setText(String.valueOf(temp));
                                        for (int k = 0; k < cardID.length; k++) {
                                            if (Ruler.getCardsShowedByPreviousPlayer().get(0).getCardId() == RobotCardID[i - 1][k]) {
                                                switch (RobotCardID[i - 1][k]) {
                                                    case 0:
                                                        imageView_robot1_show1.setImageResource(R.drawable.c40);
                                                        break;
                                                    case 1:
                                                        imageView_robot1_show1.setImageResource(R.drawable.c41);
                                                        break;
                                                    case 2:
                                                        imageView_robot1_show1.setImageResource(R.drawable.c42);
                                                        break;
                                                    case 3:
                                                        imageView_robot1_show1.setImageResource(R.drawable.c43);
                                                        break;
                                                    case 4:
                                                        imageView_robot1_show1.setImageResource(R.drawable.c44);
                                                        break;
                                                    case 5:
                                                        imageView_robot1_show1.setImageResource(R.drawable.c45);
                                                        break;
                                                    case 6:
                                                        imageView_robot1_show1.setImageResource(R.drawable.c46);
                                                        break;
                                                    case 7:
                                                        imageView_robot1_show1.setImageResource(R.drawable.c47);
                                                        break;
                                                    case 8:
                                                        imageView_robot1_show1.setImageResource(R.drawable.c48);
                                                        break;
                                                    case 9:
                                                        imageView_robot1_show1.setImageResource(R.drawable.c49);
                                                        break;
                                                    case 10:
                                                        imageView_robot1_show1.setImageResource(R.drawable.c50);
                                                        break;
                                                    case 11:
                                                        imageView_robot1_show1.setImageResource(R.drawable.c51);
                                                        break;
                                                    case 12:
                                                        imageView_robot1_show1.setImageResource(R.drawable.c52);
                                                        break;
                                                    case 13:
                                                        imageView_robot1_show1.setImageResource(R.drawable.c27);
                                                        break;
                                                    case 14:
                                                        imageView_robot1_show1.setImageResource(R.drawable.c28);
                                                        break;
                                                    case 15:
                                                        imageView_robot1_show1.setImageResource(R.drawable.c29);
                                                        break;
                                                    case 16:
                                                        imageView_robot1_show1.setImageResource(R.drawable.c30);
                                                        break;
                                                    case 17:
                                                        imageView_robot1_show1.setImageResource(R.drawable.c31);
                                                        break;
                                                    case 18:
                                                        imageView_robot1_show1.setImageResource(R.drawable.c32);
                                                        break;
                                                    case 19:
                                                        imageView_robot1_show1.setImageResource(R.drawable.c33);
                                                        break;
                                                    case 20:
                                                        imageView_robot1_show1.setImageResource(R.drawable.c34);
                                                        break;
                                                    case 21:
                                                        imageView_robot1_show1.setImageResource(R.drawable.c35);
                                                        break;
                                                    case 22:
                                                        imageView_robot1_show1.setImageResource(R.drawable.c36);
                                                        break;
                                                    case 23:
                                                        imageView_robot1_show1.setImageResource(R.drawable.c37);
                                                        break;
                                                    case 24:
                                                        imageView_robot1_show1.setImageResource(R.drawable.c38);
                                                        break;
                                                    case 25:
                                                        imageView_robot1_show1.setImageResource(R.drawable.c39);
                                                        break;
                                                    case 26:
                                                        imageView_robot1_show1.setImageResource(R.drawable.c14);
                                                        break;
                                                    case 27:
                                                        imageView_robot1_show1.setImageResource(R.drawable.c15);
                                                        break;
                                                    case 28:
                                                        imageView_robot1_show1.setImageResource(R.drawable.c16);
                                                        break;
                                                    case 29:
                                                        imageView_robot1_show1.setImageResource(R.drawable.c17);
                                                        break;
                                                    case 30:
                                                        imageView_robot1_show1.setImageResource(R.drawable.c18);
                                                        break;
                                                    case 31:
                                                        imageView_robot1_show1.setImageResource(R.drawable.c19);
                                                        break;
                                                    case 32:
                                                        imageView_robot1_show1.setImageResource(R.drawable.c20);
                                                        break;
                                                    case 33:
                                                        imageView_robot1_show1.setImageResource(R.drawable.c21);
                                                        break;
                                                    case 34:
                                                        imageView_robot1_show1.setImageResource(R.drawable.c22);
                                                        break;
                                                    case 35:
                                                        imageView_robot1_show1.setImageResource(R.drawable.c23);
                                                        break;
                                                    case 36:
                                                        imageView_robot1_show1.setImageResource(R.drawable.c24);
                                                        break;
                                                    case 37:
                                                        imageView_robot1_show1.setImageResource(R.drawable.c25);
                                                        break;
                                                    case 38:
                                                        imageView_robot1_show1.setImageResource(R.drawable.c26);
                                                        break;
                                                    case 39:
                                                        imageView_robot1_show1.setImageResource(R.drawable.c1);
                                                        break;
                                                    case 40:
                                                        imageView_robot1_show1.setImageResource(R.drawable.c2);
                                                        break;
                                                    case 41:
                                                        imageView_robot1_show1.setImageResource(R.drawable.c3);
                                                        break;
                                                    case 42:
                                                        imageView_robot1_show1.setImageResource(R.drawable.c4);
                                                        break;
                                                    case 43:
                                                        imageView_robot1_show1.setImageResource(R.drawable.c5);
                                                        break;
                                                    case 44:
                                                        imageView_robot1_show1.setImageResource(R.drawable.c6);
                                                        break;
                                                    case 45:
                                                        imageView_robot1_show1.setImageResource(R.drawable.c7);
                                                        break;
                                                    case 46:
                                                        imageView_robot1_show1.setImageResource(R.drawable.c8);
                                                        break;
                                                    case 47:
                                                        imageView_robot1_show1.setImageResource(R.drawable.c9);
                                                        break;
                                                    case 48:
                                                        imageView_robot1_show1.setImageResource(R.drawable.c10);
                                                        break;
                                                    case 49:
                                                        imageView_robot1_show1.setImageResource(R.drawable.c11);
                                                        break;
                                                    case 50:
                                                        imageView_robot1_show1.setImageResource(R.drawable.c12);
                                                        break;
                                                    case 51:
                                                        imageView_robot1_show1.setImageResource(R.drawable.c13);
                                                        break;
                                                    default:
                                                        break;
                                                }
                                                break;
                                            }
                                        }
                                        break;
                                    case 2:
                                        imageView_robot1_pass.setImageDrawable(null);
                                        temp = Integer.valueOf(CardNumber2.getText().toString()) - temp;
                                        CardNumber2.setText(String.valueOf(temp));
                                        for (int k = 0; k < cardID.length; k++) {
                                            if (Ruler.getCardsShowedByPreviousPlayer().get(0).getCardId() == RobotCardID[i - 1][k]) {
                                                switch (RobotCardID[i - 1][k]) {
                                                    case 0:
                                                        imageView_robot2_show1.setImageResource(R.drawable.c40);
                                                        break;
                                                    case 1:
                                                        imageView_robot2_show1.setImageResource(R.drawable.c41);
                                                        break;
                                                    case 2:
                                                        imageView_robot2_show1.setImageResource(R.drawable.c42);
                                                        break;
                                                    case 3:
                                                        imageView_robot2_show1.setImageResource(R.drawable.c43);
                                                        break;
                                                    case 4:
                                                        imageView_robot2_show1.setImageResource(R.drawable.c44);
                                                        break;
                                                    case 5:
                                                        imageView_robot2_show1.setImageResource(R.drawable.c45);
                                                        break;
                                                    case 6:
                                                        imageView_robot2_show1.setImageResource(R.drawable.c46);
                                                        break;
                                                    case 7:
                                                        imageView_robot2_show1.setImageResource(R.drawable.c47);
                                                        break;
                                                    case 8:
                                                        imageView_robot2_show1.setImageResource(R.drawable.c48);
                                                        break;
                                                    case 9:
                                                        imageView_robot2_show1.setImageResource(R.drawable.c49);
                                                        break;
                                                    case 10:
                                                        imageView_robot2_show1.setImageResource(R.drawable.c50);
                                                        break;
                                                    case 11:
                                                        imageView_robot2_show1.setImageResource(R.drawable.c51);
                                                        break;
                                                    case 12:
                                                        imageView_robot2_show1.setImageResource(R.drawable.c52);
                                                        break;
                                                    case 13:
                                                        imageView_robot2_show1.setImageResource(R.drawable.c27);
                                                        break;
                                                    case 14:
                                                        imageView_robot2_show1.setImageResource(R.drawable.c28);
                                                        break;
                                                    case 15:
                                                        imageView_robot2_show1.setImageResource(R.drawable.c29);
                                                        break;
                                                    case 16:
                                                        imageView_robot2_show1.setImageResource(R.drawable.c30);
                                                        break;
                                                    case 17:
                                                        imageView_robot2_show1.setImageResource(R.drawable.c31);
                                                        break;
                                                    case 18:
                                                        imageView_robot2_show1.setImageResource(R.drawable.c32);
                                                        break;
                                                    case 19:
                                                        imageView_robot2_show1.setImageResource(R.drawable.c33);
                                                        break;
                                                    case 20:
                                                        imageView_robot2_show1.setImageResource(R.drawable.c34);
                                                        break;
                                                    case 21:
                                                        imageView_robot2_show1.setImageResource(R.drawable.c35);
                                                        break;
                                                    case 22:
                                                        imageView_robot2_show1.setImageResource(R.drawable.c36);
                                                        break;
                                                    case 23:
                                                        imageView_robot2_show1.setImageResource(R.drawable.c37);
                                                        break;
                                                    case 24:
                                                        imageView_robot2_show1.setImageResource(R.drawable.c38);
                                                        break;
                                                    case 25:
                                                        imageView_robot2_show1.setImageResource(R.drawable.c39);
                                                        break;
                                                    case 26:
                                                        imageView_robot2_show1.setImageResource(R.drawable.c14);
                                                        break;
                                                    case 27:
                                                        imageView_robot2_show1.setImageResource(R.drawable.c15);
                                                        break;
                                                    case 28:
                                                        imageView_robot2_show1.setImageResource(R.drawable.c16);
                                                        break;
                                                    case 29:
                                                        imageView_robot2_show1.setImageResource(R.drawable.c17);
                                                        break;
                                                    case 30:
                                                        imageView_robot2_show1.setImageResource(R.drawable.c18);
                                                        break;
                                                    case 31:
                                                        imageView_robot2_show1.setImageResource(R.drawable.c19);
                                                        break;
                                                    case 32:
                                                        imageView_robot2_show1.setImageResource(R.drawable.c20);
                                                        break;
                                                    case 33:
                                                        imageView_robot2_show1.setImageResource(R.drawable.c21);
                                                        break;
                                                    case 34:
                                                        imageView_robot2_show1.setImageResource(R.drawable.c22);
                                                        break;
                                                    case 35:
                                                        imageView_robot2_show1.setImageResource(R.drawable.c23);
                                                        break;
                                                    case 36:
                                                        imageView_robot2_show1.setImageResource(R.drawable.c24);
                                                        break;
                                                    case 37:
                                                        imageView_robot2_show1.setImageResource(R.drawable.c25);
                                                        break;
                                                    case 38:
                                                        imageView_robot2_show1.setImageResource(R.drawable.c26);
                                                        break;
                                                    case 39:
                                                        imageView_robot2_show1.setImageResource(R.drawable.c1);
                                                        break;
                                                    case 40:
                                                        imageView_robot2_show1.setImageResource(R.drawable.c2);
                                                        break;
                                                    case 41:
                                                        imageView_robot2_show1.setImageResource(R.drawable.c3);
                                                        break;
                                                    case 42:
                                                        imageView_robot2_show1.setImageResource(R.drawable.c4);
                                                        break;
                                                    case 43:
                                                        imageView_robot2_show1.setImageResource(R.drawable.c5);
                                                        break;
                                                    case 44:
                                                        imageView_robot2_show1.setImageResource(R.drawable.c6);
                                                        break;
                                                    case 45:
                                                        imageView_robot2_show1.setImageResource(R.drawable.c7);
                                                        break;
                                                    case 46:
                                                        imageView_robot2_show1.setImageResource(R.drawable.c8);
                                                        break;
                                                    case 47:
                                                        imageView_robot2_show1.setImageResource(R.drawable.c9);
                                                        break;
                                                    case 48:
                                                        imageView_robot2_show1.setImageResource(R.drawable.c10);
                                                        break;
                                                    case 49:
                                                        imageView_robot2_show1.setImageResource(R.drawable.c11);
                                                        break;
                                                    case 50:
                                                        imageView_robot2_show1.setImageResource(R.drawable.c12);
                                                        break;
                                                    case 51:
                                                        imageView_robot2_show1.setImageResource(R.drawable.c13);
                                                        break;
                                                    default:
                                                        break;
                                                }
                                                break;
                                            }
                                        }
                                        break;
                                    case 3:
                                        imageView_robot2_pass.setImageDrawable(null);
                                        temp = Integer.valueOf(CardNumber3.getText().toString()) - temp;
                                        CardNumber3.setText(String.valueOf(temp));
                                        for (int k = 0; k < cardID.length; k++) {
                                            if (Ruler.getCardsShowedByPreviousPlayer().get(0).getCardId() == RobotCardID[i - 1][k]) {
                                                switch (RobotCardID[i - 1][k]) {
                                                    case 0:
                                                        imageView_robot3_show1.setImageResource(R.drawable.c40);
                                                        break;
                                                    case 1:
                                                        imageView_robot3_show1.setImageResource(R.drawable.c41);
                                                        break;
                                                    case 2:
                                                        imageView_robot3_show1.setImageResource(R.drawable.c42);
                                                        break;
                                                    case 3:
                                                        imageView_robot3_show1.setImageResource(R.drawable.c43);
                                                        break;
                                                    case 4:
                                                        imageView_robot3_show1.setImageResource(R.drawable.c44);
                                                        break;
                                                    case 5:
                                                        imageView_robot3_show1.setImageResource(R.drawable.c45);
                                                        break;
                                                    case 6:
                                                        imageView_robot3_show1.setImageResource(R.drawable.c46);
                                                        break;
                                                    case 7:
                                                        imageView_robot3_show1.setImageResource(R.drawable.c47);
                                                        break;
                                                    case 8:
                                                        imageView_robot3_show1.setImageResource(R.drawable.c48);
                                                        break;
                                                    case 9:
                                                        imageView_robot3_show1.setImageResource(R.drawable.c49);
                                                        break;
                                                    case 10:
                                                        imageView_robot3_show1.setImageResource(R.drawable.c50);
                                                        break;
                                                    case 11:
                                                        imageView_robot3_show1.setImageResource(R.drawable.c51);
                                                        break;
                                                    case 12:
                                                        imageView_robot3_show1.setImageResource(R.drawable.c52);
                                                        break;
                                                    case 13:
                                                        imageView_robot3_show1.setImageResource(R.drawable.c27);
                                                        break;
                                                    case 14:
                                                        imageView_robot3_show1.setImageResource(R.drawable.c28);
                                                        break;
                                                    case 15:
                                                        imageView_robot3_show1.setImageResource(R.drawable.c29);
                                                        break;
                                                    case 16:
                                                        imageView_robot3_show1.setImageResource(R.drawable.c30);
                                                        break;
                                                    case 17:
                                                        imageView_robot3_show1.setImageResource(R.drawable.c31);
                                                        break;
                                                    case 18:
                                                        imageView_robot3_show1.setImageResource(R.drawable.c32);
                                                        break;
                                                    case 19:
                                                        imageView_robot3_show1.setImageResource(R.drawable.c33);
                                                        break;
                                                    case 20:
                                                        imageView_robot3_show1.setImageResource(R.drawable.c34);
                                                        break;
                                                    case 21:
                                                        imageView_robot3_show1.setImageResource(R.drawable.c35);
                                                        break;
                                                    case 22:
                                                        imageView_robot3_show1.setImageResource(R.drawable.c36);
                                                        break;
                                                    case 23:
                                                        imageView_robot3_show1.setImageResource(R.drawable.c37);
                                                        break;
                                                    case 24:
                                                        imageView_robot3_show1.setImageResource(R.drawable.c38);
                                                        break;
                                                    case 25:
                                                        imageView_robot3_show1.setImageResource(R.drawable.c39);
                                                        break;
                                                    case 26:
                                                        imageView_robot3_show1.setImageResource(R.drawable.c14);
                                                        break;
                                                    case 27:
                                                        imageView_robot3_show1.setImageResource(R.drawable.c15);
                                                        break;
                                                    case 28:
                                                        imageView_robot3_show1.setImageResource(R.drawable.c16);
                                                        break;
                                                    case 29:
                                                        imageView_robot3_show1.setImageResource(R.drawable.c17);
                                                        break;
                                                    case 30:
                                                        imageView_robot3_show1.setImageResource(R.drawable.c18);
                                                        break;
                                                    case 31:
                                                        imageView_robot3_show1.setImageResource(R.drawable.c19);
                                                        break;
                                                    case 32:
                                                        imageView_robot3_show1.setImageResource(R.drawable.c20);
                                                        break;
                                                    case 33:
                                                        imageView_robot3_show1.setImageResource(R.drawable.c21);
                                                        break;
                                                    case 34:
                                                        imageView_robot3_show1.setImageResource(R.drawable.c22);
                                                        break;
                                                    case 35:
                                                        imageView_robot3_show1.setImageResource(R.drawable.c23);
                                                        break;
                                                    case 36:
                                                        imageView_robot3_show1.setImageResource(R.drawable.c24);
                                                        break;
                                                    case 37:
                                                        imageView_robot3_show1.setImageResource(R.drawable.c25);
                                                        break;
                                                    case 38:
                                                        imageView_robot3_show1.setImageResource(R.drawable.c26);
                                                        break;
                                                    case 39:
                                                        imageView_robot3_show1.setImageResource(R.drawable.c1);
                                                        break;
                                                    case 40:
                                                        imageView_robot3_show1.setImageResource(R.drawable.c2);
                                                        break;
                                                    case 41:
                                                        imageView_robot3_show1.setImageResource(R.drawable.c3);
                                                        break;
                                                    case 42:
                                                        imageView_robot3_show1.setImageResource(R.drawable.c4);
                                                        break;
                                                    case 43:
                                                        imageView_robot3_show1.setImageResource(R.drawable.c5);
                                                        break;
                                                    case 44:
                                                        imageView_robot3_show1.setImageResource(R.drawable.c6);
                                                        break;
                                                    case 45:
                                                        imageView_robot3_show1.setImageResource(R.drawable.c7);
                                                        break;
                                                    case 46:
                                                        imageView_robot3_show1.setImageResource(R.drawable.c8);
                                                        break;
                                                    case 47:
                                                        imageView_robot3_show1.setImageResource(R.drawable.c9);
                                                        break;
                                                    case 48:
                                                        imageView_robot3_show1.setImageResource(R.drawable.c10);
                                                        break;
                                                    case 49:
                                                        imageView_robot3_show1.setImageResource(R.drawable.c11);
                                                        break;
                                                    case 50:
                                                        imageView_robot3_show1.setImageResource(R.drawable.c12);
                                                        break;
                                                    case 51:
                                                        imageView_robot3_show1.setImageResource(R.drawable.c13);
                                                        break;
                                                    default:
                                                        break;
                                                }
                                                break;
                                            }
                                        }
                                        break;
                                    default:
                                        break;
                                }
                            }
                        }
                    }
                    exit=true;
                    break;
                default:
                    break;
            }
        }
    }
}
