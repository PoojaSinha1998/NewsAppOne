package com.intellinettechnologies.newsappone.MyController;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.intellinettechnologies.newsappone.MyModel.Article;
import com.intellinettechnologies.newsappone.R;
//
//import com.intellinettechnologies.newsappone.R;

import java.util.List;

//RecyclerView Adapter to Rander the data on UI
public class recyclerViewAdapter  extends RecyclerView.Adapter<recyclerViewAdapter.MyViewHolder> {

    private final List<Article> data;
    private final Context context;

    private ItemClickListener mClickListener;

    //Constructor to initilize the Adapter using Data and context
    public recyclerViewAdapter(List<Article> data, Context context) {
        this.data = data;
        this.context = context;
    }

    // OnCreateViewHolder to initilize the view  using LayoutInflator
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_view_items, parent, false);

        return new MyViewHolder(itemView);
    }

    //BindViewHolder binds the data with UI
    @SuppressLint("CheckResult")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.ic_launcher_background);
        //requestOptions.error(R.drawable.ic_launcher_background);
        holder.title.setText(data.get(position).getTitle());
        String url = data.get(position).getUrlToImage();

        Glide.with(context)
                .load(url)
                .apply(requestOptions)

                .into(holder.logo);

        //Adding clickListener on Perticular item in RecyclerView
        holder.mLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mClickListener != null)
                    mClickListener.onItemClick(data,position);

                Log.d("VT", "inside viewHolder id " + data.get(position).getUrl());
                Log.d("VT", "inside viewHolder  title " + data.get(position).getAuthor());
            }
        });
    }

    //Returing total number(Size) of Data
    @Override
    public int getItemCount() {
        Log.d("VT","Size of data :" +data.size());
        return data.size();
    }


    //To define View
    public class MyViewHolder extends RecyclerView.ViewHolder {
        final TextView title;
        final ImageView logo;
        final LinearLayout mLinearLayout;



        MyViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.textView);
            logo = view.findViewById(R.id.imageView);

            mLinearLayout = view.findViewById(R.id.myImage);

        }
    }
    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(List<Article> data, int position);
    }

    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

}
