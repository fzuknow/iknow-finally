package com.example.chen.entity;

import java.io.Serializable;

/**
 * Created by laixl on 2018/1/2.
 */

public class QuestPraise implements Serializable {
    private int id;
    private int studentId;
    private int questionId;
    private int bepraise_studentId;
    private String praiseDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public String getPraiseDate() {
        return praiseDate;
    }

    public void setPraiseDate(String praiseDate) {
        this.praiseDate = praiseDate;
    }

    public int getBepraise_studentId() {
        return bepraise_studentId;
    }

    public void setBepraise_studentId(int bepraise_studentId) {
        this.bepraise_studentId = bepraise_studentId;
    }
}
