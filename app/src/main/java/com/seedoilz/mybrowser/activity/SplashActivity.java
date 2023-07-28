package com.seedoilz.mybrowser.activity;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.util.Log;

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
    private final String TAG = SplashActivity.class.getSimpleName();

    @Override
    protected void onCreate() {
        setFullScreenImmersion();
        viewModel = new ViewModelProvider(this).get(SplashViewModel.class);
        //检查启动
        checkingStartup();
        //检查今天第一次运行
        checkFirstRunToday();
//        onObserveData();
        new Handler().postDelayed(() -> jumpActivityFinish(WeatherActivity.class), 300);
    }

    /**
     * 检查启动
     */
    private void checkingStartup() {
        if (MVUtils.getBoolean(Constant.FIRST_RUN, false)) return;
        //第一次运行，获取城市数据，没有就会去加载到数据库中
        viewModel.getAllCityData();
        MVUtils.put(Constant.FIRST_RUN, true);
    }

    /**
     * 检查今天第一次运行
     */
    private void checkFirstRunToday() {
        long todayFirstRunTime = MVUtils.getLong(Constant.FIRST_STARTUP_TIME_TODAY);
        long currentTimeMillis = System.currentTimeMillis();
        //满足更新启动时间的条件，1.为0表示没有保存过时间，2. 保存时间是否为今天
        if (todayFirstRunTime == 0 || !EasyDate.isToday(todayFirstRunTime)) {
            MVUtils.put(Constant.FIRST_STARTUP_TIME_TODAY, currentTimeMillis);
        }
    }

    @Override
    protected void onObserveData() {
        List<Province> provincess = loadCityData();
        if (provincess != null) viewModel.addCityData(provincess);
        //城市数据返回
//        viewModel.listMutableLiveData.observe(this, provinceList -> {
//            if (provinceList.size() == 0) {
//                Log.d(TAG, "onObserveData: 第一次添加数据");
//                //没有保存过数据，只需要保存一次即可。
//                List<Province> provinces = loadCityData();
//                if (provinces != null) viewModel.addCityData(provinces);
//            } else {
//                Log.d(TAG, "onObserveData: 有数据了");
//            }
//        });
//        //错误信息返回
//        viewModel.failed.observe(this, this::showLongMsg);
    }

    /**
     * 加载本地的城市数据
     */
    private List<Province> loadCityData() {
        List<Province> provinceList = new ArrayList<>();
        //读取城市数据
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
                //得到省份对象
                JSONObject provinceJsonObject = jsonArray.getJSONObject(i);
                province.setProvinceName(provinceJsonObject.getString("name"));
                //得到省份下的市数组
                JSONArray cityJsonArray = provinceJsonObject.getJSONArray("city");
                for (int j = 0; j < cityJsonArray.length(); j++) {
                    Province.City city = new Province.City();
                    List<Province.City.Area> areaList = new ArrayList<>();
                    //得到市对象
                    JSONObject cityJsonObject = cityJsonArray.getJSONObject(j);
                    city.setCityName(cityJsonObject.getString("name"));
                    //得到市下的区/县数组
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