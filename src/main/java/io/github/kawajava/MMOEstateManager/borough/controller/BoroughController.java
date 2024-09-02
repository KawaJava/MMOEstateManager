package io.github.kawajava.MMOEstateManager.borough.controller;

import io.github.kawajava.MMOEstateManager.borough.model.Borough;
import io.github.kawajava.MMOEstateManager.borough.service.BoroughService;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/boroughs")
@Validated
public class BoroughController {

    private final BoroughService boroughService;

    @GetMapping
    public Page<Borough> getBoroughs(Pageable pageable) {
        return boroughService.getBoroughs(pageable);
    }

    @GetMapping("/{slug}")
    public Borough getBoroughBySlug(@PathVariable @Pattern(regexp = "[a-z0-9\\-]+")
                                    @Length(max = 255) String slug) {
        return boroughService.getBoroughBySlug(slug);
    }
}
