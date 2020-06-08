package com.company;

import javafx.scene.Node;
import javafx.scene.Scene;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Random;
import java.util.stream.Stream;
import javafx.scene.Group;
import javafx.scene.paint.*;
import javafx.geometry.*;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;

import javax.imageio.ImageIO;

public class ParcerMan implements Int{

    boolean hasVars;
    boolean hasComm;
    String comp;
    String scheme;
    String dir;

    ArrayList<String> paths = new ArrayList<>();

    public ParcerMan(String d, boolean hv, boolean hc, String scheme, String comp) {

        this.hasVars = hv;
        this.hasComm = hc;
        this.dir = d;
        this.comp = comp;
        this.scheme = scheme;

        try (Stream<Path> srcFolder = Files.walk(Paths.get(dir))) {
            srcFolder.forEach(n -> paths.add(String.valueOf(n)));
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-2);
        }

        woah();

        switch (comp){
            case "Wide, Descending": wideDescending(); break;
            case "Wide, Ascending": wideAcceding(); break;
            default: System.exit(-3);
        }

        Stage stage = new Stage();
        stage.setTitle("Make a drawing");
        stage.setScene(s);
        s.setFill(Paint.valueOf(entities.get(0).scheme[entities.get(0).scheme.length-1]));
        stage.show();
    }

    ArrayList<Ent> entities = new ArrayList();
    ArrayList<String> vars = new ArrayList<>();
    ArrayList<String> comments = new ArrayList<>();

    String[] inputScheme = null;

    public void woah(){

        switch (scheme){
            case "80's": inputScheme = basicColorShemes[0]; break;
            case "Icecream": inputScheme = basicColorShemes[1]; break;
            case "Nice": inputScheme = basicColorShemes[2]; break;
            case "Dark Rainbow": inputScheme = basicColorShemes[3]; break;
            case "Yellow Mono": inputScheme = basicColorShemes[4]; break;
            case "Rainbow": inputScheme = basicColorShemes[5]; break;
            case "Sunset": inputScheme = basicColorShemes[6]; break;
            case "Hot Garbage": inputScheme = basicColorShemes[7]; break;
            default: System.exit(-1);
        }

        for(int i = 1; i < paths.size(); i++){
            Parser p = new Parser(inputScheme);
                p.parse(paths.get(i));
                entities.addAll(p.entities);
                vars.addAll(p.vars);
                comments.addAll(p.comments);
        }

        setVars();
    }

    ArrayList<Var> varObjs = new ArrayList<>();

    public void setVars(){
        for(int i = 0; i < vars.size(); i++){
            boolean isnotinside = true;
            for(int j = 0; j < varObjs.size(); j++){
                if(vars.get(i).equals(varObjs.get(j).var)){
                    isnotinside = false;
                    varObjs.get(j).woah();
                }
            }
            if(isnotinside){
                varObjs.add(new Var(vars.get(i)));
            }
        }
    }

    public void sortBubbles(){
        varObjs.sort(new BubbleSort());
    }

    public void wideDescending(){
        descendingHeightSort();
        setupCanvasWide();
        setupSceneWide();
    }

    public void wideAcceding(){
        accedingHeightSort();
        setupCanvasWide();
        setupSceneWide();
    }

    public void descendingHeightSort(){
        entities.sort(new CompareLogic1());
    }

    public void accedingHeightSort(){
        entities.sort(new CompareLogic2());
    }

    double canX = 0;
    double canY = 0;

    public void setupCanvasWide(){
        //canX = 2.0;
        canY = 0;
        for(int i = 0; i < entities.size(); i++){
            canX+= entities.get(i).size[0];
            canX+= xSpace;
            if(entities.get(i).size[1] > canY){
                canY = entities.get(i).size[1];
            }
        }
    }

    public void setBubbles(){
        sortBubbles();
        int min = varObjs.get(0).ammt;
        int max = varObjs.get(varObjs.size()-1).ammt;

        double slope = (bubbleEnd-bubbleStart)/(max-min);
        double yInt = (slope*min)/bubbleStart;

        System.out.println("slope " + slope + " inter " + yInt);

        for(int i = 0; i < varObjs.size(); i++){
            varObjs.get(i).setRadius(yInt, slope);
        }
    }

    Scene s;

    public void makeBubbles(double x, double y,  Pane root){
        Random r = new Random();

        for(int i = 0; i < varObjs.size(); i++){

            double rad = varObjs.get(i).radius;

            double randX = (x) - varObjs.get(i).radius;
            double centerX = (varObjs.get(i).radius) + r.nextInt((int) randX);

            double randY = y - (varObjs.get(i).radius);
            double centerY = (varObjs.get(i).radius) + r.nextInt((int) randY);

            Circle c = new Circle(centerX, centerY, rad);
            c.setFill(Paint.valueOf(inputScheme[r.nextInt(inputScheme.length)]));

            boolean isIntersect;

            do {
               isIntersect = false;
               for(Node node : root.getChildren()){
                   if(node.getBoundsInParent().intersects(c.getBoundsInParent())){
                        isIntersect = true;
                   }
               }
               if(isIntersect){
                   randX = (x) - varObjs.get(i).radius;
                   centerX = (varObjs.get(i).radius) + r.nextInt((int) randX);

                   randY = y - (varObjs.get(i).radius);
                   centerY = (varObjs.get(i).radius) + r.nextInt((int) randY);

                   c = new Circle(centerX, centerY, rad);
                   c.setFill(Paint.valueOf(inputScheme[r.nextInt(inputScheme.length)]));
               }
            }while(isIntersect);

            root.getChildren().add(c);
        }
    }

    public void setupSceneWide(){
        double x = wideXstart;
        double y = wideYStart;
        Pane root = new Pane();

        for(int i = 0; i < entities.size(); i++){
            entities.get(i).pRoot.setLayoutX(x);
            entities.get(i).pRoot.setLayoutY(y);
            root.getChildren().add(entities.get(i).pRoot);
            x+= xSpace + entities.get(i).size[0];
        }

        setBubbles();
        double yMax = root.getBoundsInParent().getHeight();

        for(int i = 0; i < varObjs.size(); i++){
            varObjs.get(i).setRadius(yMax);
        }
        if(hasVars) {
            makeBubbles(root.getBoundsInParent().getWidth(), root.getBoundsInParent().getHeight(), root);
        }

        System.out.println(root.getBoundsInParent().getWidth() + "   " + root.getBoundsInParent().getHeight());

        root.setStyle("-fx-background-color: #" + inputScheme[inputScheme.length-1]);
        s = new Scene(root, root.getBoundsInParent().getWidth(), root.getBoundsInParent().getHeight());

        WritableImage img = root.snapshot(new SnapshotParameters(), null);
        root.getChildren().add(new ImageView(img));
        BufferedImage temp = new BufferedImage((int) root.getBoundsInParent().getWidth(), (int) root.getBoundsInParent().getHeight(), BufferedImage.TYPE_INT_ARGB);
        BufferedImage finImg = javafx.embed.swing.SwingFXUtils.fromFXImage(img, temp);
        Graphics2D g = (Graphics2D)finImg.getGraphics();
        g.translate(canX, canY);

        File f = new File(dir + " img.png");
        try {
            ImageIO.write(finImg, "png", f);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}