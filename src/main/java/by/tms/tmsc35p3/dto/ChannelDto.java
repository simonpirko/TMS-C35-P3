package by.tms.tmsc35p3.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChannelDto {

    @NotBlank
    @NotNull
    @NotEmpty
    private String title;

    @NotBlank
    @NotNull
    @NotEmpty
    private String description;

    @NotBlank
    @NotNull
    @NotEmpty
    private Long authorId;
}
