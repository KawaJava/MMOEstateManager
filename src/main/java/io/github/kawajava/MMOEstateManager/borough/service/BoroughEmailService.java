package io.github.kawajava.MMOEstateManager.borough.service;

import io.github.kawajava.MMOEstateManager.borough.model.Borough;
import io.github.kawajava.MMOEstateManager.common.repository.BoroughRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BoroughEmailService {

    @Value("${time.to.add.updated.gold}")
    private int daysToUpdateGold;

    private final BoroughRepository boroughRepository;

    @Transactional
    @Scheduled(cron = "${cron.borough.time.email.expression}")
    public void updateBoroughsWhereGoldIsAddedTooLate() {
        List<Borough> boroughs = boroughRepository.findByEmailSendFalseAndDateAddedLessThan
                (LocalDateTime.now().minusDays(daysToUpdateGold));
        log.info("gminy z przekroczonym czasem dodania złota " + boroughs.size());

        List<Long> playersIds = boroughs.stream()
                .map(Borough::getActualLeaderId)
                .distinct()
                .toList();

        //emails to players

        List<Long> boroughsIds = boroughs.stream()
                .map(Borough::getId)
                .distinct()
                .toList();
        boroughRepository.updateEmailSend(boroughsIds);
    }
}
