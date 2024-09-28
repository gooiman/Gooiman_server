package dev.gooiman.server.user.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginRequestDto {

    private String name;
    private String password;
}
