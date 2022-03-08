package io.orkes.conductor.client.http.auth;

public class TokenHolder {
    private static volatile String TOKEN;

    public static void setToken(String token) {
        TOKEN = token;
    }

    public static String getToken() {
        return TOKEN;
    }
}
