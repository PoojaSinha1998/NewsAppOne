package com.intellinettechnologies.newsappone.MyPresenter;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.intellinettechnologies.newsappone.MyController.ApiInterface;
import com.intellinettechnologies.newsappone.MyController.recyclerViewAdapter;
import com.intellinettechnologies.newsappone.MyModel.Article;
import com.intellinettechnologies.newsappone.MyModel.Example;
import com.intellinettechnologies.newsappone.MyModel.myapplication;
import com.intellinettechnologies.newsappone.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements recyclerViewAdapter.ItemClickListener {

    // to refresh to recyclerView
    private SwipeRefreshLayout mSwipeRefreshLayout;
    // to exit on double back press
    private boolean doubleBackToExitPressedOnce = false;

    //onCreate method, it will load initially
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initilization of SwipeRefereshLayout and adding OnRefreshListener on that
        mSwipeRefreshLayout = findViewById(R.id.swipeToRefresh);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                mSwipeRefreshLayout.setRefreshing(false);
            }
        });

        // AsyncTask to get data from Server
        new AccessNewsData().execute();
    }

    //Interface from Adapter to handle item click listener, it is having Artical in List data and adapter position
    @Override
    public void onItemClick(List<Article> data, int position) {

        //Calling Detailed activity for news by Intent and  passing ID(Position)
        Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
        //Setting Artical data on Application class to access on Detailed activity
        myapplication.setExamples(data);

        // adding ID to Intent
        intent.putExtra("ID", position);

        //Starting another Activity
        startActivity(intent);

    }

    //AsyncTask to get Data from Server using Retrofit
    @SuppressLint("StaticFieldLeak")
    private class AccessNewsData extends AsyncTask<Void, Void, Void> {
        // Dialog box initilization to block the UI before loading data
        final ProgressDialog dialog = new ProgressDialog(MainActivity.this);
        //URL to fetch data from server
        static final String BASE_URL = "https://newsapi.org/v2/";

        //Initilization of ArrayList of Artical
        ArrayList<Article> myArticlesList;
        //List of Artical
        List<Article> data;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Dialog box formatting, calcelable outside is false
            dialog.setMessage("Please wait...");
            dialog.setIndeterminate(true);
            dialog.setCancelable(false);
            dialog.show();

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            //it will executer after DoinBackground, so here dailog box will get dismiss
            dialog.dismiss();

        }

        //This is the main part, where will do main processsing to download data from server
        @Override
        protected Void doInBackground(Void... voids) {
            // Retrofit.Builder uses the interface and the Builder API to allow defining the URL end point for the HTTP operations.
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            //Creating ApiInterface by using retrofit Builder object
            final ApiInterface request = retrofit.create(ApiInterface.class);

            //
            Call<Example> call = request.getJSON();
            Log.d("VT", "Base url :" + BASE_URL);

//   Asynchronously send the request and notify {@code callback} of its response or if an error
//    occurred talking to the server, creating the request, or processing the response
            call.enqueue(new Callback<Example>() {
                @Override
                //Response
                public void onResponse(@NonNull Call<Example> call, @NonNull Response<Example> response) {
                    Log.d("VT", "status " + response);
                    myArticlesList = new ArrayList<>();
                    System.out.println("Message 1" + response.message());
                    if (response.isSuccessful()) {
                        System.out.println("Message 2" + response.message());
                        //Getting response body in Example Model class
                        Example example = response.body();

                        assert example != null;

                        data = example.getArticles();

                        Log.d("VT", "Example data : " + example.getArticles().get(1).getDescription());
                        for (int i = 0; i < data.size(); i++) {
                            Log.d("VT", " data : " + data.get(i));
                        }
                        Log.d("VT", "Application class : " + data.size());


                    } else {
                        System.out.println("Message 3" + response.message());
                    }
                    //Setting adapter to RecyclerView with data
                    setMyAdapter(data);
                }

                @Override
                public void onFailure(@NonNull Call<Example> call, @NonNull Throwable t) {
                    Log.d("VT", "Got error : " + t.getMessage());
                }
            });


            return null;
        }
    }

    private void setMyAdapter(List<Article> data) {
        Log.d("VT", "setMyAdapter() called with: data = [" + data + "]");
        //Typecasting RecyclerView, and setting LayoutManager and adapter as well as click listener
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerViewAdapter adapter = new recyclerViewAdapter(data, this);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
    }

    //Predefined backpress method, enabling double tab to exit
    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }
}
