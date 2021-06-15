package com.lts.model.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

@Entity
@Table(name = "config",
        indexes = {
                @Index(name = "cheie", columnList = "cheie")})
public class Config {

    public static final String DEBUG_EMAILS = "debug.emails";
    public static final String TEST_NOTIFICATION_EMAIL = "test.notification.email";
    public static final String DEV_TEAM_EMAILS = "dev.team.emails";

    @Id
    private String cheie;

    private String valoare;

    public Config() {
        //required
    }

    public Config(String cheie, String valoare) {
        this.cheie = cheie;
        this.valoare = valoare;
    }

    public String getCheie() {
        return cheie;
    }

    public void setCheie(String cheie) {
        this.cheie = cheie;
    }

    public String getValoare() {
        return valoare;
    }

    public void setValoare(String valoare) {
        this.valoare = valoare;
    }
}
