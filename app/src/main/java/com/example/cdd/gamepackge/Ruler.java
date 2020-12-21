package com.example.cdd.gamepackge;

import android.util.Log;

import java.util.ArrayList;
import java.util.Random;

public class Ruler{
    //新添加的代码
    private static int idOfPlayerWhoShowCardSuccessfully;
   //
    private static int roundCounter;
    private static Card[] cardArray;
    private static Robot[] playerArray;
    private static ArrayList<Card> cardsShowedByPreviousPlayer;
    private static TypeOfCards typeOfCards;
    private static int firstPlayerIdDuringARound;//一个回合内的第一个出牌玩家ID
    private static int numberOfHumanPlayer;
    private static HumanPlayer[] humanPlayerArray;



    //新添加的函数

    //

    public Ruler(int _numberOfHumanPlayer){
        numberOfHumanPlayer = _numberOfHumanPlayer;
        humanPlayerArray = new HumanPlayer[numberOfHumanPlayer];
        //新添加的代码


        //
        for (int i = 0; i < humanPlayerArray.length; ++i){
            humanPlayerArray[i] = new HumanPlayer();
        }
        playerArray = new Robot[4];
        for (int i = 0; i < playerArray.length; ++i){
            playerArray[i] = new Robot();
        }
        //玩家ID依次为0 1 2 3
        //将人类玩家随机分配到四个位置上
//        Random r = new Random(3);//种子最后要保持默认
//        int tag[] = {0,0,0,0};
//        int temp = 0;
//        int counter = 0;
//        int a[] = new int[numberOfHumanPlayer];
//        while (counter < numberOfHumanPlayer){
//            temp = r.nextInt(4);
//            if (tag[temp] == 0){
//                a[counter] = temp;
//                tag[temp] = 1;
//                ++counter;
//            }
//        }
//        for (int i = 0; i <numberOfHumanPlayer; ++i){
            playerArray[0] = (Robot) humanPlayerArray[0];
            playerArray[0].setHumanTrue();
//        }


        for (int i = 0; i < 4; ++i){
            playerArray[i].setPlayerId(i);
        }
        setRoundCounter(1);
        createCard();
        shuffle();
        distributeCard();
        setRoundCounter(1);
        decidePlayerStateInFirstRound();
    }


    public static int getIdOfPlayerWhoShowCardSuccessfully() {
        return idOfPlayerWhoShowCardSuccessfully;
    }

    //新添加的代码
    public static void setIdOfPlayerWhoShowCardSucessfully(int idOfPlayerWhoShowCardSucessfully) {
        Ruler.idOfPlayerWhoShowCardSuccessfully = idOfPlayerWhoShowCardSucessfully;
    }
    ////

    //返回五张牌符不符合某类型
    public static int judgeCardType(ArrayList<Card> cards) {
        if(willBeFlush(cards))
            return 1;
        if(willBeFour_One(cards))
            return 2;
        if(willBeThree_Two(cards))
            return 3;
        if(willBeFive_Same_Type(cards))
            return 4;
        if(willBeShunzi(cards))
            return 5;
        return -1;
    }

    //
    public static Card[] getCardArray() {
        return cardArray;
    }//无bug
    public static Robot[] getPlayerArray() {
        return playerArray;
    }//无bug
    public static HumanPlayer[] getHumanPlayerArray() {
        return humanPlayerArray;
    }//无bug
    public static ArrayList<Card> getCardsShowedByPreviousPlayer() {
        return cardsShowedByPreviousPlayer;
    }//无bug
    public static TypeOfCards getTypeOfCards() {
        return typeOfCards;
    }//无bug
    public static int getNumberOfHumanPlayer() {
        return numberOfHumanPlayer;
    }//无bug

    public static int getRoundCounter() {
        return roundCounter;
    }//无bug

    public static int getFirstPlayerIdDuringARound() {
        return firstPlayerIdDuringARound;
    }//无bug

    public void setRoundCounter(int roundCounter) {
        this.roundCounter = roundCounter;
    }//无bug

    public static void addCardToCardsShowedByPreviousPlayer(ArrayList<Card> cards){
        if (cardsShowedByPreviousPlayer == null){
            cardsShowedByPreviousPlayer = new ArrayList<Card>();
        }
        cardsShowedByPreviousPlayer.addAll(cards);
    }//无bug

    public static Robot whoIsInPlayerRound(){
        for (int i = 0; i < playerArray.length; ++i){
            if (playerArray[i].getPlayerState() == PlayerState.PlayerRound)
                return playerArray[i];
        }
        return null;
    }//无bug

    //移除牌桌上展示的牌以及改变这些被移除牌的状态
    public static void removeAllCardsShowedByPreviousPlayer(){
        if (cardsShowedByPreviousPlayer != null) {
            for (int i = 0; i < cardsShowedByPreviousPlayer.size(); ++i) {
                cardsShowedByPreviousPlayer.get(i).setCardState(CardState.Passed);
                cardsShowedByPreviousPlayer.remove(i--);
            }
        }
    }

