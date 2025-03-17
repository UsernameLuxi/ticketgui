package com.example.ticketgui.DAL;

import java.util.List;

// denne kan fyldes ud ig
// her skal kun generelle ting, som gælder for alle, ind
public interface IDataAccess<T> {
    List<T> getAll();

    T create(T t);

    T getById(int id);

    void update(T t);

    void delete(T t);
}
