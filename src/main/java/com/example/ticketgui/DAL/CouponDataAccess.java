package com.example.ticketgui.DAL;

import com.example.ticketgui.BE.Coupon;
import com.example.ticketgui.BE.Event;
import com.example.ticketgui.DAL.Interfaces.ICouponAccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CouponDataAccess implements ICouponAccess {
    @Override
    public List<Coupon> getAll() throws Exception {
        String sql = """
                SELECT Cupons.ID, Cupons.Name, Cupons.Price, Cupons.Expirationdate, Events.ID, Events.Name
                FROM Cupons
                INNER JOIN Events ON Events.ID = Cupons.ID;
                """; // måske lave om
        DBConnector db = new DBConnector();
        try(Connection conn = db.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            List<Coupon> coupons = new ArrayList<Coupon>();
            while(rs.next()) {
                Coupon c = new Coupon(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getString(4), new Event(rs.getInt(5), rs.getString(6)));
                coupons.add(c);
            }

            return coupons;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
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
            if (coupon.getEvent() == null)
               ps.setNull(4, java.sql.Types.INTEGER);
            else
                ps.setInt(4, coupon.getEvent().getId());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next())
                return new Coupon(rs.getInt(1), coupon.getName(), coupon.getPrice(), coupon.getExpiryDate(), coupon.getEvent());
        } catch (Exception e) {
            e.printStackTrace();
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
        String sql = """
                UPDATE Cupons SET Name = ?, Price = ?, Expirationdate = ?, EventID = ? WHERE ID = ?;
                """;
        DBConnector db = new DBConnector();
        try(Connection conn = db.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, coupon.getName());
            stmt.setInt(2, coupon.getPrice());
            stmt.setString(3, coupon.getExpiryDate());
            if (coupon.getEvent() == null)
                stmt.setNull(4, java.sql.Types.INTEGER);
            else
                stmt.setInt(4, coupon.getEvent().getId());

            stmt.setInt(5, coupon.getId());
            stmt.executeUpdate();
        }
        catch (Exception e) {
            throw new Exception(e.getMessage());
        }

    }

    @Override
    public void delete(Coupon coupon) throws Exception {
        String sql = """
                DELETE FROM Cupons WHERE ID = ?;
                """;
        DBConnector db = new DBConnector();
        try(Connection conn = db.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, coupon.getId());
            pstmt.executeUpdate();
        }
        catch(Exception e) {
            throw new Exception(e.getMessage());
        }

    }
}
