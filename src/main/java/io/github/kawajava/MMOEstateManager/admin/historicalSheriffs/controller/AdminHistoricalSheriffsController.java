package io.github.kawajava.MMOEstateManager.admin.historicalSheriffs.controller;

import io.github.kawajava.MMOEstateManager.admin.historicalSheriffs.model.AdminHistoricalSheriffs;
import io.github.kawajava.MMOEstateManager.admin.historicalSheriffs.service.AdminHistoricalSheriffsService;
import io.github.kawajava.MMOEstateManager.admin.player.controller.dto.HistoricalSheriffsFilteredDto;
import io.github.kawajava.MMOEstateManager.admin.player.model.AdminPlayer;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/historical-sheriffs")
public class AdminHistoricalSheriffsController {

    private final AdminHistoricalSheriffsService adminHistoricalSheriffsService;

    @GetMapping
    public Page<AdminHistoricalSheriffs> getAdminHistoricalSheriffs(Pageable pageable) {
        return adminHistoricalSheriffsService.getAdminHistoricalSheriffs(pageable);
    }

    @PostMapping("/filtered")
    public List<AdminHistoricalSheriffs> getFilteredData(
            @RequestBody HistoricalSheriffsFilteredDto filteredDto) {
        return adminHistoricalSheriffsService.getFilteredData(filteredDto);
    }


}
