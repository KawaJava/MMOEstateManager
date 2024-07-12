package io.github.kawajava.MMOEstateManager.admin.player.service;

import io.github.kawajava.MMOEstateManager.admin.player.model.AdminPlayer;
import io.github.kawajava.MMOEstateManager.admin.player.repository.AdminPlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminPlayerService {

    private final AdminPlayerRepository adminPlayerRepository;

    public List<AdminPlayer> getAdminPlayers() {
        return adminPlayerRepository.findAll();
    }

    public List<AdminPlayer> getInActiveAdminPlayers() {
        return adminPlayerRepository.findAll().stream()
                .filter(adminPlayer -> !adminPlayer.isActive())
                .toList();
    }

    public AdminPlayer getAdminPlayer(Long id) {
        return adminPlayerRepository.findById(id).orElseThrow();
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
