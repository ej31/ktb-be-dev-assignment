CREATE TABLE company
(
    company_code VARCHAR(10) PRIMARY KEY,
    company_name VARCHAR(100) NOT NULL
);

CREATE TABLE stocks_history
(
    company_code VARCHAR(10),
    trade_date   DATE,
    open_price   FLOAT,
    high_price   FLOAT,
    low_price    FLOAT,
    close_price  FLOAT,
    volume       FLOAT,
    PRIMARY KEY (company_code, trade_date),
    FOREIGN KEY (company_code) REFERENCES company (company_code)
);
