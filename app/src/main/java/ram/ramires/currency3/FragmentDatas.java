package ram.ramires.currency3;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ram.ramires.currency3.databinding.FragmentDatasBinding;

public class FragmentDatas extends Fragment {
    ViewModelCurrency viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        viewModel=new ViewModelProvider(requireActivity()).get(ViewModelCurrency.class);
        FragmentDatasBinding binding=FragmentDatasBinding.inflate(inflater, container, false);
        binding.setData(viewModel);
        View view=binding.getRoot();
        // Inflate the layout for this fragment
        return view;
    }
}