package com.unibuc.appbackend.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Required details for creating a new sprint")
public class SprintRequest {

    @NotBlank(message = "Sprint deadline cannot be null")
    @Schema(description = "Deadline for this sprint")
    LocalDate deadline;
}
