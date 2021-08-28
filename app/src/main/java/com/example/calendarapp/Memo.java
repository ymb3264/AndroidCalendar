package com.example.calendarapp;

public class Memo {
//    private String user;
//    private String memoDate;
    private String date;
    private String content;

    public Memo() {}

    public Memo(String date, String content) {
//        this.user = user;
        this.date = date;
        this.content = content;
    }

//    public String getUser() {
//        return user;
//    }
//
//    public void setUser(String user) {
//        this.user = user;
//    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

//    public String getMemoDate() {
//        return memoDate;
//    }
//
//    public void setMemoDate(String memoDate) {
//        this.memoDate = memoDate;
//    }

    @Override
    public String toString() {
        return "Memo{" +
                "date='" + date + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
