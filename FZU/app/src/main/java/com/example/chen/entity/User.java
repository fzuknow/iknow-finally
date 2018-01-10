package com.example.chen.entity;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by laixl on 2017/11/7.
 */

public class User extends AppCompatActivity implements Serializable{
    private int studentId;
    private String studentNo;
    private String password;
    private String studentName;
    private String sex;
    private String headimageUrl;
    private String birthday;
    private String academyName;

    public String getStudentNo() {return studentNo;}

    public void setStudentNo(String studentNo) {
        this.studentNo = studentNo;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStudentName() {return studentName;}

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getHeadimageUrl() {
        return headimageUrl;
    }

    public void setHeadimageUrl(String headimageUrl) {
        this.headimageUrl = headimageUrl;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getAcademyName() {
        return academyName;
    }

    public void setAcademyName(String academyName) {
        this.academyName = academyName;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getWealth() {
        return wealth;
    }

    public void setWealth(int wealth) {
        this.wealth = wealth;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public int getXp() {
        return xp;
    }

    public void setXp(int xp) {
        this.xp = xp;
    }

    public int getLv() {
        return lv;
    }

    public void setLv(int lv) {
        this.lv = lv;
    }

    private String major;
    private String email;
    private int wealth;
    private String telephone;
    private int xp;
    private int lv;
    public String toString() {
        return
                "name='" + studentName + '\'' +
                ", no='" + studentNo +'\'' +
                ", id='" + studentId + '\'' + ", password='" + password + '\'' +
        ",sex='"+sex+'\''+
        ",headimageUrl='"+headimageUrl+'\''+
       ",birthday='"+ birthday+'\''+
       ",academyName="+academyName+
                        ", major='" + major + '\'' +
                        ", email='" + email+'\''+
                        ",wealth="+wealth+
                        ",telephone='"+telephone+'\''+
                        ",xp="+ xp+
                        ",lv="+lv;
    }
}
