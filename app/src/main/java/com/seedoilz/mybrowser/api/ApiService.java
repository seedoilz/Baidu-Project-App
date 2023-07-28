package com.seedoilz.mybrowser.api;

import static com.seedoilz.mybrowser.Constant.API_KEY;

import com.seedoilz.mybrowser.db.bean.DailyResponse;
import com.seedoilz.mybrowser.db.bean.HourlyResponse;
import com.seedoilz.mybrowser.db.bean.NowResponse;
import com.seedoilz.mybrowser.db.bean.SearchCityResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface ApiService {

    
    @GET("/v2/city/lookup?key=" + API_KEY + "&range=cn")
    Observable<SearchCityResponse> searchCity(@Query("location") String location);

    
    @GET("/v7/weather/now?key=" + API_KEY)
    Observable<NowResponse> nowWeather(@Query("location") String location);

    
    @GET("/v7/weather/7d?key=" + API_KEY)
    Observable<DailyResponse> dailyWeather(@Query("location") String location);

    
    @GET("/v7/weather/24h?key=" + API_KEY)
    Observable<HourlyResponse> hourlyWeather(@Query("location") String location);

}
