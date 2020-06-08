/*

import java.util.ArrayList;
import java.util.concurrent.*;

public class TaskManager implements Constraints{

    public ArrayList<Floor> randGen(String machineList) throws ExecutionException, InterruptedException {
        CountDownLatch l = new CountDownLatch(numChildren);
        ExecutorService exe = Executors.newFixedThreadPool(numChildren);
        ArrayList<Future<Floor>> floors = new ArrayList<>();

        for(int i = 0; i < numChildren; i++){
            floors.add(exe.submit(new RandomGenThred(l, i, machineList)));
        }

        try {
            l.await();
        } catch (InterruptedException ie){
            ie.printStackTrace();
        }

        ArrayList<Floor> newFloors = new ArrayList<>();
        for(int i = 0; i < floors.size(); i++){
            newFloors.add(floors.get(i).get());
            newFloors.get(i).fitness();
            System.out.println(newFloors.get(i).fitness);
        }
        return newFloors;
    }

    public ArrayList<Floor> updateGen(String machineList, Floor[] genePool) throws ExecutionException, InterruptedException {
        CountDownLatch l = new CountDownLatch(numChildren);
        ExecutorService exe = Executors.newFixedThreadPool(numChildren);
        ArrayList<Future<Floor>> floors = new ArrayList<>();

        for(int i = 0; i < numChildren; i++){
            floors.add(exe.submit(new UpdateThred(l, i, machineList)));
        }

        try {
            l.await();
        } catch (InterruptedException ie){
            ie.printStackTrace();
        }

        ArrayList<Floor> newFloors = new ArrayList<>();
        for(int i = 0; i < floors.size(); i++){
            newFloors.add(floors.get(i).get());
            newFloors.get(i).fitness();
            System.out.println(newFloors.get(i).fitness);
        }
        return newFloors;
    }
}
*/