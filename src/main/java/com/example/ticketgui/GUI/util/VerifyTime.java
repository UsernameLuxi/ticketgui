package com.example.ticketgui.GUI.util;

public class VerifyTime {
    public static boolean verifyTime(String time){
        String[] timeArr = time.split(":");
        if (timeArr.length != 2){
            return false;
        }
        try{
            int hours = Integer.parseInt(timeArr[0]);
            int minutes = Integer.parseInt(timeArr[1]);
            if (hours >= 24 || hours < 0){
                return false;
            }
            if (minutes >= 60 || minutes < 0){
                return false;
            }
        }
        catch (Exception e){
            return false;
        }
        return true;
    }
}
