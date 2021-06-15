package com.lts.model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "company_details")
public final class CompanyDetails {

    @Id
    @Column(name = "denumire")
    private String denumire;

    @Column(name = "valoare")
    private String valoare;

    public CompanyDetails() {
        super();
    }

    public CompanyDetails(String denumire, String valoare) {
        this.denumire = denumire;
        this.valoare = valoare;
    }

    public String getDenumire() {
        return denumire;
    }

    public void setDenumire(String denumire) {
        this.denumire = denumire;
    }

    public String getValoare() {
        return valoare;
    }

    public void setValoare(String valoare) {
        this.valoare = valoare;
    }
}
