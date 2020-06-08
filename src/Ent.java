package com.company;

import javafx.scene.Group;
import javafx.scene.paint.Paint;

public class Ent implements Int{
    double[] size = new double[2];
    String[] scheme;
    Group pRoot = new Group();
    Obj root = null;
    int ySize = -1;
    int xSize = -1;
    double ypt = -1.0;
    double xpt = -1.0;
    double maxRadius = -1;

    public Ent(String[] s){
        scheme = s;
    }

    public void doYoThang(){
        setCumPos();
        setCumSize();
        //setYSize();
        //setxSize();
        setSizes();
        color();
        fillPolyList();
    }

    public void setSizes(){
        xpt = xObjSize;
        ypt = yObjSize;
        ySize = root.cumSizeOffset;
        size[0] = ((xSize*xpt)*2)+(xpt*2);
        size[1] = ypt*ySize;

    }

    //public void setxSize(){
        //xpt = (((size[0]*.5)/xSize)/2);
    //}

    //public void setYSize(){
        //ySize = root.cumSizeOffset;
        //ypt = size[1]/ySize;
    //}

    public void setCumSize(){
        sizeDecent(root);
    }

    public void setCumPos(){
        posDecent(0, root);
    }

    public void sizeDecent(Obj layer){
        layer.offset = layer.children.size()+1;
        if(layer.children.size() > 0) {
            int temp = 0;
            for (int i = 0; i < layer.children.size(); i++) {
                sizeDecent(layer.children.get(i));
                temp = temp + layer.children.get(i).cumSizeOffset;
            }
            layer.cumSizeOffset = temp + layer.offset;
        }else{
            layer.cumSizeOffset = layer.children.size()+1;
        }
    }
    //gets cumulative size

    public int posDecent(int pos, Obj layer){
        layer.cumYPosOffset = pos;
        pos++;
        for(int i = 0; i < layer.children.size(); i++){
            pos = posDecent(pos, layer.children.get(i));
        }
        return pos+1;
    }

    public void color(){
        colorDecent(root, 0);
    }

    public void colorDecent(Obj layer, int mod){
        layer.color = scheme[((mod+1)%scheme.length)];
        for(int i = 0; i < layer.children.size(); i++){
            colorDecent(layer.children.get(i), mod+1);
        }
        layer.polygon.setFill(Paint.valueOf(layer.color));
    }

    public void fillPolyList(){
        polyDecent(root);

    }

    public void polyDecent(Obj layer) {
        layer.all();
        pRoot.getChildren().add(layer.polygon);
        for (int i = 0; i < layer.children.size(); i++) {
            polyDecent(layer.children.get(i));
        }
    }
}
