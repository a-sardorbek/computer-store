package com.system.ws.dto;

public class RegisterSystemUser {
    private String fio;
    private String username;
    private String password1;
    private String password2;

    public RegisterSystemUser() {
    }

    public RegisterSystemUser(String fio, String username, String password1, String password2) {
        this.fio = fio;
        this.username = username;
        this.password1 = password1;
        this.password2 = password2;
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword1() {
        return password1;
    }

    public void setPassword1(String password1) {
        this.password1 = password1;
    }

    public String getPassword2() {
        return password2;
    }

    public void setPassword2(String password2) {
        this.password2 = password2;
    }
}
