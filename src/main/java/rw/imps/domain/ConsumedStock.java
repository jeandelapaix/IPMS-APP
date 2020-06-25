package rw.imps.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class ConsumedStock {

    @Id
    private Long id;

    @ManyToOne
    private Stock stock;
    private int usedItems;
    private int remainingItems;

    @ManyToOne
    private DailyOperationTrack dailyOperationCode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Stock getStock() {
        return stock;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
    }

    public int getUsedItems() {
        return usedItems;
    }

    public void setUsedItems(int usedItems) {
        this.usedItems = usedItems;
    }

    public int getRemainingItems() {
        return remainingItems;
    }

    public void setRemainingItems(int remainingItems) {
        this.remainingItems = remainingItems;
    }

    public DailyOperationTrack getDailyOperationCode() {
        return dailyOperationCode;
    }

    public void setDailyOperationCode(DailyOperationTrack dailyOperationCode) {
        this.dailyOperationCode = dailyOperationCode;
    }
}
