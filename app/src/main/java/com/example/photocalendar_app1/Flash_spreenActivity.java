package com.example.photocalendar_app1.DTO;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import com.example.photocalendar_app1.R;

public class Flash_spreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flash_spreen);
        @SuppressLint("CommitPrefEdits") Thread thread = new Thread(() ->
        {
            try {
                Thread.sleep(2000);
            }
            catch (Exception e)
            {}
            finally {
                Intent iDangNhap = new Intent(this, Flash_spreenActivity.class);
                startActivity(iDangNhap);
                finish();
            }

        });
        thread.start();
    }
    }
