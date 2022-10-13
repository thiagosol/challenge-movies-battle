package com.thiagosol.moviesbattle.core.domain;

public class Player {

    private Long id;
    private String login;
    private String password;

    public Player(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public Player(Long id, String login, String password) {
        this.id = id;
        this.login = login;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }
}
