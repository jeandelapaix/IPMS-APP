package rw.imps.domain;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
public class AgentTransaction {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private ServicesToProvide providedService;

    private Date transactionDateTime;
    private double negotiablePrice;

    @ManyToOne
    private DailyOperationTrack dailyOperationCode;

    @ManyToOne
    private Account performedAccount;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ServicesToProvide getProvidedService() {
        return providedService;
    }

    public void setProvidedService(ServicesToProvide providedService) {
        this.providedService = providedService;
    }

    public Date getTransactionDateTime() {
        return transactionDateTime;
    }

    public void setTransactionDateTime(Date transactionDateTime) {
        this.transactionDateTime = transactionDateTime;
    }

    public double getNegotiablePrice() {
        return negotiablePrice;
    }

    public void setNegotiablePrice(double negotiablePrice) {
        this.negotiablePrice = negotiablePrice;
    }

    public DailyOperationTrack getDailyOperationCode() {
        return dailyOperationCode;
    }

    public void setDailyOperationCode(DailyOperationTrack dailyOperationCode) {
        this.dailyOperationCode = dailyOperationCode;
    }

    public Account getPerformedAccount() {
        return performedAccount;
    }

    public void setPerformedAccount(Account performedAccount) {
        this.performedAccount = performedAccount;
    }
}
