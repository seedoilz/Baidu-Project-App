package com.seedoilz.mybrowser.activity;

import android.annotation.SuppressLint;
import android.os.Handler;

import androidx.lifecycle.ViewModelProvider;

import com.seedoilz.mybrowser.Constant;
import com.seedoilz.mybrowser.databinding.ActivitySplashBinding;
import com.seedoilz.mybrowser.db.bean.Province;
import com.seedoilz.mybrowser.utils.EasyDate;
import com.seedoilz.mybrowser.utils.MVUtils;
import com.seedoilz.mybrowser.viewmodel.SplashViewModel;
import com.seedoilz.library.base.NetworkActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@SuppressLint("CustomSplashScreen")
public class SplashActivity extends NetworkActivity<ActivitySplashBinding> {

    private SplashViewModel viewModel;

    @Override
    protected void onCreate() {
        setFullScreenImmersion();
        viewModel = new ViewModelProvider(this).get(SplashViewModel.class);
        checkingStartup();
        checkFirstRunToday();
        new Handler().postDelayed(() -> jumpActivityFinish(WeatherActivity.class), 300);
    }


    private void checkingStartup() {
        if (MVUtils.getBoolean(Constant.FIRST_RUN, false)) return;
        viewModel.getAllCityData();
        MVUtils.put(Constant.FIRST_RUN, true);
    }


    private void checkFirstRunToday() {
        long todayFirstRunTime = MVUtils.getLong(Constant.FIRST_STARTUP_TIME_TODAY);
        long currentTimeMillis = System.currentTimeMillis();
        if (todayFirstRunTime == 0 || !EasyDate.isToday(todayFirstRunTime)) {
            MVUtils.put(Constant.FIRST_STARTUP_TIME_TODAY, currentTimeMillis);
        }
    }

    @Override
    protected void onObserveData() {
        List<Province> provincess = loadCityData();
        if (provincess != null) viewModel.addCityData(provincess);
    }


    private List<Province> loadCityData() {
        List<Province> provinceList = new ArrayList<>();
        InputStream inputStream;
        try {
            inputStream = getResources().getAssets().open("city.txt");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuffer stringBuffer = new StringBuffer();
            String lines = bufferedReader.readLine();
            while (lines != null) {
                stringBuffer.append(lines);
                lines = bufferedReader.readLine();
            }
            final JSONArray jsonArray = new JSONArray(stringBuffer.toString());

            for (int i = 0; i < jsonArray.length(); i++) {
                Province province = new Province();
                List<Province.City> cityList = new ArrayList<>();
                JSONObject provinceJsonObject = jsonArray.getJSONObject(i);
                province.setProvinceName(provinceJsonObject.getString("name"));
                JSONArray cityJsonArray = provinceJsonObject.getJSONArray("city");
                for (int j = 0; j < cityJsonArray.length(); j++) {
                    Province.City city = new Province.City();
                    List<Province.City.Area> areaList = new ArrayList<>();
                    JSONObject cityJsonObject = cityJsonArray.getJSONObject(j);
                    city.setCityName(cityJsonObject.getString("name"));
                    JSONArray areaJsonArray = cityJsonObject.getJSONArray("area");
                    for (int k = 0; k < areaJsonArray.length(); k++) {
                        areaList.add(new Province.City.Area(areaJsonArray.getString(k)));
                    }
                    cityList.add(city);
                    city.setAreaList(areaList);
                }
                provinceList.add(province);
                province.setCityList(cityList);
            }
            return provinceList;
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}