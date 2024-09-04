package io.github.kawajava.MMOEstateManager.historicalLeaders.controller;

import io.github.kawajava.MMOEstateManager.historicalLeaders.model.HistoricalLeaders;
import io.github.kawajava.MMOEstateManager.historicalLeaders.service.HistoricalLeadersService;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/historical-leaders")
@Validated
public class HistoricalLeadersController {

    private final HistoricalLeadersService historicalLeadersService;

    @GetMapping("/{slug}")
    public List<HistoricalLeaders> getHistoricalLeadersBySlug(@PathVariable @Pattern(regexp = "[a-z0-9\\-]+")
                                                                @Length(max = 255) String slug) {
        return historicalLeadersService.getHHistoricalLeadersBySlug(slug);
    }
}
