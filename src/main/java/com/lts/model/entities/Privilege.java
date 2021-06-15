package com.lts.model.entities;

import org.hibernate.annotations.Where;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tms_privilege")
@Where(clause = "valid='1'")
public class Privilege {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private long id;

    @Column(name = "id_parinte")
    private long idParinte;

    private String descriere;

    private boolean valid;

    public long getId() {
        return id;
    }

    public long getIdParinte() {
        return idParinte;
    }

    public void setIdParinte(long idParinte) {
        this.idParinte = idParinte;
    }

    public String getDescriere() {
        return descriere;
    }

    public void setDescriere(String descriere) {
        this.descriere = descriere;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    @Override
    public String toString() {
        return "Privilege{" +
                "id=" + id +
                ", idParinte=" + idParinte +
                ", descriere='" + descriere + '\'' +
                ", valid=" + valid +
                '}';
    }
}
