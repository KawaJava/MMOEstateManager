package io.github.kawajava.MMOEstateManager.admin.charts.controller;

import io.github.kawajava.MMOEstateManager.admin.charts.model.GoldInBorough;
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
}
