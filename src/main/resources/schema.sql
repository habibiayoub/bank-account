CREATE TABLE IF NOT EXISTS account (
    id INT NOT NULL AUTO_INCREMENT,
    account_number VARCHAR (255) NOT NULL UNIQUE,
    type VARCHAR (50) NOT NULL ,
    balance DECIMAL(10, 2),
    is_overdraft_authorised BOOLEAN,
    overdraft_amount DECIMAL(10, 2),
    deposite_limit DECIMAL(10, 2),
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS transaction (
    id INT NOT NULL AUTO_INCREMENT,
    type VARCHAR (50) NOT NULL ,
    amount DECIMAL(10, 2),
    date TIMESTAMP,
    account_id INT,
    PRIMARY KEY (id)
);

ALTER TABLE transaction
    ADD FOREIGN KEY (account_id)
        REFERENCES account(id);
