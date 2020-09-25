package com.example.hospital;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class PagerAdapterFreg extends FragmentPagerAdapter {
private int numoftaps;
   public PagerAdapterFreg(FragmentManager fm,int numoftaps)
   {
        super(fm);
        this.numoftaps=numoftaps;

   }


    @Override
    public Fragment getItem(int position) {
       if(position==0)
           return  new AdminRemoveFrag();
       else if(position==1)
        return  new AdminAddFrag();
        return null;
    }

    @Override
    public int getCount() {
        return numoftaps;
    }
}
