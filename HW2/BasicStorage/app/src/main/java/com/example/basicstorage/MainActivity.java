package com.example.basicstorage;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class MainActivity extends AppCompatActivity {

    private ImageView imageView,imageViewLoad;

    private Uri savedImageUri;
    private static final String IMAGE_FILE_NAME = "sample_image.jpg";
    private static final String PREF_NAME = "MyAppSettings";
    private static final String PREF_KEY_SETTING = "exampleSetting";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.imageView);
        imageViewLoad = findViewById(R.id.imageViewLoad);
        imageView.setImageResource(R.drawable.sample_image);

        Button btnSaveMedia = findViewById(R.id.btnSaveMedia);
        Button btnLoadMedia = findViewById(R.id.btnLoadMedia);
        Button btnSaveSetting = findViewById(R.id.btnSaveSetting);
        Button btnLoadSetting = findViewById(R.id.btnLoadSetting);

        btnSaveMedia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveMedia();
            }
        });

        btnLoadMedia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadMedia();
            }
        });

        btnSaveSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveSetting();
            }
        });

        btnLoadSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadSetting();
            }
        });
    }

    private void saveMedia() {
// Use MediaStore to save the image
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.sample_image);
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.DISPLAY_NAME, "sample_image.jpg");
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
        ContentResolver resolver = getContentResolver();
        savedImageUri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        try {
            if (savedImageUri != null) {
                OutputStream outputStream = resolver.openOutputStream(savedImageUri);
                if (outputStream != null) {
                    // Compress and write the bitmap to the OutputStream
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                    outputStream.close();
                    Toast.makeText(this, "Image saved", Toast.LENGTH_SHORT).show();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Failed to create image file", Toast.LENGTH_SHORT).show();
        }

    }

    private void loadMedia() {
        // Load media item (image)
        Bitmap bitmap = null;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), savedImageUri);
            Toast.makeText(this, "Media Load Success! " , Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        // Display the loaded image
        if (bitmap != null) {
            imageViewLoad.setImageBitmap(bitmap);
        }
    }

    private void saveSetting() {
        SharedPreferences preferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        String settingText = ((EditText) findViewById(R.id.editTextSetting)).getText().toString();
        editor.putString(PREF_KEY_SETTING, settingText);
        editor.apply();
        Toast.makeText(this, "Setting saved", Toast.LENGTH_SHORT).show();
    }

    private void loadSetting() {
        SharedPreferences preferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        String settingValue = preferences.getString(PREF_KEY_SETTING, "Default Value");
        TextView loadSetting =  (TextView)findViewById(R.id.loadTextSetting);
        loadSetting.setText(settingValue);
        Toast.makeText(this, "Loaded Setting: " + settingValue, Toast.LENGTH_LONG).show();
    }
}
