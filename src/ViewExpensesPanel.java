import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.List;

public class ViewExpensesPanel extends JPanel {

    private JTable expenseTable;

    public ViewExpensesPanel(List<Expense> expenses) {
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(10, 10, 10, 10));

        JLabel titleLabel = new JLabel("All Expenses");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(titleLabel, BorderLayout.NORTH);

        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.addColumn("Expense Name");
        tableModel.addColumn("Amount (INR)");
        tableModel.addColumn("Date");
        tableModel.addColumn("Category");

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

        for (Expense expense : expenses) {
            Object[] row = {expense.getName(), expense.getAmount(), dateFormat.format(expense.getDate()), expense.getCategory()};
            tableModel.addRow(row);
        }

        expenseTable = new JTable(tableModel);
        expenseTable.setDefaultRenderer(Object.class, new CenterRenderer());
        JScrollPane scrollPane = new JScrollPane(expenseTable);
        add(scrollPane, BorderLayout.CENTER);
    }

    private static class CenterRenderer extends DefaultTableCellRenderer {
        public CenterRenderer() {
            setHorizontalAlignment(SwingConstants.CENTER);
        }
    }
}