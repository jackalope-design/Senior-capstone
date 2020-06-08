import java.util.ArrayList;
import java.util.Random;

public class Floor implements Constraints, Comparable<Floor>{

    char[][] floor  = new char[gridSizeX][gridSizeY];
    char[][] tempFloor = new char[gridSizeX][gridSizeY];
    double fitness = 0;

    public void generateRandomFloor(String machinelist){
        Random r = new Random();
        for(int i = 0; i < floor.length; i++){
            for(int j = 0; j < floor[0].length; j++){
                int tempRandom = r.nextInt(machinelist.length());
                floor[i][j] = machinelist.charAt(tempRandom);
                StringBuilder sb = new StringBuilder(machinelist);
                sb.deleteCharAt(tempRandom);
                machinelist = sb.toString();
            }
        }
    }

    public void mutate(int ammount){
        for(int i = 0; i < ammount; i++){
            this.swap();
        }
    }

    public void swap(){
        Random r = new Random();
        int x1, x2, y1, y2;
        x1 = x2 = y1 = y2 = 0;

        while (x1 == x2 && y1 == y2){
            x1 = r.nextInt(floor.length);
            x2 = r.nextInt(floor.length);
            y1 = r.nextInt(floor[0].length);
            y2 = r.nextInt(floor[0].length);
        }

        char x2PositionHolder = floor[x2][y2];
        floor[x2][y2] = floor[x1][y1];
        floor[x1][y1] = x2PositionHolder;
    }

    public void fitness(){

        ArrayList<Integer> machines = new ArrayList<>();
        tempArrayCopy();
        for(int i = 0; i < tempFloor.length; i++){
            for(int j = 0; j < tempFloor[0].length; j++){
                if(tempFloor[i][j] != 'n'){
                    //if(floor[i][j] == 'h'){
                        //holes.add(plauge(i, j, feild[i][j], holeCompareList));
                    //}else {
                        machines.add(plauge(i, j, tempFloor[i][j]));
                    //}
                }
            }
        }

        int sum = 0;
        for (int i = 0; i < machines.size(); i++){
            sum = sum + machines.get(i);
        }

        int average = sum/machines.size();

        this.fitness = (Math.pow(machines.size(), 3) / average);
    }

    public int plauge(int x, int y, char c){
        tempFloor[x][y] = 'n';
        int right, left, up, down;
        left = right = up = down = 0;

        if(!(x <= 0)){
            if(c == tempFloor[x - 1][y]) {
                left = plauge(x - 1, y, c);
            }
        }
        if(!(x >= tempFloor.length - 1)){
            if(c  == tempFloor[x + 1][y]) {
                right = plauge(x + 1, y, c);
            }
        }
        if(!(y <= 0)){
            if(c == tempFloor[x][y - 1]) {
                down = plauge(x,y - 1, c);
            }
        }
        if(!(y >= tempFloor.length - 1)){
            if(c == tempFloor[x][y + 1]) {
                up = plauge(x,y + 1, c);
            }
        }
        return 1 + left + right + up + down;
    }

    private void tempArrayCopy() {
        for (int i = 0; i < floor.length; i++) {
            for (int j = 0; j < tempFloor[0].length; j++) {
                tempFloor[i][j] = floor[i][j];
            }
        }
    }

    public void setFloor(char[][] floor){
        this.floor = floor;
    }

    @Override
    public int compareTo(Floor o) {

        if (o.fitness == this.fitness){
            return 0;
        }

        if (o.fitness < this.fitness){
            return 1;
        }

        return -1;
    }
}
