package by.tms.tmsc35p3.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RegisterRequest {
    @Email
    private String email;

    @NotBlank
    private String password;
}