package com.example.currency3;
import android.util.Log;
import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.currency3.pojo.Currency;
import com.example.currency3.pojo.Single;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

public class ViewModelCurrency extends ViewModel {
    public ObservableField<List<String>> observableCurrency=new ObservableField<>();
    public ObservableField<String> observableCoast=new ObservableField<>();
    public ObservableField<String> observableDate=new ObservableField<>();
    public ObservableField<List<Single>> observableSingle=new ObservableField<>();

    public MutableLiveData<LinkedHashMap<String,Float>> liveDataSet=new MutableLiveData<>();
    public MutableLiveData<List<Currency>> liveCurrency=new MutableLiveData<>();
    public MutableLiveData<Boolean> liveToChart=new MutableLiveData<>();

    private Model model;
    String singleArgument="USD_RUB,EUR_RUB";
    String currencyArgument="USD_RUB";
    private Long dateRange;
    private DateCalculate date;

    public ViewModelCurrency(){
        model=new ModelCurrency();
        date=new DateCalculate();

    }
    public void currencyDatas(){
        model.getCurrency(liveCurrency);
    }

    public void singleHistorical(){
        model.singleRequest(observableSingle,singleArgument,date.dateEnd);
    }

    public void historicalDatas(){
        model.requestHistorical(observableCurrency, liveDataSet,
                currencyArgument, date.dateStart, date.dateEnd);

    }
    public void toChart(){
        liveToChart.setValue(true);
    }

    class DateCalculate{
        private String dateEnd;
        private String dateStart;
        Calendar calendar;
        SimpleDateFormat dateFormater;

        public DateCalculate(){
            calendar = Calendar.getInstance(); // this would default to now
            dateFormater=new SimpleDateFormat("yyyy-MM-dd");
            calculateDateRange();
        }


        private void calculateDateRange(){
            Date date_b=calendar.getTime();
            dateEnd= dateFormater.format(date_b);
            calendar.add(Calendar.DAY_OF_MONTH, -8);
            Date date_a=calendar.getTime();
            dateStart=dateFormater.format(date_a);
        }
    }
}
