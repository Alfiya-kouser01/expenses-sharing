package com.assignment.expenses_sharing.util;

import com.assignment.expenses_sharing.entities.ExpensesTB;
import java.util.List;

public class BalanceSheetUtil {

    public static String createBalanceSheet(List<ExpensesTB> expenses) {
        // Logic to generate balance sheet (e.g., create a CSV file or a PDF)
        StringBuilder sb = new StringBuilder();
        sb.append("Expense Report\n");
        sb.append("------------------\n");

        for (ExpensesTB expense : expenses) {
            sb.append("ID: ").append(expense.getId()).append("\n");
            sb.append("Name: ").append(expense.getExpensesName()).append("\n");
            sb.append("Amount: ").append(expense.getExpensesAmount()).append("\n");
            sb.append("Method: ").append(expense.getSplitMethod()).append("\n");
            sb.append("User: ").append(expense.getUser().getName()).append("\n");
            sb.append("------------------\n");
        }

        // In real applications, you would generate a file (CSV, PDF) here and return its URL or path.
        return sb.toString();
    }
}