    //此处排序完全在自然数比较的基础上并且依据类型来排序
    public static void sort(ArrayList<Card> cardArrayList){
        for(int i = 0; i < cardArrayList.size() - 1;++i){
            for(int j = i + 1; j < cardArrayList.size(); ++j){
                if (cardArrayList.get(i).getCardValue() > cardArrayList.get(j).getCardValue()) {
                    Card swap = cardArrayList.get(i);
                    cardArrayList.set(i,cardArrayList.get(j));
                    cardArrayList.set(j, swap);
                }
                else if (cardArrayList.get(i).getCardValue() == cardArrayList.get(j).getCardValue()){//数值相同，根据花色决定是否交换
                    if (compareTypesOfTwoCards(cardArrayList.get(i), cardArrayList.get(j)) == 2) {
                        Card swap = cardArrayList.get(i);
                        cardArrayList.set(i,cardArrayList.get(j));
                        cardArrayList.set(j, swap);
                    }
                }
            }
        }
    }//无bug



    //比较两张牌的数值大小,不包含顺子。0:card1小于card2；1：等于；2：大于。
    public static int compareValuesOfTwoCards(Card card1, Card card2){

            if (card1.getCardValue() >= 3 && card1.getCardValue() <= 13) {
                if (card2.getCardValue() > card1.getCardValue() ||
                        card2.getCardValue() == 1 || card2.getCardValue() == 2
                )
                    return 0;
                else if (card2.getCardValue() == card1.getCardValue())
                    return 1;
                else
                    return 2;
            } else {
                if (card2.getCardValue() > card1.getCardValue() && card2.getCardValue() < 3)
                    return 0;
                else if (card2.getCardValue() == card1.getCardValue())
                    return 1;
                else
                    return 2;
            }


    }//无bug

    //比较两张牌的类型大小。0:card1小于card2；1：等于；2：大于
    public static int compareTypesOfTwoCards(Card card1, Card card2){
        if (card1.getCardType() == CardType.DIMOND){
            if (card2.getCardType()!= CardType.DIMOND)
                return 0;
            else
                return 1;
        }
        else if (card1.getCardType() == CardType.CLUB){
            if (card2.getCardType() == CardType.HEART || card2.getCardType() == CardType.SPADE)
                return 0;
            else if (card2.getCardType() == CardType.CLUB)
                return 1;
            else
                return 2;
        }
        else if (card1.getCardType() == CardType.HEART){
            if (card2.getCardType() == CardType.SPADE)
                return 0;
            else if (card2.getCardType() == CardType.HEART)
                return 1;
            else
                return 2;
        }
        else // (card1.getCardType() == com.example.test.CardType.SPADE)
        {
            if (card2.getCardType() == CardType.SPADE)
                return 1;
            else
                return 0;
        }
    }//无bug

    //比较两张牌的大小。返回大的那一张.注明是否为顺子.如输入两张相同的牌，则返回card1
    public static Card getBiggerCard(Card card1, Card card2, boolean IsShunzi){
        if (IsShunzi == false){
            if (compareValuesOfTwoCards(card1, card2) == 0)
                return card2;
            else if (compareValuesOfTwoCards(card1,card2) == 2)
                return card1;
            else {
                if (compareTypesOfTwoCards(card1,card2) == 0)
                    return card2;
                else
                    return card1;
            }
        }
        else {
                if (card1.getCardValue() == 1 && card2.getCardValue() != 13)
                    return card2;
                else if (card1.getCardValue() == 1 && card2.getCardValue()  == 13)
                    return card1;
                else {
                    if (card1.getCardValue() > card2.getCardValue())
                        return card1;
                    else
                        return card2;
                }
        }
    }//无bug

