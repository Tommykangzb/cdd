package com.example.cdd.gamepackge;

import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Robot{
    private int playerId;
    private boolean isHuman;
    private PlayerState playerState;
    //手牌
    private ArrayList<Card> cardsAtHand;
    //要打的牌
    private ArrayList<Card> cardsToShow;

    //新添加的代码
    //五个二维数组
    private ArrayList<ArrayList<Card>> cardPack_1 = new ArrayList<>();//用来装单张牌
    private ArrayList<ArrayList<Card>> cardPack_2 = new ArrayList<>();//用来装对子
    private ArrayList<ArrayList<Card>> cardPack_3 = new ArrayList<>();//用来装三连
    private ArrayList<ArrayList<Card>> cardPack_4 = new ArrayList<>();//用来装四条，
    private ArrayList<ArrayList<Card>> cardPack_5 = new ArrayList<>();//用来装五个一组的牌组


    //

    //可能出问题
    public Robot(){
    playerId = -1;
    playerState = PlayerState.Passed;
    cardsAtHand = new ArrayList<Card>();
    cardsToShow = new ArrayList<Card>();
    isHuman=false;
//    this.updateCardPacks();
    }


    //可能出问题
    public int RobotFirstShow(){
        this.updateCardPacks();
        int temp=0;
        for (int i=0;i<cardPack_1.size();i++){
            if (cardPack_1.get(i).get(0).getCardId()==41){
                cardsToShow.add(cardPack_1.get(i).get(0));
               if(Ruler.CanPlayerShowCard(this)) {
                   temp=cardsToShow.size();
                   showCards();
                   Ruler.toTheTurnOfNextPlayer(this);

               }
               break;
            }
        }
        return temp;
    }

    public void setHumanTrue(){
        isHuman=true;
    }

    public int getPlayerId() {
        return playerId;
    }

    public PlayerState getPlayerState() {
        return playerState;
    }

    public ArrayList<Card> getCardsToShow() {
        return cardsToShow;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public void setPlayerState(PlayerState playerState) {
        this.playerState = playerState;
    }

    public void addCardAtHand(Card card){
        cardsAtHand.add(card);
    }

    public ArrayList<Card> getCardsAtHand() {
        return cardsAtHand;
    }

    public void removeCardsAtHand(Card card){
        cardsAtHand.remove(card);
    }

    public void removeAllCardsAtHand(){
        for(int i = 0; i < cardsAtHand.size(); ++i){
            cardsAtHand.remove(i--);
        }
    }

    public void addCardToShow(Card card){
        cardsToShow.add(card);
    }

    public void addCardToShow(int ID){
        for(int i=0;i<cardsAtHand.size();i++){
            if (ID==cardsAtHand.get(i).getCardId()){
                cardsToShow.add(cardsAtHand.get(i));
            }
        }
    }
    public void removeCardsToShow(Card card){
        cardsToShow.remove(card);
    }
    public void removeCardsToShow(int ID){
        for(int i=0;i<cardsAtHand.size();i++){
            if (ID==cardsAtHand.get(i).getCardId()){
                cardsToShow.remove(cardsAtHand.get(i));
            }
        }
    }

    public void removeAllCardsToShow(){
        if (cardsToShow!=null) {
            cardsToShow.clear();
        }
    }

    //出牌,先删除桌上原有的牌，再添加现在桌上的牌
    public void showCards() {

        if(cardsToShow.size()!=0) {
            Ruler.removeAllCardsShowedByPreviousPlayer();
            Ruler.addCardToCardsShowedByPreviousPlayer(getCardsToShow());
            Ruler.setTypeOfCards(cardsToShow);
            //改变牌的状态和拥有者
            for (int i = 0; i < getCardsToShow().size(); ++i) {
                getCardsToShow().get(i).setCardState(CardState.Showed);
                getCardsToShow().get(i).setOwner(-1);
            }

            //根据出牌的情况从手牌中删除已经打出的牌
            for (int i = 0; i < getCardsToShow().size(); ++i) {
                removeCardsAtHand(getCardsToShow().get(i));
            }
                Ruler.setIdOfPlayerWhoShowCardSucessfully(this.playerId);
            //将待出牌序列清空
            removeAllCardsToShow();
        }
    }

    //新添加的代码

    public ArrayList<Card> getCardAgainstFiveCardsShowed( ArrayList<Card> cardsShowed) {
        //10121

        ArrayList<Integer> loc=new ArrayList<Integer>();
//        int type_1=Ruler.judgeCardType(cardsShowed);
//        TypeOfCards type = null;
//        switch (type_1){
//            case 1:
//                type=TypeOfCards.Flush;
//                break;
//            case 2:
//                type=TypeOfCards.Four_One;
//                break;
//            case 3:
//                type=TypeOfCards.Three_Two;
//                break;
//            case 4:
//                type=TypeOfCards.Five_Same_Type;
//                break;
//            case 5:
//                type=TypeOfCards.Shunzi;
//                break;
//            default:
//
//        }

        for (int i = 0; i < cardPack_5.size(); i++) {
           //因为一些未排查到的原因，cardPack_5的第一个可能为空，所以设置一个判断语句
            if(cardPack_5.get(i).size()!=0) {
                //找到一个更大类型的趟儿立刻返回
                if (Ruler.judgeCardType(cardsShowed) > Ruler.judgeCardType(cardPack_5.get(i)))
                    return cardPack_5.get(i);
                //先找到同类的趟儿
                if (Ruler.judgeCardType(cardsShowed) == Ruler.judgeCardType(cardPack_5.get(i)))
                    //把同类的趟儿的序号给记住
                    loc.add(i);
            }
        }

        //没有同类的趟儿
        if(loc.size()==0)
            return null;
        if(loc.size()>=1)
        {
            for (int i = 0; i < loc.size(); i++) {
                ArrayList<Card> tmp=compareWithcardsShowed(cardPack_5.get(loc.get(i)),cardsShowed);
                if(tmp.size()!=0)
                    return tmp;
            }

        }
        return null;
    }

    public ArrayList<Card> compareWithcardsShowed(ArrayList<Card> cards_to_compare,ArrayList<Card> cardsShowed){
        int type_1=Ruler.judgeCardType(cardsShowed);
        TypeOfCards type = null;
        switch (type_1){
            case 1:
                type=TypeOfCards.Flush;
                break;
            case 2:
                type=TypeOfCards.Four_One;
                break;
            case 3:
                type=TypeOfCards.Three_Two;
                break;
            case 4:
                type=TypeOfCards.Five_Same_Type;
                break;
            case 5:
                type=TypeOfCards.Shunzi;
                break;
            default:

        }

            //获得上家打的牌组中最大的牌的值
            Ruler.sort(cardsShowed);
            Card maxNumOfCardsShowed = Ruler.getMaxiumCard(cardsShowed, type);

        /* 获得手里牌组最大的卡牌的数字 */
            Ruler.sort(cards_to_compare);
            Card maxNum = Ruler.getMaxiumCard(cards_to_compare, type);

            if(type==TypeOfCards.Five_Same_Type)
            {
                //0:card1小于card2；1：等于；2：大于
                int compare_result=Ruler.compareTypesOfTwoCards(maxNum,maxNumOfCardsShowed);
                if(compare_result==2)
                    return cards_to_compare;
                else if (compare_result==1)
                {
                    if(Ruler.compareValuesOfTwoCards(maxNum,maxNumOfCardsShowed)==2)
                        return cards_to_compare;

                }
            }

            else if(Ruler.getBiggerCard(maxNum,maxNumOfCardsShowed,false)==maxNum)
                return cards_to_compare;


//        int compare_result=Ruler.compareValuesOfTwoCards(maxNumOfCardsShowed,maxNum);
//        //0:card1小于card2；1：等于；2：大于。
//
//            //手里同花顺最大的卡牌的数字比上家的最大的卡牌的数字大
//            if (compare_result==2)
//                //返回可出的牌
//                return cards_to_compare;
//
//                //同花顺两者最大卡牌数字一样
//            else if (maxNum == maxNumOfCardsShowed) {
//
//                //比较两张牌的花色大小。0:card1小于card2；1：等于；2：大于
//                //因为是同花顺，所以各取第一张牌比较就行
//                int compareResultOfCards = Ruler.compareTypesOfTwoCards(cards_to_compare.get(0), cardsShowed.get(0));
//
//                if (compareResultOfCards == 2) { //可以出牌
//                    return cards_to_compare;
//                }
//            }

         return null;
    }


    public void updateCardPacks(){

        //先取一张牌的组，存入cardPack_1
        if (cardsAtHand==null){
            System.exit(0);
        }

        cardPack_1.clear();
        cardPack_2.clear();
        cardPack_3.clear();
        cardPack_4.clear();
        cardPack_5.clear();
        for (int i = 0; i < cardsAtHand.size(); i++) {
            ArrayList<Card> cards = new ArrayList<>();
            Card card = cardsAtHand.get(i);

            cards.add(card);
            cardPack_1.add(cards);

        }

        //从头至倒数第一检阅cardPack_1，以检查对的存在（三连只能检测出两组）
        for (int i = 0; i < cardPack_1.size() - 1; i++) {
            Card card_1 = cardPack_1.get(i).get(0);
            Card card_2 = cardPack_1.get(i + 1).get(0);
            if (card_1.getCardValue() == card_2.getCardValue()) {
                ArrayList<Card> cards = new ArrayList<>();
                cards.add(card_1);
                cards.add(card_2);

                cardPack_2.add(cards);


            }
        }


        //从头至倒数第一检阅cardPack_2, 以检查三连的存在
        int cardPack_2_Size = cardPack_2.size();
        for (int i = 0; i < cardPack_2_Size - 1; i++) {
            //检查第i组的第0张和第i+1组的第1张，若相同，存入三张到cardPack_3并且也存入两张到cardPack_2以补充上面的
            Card card_1 = cardPack_2.get(i).get(0);
            Card card_3 = cardPack_2.get(i + 1).get(1);
            if (card_1.getCardValue() == card_3.getCardValue()) {
                ArrayList<Card> cards = new ArrayList<>();
                cards.add(card_1);
                cards.add(cardPack_2.get(i).get(1));
                cards.add(card_3);
                cardPack_3.add(cards);

                //这里把两张牌补充至cardPack_2
                ArrayList<Card> cards_added = new ArrayList<>();
                cards_added.add(card_1);
                cards_added.add(card_3);
                cardPack_2.add(cards_added);


            }

        }

        //从头至倒数第一检阅cardPack_3, 以检查四连的存在
        int cardPack_3_Size = cardPack_3.size();
        for (int i = 0; i < cardPack_3_Size - 1; i++) {
            //检查第i组的第0张和第i+1组的第2张，若相同，存入四张到cardPack_4并且也存入三张到cardPack_3以补充上面的
            //并且，补充头尾两张至cardPack_2
            Card card_1 = cardPack_3.get(i).get(0);
            Card card_4 = cardPack_3.get(i + 1).get(2);
            if (card_1.getCardValue() == card_4.getCardValue()) {
                Card card_2 = cardPack_3.get(i).get(1);
                Card card_3 = cardPack_3.get(i).get(2);
                ArrayList<Card> cards = new ArrayList<>();
                cards.add(card_1);
                cards.add(card_2);
                cards.add(card_3);
                cards.add(card_4);
                cardPack_4.add(cards);



                //这里把三张牌补充至cardPack_3
                ArrayList<Card> cards_added_3_1 = new ArrayList<>();
                ArrayList<Card> cards_added_3_2 = new ArrayList<>();
                //先补充1、2、4张
                cards_added_3_1.add(card_1);
                cards_added_3_1.add(card_2);
                cards_added_3_1.add(card_4);

                //再补充1、3、4张
                cards_added_3_2.add(card_1);
                cards_added_3_2.add(card_3);
                cards_added_3_2.add(card_4);



                cardPack_3.add(cards_added_3_1);
                cardPack_3.add(cards_added_3_2);

                //这里把两张牌补充至cardPack_2
                ArrayList<Card> cards_added_2 = new ArrayList<>();
                //补充首尾两张
                cards_added_2.add(card_1);
                cards_added_2.add(card_4);

                cardPack_2.add(cards_added_2);


            }

        }



        //这里按次序检查顺子、同花顺、同花、葫芦和金刚的存在

        //顺子
        ArrayList<ArrayList<Card>> cardBucket = new ArrayList<>();//建一个二维数组，每个列存入相同点数的牌
        for (int i = 0; i < cardsAtHand.size(); ) {
            ArrayList<Card> cardsFigureOnly = new ArrayList<>();
            cardsFigureOnly.add(cardsAtHand.get(i));
            i++;
            while (i < cardsAtHand.size()) {
                if (cardsAtHand.get(i).getCardValue() == cardsAtHand.get(i - 1).getCardValue()) {
                    cardsFigureOnly.add(cardsAtHand.get(i));
                    i++;
                } else {
                    break;
                }
            }
            cardBucket.add(cardsFigureOnly);
        }


        //连续检阅cardBucket每五项的第一张牌, 若点数递增则存入所有排列组合(不包括A2345和23456)
        for (int i = 0; i < cardBucket.size() - 4; i++) {
            ArrayList<Card> cardsSample = new ArrayList<>();//取第一张作为样本，因为后面的几张的数字完全相同
            for (int j = i; j < i + 5; j++) {
                cardsSample.add(cardBucket.get(j).get(0));
            }
            sequentialCardMatch(cardPack_5,cardBucket,cardsSample,i, i + 1, i + 2, i + 3, i + 4);
        }


        //同花
        ArrayList<Card> cards_diamond = new ArrayList<>();
        ArrayList<Card> cards_club = new ArrayList<>();
        ArrayList<Card> cards_heart = new ArrayList<>();
        ArrayList<Card> cards_spade = new ArrayList<>();

        for (int i = 0; i < cardsAtHand.size(); i++) {
            if(cardsAtHand.get(i).getCardType()==CardType.DIMOND)
                cards_diamond.add(cardsAtHand.get(i));
            else if(cardsAtHand.get(i).getCardType()==CardType.CLUB)
                cards_club.add(cardsAtHand.get(i));
            else if(cardsAtHand.get(i).getCardType()==CardType.HEART)
                cards_heart.add(cardsAtHand.get(i));
            else if(cardsAtHand.get(i).getCardType()==CardType.SPADE)
                cards_spade.add(cardsAtHand.get(i));
        }
        fiveSameTypeMatch(cards_diamond);
        fiveSameTypeMatch(cards_club);
        fiveSameTypeMatch(cards_heart);
        fiveSameTypeMatch(cards_spade);

        cards_diamond.clear();
        cards_club.clear();
        cards_heart.clear();
        cards_spade.clear();

        //葫芦
        //葫芦取cardPack_3和cardPack_2中不一样点数的进行排列组合, 由于葫芦是看3张牌的最大张大小, 因此2张牌的部分越小越有利
        for (int i = 0; i < cardPack_3.size(); i++) {
            ArrayList<Card> cards_3 = new ArrayList<>();
            cards_3.addAll(cardPack_3.get(i));
            for (int i1 = 0; i1 < cardPack_2.size(); i1++) {
                if (cardPack_2.get(i1).get(0).getCardValue() != cards_3.get(0).getCardValue()) {
                    ArrayList<Card> cards_2 = cardPack_2.get(i1);

                    ArrayList<Card> cards = new ArrayList<>();
                    cards.addAll(cards_2);
                    cards.addAll(cards_3);
                    cardPack_5.add(cards);


                }
            }
        }

        //金刚
        //金刚最好判, 因为只要四带一，任意一张都可以拼
        //但是, 其中的5张出牌权重要根据牌从大到小递增, 并且要根据牌的1张出牌权重从大到小递减
        for (int i = 0; i < cardPack_4.size(); i++) {
            for (int i1 = 0; i1 < cardsAtHand.size(); i1++) {
                if (cardsAtHand.get(i1).getCardValue() != cardPack_4.get(i).get(0).getCardValue()) {
                    Card card = cardsAtHand.get(i1);
                    ArrayList<Card> cards_4 = cardPack_4.get(i);

                    ArrayList<Card> cards = new ArrayList<>();
                    cards.add(card);
                    cards.addAll(cards_4);
                    cardPack_5.add(cards);


                }
            }
        }

    }

    public void randomCardShow(){


        if (cardsAtHand.size()==0){
            System.exit(0);
        }
        if (this.playerState != PlayerState.PlayerRound)  //是否是此玩家的回合
            return ;   //如果不是则报错
        //修改
        Ruler.sortForTypeOfSingle(cardsAtHand);
        cardsToShow.add(cardsAtHand.get(0));
    }



    //-1 不是他的回合， 0为pass， 1为出牌
    public int  RobotShowCard() {

        this.updateCardPacks();

        if (this.playerState != PlayerState.PlayerRound)  //是否是此玩家的回合
            return -1;   //如果不是则报错
        //是则进行下一步
//        if(!Ruler.CanPlayerShowCard(this)){
//            cardsToShow.clear();
//            //    return;
//        }

        //获取上一个玩家出的牌
        ArrayList<Card> cardsShowed = Ruler.getCardsShowedByPreviousPlayer();
        //修改
        Ruler.sortForTypeOfSingle(cardsAtHand);

        switch (cardsShowed.size()) {
            case 1:
                if (cardsAtHand.size()==0){
                    System.exit(0);
                }
                int compare_result1_of_case1 = 0;    //手里的牌与上家的牌比较结果
                for (int i = 0; i < cardsAtHand.size(); i++) { //如果
                    //0<;1==;2>
                    compare_result1_of_case1 = Ruler.compareValuesOfTwoCards(this.cardsAtHand.get(i), cardsShowed.get(0));
                    if (compare_result1_of_case1 == 2) {
                        //将这张牌加入cardsToShow里面
                        // 不考虑这张牌是否可以组成三连、三带二、四带一、顺子等
                        cardsToShow.add(cardsAtHand.get(i));
                        return 1;
                    }
                    if (compare_result1_of_case1 == 1) {
                        //两张牌相等时
                        // 不考虑这张牌是否可以组成三连、三带二、四带一、顺子等
                        int compareResult2 = Ruler.compareTypesOfTwoCards(this.cardsAtHand.get(i), cardsShowed.get(0));
                        if (compareResult2 == 2) {
                            //将这张牌加入cardsToShow里面
                            // 不考虑这张牌是否可以组成三连、三带二、四带一、顺子等
                            cardsToShow.add(cardsAtHand.get(i));
                            return 1;
                        }
                    }

                }
                Log.d("tttoo", "一张pass");
                pass();
                return 0;
            case 2:
                if (cardsAtHand.size()==0){
                    System.exit(0);
                }
                int compare_result1_of_case2 = 0;    //手里的牌与上家的牌比较结果
                int compare_result2_of_case2 = 0;
                if (this.cardsAtHand.size() >= 2) {
                    for (int i = 0; i < cardsAtHand.size() - 1; i++) { //如果
                        //0<;1==;2>
                        compare_result1_of_case2 = Ruler.compareValuesOfTwoCards(this.cardsAtHand.get(i), cardsShowed.get(0));

                        if (compare_result1_of_case2 == 2) {
                            compare_result2_of_case2 = Ruler.compareValuesOfTwoCards(this.cardsAtHand.get(i), this.cardsAtHand.get(i + 1));
                            if (compare_result2_of_case2 == 1) {
                                cardsToShow.add(cardsAtHand.get(i));
                                cardsToShow.add(cardsAtHand.get(i + 1));
                                return 1;
                            }
                        }
                    }
                }
                Log.d("147", "两张pass");
                pass();
                return 0;
            case 3: //三条
                if (cardsAtHand.size()==0){
                    System.exit(0);
                }
                int compareResult1 = 0;    //手里的牌与上家的牌比较结果
                int compareResult2 = 0;
                int compareResult3 = 0;
                if (this.cardsAtHand.size() >= 3) {
                    for (int i = 0; i < cardsAtHand.size() - 2; i++) { //如果
                        //0<;1==;2>
                        compareResult1 = Ruler.compareValuesOfTwoCards(this.cardsAtHand.get(i), cardsShowed.get(0));

                        if (compareResult1 == 2) {
                            compareResult2 = Ruler.compareValuesOfTwoCards(this.cardsAtHand.get(i), this.cardsAtHand.get(i + 1));
                            if (compareResult2 == 1) {
                                compareResult3 = Ruler.compareValuesOfTwoCards(this.cardsAtHand.get(i), this.cardsAtHand.get(i + 2));
                                if (compareResult3 == 1) {
                                    cardsToShow.add(cardsAtHand.get(i));
                                    cardsToShow.add(cardsAtHand.get(i + 1));
                                    cardsToShow.add(cardsAtHand.get(i + 2));
                                    return 1;
                                }
                            }
                        }
                    }
                }
                Log.d("t886", "三张pass");
                pass();
                return 0;
            //case 4://没有打四个的
            case 5: //可能是顺子，同花、三带二、四带一、同花顺
                if (cardsAtHand.size()==0){
                    System.exit(0);
                }
                if (this.cardsAtHand.size() >= 5) {
                    if (cardPack_5.size() == 0)
                        //假如没有五张牌组成的牌组，直接break
                        break;
//                    else if (cardPack_5.size() == 1 || cardPack_5.size() == 2) {
//                        //判断牌型，返回值是1,2,3,4,5,分别对应同花顺、四带一、三带二、同花、顺子
//                        switch (Ruler.judgeCardType(cardsShowed)) {
//                            case 1: {//同花顺的情况
//                                cardsToShow = getCardAgainstFlush(cardPack_5, cardsShowed);
//                                if (cardsToShow != null)
//                                    return 1;
//                                else return 0;
//                                //如果返回值为null，则pass(
//                            }
//                            case 2: //四带一的情况
//                                cardsToShow = getCardsAgainstFour_one(cardPack_5, cardsShowed);
//                                //如果返回值为null，则pass(
//                                if (cardsToShow != null)
//                                    return 1;
//                                else return 0;
//                            case 3: //三带二的情况
//                                cardsToShow = getCardsAgainstThree_Two(cardPack_5, cardsShowed);
//                                //如果返回值为null，则pass(
//                                if (cardsToShow != null)
//                                    return 1;
//                                else return 0;
//                            case 4://同花
//                                cardsToShow = getCardsAgainstFive_The_Same_Type(cardPack_5, cardsShowed);
//                                //如果返回值为null，则pass(
//                                if (cardsToShow != null)
//                                    return 1;
//                                else return 0;
//                            case 5://顺子
//                                cardsToShow = getCardsAgainstShunZi(cardPack_5, cardsShowed);
//                                //如果返回值为null，则pass(
//                                if (cardsToShow != null)
//                                    return 1;
//                                else return 0;
//                            default:
//                                break;
//                        }
                    cardsToShow=getCardAgainstFiveCardsShowed(cardsShowed);
                    if (cardsToShow != null)
                        return 1;
                    else return 0;
                }

                Log.d("t944o", "五张pass");
                pass();
                return 0;
            default:
                return 0;
        }
        return 0;
    }


    public void sequentialCardMatch(ArrayList<ArrayList<Card>> cardPack_5, ArrayList<ArrayList<Card>> cardBucket, ArrayList<Card> cardsSample, int... i) {
        int cardType = Ruler.judgeCardType(cardsSample);
        if (cardType == 0 || cardType == 4) {//判定为顺子
            for (int i1 = 0; i1 < cardBucket.get(i[0]).size(); i1++) {
                //第一层循环, 放入第一张
                Card card_1 = cardBucket.get(i[0]).get(i1);
                for (int i2 = 0; i2 < cardBucket.get(i[1]).size(); i2++) {
                    //第二层循环, 放入第二张
                    Card card_2 = cardBucket.get(i[1]).get(i2);
                    for (int i3 = 0; i3 < cardBucket.get(i[2]).size(); i3++) {
                        //第三层循环, 放入第三张
                        Card card_3 = cardBucket.get(i[2]).get(i3);
                        for (int i4 = 0; i4 < cardBucket.get(i[3]).size(); i4++) {
                            //第四层循环, 放入第四张
                            Card card_4 = cardBucket.get(i[3]).get(i4);
                            for (int i5 = 0; i5 < cardBucket.get(i[4]).size(); i5++) {
                                //第五层循环, 放入第五张
                                Card card_5 = cardBucket.get(i[4]).get(i5);
                                //把以上数组合并
                                ArrayList<Card> cards = new ArrayList<>();
                                cards.add(card_1);
                                cards.add(card_2);
                                cards.add(card_3);
                                cards.add(card_4);
                                cards.add(card_5);

                                cardPack_5.add(cards);

                                cards=null;
                            }
                        }
                    }
                }
            }
        }
    }

    //
    //返回一个boolean值，如果为真，则可以pass，否则不能
    public boolean canPass(){
        return Ruler.getIdOfPlayerWhoShowCardSuccessfully() != this.getPlayerId();
    }

    public void pass(){
        if(canPass()) {
            Log.d("ee","rr");
            removeAllCardsToShow();//回合第一个出牌的人不能跳过
            Ruler.toTheTurnOfNextPlayer(this);

        }
    }

    public void fiveSameTypeMatch(ArrayList<Card> cards_1){
        if(cards_1.size()==5)
            cardPack_5.add(cards_1);
        else if(cards_1.size()>5)
        {
            for (int i = 0; i <(cards_1.size()-5) ; i++) {
                ArrayList<Card> cards = new ArrayList<>();
                cards.add(cards_1.get(i));
                cards.add(cards_1.get(i+1));
                cards.add(cards_1.get(i+2));
                cards.add(cards_1.get(i+3));
                cards.add(cards_1.get(i+4));
                cardPack_5.add(cards);
            }

        }
    }

}