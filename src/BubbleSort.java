package com.company;

import java.util.Comparator;

public class BubbleSort implements Comparator<Var> {
    @Override
    public int compare(Var v, Var v2) {
            if(v.ammt < v2.ammt){
                return -1;
            }
        return 1;
    }
}

