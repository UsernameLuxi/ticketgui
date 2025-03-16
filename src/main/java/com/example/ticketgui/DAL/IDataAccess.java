package com.example.ticketgui.DAL;

import java.util.List;

// denne kan fyldes ud ig
public interface IDataAccess<T> {
    List<T> getAll();
}
