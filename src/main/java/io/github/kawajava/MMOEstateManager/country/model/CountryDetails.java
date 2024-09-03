package io.github.kawajava.MMOEstateManager.country.model;

import io.github.kawajava.MMOEstateManager.borough.model.Borough;
import io.github.kawajava.MMOEstateManager.common.model.Clan;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Getter
@Builder
public class CountryDetails {
    private Long id;
    private String name;
    private String slug;
    private List<PlayerInfo> playerInfoList;
    private BigDecimal goldLimit;
    private LocalDateTime sheriffStartDate;
    private int boroughCount;
    private List<Borough> boroughs;
    private BigDecimal allGold;
    private BigDecimal goldToCollect;
    private Map<String, BigDecimal> goldByPlayers;
    private Map<Clan, BigDecimal> goldByClan;
    private Map<String, BigDecimal> playerPercentage;
    private Map<Clan, BigDecimal> clanPercentage;
}
