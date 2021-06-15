package com.lts.controller;

import com.lts.model.entities.Angajat;
import com.lts.service.AngajatiService;
import io.swagger.annotations.Api;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "Angajati")
@RestController
@RequestMapping(path = "/api/v1/employees")
public class AngajatiController {

    private final AngajatiService angajatiService;

    public AngajatiController(AngajatiService angajatiService) {
        this.angajatiService = angajatiService;
    }

    @GetMapping("/{id}")
    public Angajat getAngajat(@PathVariable Long id) {
        return angajatiService.getAngajat(id);
    }

    @GetMapping
    public @ResponseBody
    Page<Angajat> get(@RequestParam int pageNumber,
                      @RequestParam int pageSize) {
        return angajatiService.get(pageNumber, pageSize);
    }

    @GetMapping("/filter")
    public @ResponseBody
    Page<Angajat> filter(@RequestParam String filter,
                         @RequestParam String filter1,
                         @RequestParam Object value,
                         @RequestParam int pageNumber,
                         @RequestParam int pageSize) {
        return angajatiService.filter(filter, filter1,value, pageNumber, pageSize);
    }

    @PostMapping
    public Angajat create(@RequestBody Angajat angajat) {
        return angajatiService.create(angajat);
    }

    @PutMapping
    public Angajat update(@RequestBody Angajat angajat) {
        return angajatiService.update(angajat);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        angajatiService.delete(id);
    }

}