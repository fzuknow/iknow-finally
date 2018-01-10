package com.example.chen.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by laixl on 2017/11/9.
 */

 public class Answer implements Serializable {
        private int id;
        private int studentId;
        private int questionId;
        private String content;
        private String answerDate;
        private int praiseNum;
    private User student;

    public User getStudent() {
        return student;
    }

    public void setStudent(User student) {
        this.student = student;
    }

        public int getId() {
            return id;
        }

        public void setId(int id) {this.id = id;}

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

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getAnswerDate() {
            return answerDate;
        }

        public void setAnswerDate(String answerDate) {
            this.answerDate = answerDate;
        }

        public int getPraiseNum() {
            return praiseNum;
        }

        public void setPraiseNum(int praiseNum) {
            this.praiseNum = praiseNum;
        }
    }


