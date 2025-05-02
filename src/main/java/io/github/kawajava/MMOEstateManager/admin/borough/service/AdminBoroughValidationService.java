package io.github.kawajava.MMOEstateManager.admin.borough.service;

import io.github.kawajava.MMOEstateManager.admin.borough.model.AdminBorough;
import io.github.kawajava.MMOEstateManager.admin.borough.repository.AdminBoroughRepository;
import io.github.kawajava.MMOEstateManager.admin.common.exception.adminBorough.BoroughNameAlreadyExistsException;
import io.github.kawajava.MMOEstateManager.admin.common.exception.adminBorough.BoroughSlugAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminBoroughValidationService {

    private final AdminBoroughRepository adminBoroughRepository;

    public void validateUniqueConstraints(AdminBorough boroughToValidate) {
        List<AdminBorough> all = adminBoroughRepository.findAll();

        all.stream()
                .filter(b -> boroughToValidate.getId() == null || !b.getId().equals(boroughToValidate.getId()))
                .forEach(b -> {
                    if (b.getName().equalsIgnoreCase(boroughToValidate.getName())) {
                        throw new BoroughNameAlreadyExistsException(boroughToValidate.getName());
                    }
                    if (b.getSlug().equalsIgnoreCase(boroughToValidate.getSlug())) {
                        throw new BoroughSlugAlreadyExistsException(boroughToValidate.getSlug());
                    }
                });
    }
}

