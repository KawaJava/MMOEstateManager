package io.github.kawajava.MMOEstateManager.admin.borough.service;

import io.github.kawajava.MMOEstateManager.admin.borough.model.AdminBorough;
import io.github.kawajava.MMOEstateManager.admin.borough.repository.AdminBoroughRepository;
import io.github.kawajava.MMOEstateManager.admin.common.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminBoroughService {

    private final AdminBoroughRepository adminBoroughRepository;

    public Page<AdminBorough> getAdminBoroughs(Pageable pageable) {
        return adminBoroughRepository.findAll(pageable);
    }

    public AdminBorough getAdminBorough(Long id) {
        return adminBoroughRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Borough", id));
    }

    public AdminBorough createAdminBorough(AdminBorough adminBorough) {
        return adminBoroughRepository.save(adminBorough);
    }
    public AdminBorough updateAdminBoroughGeneralInfo(AdminBorough adminBorough) {
        return adminBoroughRepository.save(adminBorough);
    }
}
