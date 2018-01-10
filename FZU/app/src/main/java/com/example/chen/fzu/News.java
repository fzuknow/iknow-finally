package com.example.chen.fzu;

/**
 * Created by Administrator on 2017/12/22/022.
 */

public class News {
        private String articleTitle;//文章标题
        private String articleUrl;//文章链接地址
        //private String desc;//文章概要
        private String articleDivision;//文章发布者
        private String articleTime;//文章时间
        public News(String articleTitle,String articleUrl,String articleDivision,String articleTime)
        {
            this.articleTitle=articleTitle;
            this.articleUrl=articleUrl;
       //     this.desc=desc;
            this.articleDivision=articleDivision;
            this.articleTime=articleTime;
        }

        public String getArticleTime() {
            return articleTime;
        }

        public void setArticleTime(String articleTime) {
            this.articleTime = articleTime;
        }

        public String getArticleTitle() {
            return "> "+articleTitle;
        }

        public void setArticleTitle(String articleTitle) {
            this.articleTitle = articleTitle;
        }

        public String getArticleUrl() {
            return "http://jwch.fzu.edu.cn"+articleUrl;
        }

        public void setArticleUrl(String articleUrl) {
            this.articleUrl = articleUrl;
        }


        public String getArticleDivision() {
            return "["+articleDivision+"]";
        }

        public void setArticleDivision(String articleDivision) {
            this.articleDivision = articleDivision;
        }
    }
