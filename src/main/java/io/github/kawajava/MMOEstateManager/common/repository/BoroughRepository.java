package io.github.kawajava.MMOEstateManager.common.repository;

import io.github.kawajava.MMOEstateManager.borough.model.Borough;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface BoroughRepository extends JpaRepository<Borough, Long> {
    Optional<Borough> findBySlug(String slug);

    List<Borough> findAllByActualLeaderId(Long actualLeaderId);

    List<Borough> findByEmailSendFalseAndDateAddedLessThan(LocalDateTime localDateTime);

    @Query("update Borough b set b.emailSend = true where b.id in (:ids)")
    @Modifying
    void updateEmailSend(List<Long> ids);
}