    //从已知牌型的相应数量的牌里面取最大的一张牌,先排序(sort)再调用此函数
    public static Card getMaxiumCard(ArrayList<Card> cardArrayList, TypeOfCards typeOfCards){
        if (cardArrayList.size() == 0)
            return null;
        else {
            Card card0, card1, card2, card3, card4;
            switch (typeOfCards) {
                case Single:
                    return cardArrayList.get(0);
                case Pairs:
                    card0 = cardArrayList.get(0);
                    card1 = cardArrayList.get(1);
                    return getBiggerCard(card0,card1,false);
                case Three:
                    card0 = cardArrayList.get(0);
                    card1 = cardArrayList.get(1);
                    card2 = cardArrayList.get(2);
                    card0 = getBiggerCard(card0, card1, false);
                    return getBiggerCard(card0, card2,false);
                case Shunzi:
                case Flush:
                    if (cardArrayList.get(0).getCardValue() == 1 && cardArrayList.get(1).getCardValue() != 2)//顺子尾为1
                        return cardArrayList.get(0);
                    else
                        return cardArrayList.get(4);
                case Three_Two:
                    if (cardArrayList.get(0).getCardValue() == cardArrayList.get(2).getCardValue()) {//说明头三张相同
                        card0 = cardArrayList.get(0);
                        card1 = cardArrayList.get(1);
                        card2 = cardArrayList.get(2);
                        card0 = getBiggerCard(card0, card1, false);
                        return getBiggerCard(card0, card2,false);
                    }
                    else {
                        card0 = cardArrayList.get(2);
                        card1 = cardArrayList.get(3);
                        card2 = cardArrayList.get(4);
                        card0 = getBiggerCard(card0, card1, false);
                        return getBiggerCard(card0, card2,false);
                    }
                case Four_One:
                    if (cardArrayList.get(0).getCardValue() == cardArrayList.get(1).getCardValue()) {//说明头四张相同
                        card0 = cardArrayList.get(0);
                        card1 = cardArrayList.get(1);
                        card2 = cardArrayList.get(2);
                        card3 = cardArrayList.get(3);
                        card0 = getBiggerCard(card0, card1, false);
                        card0 = getBiggerCard(card0, card2, false);
                        return getBiggerCard(card0, card3, false);
                    }
                    else {
                        card0 = cardArrayList.get(1);
                        card1 = cardArrayList.get(2);
                        card2 = cardArrayList.get(3);
                        card3 = cardArrayList.get(4);
                        card0 = getBiggerCard(card0, card1, false);
                        card0 = getBiggerCard(card0, card2, false);
                        return getBiggerCard(card0, card3, false);
                    }
                case Five_Same_Type:
                    card0 = cardArrayList.get(0);
                    card1 = cardArrayList.get(1);
                    card2 = cardArrayList.get(2);
                    card3 = cardArrayList.get(3);
                    card4 = cardArrayList.get(4);
                    card0 = getBiggerCard(card0, card1, false);
                    card0 = getBiggerCard(card0, card2, false);
                    card0 = getBiggerCard(card0, card3, false);
                    return getBiggerCard(card0, card4, false);
                default:
                    return null;
            }
        }
    }//无bug


    //先排序再调用此函数，是否为顺子
    public static boolean willBeShunzi(ArrayList<Card> cardArrayList){
        if (cardArrayList.size() < 5)
            return false;
         for (int i = 1; i < cardArrayList.size() - 1; ++i) {//先从第二张开始检
                if (cardArrayList.get(i).getCardValue() + 1 != cardArrayList.get(i + 1).getCardValue())
                    return false;
         }
         int a = cardArrayList.get(0).getCardValue();
         int b = cardArrayList.get(1).getCardValue();
         int c = cardArrayList.get(4).getCardValue();
         if (a != 1) {//顺子不含牌A
             if (b != a + 1)
                 return false;
             else
                 return true;
         }
         else{
             if (b == 2 || c == 13)//牌A在顺子头或尾
                 return true;
             else
                 return false;
         }
    }//无bug

    //先排序并且确定是顺子之后再调用此函数，是否为同花顺
    public static boolean willBeFlush(ArrayList<Card> cardArrayList){
        return willBeFive_Same_Type(cardArrayList);
    }//无bug

    //先排序再调用此函数，是否为三带二
    public static boolean willBeThree_Two(ArrayList<Card> cardArrayList){
        int k1, k2, k3, k4;
        //情况一：前两个，后三个分别相同
        boolean a;//前两个，后三个分别相同的条件
        boolean a1 = false;//条件1：前两个相同
        boolean a2 = false;//条件2：后三个相同
        boolean a3 = false;//条件3：这两组值不相同
        k1 = cardArrayList.get(0).getCardValue();
        k2 = cardArrayList.get(2).getCardValue();
        if (k1 == cardArrayList.get(1).getCardValue())
            a1 = true;//前两个相同
        int counter = 0;
        for (int i = 3; i < 5; ++i){
            if (k2 != cardArrayList.get(i).getCardValue())
                break;
            ++counter;
        }
        if (counter == 2)
            a2 = true;
        if (k1 != k2)
            a3 = true;
        a = a1 && a2 && a3;
        if (a)//满足情况一，可直接返回
            return true;

        //情况二：前三个，后两个分别相同
        boolean b;
        boolean b1 = false;
        boolean b2 = false;
        boolean b3 = false;
        k3 = cardArrayList.get(0).getCardValue();
        k4 = cardArrayList.get(3).getCardValue();
        if (k4 == cardArrayList.get(4).getCardValue())
            b1 = true;
        counter = 0;
        for (int i = 1; i < 3; ++i){
            if (k3 != cardArrayList.get(i).getCardValue())
                break;
            ++counter;
        }
        if (counter == 2)
            b2 = true;
        if (k3 != k4)
            b3 = true;
        b = b1 && b2 && b3;
        if (b)
            return true;
        //情况三：不是三带二的类型
        else
            return false;
    }//无bug

