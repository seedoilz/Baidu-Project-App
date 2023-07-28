package com.seedoilz.mybrowser.adapter;

import android.view.View;

import com.seedoilz.mybrowser.utils.AdministrativeType;


public interface AdministrativeClickCallback {
    
    void onAdministrativeItemClick(View view, int position, AdministrativeType type);
}

