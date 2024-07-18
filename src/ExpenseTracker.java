import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ExpenseTracker {

    private JFrame mainFrame;
    private JTextField expenseField;
    private JTextField amountField;
    private JComboBox<String> categoryComboBox;
    private DefaultTableModel tableModel;
    private JTable expenseTable;
    private JLabel totalLabel;

    private List<Expense> expenses;

    public ExpenseTracker() {
        expenses = new ArrayList<>();
    }

    public void initUI() {
        mainFrame = new JFrame("Expense Tracker");
        mainFrame.setSize(800, 600);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel topPanel = createTopPanel();
        JPanel centerPanel = createCenterPanel();

        mainFrame.add(topPanel, BorderLayout.NORTH);
        mainFrame.add(centerPanel, BorderLayout.CENTER);

        mainFrame.setVisible(true);
    }

    private JPanel createTopPanel() {
        JPanel topPanel = new JPanel();
        topPanel.setBackground(new Color(53, 152, 219));
        JLabel titleLabel = new JLabel("Expense Tracker");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        topPanel.add(titleLabel);
        return topPanel;
    }

    private JPanel createCenterPanel() {
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel inputPanel = createInputPanel();
        JPanel expensePanel = createExpensePanel();
        JPanel buttonPanel = createButtonPanel();

        centerPanel.add(inputPanel, BorderLayout.NORTH);
        centerPanel.add(expensePanel, BorderLayout.CENTER);
        centerPanel.add(buttonPanel, BorderLayout.SOUTH);

        return centerPanel;
    }

    private JPanel createInputPanel() {
        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBackground(Color.WHITE);
        inputPanel.setBorder(createRoundedBorder(10));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel expenseLabel = new JLabel("Expense:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        inputPanel.add(expenseLabel, gbc);

        expenseField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 0;
        inputPanel.add(expenseField, gbc);

        JLabel amountLabel = new JLabel("Amount (INR):");
        gbc.gridx = 0;
        gbc.gridy = 1;
        inputPanel.add(amountLabel, gbc);

        amountField = new JTextField(10);
        gbc.gridx = 1;
        gbc.gridy = 1;
        inputPanel.add(amountField, gbc);

        JLabel categoryLabel = new JLabel("Category:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        inputPanel.add(categoryLabel, gbc);

        categoryComboBox = new JComboBox<>(new String[]{"Groceries","Food", "Entertainment", "Utilities", "Other"});
        gbc.gridx = 1;
        gbc.gridy = 2;
        inputPanel.add(categoryComboBox, gbc);

        JButton addButton = createButton("Add Expense", new Color(46, 204, 113));
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addExpense();
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        inputPanel.add(addButton, gbc);

        JButton viewButton = createButton("View Expenses", new Color(241, 196, 15));
        viewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showExpenseView();
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        inputPanel.add(viewButton, gbc);

        return inputPanel;
    }

    private JButton createButton(String text, Color background) {
        JButton button = new JButton(text);
        button.setBackground(background);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.PLAIN, 14));
        button.setFocusPainted(false); // Remove focus border
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20)); // Add padding
        return button;
    }

    private JPanel createExpensePanel() {
        JPanel expensePanel = new JPanel(new BorderLayout());
        expensePanel.setBorder(BorderFactory.createTitledBorder(createRoundedBorder(10), "Expenses"));

        String[] columns = {"Expense Name", "Amount (INR)", "Date", "Category"};
        Object[][] data = {};
        tableModel = new DefaultTableModel(data, columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        expenseTable = new JTable(tableModel);
        expenseTable.setDefaultRenderer(Object.class, new CenterRenderer());
        JScrollPane scrollPane = new JScrollPane(expenseTable);
        scrollPane.setPreferredSize(new Dimension(600, 200));

        expensePanel.add(scrollPane, BorderLayout.CENTER);
        return expensePanel;
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        totalLabel = new JLabel("Total Amount: ₹0.00");
        totalLabel.setFont(new Font("Arial", Font.BOLD, 16));
        buttonPanel.add(totalLabel);
        return buttonPanel;
    }

    private void addExpense() {
        try {
            String expenseName = expenseField.getText();
            double amount = Double.parseDouble(amountField.getText());
            String category = (String) categoryComboBox.getSelectedItem();
            Expense expense = new Expense(expenseName, amount, new Date(), category); // Create expense with current date
            expenses.add(expense);
            Object[] row = {expense.getName(), expense.getAmount(), formatDate(expense.getDate()), expense.getCategory()};
            tableModel.addRow(row);
            updateTotal();
            expenseField.setText("");
            amountField.setText("");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(mainFrame, "Invalid amount format", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private String formatDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(date);
    }

    private void updateTotal() {
        double total = expenses.stream().mapToDouble(Expense::getAmount).sum();
        totalLabel.setText("Total Amount: ₹" + String.format("%.2f", total));
    }

    private void showExpenseView() {
        SwingUtilities.invokeLater(() -> {
            ViewExpensesPanel expensesPanel = new ViewExpensesPanel(expenses);
            JOptionPane.showMessageDialog(mainFrame, expensesPanel, "View Expenses", JOptionPane.PLAIN_MESSAGE);
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new ExpenseTracker().initUI();
        });
    }

    private static class CenterRenderer extends DefaultTableCellRenderer {
        public CenterRenderer() {
            setHorizontalAlignment(SwingConstants.CENTER);
        }
    }

    private Border createRoundedBorder(int radius) {
        return BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY),
                BorderFactory.createEmptyBorder(radius, radius, radius, radius)
        );
    }
}
