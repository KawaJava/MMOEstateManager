package io.github.kawajava.MMOEstateManager.admin.historicalLeaders.controller;

import io.github.kawajava.MMOEstateManager.admin.historicalLeaders.model.AdminHistoricalLeaders;
import io.github.kawajava.MMOEstateManager.admin.historicalLeaders.service.AdminHistoricalLeadersService;
import io.github.kawajava.MMOEstateManager.admin.historicalSheriffs.model.AdminHistoricalSheriffs;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/historical-leaders")
public class AdminHistoricalLeadersController {

    private final AdminHistoricalLeadersService adminHistoricalLeadersService;

    @GetMapping
    public Page<AdminHistoricalLeaders> getAdminHistoricalLeaders(Pageable pageable) {
        return adminHistoricalLeadersService.getAdminHistoricalLeaders(pageable);
    }
}
