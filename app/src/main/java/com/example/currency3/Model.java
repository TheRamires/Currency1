package com.example.currency3;

import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;

import com.example.currency3.pojo.Currency;
import com.example.currency3.pojo.Single;

import java.util.LinkedHashMap;
import java.util.List;

public interface Model {
    public void requestCurrencyApi(MutableLiveData<List<Currency>> liveCurrency);
    public void requestHistorical(ObservableField<List<String>> observableCurrency,
                                  MutableLiveData<LinkedHashMap<String,Float>> liveDataSet,
                                  String currencyArgument, String dataStart, String dataEnd);
    public void singleRequest(ObservableField<List<Single>> observableSingle,
                              String currencyArgument, String data);
    public void getCurrency(MutableLiveData<List<Currency>> liveCurrency);
    public void insertDb(List<Currency> currencies);
}
