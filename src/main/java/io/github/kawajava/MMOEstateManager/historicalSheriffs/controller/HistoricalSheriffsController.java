package io.github.kawajava.MMOEstateManager.historicalSheriffs.controller;

import io.github.kawajava.MMOEstateManager.historicalSheriffs.model.HistoricalSheriffs;
import io.github.kawajava.MMOEstateManager.historicalSheriffs.service.HistoricalSheriffsService;
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
@RequestMapping("/historical-sheriffs")
@Validated
public class HistoricalSheriffsController {

    private final HistoricalSheriffsService historicalSheriffsService;

    @GetMapping("/{slug}")
    public List<HistoricalSheriffs> getHistoricalSheriffsBySlug(@PathVariable @Pattern(regexp = "[a-z0-9\\-]+")
                                    @Length(max = 255) String slug) {
        return historicalSheriffsService.getHistoricalSheriffsBySlug(slug);
    }
}
