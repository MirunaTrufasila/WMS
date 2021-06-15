package com.lts.controller;

import com.lts.model.entities.Furnizor;
import com.lts.service.FurnizoriService;
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

@Api(tags = "Furnizori")
@RestController
@RequestMapping(path = "/api/v1/furnizori")
public class FurnizoriController {

    private final FurnizoriService furnizoriService;

    public FurnizoriController(FurnizoriService furnizoriService) {
        this.furnizoriService = furnizoriService;
    }

    @GetMapping("/{id}")
    public Furnizor getFurnizor(@PathVariable Long id) {
        return furnizoriService.getFurnizor(id);
    }

    @GetMapping
    public @ResponseBody
    Page<Furnizor> get(@RequestParam int pageNumber,
                       @RequestParam int pageSize) {
        return furnizoriService.get(pageNumber, pageSize);
    }

    @GetMapping("/filter")
    public @ResponseBody
    Page<Furnizor> filter(@RequestParam String filter,
                          @RequestParam Object value,
                          @RequestParam int pageNumber,
                          @RequestParam int pageSize) {
        return furnizoriService.filter(filter, value, pageNumber, pageSize);
    }

    @PostMapping
    public Furnizor create(@RequestBody Furnizor furnizor) {
        return furnizoriService.create(furnizor);
    }

    @PutMapping
    public Furnizor update(@RequestBody Furnizor furnizor) {
        return furnizoriService.update(furnizor);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        furnizoriService.delete(id);
    }

}