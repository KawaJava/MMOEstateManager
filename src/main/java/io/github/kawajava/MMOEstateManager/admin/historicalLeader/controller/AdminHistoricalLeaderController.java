package io.github.kawajava.MMOEstateManager.admin.historicalLeader.controller;

import io.github.kawajava.MMOEstateManager.admin.historicalLeader.model.AdminHistoricalLeader;
import io.github.kawajava.MMOEstateManager.admin.common.service.AdminHistoricalLeaderService;
import io.github.kawajava.MMOEstateManager.admin.historicalLeader.service.dto.HistoricalLeaderFilteredDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/historical-leaders")
public class AdminHistoricalLeaderController {

    private final AdminHistoricalLeaderService adminHistoricalLeaderService;

    @GetMapping
    public Page<AdminHistoricalLeader> getAdminHistoricalLeaders(Pageable pageable) {
        return adminHistoricalLeaderService.getAdminHistoricalLeaders(pageable);
    }
    @PostMapping("/filtered")
    public List<AdminHistoricalLeader> getFilteredData(@RequestBody HistoricalLeaderFilteredDto filteredDto) {
        return adminHistoricalLeaderService.getFilteredData(filteredDto);
    }
}
