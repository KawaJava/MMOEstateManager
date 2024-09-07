package io.github.kawajava.MMOEstateManager.borough.controller;

import io.github.kawajava.MMOEstateManager.borough.controller.dto.GoldDto;
import io.github.kawajava.MMOEstateManager.borough.model.Borough;
import io.github.kawajava.MMOEstateManager.common.service.BoroughService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/boroughs")
@Validated
public class BoroughController {

    private final BoroughService boroughService;

    @GetMapping
    @Cacheable("boroughs")
    public Page<Borough> getBoroughs(Pageable pageable) {
        return boroughService.getBoroughs(pageable);
    }

    @GetMapping("/{slug}")
    public Borough getBoroughBySlug(@PathVariable @Pattern(regexp = "[a-z0-9\\-]+")
                                    @Length(max = 255) String slug) {
        return boroughService.getBoroughBySlug(slug);
    }

    @PatchMapping("/{slug}/update-gold")
    public Borough updateGoldInBorough(@PathVariable @Pattern(regexp = "[a-z0-9\\-]+")
                                           @Length(max = 255) String slug,
                                       @RequestBody @Valid GoldDto goldDto) {
        return boroughService.updateGoldInBorough(slug, goldDto);
    }
}
