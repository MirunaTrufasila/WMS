package com.lts.model.entities;

import java.io.Serializable;

public class UserPrivilegeId implements Serializable {

    private long idUser;
    private long idPrivilegeDetail;
    public UserPrivilegeId() {

    }

    public UserPrivilegeId(long idUser, long idPrivilegeDetail) {
        this.idUser = idUser;
        this.idPrivilegeDetail = idPrivilegeDetail;
    }

    public long getIdUser() {
        return idUser;
    }

    public long getIdPrivilegeDetail() {
        return idPrivilegeDetail;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return "UserPrivilegeId{" +
                "idUser=" + idUser +
                ", idPrivilegeDetail=" + idPrivilegeDetail +
                '}';
    }
}
