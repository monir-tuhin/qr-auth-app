package com.app.authentiScan.payload.request;

import com.app.authentiScan.models.ERole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SignupRequest {
    private Long id;
    @NotBlank
    private String fullName;
    @NotBlank
    private String username;
    @NotBlank
    private String email;
    @NotBlank
    private String mobileNumber;
    @NotBlank
    private String password;
    private Set<String> roles;
}
