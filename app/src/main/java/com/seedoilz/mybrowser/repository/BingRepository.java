package com.seedoilz.mybrowser.repository;

import android.annotation.SuppressLint;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.seedoilz.mybrowser.api.ApiService;
import com.seedoilz.mybrowser.db.bean.BingResponse;
import com.seedoilz.library.network.ApiType;
import com.seedoilz.library.network.NetworkApi;
import com.seedoilz.library.network.observer.BaseObserver;

/**
 * 必应壁纸存储库，用于获取城市数据
 */
@SuppressLint("CheckResult")
public class BingRepository {

    private static final String TAG = WeatherRepository.class.getSimpleName();

    private static final class BingRepositoryHolder {
        private static final BingRepository mInstance = new BingRepository();
    }

    public static BingRepository getInstance() {
        return BingRepositoryHolder.mInstance;
    }

    /**
     * 必应壁纸
     *
     * @param responseLiveData 成功数据
     * @param failed           错误信息
     */
    public void bing(MutableLiveData<BingResponse> responseLiveData, MutableLiveData<String> failed) {
        String type = "必应壁纸-->";
        NetworkApi.createService(ApiService.class, ApiType.BING).bing()
                .compose(NetworkApi.applySchedulers(new BaseObserver<>() {
                    @Override
                    public void onSuccess(BingResponse bingResponse) {
                        if (bingResponse == null) {
                            failed.postValue("必应壁纸数据为null。");
                            return;
                        }
                        responseLiveData.postValue(bingResponse);
                    }

                    @Override
                    public void onFailure(Throwable e) {
                        Log.e(TAG, "onFailure: " + e.getMessage());
                        failed.postValue(type + e.getMessage());
                    }
                }));
    }
}
