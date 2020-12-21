package com.example.cdd.gamepackge;

import java.util.ArrayList;

public class GamePlayers {
    private static ArrayList<HumanPlayer> players;
    public static HumanPlayer ReturnPlayer(String name){
        for(int i=0;i<players.size();i++){
            if(name==players.get(i).getNickName()){
                return players.get(i);
            }
        }
        return null;
    }
}
