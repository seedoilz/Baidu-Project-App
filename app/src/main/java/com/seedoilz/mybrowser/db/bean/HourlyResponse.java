package com.seedoilz.mybrowser.db.bean;

import java.util.List;


public class HourlyResponse {

    private String code;
    private List<HourlyBean> hourly;

    public String getCode() {
        return code;
    }

    public List<HourlyBean> getHourly() {
        return hourly;
    }


    public static class HourlyBean {

        private String fxTime;
        private String temp;
        private String icon;

        public String getFxTime() {
            return fxTime;
        }

        public String getTemp() {
            return temp;
        }

        public String getIcon() {
            return icon;
        }

    }
}
