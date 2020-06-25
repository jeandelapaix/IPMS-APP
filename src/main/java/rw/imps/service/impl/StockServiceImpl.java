package rw.imps.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rw.imps.domain.Stock;
import rw.imps.repository.StockRepo;
import rw.imps.service.StockService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class StockServiceImpl implements StockService {

    @Autowired
    private StockRepo stockRepo;

    @Override
    public Stock createStock(Stock stock) {
        stock.setStockCode("stock-"+stock.getServiceForStock().getServiceName()+ LocalDateTime.now());
        stock.setNumberOfItems(0);
        return stockRepo.save(stock);
    }

    @Override
    public Stock updateStock(Stock stock, Long id) throws Exception {
        Optional<Stock> stock1 = stockRepo.findById(id);

        if (stock1.get() != null) {
            Stock stockToSave = (Stock) stock.clone();
            stockRepo.save(stockToSave);
        }
        throw new Exception("Something is wrong");
    }

    @Override
    public Stock findStock(Long id) {
        return stockRepo.findById(id).get();
    }

    @Override
    public List<Stock> viewAllStock() {
        return stockRepo.findAll();
    }

    @Override
    public Stock stockIn(Long id, int numberOfItemsIn) throws Exception {
        Optional<Stock> searchedStock = stockRepo.findById(id);
        if (searchedStock == null) {
            throw new Exception("Invalid stock");
        }
        Stock newStockIn = searchedStock.get();
        int currentQty = newStockIn.getNumberOfItems();
        newStockIn.setNumberOfItems(currentQty + numberOfItemsIn);
        return stockRepo.save(newStockIn);
    }

    @Override
    public Stock stockOut(Long id, int numberOfItemsOut) throws Exception {
        Optional<Stock> searchedStock = stockRepo.findById(id);
        if (searchedStock == null) {
            throw new Exception("Invalid stock");
        }
        Stock newStockOut = searchedStock.get();
        int currentQty = newStockOut.getNumberOfItems();
        int newItem = currentQty - numberOfItemsOut;
        if (newItem < 0) {
            throw new Exception("Beyond stock");
        }
        newStockOut.setNumberOfItems(newItem);
        return stockRepo.save(newStockOut);
    }
}
