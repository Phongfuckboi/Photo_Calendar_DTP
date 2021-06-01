package com.example.photocalendar_app1;

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
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class export_calendar extends AppCompatActivity {



    private ImageView img, img_user;
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


       //get  image_user
        String filename1 = getIntent().getStringExtra("img_user_c");
        String filename2 = getIntent().getStringExtra("img_user_g");
        Log.d("AAA",""+filename2);
        if(filename1!=null) {
            try {
                FileInputStream is = this.openFileInput(filename);
                bmp = BitmapFactory.decodeStream(is);
                img_user.setImageBitmap(bmp);
                is.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else if(filename2!=null)
        {
            try {
                FileInputStream is = this.openFileInput(filename1);
                bmp = BitmapFactory.decodeStream(is);
                is.close();

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
