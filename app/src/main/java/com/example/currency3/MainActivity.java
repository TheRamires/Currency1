package com.example.currency3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.example.currency3.ViewPagerInstruments.ViewPagerAdapter;
import com.example.currency3.ViewPagerInstruments.ZoomOutPageTransformer;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;


public class MainActivity extends AppCompatActivity {
    public static final String LOG="myLog";
    // Om many padme hum

    private Model model;
    private ViewModelCurrency viewModel;
    private ViewPager2 viewPager;
    private TabLayout tabLayout;
    private FragmentStateAdapter pagerAdapter;
    private String [] titleList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        titleList=getResources().getStringArray(R.array.title);

        //Base modules-------------------------------------------------------------------
        viewModel=new ViewModelProvider(this).get(ViewModelCurrency.class);
        viewModel.singleHistorical();
        viewModel.currencyDatas();

        //-------------------------------------View Pager---------------------------------
        tabLayout=findViewById(R.id.tabs);
        viewPager=findViewById(R.id.pager);
        viewPager.setPageTransformer(new ZoomOutPageTransformer());
        pagerAdapter = new ViewPagerAdapter(this);
        viewPager.setAdapter(pagerAdapter);
        new TabLayoutMediator(tabLayout, viewPager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                tab.setText(titleList[position]);
            }
        }).attach();

        //Перелистывание кнопкой toChart------
        viewModel.liveToChart.observe(this, (Boolean aBoolean) ->{
            changePage();
        });
    }
    //-------------------------------------View Pager methods---------------------------------
    @Override
    public void onBackPressed() {
        if (viewPager.getCurrentItem() == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed();
        } else {
            // Otherwise, select the previous step.
            viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
        }
    }
    private void changePage(){
        if (viewPager.getCurrentItem()==1){
            viewPager.setCurrentItem(viewPager.getCurrentItem()-1);

        }
    }
}