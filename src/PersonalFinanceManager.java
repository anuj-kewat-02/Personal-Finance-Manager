import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PersonalFinanceManager {

    private JFrame mainFrame;

    public void initGUI() {
        mainFrame = new JFrame("Personal Finance Manager");
        mainFrame.setSize(400, 200);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JButton openExpenseTrackerButton = new JButton("Open Expense Tracker");
        openExpenseTrackerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openExpenseTracker();
            }
        });

        panel.add(openExpenseTrackerButton, BorderLayout.CENTER);

        mainFrame.add(panel);
        mainFrame.setVisible(true);
    }

    private void openExpenseTracker() {
        SwingUtilities.invokeLater(() -> {
            new ExpenseTracker().initUI();
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new PersonalFinanceManager().initGUI();
        });
    }
}
