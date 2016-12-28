package com.moviebasket.android.client.mypage.basket_list;

/**
 * Created by pilju on 2016-12-28.
 */

public class BasketListDatas {
    int basketImg;
    int textImg;
    String basketName;
    int downBtn;
    String downCount;

    public BasketListDatas(int basketImg, int textImg, String basketName, int downBtn, String downCount) {
        this.basketImg = basketImg;
        this.textImg = textImg;
        this.basketName = basketName;
        this.downBtn = downBtn;
        this.downCount = downCount;
    }
}