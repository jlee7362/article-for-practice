package org.example;

public class Member {
    private int id;
    private String loginiD;
    private String loginPw;
    private String name;

    public Member(int id, String loginiD, String loginPw, String name) {
        this.id = id;
        this.loginiD = loginiD;
        this.loginPw = loginPw;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getLoginId() {
        return loginiD;
    }

    public String getLoginPw() {
        return loginPw;
    }

    public String getName() {
        return name;
    }

}
