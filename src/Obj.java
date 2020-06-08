package com.company;

import javafx.scene.shape.Polygon;

import java.util.ArrayList;

public class Obj {

    Polygon polygon = new Polygon();

    Double[] shape;

    ArrayList<Obj> children = new ArrayList<>();

    String color = "";

    int layer = 0;

    Ent e;

    Obj parent = null;

    int offset = 0;

    int cumSizeOffset = 0;

    int cumYPosOffset = 0;

    public Obj(Ent e){
        this.e = e;
    }

    public void all(){
        makeShape();
        finishShape();
    }

    public void makeShape(){
        double x1, x2, y1, y2;
        x1 = layer*e.xpt;
        x2 = e.size[0] - x1;
        y1 = e.ypt*cumYPosOffset;
        y2 = e.ypt*cumSizeOffset + y1;
        shape = new Double[]{x1, y1, x2, y1, x2, y2, x1, y2};
    }
    public void finishShape(){
        polygon.getPoints().addAll(shape);
    }

}