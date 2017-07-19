package com.howell.bean.httpbean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/4/19.
 */

public class RFIDCardList {
    Page page;
    ArrayList<RFIDCard> cards;

    @Override
    public String toString() {
        return "RFIDCardList{" +
                "page=" + page +
                ", cards=" + cards +
                '}';
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public ArrayList<RFIDCard> getCards() {
        return cards;
    }

    public void setCards(ArrayList<RFIDCard> cards) {
        this.cards = cards;
    }

    public RFIDCardList() {

    }

    public RFIDCardList(Page page, ArrayList<RFIDCard> cards) {

        this.page = page;
        this.cards = cards;
    }
}
