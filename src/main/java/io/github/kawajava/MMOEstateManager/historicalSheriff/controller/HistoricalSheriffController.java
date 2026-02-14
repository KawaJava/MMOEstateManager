package io.github.kawajava.MMOEstateManager.historicalSheriff.controller;

import io.github.kawajava.MMOEstateManager.historicalSheriff.model.HistoricalSheriff;
import io.github.kawajava.MMOEstateManager.historicalSheriff.service.HistoricalSheriffService;
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
public class HistoricalSheriffController {

    private final HistoricalSheriffService historicalSheriffService;

    @GetMapping("/{slug}")
    public List<HistoricalSheriff> getHistoricalSheriffsBySlug(@PathVariable @Pattern(regexp = "[a-z0-9\\-]+")
                                    @Length(max = 255) String slug) {
        return historicalSheriffService.getHistoricalSheriffsBySlug(slug);
    }
}
