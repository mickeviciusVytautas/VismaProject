package com.visma.fishing.security.key;

import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.spec.SecretKeySpec;
import javax.ejb.Stateless;
import java.security.Key;

@Stateless
public class SimpleKeyGenerator implements KeyGenerator {
    @Override
    public Key generateKey() {
        String keyString = "F448D61A83F3EECCD6B0E22F1BDD7979E39A176A2FDBBCC409565E3A913CE5B2";
        return new SecretKeySpec(keyString.getBytes(), SignatureAlgorithm.HS512.getJcaName());
    }
}
