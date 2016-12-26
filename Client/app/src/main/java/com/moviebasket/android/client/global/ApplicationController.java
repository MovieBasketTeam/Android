package com.moviebasket.android.client.global;

import android.app.Application;

/**
 * Created by LEECM on 2016-12-26.
 */

public class ApplicationController extends Application{

    private static ApplicationController instance;

    public  ApplicationController(){
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if(instance==null){
            instance = new ApplicationController();
        }
        instance = this;

    }


    /**
     *  Getter & Setter
     */
    public static ApplicationController getInstance() {
        return instance;
    }
}
