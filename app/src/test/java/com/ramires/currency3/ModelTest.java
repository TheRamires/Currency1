package com.ramires.currency3;

import androidx.lifecycle.MutableLiveData;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.List;

import ram.ramires.currency3.Model;
import ram.ramires.currency3.ModelCurrency;
import ram.ramires.currency3.pojo.Currency;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(JUnit4.class)
public class ModelTest {
    Model modelTest;
    MutableLiveData<List<Currency>> liveCurrency=new MutableLiveData<>();
    @Before
    public void setUo() throws Exception{
        modelTest=new ModelCurrency();
    }
    @After
    public void tearDown() throws Exception{
        modelTest=null;
    }
    @Test
    public void requestCurrencyApi() throws InterruptedException {
        modelTest.requestCurrencyApi(liveCurrency);
        Thread.sleep(200);
        assertNotNull("Currency", liveCurrency.getValue());
    }
}

