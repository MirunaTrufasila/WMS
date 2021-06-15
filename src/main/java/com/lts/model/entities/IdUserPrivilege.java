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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        IdUserPrivilege that = (IdUserPrivilege) o;

        if (idUser != that.idUser) return false;
        return idPrivilege == that.idPrivilege;
    }

    @Override
    public int hashCode() {
        int result = (int) (idUser ^ (idUser >>> 32));
        result = 31 * result + (int) (idPrivilege ^ (idPrivilege >>> 32));
        return result;
    }
}
