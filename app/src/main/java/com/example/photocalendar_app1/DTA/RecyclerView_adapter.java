package com.example.photocalendar_app1.DTA;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.photocalendar_app1.R;

//import net.alhazmy13.imagefilter.ImageFilter;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecyclerView_adapter extends RecyclerView.Adapter<RecyclerView_adapter.viewholder> {


    private ArrayList<String> arrayList_name;
    private  ArrayList<String>arrayList_image;
    private Context context;

    public RecyclerView_adapter(Context context,ArrayList<String> arrayList_name, ArrayList<String> arrayList_image) {
        this.arrayList_name = arrayList_name;
        this.arrayList_image = arrayList_image;
        this.context = context;
    }

    @NonNull
    @NotNull
    @Override
    public viewholder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_list_item,parent,false);
        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RecyclerView_adapter.viewholder holder, int position) {
        Glide.with(context)
                .asBitmap()
                .load(arrayList_image.get(position)).into(holder.circleImageView);

        holder.text_namefilter.setText(arrayList_name.get(position));
        holder.circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,"ok",Toast.LENGTH_SHORT).show();

            }
        });



    }

    @Override
    public int getItemCount() {
        return arrayList_image.size();
    }

    public  class  viewholder extends RecyclerView.ViewHolder
    {

        CircleImageView circleImageView;
        TextView text_namefilter;
        public viewholder(@NonNull @NotNull View itemView) {
            super(itemView);
            circleImageView= itemView.findViewById(R.id.image_view);
            text_namefilter= itemView.findViewById(R.id.name);
        }

    }
}
