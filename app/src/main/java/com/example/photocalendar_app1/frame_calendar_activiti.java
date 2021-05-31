package com.example.photocalendar_app1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.GridView;

import com.example.photocalendar_app1.DTA.fram_adapter;
import com.example.photocalendar_app1.DTO.Abum_frame;

import java.util.ArrayList;

public class frame_calendar_activiti extends AppCompatActivity {

    int[] arr_frame ={R.drawable.anh1, R.drawable.anh3};
    GridView giGridView;
    ArrayList<Abum_frame> arrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frame_calendar_activiti);
        giGridView=(findViewById(R.id.grv_frame));

        arrayList= new ArrayList<Abum_frame>();
        for(int i = 0; i <arr_frame.length; i++)
        {
            Abum_frame abum_frame= new Abum_frame();
            abum_frame.setFrame(arr_frame[i]);
            arrayList.add(abum_frame);

        }
        fram_adapter fram_adapter= new fram_adapter(this,R.layout.frame_custom,arrayList);
        fram_adapter.notifyDataSetChanged();
        giGridView.setAdapter(fram_adapter);
    }
}