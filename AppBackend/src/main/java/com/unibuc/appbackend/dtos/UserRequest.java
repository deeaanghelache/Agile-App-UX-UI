package com.unibuc.appbackend.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Required details for create new user request")
public class UserRequest {

    @NotBlank(message = "First Name cannot be null")
    @Schema(description = "First Name used to create an account on this platform", required = true)
    private String firstName;

    @NotBlank(message = "Last Name cannot be null")
    @Schema(description = "Last Name used to create an account on this platform", required = true)
    private String lastName;

    @NotBlank(message = "Email cannot be null")
    @Schema(description = "Email address used to create an account on this platform", required = true)
    private String email;

    @NotBlank(message = "Password cannot be null")
    @Schema(description = "Password used to create an account on this platform", required = true)
    private String password;
}
