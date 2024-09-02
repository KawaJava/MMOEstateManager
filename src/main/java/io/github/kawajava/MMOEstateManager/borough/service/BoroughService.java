package io.github.kawajava.MMOEstateManager.borough.service;

import io.github.kawajava.MMOEstateManager.borough.model.Borough;
import io.github.kawajava.MMOEstateManager.borough.repository.BoroughRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoroughService {

    private final BoroughRepository boroughRepository;

    public Page<Borough> getBoroughs(Pageable pageable) {
        return boroughRepository.findAll(pageable);
    }

    public Borough getBoroughBySlug(String slug) {
        return boroughRepository.findBySlug(slug).orElseThrow();
    }
}
