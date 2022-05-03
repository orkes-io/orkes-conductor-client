package io.orkes.conductor.client.http.auth;

public class TokenHolder {
    private volatile String token;

    public void setToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
