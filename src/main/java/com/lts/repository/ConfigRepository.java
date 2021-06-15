package com.lts.repository;

import com.lts.model.entities.Config;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConfigRepository extends JpaRepository<Config, String> {

    Config getByCheie(String cheie);

}