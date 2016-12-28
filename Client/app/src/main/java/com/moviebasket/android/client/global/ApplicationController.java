package com.moviebasket.android.client.global;

import android.app.Application;

import com.moviebasket.android.client.network.MBService;
import com.moviebasket.android.client.network.NaverService;
import com.moviebasket.android.client.security.SecurityDataSet;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by LEECM on 2016-12-26.
 */

public class ApplicationController extends Application{

    private static final String naverURL = "https://openapi.naver.com/v1/search/";
    private static final String MovieBasketURL = SecurityDataSet.MBServerUrl;
    private static ApplicationController instance;

    private MBService mbService;
    private NaverService naverService;

    public  ApplicationController(){
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if(instance==null){
            instance = new ApplicationController();
        }
        instance = this;

        //NaverService Build
        buildNaverService();
        //MBService Build
        buildMBService();
    }

    /**
     *  Getter & Setter
     */
    public static ApplicationController getInstance() {
        return instance;
    }

    public MBService getMbService() {
        return mbService;
    }

    public NaverService getNaverService() {
        return naverService;
    }

    /**
     *  methods
     */
    public void buildNaverService(){



        Retrofit.Builder builder = new Retrofit.Builder();
        Retrofit retrofit = builder
                .baseUrl(naverURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        naverService = retrofit.create(NaverService.class);
    }
    public void buildMBService(){
        Retrofit.Builder builder = new Retrofit.Builder();
        Retrofit retrofit = builder
                .baseUrl(MovieBasketURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mbService = retrofit.create(MBService.class);
    }

}