    //先排序再调用此函数，是否为四带一
    public static boolean willBeFour_One(ArrayList<Card> cardArrayList){
        int k1, k2, k3, k4;
        //情况一：前一张与后四张
        boolean a;
        boolean a1 = false;
        boolean a2 = false;
        k1 = cardArrayList.get(0).getCardValue();
        k2 = cardArrayList.get(1).getCardValue();
        int counter = 0;
        for (int i = 2; i < 5; ++i){
            if (k2 != cardArrayList.get(i).getCardValue())
                break;
            ++counter;
        }
        if (counter == 3)
            a1 = true;
        if (k1 != k2)
            a2 = true;
        a = a1 && a2;
        if (a)
            return true;
        //情况二：前四张与后一张
        boolean b;
        boolean b1 = false;
        boolean b2 = false;
        k3 = cardArrayList.get(0).getCardValue();
        k4 = cardArrayList.get(4).getCardValue();
        counter = 0;
        for (int i = 1; i < 4; ++i){
            if (k3 != cardArrayList.get(i).getCardValue())
                break;
            ++counter;
        }
        if (counter == 3)
            b1 = true;
        if (k3 != k4)
            b2 = true;
        b = b1 && b2;
        if (b)
            return true;
        //情况三：不是四带一
        else
            return false;
    }//无bug

    //先排序再调用此函数，是否为同花
    public static boolean willBeFive_Same_Type(ArrayList<Card> cardArrayList){
        CardType cardType = cardArrayList.get(0).getCardType();
        for (int i = 0; i < cardArrayList.size(); ++i){
            if (cardType != cardArrayList.get(i).getCardType())
                return false;
        }
        return true;
    }//无bug


    //在第一回合给每个玩家设置玩家状态
    public void decidePlayerStateInFirstRound(){
    for(int i = 0; i < 52; ++i){
        if (cardArray[i].getCardId() == 41){//id为41的对应方块3
            int owner = cardArray[i].getOwner();
            for(int i1 = 0; i1 < 4; ++i1){//给每个玩家设定状态
                if (i1 != owner)
                    playerArray[i1].setPlayerState(PlayerState.Waiting);
                else
                    playerArray[i1].setPlayerState(PlayerState.PlayerRound);
            }
            firstPlayerIdDuringARound = owner;
            break;
        }
    }
    }//无bug

    public void createCard(){
        cardArray = new Card[52];
        for (int i = 0; i < cardArray.length; ++i){
            cardArray[i] = new Card();
        }
        for (int i = 0; i < 52; ++i){
            if (i < 13){
                cardArray[i].setCardType(CardType.SPADE);
                cardArray[i].setCardValue(i + 1);
            }
            else if (i < 26){
                cardArray[i].setCardType(CardType.HEART);
                cardArray[i].setCardValue(i - 12);
            }
            else if ( i < 39){
                cardArray[i].setCardType(CardType.CLUB);
                cardArray[i].setCardValue(i - 25);
            }
            else {
                cardArray[i].setCardType(CardType.DIMOND);
                cardArray[i].setCardValue(i - 38);
            }
            cardArray[i].setOwner(-1);
            cardArray[i].setCardId(i);
        }

    }//无bug
    public void shuffle(){
        Random rand = new Random();//种子最后要保持默认
        for (int i = 0; i < 52; ++i){
            int a = rand.nextInt(52);
            Card card = cardArray[a];
            cardArray[a] = cardArray[i];
            cardArray[i] = card;
        }
    }//无bug
    public void distributeCard(){
        for(int i = 0; i < 52; ++i){
            if (i < 13){
                cardArray[i].setOwner(0);
                playerArray[0].addCardAtHand(cardArray[i]);
            }
            else if (i < 26){
                cardArray[i].setOwner(1);
                playerArray[1].addCardAtHand(cardArray[i]);
            }
            else if (i < 39){
                cardArray[i].setOwner(2);
                playerArray[2].addCardAtHand(cardArray[i]);
            }
            else {
                cardArray[i].setOwner(3);
                playerArray[3].addCardAtHand(cardArray[i]);
            }
            cardArray[i].setCardState(CardState.AtHandNotShowed);
        }

        for(int i=0;i<4;i++){
            sort(playerArray[i].getCardsAtHand());
        }

    }//无bug

    public static boolean WillWin(Robot player){
        if (isEmptyCardsAtHand(player)){
            caculateScore();
            //nextGame
            return true;
        }
        else {
           // toTheTurnOfNextPlayer(player);
            return false;
        }
    }
    //检查玩家手牌是否全部出完
    public static boolean isEmptyCardsAtHand(Robot player){
        return player.getCardsAtHand().isEmpty();
    }//无bug

    public static Robot getNextPlayer(Robot player){

        int id = player.getPlayerId();
        if (id == 3)
            return playerArray[0];
        else
            return playerArray[id + 1];
    }//无bug

    //轮到下一位玩家
    public static void toTheTurnOfNextPlayer(Robot player/*刚刚结束出牌阶段的那个玩家*/){
        Robot nextPlayer = getNextPlayer(player);

        //设定玩家状态
        if (nextPlayer.getPlayerState() == PlayerState.Passed){
            for(int i = 0; i < playerArray.length; ++i){
                playerArray[i].setPlayerState(PlayerState.Waiting);
            }
            nextPlayer.setPlayerState(PlayerState.PlayerRound);
        }
        else {
            player.setPlayerState(PlayerState.Passed);
            nextPlayer.setPlayerState(PlayerState.PlayerRound);
        }

        //如果下一个玩家是上一个成功出牌的玩家（其他三名玩家要不起），那么就开始新的回合,重新设定回合内第一个出牌的玩家
        if(nextPlayer.getPlayerId() == idOfPlayerWhoShowCardSuccessfully){
            firstPlayerIdDuringARound = nextPlayer.getPlayerId();
            roundCounter++;
        }
    }


