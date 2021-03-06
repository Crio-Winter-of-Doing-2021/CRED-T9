package com.crio.cred.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * The type Login response dto.
 *
 * @author harikesh.pallantla
 */
@Getter
@Setter
@AllArgsConstructor
public class LoginResponseDTO {
    private Long userId;
    private String emailId;
    private String token;
    private String tokenType;
}
