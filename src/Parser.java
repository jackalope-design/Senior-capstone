package com.company;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {

    ArrayList<String> remainingFile = new ArrayList<>();
    String[] scheme;

    public Parser(String[] s){
        scheme = s;
    }

    public void parse(String path) {

        File f = new File(path);
        FileReader fr;
        remainingFile.clear();

        {
            try {
                fr = new FileReader(f);
                BufferedReader bfr = new BufferedReader(fr);
                String line;
                while ((line = bfr.readLine()) != null) {
                    if (!line.equals("")) {
                        remainingFile.add(line.trim());
                    }
                }
                bfr.close();
                fr.close();

                getComments();
                getVars();
                getFunc();
                setEnts();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    ArrayList<String> comments = new ArrayList<>();

    public void getComments(){
        for(int i = 0; i < remainingFile.size(); i++){
            int sub = remainingFile.get(i).indexOf("//");
            if(sub != -1){
                comments.add(remainingFile.get(i).substring(sub));
                remainingFile.set(i, "");
            }
        }

        boolean b = true;
        while (b){
            int line1 = 0;
            b = false;
            while (line1 < remainingFile.size()){
                if(remainingFile.get(line1).contains("/**")){
                    b = true;
                    int line2 = line1;
                    while (line2 < remainingFile.size()){
                       if(remainingFile.get(line2).contains("**/")){
                           for(int i = 0; i < (line2 + 1) - line1; i++){
                               remainingFile.remove(line1);
                           }
                       }
                       line2++;
                    }
                }
                line1++;
            }
        }

        for (int i = 0; i < remainingFile.size(); i++){
            if(remainingFile.get(i).contains("@Override")){
                remainingFile.set(i, "");
            }
        }

        int i = 0;
        while(i < remainingFile.size()){
            if(remainingFile.get(i).equals("")){
                remainingFile.remove(i);
            }else {
                i++;
            }
        }
        //System.out.println(Arrays.toString(comments.toArray()));
    }

    ArrayList<String> vars = new ArrayList<>();

    public void getVars(){
        Pattern p = Pattern.compile("[A-Za-z0-9]+ [A-Za-z0-9]+ *=");

        for(int i = 0; i < remainingFile.size(); i++){
            Matcher m = p.matcher(remainingFile.get(i));
            while (m.find()){
                vars.add(m.group());
            }
        }

        Pattern p2 = Pattern.compile("[A-Za-z0-9]+ *=");
        Pattern p3 = Pattern.compile("[A-Za-z0-9]+");

        for(int i = 0; i < vars.size(); i++){
            String boi = vars.get(i);
            Matcher m2 = p2.matcher(boi);
            if(m2.find()){
                boi = m2.group();
            }
            Matcher m3 = p3.matcher(boi);
            if(m3.find()) {
                boi = m3.group();
            }

            vars.set(i, boi);
        }
        //System.out.println(Arrays.toString(vars.toArray()));
    }


    ArrayList<String> structs = new ArrayList<>();

    public void getFunc(){
        StringBuilder structure = new StringBuilder();
        for(int i = 0; i < remainingFile.size(); i++){
            String line = remainingFile.get(i);
            for(int j = 0; j < line.length(); j++){
                if(line.charAt(j) == '{'){
                    structure.append("{");
                }
                if(line.charAt(j) == '}'){
                    structure.append("}");
                }
            }
        }
        String temp = structure.toString();
        int place = 0;
        StringBuilder ent = new StringBuilder();
        for(int i = 1; i < temp.length(); i++){
            if(temp.charAt(i) == '{'){
                place++;
                ent.append("{");
            }
            if(temp.charAt(i) == '}'){
                place--;
                ent.append("}");
            }
            if(place == 0){
                String sTemp = ent.toString();
                if(sTemp.length() > 2){
                    structs.add(sTemp);
                }
                ent = new StringBuilder();
            }
        }
        //System.out.println(Arrays.toString(structs.toArray()));
    }

    public void setEnts(){
        for(int i = 0; i < structs.size(); i++){
            forgeEnt(structs.get(i));
        }
    }

    ArrayList<Ent> entities = new ArrayList();

    public void forgeEnt(String struct){
        Ent e = new Ent(scheme);
        Obj o = new Obj(e);
        e.root = o;
        o.parent = o;
        Obj cur = o;
        int layerChamp = 0;
        for(int i = 1; i < struct.length(); i++){
            if(struct.charAt(i) == '{'){
                Obj nObj = new Obj(e);
                nObj.parent = cur;
                nObj.layer = cur.layer+1;
                cur.children.add(nObj);
                if(nObj.layer > layerChamp){
                    layerChamp  = nObj.layer;
                }
                cur = nObj;
            }else{
                cur = cur.parent;
            }
        }
        e.xSize = layerChamp;
        e.doYoThang();
        entities.add(e);
    }
}


//[a-z].* [a-z].*=(?!=)
//[^A-Za-z0-9]+ [^A-Za-z0-9]+ *=(?!=
//\(.*\)\{
//(for|while|if|switch|do|catch) *\(.*\)