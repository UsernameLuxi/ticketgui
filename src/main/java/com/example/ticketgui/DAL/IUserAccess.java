package com.example.ticketgui.DAL;
import com.example.ticketgui.BE.User;

// her skal kun specifikke ting til burgere ind
public interface IUserAccess extends IDataAccess<User>{
    User getUserOfUsername(String username) throws Exception;
}
