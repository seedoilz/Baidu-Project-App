package com.seedoilz.mybrowser.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.seedoilz.mybrowser.MyBrowserApp;
import com.seedoilz.mybrowser.R;
import com.seedoilz.mybrowser.model.User;

public class LoginActivity extends AppCompatActivity {
    private EditText usernameEditText;
    private EditText passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        usernameEditText = findViewById(R.id.login_username);
        passwordEditText = findViewById(R.id.login_password);
        Button loginButton = findViewById(R.id.login_button);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        User user = MyBrowserApp.getDb().userDao().findUser(username, password);
                        if (user != null) {
                            // Login success
                            SharedPreferences sharedPref = getSharedPreferences("user_pref", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPref.edit();
                            editor.putString("username", user.username);
                            editor.putString("password", user.password);
                            editor.apply();
                        } else {
                            // Login failed
                            passwordEditText.setText("");
                            Toast.makeText(LoginActivity.this, "账号或密码错误", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).start();

                onBackPressed();
            }
        });

        View backView = findViewById(R.id.back_image);
        backView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}
