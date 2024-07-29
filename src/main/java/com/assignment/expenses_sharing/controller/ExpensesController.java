package com.assignment.expenses_sharing.controller;

import com.assignment.expenses_sharing.dto.ExpensesDTO;
import com.assignment.expenses_sharing.entities.ExpensesTB;
import com.assignment.expenses_sharing.service.ExpensesService;
import com.assignment.expenses_sharing.dto.ParticipantDTO;
import com.assignment.expenses_sharing.dto.PercentageParticipantDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/expenses")
public class ExpensesController {

    @Autowired
    private ExpensesService expensesService;

    @PostMapping
    public ExpensesTB saveExpense(@RequestBody ExpensesTB expenses) {
        log.info("Received request to save expense: {}", expenses);
        ExpensesTB savedExpense = expensesService.saveExpense(expenses);
        log.info("Expense saved successfully: {}", savedExpense);
        return savedExpense;
    }

    @GetMapping("/user/{userId}")
    public List<ExpensesTB> getExpensesByUserId(@PathVariable int userId) {
        log.info("Received request to fetch expenses for user ID: {}", userId);
        List<ExpensesTB> expenses = expensesService.getExpensesByUserId(userId);
        log.info("Fetched {} expenses for user ID: {}", expenses.size(), userId);
        return expenses;
    }

    @GetMapping("/all")
    public List<ExpensesTB> getAllExpenses() {
        log.info("Received request to fetch all expenses");
        List<ExpensesTB> expenses = expensesService.getAllExpenses();
        log.info("Fetched {} total expenses", expenses.size());
        return expenses;
    }

    @GetMapping("/balance-sheet")
    public String downloadBalanceSheet() {
        log.info("Received request to generate and download balance sheet");
        String balanceSheet = expensesService.generateBalanceSheet();
        log.info("Generated balance sheet successfully");
        return balanceSheet;
    }

    @DeleteMapping("/user/{userId}")
    public String deleteExpensesByUserId(@PathVariable int userId) {
        log.info("Received request to delete expenses for user ID: {}", userId);
        String result = expensesService.deleteExpensesByUserId(userId);
        log.info("Deletion result for user ID {}: {}", userId, result);
        return result;
    }

    @PostMapping("/split/equal/{expenseId}")
    public ExpensesDTO splitExpenseEqual(@PathVariable int expenseId, @RequestBody List<ParticipantDTO> participants) {
        log.info("Received request to split expense with ID {} equally among participants: {}", expenseId, participants);
        ExpensesDTO updatedExpense = expensesService.splitExpenseEqual(expenseId, participants);
        log.info("Expense split equally successfully for ID {}: {}", expenseId, updatedExpense);
        return updatedExpense;
    }

    @PostMapping("/split/exact/{expenseId}")
    public ExpensesTB splitExpenseExact(@PathVariable int expenseId, @RequestBody List<ParticipantDTO> participants) {
        log.info("Received request to split expense with ID {} with exact amounts for participants: {}", expenseId, participants);
        ExpensesTB updatedExpense = expensesService.splitExpenseExact(expenseId, participants);
        log.info("Expense split with exact amounts successfully for ID {}: {}", expenseId, updatedExpense);
        return updatedExpense;
    }

    @PostMapping("/split/percentage/{expenseId}")
    public ExpensesTB splitExpensePercentage(@PathVariable int expenseId, @RequestBody List<PercentageParticipantDTO> participants) {
        log.info("Received request to split expense with ID {} with percentage amounts for participants: {}", expenseId, participants);
        ExpensesTB updatedExpense = expensesService.splitExpensePercentage(expenseId, participants);
        log.info("Expense split with percentages successfully for ID {}: {}", expenseId, updatedExpense);
        return updatedExpense;
    }
}
