package com.example.chen.fzu;

/**
 * Created by Administrator on 2017/12/27/027.
 */

public class MTFuDa {
    private String mtfdTitle;//文章标题
    private String mtfdUrl;//文章链接地址
    private String mtfdTime;//文章时间
    public MTFuDa(String mtfdTitle,String mtfdUrl,String mtfdTime)
    {
        this.mtfdTitle=mtfdTitle;
        this.mtfdUrl=mtfdUrl;
        this.mtfdTime=mtfdTime;
    }

    public String getMtfdTitle() {
        return "> "+mtfdTitle;
    }

    public void setMtfdTitle(String mtfdTitle) {
        this.mtfdTitle = mtfdTitle;
    }

    public String getMtfdUrl() {
        return "http://news.fzu.edu.cn"+mtfdUrl;
    }

    public void setMtfdUrl(String mtfdUrl) {
        this.mtfdUrl = mtfdUrl;
    }

    public String getMtfdTime() {
        return mtfdTime;
    }

    public void setMtfdTime(String mtfdTime) {
        this.mtfdTime = mtfdTime;
    }
}
