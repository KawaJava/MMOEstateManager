package io.github.kawajava.MMOEstateManager.borough.service;

import io.github.kawajava.MMOEstateManager.borough.controller.dto.GoldDto;
import io.github.kawajava.MMOEstateManager.borough.model.Borough;
import io.github.kawajava.MMOEstateManager.common.repository.BoroughRepository;
import io.github.kawajava.MMOEstateManager.goldHistory.model.GoldHistory;
import io.github.kawajava.MMOEstateManager.goldHistory.repository.GoldHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class BoroughService {

    private final BoroughRepository boroughRepository;
    private final GoldHistoryRepository goldHistoryRepository;

    public Page<Borough> getBoroughs(Pageable pageable) {
        return boroughRepository.findAll(pageable);
    }

    public Borough getBoroughBySlug(String slug) {
        return boroughRepository.findBySlug(slug).orElseThrow();
    }

    @Transactional
    public Borough updateGoldInBorough(String slug, GoldDto goldDto) {

        var borough = boroughRepository.findBySlug(slug).orElseThrow();
        var goldHistory = getGoldHistory(borough);
        goldHistoryRepository.save(goldHistory);
        var updatedBorough = getUpdatedBorough(goldDto, borough);
        boroughRepository.save(updatedBorough);
        return updatedBorough;
    }

    private GoldHistory getGoldHistory(Borough borough) {
        return GoldHistory.builder()
                .id(null)
                .boroughId(borough.getId())
                .gold(borough.getActualGold())
                .goldAddedBy(borough.getGoldAddedBy())
                .dateAdded(borough.getDateAdded())
                .emailSend(borough.getEmailSend())
                .build();
    }

    private Borough getUpdatedBorough(GoldDto goldDto, Borough borough) {
        return Borough.builder()
                .id(borough.getId())
                .name(borough.getName())
                .slug(borough.getSlug())
                .countryId(borough.getCountryId())
                .actualLeaderId(borough.getActualLeaderId())
                .leaderStartDate(borough.getLeaderStartDate())
                .actualGold(goldDto.getNewGold())
                .goldAddedBy(goldDto.getGoldAddedBy())
                .dateAdded(LocalDateTime.now())
                .emailSend(false)
                .build();
    }
}
