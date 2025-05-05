package io.github.kawajava.MMOEstateManager.borough.service.mapper;

import io.github.kawajava.MMOEstateManager.borough.model.Borough;

import java.util.List;

public class BoroughMapper {

    public static List<Long> getBoroughsIds(List<Borough> boroughs) {
        return boroughs.stream()
                .map(Borough::getId)
                .distinct()
                .toList();
    }

    public static List<Long> getPlayersIds(List<Borough> boroughs) {
        return boroughs.stream()
                .map(borough -> borough.getActualLeader().getId())
                .distinct()
                .toList();
    }
}
