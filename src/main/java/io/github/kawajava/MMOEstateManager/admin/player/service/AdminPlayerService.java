package io.github.kawajava.MMOEstateManager.admin.player.service;

import io.github.kawajava.MMOEstateManager.admin.common.exception.ResourceNotFoundException;
import io.github.kawajava.MMOEstateManager.admin.player.model.AdminPlayer;
import io.github.kawajava.MMOEstateManager.admin.player.repository.AdminPlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminPlayerService {

    private final AdminPlayerRepository adminPlayerRepository;

    public Page<AdminPlayer> getAdminPlayers(Pageable pageable) {
        return adminPlayerRepository.findAll(pageable);
    }

    public List<AdminPlayer> getInactiveAdminPlayers() {
        return adminPlayerRepository.findAll().stream()
                .filter(adminPlayer -> !adminPlayer.isActive())
                .toList();
    }

    public AdminPlayer getAdminPlayer(Long id) {
        return adminPlayerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Player", id));
    }

    public AdminPlayer createAdminPlayer(AdminPlayer adminPlayer) {
        return adminPlayerRepository.save(adminPlayer);
    }

    public AdminPlayer updateAdminPlayer(AdminPlayer adminPlayer) {
        return adminPlayerRepository.save(adminPlayer);
    }

    public void deactivateAdminPlayer(Long id) {
        adminPlayerRepository.deactivate(id);
    }

    public void deleteAdminPlayer(Long id) {
        adminPlayerRepository.deleteById(id);
    }
}
