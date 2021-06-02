package com.example.photocalendar_app1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
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
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.photocalendar_app1.DTA.RecyclerView_adapter;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.snackbar.Snackbar;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.DialogOnAnyDeniedMultiplePermissionsListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.karumi.dexter.listener.multi.SnackbarOnAnyDeniedMultiplePermissionsListener;
import com.karumi.dexter.listener.single.DialogOnDeniedPermissionListener;
import com.karumi.dexter.listener.single.PermissionListener;
import com.karumi.dexter.listener.single.SnackbarOnDeniedPermissionListener;

import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class export_calendar extends AppCompatActivity {


    private static final String TAG = "AAA";
    private ImageView img, img_user;
    private ImageView img_cam , img_gall, img_save, img_share, img_filter;
    private final static int CAMERA_REQUEST_CODE = 1;
    private final static int GALLERLY_REQUEST_CODE = 11;
    private ArrayList<String> arrayList_name=new ArrayList<>();
    private  ArrayList<String>arrayList_image=new ArrayList<>();
    LinearLayout l1,l2;
    int i=1;
    int k=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_export_calendar);
        img= findViewById(R.id.img_mark);
        img_user= findViewById(R.id.img_user);
        img_save= findViewById(R.id.img_save);
        img_share= findViewById(R.id.img_share);
        img_filter=findViewById(R.id.img_filter);
        RelativeLayout relativeLayout= findViewById(R.id.real);


        Dexter.withContext(this)
                .withPermissions(
                        Manifest.permission.CAMERA,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                ).withListener(new MultiplePermissionsListener() {
            @Override public void onPermissionsChecked(MultiplePermissionsReport report) {/* ... */}
            @Override public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {/* ... */}
        }).check();

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


        // save imgae
        img_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               Bitmap bm= loadBitmapFromView(relativeLayout);
               Log.d("AAA",""+bm);
                saveImage(bm);
                Toast.makeText( export_calendar.this, "Saved", Toast.LENGTH_SHORT).show();
            }
        });


        // share image with other app
        img_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap bitmap= loadBitmapFromView(relativeLayout);
                Uri uri=saveImageshare(bitmap);
                shareImageUri(uri);

            }
        });


        //add filter
        addfilte();
        l1= findViewById(R.id.Linear_storge);
        l2= findViewById(R.id.Liner_filter);
        l2.setVisibility(View.GONE);
        img_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                l1.setVisibility(View.GONE);
                l2.setVisibility(View.VISIBLE);
            }
        });




    }

    private void addfilte() {

        arrayList_image.add("https://cloud.githubusercontent.com/assets/4659608/13037677/6060dfe4-d397-11e5-8d50-0cf960914a8b.png");
        arrayList_name.add("Gray");

        arrayList_image.add("https://cloud.githubusercontent.com/assets/4659608/13037676/605da3c4-d397-11e5-93cb-22a4895e59e2.png");
        arrayList_name.add("Relief");

        arrayList_image.add("https://cloud.githubusercontent.com/assets/4659608/13037708/0cb05126-d398-11e5-9b70-09cc415ac791.png");
        arrayList_name.add("Average blur");

        arrayList_image.add("https://cloud.githubusercontent.com/assets/4659608/13037707/0cafb996-d398-11e5-9610-48467208441b.png");
        arrayList_name.add("OIL");

        arrayList_image.add("https://cloud.githubusercontent.com/assets/4659608/13037677/6060dfe4-d397-11e5-8d50-0cf960914a8b.png");
        arrayList_name.add("NEON");

        arrayList_image.add("https://cloud.githubusercontent.com/assets/4659608/13060886/9f6c03a4-d445-11e5-9771-36bf3e20591f.png");
        arrayList_name.add("Pixelate");

        initRecycirleView();



    }

    private void initRecycirleView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(layoutManager);
        RecyclerView_adapter adapter = new RecyclerView_adapter(this, arrayList_name, arrayList_image);
        recyclerView.setAdapter(adapter);

    }



    //get image from view
    public static Bitmap loadBitmapFromView(View v) {
        v.clearFocus();
        v.setPressed(false);

        boolean willNotCache = v.willNotCacheDrawing();
        v.setWillNotCacheDrawing(false);

        // Reset the drawing cache background color to fully transparent
        // for the duration of this operation
        int color = v.getDrawingCacheBackgroundColor();
        v.setDrawingCacheBackgroundColor(0);

        if (color != 0) {
            v.destroyDrawingCache();
        }
        v.buildDrawingCache();
        Bitmap cacheBitmap = v.getDrawingCache();
        if (cacheBitmap == null) {
            Log.e("AAA", "failed getViewBitmap(" + v + ")", new RuntimeException());
            return null;
        }

        Bitmap bitmap = Bitmap.createBitmap(cacheBitmap);

        // Restore the view
        v.destroyDrawingCache();
        v.setWillNotCacheDrawing(willNotCache);
        v.setDrawingCacheBackgroundColor(color);
        return bitmap;
    }

    // Save bitmap as storge
