package com.assignment.expenses_sharing.service;

import com.assignment.expenses_sharing.dto.ExpensesDTO;
import com.assignment.expenses_sharing.dto.ParticipantDTO;
import com.assignment.expenses_sharing.dto.PercentageParticipantDTO;
import com.assignment.expenses_sharing.entities.ExpensesTB;

import java.util.List;

public interface ExpensesService {
    ExpensesTB saveExpense(ExpensesTB expenses);

    List<ExpensesTB> getExpensesByUserId(int userId);

    List<ExpensesTB> getAllExpenses();

    String generateBalanceSheet();

    String deleteExpensesByUserId(int userId);

    // Methods to handle different split types
    ExpensesDTO splitExpenseEqual(int expenseId, List<ParticipantDTO> participants);

    ExpensesTB splitExpenseExact(int expenseId, List<ParticipantDTO> participants);

    ExpensesTB splitExpensePercentage(int expenseId, List<PercentageParticipantDTO> participants);
}
