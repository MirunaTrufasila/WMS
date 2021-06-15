package com.lts.model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "user_privilege_detail")
public class UserPrivilegeDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private long id;

    @NotNull
    @Column(name = "id_category")
    private long idCategory;

    @ManyToOne
    @JoinColumn(name = "id_category", insertable = false, updatable = false)
    private UserPrivilegeDetailCategory category;

    @NotNull
    @Column(name = "name")
    private String name;

    public long getId() {
        return id;
    }

    public long getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(long idCategory) {
        this.idCategory = idCategory;
    }

    public UserPrivilegeDetailCategory getCategory() {
        return category;
    }

    public void setCategory(UserPrivilegeDetailCategory category) {
        this.category = category;
        if (category != null) {
            this.idCategory = category.getId();
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "UserPrivilegeDetail{" +
                "id=" + id +
                ", idCategory=" + idCategory +
                ", category=" + category +
                ", name='" + name + '\'' +
                '}';
    }
}
