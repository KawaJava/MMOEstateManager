package io.github.kawajava.MMOEstateManager.admin.borough.controller;

import io.github.kawajava.MMOEstateManager.admin.borough.controller.dto.AdminBoroughDto;
import io.github.kawajava.MMOEstateManager.admin.borough.controller.dto.AdminBoroughGeneralInfoDto;
import io.github.kawajava.MMOEstateManager.admin.borough.model.AdminBorough;
import io.github.kawajava.MMOEstateManager.admin.borough.service.AdminBoroughService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

import static io.github.kawajava.MMOEstateManager.admin.utils.SlugifyUtils.slugifySlug;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/boroughs")
public class AdminBoroughController {

    private final AdminBoroughService adminBoroughService;

    @GetMapping
    public Page<AdminBorough> getAdminBoroughs(Pageable pageable) {
        return adminBoroughService.getAdminBoroughs(pageable);
    }

    @GetMapping("/{id}")
    public AdminBorough getAdminBorough(@PathVariable Long id) {
        return adminBoroughService.getAdminBorough(id);
    }

    @PostMapping
    public AdminBorough createAdminBorough(@RequestBody @Valid AdminBoroughDto adminBoroughDto) {
        return adminBoroughService.createAdminBorough(mapAdminBorough(adminBoroughDto));
    }

    @PatchMapping("/{id}")
    public AdminBorough updateAdminBoroughGeneralInfo(@PathVariable Long id,
                                                      @RequestBody @Valid AdminBoroughGeneralInfoDto adminBoroughGeneralInfo ) {
        return adminBoroughService.updateAdminBoroughGeneralInfo(
                mapUpdatedAdminBorough(id, adminBoroughGeneralInfo));
    }

    @PatchMapping("/{boroughId}/changeLeader/{leaderId}")
    public AdminBorough changeLeader(@PathVariable Long boroughId, @PathVariable Long leaderId) {
        return adminBoroughService.changeLeader(boroughId, leaderId);
    }

    private AdminBorough mapUpdatedAdminBorough(Long boroughId, AdminBoroughGeneralInfoDto adminBoroughGeneralInfoDto) {
        AdminBorough adminBorough = adminBoroughService.getAdminBorough(boroughId);
        return AdminBorough.builder()
                .id(boroughId)
                .name(adminBoroughGeneralInfoDto.getName())
                .slug(slugifySlug(adminBoroughGeneralInfoDto.getSlug()))
                .countryId(adminBoroughGeneralInfoDto.getCountryId())
                .actualLeaderId(adminBorough.getActualLeaderId())
                .leaderStartDate(adminBorough.getLeaderStartDate())
                .actualGold(adminBorough.getActualGold())
                .goldAddedBy(adminBorough.getGoldAddedBy())
                .dateAdded(adminBorough.getDateAdded())
                .emailSend(adminBorough.getEmailSend())
                .build();
    }

    private AdminBorough mapAdminBorough(AdminBoroughDto adminBoroughDto) {
        return AdminBorough.builder()
                .id(null)
                .name(adminBoroughDto.getName())
                .slug(slugifySlug(adminBoroughDto.getSlug()))
                .countryId(adminBoroughDto.getCountryId())
                .actualLeaderId(adminBoroughDto.getActualLeaderId())
                .leaderStartDate(adminBoroughDto.getLeaderStartDate())
                .actualGold(adminBoroughDto.getActualGold())
                .goldAddedBy(adminBoroughDto.getGoldAddedBy())
                .dateAdded(LocalDateTime.now())
                .emailSend(false)
                .build();
    }
}
