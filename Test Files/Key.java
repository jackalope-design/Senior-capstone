import java.util.ArrayList;
import java.util.LinkedList;

public class Key {

    //private LinkedList<String> img;
    public ArrayList<String> images = new ArrayList<>();
    public String key;

    public Key(){
        key = "e";
    }

    public void addScan(String s){
        images.add(s);
    }

    public void setKey(String k){
        key = k;
    }

    public void removeKey(){
        key = "e";
    }

}