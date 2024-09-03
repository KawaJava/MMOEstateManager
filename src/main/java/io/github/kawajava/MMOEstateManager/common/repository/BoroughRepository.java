package io.github.kawajava.MMOEstateManager.common.repository;

import io.github.kawajava.MMOEstateManager.borough.model.Borough;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BoroughRepository extends JpaRepository<Borough, Long> {
    Optional<Borough> findBySlug(String slug);

    List<Borough> findAllByActualLeaderId(Long actualLeaderId);
}
