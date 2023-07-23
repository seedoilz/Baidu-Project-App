package com.seedoilz.mybrowser.activity;

import static android.content.ContentValues.TAG;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.seedoilz.mybrowser.MyBrowserApp;
import com.seedoilz.mybrowser.R;
import com.seedoilz.mybrowser.model.Article;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PublishNewsActivity extends AppCompatActivity {

    private EditText newsTitle;
    private EditText summaryText;
    private EditText bodyText;
    private ImageView thumbnailImage = null;
    private ImageView headlineImage = null;

    private String tempPath;

    private String thumbnailPath;
    private String headlinePath;

    private ImageView tempImage = null;
    private Uri selectedImageUri;

    private static final int SELECT_IMAGE_REQUEST_CODE = 100;
    private static final int PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 101;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.publish_news);
        initView();
    }

    private void initView() {

        newsTitle = findViewById(R.id.news_title);
        summaryText = findViewById(R.id.summary_text);
        bodyText = findViewById(R.id.body_text);
        thumbnailImage = findViewById(R.id.thumbnail_image);
        headlineImage = findViewById(R.id.headline_image);

        View backButton = findViewById(R.id.back_image);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        thumbnailImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tempImage = thumbnailImage;
                tempPath = "thumbnail";
                checkPermissions();
            }
        });

        headlineImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tempImage = headlineImage;
                tempPath = "headline";
                checkPermissions();
            }
        });

        View submitButton = findViewById(R.id.publish_button);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Article article = new Article();
                String title = newsTitle.getText().toString();
                String summary = summaryText.getText().toString();
                String body = bodyText.getText().toString();

                article.title = title;
                article.summary = summary;
                article.content = body;
                article.thumbnailPath = thumbnailPath;
                article.imagePath = headlinePath;


//                MyBrowserApp.getDb().articleDao().insertAll(article);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        MyBrowserApp.getDb().articleDao().insertAll(article);
                    }
                }).start();

                Toast.makeText(PublishNewsActivity.this, "提交成功", Toast.LENGTH_SHORT).show();

                onBackPressed();
            }
        });
    }


    //检查权限并打开相册
    private void checkPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            openImagePicker();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
        }
    }


    private void openImagePicker() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, SELECT_IMAGE_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openImagePicker();
            } else {
                Log.e(TAG, "读取存储权限被拒绝");
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SELECT_IMAGE_REQUEST_CODE && resultCode == RESULT_OK) {
            if (data != null) {
                selectedImageUri = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImageUri);
                    tempImage.setImageBitmap(bitmap);
                    if (tempPath.equals("thumbnail")){
                        thumbnailPath = saveImageToInternalStorage(bitmap);
                    }
                    else{
                        headlinePath = saveImageToInternalStorage(bitmap);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private String saveImageToInternalStorage(Bitmap bitmap) {
        try {
            // Use the application's context to get the files directory.
            File directory = getApplicationContext().getFilesDir();
            // Create a new file in the specified directory.
            long currentTimeMillis = System.currentTimeMillis();

            // 将时间戳转换为字符串
            String timeStampString = convertTimestampToString(currentTimeMillis);
            File file = new File(directory, timeStampString + ".png");

            // Create an output stream from the file.
            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(file);
                // Use the compress method on the BitMap object to write image to the output stream.
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
                // Flush and close the output stream.
                fos.flush();
                fos.close();
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
            // Return the image file's absolute path.
            return file.getAbsolutePath();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    private String convertTimestampToString(long timestamp) {
        // 创建一个SimpleDateFormat对象，用于格式化时间戳
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss.SSS");

        // 使用SimpleDateFormat对象格式化时间戳，并返回字符串
        return dateFormat.format(new Date(timestamp));
    }

}
