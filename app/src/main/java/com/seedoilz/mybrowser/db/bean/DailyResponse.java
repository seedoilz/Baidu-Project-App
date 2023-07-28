package com.seedoilz.mybrowser.db.bean;

import java.util.List;


public class DailyResponse {

    private String code;
    private List<DailyBean> daily;

    public String getCode() {
        return code;
    }

    public List<DailyBean> getDaily() {
        return daily;
    }


    public static class DailyBean {

        private String fxDate;
        private String tempMax;
        private String tempMin;
        private String iconDay;

        public String getFxDate() {
            return fxDate;
        }

        public String getTempMax() {
            return tempMax;
        }

        public String getTempMin() {
            return tempMin;
        }

        public String getIconDay() {
            return iconDay;
        }

    }
}
