package com.example.chen.entity;

/**
 * Created by laixl on 2018/1/3.
 */

public class Academy {
    private String academyTitle;//文章标题
    private String academyUrl;//文章链接地址
    private String academyTime;//文章时间
    public Academy(String academyTitle,String academyUrl,String academyTime) {

        this.academyTitle=academyTitle;
        this.academyUrl=academyUrl;
        this.academyTime=academyTime;
    }

    public String getAcademyTitle() {
        return "> "+academyTitle;
    }

    public void setAcademyTitle(String academyTitle) {
        this.academyTitle = academyTitle;
    }

    public String getAcademyUrl() {
        return "http://news.fzu.edu.cn"+academyUrl;
    }

    public void setAcademyUrl(String academyUrl) {
        this.academyUrl = academyUrl;
    }

    public String getAcademyTime() {
        return academyTime;
    }

    public void setAcademyTime(String academyTime) {
        this.academyTime = academyTime;
    }
}
