import java.util.Date;

public class Expense {

    private String name;
    private double amount;
    private Date date;
    private String category;

    public Expense(String name, double amount, Date date, String category) {
        this.name = name;
        this.amount = amount;
        this.date = date;
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public double getAmount() {
        return amount;
    }

    public Date getDate() {
        return date;
    }

    public String getCategory() {
        return category;
    }
}