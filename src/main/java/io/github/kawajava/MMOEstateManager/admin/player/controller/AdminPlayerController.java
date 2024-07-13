package io.github.kawajava.MMOEstateManager.admin.player.controller;

import io.github.kawajava.MMOEstateManager.admin.player.controller.dto.AdminPlayerDto;
import io.github.kawajava.MMOEstateManager.admin.player.model.AdminPlayer;
import io.github.kawajava.MMOEstateManager.admin.player.service.AdminPlayerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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

    @GetMapping("/inactive")
    public List<AdminPlayer> getInActiveAdminPlayers() {
        return adminPlayerService.getInActiveAdminPlayers();
    }

    @GetMapping("/{id}")
    public AdminPlayer getAdminPlayer(@PathVariable Long id) {
        return adminPlayerService.getAdminPlayer(id);
    }

    @PostMapping
    public AdminPlayer createAdminPlayer(@RequestBody @Valid AdminPlayerDto adminPlayerDto) {
        return adminPlayerService.createAdminPlayer(mapAdminPlayer(adminPlayerDto, EMPTY_ID));
    }

    @PutMapping("/{id}")
    public AdminPlayer updateAdminPlayer(@RequestBody @Valid AdminPlayerDto adminPlayerDto,
                                         @PathVariable Long id) {
        return adminPlayerService.updateAdminPlayer(mapAdminPlayer(adminPlayerDto, id));
    }

    @DeleteMapping("/{id}/deactivate")
    public void deactivateAdminPlayer(@PathVariable Long id){
        adminPlayerService.deactivateAdminPlayer(id);
    }

    @DeleteMapping("/{id}")
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
