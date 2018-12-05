package com.hust.itss.utils.comparer;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DateComparer {
    private static final Pattern PATTERN = Pattern.compile("\\d+");

    public static boolean afterNow(Date date, String timeString){

        Matcher matcher = PATTERN.matcher(timeString);
        List<Integer> times = new ArrayList<>();
        while (matcher.find()){
            times.add(Integer.parseInt(matcher.group()));
        }
        try{
            date.setHours(times.get(0));
            date.setMinutes(times.get(1));
            System.out.println(date);
        }catch (Exception e){
            return false;
        }

        return date.after(new Date());
    }
}
