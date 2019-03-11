package com.intellinettechnologies.newsappone.MyPresenter;

import android.annotation.SuppressLint;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import android.support.v7.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.intellinettechnologies.newsappone.MyModel.Article;
import com.intellinettechnologies.newsappone.MyModel.myapplication;
import com.intellinettechnologies.newsappone.R;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    private int i;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        //Getting Bundle data sended by Calling Activity
        Bundle args = getIntent().getExtras();
        if(args !=null) {
            i = args.getInt("ID");
        }

        //Calling function to initilize UI component and set data

        InitilizeVariableAndPopulate();
        //toolbar typecasting to set back arrow
        Toolbar toolbar = findViewById(R.id.toolbar);
        //adding clickListenr on toolbar's back arrow
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               onBackPressed();
            }
        });

    }

    //Initilize Component and setting data
    @SuppressLint("CheckResult")
    private void InitilizeVariableAndPopulate() {

        List<Article> articles = myapplication.getExamples();



        Log.d("VT","Detailed Activity :"+ articles.get(i).getTitle());

        TextView mType = findViewById(R.id.detail_title);
        mType.setText(articles.get(i).getTitle());

        mType = findViewById(R.id.dAuthor);
        mType.setText(articles.get(i).getAuthor());

        mType = findViewById(R.id.dDescription);
        mType.setText(articles.get(i).getDescription());

        mType = findViewById(R.id.dcontent);
        mType.setText(articles.get(i).getContent());

        mType = findViewById(R.id.dPublished);
        mType.setText(articles.get(i).getPublishedAt());

        ImageView mImageView = findViewById(R.id.detailImage);

        Glide.with(getApplicationContext())
                .load(articles.get(i).getUrlToImage())
                .into(mImageView);

    }
    //predefined back press
    @Override
    public void onBackPressed() {
        finish();
    }
}
