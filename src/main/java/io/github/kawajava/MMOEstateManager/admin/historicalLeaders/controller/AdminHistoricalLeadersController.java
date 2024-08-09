package io.github.kawajava.MMOEstateManager.admin.historicalLeaders.controller;

import io.github.kawajava.MMOEstateManager.admin.historicalLeaders.model.AdminHistoricalLeaders;
import io.github.kawajava.MMOEstateManager.admin.common.service.AdminHistoricalLeadersService;
import io.github.kawajava.MMOEstateManager.admin.historicalLeaders.service.dto.HistoricalLeadersFilteredDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/historical-leaders")
public class AdminHistoricalLeadersController {

    private final AdminHistoricalLeadersService adminHistoricalLeadersService;

    @GetMapping
    public Page<AdminHistoricalLeaders> getAdminHistoricalLeaders(Pageable pageable) {
        return adminHistoricalLeadersService.getAdminHistoricalLeaders(pageable);
    }
    @PostMapping("/filtered")
    public List<AdminHistoricalLeaders> getFilteredData(@RequestBody HistoricalLeadersFilteredDto filteredDto) {
        return adminHistoricalLeadersService.getFilteredData(filteredDto);
    }
}
