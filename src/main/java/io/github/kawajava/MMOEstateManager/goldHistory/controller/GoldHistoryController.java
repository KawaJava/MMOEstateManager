package io.github.kawajava.MMOEstateManager.goldHistory.controller;

import io.github.kawajava.MMOEstateManager.goldHistory.model.GoldHistory;
import io.github.kawajava.MMOEstateManager.goldHistory.service.GoldHistoryService;
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
@RequestMapping("/history-golds")
@Validated
public class GoldHistoryController {

    private final GoldHistoryService goldHistoryService;

    @GetMapping("/{slug}")
    public List<GoldHistory> getHistoryGoldsByBoroughSlug(@PathVariable @Pattern(regexp = "[a-z0-9\\-]+")
                                                              @Length(max = 255) String slug) {
        return goldHistoryService.getHistoryGoldsByBoroughSlug(slug);
    }
}
