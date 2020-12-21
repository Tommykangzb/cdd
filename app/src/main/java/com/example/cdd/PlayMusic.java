package com.example.cdd;

import android.app.Activity;
import android.media.MediaPlayer;
import android.util.Log;

import java.io.IOException;

public  class PlayMusic {
    public static boolean monitor=true;
    private static MediaPlayer mediaPlayer;
    public static String numbercode="";
    public static int callnumber=0;
    public static void changeMusic(Activity activity,String number){
        mediaPlayer.stop();
        if (!mediaPlayer.isPlaying()) {
            switch (number) {
                case "0001":
                    mediaPlayer = MediaPlayer.create(activity, R.raw.moonoverfountain);
                    play();
                    break;
                case "0002":
                    mediaPlayer = MediaPlayer.create(activity, R.raw.hometown);
                    play();
                    break;
                case "0003":
                    mediaPlayer = MediaPlayer.create(activity, R.raw.summer);
                    play();
                    break;
                case "0004":
                    mediaPlayer = MediaPlayer.create(activity, R.raw.mountain_water);
                    play();
                    break;
                case "0005":
                    mediaPlayer = MediaPlayer.create(activity, R.raw.cccp);
                    play();
                    break;
                default:
                    break;
            }
        }
    }
    public static void changestate(){
        if(monitor==true){
            monitor=false;
        }
        else{
            monitor=true;
        }
    }
    public PlayMusic(Activity activity){
        mediaPlayer=MediaPlayer.create(activity,R.raw.target);
    }
    public static void play() {
//播放工程baires目录du下的raw目录中的音乐文件in_call_alarm
        try {
            mediaPlayer.prepare();
        } catch (IllegalStateException e) {
        } catch (IOException e) {
        }
        mediaPlayer.setLooping(true);
        mediaPlayer.start();
    }
    public void Go_on(){
        mediaPlayer.start();
    }
    public void pause(){
        if(mediaPlayer!=null){
            mediaPlayer.pause();
        }
    }
}
