package com.assignment.expenses_sharing.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class ParticipantDTO {
    private int id;
    private UserDTO user;
    private float amount;
    private float percentage;
}
