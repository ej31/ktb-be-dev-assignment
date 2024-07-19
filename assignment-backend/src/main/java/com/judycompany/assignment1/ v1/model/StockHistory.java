package com.judycompany.assignment1.v1.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import java.time.LocalDate;

@Entity(name = "stocks_history")
public class StockHistory {
    @Id
    private Long id;
    private String companyCode;
    private LocalDate tradeDate;
    private long closePrice;

    @ManyToOne
    @JoinColumn(name = "companyCode", insertable = false, updatable = false)
    private com.judycompany.assignment1.v1.model.Company company;

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public LocalDate getTradeDate() {
        return tradeDate;
    }

    public void setTradeDate(LocalDate tradeDate) {
        this.tradeDate = tradeDate;
    }

    public long getClosePrice() {
        return closePrice;
    }

    public void setClosePrice(long closePrice) {
        this.closePrice = closePrice;
    }

    public com.judycompany.assignment1.v1.model.Company getCompany() {
        return company;
    }

    public void setCompany(com.judycompany.assignment1.v1.model.Company company) {
        this.company = company;
    }
}
