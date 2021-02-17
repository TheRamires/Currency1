package com.ramires.currency3;

import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.LinkedHashMap;
import java.util.List;

import ram.ramires.currency3.ModelCurrency;
import ram.ramires.currency3.pojo.Currency;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class ModelTest {

    ObservableField<List<String>> observableCurrency;
    MutableLiveData<LinkedHashMap<String, Float>> liveDataSet;
    String currencyArgument;
    String dataStart;
    String dataEnd;
    @Mock
    ModelCurrency model;
    List<Currency> currencyList;
    @Mock
    Currency currency;

    @Before
    public void setUp(){
        observableCurrency=new ObservableField<>();
        liveDataSet= new MutableLiveData<>();
        currencyArgument="USD_RUB";
        dataStart="2021-02-07";
        dataEnd="2021-02-09";
    }

    @Test
    public void requestCurrencyApi() throws InterruptedException {
        model.requestHistorical(observableCurrency,
                liveDataSet, currencyArgument,dataStart, dataEnd);
        //Mockito.verify(model).unpacking_response_historical(Mockito.any(), Mockito.any(),Mockito.any());
    }

    /*@Test
    public void getData() {
        storage =Mockito.mock(Storage.class);
        Mockito.doReturn(Maybe.just(currencyList)).when(storage).queryCurrency();

        TestObserver<List<Currency>> testObserver=model.requestCurrencyApi(liveCurrency);
        testObserver.assertNoErrors();

    }*/
    /*
    @Test
    public void doReps() {
        // Make sure you are swapping the actual Scheduler with our TestScheduler object here
        TestScheduler testScheduler = new TestScheduler();
        Mockito.doReturn(testScheduler)
                .when(viewModel)
                .getSchedulerIo();
        // Other preparations
        Mockito.doReturn(Single.just(123))
                .when(viewModel)
                .fetchDataRemote();

        // Read carefully, this is very important
        TestObserver<Integer> testObserver = viewModel.doReps()
                .subscribeOn(testScheduler)
                .observeOn(testScheduler)
                .test();

        testObserver.assertNotTerminated() // not compulsory, but STRONGLY recommended
                .assertNoErrors()
                .assertValueCount(0);// "time" hasn't started so no value expected

        testScheduler.advanceTimeBy(1L, TimeUnit.SECONDS);
        testObserver.assertValueCount(1);// 1 value expected after the initial delay of 1 second
        testScheduler.advanceTimeBy(5L, TimeUnit.SECONDS);
        testObserver.assertValueCount(2);// 1st interval, 1 more value expected
        testScheduler.advanceTimeBy(5L, TimeUnit.SECONDS);
        testObserver.assertValueCount(3);// 2nd interval, 1 more value

        // Clean up resources, not compulsory, but STRONGLY recommended
        testObserver.dispose();*/
}
