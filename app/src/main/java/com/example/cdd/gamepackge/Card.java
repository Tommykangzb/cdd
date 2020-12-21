package com.example.cdd.gamepackge;

//方块3的id：41
//黑桃2的id：1
//红桃2的id：14
//梅花2的id：27
//方块2的id：40
public class Card{
    private int cardId;
    private CardType cardType;
    private int cardValue;
    private CardState cardState;
    private int owner;

    public Card(){
        cardId = -1;
        cardType = CardType.SPADE;
        cardValue = -1;
        cardState = CardState.Passed;
        owner = -1;
    }

    public Card(int _cardId, CardType _cardType, int _cardValue, CardState _cardState, int _owner){
        cardId = _cardId;
        cardType = _cardType;
        cardValue = _cardValue;
        cardState = _cardState;
        owner = _owner;
    }

    public CardType getCardType() {
        return cardType;
    }

    public int getCardValue() {
        return cardValue;
    }

    public int getCardId() {
        return cardId;
    }

    public CardState getCardState() {
        return cardState;
    }

    public int getOwner() {
        return owner;
    }

    public void setCardId(int cardId) {
        this.cardId = cardId;
    }

    public void setCardValue(int _cardValue) {
        cardValue = _cardValue;
    }

    public void setCardType(CardType cardType) {
        this.cardType = cardType;
    }

    public void setCardState(CardState cardState) {
        this.cardState = cardState;
    }

    public void setOwner(int owner) {
        this.owner = owner;
    }
}