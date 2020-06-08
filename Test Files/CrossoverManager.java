import java.util.Random;

public class CrossoverManager implements Constraints {

    int r, b, g, v, p, o, y;

    public Floor crossover(Floor mother, Floor father, String machineList) {

        Random random = new Random();

        char[][] child = new char[gridSizeX][gridSizeY];

        r = machineList.length() - machineList.replace("r", "").length();
        b = machineList.length() - machineList.replace("b", "").length();
        g = machineList.length() - machineList.replace("g", "").length();
        v = machineList.length() - machineList.replace("v", "").length();
        p = machineList.length() - machineList.replace("p", "").length();
        o = machineList.length() - machineList.replace("o", "").length();
        y = machineList.length() - machineList.replace("y", "").length();

        int turningPoint = (gridSizeX*gridSizeY/2);

        for(int i = 0; i < gridSizeX; i++){
            for(int j = 0; j < gridSizeY; j++){
                if(i * j < turningPoint){
                    if(subtractColorList(mother.floor[i][j])){
                        child[i][j] = mother.floor[i][j];
                    } else {
                        child[i][j] = 'h';
                    }
                } else {
                    if(subtractColorList(father.floor[i][j])){
                        child[i][j] = father.floor[i][j];
                    } else {
                        child[i][j] = 'h';
                    }
                }
            }
        }

        String failedMachines = this.createNewReturnList();
        int mutationProb = failedMachines.length();

        for(int i = 0; i < gridSizeX; i++){
            for(int j = 0; j < gridSizeY; j++){
                if(child[i][j] == 'h'){
                    StringBuilder sb = new StringBuilder(failedMachines);
                    int index = random.nextInt(failedMachines.length());
                    child[i][j] = failedMachines.charAt(index);
                    sb.deleteCharAt(index);
                    failedMachines = sb.toString();
                }
            }
        }

        Floor f = new Floor();
        f.setFloor(child);

        if(mutationProb<mutationThreshold) {
            f.mutate(random.nextInt(mutationThreshold - mutationProb));
        }

        return f;
    }

    private boolean subtractColorList(char c){
        switch (c){
            case 'r': if(r > 0){
                r--;
                return true;
            } break;

            case 'b': if(b > 0){
                b--;
                return true;
            } break;

            case 'g': if(g > 0){
                g--;
                return true;
            } break;

            case 'v': if(v > 0){
                v--;
                return true;
            } break;

            case 'p': if(p > 0){
                p--;
                return true;
            } break;

            case 'o': if(o > 0){
                o--;
                return true;
            } break;

            case 'y': if(y > 0){
                y--;
                return true;
            } break;
        }
        return false;
    }

    private String createNewReturnList(){
        StringBuilder temp = new StringBuilder();

        for (int i = 0; i <  r; i++){
            temp.append("r");
        }
        for (int i = 0; i <  b; i++){
            temp.append("b");
        }
        for (int i = 0; i <  g; i++){
            temp.append("g");
        }
        for (int i = 0; i <  v; i++){
            temp.append("v");
        }
        for (int i = 0; i <  p; i++){
            temp.append("p");
        }
        for (int i = 0; i <  o; i++){
            temp.append("o");
        }
        for (int i = 0; i <  y; i++){
            temp.append("y");
        }
        return temp.toString();
    }
}
