package io.github.kawajava.MMOEstateManager.admin.charts.controller;

import io.github.kawajava.MMOEstateManager.admin.charts.model.ClanShareDto;
import io.github.kawajava.MMOEstateManager.admin.charts.model.GoldInBorough;
import io.github.kawajava.MMOEstateManager.admin.charts.model.PlayerBoroughCountDto;
import io.github.kawajava.MMOEstateManager.admin.charts.service.ChartsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/charts")
@RequiredArgsConstructor
public class ChartsController {

    private final ChartsService adminChartsService;

    @GetMapping("/gold-in-borough/{id}")
    public List<GoldInBorough> getGoldInBorough(@PathVariable Long id) {
        return adminChartsService.getGoldAmountGroupedByDate(id);
    }

    @GetMapping("/clans-in-country/{id}")
    public List<ClanShareDto> getClansInCountry(@PathVariable Long id) {
        return adminChartsService.calculateClanSharesInCountry(id);
    }

    @GetMapping("/player-boroughs-in-country/{id}")
    public List<PlayerBoroughCountDto> getPlayerBoroughCounts(@PathVariable Long id) {
        return adminChartsService.calculatePlayerBoroughCountsInCountry(id);
    }

}
