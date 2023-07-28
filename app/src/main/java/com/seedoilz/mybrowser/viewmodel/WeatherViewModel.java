package com.seedoilz.mybrowser.viewmodel;

import androidx.lifecycle.MutableLiveData;

import com.seedoilz.mybrowser.db.bean.DailyResponse;
import com.seedoilz.mybrowser.db.bean.HourlyResponse;
import com.seedoilz.mybrowser.db.bean.NowResponse;
import com.seedoilz.mybrowser.db.bean.Province;
import com.seedoilz.mybrowser.db.bean.SearchCityResponse;
import com.seedoilz.mybrowser.repository.CityRepository;
import com.seedoilz.mybrowser.repository.SearchCityRepository;
import com.seedoilz.mybrowser.repository.WeatherRepository;
import com.seedoilz.library.base.BaseViewModel;

import java.util.List;


public class WeatherViewModel extends BaseViewModel {

    public MutableLiveData<SearchCityResponse> searchCityResponseMutableLiveData = new MutableLiveData<>();

    public MutableLiveData<NowResponse> nowResponseMutableLiveData = new MutableLiveData<>();

    public MutableLiveData<DailyResponse> dailyResponseMutableLiveData = new MutableLiveData<>();

    public MutableLiveData<List<Province>> cityMutableLiveData = new MutableLiveData<>();

    public MutableLiveData<HourlyResponse> hourlyResponseMutableLiveData = new MutableLiveData<>();

    
    public void searchCity(String cityName) {
        SearchCityRepository.getInstance().searchCity(searchCityResponseMutableLiveData, failed, cityName);
    }

    
    public void nowWeather(String cityId) {
        WeatherRepository.getInstance().nowWeather(nowResponseMutableLiveData, failed, cityId);
    }

    
    public void dailyWeather(String cityId) {
        WeatherRepository.getInstance().dailyWeather(dailyResponseMutableLiveData, failed, cityId);
    }


    
    public void getAllCity() {
        CityRepository.getInstance().getCityData(cityMutableLiveData);
    }

    
    public void hourlyWeather(String cityId) {
        WeatherRepository.getInstance().hourlyWeather(hourlyResponseMutableLiveData, failed, cityId);
    }
}
