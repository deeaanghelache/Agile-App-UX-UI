package com.unibuc.appbackend.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Required details for create new project")
public class ProjectRequest {

    @NotBlank(message = "Project Name cannot be null")
    @Schema(description = "Project Name used to create a new project", required = true)
    private String name;

    @NotBlank(message = "Description cannot be null")
    @Schema(description = "Description for the new project", required = true)
    private String description;
}
