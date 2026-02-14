package io.github.kawajava.MMOEstateManager.admin.historicalSheriff.controller;

import io.github.kawajava.MMOEstateManager.admin.historicalSheriff.model.AdminHistoricalSheriff;
import io.github.kawajava.MMOEstateManager.admin.common.service.AdminHistoricalSheriffService;
import io.github.kawajava.MMOEstateManager.admin.historicalSheriff.service.dto.HistoricalSheriffFilteredDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/historical-sheriffs")
public class AdminHistoricalSheriffController {

    private final AdminHistoricalSheriffService adminHistoricalSheriffService;

    @GetMapping
    public Page<AdminHistoricalSheriff> getAdminHistoricalSheriffs(Pageable pageable) {
        return adminHistoricalSheriffService.getAdminHistoricalSheriffs(pageable);
    }

    @PostMapping("/filtered")
    public List<AdminHistoricalSheriff> getFilteredData(
            @RequestBody HistoricalSheriffFilteredDto filteredDto) {
        return adminHistoricalSheriffService.getFilteredData(filteredDto);
    }
}
