package com.parsegram.boot.utils;

import lombok.SneakyThrows;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Random;
import java.util.function.BiFunction;

public final class PasswordUtils {

    private PasswordUtils() {
    }

    public static String generatePassword() {
        BiFunction<Character, Character, String> seqGenerator = (from, to) -> {

            char[] result = new char[to - from + 1];
            for (char c = from; c <= to; c++) {
                result[c - from] = c;
            }

            return new String(result);
        };

        String pool = String.join("", seqGenerator.apply('a', 'z'), seqGenerator.apply('A', 'Z'), seqGenerator.apply('0', '9'));
        Random rand = new Random(System.currentTimeMillis());

        char[] result = new char[25];
        for (int i = 0; i <= 24; i ++) {
            result[i] = pool.charAt(rand.nextInt(pool.length()));
        }

        return new String(result);
    }

    public static String generateSalt() {
        final Random r = new SecureRandom();
        byte[] salt = new byte[32];
        r.nextBytes(salt);

        return Base64.getEncoder().encodeToString(salt);
    }

    @SneakyThrows
    public static String generateSecret() {
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(256);
        SecretKey secretKey = keyGen.generateKey();
        byte[] encoded = secretKey.getEncoded();

        return Base64.getEncoder().encodeToString(encoded);
    }

    @SneakyThrows
    public static String hashPassword(String password, String salt) {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] saltBytes = Base64.getDecoder().decode(salt);
        byte[] passwordBytes = password.getBytes(StandardCharsets.UTF_8);
        byte[] allBytes = new byte[saltBytes.length + passwordBytes.length];
        System.arraycopy(passwordBytes, 0, allBytes, 0, passwordBytes.length);
        System.arraycopy(saltBytes, 0, allBytes, passwordBytes.length, saltBytes.length);
        byte[] hash = digest.digest(allBytes);

        return Base64.getEncoder().encodeToString(hash);
    }
}
