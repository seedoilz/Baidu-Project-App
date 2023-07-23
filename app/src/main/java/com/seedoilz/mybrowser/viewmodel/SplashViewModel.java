package com.seedoilz.mybrowser.viewmodel;


import androidx.lifecycle.MutableLiveData;

import com.seedoilz.mybrowser.activity.SplashActivity;
import com.seedoilz.mybrowser.db.bean.Province;
import com.seedoilz.mybrowser.repository.CityRepository;
import com.seedoilz.library.base.BaseViewModel;

import java.util.List;

/**
 * 启动页ViewModel
 * {@link SplashActivity}
 */
public class SplashViewModel extends BaseViewModel {

    public MutableLiveData<List<Province>> listMutableLiveData = new MutableLiveData<>();

    /**
     * 添加城市数据
     */
    public void addCityData(List<Province> provinceList) {
        CityRepository.getInstance().addCityData(provinceList);
    }

    /**
     * 获取所有城市数据
     */
    public void getAllCityData() {
        CityRepository.getInstance().getCityData(listMutableLiveData);
    }

}
