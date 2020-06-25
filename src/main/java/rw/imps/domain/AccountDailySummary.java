package rw.imps.domain;

import javax.persistence.*;

@Entity
public class AccountDailySummary {

    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    private DailyOperationTrack dailyOperationCode;

    @ManyToOne
    private Account account;

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

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
