package com.example.cdd.gamepackge;

public class HumanPlayer extends Robot{
    private String nickName;
    private int score= 0;
    private boolean doGiveUp = false;

    public int getScore() {
        return score;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public boolean getDoGiveUp(){
        return doGiveUp;
    }

    public void giveUp(){
        doGiveUp = true;
    }

}