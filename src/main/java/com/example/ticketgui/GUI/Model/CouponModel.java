package com.example.ticketgui.GUI.Model;

import com.example.ticketgui.BE.Coupon;
import com.example.ticketgui.BLL.CouponLogic;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class CouponModel {
    private ObservableList<Coupon> coupons;
    private CouponLogic logic;
    public CouponModel() {
        logic = new CouponLogic();
    }

    public ObservableList<Coupon> getCoupons() throws Exception {
        if (coupons == null || coupons.isEmpty()) {
            coupons = FXCollections.observableArrayList();
            coupons.addAll(logic.getAllCoupons());
        }
        return coupons;
    }

    public void createCoupon(Coupon coupon) throws Exception {
        Coupon c = logic.createCoupon(coupon);
        coupons.add(c);
    }

    public void loadCoupons() throws Exception {
        getCoupons();
    }

    public void delete(Coupon coupon) throws Exception {
        logic.delete(coupon);
        for (Coupon c : coupons) { // idk om man kan bruge .remove()
            if (c.getId() == coupon.getId()) {
                coupons.remove(c);
                break;
            }
        }
    }
}
