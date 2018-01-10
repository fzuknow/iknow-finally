package com.example.chen.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by laixl on 2017/11/24.
 */

public class Article implements Serializable {
    private String title;
    private String content;
    private int articleId;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getArticleId() {
        return articleId;
    }

    public void setArticleId(int articleId) {
        this.articleId = articleId;
    }

}
