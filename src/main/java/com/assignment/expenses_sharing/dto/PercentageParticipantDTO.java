package com.assignment.expenses_sharing.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class PercentageParticipantDTO {
    private int userId;
    private float percentage;
    private float amount; // Calculated amount based on percentage
}
