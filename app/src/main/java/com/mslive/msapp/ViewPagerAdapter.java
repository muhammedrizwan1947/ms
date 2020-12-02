package com.mslive.msapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private Fragment[] childFragments;

    public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
        childFragments= new Fragment[]{new FourFragment(),new TwoFragment(),new ThreeFragment()};
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
       /* switch (position){
            case 0 :
                return new AllFragment();
            case 1 :
                return new LastDateFragment();
            case 2 :
                return new SavedFragment();
*/

        return childFragments[position];


        //  }
    }

    @Override
    public int getCount() {
        return childFragments.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        //  String title=getItem(position).getClass().getName();
        // return title.subSequence(title.lastIndexOf(".")+1,title.length());

        String title="All";

        if (position==0){
            title="All";

        }

        else if (position==1){
            title="Ending";
        }

        else {
            title = "Saved";
        }

        return title;
    }
}