//    public static File savebitmap(Bitmap bmp) throws IOException {
//        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//        bmp.compress(Bitmap.CompressFormat.JPEG, 60, bytes);
//        File f = new File(Environment.getExternalStorageDirectory()
//                + File.separator + bmp);
//        f.createNewFile();
//        FileOutputStream fo = new FileOutputStream(f);
//        fo.write(bytes.toByteArray());
//        fo.close();
//        return f;
//

    //save as storge
    private void saveImage(Bitmap bitmap1) {
        OutputStream outputStream=null;
        String fileName=String.format("%d.png",System.currentTimeMillis());
        File outFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath()
                +"/Pictures",fileName);
        try {
           outputStream=new FileOutputStream(outFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        bitmap1.compress(Bitmap.CompressFormat.PNG,100,outputStream);
        try {
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // pick imagre form gallerly
    private void pickimagegallerly() {
        img_gall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent_gallerly = new Intent(Intent.ACTION_PICK,  android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
               startActivityForResult(intent_gallerly, GALLERLY_REQUEST_CODE);
            }
        });
    }



    //pick image form cam
    private void pickimagecam() {
        img_cam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull @NotNull String[] permissions, @NonNull @NotNull int[] grantResults) {
        if(requestCode==CAMERA_REQUEST_CODE && grantResults.length>0 && grantResults[0]== PackageManager.PERMISSION_GRANTED)
        {
            Intent intent_gallerly = new Intent(Intent.ACTION_PICK,  android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent_gallerly, GALLERLY_REQUEST_CODE);
        }
        else
        {
            Toast.makeText(export_calendar.this,"Permission Denied",Toast.LENGTH_SHORT).show();
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
    // create uri

    private Uri saveImageshare(Bitmap image) {
        //TODO - Should be processed in another thread
        File imagesFolder = new File(getCacheDir(), "images");
        Uri uri = null;
        try {
            imagesFolder.mkdirs();
            File file = new File(imagesFolder, "shared_image.png");
            FileOutputStream stream = new FileOutputStream(file);
            image.compress(Bitmap.CompressFormat.PNG, 90, stream);
            stream.flush();
            stream.close();
            uri = FileProvider.getUriForFile(this, "com.example.photocalendar_app1.fileprovider", file);
        } catch (IOException e) {
            Log.d(TAG, "IOException while trying to write file for sharing: " + e.getMessage());
        }
        return uri;
    }

    //Inten share
    private void shareImageUri(Uri uri){
        Intent intent = new Intent(android.content.Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setType("image/png");
        startActivity(intent);
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
//    private void checkStoragePermission() {
//        if (Build.VERSION.SDK_INT<Build.VERSION_CODES.M){
//            return;
//        }
//
//        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED
//                &&checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.CAMERA)== PackageManager.PERMISSION_GRANTED){
//            Toast.makeText(this,"Permission granted",Toast.LENGTH_LONG).show();
//        }else {
//            requestPermission();
//        }
//    }
//
//    private void requestPermission() {
//        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
//                Manifest.permission.READ_EXTERNAL_STORAGE) || ActivityCompat.shouldShowRequestPermissionRationale(this,
//                Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
//            new AlertDialog.Builder(this)
//                    .setTitle("Permission needed")
//                    .setMessage("This permission is needed because of this and that")
//                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            ActivityCompat.requestPermissions(export_calendar.this,
//                                    new String[] {Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.CAMERA,
//                                            Manifest.permission.WRITE_EXTERNAL_STORAGE},GALLERLY_REQUEST_CODE );
//
//                        }
//                    })
//                    .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            dialog.dismiss();
//                        }
//                    })
//                    .create().show();
//        } else {
//            ActivityCompat.requestPermissions(this,
//                    new String[] {Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.CAMERA,
//                            Manifest.permission.WRITE_EXTERNAL_STORAGE
//                            },GALLERLY_REQUEST_CODE);
//        }
//    }
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
