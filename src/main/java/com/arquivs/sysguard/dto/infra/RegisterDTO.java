package com.arquivs.sysguard.dto.infra;

import com.arquivs.sysguard.model.Role;

public record RegisterDTO(String login, String password, Role role) {
}
