package ram.ramires.currency3;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ram.ramires.currency3.R;

import ram.ramires.currency3.pojo.Currency;
import com.wx.wheelview.widget.WheelView;

import java.util.List;

import static ram.ramires.currency3.MainActivity.LOG;

public class FragmentCurrency extends Fragment{
    private ViewModelCurrency viewModel;
    private StringBuilder argument=new StringBuilder();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_currency, container, false);
        viewModel=new ViewModelProvider(requireActivity()).get(ViewModelCurrency.class);

        if (viewModel.currencyArgument.length()!=0) {
            argument.append(viewModel.currencyArgument.substring(0, 3));
            argument.append("_");
            argument.append(viewModel.currencyArgument.substring(4, 7));
        }

        viewModel.liveCurrency.observe(getViewLifecycleOwner(), new Observer<List<Currency>>() {
            @Override
            public void onChanged(List<Currency> currencies) {
                //Wheel View 1------------------------------------------------------------------
                WheelView wheelView= (WheelView) requireActivity().findViewById(R.id.wheelview1);

                WheelCustomAdapter adapter=new WheelCustomAdapter(currencies);
                WheelView.WheelViewStyle style=new WheelView.WheelViewStyle();
                style.holoBorderColor= Color.BLACK;
                style.selectedTextZoom=1.2f;
                wheelView.setStyle(style);

                wheelView.setWheelAdapter(adapter); // 文本数据源
                wheelView.setSkin(WheelView.Skin.Holo); // common皮肤
                wheelView.setWheelData( currencies);  // 数据集合
                wheelView.setWheelSize(11);
                int x1=getCurrentId(currencies)[0];
                wheelView.setSelection(x1);

                wheelView.setOnWheelItemSelectedListener(new WheelView.OnWheelItemSelectedListener() {
                    @Override
                    public void onItemSelected(int position, Object o) {
                        Currency currency=(Currency)o;
                        argument.replace(0,3,currency.getCurrencyId());
                        viewModel.currencyArgument=argument.toString();
                    }
                });
                //Wheel View 2------------------------------------------------------------------
                WheelView wheelView2 = (WheelView) requireActivity().findViewById(R.id.wheelview2);

                wheelView2.setStyle(style);
                wheelView2.setWheelAdapter(adapter); // 文本数据源
                wheelView2.setSkin(WheelView.Skin.Holo); // common皮肤
                wheelView2.setWheelData( currencies);  // 数据集合
                wheelView2.setWheelSize(11);
                int x2=getCurrentId(currencies)[1];
                wheelView2.setSelection(x2);

                wheelView2.setOnWheelItemSelectedListener(new WheelView.OnWheelItemSelectedListener() {
                    @Override
                    public void onItemSelected(int position, Object o) {
                        Currency currency=(Currency)o;
                        argument.replace(4,7,currency.getCurrencyId());
                        viewModel.currencyArgument=argument.toString();
                    }
                });
            }
        });
        return view;
    }
    private int[] getCurrentId (List<Currency> currencies){
        int [] id= {141,109};
        if (viewModel.currencyArgument.length()==0){
            return id;
        }
        String arg1=viewModel.currencyArgument.substring(0,3);
        String arg2=viewModel.currencyArgument.substring(4,7);
        for (int i=0;i<currencies.size();i++){
            if(currencies.get(i).getCurrencyId().equals(arg1)){
                id[0]=i;
            } else if (currencies.get(i).getCurrencyId().equals(arg2)){
                id[1]=i;
            }
        }
        Log.d(LOG,"Currensyies ID a/b : "+id[0]+"/"+id[1]);
        return id;
    }
}