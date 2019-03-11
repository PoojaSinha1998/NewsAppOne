package com.intellinettechnologies.newsappone.MyModel;

import android.app.Application;
import java.util.List;


//Application class to manage data throughout the application
public class myapplication extends Application {


    private static List<Article> data;

    public static List<Article> getExamples() {
        return data;
    }

    public static void setExamples(List<Article> data) {
        myapplication.data = data;
    }


}
