package com.dkds.payment_processor.user_service;

import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Base64;

public class JwtKeyGenerator {
    public static void main(String[] args) {
        Key key = Keys.secretKeyFor(io.jsonwebtoken.SignatureAlgorithm.HS256); // Generates a 256-bit key
        String base64Key = Base64.getEncoder().encodeToString(key.getEncoded());
        System.out.println("Generated Key (Base64): " + base64Key);
    }
}
