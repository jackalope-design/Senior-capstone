package com.company;

import java.util.Comparator;

public class CompareLogic1 implements Comparator<Ent> {
    @Override
    public int compare(Ent ent, Ent t1) {
        if(ent.ySize == t1.ySize){
            if(ent.xSize < t1.xSize){
                return -1;
            }
        }else if (ent.ySize < t1.ySize){
            return -1;
        }
        return 1;
    }
}

