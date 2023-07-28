package com.seedoilz.mybrowser.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.seedoilz.mybrowser.R;
import com.seedoilz.mybrowser.databinding.WeatherBinding;
import com.seedoilz.mybrowser.db.bean.HourlyResponse;
import com.seedoilz.mybrowser.adapter.DailyAdapter;
import com.seedoilz.mybrowser.adapter.HourlyAdapter;
import com.seedoilz.mybrowser.db.bean.DailyResponse;
import com.seedoilz.mybrowser.db.bean.NowResponse;
import com.seedoilz.mybrowser.db.bean.SearchCityResponse;
import com.seedoilz.mybrowser.utils.CityDialog;
import com.seedoilz.mybrowser.utils.EasyDate;
import com.seedoilz.mybrowser.viewmodel.WeatherViewModel;
import com.seedoilz.library.base.NetworkActivity;

import java.util.ArrayList;
import java.util.List;

public class WeatherActivity extends NetworkActivity<WeatherBinding> implements CityDialog.SelectedCityCallback {


    private WeatherViewModel viewModel;

    private final List<DailyResponse.DailyBean> dailyBeanList = new ArrayList<>();
    private final DailyAdapter dailyAdapter = new DailyAdapter(dailyBeanList);
    private final List<HourlyResponse.HourlyBean> hourlyBeanList = new ArrayList<>();
    private final HourlyAdapter hourlyAdapter = new HourlyAdapter(hourlyBeanList);
    private CityDialog cityDialog;

    private Menu mMenu;
    private int cityFlag = 0;
    private String mCityName;
    private boolean isRefresh;


    @Override
    protected void onCreate() {
        setFullScreenImmersion();
        initView();
        viewModel = new ViewModelProvider(this).get(WeatherViewModel.class);
        viewModel.getAllCity();
    }


    private void initView() {
        setToolbarMoreIconCustom(binding.materialToolbar);
        binding.rvDaily.setLayoutManager(new LinearLayoutManager(this));
        binding.rvDaily.setAdapter(dailyAdapter);
        LinearLayoutManager hourlyLayoutManager = new LinearLayoutManager(this);
        hourlyLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        binding.rvHourly.setLayoutManager(hourlyLayoutManager);
        binding.rvHourly.setAdapter(hourlyAdapter);
        binding.layRefresh.setOnRefreshListener(() -> {
            if (mCityName == null) {
                binding.layRefresh.setRefreshing(false);
                return;
            }
            isRefresh = true;
            viewModel.searchCity(mCityName);
        });
        binding.layScroll.setOnScrollChangeListener((View.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            if (scrollY > oldScrollY) {
                if (scrollY > binding.layScrollHeight.getMeasuredHeight()) {
                    binding.tvTitle.setText((mCityName == null ? "城市天气" : mCityName));
                }
            } else if (scrollY < oldScrollY) {
                if (scrollY < binding.layScrollHeight.getMeasuredHeight()) {
                    binding.tvTitle.setText("城市天气");
                }
            }
        });
        View backView = findViewById(R.id.back_image);
        backView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        mMenu = menu;
        mMenu.findItem(R.id.item_relocation).setVisible(cityFlag == 1);
        return true;
    }


    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_switching_cities:
                if (cityDialog != null) cityDialog.show();
                break;
        }
        return true;
    }


    public void setToolbarMoreIconCustom(Toolbar toolbar) {
        if (toolbar == null) return;
        toolbar.setTitle("");
        Drawable moreIcon = ContextCompat.getDrawable(toolbar.getContext(), R.drawable.ic_round_add_32);
        if (moreIcon != null) toolbar.setOverflowIcon(moreIcon);
        setSupportActionBar(toolbar);
    }


    @Override
    protected void onObserveData() {
        if (viewModel != null) {
            viewModel.searchCityResponseMutableLiveData.observe(this, searchCityResponse -> {
                List<SearchCityResponse.LocationBean> location = searchCityResponse.getLocation();
                if (location != null && location.size() > 0) {
                    String id = location.get(0).getId();
                    mMenu.findItem(R.id.item_relocation).setVisible(cityFlag == 1);
                    if (isRefresh) {
                        showMsg("刷新完成");
                        binding.layRefresh.setRefreshing(false);
                        isRefresh = false;
                    }
                    if (id != null) {
                        viewModel.nowWeather(id);
                        viewModel.dailyWeather(id);
                        viewModel.hourlyWeather(id);
                    }
                }
            });
            viewModel.nowResponseMutableLiveData.observe(this, nowResponse -> {
                NowResponse.NowBean now = nowResponse.getNow();
                if (now != null) {
                    binding.tvInfo.setText(now.getText());
                    binding.tvTemp.setText(now.getTemp());
                    String time = EasyDate.updateTime(nowResponse.getUpdateTime());
                    binding.tvUpdateTime.setText(String.format("最近更新时间：%s%s", EasyDate.showTimeInfo(time), time));
                    binding.tvWindDirection.setText(String.format("风向     %s", now.getWindDir()));
                    binding.tvWindPower.setText(String.format("风力     %s级", now.getWindScale()));
                }
            });
            viewModel.dailyResponseMutableLiveData.observe(this, dailyResponse -> {
                List<DailyResponse.DailyBean> daily = dailyResponse.getDaily();
                if (daily != null) {
                    if (dailyBeanList.size() > 0) {
                        dailyBeanList.clear();
                    }
                    dailyBeanList.addAll(daily);
                    dailyAdapter.notifyDataSetChanged();
                    binding.tvHeight.setText(String.format("%s℃", daily.get(0).getTempMax()));
                    binding.tvLow.setText(String.format(" / %s℃", daily.get(0).getTempMin()));
                }
            });

            viewModel.cityMutableLiveData.observe(this, provinces -> {
                cityDialog = CityDialog.getInstance(WeatherActivity.this, provinces);
                cityDialog.setSelectedCityCallback(this);
            });
            viewModel.hourlyResponseMutableLiveData.observe(this, hourlyResponse -> {
                List<HourlyResponse.HourlyBean> hourly = hourlyResponse.getHourly();
                if (hourly != null) {
                    if (hourlyBeanList.size() > 0) {
                        hourlyBeanList.clear();
                    }
                    hourlyBeanList.addAll(hourly);
                    hourlyAdapter.notifyDataSetChanged();
                }
            });
            viewModel.failed.observe(this, this::showLongMsg);
        }
    }


    @Override
    public void selectedCity(String cityName) {
        cityFlag = 1;
        mCityName = cityName;
        viewModel.searchCity(cityName);
        binding.tvCity.setText(cityName);
    }
}