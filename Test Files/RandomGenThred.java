

import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;

public class RandomGenThred implements Callable<Floor>, Constraints {

    CountDownLatch l;
    int runNum;
    char[][] floor;
    String machineList;

    public RandomGenThred(CountDownLatch l, int numRun, String machineList){
        this.l = l;
        this.runNum = numRun;
        this.machineList = machineList;
    }

    @Override
    public Floor call() throws Exception {
        Floor f = new Floor();
        //Thread.sleep(2000);
        f.generateRandomFloor(machineList);
        l.countDown();
        return f;
    }
}
