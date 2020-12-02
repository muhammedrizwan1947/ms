package com.mslive.msapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class ViewPagerAdapter2 extends FragmentPagerAdapter {

    private Fragment[] childFragments;

    public ViewPagerAdapter2(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
        childFragments= new Fragment[]{new FourFragment()};
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {


        return childFragments[position];


        //  }
    }

    @Override
    public int getCount() {
        return childFragments.length;
    }


}

