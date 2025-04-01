package com.example.ticketgui.BLL;

import com.example.ticketgui.BE.Coupon;
import com.example.ticketgui.DAL.CouponDataAccess;
import com.example.ticketgui.DAL.Interfaces.ICouponAccess;

import java.util.List;

public class CouponLogic {
    ICouponAccess couponAccess;

    public CouponLogic() {
        this.couponAccess = new CouponDataAccess();
    }

    public Coupon createCoupon(Coupon coupon) throws Exception {
        return couponAccess.create(coupon);
    }

    public List<Coupon> getAllCoupons() throws Exception {
        return couponAccess.getAll();
    }

    public void delete(Coupon coupon) throws Exception {
        couponAccess.delete(coupon);
    }

    public void update(Coupon coupon) throws Exception {
        couponAccess.update(coupon);
    }
}
