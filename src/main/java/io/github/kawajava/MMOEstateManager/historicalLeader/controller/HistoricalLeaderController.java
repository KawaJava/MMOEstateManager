package io.github.kawajava.MMOEstateManager.historicalLeader.controller;

import io.github.kawajava.MMOEstateManager.historicalLeader.model.HistoricalLeader;
import io.github.kawajava.MMOEstateManager.historicalLeader.service.HistoricalLeaderService;
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
public class HistoricalLeaderController {

    private final HistoricalLeaderService historicalLeaderService;

    @GetMapping("/{slug}")
    public List<HistoricalLeader> getHistoricalLeadersBySlug(@PathVariable @Pattern(regexp = "[a-z0-9\\-]+")
                                                                @Length(max = 255) String slug) {
        return historicalLeaderService.getHHistoricalLeadersBySlug(slug);
    }
}
