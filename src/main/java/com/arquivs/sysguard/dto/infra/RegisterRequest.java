package com.arquivs.sysguard.dto.infra;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterRequest {
    private String login;
    private String password;
}
