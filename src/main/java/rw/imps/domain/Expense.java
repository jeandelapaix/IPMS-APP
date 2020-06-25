package rw.imps.domain;

import javax.persistence.*;

@Entity
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private DailyOperationTrack dailyOperationCode;

    private String expenseName;
    private String expenseAmount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DailyOperationTrack getDailyOperationCode() {
        return dailyOperationCode;
    }

    public void setDailyOperationCode(DailyOperationTrack dailyOperationCode) {
        this.dailyOperationCode = dailyOperationCode;
    }

    public String getExpenseName() {
        return expenseName;
    }

    public void setExpenseName(String expenseName) {
        this.expenseName = expenseName;
    }

    public String getExpenseAmount() {
        return expenseAmount;
    }

    public void setExpenseAmount(String expenseAmount) {
        this.expenseAmount = expenseAmount;
    }
}
