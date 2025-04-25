package io.github.kawajava.MMOEstateManager.admin.player.controller;

import io.github.kawajava.MMOEstateManager.admin.player.controller.dto.AdminPlayerDto;
import io.github.kawajava.MMOEstateManager.admin.player.controller.dto.AdminPlayerToAutocomplete;
import io.github.kawajava.MMOEstateManager.admin.player.model.AdminPlayer;
import io.github.kawajava.MMOEstateManager.admin.player.service.AdminPlayerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

import static io.github.kawajava.MMOEstateManager.admin.utils.SlugifyUtils.slugifySlug;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/players")
public class AdminPlayerController {

    public static final Long EMPTY_ID = null;
    private final AdminPlayerService adminPlayerService;

    @GetMapping
    public Page<AdminPlayer> getAdminPlayers(Pageable pageable) {
        return adminPlayerService.getAdminPlayers(pageable);
    }

    @GetMapping("/list")
    @Cacheable("playersList")
    public List<AdminPlayer> getAdminPlayersAsList() {
        return adminPlayerService.getAdminPlayersAsList();
    }

    @GetMapping("/inactive")
    public List<AdminPlayer> getInactiveAdminPlayers() {
        return adminPlayerService.getInactiveAdminPlayers();
    }

    @GetMapping("/{id}")
    public AdminPlayer getAdminPlayer(@PathVariable Long id) {
        return adminPlayerService.getAdminPlayer(id);
    }

    @GetMapping("/toAutocomplete")
    public List<AdminPlayerToAutocomplete> getAdminPlayersToAutoComplete(@RequestParam String beginning) {
        return adminPlayerService.getAdminPlayersToAutocomplete(beginning);
    }

    @PostMapping
    @CacheEvict(value = {"players", "playersList"}, allEntries = true)
    public AdminPlayer createAdminPlayer(@RequestBody @Valid AdminPlayerDto adminPlayerDto) {
        return adminPlayerService.createAdminPlayer(mapAdminPlayer(adminPlayerDto, EMPTY_ID));
    }

    @PutMapping("/{id}")
    @CacheEvict(value = {"players", "playersList"}, allEntries = true)
    public AdminPlayer updateAdminPlayer(@RequestBody @Valid AdminPlayerDto adminPlayerDto,
                                         @PathVariable Long id) {
        return adminPlayerService.updateAdminPlayer(mapAdminPlayer(adminPlayerDto, id));
    }

    @DeleteMapping("/{id}/deactivate")
    @CacheEvict(value = {"players", "playersList"}, allEntries = true)
    public void deactivateAdminPlayer(@PathVariable Long id){
        adminPlayerService.deactivateAdminPlayer(id);
    }

    @DeleteMapping("/{id}")
    @CacheEvict(value = {"players", "playersList"}, allEntries = true)
    public void deleteAdminPlayer(@PathVariable Long id){
        adminPlayerService.deleteAdminPlayer(id);
    }

    private AdminPlayer mapAdminPlayer(AdminPlayerDto adminPlayerDto, Long id) {
        return AdminPlayer.builder()
                .id(id)
                .name(adminPlayerDto.getName())
                .email(adminPlayerDto.getEmail())
                .clan(adminPlayerDto.getClan())
                .isActive(true)
                .created(LocalDate.now())
                .slug(slugifySlug(adminPlayerDto.getSlug()))
                .build();
    }
}