    //玩家想要出牌时调用
    /*
    public static boolean CanPlayerShowCard(Robot player){

        ArrayList<Card> cardsToShow = player.getCardsToShow();
        if (cardsToShow.size()==0){
            return  false;
        }
        sort(cardsToShow);
        //是否本回合第一个出牌的玩家
        if (player.getPlayerId() == firstPlayerIdDuringARound){
            //是否第一回合
            if (roundCounter == 1){//第一回合,检验cardToShow里有没有方块3
                    for(int i = 0; i < cardsToShow.size(); ++i){
                        if (cardsToShow.get(i).getCardId() == 41)
                            break;//能找到方块3，就跳出循环
                        if (i == cardsToShow.size() -1)//cardToShow里没有方块3
                            return false;
                    }
            }
            //第一回合且有方块3待出，或者不是第一回合，则检查牌的类型是否符合要求即可
            switch (cardsToShow.size()){
                case 1:
                    typeOfCards = TypeOfCards.Single;
                    return true;
                case 2:
                    //检查对子的数字是否相同
                    if (cardsToShow.get(0).getCardValue() != cardsToShow.get(1).getCardValue())
                        return false;
                    typeOfCards = TypeOfCards.Pairs;
                    return true;
                case 3:
                    //检查3张的数字是否相同
                    for(int i = 0; i < cardsToShow.size(); ++i){
                        int a = cardsToShow.get(0).getCardValue();
                        if (a != cardsToShow.get(i).getCardValue())
                            return false;
                    }
                    typeOfCards = TypeOfCards.Three;
                    return true;
                case 5:
                    if (willBeShunzi(cardsToShow)){
                        if(willBeFlush(cardsToShow)) {
                            typeOfCards = TypeOfCards.Flush;
                            return true;
                        }
                        else {
                            typeOfCards = TypeOfCards.Shunzi;
                            return true;
                        }
                    }
                    if (willBeThree_Two(cardsToShow)){
                        typeOfCards = TypeOfCards.Three_Two;
                        return true;
                    }
                    if(willBeFour_One(cardsToShow)){
                        typeOfCards = TypeOfCards.Four_One;
                        return true;
                    }
                    if (willBeFive_Same_Type(cardsToShow)){
                        typeOfCards = TypeOfCards.Five_Same_Type;
                        return true;
                    }
                default:
                    return false;
            }
        }
        //不是本回合第一个出牌的玩家
        else {
            Card cardPrevious = null;
            Card cardToShow = null;
            switch (typeOfCards) {
                case Single:
                    if (cardsToShow.size() > 1)
                        return false;
                    cardToShow = getMaxiumCard(cardsToShow, TypeOfCards.Single);
                    cardPrevious = getMaxiumCard(cardsShowedByPreviousPlayer, TypeOfCards.Single);
                    if (getBiggerCard(cardPrevious, cardToShow, false) == cardToShow)
                        return true;
                    else
                        return false;
                case Pairs:
                    if (cardsToShow.size() >2)
                        return false;

                    //检查对子的数字是否相同
                    if (cardsToShow.get(0).getCardValue() != cardsToShow.get(1).getCardValue())
                        return false;
                    //和上个玩家的牌比较大小
                    cardToShow = getMaxiumCard(cardsToShow, TypeOfCards.Pairs);
                    cardPrevious = getMaxiumCard(cardsShowedByPreviousPlayer, TypeOfCards.Pairs);
                    if (getBiggerCard(cardPrevious, cardToShow, false) == cardToShow)
                        return true;
                    else
                        return false;
                case Three:
                    if (cardsToShow.size() >3)
                        return false;

                    //检查3张的数字是否相同
                    for(int i = 0; i < cardsToShow.size(); ++i){
                        int a = cardsToShow.get(0).getCardValue();
                        if (a != cardsToShow.get(i).getCardValue())
                                return false;
                    }
                    cardToShow = getMaxiumCard(cardsToShow, TypeOfCards.Three);
                    cardPrevious = getMaxiumCard(cardsShowedByPreviousPlayer, TypeOfCards.Three);
                    if (getBiggerCard(cardPrevious, cardToShow, false) == cardToShow)
                        return true;
                    else
                        return false;
                case Shunzi:
                    if (cardsToShow.size() >5)
                        return false;

                    //检查是否符合顺子的要求
                    if (willBeShunzi(cardsToShow) == false)
                    return false;

                    cardToShow = getMaxiumCard(cardsToShow, TypeOfCards.Shunzi);
                    cardPrevious = getMaxiumCard(cardsShowedByPreviousPlayer, TypeOfCards.Shunzi);
                    if (getBiggerCard(cardPrevious, cardToShow, true) == cardToShow)
                        return true;
                    else
                        return false;
                case Flush:
                    if (cardsToShow.size() >5)
                        return false;

                    //检查是否符合同花顺的要求
                    if (willBeFlush(cardsToShow) == false)
                        return false;

                    cardToShow = getMaxiumCard(cardsToShow, TypeOfCards.Flush);
                    cardPrevious = getMaxiumCard(cardsShowedByPreviousPlayer, TypeOfCards.Flush);
                    if (getBiggerCard(cardPrevious, cardToShow, true) == cardToShow)
                        return true;
                    else
                        return false;
                case Three_Two:
                    if (cardsToShow.size() >5)
                        return false;

                    //检查是否符合Three_Two的要求
                    if (willBeThree_Two(cardsToShow) == false)
                        return false;

                    cardToShow = getMaxiumCard(cardsToShow, TypeOfCards.Three_Two);
                    cardPrevious = getMaxiumCard(cardsShowedByPreviousPlayer, TypeOfCards.Three_Two);
                    if (getBiggerCard(cardPrevious, cardToShow, false) == cardToShow)
                        return true;
                    else
                        return false;
                case Four_One:
                    if (cardsToShow.size() >5)
                        return false;

                    //检查是否符合Four_One的要求
                    if (willBeFour_One(cardsToShow) == false)
                        return false;

                    cardToShow = getMaxiumCard(cardsToShow, TypeOfCards.Four_One);
                    cardPrevious = getMaxiumCard(cardsShowedByPreviousPlayer, TypeOfCards.Four_One);
                    if (getBiggerCard(cardPrevious, cardToShow, false) == cardToShow)
                        return true;
                    else
                        return false;
                case Five_Same_Type:
                    if (cardsToShow.size() >5)
                        return false;

                    //检查是否符合Five_Same_Type的要求
                    if (willBeFive_Same_Type(cardsToShow) == false)
                        return false;

                    cardToShow = getMaxiumCard(cardsToShow, TypeOfCards.Five_Same_Type);
                    cardPrevious = getMaxiumCard(cardsShowedByPreviousPlayer, TypeOfCards.Five_Same_Type);
                    if (getBiggerCard(cardPrevious, cardToShow, false) == cardToShow)
                        return true;
                    else
                        return false;
                default:
                    return false;
            }

        }
    }
*/

