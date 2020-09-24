package com.example.hospital;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.widget.Toast;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

public class NavigateActivity extends AppCompatActivity {
    public int pageIndex;
    private Patient patient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigate);

        patient = (Patient) getIntent().getSerializableExtra("Patient");
        pageIndex = Integer.parseInt(getIntent().getExtras().get("pageIndex").toString());

        TabLayout tabLayout=findViewById(R.id.tab_Layout);
        TabItem removeTab = findViewById(R.id.profile_tab);
        TabItem addTab = findViewById(R.id.home_tab);
        TabItem editTab = findViewById(R.id.test_tab);
        final ViewPager viewPager = findViewById(R.id.view_Pager);
        NavigateAdapterFrag pagerAdapter= new NavigateAdapterFrag(getSupportFragmentManager(),tabLayout.getTabCount());
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        viewPager.setCurrentItem(pageIndex);
        Toast.makeText(this,"Welcome " + patient.getName() ,Toast.LENGTH_LONG).show();
    }
}