package com.example.ticketgui.DAL.Interfaces;

import com.example.ticketgui.BE.Coupon;

import java.io.IOException;
import java.util.List;

public interface ICouponAccess extends IDataAccess<Coupon>{
    List<Coupon> getCouponsByEventID(int id) throws IOException;
}
