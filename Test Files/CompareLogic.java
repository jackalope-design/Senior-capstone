public class CompareLogic {

    public boolean compare(String num1, String num2){
        String num1_s, num2_s;
        //num1_s = String.valueOf(num1);
        //num2_s = String.valueOf(num2);

        int num1_id, num1_year, num1_month, num1_day, num1_hour, num1_second, num1_img_code;
        int num2_id, num2_year, num2_month, num2_day, num2_hour, num2_second, num2_img_code;


        //
        num1_id = Integer.parseInt(num1.substring(0, 6));
        num2_id = Integer.parseInt(num2.substring(0, 6));
        //
        num1_month = Integer.parseInt(num1.substring(7, 8));
        num1_day = Integer.parseInt(num1.substring(9, 10));
        num1_year = Integer.parseInt(num1.substring(11, 12));
        num1_hour = Integer.parseInt(num1.substring(13, 14));
        num1_second = Integer.parseInt(num1.substring(15, 16));
        //
        num2_month = Integer.parseInt(num2.substring(7, 8));
        num2_day = Integer.parseInt(num2.substring(9, 10));
        num2_year = Integer.parseInt(num2.substring(11, 12));
        num2_hour = Integer.parseInt(num2.substring(13, 14));
        num2_second = Integer.parseInt(num2.substring(15, 16));
        //
        //num1_img_code = Integer.parseInt(num1.substring(16, 23));
        //num2_img_code = Integer.parseInt(num2.substring(16, 23));


        if(num1_id == num2_id){
            if(num1_year == num2_year){
                if(num1_month == num2_month){
                    if(num1_day == num2_day){
                        if(num1_hour == num2_hour){
                            if(num1_second == num2_second){
                                //return false;
                                //
                            } else if (num1_second < num2_second){
                                return false;
                            } else {
                                return true;
                            }
                        } else if (num1_hour < num2_hour){
                            return false;
                        } else {
                            return true;
                        }
                    } else if (num1_day < num2_day){
                        return false;
                    } else {
                        return true;
                    }
                } else if (num1_month < num2_month) {
                    return false;
                }else {
                    return true;
                }
            } else if (this.yearAdj(num1_year) < this.yearAdj(num2_year)){
                return false;
            } else {
                return true;
            }
        } else if (num1_id < num2_id){
            return false;
        } else  {
            return true;
        }

        return false;
    }

    private int yearAdj(int n) {
        if (1 <= n && n <= 18) {
            return n + 100;
        }
        return n;
    }
}

//iiiiiiimmddyyhhssccccccc
/*
19 - 99 | 1 - 18
 */
//000000001011901010000000
//123456704219803170000000
//123456704229803170000000
//123456704229803190000000
//666666606060606060000000
//777777707071707070000000
//999999912311824600000000