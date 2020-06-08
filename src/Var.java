package com.company;

public class Var {

    public String var;
    public int ammt = 1;
    double radius = -1;
    double val = -1;

    Var (String v){
        var = v;
    }

    public void woah(){
        ammt++;
    }

    public void setRadius(double yint, double slope){
        val = slope*ammt+yint;
    }

    public void setRadius(double mult){
        radius = (val*mult)/2;
    }
}
