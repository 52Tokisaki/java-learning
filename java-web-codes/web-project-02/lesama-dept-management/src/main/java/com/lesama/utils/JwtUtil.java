package com.lesama.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Map;
/**
 * JWT 工具类 - 用于生成和解析 JWT Token
 * 基于 jjwt 0.12.6 API
 */
public class JwtUtil {

    /** 原始密钥字符串 */
    private static final String DEFAULT_SECRET_STRING = "lesama";

    /** 默认 Token 有效期：1 小时（单位：毫秒） */
    private static final long DEFAULT_EXPIRATION = 3600 * 1000;

    /**
     * 将原始字符串通过 SHA-256 哈希为 256 bits 的安全密钥
     */
    private static SecretKey getSigningKey(String secretString) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] keyBytes = digest.digest(secretString.getBytes(StandardCharsets.UTF_8));
            return Keys.hmacShaKeyFor(keyBytes);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 算法不可用", e);
        }
    }

    // ==================== 生成 JWT ====================

    public static String generateToken(Map<String, Object> claims) {
        return generateToken(claims, DEFAULT_SECRET_STRING, DEFAULT_EXPIRATION);
    }

    public static String generateToken(Map<String, Object> claims, long expiration) {
        return generateToken(claims, DEFAULT_SECRET_STRING, expiration);
    }

    public static String generateToken(Map<String, Object> claims, String secretString, long expiration) {
        SecretKey key = getSigningKey(secretString);
        return Jwts.builder()
                .claims(claims)
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(key)
                .compact();
    }

    // ==================== 解析 JWT ====================

    public static Claims parseToken(String token) {
        return parseToken(token, DEFAULT_SECRET_STRING);
    }

    public static Claims parseToken(String token, String secretString) {
        SecretKey key = getSigningKey(secretString);
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    // ==================== 验证 JWT ====================

    public static boolean validateToken(String token) {
        return validateToken(token, DEFAULT_SECRET_STRING);
    }

    public static boolean validateToken(String token, String secretString) {
        try {
            parseToken(token, secretString);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static Object getClaim(String token, String claimName) {
        return parseToken(token).get(claimName);
    }

    public static Date getExpiration(String token) {
        return parseToken(token).getExpiration();
    }

    public static boolean isExpired(String token) {
        return parseToken(token).getExpiration().before(new Date());
    }
}