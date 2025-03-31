package com.example.ticketgui.DAL;

import com.example.ticketgui.BE.Coupon;
import com.example.ticketgui.DAL.Interfaces.ICouponAccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

public class CouponDataAccess implements ICouponAccess {
    @Override
    public List<Coupon> getAll() throws Exception {
        return List.of();
    }

    @Override
    public Coupon create(Coupon coupon) throws Exception {
        String sql = """
                INSERT INTO Cupons (Name, Price, Expirationdate, EventID) VALUES (?, ?, ?, ?);
                """;
        DBConnector db = new DBConnector();
        try(Connection conn = db.getConnection(); PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, coupon.getName());
            ps.setInt(2, coupon.getPrice());
            ps.setString(3, coupon.getExpiryDate());
            ps.setInt(4, coupon.getEventID());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next())
                return new Coupon(rs.getInt(1), coupon.getName(), coupon.getPrice(), coupon.getExpiryDate(), coupon.getEventID());
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return null;
    }

    @Override
    public Coupon getById(int id) throws Exception {
        return null;
    }

    @Override
    public void update(Coupon coupon) throws Exception {

    }

    @Override
    public void delete(Coupon coupon) throws Exception {

    }
}
