package rw.imps.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import rw.imps.domain.enums.DailyOperationTrackStatus;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Entity
public class DailyOperationTrack {

    @Id
    @GeneratedValue
    private Long id;
    @Column(unique = true)
    private String dailyCode;
    @Column(unique = true)
    private Date todayDate;
    private boolean dailyOperationTrackStatus;
    private double startAmount;
    private double closeAmount;

    @JsonIgnore
    @OneToMany(mappedBy = "dailyOperationCode")
    private List<Expense> expenseList;

    @JsonIgnore
    @OneToMany(mappedBy = "dailyOperationCode")
    private List<ConsumedStock> consumedStockList;

    @JsonIgnore
    @OneToMany(mappedBy = "dailyOperationCode")
    private List<AgentTransaction> agentTransactionList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDailyCode() {
        return dailyCode;
    }

    public void setDailyCode(String dailyCode) {
        this.dailyCode = dailyCode;
    }

    public Date getTodayDate() {
        return todayDate;
    }

    public void setTodayDate(Date todayDate) {
        this.todayDate = todayDate;
    }

    public boolean isDailyOperationTrackStatus() {
        return dailyOperationTrackStatus;
    }

    public void setDailyOperationTrackStatus(boolean dailyOperationTrackStatus) {
        this.dailyOperationTrackStatus = dailyOperationTrackStatus;
    }

    public double getStartAmount() {
        return startAmount;
    }

    public void setStartAmount(double startAmount) {
        this.startAmount = startAmount;
    }

    public double getCloseAmount() {
        return closeAmount;
    }

    public void setCloseAmount(double closeAmount) {
        this.closeAmount = closeAmount;
    }

    public List<Expense> getExpenseList() {
        return expenseList;
    }

    public void setExpenseList(List<Expense> expenseList) {
        this.expenseList = expenseList;
    }

    public List<ConsumedStock> getConsumedStockList() {
        return consumedStockList;
    }

    public void setConsumedStockList(List<ConsumedStock> consumedStockList) {
        this.consumedStockList = consumedStockList;
    }

    public List<AgentTransaction> getAgentTransactionList() {
        return agentTransactionList;
    }

    public void setAgentTransactionList(List<AgentTransaction> agentTransactionList) {
        this.agentTransactionList = agentTransactionList;
    }

}