    //在非顺子的情况下给一些牌排序（方块3最小）
    public static void sortForTypeOfSingle(ArrayList<Card> cardArrayList){
        for(int i = 0; i < cardArrayList.size() -1; ++i){
            for (int j = i + 1; j < cardArrayList.size(); ++j){
                if (cardArrayList.get(i) == getBiggerCard(cardArrayList.get(i), cardArrayList.get(j), false)){//如果i位置的牌比j位置的牌要大
                    Card swap = cardArrayList.get(i);
                    cardArrayList.set(i,cardArrayList.get(j));
                    cardArrayList.set(j, swap);
                }
            }
        }
    }

    //检索并返回牌的类型
    public static TypeOfCards whichTypeOfCards(ArrayList<Card> cardArrayList){
        switch (cardArrayList.size()){
            case 1:
                return TypeOfCards.Single;
            case 2:
                if(cardArrayList.get(0).getCardValue() != cardArrayList.get(1).getCardValue())
                    return TypeOfCards.Pairs;
                else
                    return null;
            case 3:
                for(int i = 0; i < cardArrayList.size(); ++i){
                    int a = cardArrayList.get(0).getCardValue();
                    if (a != cardArrayList.get(i).getCardValue())
                        return null;
                }
                return TypeOfCards.Three;
            case 5:
                if (willBeShunzi(cardArrayList)){
                    if(willBeFlush(cardArrayList)) {
                        return TypeOfCards.Flush;
                    }
                    else {
                        return TypeOfCards.Shunzi;
                    }
                }
                if (willBeThree_Two(cardArrayList)){
                    return TypeOfCards.Three_Two;
                }
                if(willBeFour_One(cardArrayList)){
                    return TypeOfCards.Four_One;
                }
                if (willBeFive_Same_Type(cardArrayList)){
                    return TypeOfCards.Five_Same_Type;
                }
            default:
                return null;
        }
    }

    //检索并直接设置牌的类型
    public static void setTypeOfCards(ArrayList<Card> cardArrayList){
        typeOfCards = whichTypeOfCards(cardArrayList);
    }

