package com.example.android_work;

import java.io.Serializable;

public class Info implements Serializable {
    private String thing;
    private String date;
    public String getDate(){
        return date;
    }
    public String getThing(){
        return thing;
    }
    public Info(String thing,String date){
        this.thing = thing;
        this.date = date;
    }
    public void setThing(String thing){
        this.thing = thing;
    }
    public void setDate(String date){
        this.date = date;
    }
}
