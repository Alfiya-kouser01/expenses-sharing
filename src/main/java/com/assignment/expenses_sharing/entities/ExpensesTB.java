package com.assignment.expenses_sharing.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "EXPENSES_TBL")
public class ExpensesTB {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Column(name = "expenses_name")
    private String expensesName;

    @Column(name = "expenses_amount")
    private float expensesAmount;

    @Enumerated(EnumType.STRING)
    @Column(name = "split_method")
    private SplitMethod splitMethod;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserTB user;
//
//    @OneToMany(mappedBy = "expense", cascade = CascadeType.ALL, orphanRemoval = true)
//    @JsonIgnore
//    private List<Participant> participants = new ArrayList<>();

    @OneToMany(mappedBy = "expense", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    @ToString.Exclude // If using Lombok to prevent recursive call
    private List<Participant> participants = new ArrayList<>();

    public void addParticipant(Participant participant) {
        participants.add(participant);
        participant.setExpense(this);
    }

    public void removeParticipant(Participant participant) {
        participants.remove(participant);
        participant.setExpense(null);
    }

    public void setParticipants(List<Participant> participants) {
        this.participants.clear();
        if (participants != null) {
            participants.forEach(this::addParticipant);
        }
    }

    public enum SplitMethod {
        EQUAL, EXACT, PERCENTAGE
    }
}
