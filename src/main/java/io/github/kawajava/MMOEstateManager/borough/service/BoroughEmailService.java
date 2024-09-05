package io.github.kawajava.MMOEstateManager.borough.service;

import io.github.kawajava.MMOEstateManager.borough.model.Borough;
import io.github.kawajava.MMOEstateManager.common.repository.BoroughRepository;
import io.github.kawajava.MMOEstateManager.common.repository.PlayerRepository;
import io.github.kawajava.MMOEstateManager.mailing.service.EmailClientService;
import io.github.kawajava.MMOEstateManager.player.model.Player;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static io.github.kawajava.MMOEstateManager.borough.service.mapper.BoroughEmailMessageCreator.createEmailMessage;
import static io.github.kawajava.MMOEstateManager.borough.service.mapper.BoroughMapper.getBoroughsIds;
import static io.github.kawajava.MMOEstateManager.borough.service.mapper.BoroughMapper.getPlayersIds;

@Service
@RequiredArgsConstructor
public class BoroughEmailService {

    @Value("${time.to.add.updated.gold}")
    private int daysToUpdateGold;

    private final BoroughRepository boroughRepository;
    private final PlayerRepository playerRepository;
    private final EmailClientService emailClientService;

    @Transactional
    @Scheduled(cron = "${cron.borough.time.email.expression}")
    public void updateBoroughsWhereGoldIsAddedTooLate() {
        List<Borough> boroughs = boroughRepository.findByEmailSendFalseAndDateAddedLessThan
                (LocalDateTime.now().minusDays(daysToUpdateGold));

        List<Long> playersIds = getPlayersIds(boroughs);
        List<Player> players = playerRepository.selectAllByIdIn(playersIds);

        players.forEach(this::sendEmail);

        List<Long> boroughsIds = getBoroughsIds(boroughs);
        boroughRepository.updateEmailSend(boroughsIds);
    }

    private void sendEmail(Player player) {
        emailClientService.getInstance().send(player.getEmail(),
                "Prośba o aktualizacje stanu złota w gminie", createEmailMessage(player));
    }

}
