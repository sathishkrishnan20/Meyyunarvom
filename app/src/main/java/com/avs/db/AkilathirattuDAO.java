package com.avs.db;

/**
 * Created by Sathish on 11/8/2016.
 */

public class AkilathirattuDAO
{
    public AkilathirattuDAO()
    {

    }


    private String content;
    private String title;
    private int rollno;


    public AkilathirattuDAO(int rollno, String title, String content){
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
