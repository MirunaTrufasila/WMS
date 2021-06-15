package com.lts.model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@IdClass(IdUserPrivilege.class)
@Table(name = "user_privilege")
public class UserPrivilege implements Serializable {

    @Id
    @Column(name = "id_user")
    private long idUser;

    @Id
    @Column(name = "id_privilege")
    private long idPrivilege;

    public UserPrivilege() {
        // required
    }

    public UserPrivilege(long idUser, long idPrivilege) {
        this.idUser = idUser;
        this.idPrivilege = idPrivilege;
    }

    public long getIdUser() {
        return idUser;
    }

    public void setIdUser(long idUser) {
        this.idUser = idUser;
    }

    public long getIdPrivilege() {
        return idPrivilege;
    }

    public void setIdPrivilege(long idPrivilege) {
        this.idPrivilege = idPrivilege;
    }

    @Override
    public String toString() {
        return "UserPrivilege{" +
                "idUser=" + idUser +
                ", idPrivilege=" + idPrivilege +
                '}';
    }
}