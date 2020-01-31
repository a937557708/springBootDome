package com.tjr.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JWTUtil {

    private static final String TOKEN_SECRET = "eyJhbGciO";

    private static final String CLAIM_NAME = "sign";

    private static final long EXPIRE_TIME = 2000;//过期时间

    public static String sign(String sign) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
            Map<String, Object> header = new HashMap<String, Object>();
            header.put("typ", "JWT");
            header.put("alg", "HS256");
            Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
            String token = JWT.create().withHeader(header).withClaim(CLAIM_NAME, sign).withExpiresAt(date)
                    .sign(algorithm);
            return token;
        } catch (Exception e) {
            return null;
        }
    }

    public static String verify(String token) {

        try {
            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT jwt = verifier.verify(token);
            return jwt.getClaim(CLAIM_NAME).asString();
        } catch (Exception e) {
            return null;
        }
    }

    public static void main(String[] args) {
        //eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzaWduIjoidHd4Njg0ODgyIiwiZXhwIjoxNTc5NTc0MjM5fQ.5Vs_eemA4O2Atn2eff2jhvbYgEyaehV14Nx0COstxQo
        String token= JWTUtil.sign("twx684882");
        System.out.println(token);
        token= JWTUtil.verify(token);
        System.out.println(token);
    }

}