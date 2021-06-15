package com.lts.model.entities;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.lts.util.json.LocalDateDeserializer;
import com.lts.util.json.LocalDateSerializer;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "facturi")
public final class Factura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private long id;

    @Column(name = "nr_factura")
    private String nrFactura;

    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @Column(name = "scadenta")
    private LocalDate scadenta;

    @Column(name = "achitata")
    private boolean achitata;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNrFactura() {
        return nrFactura;
    }

    public void setNrFactura(String nrFactura) {
        this.nrFactura = nrFactura;
    }

    public LocalDate getScadenta() {
        return scadenta;
    }

    public void setScadenta(LocalDate scadenta) {
        this.scadenta = scadenta;
    }

    public boolean isAchitata() {
        return achitata;
    }

    public void setAchitata(boolean achitata) {
        this.achitata = achitata;
    }
}
