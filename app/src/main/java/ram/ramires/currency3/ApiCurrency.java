package ram.ramires.currency3;

import ram.ramires.currency3.pojo.Currency;
import ram.ramires.currency3.pojo.Historical;

import java.util.LinkedHashMap;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiCurrency {
    @GET("countries?apiKey=635c15bac66f5d8a47be")
    Observable<Map<String, Map<String, Currency>>> getCurrency();

    @GET("convert?apiKey=635c15bac66f5d8a47be&compact=ultra")
    Observable<LinkedHashMap<String, Historical>> getHistooricalRange(
            @Query("q") String currency, @Query("date") String dateStart, @Query("endDate") String dateEnd);

    @GET("convert?apiKey=635c15bac66f5d8a47be&compact=ultra")
    Observable<Map<String, Map<String, Float>>> getSingleData(
            @Query("q") String currency, @Query("date") String date);
}