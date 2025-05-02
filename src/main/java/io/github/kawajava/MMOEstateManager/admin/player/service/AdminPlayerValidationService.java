package io.github.kawajava.MMOEstateManager.admin.player.service;

import io.github.kawajava.MMOEstateManager.admin.common.exception.adminPlayer.EmailAlreadyExistsException;
import io.github.kawajava.MMOEstateManager.admin.common.exception.adminPlayer.NameAlreadyExistsException;
import io.github.kawajava.MMOEstateManager.admin.common.exception.adminPlayer.SlugAlreadyExistsException;
import io.github.kawajava.MMOEstateManager.admin.player.model.AdminPlayer;
import io.github.kawajava.MMOEstateManager.admin.player.repository.AdminPlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminPlayerValidationService {

    private final AdminPlayerRepository adminPlayerRepository;

    public void validateUniqueConstraints(AdminPlayer playerToValidate) {
        List<AdminPlayer> all = adminPlayerRepository.findAll();

        all.stream()
                .filter(p -> playerToValidate.getId() == null || !p.getId().equals(playerToValidate.getId()))
                .forEach(p -> {
                    if (p.getName().equalsIgnoreCase(playerToValidate.getName())) {
                        throw new NameAlreadyExistsException(playerToValidate.getName());
                    }
                    if (p.getEmail().equalsIgnoreCase(playerToValidate.getEmail())) {
                        throw new EmailAlreadyExistsException(playerToValidate.getEmail());
                    }
                    if (p.getSlug().equalsIgnoreCase(playerToValidate.getSlug())) {
                        throw new SlugAlreadyExistsException(playerToValidate.getSlug());
                    }
                });
    }
}