    //玩家想要出牌时调用
    public static boolean CanPlayerShowCard(Robot player){
        ArrayList<Card> cardsToShow = player.getCardsToShow();
        if (cardsToShow.size() == 0)
            return false;

        sort(cardsToShow);
        //是否本回合第一个出牌的玩家
        if (player.getPlayerId() == firstPlayerIdDuringARound){
            //是否第一回合
          //  if (roundCounter == 1){//第一回合,检验cardToShow里有没有方块3
                for(int i = 0; i < cardsToShow.size(); ++i){
                    if (cardsToShow.get(i).getCardId() == 41)
                        break;//能找到方块3，就跳出循环
               //     if (i == cardsToShow.size() -1)//cardToShow里没有方块3
               //         return false;
             //   }
            }
            //第一回合且有方块3待出，或者不是第一回合，则检查牌的类型是否符合要求即可
            switch (cardsToShow.size()){
                case 1:
                    //typeOfCards = TypeOfCards.Single;
                    return true;
                case 2:
                    //检查对子的数字是否相同
                    if (cardsToShow.get(0).getCardValue() != cardsToShow.get(1).getCardValue())
                        return false;
                    //typeOfCards = TypeOfCards.Pairs;
                    return true;
                case 3:
                    //检查3张的数字是否相同
                    for(int i = 0; i < cardsToShow.size(); ++i){
                        int a = cardsToShow.get(0).getCardValue();
                        if (a != cardsToShow.get(i).getCardValue())
                            return false;
                    }
                    //typeOfCards = TypeOfCards.Three;
                    return true;
                case 5:
                    if (willBeShunzi(cardsToShow)){
                        if(willBeFlush(cardsToShow)) {
                            //typeOfCards = TypeOfCards.Flush;
                            return true;
                        }
                        else {
                            //typeOfCards = TypeOfCards.Shunzi;
                            return true;
                        }
                    }
                    if (willBeThree_Two(cardsToShow)){
                        //typeOfCards = TypeOfCards.Three_Two;
                        return true;
                    }
                    if(willBeFour_One(cardsToShow)){
                        //typeOfCards = TypeOfCards.Four_One;
                        return true;
                    }
                    if (willBeFive_Same_Type(cardsToShow)){
                        //typeOfCards = TypeOfCards.Five_Same_Type;
                        return true;
                    }
                default:
                    return false;
            }
        }
        //不是本回合第一个出牌的玩家
        else {
            Card cardPrevious = null;
            Card cardToShow = null;
            switch (typeOfCards) {
                case Single:
                    if (cardsToShow.size() != 1)
                        return false;
                    cardToShow = getMaxiumCard(cardsToShow, TypeOfCards.Single);
                    cardPrevious = getMaxiumCard(cardsShowedByPreviousPlayer, TypeOfCards.Single);
                    if (getBiggerCard(cardPrevious, cardToShow, false) == cardToShow)
                        return true;
                    else
                        return false;
                case Pairs:
                    if (cardsToShow.size() != 2)
                        return false;

                    //检查对子的数字是否相同
                    if (cardsToShow.get(0).getCardValue() != cardsToShow.get(1).getCardValue())
                        return false;
                    //和上个玩家的牌比较大小
                    cardToShow = getMaxiumCard(cardsToShow, TypeOfCards.Pairs);
                    cardPrevious = getMaxiumCard(cardsShowedByPreviousPlayer, TypeOfCards.Pairs);
                    if (getBiggerCard(cardPrevious, cardToShow, false) == cardToShow)
                        return true;
                    else
                        return false;
                case Three:
                    if (cardsToShow.size() != 3)
                        return false;

                    //检查3张的数字是否相同
                    for (int i = 0; i < cardsToShow.size(); ++i) {
                        int a = cardsToShow.get(0).getCardValue();
                        if (a != cardsToShow.get(i).getCardValue())
                            return false;
                    }
                    cardToShow = getMaxiumCard(cardsToShow, TypeOfCards.Three);
                    cardPrevious = getMaxiumCard(cardsShowedByPreviousPlayer, TypeOfCards.Three);
                    if (getBiggerCard(cardPrevious, cardToShow, false) == cardToShow)
                        return true;
                    else
                        return false;

                    //五张的情况
                case Shunzi:
                    if (cardsToShow.size() != 5)
                        return false;

                    //检查是否符合顺子的要求
                    if (willBeShunzi(cardsToShow) == false) {//不是顺子，那么就要看看是不是（三带二，四带一，同花）的情况,如果是，则能出，不然不能
                        if (willBeThree_Two(cardsToShow) || willBeFour_One(cardsToShow) || willBeFive_Same_Type(cardsToShow))
                            return true;
                        else
                            return false;

                    }

                    cardToShow = getMaxiumCard(cardsToShow, TypeOfCards.Shunzi);
                    cardPrevious = getMaxiumCard(cardsShowedByPreviousPlayer, TypeOfCards.Shunzi);
                    if (getBiggerCard(cardPrevious, cardToShow, true) == cardToShow)
                        return true;
                    else
                        return false;
                case Flush:
                    if (cardsToShow.size() != 5)
                        return false;

                    //检查是否符合同花顺的要求
                    if (willBeFlushWithoutConfirmingShunzi(cardsToShow) == false)
                        return false;

                    cardToShow = getMaxiumCard(cardsToShow, TypeOfCards.Flush);
                    cardPrevious = getMaxiumCard(cardsShowedByPreviousPlayer, TypeOfCards.Flush);
                    if (getBiggerCard(cardPrevious, cardToShow, true) == cardToShow)
                        return true;
                    else
                        return false;
                case Three_Two:
                    if (cardsToShow.size() != 5)
                        return false;

                    //检查是否符合Three_Two的要求
                    if (willBeThree_Two(cardsToShow) == false){//不符合的情况下，如果是四带一或者同花顺，则可以出
                        if (willBeFour_One(cardsToShow) || willBeFlush(cardsToShow))
                            return true;
                        else
                            return false;
                    }

                    cardToShow = getMaxiumCard(cardsToShow, TypeOfCards.Three_Two);
                    cardPrevious = getMaxiumCard(cardsShowedByPreviousPlayer, TypeOfCards.Three_Two);
                    if (getBiggerCard(cardPrevious, cardToShow, false) == cardToShow)
                        return true;
                    else
                        return false;
                case Four_One:
                    if (cardsToShow.size() != 5)
                        return false;

                    //检查是否符合Four_One的要求
                    if (willBeFour_One(cardsToShow) == false){//如果是同花顺，则可以出
                        if(willBeFlushWithoutConfirmingShunzi(cardsToShow))
                            return true;
                        else
                            return false;
                    }

                    cardToShow = getMaxiumCard(cardsToShow, TypeOfCards.Four_One);
                    cardPrevious = getMaxiumCard(cardsShowedByPreviousPlayer, TypeOfCards.Four_One);
                    if (getBiggerCard(cardPrevious, cardToShow, false) == cardToShow)
                        return true;
                    else
                        return false;
                case Five_Same_Type:
                    if (cardsToShow.size() != 5)
                        return false;

                    //检查是否符合Five_Same_Type的要求
                    if (willBeFive_Same_Type(cardsToShow) == false){//检查是不是三带二，四带一，同花顺，如果是，则能出，不然不能出
                        if (willBeThree_Two(cardsToShow) || willBeFour_One(cardsToShow) || willBeFlushWithoutConfirmingShunzi(cardsToShow))
                            return true;
                        else
                            return false;
                    }

                    cardToShow = getMaxiumCard(cardsToShow, TypeOfCards.Five_Same_Type);
                    cardPrevious = getMaxiumCard(cardsShowedByPreviousPlayer, TypeOfCards.Five_Same_Type);
                    if (getBiggerCard(cardPrevious, cardToShow, false) == cardToShow)
                        return true;
                    else
                        return false;
                default:
                    return false;
            }

        }
    }

