package rw.imps.service;

import rw.imps.domain.Stock;

import java.util.List;

public interface StockService {

    public Stock createStock(Stock stock);

    public Stock updateStock(Stock stock, Long id) throws CloneNotSupportedException, Exception;

    public Stock findStock(Long id);

    public List<Stock> viewAllStock();

    public Stock stockIn(Long id, int numberOfItemsIn) throws Exception;

    public Stock stockOut(Long id, int numberOfItemsOut) throws Exception;
}
