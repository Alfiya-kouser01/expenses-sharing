package com.assignment.expenses_sharing.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "PARTICIPANTS")
public class Participant {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "expense_id")
    @JsonIgnore
    private ExpensesTB expense;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserTB user;

    @Column(name = "amount")
    private float amount;

    @Column(name = "percentage")
    private float percentage; // Used for percentage split method, if needed

}
