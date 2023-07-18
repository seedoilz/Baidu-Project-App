package com.seedoilz.mybrowser;

import android.content.Context;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.qweather.sdk.view.HeConfig;
import com.qweather.sdk.view.QWeather;

import org.jetbrains.annotations.Nullable;

public class WeatherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather);
    }


    //数据初始化
    public void initData() {
        HeConfig.init("HE2307181419281996","c926b6e4c1b341b0b1dede1dbc8a6c48");
        HeConfig.switchToDevService();
        QWeather.getWeatherNow(Context,);
        QWeather.OnResultWeatherNowListener onResultWeatherNowListener = new QWeather.OnResultWeatherNowListener() {
            @Override
            public void onError(Throwable throwable) {
                DebugPrint.print("getWeatherNow onError: " + throwable.getLocalizedMessage());
                result.success(null);
            }

            @Override
            public void onSuccess(WeatherNowBean weatherNowBean) {
                Gson gson = new Gson();
                String jsonStr = gson.toJson(weatherNowBean);
                DebugPrint.print("getWeatherNow onSuccess: " + jsonStr);
                result.success(gson.fromJson(jsonStr, Map.class));
            }
        };
        initList();//天气预报列表初始化

        if (isOpenLocationServiceEnable()) {
            //tvCity.setEnabled(false);//不可点击
            startLocation();//开始定位
        } else {
            //tvCity.setEnabled(true);//可以点击
            Toast.makeText(WeatherActivity.this, "没有打开定位服务", Toast.LENGTH_SHORT).show();
        }
        //由于这个刷新框架默认是有下拉和上拉，但是上拉没有用到，为了不造成误会，我这里禁用上拉
        //初始化弹窗
        mPopupWindow = new PopupWindow(this);
    }

    private void initList() {
        /**   V7 版本   **/
        //天气预报  7天
        mAdapterDailyV7 = new DailyAdapter(R.layout.item_weather_forecast_list, dailyListV7);
        binding.rv.setLayoutManager(new LinearLayoutManager(this));
        binding.rv.setAdapter(mAdapterDailyV7);

        //逐小时天气预报  24小时
        mAdapterHourlyV7 = new HourlyAdapter(R.layout.item_weather_hourly_list, hourlyListV7);
        LinearLayoutManager managerHourly = new LinearLayoutManager(context);
        managerHourly.setOrientation(RecyclerView.HORIZONTAL);//设置列表为横向
        binding.rvHourly.setLayoutManager(managerHourly);
        binding.rvHourly.setAdapter(mAdapterHourlyV7);

        //分钟级降水
        mAdapterMinutePrec = new MinutePrecAdapter(R.layout.item_prec_detail_list, minutelyList);
        GridLayoutManager managerMinutePrec = new GridLayoutManager(context, 2);
        managerMinutePrec.setOrientation(RecyclerView.HORIZONTAL);
        binding.rvPrecDetail.setLayoutManager(managerMinutePrec);
        binding.rvPrecDetail.setAdapter(mAdapterMinutePrec);
    }

    private boolean isOpenLocationServiceEnable() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        boolean gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        boolean network = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        return gps || network;
    }

    private void startLocation() {
        //声明LocationClient类
        mLocationClient = new LocationClient(this);
        //注册监听函数
        mLocationClient.registerLocationListener(myListener);
        LocationClientOption option = new LocationClientOption();

        //如果开发者需要获得当前点的地址信息，此处必须为true
        option.setIsNeedAddress(true);
        //可选，设置是否需要最新版本的地址信息。默认不需要，即参数为false
        option.setNeedNewVersionRgc(true);
        //mLocationClient为第二步初始化过的LocationClient对象
        //需将配置好的LocationClientOption对象，通过setLocOption方法传递给LocationClient对象使用
        mLocationClient.setLocOption(option);
        //启动定位
        mLocationClient.start();

    }
}