package com.example.hospital;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class NavigateAdapterFrag extends FragmentPagerAdapter {

    private final int numOfTaps;

    public NavigateAdapterFrag(FragmentManager fm, int numOfTaps)
    {
        super(fm);
        this.numOfTaps = numOfTaps;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        System.out.println("--> " +position);
        switch (position)
        {
            case 0:
                return new ProfileFrag();
            case 1:
                return new HomeFrag();
            case 2:
                return new ProfileFrag();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numOfTaps;
    }
}
