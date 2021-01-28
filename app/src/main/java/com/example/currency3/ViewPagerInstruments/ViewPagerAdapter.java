package com.example.currency3.ViewPagerInstruments;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.currency3.FragmentChart;
import com.example.currency3.FragmentCurrency;

public class ViewPagerAdapter extends FragmentStateAdapter {

    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new FragmentChart();
            case 1:
                return new FragmentCurrency();

        }
        return null;
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}