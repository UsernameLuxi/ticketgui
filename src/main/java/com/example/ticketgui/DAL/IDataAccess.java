package com.example.ticketgui.DAL;

import java.util.List;

// denne kan fyldes ud ig
// her skal kun generelle ting, som gælder for alle, ind
public interface IDataAccess<T> {
    List<T> getAll() throws Exception;

    T create(T t) throws Exception;

    T getById(int id) throws Exception;

    void update(T t) throws Exception;

    void delete(T t) throws Exception;
}
