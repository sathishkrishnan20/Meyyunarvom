package com.avs.db;

/**
 * Created by SATHISH on 1/24/2017.
 */

public class ChattuDAO {

    public ChattuDAO()
    {

    }


    private String content;
    private String title;
    private int rollno;

    public ChattuDAO(int rollno, String title, String content){
        this.rollno = rollno;
        this.title = title;
        this.content = content;
    }

    public int getRollno() {

        return rollno;
    }

    public void setRollno(int rollno)
    {
        this.rollno = rollno;
    }

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

}
