
package com.intellinettechnologies.newsappone.MyModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Example {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("totalResults")
    @Expose
    private Integer totalResults;
    @SerializedName("articles")
    @Expose
    private List<Article> articles = null;

// --Commented out by Inspection START (10-Mar-19 10:27 PM):
//    public String getStatus() {
//        return status;
//    }
// --Commented out by Inspection STOP (10-Mar-19 10:27 PM)

    public void setStatus(String status) {
        this.status = status;
    }

// --Commented out by Inspection START (10-Mar-19 10:39 PM):
//    public Integer getTotalResults() {
//        return totalResults;
//    }
// --Commented out by Inspection STOP (10-Mar-19 10:39 PM)

    public void setTotalResults(Integer totalResults) {
        this.totalResults = totalResults;
    }

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }

}
