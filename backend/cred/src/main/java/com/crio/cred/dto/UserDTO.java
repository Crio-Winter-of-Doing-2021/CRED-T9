package com.crio.cred.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;

/**
 * The type User dto.
 *
 * @author harikesh.pallantla
 */
@Getter
@Setter
@JsonInclude(Include.NON_NULL)
public class UserDTO {
    private Long userId;
    private String emailId;
    private String firstName;
    private String lastName;
    private String mobileNumber;
    @Nullable
    private String token;
    @Nullable
    private String tokenType;
}
