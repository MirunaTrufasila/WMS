package com.lts.model.entities;

import java.io.Serializable;

public class IdUserPrivilege implements Serializable {

    private long idUser;
    private long idPrivilege;

    public IdUserPrivilege() {

    }

    public IdUserPrivilege(long idUser, long idPrivilege) {
        this.idUser = idUser;
        this.idPrivilege = idPrivilege;
    }

    public long getIdUser() {
        return idUser;
    }

    public long getIdPrivilege() {
        return idPrivilege;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

}
