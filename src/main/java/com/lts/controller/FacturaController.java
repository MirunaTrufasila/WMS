package com.lts.controller;

import com.lts.model.entities.Factura;
import com.lts.service.FacturaService;
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

@Api(tags = "Facturi")
@RestController
@RequestMapping(path = "/api/v1/facturi")
public class FacturaController {

    private final FacturaService facturaService;

    public FacturaController(FacturaService facturaService) {
        this.facturaService = facturaService;
    }

    @GetMapping("/{id}")
    public Factura getFactura(@PathVariable Long id) {
        return facturaService.getFactura(id);
    }

    @GetMapping
    public @ResponseBody
    Page<Factura> get(@RequestParam int pageNumber,
                      @RequestParam int pageSize) {
        return facturaService.get(pageNumber, pageSize);
    }

    @GetMapping("/filter")
    public @ResponseBody
    Page<Factura> filter(@RequestParam String filter,
                         @RequestParam Object value,
                         @RequestParam int pageNumber,
                         @RequestParam int pageSize) {
        return facturaService.filter(filter, value, pageNumber, pageSize);
    }

    @PostMapping
    public Factura create(@RequestBody Factura factura) {
        return facturaService.create(factura);
    }

    @PutMapping
    public Factura update(@RequestBody Factura factura) {
        return facturaService.update(factura);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        facturaService.delete(id);
    }
}