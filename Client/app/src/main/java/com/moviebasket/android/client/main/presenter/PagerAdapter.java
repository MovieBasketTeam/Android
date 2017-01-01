package com.moviebasket.android.client.main.presenter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.moviebasket.android.client.main.model.NewFragment;
import com.moviebasket.android.client.main.model.PopluarFragment;
import com.moviebasket.android.client.main.model.RecommendFragment;

/**
 * Created by kh on 2016. 12. 7..
 */
public class PagerAdapter extends FragmentStatePagerAdapter {


    public Fragment[] fragments = new Fragment[3];

    public PagerAdapter(FragmentManager fm) {
        super(fm);
        fragments[0] = new NewFragment();
        fragments[1] = new PopluarFragment();
        fragments[2] = new RecommendFragment();
    }
    public Fragment getItem(int arg0) {
        return fragments[arg0];
    }
    public int getCount() {
        return fragments.length;
    }


}