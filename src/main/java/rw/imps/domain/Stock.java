package rw.imps.domain;


import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Stock implements Cloneable{

    @Id
    @GeneratedValue
    private Long id;
    @Column(unique = true)
    private String stockCode;
    private int numberOfItems;
    private LocalDateTime stockDateTime;

    @ManyToOne
    private ServicesToProvide serviceForStock ;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStockCode() {
        return stockCode;
    }

    public void setStockCode(String stockCode) {
        this.stockCode = stockCode;
    }

    public int getNumberOfItems() {
        return numberOfItems;
    }

    public void setNumberOfItems(int numberOfItems) {
        this.numberOfItems = numberOfItems;
    }

    public LocalDateTime getStockDateTime() {
        return stockDateTime;
    }

    public void setStockDateTime(LocalDateTime stockDateTime) {
        this.stockDateTime = stockDateTime;
    }

    public ServicesToProvide getServiceForStock() {
        return serviceForStock;
    }

    public void setServiceForStock(ServicesToProvide serviceForStock) {
        this.serviceForStock = serviceForStock;
    }

    public Object clone() throws
            CloneNotSupportedException
    {
        return super.clone();
    }

}
