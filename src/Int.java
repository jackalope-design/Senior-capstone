package com.company;

public interface Int {
    double wideXstart = 0;
    double wideYStart = 0;
    double xSpace = 20;
    double xObjSize = 20;
    double yObjSize = 20;
    double bubbleStart = 0.1;
    double bubbleEnd = 0.4;

    String[][] basicColorShemes = {
            {"fdfffc", "ef4c05", "03313d", "e0b610", "444f03"}, //"80's"
            {"c9b79c", "f4afe6", "c7eae4", "da667b", "342a21"},  // icecream
            {"5bc0eb", "fde74c", "9bc53d", "e55934", "fa7921"}, //nice
            {"4aff05", "e7b222", "fa4210", "a204e6", "0673fa", "000000"}, //dark rainbow
            {"f4e409", "eeba0b", "c36f09", "a63c06", "710000"}, //yellow mono
            {"390099", "9e0059", "ff0054", "ff5400", "ffbd00"}, //rainbow
            {"290078", "780047", "a5002a", "f70000", "ff4e00", "1f004b"}, //sunset
            {"red", "orange", "yellow"} // HOt garbage
    };
}
