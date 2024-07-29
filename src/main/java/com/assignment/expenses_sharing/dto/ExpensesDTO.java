package com.assignment.expenses_sharing.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ExpensesDTO {
    private int id;
    private String expensesName;
    private float expensesAmount;
    private String splitMethod;
    private UserDTO user;
    private List<ParticipantDTO> participants;

    // Getters and setters
}
