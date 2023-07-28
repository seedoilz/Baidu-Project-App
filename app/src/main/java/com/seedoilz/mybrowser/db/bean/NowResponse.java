package com.seedoilz.mybrowser.db.bean;

import java.util.List;


public class NowResponse {

    private String code;
    private String updateTime;
    private NowBean now;

    public String getCode() {
        return code;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public NowBean getNow() {
        return now;
    }


    public static class NowBean {

        private String temp;

        private String text;

        private String windDir;
        private String windScale;


        public String getTemp() {
            return temp;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getWindDir() {
            return windDir;
        }

        public String getWindScale() {
            return windScale;
        }
    }
}
