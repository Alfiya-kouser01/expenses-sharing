package com.assignment.expenses_sharing.service.impl;

import com.assignment.expenses_sharing.dto.ExpensesDTO;
import com.assignment.expenses_sharing.dto.ParticipantDTO;
import com.assignment.expenses_sharing.dto.PercentageParticipantDTO;
import com.assignment.expenses_sharing.dto.UserDTO;
import com.assignment.expenses_sharing.entities.ExpensesTB;
import com.assignment.expenses_sharing.entities.Participant;
import com.assignment.expenses_sharing.entities.UserTB;
import com.assignment.expenses_sharing.repository.ExpensesRepository;
import com.assignment.expenses_sharing.repository.UserRepository;
import com.assignment.expenses_sharing.service.ExpensesService;
import com.assignment.expenses_sharing.util.BalanceSheetUtil;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ExpensesServiceImpl implements ExpensesService {

    @Autowired
    private ExpensesRepository expensesRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public ExpensesTB saveExpense(ExpensesTB expenses) {
        log.info("Saving expense: {}", expenses);
        UserTB user = userRepository.findById(expenses.getUser().getId())
                .orElseThrow(() -> new EntityNotFoundException("User not found with ID " + expenses.getUser().getId()));
        expenses.setUser(user);
        return expensesRepository.save(expenses);
    }

    @Override
    public List<ExpensesTB> getExpensesByUserId(int userId) {
        log.info("Fetching expenses for user ID: {}", userId);
        return expensesRepository.findByUserId(userId);
    }

    @Override
    public List<ExpensesTB> getAllExpenses() {
        log.info("Fetching all expenses");
        return expensesRepository.findAll();
    }

    @Override
    public String generateBalanceSheet() {
        log.info("Generating balance sheet");
        List<ExpensesTB> expenses = expensesRepository.findAll();
        return BalanceSheetUtil.createBalanceSheet(expenses);
    }

    @Override
    public String deleteExpensesByUserId(int userId) {
        log.info("Deleting expenses for user ID: {}", userId);
        List<ExpensesTB> expenses = expensesRepository.findByUserId(userId);
        if (expenses.isEmpty()) {
            return "No expenses found for user ID " + userId;
        }
        expensesRepository.deleteAll(expenses);
        return "Expenses for user ID " + userId + " deleted successfully";
    }

    @Override
    public ExpensesDTO splitExpenseEqual(int expenseId, List<ParticipantDTO> participants) {
        log.info("Splitting expense ID: {} equally among participants", expenseId);

        // Fetch the expense
        ExpensesTB expense = expensesRepository.findById(expenseId)
                .orElseThrow(() -> new EntityNotFoundException("Expense not found with ID " + expenseId));

        // Check if the list of participants is not empty
        if (participants == null || participants.isEmpty()) {
            throw new IllegalArgumentException("No participants provided");
        }

        // Ensure the split method is initialized
        if (expense.getSplitMethod() == null) {
            throw new IllegalStateException("Expense split method is not set");
        }

        // Calculate the amount per participant
        float amountPerParticipant = expense.getExpensesAmount() / participants.size();

        // Create participant entities
        ExpensesTB finalExpense = expense;
        List<Participant> participantEntities = participants.stream()
                .map(dto -> createParticipant(finalExpense, dto, amountPerParticipant))
                .collect(Collectors.toList());

        // Clear existing participants and set new ones
        expense.setParticipants(participantEntities);

        // Save the updated expense
        expense = expensesRepository.save(expense);

        return convertToDTO(expense);
    }

    // Method to create a participant entity
    private Participant createParticipant(ExpensesTB expense, ParticipantDTO dto, float amountPerParticipant) {
        Participant participant = new Participant();
        participant.setExpense(expense);

        participant.setUser(userRepository.findById(dto.getUser().getId())
                .orElseThrow(() -> new EntityNotFoundException("User not found with ID " + dto.getUser().getId())));
        participant.setAmount(amountPerParticipant);
        return participant;
    }



    @Override
    public ExpensesTB splitExpensePercentage(int expenseId, List<PercentageParticipantDTO> participants) {
        log.info("Splitting expense ID: {} with percentage amounts", expenseId);
        ExpensesTB expense = expensesRepository.findById(expenseId)
                .orElseThrow(() -> new EntityNotFoundException("Expense not found with ID " + expenseId));

        float totalPercentage = participants.stream()
                .map(PercentageParticipantDTO::getPercentage)
                .reduce(0f, Float::sum);

        if (totalPercentage != 100) {
            throw new IllegalArgumentException("Total percentages do not add up to 100%");
        }

        List<Participant> participantEntities = participants.stream().map(dto -> {
            Participant participant = new Participant();
            participant.setExpense(expense);
            participant.setUser(userRepository.findById(dto.getUserId())
                    .orElseThrow(() -> new EntityNotFoundException("User not found with ID " + dto.getUserId())));
            participant.setAmount((expense.getExpensesAmount() * dto.getPercentage()) / 100);
            return participant;
        }).collect(Collectors.toList());

        // Clear existing participants and set new ones
        expense.getParticipants().clear();
        expense.getParticipants().addAll(participantEntities);
        return expensesRepository.save(expense);
    }


    private ExpensesDTO convertToDTO(ExpensesTB expense) {
        ExpensesDTO dto = new ExpensesDTO();
        dto.setId(expense.getId());
        dto.setExpensesName(expense.getExpensesName());
        dto.setExpensesAmount(expense.getExpensesAmount());
        dto.setSplitMethod(expense.getSplitMethod().name());
        dto.setUser(convertToDTO(expense.getUser()));
        dto.setParticipants(expense.getParticipants().stream()
                .map(p -> {
                    ParticipantDTO participantDTO = new ParticipantDTO();
                    participantDTO.setId(p.getId());
                    participantDTO.setUser(convertToDTO(p.getUser()));
                    participantDTO.setAmount(p.getAmount());
                    participantDTO.setPercentage(p.getPercentage());
                    return participantDTO;
                }).collect(Collectors.toList()));
        return dto;
    }

    private UserDTO convertToDTO(UserTB user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setContact(user.getContact());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        return dto;
    }

    @Override
    public ExpensesTB splitExpenseExact(int expenseId, List<ParticipantDTO> participants) {
        log.info("Splitting expense ID: {} with exact amounts", expenseId);
        ExpensesTB expense = expensesRepository.findById(expenseId)
                .orElseThrow(() -> new EntityNotFoundException("Expense not found with ID " + expenseId));

        // Calculate total amount from participants
        float totalAmount = participants.stream()
                .map(ParticipantDTO::getAmount)
                .reduce(0f, Float::sum);

        // Validate if the total amount matches the expense amount
        if (totalAmount != expense.getExpensesAmount()) {
            throw new IllegalArgumentException("Total amounts do not match the expense amount");
        }

        // Convert DTOs to Participant entities
        List<Participant> participantEntities = participants.stream().map(dto -> {
            Participant participant = new Participant();
            participant.setExpense(expense);
            participant.setUser(userRepository.findById(dto.getUser().getId())
                    .orElseThrow(() -> new EntityNotFoundException("User not found with ID " +dto.getUser().getId())));
            participant.setAmount(dto.getAmount());
            return participant;
        }).collect(Collectors.toList());

        // Clear existing participants and set new ones
        expense.getParticipants().clear();
        expense.getParticipants().addAll(participantEntities);
        return expensesRepository.save(expense);
    }
}
