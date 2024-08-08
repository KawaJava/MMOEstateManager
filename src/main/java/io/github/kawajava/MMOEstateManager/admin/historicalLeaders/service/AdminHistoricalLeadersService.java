package io.github.kawajava.MMOEstateManager.admin.historicalLeaders.service;

import io.github.kawajava.MMOEstateManager.admin.historicalLeaders.model.AdminHistoricalLeaders;
import io.github.kawajava.MMOEstateManager.admin.historicalLeaders.repository.AdminHistoricalLeadersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminHistoricalLeadersService {

    private final AdminHistoricalLeadersRepository adminHistoricalLeadersRepository;

    public Page<AdminHistoricalLeaders> getAdminHistoricalLeaders(Pageable pageable) {
        return adminHistoricalLeadersRepository.findAll(pageable);
    }
    public void createAdminHistoricalLeader(AdminHistoricalLeaders adminHistoricalLeader) {
        adminHistoricalLeadersRepository.save(adminHistoricalLeader);
    }

}
