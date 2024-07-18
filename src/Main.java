import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            PersonalFinanceManager app = new PersonalFinanceManager();
            app.initGUI();
        });
    }
}
