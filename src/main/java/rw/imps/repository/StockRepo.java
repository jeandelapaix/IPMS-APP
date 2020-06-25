package rw.imps.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rw.imps.domain.Stock;


@Repository
public interface StockRepo extends JpaRepository<Stock, Long> {

}
