import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
import java.util.concurrent.*;

public class GeneticManager implements Constraints{
    ArrayList<Floor> children = new ArrayList<>();
    Floor[] genePool = new Floor[poolSize];
    String machineList = "";
    int genNum = 0;
    CountDownLatch l = new CountDownLatch(numChildren);
    //ExecutorService exe = Executors.newFixedThreadPool(numChildren);
    ArrayList<Future<Floor>> floors = new ArrayList<>();

    public void startGA(){
        this.setMachineList();
        try {
            this.firstGeneration();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        this.calculatePopulationFitness();
        this.sortChildren();
        this.initGenePool();
        genNum++;
    }

    public void firstGeneration() throws ExecutionException {
        ///CountDownLatch l = new CountDownLatch(numChildren);
        ExecutorService exe = Executors.newFixedThreadPool(numChildren);
        ///ArrayList<Future<Floor>> floors = new ArrayList<>();
        floors.clear();

        for(int i = 0; i < numChildren; i++){
            floors.add(exe.submit(new RandomGenThred(l, i, machineList)));
        }

        try {
            l.await();
        } catch (InterruptedException ie){
            ie.printStackTrace();
        }

        for(int i = 0; i < floors.size(); i++){
            try {
                children.add(floors.get(i).get());
            } catch (InterruptedException e) {

                e.printStackTrace();
            }
        }
    }

    public void nextGeneration() throws ExecutionException {
        children.clear();
        Random r = new Random();
        CrossoverManager cm = new CrossoverManager();

        ///CountDownLatch l = new CountDownLatch(numChildren);
        ExecutorService exe = Executors.newFixedThreadPool(numChildren);
        ///ArrayList<Future<Floor>> floors = new ArrayList<>();
        floors.clear();

        for(int i = 0; i < numChildren; i++){
            //children.add(cm.crossover(genePool[r.nextInt(genePool.length)], genePool[r.nextInt(genePool.length)], machineList));
            floors.add(exe.submit(new UpdateThred(l, i, genePool[r.nextInt(genePool.length)], genePool[r.nextInt(genePool.length)], machineList)));
        }

        try {
            l.await();
        } catch (InterruptedException ie){
            ie.printStackTrace();
        }

        for(int i = 0; i < floors.size(); i++){
            try {
                children.add(floors.get(i).get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        this.calculatePopulationFitness();
        this.sortChildren();
        this.initGenePool();
        genNum++;
    }

    public void calculatePopulationFitness(){
        for(int i = 0; i < children.size(); i++){
            children.get(i).fitness();
        }
    }

    public void setMachineList(){
        Random r = new Random();
        int amountOfMachines = gridSizeX*gridSizeY;
        for(int i = 0; i < amountOfMachines; i++){
            char c = machineIdentifiers[r.nextInt(machineIdentifiers.length)];
            machineList = machineList + c;
        }
    }

    public void sortChildren(){
        Collections.sort(children);
    }

    public void initGenePool(){
        for(int i = 0; i < genePool.length; i++){
            genePool[i] = children.get(i);
        }
    }

    public void updateGenePool(){
        ArrayList<Floor> temp = new ArrayList<>();
        temp.addAll(Arrays.asList(genePool));
        temp.addAll(children);
        Collections.sort(temp);
        for(int i = 0; i < genePool.length; i++){
            genePool[i] = children.get(i);
        }
    }

    public Floor getChampian(){
        return genePool[0];
    }

}