    //是否为同花顺，此方法不用先确定是顺子，但是仍然要先排序（sort）
    public static boolean willBeFlushWithoutConfirmingShunzi(ArrayList<Card> cardArrayList){
        if(willBeShunzi(cardArrayList) == false)
            return false;
        else
            return willBeFlush(cardArrayList);
    }//无bug


    //结束时计算本局分数
    public static void caculateScore(){
        //如果有人认输
        for (int i = 0; i < humanPlayerArray.length; ++i){
            if (humanPlayerArray[i].getDoGiveUp() == true){
                humanPlayerArray[i].setScore(-52 * 4);
                for (int i1 = 0; i1 < humanPlayerArray.length; ++i1){
                    if (i1 != i)
                        humanPlayerArray[i1].setScore(52);
                }
                return;
            }

        }
        //无人认输
        int[] firstScore = new int[4];
        int numberOfCardsAtHand;
        for(int i = 0; i < playerArray.length; ++i){
            numberOfCardsAtHand = playerArray[i].getCardsAtHand().size();
            if (numberOfCardsAtHand < 8)
                firstScore[i] = numberOfCardsAtHand;
            else if (numberOfCardsAtHand < 10)
                firstScore[i] = numberOfCardsAtHand * 2;
            else if (numberOfCardsAtHand < 13)
                firstScore[i] = numberOfCardsAtHand * 3;
            else
                firstScore[i] = numberOfCardsAtHand * 4;
        }
        //牌值为2的玩家得分翻倍
        for (int i = 0; i < cardArray.length; ++i) {
            if (cardArray[i].getCardId() == 1 || cardArray[i].getCardId() == 14 ||cardArray[i].getCardId() == 27 ||cardArray[i].getCardId() == 40) {
                if (cardArray[i].getOwner() == 0 || cardArray[i].getOwner() == 1 || cardArray[i].getOwner() == 2 || cardArray[i].getOwner() == 3) {
                    firstScore[cardArray[i].getOwner()] *= 2;
                }
            }
        }

        //计算人类玩家最终得分
        int[] secondScore = {0,0,0,0};
        for(int i = 0; i < playerArray.length; ++i){
            if (playerArray[i] instanceof HumanPlayer) {
                for (int i1 = 0; i1 < playerArray.length; ++i1) {
                    if (i1 != i)
                        secondScore[i] += (firstScore[i1] - firstScore[i]);
                }
                HumanPlayer player = (HumanPlayer)playerArray[i];
                player.setScore(secondScore[i]);
            }
        }
    }//无bug



}