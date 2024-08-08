package io.github.kawajava.MMOEstateManager.admin.goldHistory.controller;

import io.github.kawajava.MMOEstateManager.admin.borough.model.AdminBorough;
import io.github.kawajava.MMOEstateManager.admin.goldHistory.controller.dto.GoldHistoryFilteredDto;
import io.github.kawajava.MMOEstateManager.admin.goldHistory.model.AdminGoldHistory;
import io.github.kawajava.MMOEstateManager.admin.goldHistory.service.AdminGoldHistoryService;
import io.github.kawajava.MMOEstateManager.admin.historicalSheriffs.model.AdminHistoricalSheriffs;
import io.github.kawajava.MMOEstateManager.admin.historicalSheriffs.service.dto.HistoricalSheriffsFilteredDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/gold-histories")
public class AdminGoldHistoryController {

    private final AdminGoldHistoryService adminGoldHistoryService;

    @GetMapping
    public Page<AdminGoldHistory> getAdminGoldHistory(Pageable pageable) {
        return adminGoldHistoryService.getAdminGoldHistory(pageable);
    }
    @PostMapping("/filtered")
    public List<AdminGoldHistory> getFilteredData(
            @RequestBody GoldHistoryFilteredDto filteredDto) {
        return adminGoldHistoryService.getFilteredData(filteredDto);
    }
}
