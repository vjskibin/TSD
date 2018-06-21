package lab1;

/*

CREATED BY VJSKIBIN
on 28.05.2018 | 3:37
 -> vk.com/vjskibin
 -> github.com/vjskibin
 -> vjskibin@gmail.com
 
 No more punk, alcohol and parties
 Only machine learning studing and evolution

*/

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Main {

    public static Calendar firstDayOfWeek(Calendar calendar){
        Calendar cal = (Calendar) calendar.clone();
        int day = cal.get(Calendar.DAY_OF_YEAR);
        while(cal.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY){
            cal.set(Calendar.DAY_OF_YEAR, --day);
        }
        return cal;
    }

    public static Calendar lastDayOfWeek(Calendar calendar){
        Calendar cal = (Calendar) calendar.clone();
        int day = cal.get(Calendar.DAY_OF_YEAR);
        while(cal.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY){
            cal.set(Calendar.DAY_OF_YEAR, ++day);
        }
        return cal;
    }

    public static Calendar nextDay(Calendar calendar)
    {
        Calendar cal = (Calendar) calendar.clone();
        int day = cal.get(Calendar.DAY_OF_YEAR);
        cal.set(Calendar.DAY_OF_YEAR, ++day);
        return cal;
    }

    public static Calendar nextMonth(Calendar calendar)
    {
        Calendar cal = (Calendar) calendar.clone();
        int day = cal.get(Calendar.DAY_OF_YEAR);
        int month = cal.get(Calendar.MONTH);

        cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) + 1);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        return cal;
    }

    public static void main(String[] args) throws ParseException {
        String tradeDate = "2018-12-30T15:49:18Z";



        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(Calendar.MONDAY);


        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX");
// parse string and set the resulting date to Calendar
        try {
            cal.setTime(df.parse(tradeDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Date dt = new Date();
        dt = firstDayOfWeek(cal).getTime();
        String asISO = df.format(dt);
        System.out.println("First day of week: " + asISO);

        dt = lastDayOfWeek(cal).getTime();
        asISO = df.format(dt);
        System.out.println("Last day of week: " + asISO);

        dt = nextDay(cal).getTime();
        asISO = df.format(dt);
        System.out.println("Next day: " + asISO);

        dt = nextMonth(cal).getTime();
        asISO = df.format(dt);
        System.out.println("Next month: " + asISO);
    }
}
