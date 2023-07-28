package com.seedoilz.mybrowser.db.bean;

import java.util.List;


public class SearchCityResponse {

    private String code;
    private List<LocationBean> location;

    public String getCode() {
        return code;
    }

    public List<LocationBean> getLocation() {
        return location;
    }


    public static class LocationBean {

        private String name;
        private String id;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
}
