package io.github.kawajava.MMOEstateManager.admin.playerReview.model.dto;

import io.github.kawajava.MMOEstateManager.admin.playerReview.model.AiOpinion;

public record OpinionDto(Long id, String content, AiOpinion aiOpinion) {}

