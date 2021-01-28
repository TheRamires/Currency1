package com.example.currency3;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.currency3.databinding.FragmentBackToChartBinding;

public class FragmentBackToChart extends Fragment {
    ViewModelCurrency viewModel;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        viewModel=new ViewModelProvider(requireActivity()).get(ViewModelCurrency.class);
        FragmentBackToChartBinding binding=FragmentBackToChartBinding.inflate(inflater, container, false);
        binding.setBactToChart(this);
        return binding.getRoot();
    }
    public void backToChart(View view){viewModel.toChart();
    }
}