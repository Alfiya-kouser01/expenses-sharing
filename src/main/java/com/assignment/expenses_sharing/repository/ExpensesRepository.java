package com.assignment.expenses_sharing.repository;

import com.assignment.expenses_sharing.entities.ExpensesTB;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpensesRepository extends JpaRepository<ExpensesTB, Integer> {
    List<ExpensesTB> findByUserId(int userId);
}
