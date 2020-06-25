package rw.imps.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import rw.imps.domain.Stock;
import rw.imps.service.StockService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/stocks")
public class StockController {

    @Autowired
    private StockService stockService;

    @PostMapping
    @ApiOperation(value = "", authorizations = {@Authorization(value="jwtToken")})
    public ResponseEntity<?> createNewStock(@RequestBody @Valid Stock stock, BindingResult result) throws Exception {
        if (!result.hasErrors()) {
            return ResponseEntity.ok(stockService.createStock(stock));
        }
        throw new Exception("Something went wrong");
    }

    @GetMapping
    @ApiOperation(value = "", authorizations = {@Authorization(value="jwtToken")})
    public List<Stock> viewStock() {
        return stockService.viewAllStock();
    }

    @PostMapping("/stockin/{id}/{quantity}")
    @ApiOperation(value = "", authorizations = {@Authorization(value = "jwtToken")})
    public ResponseEntity<?> stockIn(@PathVariable Long id, @PathVariable int quantity) throws Exception {
        return ResponseEntity.ok(stockService.stockIn(id,quantity));
    }

    @PostMapping("/stockout/{id}/{quantity}")
    @ApiOperation(value = "", authorizations = {@Authorization(value = "jwtToken")})
    public ResponseEntity<?> stockOut(@PathVariable Long id, @PathVariable int quantity) throws Exception {
        return ResponseEntity.ok(stockService.stockOut(id,quantity));
    }
}
