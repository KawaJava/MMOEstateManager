package io.github.kawajava.MMOEstateManager.admin.borough.controller;

import io.github.kawajava.MMOEstateManager.admin.borough.model.AdminBorough;
import io.github.kawajava.MMOEstateManager.admin.borough.service.AdminBoroughService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/boroughs")
public class AdminBoroughController {

    private final AdminBoroughService adminBoroughService;

    @GetMapping
    public Page<AdminBorough> getAdminBoroughs(Pageable pageable) {
        return adminBoroughService.getAdminBoroughs(pageable);
    }

    @GetMapping("/{id}")
    public AdminBorough getAdminBorough(@PathVariable Long id) {
        return adminBoroughService.getAdminBorough(id);
    }
}
