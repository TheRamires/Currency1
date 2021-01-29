package ram.ramires.currency3;
import android.util.Log;

import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;

import ram.ramires.currency3.Room.AppDatabase;
import ram.ramires.currency3.Room.DaoCurrency;
import ram.ramires.currency3.pojo.Currency;
import ram.ramires.currency3.pojo.Historical;
import ram.ramires.currency3.pojo.Single;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ModelCurrency implements Model {
    private AppDatabase db = App.getInstance().getDatabase();
    private DaoCurrency dao = db.daoCurrency();
    private List<String> listCurrencyKey = new ArrayList<>();
    private List<Currency> valueList = new ArrayList<>();
    public MutableLiveData<List<String>> listDates;
    List<String> listCoast;

    private ApiCurrency creatApiCurrency() {
        return new Retrofit.Builder().
                baseUrl("https://free.currconv.com/api/v7/").
                addConverterFactory(GsonConverterFactory.create()).
                addCallAdapterFactory(RxJava2CallAdapterFactory.create()).
                build().
                create(ApiCurrency.class);
    }
    @Override
    public void requestCurrencyApi(MutableLiveData<List<Currency>> liveCurrency) {
        creatApiCurrency().getCurrency()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorResumeNext(throwable -> {
                    Log.d(MainActivity.LOG, "requestCurrencyApi error " + throwable);
                })
                .map((@NonNull Map<String, Map<String, Currency>> response) -> {
                    Map<String, Currency> map = response.get("results");
                    listCurrencyKey.clear();
                    listCurrencyKey.addAll(map.keySet());

                    List<Currency> tempList = new ArrayList<>(map.values());

                    valueList.clear();
                    valueList.addAll(iterateCurrency(tempList));
                    new Thread(() -> {
                        //insert DB
                        dao.insert(valueList);
                    }).start();
                    return valueList;
                })
                .subscribe((@NonNull List<Currency> currencies) -> {
                    Log.d(MainActivity.LOG, "requestCurrencyApi: "+currencies.toString());
                    liveCurrency.setValue(currencies);

                });
    }

    @Override
    public void requestHistorical(ObservableField<List<String>> observableCurrency,
                                  MutableLiveData<LinkedHashMap<String, Float>> liveDataSet,
                                  String currencyArgument, String dataStart, String dataEnd) {
        creatApiCurrency().getHistooricalRange(currencyArgument, dataStart, dataEnd)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorResumeNext(throwable -> {
                    Log.d(MainActivity.LOG, "requestHistorical error " + throwable);
                })
                .subscribe((@NonNull LinkedHashMap<String, Historical> response) -> {
                    Log.d(MainActivity.LOG, "requestHistorical: "+response.toString());

                    unpacking_response_historical(observableCurrency, liveDataSet, response);
                });
    }
    @Override
    public void singleRequest(ObservableField<List<Single>> observableSingle,
                              String currencyArgument, String data){
        creatApiCurrency().getSingleData(currencyArgument, data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorResumeNext(throwable -> {
                    Log.d(MainActivity.LOG, "singleRequest error " + throwable);
                })
                .subscribe((@NonNull Map<String, Map<String, Float>> response) ->{
                    Log.d(MainActivity.LOG, "singleRequest: "+response.toString());
                    unpacking_single_historical(response, observableSingle);

                });
    }

    @Override
    public void getCurrency(MutableLiveData<List<Currency>> liveCurrency) {
        //from DB
        dao.queryCurency()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((List<Currency> currencies) -> {
                    if (currencies.size() == 0) {
                        requestCurrencyApi(liveCurrency);
                    } else {
                        liveCurrency.setValue(currencies);
                        Log.d(MainActivity.LOG, "GetCurrency from bd is successful ☺ "+currencies);
                    }
                });
    }

    @Override
    public void insertDb(List<Currency> currencies) {
        new Thread(() -> {
            dao.insert(currencies);
        }).start();
    }

    //unpacking func--------------------------------------------------------------------

    private void unpacking_response_historical(ObservableField<List<String>> observableCurrency,
                                               MutableLiveData<LinkedHashMap<String, Float>> liveDataSet,
                                               LinkedHashMap<String, Historical> response) {

        // keyCurrency, For Example USD_RUB, RUB_USD
        LinkedHashMap<String, Historical> map = response;
        List<String> keyCurrency = new ArrayList<>(map.keySet());
        String key1 = keyCurrency.get(0);  //first currency argument

        LinkedHashMap<String, Float> set1 = map.get(key1);
        liveDataSet.setValue(set1);

        //datas
        List<String> dates1 = new ArrayList<>(set1.keySet());
        List<Float> rate1 = new ArrayList<>(set1.values());

        double i1 = Math.round(rate1.get(rate1.size() - 1) * Math.pow(10, 3)) / Math.pow(10, 3);
        listCoast = new ArrayList<>();
        listCoast.add(0, String.valueOf(i1));
/*
        Log.d(LOG, ">> Unpacking_response_of_request_historical: " + key1 + " << ☺ ");
        for (String data1 : dates1) {
            Log.d(LOG,  data1 + " : " +set1.get(data1));
        }*/
        //Данные для курса валют
        String arg1 = keyCurrency.get(0);
        arg1 = arg1.replace("_", "/");
        List<String> list = new ArrayList<>();
        list.add(arg1);

        observableCurrency.set(list);

    }
    private void unpacking_single_historical(Map<String, Map<String, Float>> response,
                                             ObservableField<List<Single>> observableSingle){
        List<Single> listSingle=new ArrayList<>();

        List<String> currencyList=new ArrayList<>(response.keySet()); //Currency list
        Map<String,Float> map1=response.get(currencyList.get(0)); //SingleSet 1
        Map<String,Float> map2=response.get(currencyList.get(1)); //SingleSet 2

        listSingle.add(getSingle(currencyList.get(0),map1));
        listSingle.add(getSingle(currencyList.get(1),map2));
        observableSingle.set(listSingle);
    }
    private Single getSingle(String currency, Map<String,Float> map){
        Single single=new Single();

        List<Float> listF= new ArrayList<>( map.values());
        double i1 = Math.round(listF.get(0) * Math.pow(10, 3)) / Math.pow(10, 3);

        single.setCurrency(currency.replace("_","/"));
        single.setCoast(String.valueOf(i1));
        return single;
    }

    private List<Currency> iterateCurrency(List<Currency> currencys) {
        List<Currency> list = new ArrayList<>(currencys);
        Collections.sort(list, (Currency o1, Currency o2) -> {
            return o1.getCurrencyName().compareTo(o2.getCurrencyName());
        });

        List<Currency> list2 = new ArrayList<>();

        list2.add(list.get(0));
        for (int i=1;i<list.size();i++){
            if (list.get(i).getCurrencyName().equalsIgnoreCase(list.get(i-1).getCurrencyName())){
                i++;
            }else {
                list2.add(list.get(i));}
        }
        return list2;
    }
}

