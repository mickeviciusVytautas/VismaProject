package com.visma.fishing.security.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;

public class PasswordEncoder {

    public static String encode(String plainPassword) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(plainPassword.getBytes(StandardCharsets.UTF_8));
            byte[] passwordDigest = md.digest();
            return new String(Base64.getEncoder().encode(passwordDigest));
        } catch (Exception e) {
            throw new RuntimeException("Exception encoding password", e);
        }
    }

    private PasswordEncoder() {}

}
