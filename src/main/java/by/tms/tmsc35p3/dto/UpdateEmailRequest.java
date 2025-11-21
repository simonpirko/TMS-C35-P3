package by.tms.tmsc35p3.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateEmailRequest {
    private String oldEemail;

    @NotBlank
    private String newEemail;
}
