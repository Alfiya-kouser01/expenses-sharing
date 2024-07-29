package com.assignment.expenses_sharing.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "USER_TBL")
public class UserTB {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @Column(name = "contact")
    private long contact;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;
}
