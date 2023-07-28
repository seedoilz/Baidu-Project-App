package com.seedoilz.mybrowser.viewmodel;


import androidx.lifecycle.MutableLiveData;

import com.seedoilz.mybrowser.db.bean.Province;
import com.seedoilz.mybrowser.repository.CityRepository;
import com.seedoilz.library.base.BaseViewModel;

import java.util.List;


public class SplashViewModel extends BaseViewModel {

    public MutableLiveData<List<Province>> listMutableLiveData = new MutableLiveData<>();

    
    public void addCityData(List<Province> provinceList) {
        CityRepository.getInstance().addCityData(provinceList);
    }

    
    public void getAllCityData() {
        CityRepository.getInstance().getCityData(listMutableLiveData);
    }

}
