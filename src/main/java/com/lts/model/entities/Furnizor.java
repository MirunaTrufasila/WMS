package com.lts.model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "furnizori")
public final class Furnizor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private long id;

    @Column(name = "nume_firma")
    private String numeFirma;

    @Column(name = "cod_ui")
    private String codUi;

    @Column(name = "cod_rj")
    private String codRj;

    @Column(name = "cod_rn")
    private String codRn;

    @Column(name = "cod_ra")
    private String codRa;

    @Column(name = "capital_social")
    private String capitalSocial;

    @Column(name = "punct_lucru")
    private String punctLucru;

    @Column(name = "telefon")
    private String telefon;

    @Column(name = "fax")
    private String fax;

    @Column(name = "email")
    private String email;

    @Column(name = "strada")
    private String strada;

    @Column(name = "web")
    private String web;

    @Column(name = "judet")
    private String judet;

    @Column(name = "localitate")
    private String localitate;

    @Column(name = "numar")
    private String numar;

    @Column(name = "sector")
    private String sector;

    @Column(name = "cod_postal")
    private String codPostal;

    @Column(name = "bloc")
    private String bloc;

    @Column(name = "scara")
    private String scara;

    @Column(name = "apartament")
    private String apartament;

    @Column(name = "nume_banca")
    private String numeBanca;

    @Column(name = "filiala_banca")
    private String filialaBanca;

    @Column(name = "cont_banca")
    private String contBanca;

    @Column(name = "nume_pers")
    private String numePers;

    @Column(name = "functie_pers")
    private String functiePers;

    @Column(name = "adresa_pers")
    private String adresaPers;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNumeFirma() {
        return numeFirma;
    }

    public void setNumeFirma(String numeFirma) {
        this.numeFirma = numeFirma;
    }

    public String getCodUi() {
        return codUi;
    }

    public void setCodUi(String codUi) {
        this.codUi = codUi;
    }

    public String getCodRj() {
        return codRj;
    }

    public void setCodRj(String codRj) {
        this.codRj = codRj;
    }

    public String getCodRn() {
        return codRn;
    }

    public void setCodRn(String codRn) {
        this.codRn = codRn;
    }

    public String getCodRa() {
        return codRa;
    }

    public void setCodRa(String codRa) {
        this.codRa = codRa;
    }

    public String getCapitalSocial() {
        return capitalSocial;
    }

    public void setCapitalSocial(String capitalSocial) {
        this.capitalSocial = capitalSocial;
    }

    public String getPunctLucru() {
        return punctLucru;
    }

    public void setPunctLucru(String punctLucru) {
        this.punctLucru = punctLucru;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWeb() {
        return web;
    }

    public void setWeb(String web) {
        this.web = web;
    }

    public String getJudet() {
        return judet;
    }

    public void setJudet(String judet) {
        this.judet = judet;
    }

    public String getLocalitate() {
        return localitate;
    }

    public void setLocalitate(String localitate) {
        this.localitate = localitate;
    }

    public String getNumar() {
        return numar;
    }

    public void setNumar(String numar) {
        this.numar = numar;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public String getCodPostal() {
        return codPostal;
    }

    public void setCodPostal(String codPostal) {
        this.codPostal = codPostal;
    }

    public String getBloc() {
        return bloc;
    }

    public void setBloc(String bloc) {
        this.bloc = bloc;
    }

    public String getScara() {
        return scara;
    }

    public void setScara(String scara) {
        this.scara = scara;
    }

    public String getApartament() {
        return apartament;
    }

    public void setApartament(String apartament) {
        this.apartament = apartament;
    }

    public String getNumeBanca() {
        return numeBanca;
    }

    public void setNumeBanca(String numeBanca) {
        this.numeBanca = numeBanca;
    }

    public String getFilialaBanca() {
        return filialaBanca;
    }

    public void setFilialaBanca(String filialaBanca) {
        this.filialaBanca = filialaBanca;
    }

    public String getContBanca() {
        return contBanca;
    }

    public void setContBanca(String contBanca) {
        this.contBanca = contBanca;
    }

    public String getNumePers() {
        return numePers;
    }

    public void setNumePers(String numePers) {
        this.numePers = numePers;
    }

    public String getFunctiePers() {
        return functiePers;
    }

    public void setFunctiePers(String functiePers) {
        this.functiePers = functiePers;
    }

    public String getAdresaPers() {
        return adresaPers;
    }

    public void setAdresaPers(String adresaPers) {
        this.adresaPers = adresaPers;
    }

    public String getStrada() {
        return strada;
    }

    public void setStrada(String strada) {
        this.strada = strada;
    }
}
