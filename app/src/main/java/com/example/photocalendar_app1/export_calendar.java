package com.example.photocalendar_app1;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class export_calendar extends AppCompatActivity {



    private ImageView img, img_user;
    private ImageView img_cam , img_gall;
    private final static int CAMERA_REQUEST_CODE = 1;
    private final static int GALLERLY_REQUEST_CODE = 11;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_export_calendar);
        img= findViewById(R.id.img_mark);
        img_user= findViewById(R.id.img_user);

        //lay kich thuuoc goc cua man hinh
        int w = getResources().getDisplayMetrics().widthPixels;
        LinearLayout.LayoutParams params= new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (w*4)/3);
        RelativeLayout raRelativeLayout= findViewById(R.id.real);
        raRelativeLayout.setLayoutParams(params);
        //

        Bitmap bmp = null;

        //get imgae_frame
        String filename = getIntent().getStringExtra("img_frame");
        Bundle bundle=getIntent().getExtras();
       if(bundle!=null)
       {
           try {
               int a= bundle.getInt("img_frame");
               Log.d("AAA",""+a);
               img.setImageResource(a);
           }catch (Exception e)
           {
               Toast.makeText(this,"loi", Toast.LENGTH_SHORT).show();
           }
       }

       //get image form cam
        img_cam= findViewById(R.id.img_camara);
        pickimagecam();

        //get from gallary
        img_gall= findViewById(R.id.img_gallerly);
        pickimagegallerly();





    }

    private void pickimagegallerly() {
        img_gall.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                Intent intent_gallerly = new Intent(Intent.ACTION_PICK,  android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent_gallerly, GALLERLY_REQUEST_CODE);
            }
        });
    }

    private void pickimagecam() {
        img_cam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, CAMERA_REQUEST_CODE);
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
                    img_user.setImageBitmap(bitmap);
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
                img_user.setImageBitmap(thumbnail);
                
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
//    public void draw(Canvas canvas){
//        Bitmap original = getBitmapFromAsset(this,"anh1.jpg");
//        Bitmap mask =getBitmapFromAsset(this,"ntitle.png");
//        Log.d("AAA",""+ original.getWidth()+" "+original.getHeight());
//        Log.d("AAA",""+ mask.getWidth()+ " " +mask.getHeight());
//        Bitmap result = Bitmap.createBitmap(mask.getWidth(), mask.getHeight(), Bitmap.Config.ARGB_8888);
//        Canvas tempCanvas = new Canvas(result);
//        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
//        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
//        tempCanvas.drawBitmap(original, 0, 0, null);
//        tempCanvas.drawBitmap(mask, 0, 0, paint);
//        paint.setXfermode(null);
//
//        canvas.drawBitmap(result, 0, 0, new Paint());
//    }
//    public  Bitmap getBitmapFromAsset(Context context, String filePath) {
//        AssetManager assetManager = context.getAssets();
//
//        InputStream istr;
//        Bitmap bitmap = null;
//        try {
//            istr = assetManager.open(filePath);
//            bitmap = BitmapFactory.decodeStream(istr);
//        } catch (IOException e) {
//            // handle exception
//        }
//
//        return bitmap;
//    }

    }
