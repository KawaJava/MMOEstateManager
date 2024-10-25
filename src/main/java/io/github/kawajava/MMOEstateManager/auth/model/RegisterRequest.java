package io.github.kawajava.MMOEstateManager.auth.model;

import io.github.kawajava.MMOEstateManager.user.model.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    private String username;
    private String password;
    private Role role;
    private Long playerId;
}
