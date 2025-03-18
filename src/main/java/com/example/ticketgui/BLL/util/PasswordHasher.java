package com.example.ticketgui.BLL.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordHasher implements IHashing{
    @Override
    public String hashString(String input) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA3-512");
        byte[] hash = md.digest(input.getBytes(StandardCharsets.UTF_8));
        StringBuilder hexString = new StringBuilder();
        for (byte b : hash) {
            //  This masks the byte b with 0xff (which is 11111111 in binary).
            //  This operation ensures that the byte is treated as an unsigned value by discarding
            //  any negative sign (since in Java, a byte is signed and can range from -128 to 127).
            //  - allegedly
            String hex = Integer.toHexString(0xff & b);
            hexString.append(hex.length() == 1 ? "0" + hex : hex); // hvis det er 9 som kommer ud -> så bliver det 09 ;)
        }
        return hexString.toString();
    }

    @Override
    /**
     * Compares input(non hash) to a hashed value and returns if the input string has the same hashed value as the hash string
     */
    public boolean compare(String input, String hash) throws Exception {
        String inputHash = hashString(input);
        return inputHash.equals(hash);
    }


}
