package com.example.feeding_machine_2;

import java.util.HashMap;
import java.util.Map;

public class Time {
    public int hour;
    public int minute;

    public int Run;

    public String foodsize;

    public Time(int hour, int minute){
        this.hour = hour;
        this.minute = minute;
    }

    public Time(int Run){
        this.Run = Run;
    }

    public Time(String foodsize){
        this.foodsize = foodsize;
    }

    public Map<String, Object> toMap(){
        HashMap<String, Object> result = new HashMap<>();

        result.put("hour", hour);
        result.put("minute", minute);
        return result;
    }

    public Map<String, Object> toMap_2(){
        HashMap<String, Object> result = new HashMap<>();

        result.put("Run",Run);
        return result;
    }

    public Map<String, Object> toMap_3(){
        HashMap<String, Object> result = new HashMap<>();

        result.put("FoodSize", foodsize);
        return result;
    }
}
