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
import com.seedoilz.mybrowser.activity.WeatherActivity;

import java.util.List;

/**
 * 主页面ViewModel
 * {@link WeatherActivity}
 */
public class MainViewModel extends BaseViewModel {

    public MutableLiveData<SearchCityResponse> searchCityResponseMutableLiveData = new MutableLiveData<>();

    public MutableLiveData<NowResponse> nowResponseMutableLiveData = new MutableLiveData<>();

    public MutableLiveData<DailyResponse> dailyResponseMutableLiveData = new MutableLiveData<>();

    public MutableLiveData<List<Province>> cityMutableLiveData = new MutableLiveData<>();

    public MutableLiveData<HourlyResponse> hourlyResponseMutableLiveData = new MutableLiveData<>();

    /**
     * 搜索城市
     *
     * @param cityName 城市名称
     */
    public void searchCity(String cityName) {
        SearchCityRepository.getInstance().searchCity(searchCityResponseMutableLiveData, failed, cityName);
    }

    /**
     * 实况天气
     *
     * @param cityId 城市ID
     */
    public void nowWeather(String cityId) {
        WeatherRepository.getInstance().nowWeather(nowResponseMutableLiveData, failed, cityId);
    }

    /**
     * 天气预报
     *
     * @param cityId 城市ID
     */
    public void dailyWeather(String cityId) {
        WeatherRepository.getInstance().dailyWeather(dailyResponseMutableLiveData, failed, cityId);
    }


    /**
     * 获取行政区数据
     */
    public void getAllCity() {
        CityRepository.getInstance().getCityData(cityMutableLiveData);
    }

    /**
     * 逐小时天气预报
     *
     * @param cityId 城市ID
     */
    public void hourlyWeather(String cityId) {
        WeatherRepository.getInstance().hourlyWeather(hourlyResponseMutableLiveData, failed, cityId);
    }
}
