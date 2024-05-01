package com.unibuc.appbackend.dtos;

import com.unibuc.appbackend.enums.RoleName;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Required details for creating a new role")
public class RoleRequest {

    @NotBlank(message = "Role Name cannot be null")
    @Schema(description = "Role name for the new role, should be one of ADMIN, USER, SCRUM_MASTER", required = true)
    private RoleName roleName;
}
