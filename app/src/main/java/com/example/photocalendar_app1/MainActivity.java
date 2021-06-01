package com.example.photocalendar_app1;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class MainActivity extends AppCompatActivity {

    private ImageView img_camera, img_gallery;
    private final static int CAMERA_REQUEST_CODE = 1;
    private final static int GALLERLY_REQUEST_CODE = 11;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        img_camera = (ImageView) findViewById(R.id.img_camara);
        img_gallery = (ImageView) findViewById(R.id.img_gallerly);


        img_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, CAMERA_REQUEST_CODE);
            }
        });


        img_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_gallerly = new Intent(Intent.ACTION_PICK,  android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent_gallerly, GALLERLY_REQUEST_CODE);

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);



        if ( requestCode == GALLERLY_REQUEST_CODE) {

            if (data != null) {
                Uri contentURI = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                    Log.d("AAA","hihi"+bitmap);
                    String filename = ""+bitmap;
                    FileOutputStream stream = this.openFileOutput(filename, Context.MODE_PRIVATE);
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    stream.close();
                    bitmap.recycle();
                    Intent intent_frame = new Intent(MainActivity.this, frame_calendar_activiti.class);
                    intent_frame.putExtra("img_g",""+filename);
                    startActivity(intent_frame);
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Failed!", Toast.LENGTH_SHORT).show();
                }
            }
        }
        else if(requestCode== CAMERA_REQUEST_CODE)
        {

            try {
                Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
                String filename = ""+thumbnail;
                FileOutputStream stream = this.openFileOutput(filename, Context.MODE_PRIVATE);;
                thumbnail.compress(Bitmap.CompressFormat.PNG, 100, stream);
                stream.close();
                thumbnail.recycle();
                Intent intent_frame = new Intent(MainActivity.this, frame_calendar_activiti.class);
                intent_frame.putExtra("img_c", ""+filename);
                Log.d("AAA","hihi"+thumbnail);
                startActivity(intent_frame);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}