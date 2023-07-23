package com.seedoilz.mybrowser.bottombar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.seedoilz.mybrowser.R;
import com.seedoilz.mybrowser.activity.LoginActivity;
import com.seedoilz.mybrowser.activity.RegisterActivity;

import java.util.Objects;

public class user extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_user, container, false);

        View loginView = rootView.findViewById(R.id.loginButton);
        loginView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent loginIntent = new Intent(getActivity(), LoginActivity.class);
                startActivity(loginIntent);
            }
        });

        View registerView = rootView.findViewById(R.id.registerButton);
        registerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registerIntent = new Intent(getActivity(), RegisterActivity.class);
                startActivity(registerIntent);
            }
        });

        View logoutView = rootView.findViewById(R.id.logoutButton);
        logoutView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPref = requireActivity().getSharedPreferences("user_pref", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.clear();
                editor.apply();
                TextView usernameView = requireView().findViewById(R.id.userName);
                usernameView.setText("尚未登录！");
                Toast.makeText(getActivity(), "登出成功", Toast.LENGTH_SHORT).show();
            }
        });

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        SharedPreferences sharedPref = requireActivity().getSharedPreferences("user_pref", Context.MODE_PRIVATE);
        String username = sharedPref.getString("username", null);
        if (username != null){
            TextView usernameView = requireView().findViewById(R.id.userName);
            usernameView.setText("欢迎您！\n" + username);
        }
        else {
            TextView usernameView = requireView().findViewById(R.id.userName);
            usernameView.setText("尚未登录！");
        }
    }
}
