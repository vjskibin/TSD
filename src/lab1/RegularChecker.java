package lab1;

/*

CREATED BY VJSKIBIN
on 28.05.2018 | 3:39
 -> vk.com/vjskibin
 -> github.com/vjskibin
 -> vjskibin@gmail.com
 
 No more punk, alcohol and parties
 Only machine learning studing and evolution

*/

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegularChecker {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите строку");
        String string = scanner.nextLine();
        System.out.println(check(string));
    }

    private static String check(String string)
    {
        String temp = string;
        Pattern date = Pattern.compile("[\\d]{2}.[\\d]{2}.[\\d]{4}");
        Pattern dateTime = Pattern.compile("[\\d]{2}.[\\d]{2}.[\\d]{4}T[\\d]{2}:[\\d]{2}");
        Pattern email = Pattern.compile("^([A-Za-z0-9_-]+\\.)*[A-Za-z0-9_-]+@[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*\\.[A-Za-z]{2,6}$");

        Matcher dateM = date.matcher(string);
        Matcher dateTimeM = dateTime.matcher(string);
        Matcher emailM = email.matcher(string);
        if (dateTimeM.find())
        {
            temp = dateTimeM.replaceFirst("");
            if (temp.length() != 0)
                return "Nothing, just a string";
            else
                return "dateAndTime";
        }
        else if (dateM.find())
        {
            temp = dateM.replaceFirst("");
            if (temp.length() != 0)
                return "Nothing, just a string";
            else
                return "date";
        }
        else if (emailM.find())
        {
            temp = emailM.replaceFirst("");
            if (temp.length() != 0)
                return "Nothing, just a string";
            else
                return "email";
        }
        else
            return "Nothing, just a string";
    }
}
