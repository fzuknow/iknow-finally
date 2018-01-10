package com.example.chen.entity;

/**
 * Created by Administrator on 2018/1/5/005.
 */
import java.io.Serializable;
public class QuestAnsPraise implements Serializable {
    private int id;
    private int studentId;
    private int questAnswerId;
    private String qapDate;

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

    public int getQuestAnswerId() {
        return questAnswerId;
    }

    public void setQuestAnswerId(int questAnswerId) {
        this.questAnswerId = questAnswerId;
    }

    public String getQapDate() {
        return qapDate;
    }

    public void setQapDate(String qapDate) {
        this.qapDate = qapDate;
    }
}
